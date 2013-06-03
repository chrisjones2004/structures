package trussoptimizater.Truss;


import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Truss.Optimize.OptimizeMethods;
import trussoptimizater.Truss.ElementModels.NodeModel;
import trussoptimizater.Truss.ElementModels.BarModel;
import trussoptimizater.Truss.ElementModels.LoadModel;
import trussoptimizater.Truss.ElementModels.SupportModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.ElementModels.SectionModel;
import trussoptimizater.Truss.Elements.*;

import trussoptimizater.Truss.Elements.Load;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.Materials.Material;
import trussoptimizater.Truss.Materials.Steel;
import trussoptimizater.Truss.Sections.TubularSection;

/**
 * This class represents a model of truss.
 * @author Chris
 */
public class TrussModel extends java.util.Observable implements java.util.Observer {

    private NodeModel nodeModel;
    private BarModel barModel;
    private SupportModel supportModel;
    private LoadModel loadModel;
    private SectionModel sectionModel;
    private ArrayList<Load> selfWeightLoads;

    /**
     * A combined arraylist of sections, currently includes CHS, RHS, SHS
     */
    //private ArrayList<TubularSection> sections = readFiles();
    /**
     * A HashMap of materials
     */
    private HashMap<String, Material> materials = new HashMap<String, Material>();
    /**
     * Truss is analyzed after you have analyzed it. If truss model is edited in any way, analyzed is set to false
     */
    private boolean analysed;
    /**
     * Object containing all analyzer objects
     */
    private AnalysisMethods analysisMethods;
    /**
     * Object containing all optimizer objects
     */
    private OptimizeMethods optimizeMethods;
    /**
     * If user is not adding, removing, selecting or optimizing then operation should
     * be set to TrussModel.IDLE
     */
    //public static final int OPERATION_IDLE = 0;
    /**
     * If user is adding lots of elements to truss, for example openeing a file or creating a truss, then
     * operation should be set to TrussModel.MASS_ADDING. This is because trussmodel will not update observers
     * untill opertaion is set to TrussModel.IDLE.
     */
    //public static final int OPERATION_MASS_ADDING = 1;
    /**
     * If user is removing lots of elements to truss, for example select all-delete or new project, then
     * operation should be set to TrussModel.MASS_REMOVING. This is because trussmodel will not update observers
     * untill opertaion is set to TrussModel.IDLE.
     */
    //public static final int OPERATION_MASS_REMOVING = 2;
    /**
     * If user is selecting multiple elements either using CLI or using drag selector, then
     * operation should be set to TrussModel.MASS_SELECTING. This is because trussmodel will not update observers
     * untill opertaion is set to TrussModel.IDLE.
     */
    //public static final int OPERATION_MASS_SELECTING = 3;
    /**
     * If optimizing is occuring, then operation should be set to TrussModel.OPTIMIZING. This is
     * because trussmodel will not update observers
     * untill opertaion is set to TrussModel.IDLE.
     */
    //public static final int OPERATION_OPTIMIZING = 4;
    /**
     * To change operation from TrussModel.OPTIMIZING you must first set it to TrussModel.OPTIMIZING_STOPPED
     */
    //public static final int OPERATION_OPTIMIZING_STOPPED = 5;
    /**Probably should move these to GUI class, howver propertypanel get updated
     * from truss model so stay here for time being
     */
    //private int operation = OPERATION_IDLE;

    public TrussModel() {

        materials.put(Steel.MATERIAL_NAME, new Steel());
        selfWeightLoads = new ArrayList<Load>();

        analysisMethods = new AnalysisMethods(this);
        analysisMethods.addObserver(this);
        optimizeMethods = new OptimizeMethods(this);
        optimizeMethods.addObserver(this);

        analysed = false;
        //readFiles();


        nodeModel = new NodeModel(this);
        barModel = new BarModel(this);
        supportModel = new SupportModel(this);
        loadModel = new LoadModel(this);
        sectionModel = new SectionModel(this);

        nodeModel.addObserver(this);
        barModel.addObserver(this);
        loadModel.addObserver(this);
        supportModel.addObserver(this);
    }



