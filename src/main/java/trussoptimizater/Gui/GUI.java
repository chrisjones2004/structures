package trussoptimizater.Gui;

import trussoptimizater.Gui.Properties.PropertyPanel;
import trussoptimizater.Truss.TrussModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.*;
import javax.swing.undo.UndoManager;
import trussoptimizater.Gui.Dialogs.*;
import trussoptimizater.Gui.Tables.*;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.GUIModels.GUIModeModel;
import trussoptimizater.Gui.GUIModels.GUIPanelModel;
import trussoptimizater.Gui.GUIModels.SectionComboBoxModel;
import trussoptimizater.Gui.GUIModels.ViewModel;

public class GUI extends WindowAdapter implements java.util.Observer {

    private JFrame frame;
    private MyUndoManager undoManager;
    private TrussModel truss;
    private View view;
    private ViewModel viewModel;

    /*Main Componenets for GUI*/
    private MyMenuBar myMenuBar;
    private JPanel panel;
    private JTabbedPane tabbedPane;
    //private MyGlassPane myGlassPane;
    private GraphPanel graphPanel;
    /*dialogs*/
    private SectionDialog sectionDialog;
    private LoadDialog loadDialog;
    private NodeDialog nodeDialog;
    private TypicalTrussDialog typicalTrussDialog;
    private SupportDialog supportDialog;
    private FileChooser fileChooserDialog;
    private ViewOptionsDialog viewOptionsDialog;
    private ResultsOptionsDialog resultsOptionsDialog;
    private OptimizeDialog optimizeDialog;
    private OptimzeOptionsDialog optimizeOptionsDialog;
    private RotateDialog rotateDialog;
    private ScaleDialog scaleDialog;
    private MaterialDialog materialDialog;
    private RegressionToolDialog regressionToolDialog;
    /*ToolBar*/
    private ElementToolBar elementToolBar;
    private MainToolBar mainToolBar;
    private PropertyPanel propertyPanel;
    private ObjectInspectorPanel objectInspectorPanel;
    private TextInterfaceToolBar textInterfaceToolBar;
    private StatusToolBar statusToolBar;
    /*Tables*/
    private NodeTable nodeTable;
    private BarTable barTable;
    private LoadTable loadTable;
    private SupportTable supportTable;

    /*Save and Close*/
    private String savePath = null;    //Analysis
    /*Tab indexes*/
    public static final int VIEW_TAB_INDEX = 0;
    public static final int NODES_TAB_INDEX = 1;
    public static final int BARS_TAB_INDEX = 2;
    public static final int SUPPORTS_TAB_INDEX = 3;
    public static final int LOADS_TAB_INDEX = 4;
    public static final int NODE_OUTPUT_TAB_INDEX = 5;
    public static final int FORCE_OUTPUT_TAB_INDEX = 6;
    public static final int STRESS_OUTPUT_TAB_INDEX = 7;
    public static final int SUPPORT_OUTPUT_TAB_INDEX = 8;
    //public static final int JOINTS_TAB_INDEX = 9;

    /*Models*/
    private GUIPanelModel guiPanelModel = new GUIPanelModel();
    private GUIModeModel guiModeModel = new GUIModeModel();
    //private SectionComboBoxModel sectionComboBoxModel;
    /**
     * Used so that output tables are only constructed when project is analyzed
     */
    private boolean initTables = false;

    public GUI(TrussModel truss) {

        try {
            //dust is good
            //UIManager.setLookAndFeel("org.jvnet.substance.api.skin.SubstanceGeminiLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Look and Feel Exception " + e);
        }


        this.truss = truss;

        this.truss.addObserver(this);
        this.guiModeModel.addObserver(this);
        this.guiPanelModel.addObserver(this);


        undoManager = new MyUndoManager(truss);

        //sectionComboBoxModel = new SectionComboBoxModel(truss.getSectionModel());
        //sectionComboBoxModel.addElement(truss.getSections().get(0).getName());

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Truss Optimization");
        new MyActionMap(this, truss);
        initDialogs();
        initTables();



        //initActionMap();
        createGui();
    }//end of constructor

