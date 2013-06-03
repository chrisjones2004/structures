package trussoptimizater.Gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import trussoptimizater.Truss.TrussModel;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Elements.*;
import trussoptimizater.Truss.Materials.Steel;
import trussoptimizater.Truss.SectionLibrary;
import trussoptimizater.Truss.Sections.TubularSection;

public abstract class TrussFactory {


    /**
     * statically determinate Truss
     * @param truss
     * @param gui
     */
    public static void create7BarWarrenTruss(TrussModel truss) { //, GUI gui

        truss.getNodeModel().add(new Node(1, 0, 0));
        truss.getNodeModel().add(new Node(2, 100, 0));
        truss.getNodeModel().add(new Node(3, 200, 0));
        truss.getNodeModel().add(new Node(4, 50, -50));
        truss.getNodeModel().add(new Node(5, 150, -50));
        int sectionIndex = 0;


        truss.getBarModel().add(new Bar(1, truss.getNodeModel().get(0), truss.getNodeModel().get(3), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(2, truss.getNodeModel().get(3), truss.getNodeModel().get(1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(3, truss.getNodeModel().get(1), truss.getNodeModel().get(4), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(4, truss.getNodeModel().get(4), truss.getNodeModel().get(2), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(5, truss.getNodeModel().get(0), truss.getNodeModel().get(1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(6, truss.getNodeModel().get(1), truss.getNodeModel().get(2), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(7, truss.getNodeModel().get(3), truss.getNodeModel().get(4), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(0), Support.FIXED, Support.FIXED, Support.FREE));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(2), Support.FREE, Support.FIXED, Support.FREE));
        } catch (Exception ex) {
            System.out.println(ex);
        }


        truss.getLoadModel().add(new Load(1, truss.getNodeModel().get(3), 100, Load.VERTICAL_LOAD, Load.USER_DEFINED));
        truss.getLoadModel().add(new Load(2, truss.getNodeModel().get(4), 100, Load.VERTICAL_LOAD, Load.USER_DEFINED));
    }


    /**
     * statically Indeterminate Truss
     * @param truss
     * @param gui
     */
    public static void create9BarPrattTruss(TrussModel truss) { //, GUI gui
        truss.getNodeModel().add(new Node(1, 0, 0));
        truss.getNodeModel().add(new Node(2, 100, 0));
        truss.getNodeModel().add(new Node(3, 200, 0));
        truss.getNodeModel().add(new Node(4, 300, 0));
        truss.getNodeModel().add(new Node(5, 100, -100));
        truss.getNodeModel().add(new Node(6, 200, -100));
        int sectionIndex = 0;

        //bottom bars
        truss.getBarModel().add(new Bar(1, truss.getNodeModel().get(0), truss.getNodeModel().get(1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(2, truss.getNodeModel().get(1), truss.getNodeModel().get(2), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(3, truss.getNodeModel().get(2), truss.getNodeModel().get(3), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));

        //top bars
        truss.getBarModel().add(new Bar(4, truss.getNodeModel().get(4), truss.getNodeModel().get(5), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));

        //vertical bars
        truss.getBarModel().add(new Bar(5, truss.getNodeModel().get(1), truss.getNodeModel().get(4), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(6, truss.getNodeModel().get(2), truss.getNodeModel().get(5), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));

        //diagonal bars
        truss.getBarModel().add(new Bar(7, truss.getNodeModel().get(2), truss.getNodeModel().get(4), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(8, truss.getNodeModel().get(0), truss.getNodeModel().get(4), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(9, truss.getNodeModel().get(3), truss.getNodeModel().get(5), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT, SectionLibrary.SECTIONS.get(sectionIndex)));



        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(0), Support.FIXED, Support.FIXED, Support.FREE));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(3), Support.FIXED, Support.FIXED, Support.FREE));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        truss.getLoadModel().add(new Load(1, truss.getNodeModel().get(4), 100, Load.VERTICAL_LOAD, Load.USER_DEFINED));
        truss.getLoadModel().add(new Load(2, truss.getNodeModel().get(5), 100, Load.VERTICAL_LOAD, Load.USER_DEFINED));
    }

    public static void create45BarPrattTruss(TrussModel truss) { //,TOGui gui

        int sectionIndex = 0;
        //int sectionIndex = 81;
        //TubularSection section = SectionLibrary.SECTIONS.get(sectionIndex);
       // String sectionName = section.getName();
        //gui.getSectionDialog().addSection(sectionName);


        int noOfFields = 12;
        int load = 100;//KN
        int spacing = 100; //cm

        createPrattTruss(noOfFields * spacing, (noOfFields - 2) * spacing, spacing, noOfFields, truss);

        //adding loads
        for (int i = 1; i < noOfFields; i++) {
            truss.getLoadModel().add(new Load(i, truss.getNodeModel().get(i), load, Load.VERTICAL_LOAD, Load.USER_DEFINED));
        }

        //Assigning sections to all bars
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            truss.getBarModel().get(i).setSection(SectionLibrary.SECTIONS.get(sectionIndex));
        }

    }

    public static void create15BarCantilever(TrussModel truss) {//, GUI gui

        int sectionIndex = 15;


       // TubularSection section = SectionLibrary.SECTIONS.get(sectionIndex);
        //String sectionName = section.getName();
        //gui.getSectionDialog().addSection(sectionName);


        truss.getNodeModel().add(new Node(1, 0, 0));
        truss.getNodeModel().add(new Node(2, 308, 0));
        truss.getNodeModel().add(new Node(3, 616, 0));
        truss.getNodeModel().add(new Node(4, 924, 0));
        truss.getNodeModel().add(new Node(5, 0, 308));
        truss.getNodeModel().add(new Node(6, 308, 308));
        truss.getNodeModel().add(new Node(7, 616, 308));
        truss.getNodeModel().add(new Node(8, 924, 308));


        //top bars
        truss.getBarModel().add(new Bar(1, truss.getNodeModel().get(0), truss.getNodeModel().get(1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(2, truss.getNodeModel().get(1), truss.getNodeModel().get(2), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(3, truss.getNodeModel().get(2), truss.getNodeModel().get(3), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));

        //bottom bars
        truss.getBarModel().add(new Bar(4, truss.getNodeModel().get(4), truss.getNodeModel().get(5), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(5, truss.getNodeModel().get(5), truss.getNodeModel().get(6), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(6, truss.getNodeModel().get(6), truss.getNodeModel().get(7), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));

        //vertical bars
        truss.getBarModel().add(new Bar(7, truss.getNodeModel().get(1), truss.getNodeModel().get(5), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(8, truss.getNodeModel().get(2), truss.getNodeModel().get(6), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(9, truss.getNodeModel().get(3), truss.getNodeModel().get(7), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));

        //digonal bars
        truss.getBarModel().add(new Bar(10, truss.getNodeModel().get(0), truss.getNodeModel().get(5), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(11, truss.getNodeModel().get(4), truss.getNodeModel().get(1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(12, truss.getNodeModel().get(1), truss.getNodeModel().get(6), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(13, truss.getNodeModel().get(5), truss.getNodeModel().get(2), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(14, truss.getNodeModel().get(2), truss.getNodeModel().get(7), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        truss.getBarModel().add(new Bar(15, truss.getNodeModel().get(6), truss.getNodeModel().get(3), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));

        //supports


        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(0), Support.FIXED, Support.FIXED, Support.FIXED));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(4), Support.FIXED, Support.FIXED, Support.FIXED));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        truss.getLoadModel().add(new Load(1, truss.getNodeModel().get(7), 44.5, Load.VERTICAL_LOAD, Load.USER_DEFINED));

    }

    public static void create35BarCantilever(TrussModel truss) { //, GUI gui
        int sectionIndex = 0;
        //TubularSection section = SectionLibrary.SECTIONS.get(sectionIndex);
        //String sectionName = section.getName();
        //gui.getSectionDialog().addSection(sectionName);


        //bottom nodes
        int horizontalSpacing = 100;
        int nodeIndex = 1;
        for (int i = 0; i < 10; i++) {
            truss.getNodeModel().add(new Node(nodeIndex++, horizontalSpacing * i, 0));
        }
        //top nodes
        for (int i = 1; i < 10; i++) {
            truss.getNodeModel().add(new Node(nodeIndex++, horizontalSpacing * i, -100));
        }
        int barIndex = 1;
        //bottom bars
        for (int i = 0; i < 9; i++) {
            truss.getBarModel().add(new Bar(barIndex++, truss.getNodeModel().get(i), truss.getNodeModel().get(i + 1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        }

        //top bars
        for (int i = 10; i < 18; i++) {
            truss.getBarModel().add(new Bar(barIndex++, truss.getNodeModel().get(i), truss.getNodeModel().get(i + 1), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        }

        //vert bars
        for (int i = 1; i < 10; i++) {
            truss.getBarModel().add(new Bar(barIndex++, truss.getNodeModel().get(i), truss.getNodeModel().get(i + 9), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        }

        //diagonal bars
        for (int i = 0; i < 9; i++) {
            truss.getBarModel().add(new Bar(barIndex++, truss.getNodeModel().get(i), truss.getNodeModel().get(i + 10), truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT,  SectionLibrary.SECTIONS.get(sectionIndex)));
        }



        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(18), Support.FIXED, Support.FIXED, Support.FIXED));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(9), Support.FIXED, Support.FIXED, Support.FIXED));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        int loadIndex = 1;
        for (int i = 0; i < 10; i++) {
            truss.getLoadModel().add(new Load(loadIndex++, truss.getNodeModel().get(i), 98.1, Load.VERTICAL_LOAD, Load.USER_DEFINED));
        }


    }


    /**
     * Generic method to create a trapezoid truss
     * @param L1 Bottom Chord length in cm
     * @param L2 Top Chord length in cm
     * @param H Truss depth in cm
     * @param fields Number of fields truss is split into
     * @param truss A TrussModel to add truss too
     * @return
     */
    public static TrussModel createTrapezoidTruss(double L1, double L2, double H, int fields, TrussModel truss) {

        //Truss truss = new Truss();
        double fieldSpacing = L2 / (fields - 2);
        double sideSpacing = (L1 - L2) / 2;
        double x = 0;
        double z = H;
        int initialNodeCount = truss.getNodeModel().size();

        //bottom row truss.getNodeModel()
        truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
        x = sideSpacing;
        while (x <= L1 - sideSpacing) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
            x += fieldSpacing;
        }
        truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x + sideSpacing - fieldSpacing, z));

        //top Row truss.getNodeModel()
        x = sideSpacing;
        z = 0;
        while (x <= L2 + sideSpacing) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
            x += fieldSpacing;
        }

        //bottom row bars
        for (int i = initialNodeCount; i < fields + initialNodeCount; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME)));
        }

        //top row bars
        for (int i = initialNodeCount + fields + 1; i < truss.getNodeModel().size() - 1; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME)));
        }

        //vertical bars
        for (int i = initialNodeCount; i < fields + initialNodeCount - 1; i++) {
            Node node1 = truss.getNodeModel().get(i + 1);
            Node node2 = truss.getNodeModel().get(i + 1 + fields);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }

        //primary diagonal bars
        double diagonalBarCount = 0;
        int nodeIndex = initialNodeCount;

        while (diagonalBarCount < fields / 2) {
            Node node1 = truss.getNodeModel().get(nodeIndex);
            Node node2 = truss.getNodeModel().get(nodeIndex + fields + 1);
            nodeIndex += 2;
            diagonalBarCount++;
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }


        //secondary diagonal bars
        diagonalBarCount = 0;
        nodeIndex = initialNodeCount + fields;
        while (diagonalBarCount < fields / 2) {
            Node node1 = truss.getNodeModel().get(nodeIndex);
            Node node2 = truss.getNodeModel().get(nodeIndex + fields - 1);
            nodeIndex -= 2;
            diagonalBarCount++;
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }

        //if number of fields is odd put cross in middle
        nodeIndex = initialNodeCount + (int) fields / 2;
        if (fields % 2 != 0) {
            //System.out.println("NodeIndex - " + nodeIndex);
            Node node1 = truss.getNodeModel().get(nodeIndex);
            Node node2 = truss.getNodeModel().get(nodeIndex + 1 + fields);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
            nodeIndex++;
            node1 = truss.getNodeModel().get(nodeIndex);
            node2 = truss.getNodeModel().get(nodeIndex + fields - 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }


        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(0), Support.FIXED, Support.FIXED, Support.FREE));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(fields), Support.FREE, Support.FIXED, Support.FREE));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return truss;
    }

    /**
     * Generic method to create a pratt truss
     * @param L1 Bottom Chord length in cm
     * @param L2 Top Chord length in cm
     * @param H Truss depth in cm
     * @param fields Number of fields truss is split into
     * @param truss A TrussModel to add truss too
     * @return
     */
    public static TrussModel createPrattTruss(double L1, double L2, double H, int fields, TrussModel truss) {
        //Truss truss = new Truss();
        double fieldSpacing = L2 / (fields - 2);
        double sideSpacing = (L1 - L2) / 2;
        double x = 0;
        double z = H;
        int initialNodeCount = truss.getNodeModel().size();

        //bottom row truss.getNodeModel()
        truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
        x = sideSpacing;
        while (x <= L1 - sideSpacing) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
            x += fieldSpacing;
        }
        truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x + sideSpacing - fieldSpacing, z));

        //top Row nodes
        x = sideSpacing;
        z = 0;
        while (x <= L2 + sideSpacing) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
            x += fieldSpacing;
        }

        //bottom row  bars
        for (int i = initialNodeCount; i < fields + initialNodeCount; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME)));
        }

