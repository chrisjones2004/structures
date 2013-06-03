package trussoptimizater.Gui;

import java.awt.Container;
import java.util.Observable;
import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.GUIModels.BarTableModel;
import trussoptimizater.Gui.GUIModels.GUIPanelModel;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.Elements.Support;
import trussoptimizater.Truss.TrussModel;

/**
 * This class uses encapsulation to resemble a JMenuBar.
 * @author Chris
 */
public class MyMenuBar implements java.util.Observer {

    private JMenuBar menuBar;
    private JMenuItem resultsOptionsMenuItem;
    private JMenuItem optimizeMenuItem;
    private TrussModel truss;
    private GUI gui;

    /*Toolbars*/
    private JCheckBoxMenuItem mainToolBarVisibleMenuItem;
    private JCheckBoxMenuItem elementToolBarVisibleMenuItem;
    private JCheckBoxMenuItem textInterfaceVisibleMenuItem;
    /*Side Panels*/
    private JCheckBoxMenuItem graphSidePanelVisibleMenuItem;
    private JCheckBoxMenuItem propertySidePanelVisibleMenuItem;
    private JCheckBoxMenuItem objectInspectorSidePanelMenuItem;
    //private ButtonGroup sidePanelGroup;
    private JCheckBoxMenuItem analyzeMethod1MenuItem;
    private JCheckBoxMenuItem analyzeMethod2MenuItem;

    public MyMenuBar(TrussModel truss, GUI gui) {
        this.truss = truss;
        this.gui = gui;
        gui.getGuiPanelModel().addObserver(this);
        truss.addObserver(this);
    }