    private void initDialogs() {
        fileChooserDialog = new FileChooser(this);
        viewModel = new ViewModel();
        viewOptionsDialog = new ViewOptionsDialog(viewModel, this.frame);
        viewOptionsDialog.createGui();
        resultsOptionsDialog = new ResultsOptionsDialog(this);
        resultsOptionsDialog.createGui();
        typicalTrussDialog = new TypicalTrussDialog(this);
        typicalTrussDialog.createGui();
        graphPanel = new GraphPanel(this, truss);


        //initilize section Property object
        sectionDialog = new SectionDialog(this, truss);
        sectionDialog.createGui();


        nodeDialog = new NodeDialog(this, truss);
        nodeDialog.createGui();
        loadDialog = new LoadDialog(this, truss);
        loadDialog.createGui();
        supportDialog = new SupportDialog(this, truss);
        supportDialog.createGui();
        optimizeOptionsDialog = new OptimzeOptionsDialog(this, truss);
        optimizeOptionsDialog.createGui();

        optimizeDialog = new OptimizeDialog(this, truss);
        optimizeDialog.createGui();

        rotateDialog = new RotateDialog(this, truss);
        rotateDialog.createGui();

        scaleDialog = new ScaleDialog(this, truss);
        scaleDialog.createGui();

        materialDialog = new MaterialDialog(truss.getMaterials(), this);
        materialDialog.createGui();

        regressionToolDialog = new RegressionToolDialog(this);
        regressionToolDialog.createGui();
    }

    private void initTables() {
        nodeTable = new NodeTable(truss.getNodeModel(), truss, this);
        barTable = new BarTable(truss.getBarModel(), truss, this);
        supportTable = new SupportTable(truss.getSupportModel(), truss, this);
        loadTable = new LoadTable(truss.getLoadModel(), truss, this);
    }