    /**
     *This method removes invalid elements associated with a specified Node
     * <ul>
     *<li>A bar must be attached to 2 nodes
     *<li>A load must be attached to 1 node
     *<li>A Support must be attached to 1 node
     * </ul>
     * <p>
     *If any of these requiments are not met the element is removed
     * </P>
     * @param node The node to be deleted
     */
    public void removeInvalidTrussElements(Node node) {
        //remove bars if they do not have two nodes

        for (int i = 0; i < barModel.size(); i++) {
            if (barModel.get(i).getNode1().equals(node) || barModel.get(i).getNode2().equals(node)) {
                barModel.remove(i);
                i--;
            }
        }

        //remove loads if node is not valid
        for (int i = 0; i < loadModel.size(); i++) {
            if (loadModel.get(i).getNode().equals(node)) {
                loadModel.remove(i);
                i--;
            }
        }

        //remove support if node is not valid
        for (int i = 0; i < supportModel.size(); i++) {
            if (supportModel.get(i).getNode().equals(node)) {
                supportModel.remove(i);
                i--;
            }
        }
    }

    /**
     * This method removes all invalid elements by looping through all elements and checking
     * the following
     *<p>
     *<ul>
     *<li>A bar must be attached to 2 nodes
     *<li>A load must be attached to 1 node
     *<li>A Support must be attached to 1 node
     *</ul>
     *<p>
     *If any of these requiments are not met the element is removed
     */
    public void removeInvalidTrussElements() {
        //remove bars if they do not have two nodes
        for (int i = 0; i < barModel.size(); i++) {
            Node node1 = barModel.get(i).getNode1();
            Node node2 = barModel.get(i).getNode2();
            if (!nodeModel.contains(node1) || !nodeModel.contains(node2)) {
                barModel.remove(i);
                i--;
            }
        }

        //remove loads if node is not valid
        for (int i = 0; i < loadModel.size(); i++) {
            Node node = loadModel.get(i).getNode();
            if (!nodeModel.contains(node)) {
                loadModel.remove(i);
                i--;
            }
        }

        //remove support if node is not valid
        for (int i = 0; i < supportModel.size(); i++) {
            Node node = supportModel.get(i).getNode();
            if (!nodeModel.contains(node)) {
                supportModel.remove(i);
                i--;
            }
        }
    }

    /**
     * 
     * @return mass of truss in Kg
     */
    public double getMass() {
        double totalMass = 0;
        for (int i = 0; i < barModel.size(); i++) {
            totalMass += barModel.get(i).getMass();
        }
        return totalMass;
    }

    /**
     * Loops through all bars in bar model and finds the maximum stress
     * @return the maximum stress in KN/mm^2
     */
    public double getMaxBarStress() {
        double maxStress = Double.MIN_VALUE;
        for (int i = 0; i < barModel.size(); i++) {
            if (Math.abs(barModel.get(i).getStress()) > maxStress) {
                maxStress = Math.abs(barModel.get(i).getStress());
            }
        }
        return maxStress;
    }

    /**
     * Loops through all the bars in bar model and finds the maximum axial force
     * @return the maximum axial force in KN
     */
    public double getMaxAxialForce() {
        return 0;
    }

    /**
     * Loops through all the loads in load model and finds the absolute maximum load
     * @return the absolute maximum load in KN
     */
    public double getMaxABSLoad() {
        double maxLoad = Double.MIN_VALUE;
        for (int i = 0; i < loadModel.size(); i++) {
            if (Math.abs(loadModel.get(i).getLoad()) > maxLoad) {
                maxLoad = Math.abs(loadModel.get(i).getLoad());
            }
        }
        return maxLoad;
    }

    /**
     * Used to construct material comboBoxes in the bar table
     * @return a array of the keys used in the Hashmap materials.
     */
    public String[] getMaterialKeys() {
        String[] materialKeys = new String[materials.keySet().size()];
        for (int i = 0; i < materials.keySet().size(); i++) {
            materialKeys[i] = materials.keySet().toArray()[i].toString();
        }
        return materialKeys;
    }

    /**
     * Calculate the self weight of each bar and add this half this load to each node
     */
    public void calculateSelfWeightOfTruss() {
        selfWeightLoads.clear();
        double barWeight = 0;
        for (int i = 0; i < barModel.size(); i++) {
            barWeight = barModel.get(i).getWeight() / 2; //So that barweight is now in KN
            selfWeightLoads.add(new Load(supportModel.size() + 1, barModel.get(i).getNode1(), barWeight, Load.VERTICAL_LOAD, Load.SELF_WEIGHT));
            selfWeightLoads.add(new Load(supportModel.size() + 1, barModel.get(i).getNode2(), barWeight, Load.VERTICAL_LOAD, Load.SELF_WEIGHT));
        }
    }