    /**
     * This method initilizes the JMenuBar and adds all the JMenus
     */
    public void createMenu() {
        menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createViewMenu());
        menuBar.add(createToolsMenu());
        menuBar.add(createOptionsMenu());
        //menuBar.add(createTrussBenchMarkMenu());
        menuBar.add(createDeveloperMenu());
        menuBar.add(createHelpMenu());
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "File" JMenu
     */
    private JMenu createFileMenu() {

        /*File Menu*/

        JMenuItem newMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.NEW_ACTION_KEY));
        JMenuItem openMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.OPEN_ACTION_KEY));
        JMenuItem saveMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.SAVE_ACTION_KEY));
        JMenuItem saveAsMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.SAVE_AS_ACTION_KEY));
        JMenuItem exitMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.EXIT_ACTION_KEY));

        gui.getStatusToolBar().getHintManager().addHintFor(newMenuItem, newMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(saveAsMenuItem, saveAsMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(openMenuItem, openMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(saveMenuItem, saveMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(saveAsMenuItem, saveAsMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(exitMenuItem, exitMenuItem.getToolTipText());



        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "Edit" JMenu
     */
    private JMenu createEditMenu() {

        
        JMenuItem undoMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.UNDO_ACTION_KEY));
        JMenuItem redoMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.REDO_ACTION_KEY));
        JMenuItem cutMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.CUT_ACTION_KEY));
        JMenuItem copyMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.COPY_ACTION_KEY));
        JMenuItem pasteMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.PASTE_ACTION_KEY));
        JMenuItem rotateMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ROTATE_ACTION_KEY));
        JMenuItem scaleMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.SCALE_ACTION_KEY));
        JMenuItem selectAllMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ALL_KEY_BINDING_KEY));


        gui.getStatusToolBar().getHintManager().addHintFor(undoMenuItem, undoMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(redoMenuItem, redoMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(cutMenuItem, cutMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(copyMenuItem, copyMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(pasteMenuItem, pasteMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(rotateMenuItem, rotateMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(scaleMenuItem, scaleMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(selectAllMenuItem, selectAllMenuItem.getToolTipText());

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.addSeparator();

        editMenu.add(rotateMenuItem);
        editMenu.add(scaleMenuItem);
        editMenu.addSeparator();

        editMenu.add(selectAllMenuItem);

        return editMenu;
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "View" JMenu
     */
    private JMenu createViewMenu() {
        /*ToolBars*/

        mainToolBarVisibleMenuItem = new JCheckBoxMenuItem("Main");
        mainToolBarVisibleMenuItem.setSelected(gui.getGuiPanelModel().isMainToolBarVisible());
        mainToolBarVisibleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
                gui.getGuiPanelModel().setMainToolBarVisible(jcb.isSelected());
            }
        });


        elementToolBarVisibleMenuItem = new JCheckBoxMenuItem("Element");
        elementToolBarVisibleMenuItem.setSelected(gui.getGuiPanelModel().isElementToolBarVisible());
        elementToolBarVisibleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
                gui.getGuiPanelModel().setElementToolBarVisible(jcb.isSelected());
            }
        });

        textInterfaceVisibleMenuItem = new JCheckBoxMenuItem("Text Interface");
        textInterfaceVisibleMenuItem.setSelected(gui.getGuiPanelModel().isCLIPanelVisible());
        textInterfaceVisibleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
                gui.getGuiPanelModel().setCLIPanelVisible(jcb.isSelected());
            }
        });

        /*Side Bars*/

        propertySidePanelVisibleMenuItem = new JCheckBoxMenuItem("Properties");
        propertySidePanelVisibleMenuItem.setSelected(gui.getGuiPanelModel().isPropertyPanelVisible());
        //propertySidePanelVisibleMenuItem.setEnabled(false);
        propertySidePanelVisibleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
                if (jcb.isSelected()) {
                    gui.getGuiPanelModel().setSidePanel(GUIPanelModel.PROPERTY_PANEL_VISIBLE);
                    graphSidePanelVisibleMenuItem.setSelected(false);
                    objectInspectorSidePanelMenuItem.setSelected(false);
                } else {
                    gui.getGuiPanelModel().setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
                    //sidePanelGroup.clearSelection();
                }

            }
        });

        graphSidePanelVisibleMenuItem = new JCheckBoxMenuItem("Beam Graphs");
        graphSidePanelVisibleMenuItem.setSelected(false);
        graphSidePanelVisibleMenuItem.setEnabled(false);
        graphSidePanelVisibleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
                if (jcb.isSelected()) {
                    gui.getGuiPanelModel().setSidePanel(GUIPanelModel.GRAPH_PANEL_VISIBLE);
                    objectInspectorSidePanelMenuItem.setSelected(false);
                    propertySidePanelVisibleMenuItem.setSelected(false);
                } else {
                    gui.getGuiPanelModel().setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
                    //sidePanelGroup.clearSelection();
                }
            }
        });

        objectInspectorSidePanelMenuItem = new JCheckBoxMenuItem("Object Inspector");
        objectInspectorSidePanelMenuItem.setSelected(gui.getGuiPanelModel().isObjectInspectorVisible());
        objectInspectorSidePanelMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
                if (jcb.isSelected()) {
                    gui.getGuiPanelModel().setSidePanel(GUIPanelModel.OBJECT_INSEPECTOR_PANEL_VISIBLE);
                    graphSidePanelVisibleMenuItem.setSelected(false);
                    propertySidePanelVisibleMenuItem.setSelected(false);
                } else {
                    //sidePanelGroup.clearSelection();
                    gui.getGuiPanelModel().setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
                }
            }
        });



        JMenuItem zoominModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_IN_ACTION_KEY));
        JMenuItem zoomoutModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_OUT_ACTION_KEY));
        JMenuItem zoomallModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY));
        JMenuItem viewOptionsMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.VIEW_OPTIONS_ACTION_KEY));

        resultsOptionsMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.RESULTS_OPTIONS_ACTION_KEY));
        resultsOptionsMenuItem.setEnabled(false);

        JMenu toolbarsMenu = new JMenu("ToolBars");
        toolbarsMenu.add(mainToolBarVisibleMenuItem);
        toolbarsMenu.add(elementToolBarVisibleMenuItem);
        toolbarsMenu.add(textInterfaceVisibleMenuItem);



        JMenu sidePanelsMenu = new JMenu("Side Panels");
        sidePanelsMenu.add(propertySidePanelVisibleMenuItem);
        sidePanelsMenu.add(objectInspectorSidePanelMenuItem);
        sidePanelsMenu.add(graphSidePanelVisibleMenuItem);



        gui.getStatusToolBar().getHintManager().addHintFor(zoominModeMenuItem, zoominModeMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(zoomoutModeMenuItem, zoomoutModeMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(zoomallModeMenuItem, zoomallModeMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(viewOptionsMenuItem, viewOptionsMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(resultsOptionsMenuItem, resultsOptionsMenuItem.getToolTipText());

        JMenu viewMenu = new JMenu("View");
        viewMenu.add(toolbarsMenu);
        viewMenu.add(sidePanelsMenu);
        viewMenu.addSeparator();
        viewMenu.add(zoominModeMenuItem);
        viewMenu.add(zoomoutModeMenuItem);
        viewMenu.add(zoomallModeMenuItem);
        viewMenu.addSeparator();
        viewMenu.add(viewOptionsMenuItem);
        viewMenu.add(resultsOptionsMenuItem);
        return viewMenu;
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "Options" JMenu
     */
    private JMenu createOptionsMenu() {

        analyzeMethod1MenuItem = new JCheckBoxMenuItem("Stiffness Method - Frame");
        analyzeMethod1MenuItem.setSelected(truss.getAnalysisMethods().getAnalysisMethod() == AnalysisMethods.FRAME_STIFFNESS_METHOD);
        analyzeMethod1MenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem cb = (JCheckBoxMenuItem) e.getSource();
                if (cb.isSelected()) {
                    truss.getAnalysisMethods().setAnalysisMethod(AnalysisMethods.FRAME_STIFFNESS_METHOD);
                    gui.getBarTable().getBarTableModel().getEditableColumns()[BarTableModel.RESTRAINTS_COLUMN_INDEX] = true;
                }

            }
        });

        analyzeMethod2MenuItem = new JCheckBoxMenuItem("Stiffness Method - Pin-Jointed");
        analyzeMethod2MenuItem.setSelected(truss.getAnalysisMethods().getAnalysisMethod() == AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD);
        analyzeMethod2MenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem cb = (JCheckBoxMenuItem) e.getSource();
                if (cb.isSelected()) {
                    for (int i = 0; i < truss.getBarModel().size(); i++) {
                        if (truss.getBarModel().get(i).getRestraint().equals(Bar.FIXED_FIXED_RESTRAINT)) {
                            Object[] options = {"Proceed", "Cancel"};
                            int n = JOptionPane.showOptionDialog(gui.getFrame(), "Certain Bars are Fixed, if you choose to proceed these will be converted to Pinned?", "Alert", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                            if (n == 0) {
                                break;
                            } else {
                                analyzeMethod1MenuItem.setSelected(true);
                                return;
                            }
                        }
                    }

                    truss.getAnalysisMethods().setAnalysisMethod(AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD);
                    for (int j = 0; j < truss.getBarModel().size(); j++) {
                        truss.getBarModel().get(j).setRestraint(Bar.PINNED_PINNED_RESTRAINT);
                    }
                    gui.getBarTable().getBarTableModel().getEditableColumns()[BarTableModel.RESTRAINTS_COLUMN_INDEX] = false;
                }
            }
        });


        JCheckBoxMenuItem optimizeMethod1MenuItem = new JCheckBoxMenuItem("GA Method");
        optimizeMethod1MenuItem.setSelected(true);
        optimizeMethod1MenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //do nothing as GA method is currently the only optimization method
            }
        });

        JMenuItem optimizeOptionsMenuItem = new JMenuItem("Optimize Options");
        optimizeOptionsMenuItem.setSelected(true);
        optimizeOptionsMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gui.getOptimizeOptionsDialog().getDialog().setLocationRelativeTo(gui.getFrame());
                gui.getOptimizeOptionsDialog().getDialog().setVisible(true);
            }
        });

        JCheckBoxMenuItem includeSelfWeightMenuItem = new JCheckBoxMenuItem("Include Self-Weight");
        includeSelfWeightMenuItem.setSelected(truss.getAnalysisMethods().getAnalyzer().getAnalysisModel().isSelfWeightIncluded());
        includeSelfWeightMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem cb = (JCheckBoxMenuItem) e.getSource();
                truss.getAnalysisMethods().getAnalyzer().getAnalysisModel().setIncludeSelfWeight(cb.isSelected());
            }
        });

        JMenuItem materialsMenuItem = new JMenuItem("Material Options");
        materialsMenuItem.setSelected(truss.getAnalysisMethods().getAnalyzer().getAnalysisModel().isSelfWeightIncluded());
        materialsMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gui.getMaterialDialog().getDialog().setLocationRelativeTo(gui.getFrame());
                gui.getMaterialDialog().getDialog().setVisible(true);
            }
        });

        //Optimize Methods
        JMenu optimizeMethodsMenu = new JMenu("Optimize Method");
        optimizeMethodsMenu.add(optimizeMethod1MenuItem);
        ButtonGroup optimizationMethods = new ButtonGroup();
        optimizationMethods.add(optimizeMethod1MenuItem);


        //Analysis Methods
        JMenu analzeMethodsMenu = new JMenu("Analsis Method");
        analzeMethodsMenu.add(analyzeMethod1MenuItem);
        analzeMethodsMenu.add(analyzeMethod2MenuItem);
        ButtonGroup analysisMethods = new ButtonGroup();
        analysisMethods.add(analyzeMethod1MenuItem);
        analysisMethods.add(analyzeMethod2MenuItem);

        //Analysis Options
        JMenu analzeOptionsMenu = new JMenu("Analsis Options");
        analzeOptionsMenu.add(includeSelfWeightMenuItem);


        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.add(analzeMethodsMenu);
        optionsMenu.add(analzeOptionsMenu);
        optionsMenu.addSeparator();
        optionsMenu.add(optimizeMethodsMenu);
        optionsMenu.add(optimizeOptionsMenuItem);
        optionsMenu.addSeparator();
        optionsMenu.add(materialsMenuItem);
        return optionsMenu;
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "Tools" JMenu
     */
    private JMenu createToolsMenu() {

        JMenuItem analyzeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ANALYZE_ACTION_KEY));
        optimizeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.OPTIMIZE_ACTION_KEY));

        JMenuItem toExcellMenuItem = new JMenuItem("Save to Excell File");
        toExcellMenuItem.setToolTipText("Copy all tabulated data to an Excel file named after"
                + " your project and located in the same folder as this jar.");
        toExcellMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new ExcellWriter(gui).writeDataToExcell();
            }
        });

        gui.getStatusToolBar().getHintManager().addHintFor(analyzeMenuItem, analyzeMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(optimizeMenuItem, optimizeMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(toExcellMenuItem, toExcellMenuItem.getToolTipText());

        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.add(analyzeMenuItem);
        toolsMenu.add(optimizeMenuItem);
        toolsMenu.addSeparator();
        toolsMenu.add(toExcellMenuItem);
        return toolsMenu;

    }

    public JMenu createTrussesMenu() {

        JMenuItem rollerPrattTrussMenuItem = new JMenuItem("45 bar roller Pratt-Truss");
        rollerPrattTrussMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (execute()) {
                    truss.resetArrayLists();
                    gui.getUndoManager().setCompoundUnadoableEdit(true);
                    TrussFactory.create45BarPrattTruss(truss);
                    gui.getUndoManager().setCompoundUnadoableEdit(false);
                    MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY).actionPerformed(null);
                }
            }
        });


        JMenuItem singlePLCantileverMenuItem = new JMenuItem("15 bar single PL Cantilever");
        singlePLCantileverMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (execute()) {
                    truss.resetArrayLists();
                    gui.getUndoManager().setCompoundUnadoableEdit(true);
                    TrussFactory.create15BarCantilever(truss);
                    gui.getUndoManager().setCompoundUnadoableEdit(false);
                    MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY).actionPerformed(null);
                }
            }
        });

        JMenuItem multiplePLCantileverMenuItem = new JMenuItem("35 bar multiple PL Cantilever");
        multiplePLCantileverMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (execute()) {
                    truss.resetArrayLists();
                    gui.getUndoManager().setCompoundUnadoableEdit(true);
                    TrussFactory.create35BarCantilever(truss);
                    gui.getUndoManager().setCompoundUnadoableEdit(false);
                    MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY).actionPerformed(null);
                }
            }
        });

        //change createDeterminateTrussMenuItem names
        JMenuItem warrenTrussMenuItem = new JMenuItem("7 bar Warren truss");
        warrenTrussMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (execute()) {
                    truss.resetArrayLists();
                    gui.getUndoManager().setCompoundUnadoableEdit(true);
                    TrussFactory.create7BarWarrenTruss(truss);
                    gui.getUndoManager().setCompoundUnadoableEdit(false);
                    MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY).actionPerformed(null);
                }
            }
        });

        //change createDeterminateTrussMenuItem names
        JMenuItem createIndeterminateTrussMenuItem = new JMenuItem("9 bar Pratt truss");
        createIndeterminateTrussMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (execute()) {
                    truss.resetArrayLists();
                    gui.getUndoManager().setCompoundUnadoableEdit(true);
                    TrussFactory.create9BarPrattTruss(truss);
                    gui.getUndoManager().setCompoundUnadoableEdit(false);
                    MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY).actionPerformed(null);
                }

            }
        });


        JMenu trussesMenu = new JMenu("Trusses");
        trussesMenu.setToolTipText("Trusses used by regression tool");
        gui.getStatusToolBar().getHintManager().addHintFor(trussesMenu, trussesMenu.getToolTipText());
        trussesMenu.add(rollerPrattTrussMenuItem);
        trussesMenu.add(singlePLCantileverMenuItem);
        trussesMenu.add(multiplePLCantileverMenuItem);
        trussesMenu.add(warrenTrussMenuItem);
        trussesMenu.add(createIndeterminateTrussMenuItem);

        return trussesMenu;
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "Developer" JMenu
     */
    private JMenu createDeveloperMenu() {


        JMenuItem fitnessSumaryMenuItem = new JMenuItem("Print Fitness Summary");
        fitnessSumaryMenuItem.setToolTipText("Displays the truss fitness and mass");
        fitnessSumaryMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                truss.getOptimizeMethods().getGAOptimizer().setVerbose(false);
                truss.getAnalysisMethods().getAnalyzer().fullAnalysis();
                double fitness = truss.getOptimizeMethods().getGAOptimizer().getFitness();
                System.out.println("\nFitness :" + fitness);
                System.out.println("Mass :" + truss.getMass() + " Kg");
                JOptionPane.showMessageDialog(gui.getFrame(), "Fitness :" + FitnessGraph.DF.format(fitness) + "\nMass :" + FitnessGraph.DF.format(truss.getMass()) + " Kg", "Fitness Summary", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem fitnessDetailedMenuItem = new JMenuItem("Print Itemized Fitness");
        fitnessDetailedMenuItem.setToolTipText("Prints to SDout, itemized fitness for nodes, bars, deflections etc.");
        fitnessDetailedMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("\n\nPrint itemized Fitmness");
                truss.getAnalysisMethods().getAnalyzer().fullAnalysis();
                truss.getOptimizeMethods().getGAOptimizer().setVerbose(true);
                double fitness = truss.getOptimizeMethods().getGAOptimizer().getFitness();
                System.out.println("\n\nFinal Fitness :" + fitness);
                System.out.println("Mass :" + truss.getMass() + " Kg");
                truss.getOptimizeMethods().getGAOptimizer().setVerbose(false);
            }
        });


        JMenuItem regressionTestMenuItem = new JMenuItem("Regression Test");
        regressionTestMenuItem.setToolTipText("Use this tool to verify analyzer is correct");
        regressionTestMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("\n\nRegression Testing Tool");
                gui.getRegressionToolDialog().showGui();
                gui.getRegressionToolDialog().getDialog().setLocationRelativeTo(gui.getFrame());
            }
        });



        gui.getStatusToolBar().getHintManager().addHintFor(fitnessSumaryMenuItem, fitnessSumaryMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(fitnessDetailedMenuItem, fitnessDetailedMenuItem.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(regressionTestMenuItem, regressionTestMenuItem.getToolTipText());


        JMenu devleoperMenu = new JMenu("Developer");
        
        
        devleoperMenu.add(fitnessSumaryMenuItem);
        devleoperMenu.add(fitnessDetailedMenuItem);
        devleoperMenu.addSeparator();
        devleoperMenu.add(regressionTestMenuItem);
        devleoperMenu.add(createTrussesMenu());


        //devleoperMenu.add(optimizeSectionMenuItem);
        return devleoperMenu;
    }

    /**
     * This method initilizes all JMenuItems and adds them to a
     * JMenu which is then returned.
     * @return the "Help" JMenu
     */
    private JMenu createHelpMenu() {

        JMenuItem aboutMenuItem = new JMenuItem("About...");
        //aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        aboutMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gui.getFrame(), "Truss Optimizer\nVersion 1.2.1\n\nChris Jones\nevyacmj@nottingham.ac.uk", "About...", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(aboutMenuItem);
        return helpMenu;
    }

    /**
     *
     * @return the menuBar object
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Checks whether there are existing elements within current project
     * @return true if there are any elements within current project
     */
    private boolean existingElements() {
        if (truss.getNodeModel().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * If there are exisitng elements this method display an option pane informing user if they still want to proceed
     * even though all exisitng elements will be deleted. If they click "Proceed" returns true
     * @return true if new truss should be created
     */
    private boolean execute() {
        if (existingElements()) {
            Object[] options = {"Proceed", "Cancel"};
            int n = JOptionPane.showOptionDialog(gui.getFrame(), "All current elements will be deleted, do you wish to proceed?", "Alert", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (n == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }


    }

    /**
     * This class observes the truss model and also the Gui panel model. When there are any changes this
     * method is called.
     *
     * <p>
     * The truss model is observed so that the results menuitem and graphs menuitem are only enabled
     * when the truss has been analysed.
     * </p>
     *
     * <p>
     * The gui panel model is observed so that the menuitems showing which toolbars and sidepanels
     * are visible are kept up to date
     * </p>
     * @param o
     * @param arg Optional argument, but is normally the object in which the change happened
     */
    public void update(Observable o, Object arg) {

        //If gui panel model change these items may change
        mainToolBarVisibleMenuItem.setSelected(gui.getGuiPanelModel().isMainToolBarVisible());
        elementToolBarVisibleMenuItem.setSelected(gui.getGuiPanelModel().isElementToolBarVisible());
        propertySidePanelVisibleMenuItem.setSelected(gui.getGuiPanelModel().isPropertyPanelVisible());
        textInterfaceVisibleMenuItem.setSelected(gui.getGuiPanelModel().isCLIPanelVisible());

        //If gui panel model change these items may change
        graphSidePanelVisibleMenuItem.setSelected(gui.getGuiPanelModel().isBarGraphModelVisible());
        objectInspectorSidePanelMenuItem.setSelected(gui.getGuiPanelModel().isObjectInspectorVisible());
        propertySidePanelVisibleMenuItem.setSelected(gui.getGuiPanelModel().isPropertyPanelVisible());

        //If truss model changes these might change
        resultsOptionsMenuItem.setEnabled(truss.isAnalysed());
        graphSidePanelVisibleMenuItem.setEnabled(truss.isAnalysed());

        analyzeMethod1MenuItem.setSelected(truss.getAnalysisMethods().getAnalysisMethod() == AnalysisMethods.FRAME_STIFFNESS_METHOD);
        analyzeMethod2MenuItem.setSelected(truss.getAnalysisMethods().getAnalysisMethod() == AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD);
    }
}