    private void createGui() {


        /*CREATE TOOLBAR*/
        statusToolBar = new StatusToolBar();
        statusToolBar.createGui();


        /*Create Menu*/
        myMenuBar = new MyMenuBar(truss, this);
        myMenuBar.createMenu();

        this.elementToolBar = new ElementToolBar(this);
        elementToolBar.createtoolBar();
        this.mainToolBar = new MainToolBar(this);
        mainToolBar.createMainToolBar();



        this.propertyPanel = new PropertyPanel(truss, this);
        propertyPanel.createGui();
        objectInspectorPanel = new ObjectInspectorPanel(truss, this);
        this.textInterfaceToolBar = new TextInterfaceToolBar(this, truss);
        textInterfaceToolBar.createGui();





        view = new View(this, viewModel);
        view.createGui();

        view.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DELETE"), MyActionMap.DELETE_KEY_BINDING_KEY);
        view.getActionMap().put(MyActionMap.DELETE_KEY_BINDING_KEY, MyActionMap.ACTION_MAP.get(MyActionMap.DELETE_KEY_BINDING_KEY));

        view.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('A', KeyEvent.CTRL_DOWN_MASK), MyActionMap.SELECT_ALL_KEY_BINDING_KEY);
        view.getActionMap().put(MyActionMap.SELECT_ALL_KEY_BINDING_KEY, MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ALL_KEY_BINDING_KEY));

        view.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), MyActionMap.ESCAPE_KEY_BINDING_KEY);
        view.getActionMap().put(MyActionMap.ESCAPE_KEY_BINDING_KEY, MyActionMap.ACTION_MAP.get(MyActionMap.ESCAPE_KEY_BINDING_KEY));


        /*CREATE TABBEDPANE*/
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(true);



        //Add tabs to tabbedPane
        tabbedPane.addTab("View", view);
        tabbedPane.addTab("Nodes", nodeTable.getPanel());
        tabbedPane.addTab("Bars", barTable.getPanel());
        tabbedPane.addTab("Supports", supportTable.getPanel());
        tabbedPane.addTab("Loads", loadTable.getPanel());
        tabbedPane.addTab("Deflcetions", null);
        tabbedPane.addTab("Forces", null);
        tabbedPane.addTab("Stress", null);
        tabbedPane.addTab("Reactions", null);
        //tabbedPane.addTab("Joints", null);
        enableOutputTabs(false);

        panel = new JPanel(new BorderLayout());
        updatePanelDisplay();


        //Initialize frame

        frame.addWindowListener(this);
        frame.setJMenuBar(myMenuBar.getMenuBar());
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(statusToolBar.getStatusToolBar(), BorderLayout.PAGE_END);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
        frame.toFront();
        frame.setLocationRelativeTo(null);
        statusToolBar.getHintManager().enableHints(frame);
    }

    private void enableOutputTabs(boolean enabled) {
        //tabbedPane.setEnabledAt(GUI.JOINTS_TAB_INDEX, enabled);
        tabbedPane.setEnabledAt(GUI.NODE_OUTPUT_TAB_INDEX, enabled);
        tabbedPane.setEnabledAt(GUI.FORCE_OUTPUT_TAB_INDEX, enabled);
        tabbedPane.setEnabledAt(GUI.STRESS_OUTPUT_TAB_INDEX, enabled);
        tabbedPane.setEnabledAt(GUI.SUPPORT_OUTPUT_TAB_INDEX, enabled);
    }

    public void updatePanelDisplay() {

        panel.removeAll();
        JSplitPane splitpane1 = null;
        JSplitPane splitpane2 = null;
        //JSplitPane splitpane3 = null;
        //System.out.println("\nGui "+getGuiPanelModel().isObjectInspectorVisible());
        //System.out.println("is side panel visible  "+guiPanelModel.isSidePanelVisible());
        if (guiPanelModel.isSidePanelVisible()) {
            //System.out.println("is property panel  "+guiPanelModel.isPropertyPanelVisible());
            if (guiPanelModel.isBarGraphModelVisible()) {
                splitpane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphPanel.getOptionPanel(), tabbedPane);
            } else if (guiPanelModel.isPropertyPanelVisible()) {
                splitpane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, propertyPanel.getOptionPanel(), tabbedPane);

            } else if (guiPanelModel.isObjectInspectorVisible()) {
                splitpane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, objectInspectorPanel.getOptionPanel(), tabbedPane);
            }
            splitpane1.setDividerLocation(200);

            if (guiPanelModel.isCLIPanelVisible()) {
                splitpane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitpane1, textInterfaceToolBar.getOptionPanel());
                splitpane2.setDividerLocation(panel.getHeight() - 200);
                splitpane2.setResizeWeight(1);
            } else {
                splitpane2 = splitpane1;
            }

        } else if (!guiPanelModel.isSidePanelVisible() && guiPanelModel.isCLIPanelVisible()) {
            splitpane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, textInterfaceToolBar.getOptionPanel());
            splitpane2.setDividerLocation(panel.getHeight() - 200);
            splitpane2.setResizeWeight(1);
        }

        if (splitpane2 == null) {
            panel.add(this.tabbedPane, BorderLayout.CENTER);
        } else {
            panel.add(splitpane2, BorderLayout.CENTER);
        }

        if (guiPanelModel.isElementToolBarVisible()) {
            panel.add(this.elementToolBar.getToolBar(), BorderLayout.EAST);
        }

        if (guiPanelModel.isMainToolBarVisible()) {
            panel.add(this.mainToolBar.getToolBar(), BorderLayout.NORTH);
        }

        panel.revalidate();
        panel.repaint();
    }

    public void updateMode() {
        switch (this.guiModeModel.getMode()) {
            case GUIModeModel.BAR_MODE:
                break;
            case GUIModeModel.LOAD_MODE:
                this.loadDialog.getDialog().setLocationRelativeTo(this.frame);
                this.loadDialog.getDialog().setVisible(true);
                guiModeModel.setMode(GUIModeModel.SELECT_MODE);
                break;
            case GUIModeModel.NODE_MODE:
                this.nodeDialog.getDialog().setLocationRelativeTo(this.frame);
                this.nodeDialog.getDialog().setVisible(true);
                this.nodeDialog.getDialog().setAlwaysOnTop(true);
                break;
            case GUIModeModel.SECTION_MODE:
                this.sectionDialog.getDialog().setLocationRelativeTo(this.frame);
                this.sectionDialog.getDialog().setVisible(true);
                guiModeModel.setMode(GUIModeModel.SELECT_MODE);
                break;
            case GUIModeModel.SELECT_MODE:
                break;
            case GUIModeModel.SUPPORT_MODE:
                this.supportDialog.getDialog().setLocationRelativeTo(this.frame);
                this.supportDialog.getDialog().setVisible(true);
                guiModeModel.setMode(GUIModeModel.SELECT_MODE);
                break;
            case GUIModeModel.TRUSS_MODE:
                this.typicalTrussDialog.getDialog().setLocationRelativeTo(this.frame);
                this.typicalTrussDialog.getDialog().setVisible(true);
        }
    }



    public void update(Observable o, Object arg) {


        if (arg instanceof GUIPanelModel) {
            updatePanelDisplay();
        }

        if (arg instanceof GUIModeModel) {
            updateMode();
        }

        if (arg instanceof TrussModel) {
            if (truss.isAnalysed() && !initTables ) {//&& truss.getOperation() == TrussModel.OPERATION_IDLE
                enableOutputTabs(true);

                this.tabbedPane.setComponentAt(GUI.NODE_OUTPUT_TAB_INDEX, new NodeOutputTable(truss, nodeTable.getTable().getSelectionModel()).getScrollTable());
                this.tabbedPane.setComponentAt(GUI.FORCE_OUTPUT_TAB_INDEX, new BarOutputTable(truss, this).getScrollTable());
                this.tabbedPane.setComponentAt(GUI.STRESS_OUTPUT_TAB_INDEX, new StressOutputTable(truss).getScrollTable());
                this.tabbedPane.setComponentAt(GUI.SUPPORT_OUTPUT_TAB_INDEX, new SupportOutputTable(truss, supportTable.getTable().getSelectionModel()).getScrollTable());
                //this.tabbedPane.setComponentAt(GUI.JOINTS_TAB_INDEX, new JointOutputTable(truss).getScrollTable());
                this.tabbedPane.revalidate();
                this.tabbedPane.repaint();
                initTables = true;

            } else if (!truss.isAnalysed() ) {//&& truss.getOperation() == TrussModel.OPERATION_IDLE
                initTables = false;
                enableOutputTabs(false);
                if (this.guiPanelModel.isBarGraphModelVisible()) {
                    guiPanelModel.setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
                }
            }
        }

        //updating drawing Panel
        view.setTrussModel(truss);

    }

    @Override
    public void windowClosing(WindowEvent e) {
        MyActionMap.ACTION_MAP.get(MyActionMap.EXIT_ACTION_KEY).actionPerformed(null);
    }


    /*GET AND SET METHODS*/
    public JFrame getFrame() {
        return frame;
    }

    public MyUndoManager getUndoManager() {
        return undoManager;
    }

    public View getView() {
        return this.view;
    }

    public LoadDialog getLoadDialog() {
        return loadDialog;
    }

    public ScaleDialog getScaleDialog() {
        return scaleDialog;
    }

    public RotateDialog getRotateDialog() {
        return rotateDialog;
    }

    public MaterialDialog getMaterialDialog() {
        return materialDialog;
    }

    public PropertyPanel getPropertyPanel() {
        return propertyPanel;
    }

    public FileChooser getFileChooser() {
        return fileChooserDialog;
    }

    public int getSelectedTabIndex() {
        return tabbedPane.getSelectedIndex();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public SupportDialog getSupportGui() {
        return supportDialog;
    }

    public ViewOptionsDialog getViewOptionsDialog() {
        return viewOptionsDialog;
    }

    public OptimizeDialog getOptimizeDialog() {
        return optimizeDialog;
    }

    public SectionDialog getSectionDialog() {
        return sectionDialog;
    }

    public NodeDialog getNodeDialog() {
        return nodeDialog;
    }

    public ResultsOptionsDialog getResultsOptionsDialog() {
        return resultsOptionsDialog;
    }

    public RegressionToolDialog getRegressionToolDialog() {
        return regressionToolDialog;
    }

    public NodeTable getNodeTable() {
        return nodeTable;
    }

    public BarTable getBarTable() {
        return barTable;
    }

    public SupportTable getSupportTable() {
        return supportTable;
    }

    public LoadTable getLoadTable() {
        return loadTable;
    }

    public TypicalTrussDialog getTypicalTrussDialog() {
        return typicalTrussDialog;
    }

    /*public SectionComboBoxModel getSectionComboBoxModel() {
        return sectionComboBoxModel;
    }*/

    /*public JPanel getMainGUIPanel() {
    return panel;
    }*/
    public OptimzeOptionsDialog getOptimizeOptionsDialog() {
        return optimizeOptionsDialog;
    }

    public void setSavePath(String p) {
        this.savePath = p;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public GUI getGui() {
        return this;
    }

    public TrussModel getTruss() {
        return truss;
    }

    public GUIModeModel getGuiModeModel() {
        return guiModeModel;
    }

    public GUIPanelModel getGuiPanelModel() {
        return guiPanelModel;
    }

    public StatusToolBar getStatusToolBar() {
        return statusToolBar;
    }

    /**
     * Used in the openAction class
     * @param sectionComboBoxModel
     */
    /*public void setSectionComboBoxModel(SectionComboBoxModel sectionComboBoxModel) {
        this.sectionComboBoxModel = sectionComboBoxModel;
    }*/
}//end of class