    public void update(Observable o, Object arg) {
        if(arg instanceof ModelEvent ){
            ModelEvent ee = (ModelEvent)arg;
            if(ee.getEventType() == ModelEvent.ADD_ELEMENTS ||
                    ee.getEventType() == ModelEvent.REMOVE_ELEMENTS){
                setAnalysed(false);
            }
        }
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Clear all elements arraylist. This method is called when starting a new project
     */
    public void resetArrayLists() {
        barModel.clear();
        nodeModel.clear();
        loadModel.clear();
        supportModel.clear();
    }

    /**
     *
     * @return trus if truss has been analyzed
     */
    public boolean isAnalysed() {
        return analysed;
    }

    public ElementModel getElementModel(Element e){


        if(e instanceof Node ){
            return this.nodeModel;
        }else if(e instanceof Bar){
            return this.barModel;
        }else if(e instanceof Load){
            return this.loadModel;
        }else if(e instanceof Support){
            return this.supportModel;
        }else{
            return null;
        }
    }

    /**
     * This method is used for saving all project data
     * @return an arrayList of all the element arrays.
     */
    public ArrayList<Object[]> getElementArrays() {
        ArrayList<Object[]> elementArrays = new ArrayList<Object[]>();
        elementArrays.add(nodeModel.toArray());
        elementArrays.add(barModel.toArray());
        elementArrays.add(supportModel.toArray());
        elementArrays.add(loadModel.toArray());
        elementArrays.add(sectionModel.toArray());
        return elementArrays;
    }

    public ElementModel getAllElements(){
        return nodeModel;
    }

    public NodeModel getNodeModel() {
        return nodeModel;
    }

    public BarModel getBarModel() {
        return barModel;
    }

    public SupportModel getSupportModel() {
        return supportModel;
    }

    public LoadModel getLoadModel() {
        return loadModel;
    }

    public SectionModel getSectionModel() {
        return sectionModel;
    }

    public ArrayList<Load> getSelfWeightLoads() {
        return selfWeightLoads;
    }

    public HashMap<String, Material> getMaterials() {
        return materials;
    }

    /*public int getOperation() {
        return operation;
    }*/

    public AnalysisMethods getAnalysisMethods() {
        return analysisMethods;
    }

    public OptimizeMethods getOptimizeMethods() {
        return optimizeMethods;
    }



    /**
     *
     * @param operation Should be one of the following
     * <ul>
     * <li> TrussModel.OPERATION_IDLE
     * <li> TrussModel.OPERATION_MASS_REMOVING
     * <li> TrussModel.OPERATION_MASS_ADDING
     * <li> TrussModel.OPERATION_OPTIMIZING
     * <li> TrussModel.OPERATION_OPTIMIZING_STOPPED
     * </ul>
     */
    /*public void setOperation(int operation) {
        if (operation == TrussModel.OPERATION_OPTIMIZING_STOPPED) {
            this.operation = TrussModel.OPERATION_IDLE;
        } else if (this.operation != TrussModel.OPERATION_OPTIMIZING) {
            this.operation = operation;
        }
        setChanged();
        notifyObservers(this);

    }*/

    public void setAnalysed(boolean analysed) {
        this.analysed = analysed;
        setChanged();
        notifyObservers(this);
    }

    /**
     * When a file is opened this method is called to update all the element arrays with arrays from
     * data file
     * @param elementArrays ArrayList containing a NodeArray, BarArray, SupportArray and LoadArray in that exact
     * order.
     */
    public void setElementArrays(ArrayList<Object[]> elementArrays) {
        nodeModel.setElements(elementArrays.get(0));
        barModel.setElements(elementArrays.get(1));
        supportModel.setElements(elementArrays.get(2));
        loadModel.setElements(elementArrays.get(3));
        sectionModel.setSections(elementArrays.get(4));
        setChanged();
        notifyObservers(this);
    }


    public void printTrussDetails() {
        System.out.println("\nNodes");
        for (int i = 0; i < this.nodeModel.size(); i++) {
            System.out.println(nodeModel.get(i).toString());
        }

        System.out.println("\nBars");
        for (int i = 0; i < this.barModel.size(); i++) {
            System.out.println(barModel.get(i).toString());
        }

        System.out.println("\nLoads");
        for (int i = 0; i < this.loadModel.size(); i++) {
            System.out.println(this.loadModel.get(i).toString());
        }

        System.out.println("\nSupports");
        for (int i = 0; i < this.supportModel.size(); i++) {
            System.out.println(this.supportModel.get(i).toString());
        }
    }
}