        //top row  bars
        for (int i = initialNodeCount + fields + 1; i < truss.getNodeModel().size() - 1; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME)));
        }

        //vertical bars
        for (int i = initialNodeCount; i < fields + initialNodeCount - 1; i++) {
            Node node1 = truss.getNodeModel().get(i + 1);
            Node node2 = truss.getNodeModel().get(i + 1 + fields);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }

        //drawing end diagonal bars
        double diagonalBarCount = 0;
        int nodeIndex = initialNodeCount;
        Node node1 = truss.getNodeModel().get(nodeIndex);
        Node node2 = truss.getNodeModel().get(nodeIndex + fields + 1);
        truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        nodeIndex = initialNodeCount + fields;
        node1 = truss.getNodeModel().get(initialNodeCount + fields);
        node2 = truss.getNodeModel().get(nodeIndex + fields - 1);
        truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));


        //primary diagonal bars
        diagonalBarCount = 0;
        nodeIndex = initialNodeCount + fields - 2;
        while (diagonalBarCount < fields / 2 - 1) {
            node1 = truss.getNodeModel().get(nodeIndex);
            node2 = truss.getNodeModel().get(nodeIndex + fields + 1);
            nodeIndex--;
            diagonalBarCount++;
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }

        //secondary diagonal truss.getBarModel()
        diagonalBarCount = 0;
        nodeIndex = initialNodeCount + fields + 1;
        while (diagonalBarCount < fields / 2 - 1) {
            node1 = truss.getNodeModel().get(nodeIndex);
            node2 = truss.getNodeModel().get(nodeIndex - fields + 1);
            nodeIndex++;
            diagonalBarCount++;
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }

        //if number of fields is odd put cross in middle
        nodeIndex = initialNodeCount + (int) fields / 2;
        if (fields % 2 != 0) {
            //System.out.println("NodeIndex - " + nodeIndex);
            node1 = truss.getNodeModel().get(nodeIndex);
            node2 = truss.getNodeModel().get(nodeIndex + 1 + fields);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
            nodeIndex++;
            node1 = truss.getNodeModel().get(nodeIndex);
            node2 = truss.getNodeModel().get(nodeIndex + fields - 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }


        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(0), Support.FIXED, Support.FIXED, Support.FREE));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(fields), Support.FREE, Support.FIXED, Support.FREE));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return truss;
    }


    /**
     * Generic method to create a Warren truss
     * @param L1 Bottom Chord length in cm
     * @param L2 Top Chord length in cm
     * @param H Truss depth in cm
     * @param fields Number of fields truss is split into
     * @param truss A TrussModel to add truss too
     * @return
     */
    public static TrussModel createWarrenTruss(double L1, double L2, double H, int fields, TrussModel truss) {
        //Truss truss = new Truss();
        double topSpacing = topSpacing = L2 / (fields - 1);
        double bottomSpacing = L1 / fields;
        int initialNodeCount = truss.getNodeModel().size();
        double x = 0;
        double z = 0;

        //create bottom truss.getNodeModel()
        z = H;
        x = 0;
        while (x <= L1) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
            x += bottomSpacing;
        }

        //create top truss.getNodeModel()
        z = 0;
        x = (L1 - L2) / 2;
        while (x <= L2 + (L1 - L2) / 2) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1, x, z));
            x += topSpacing;
        }


        //top row truss.getBarModel()
        for (int i = initialNodeCount + fields + 1; i < truss.getNodeModel().size() - 1; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME)));
        }

        //bottom row truss.getBarModel()
        for (int i = initialNodeCount; i < fields + initialNodeCount; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME)));
        }

        //secondary diagonal truss.getBarModel()
        for (int i = initialNodeCount + 1; i < initialNodeCount + fields + 1; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + fields);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }

        //primary diagonal truss.getBarModel()
        for (int i = initialNodeCount; i < initialNodeCount + fields; i++) {
            Node node1 = truss.getNodeModel().get(i);
            Node node2 = truss.getNodeModel().get(i + fields + 1);
            truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1, node1, node2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
        }


        try {
            truss.getSupportModel().add(new Support(1, truss.getNodeModel().get(0), Support.FIXED, Support.FIXED, Support.FREE));
            truss.getSupportModel().add(new Support(2, truss.getNodeModel().get(fields), Support.FREE, Support.FIXED, Support.FREE));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return truss;

    }
}
