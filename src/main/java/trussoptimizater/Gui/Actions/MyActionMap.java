package trussoptimizater.Gui.Actions;
import trussoptimizater.Truss.TrussModel;
import javax.swing.*;
import trussoptimizater.Gui.GUI;


public class MyActionMap {

    /*Action*/
    public static final ActionMap ACTION_MAP = new ActionMap();
    public static final String BAR_MODE_ACTION_KEY = "Bar Mode";
    public static final String NODE_MODE_ACTION_KEY = "Node Mode";
    public static final String LOAD_MODE_ACTION_KEY = "Load Mode";
    public static final String SUPPORT_MODE_ACTION_KEY = "Support Mode";
    public static final String SECTION_MODE_ACTION_KEY = "Section Mode";
    public static final String SELECT_MODE_ACTION_KEY = "Select Mode";
    public static final String TRUSS_MODE_ACTION_KEY = "Truss Mode";
    public static final String ESCAPE_KEY_BINDING_KEY = "ESCAPE";

    public static final String CUT_ACTION_KEY = "cut";
    public static final String COPY_ACTION_KEY = "copy";
    public static final String PASTE_ACTION_KEY = "paste";


    /*Note Lower case so that CLI can use these actions*/
    public static final String ANALYZE_ACTION_KEY = "analyze";
    public static final String OPTIMIZE_ACTION_KEY = "optimize";
    public static final String DELETE_KEY_BINDING_KEY = "delete key binding";
    public static final String DELETE_ACTION_KEY = "delete";
    public static final String NEW_ACTION_KEY = "new";
    public static final String OPEN_ACTION_KEY = "open";
    public static final String ROTATE_ACTION_KEY = "rotate";
    public static final String SAVE_ACTION_KEY = "save";
    public static final String SAVE_AS_ACTION_KEY = "save as";
    public static final String EXIT_ACTION_KEY = "exit";
    public static final String SCALE_ACTION_KEY = "scale";
    public static final String SELECT_ACTION_KEY = "select";
    public static final String SELECT_ALL_KEY_BINDING_KEY = "Select All";
    public static final String VIEW_OPTIONS_ACTION_KEY = "View Options";
    public static final String RESULTS_OPTIONS_ACTION_KEY = "Results Options";
    public static final String ZOOM_ALL_ACTION_KEY = "zoom all";
    public static final String ZOOM_IN_ACTION_KEY = "zoom in";
    public static final String ZOOM_OUT_ACTION_KEY = "zoom out";
    public static final String UNDO_ACTION_KEY = "undo";
    public static final String REDO_ACTION_KEY = "redo";
    public static final String HELP_ACTION_KEY = "Help";
    
    public MyActionMap(GUI gui, TrussModel truss){
        init(gui, truss);
    }

    public void init(GUI gui, TrussModel truss) {


        DeleteSelectedKeyBinding deleteKeyBinding = new DeleteSelectedKeyBinding(MyActionMap.DELETE_KEY_BINDING_KEY,new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/Delete24.gif")), "Delete Selected Elements");
        SelectAllKeyBinding selectAllKeyBinding = new SelectAllKeyBinding(MyActionMap.SELECT_ALL_KEY_BINDING_KEY, "Select All", truss);
        EscapeKeyBinding escapeKeyBinding = new EscapeKeyBinding(gui, MyActionMap.ESCAPE_KEY_BINDING_KEY, "Escape drawing bar");
        

        NewAction newAction = new NewAction("New", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/New24.gif")), "New Project (Ctrl+N)", gui, truss);
        OpenAction openAction = new OpenAction("Open", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/Open24.gif")), "Open Project (Ctrl+O)", gui, truss);
        SaveAction saveAction = new SaveAction("Save", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/Save24.gif")), "Save Project (Ctrl+S)", gui, truss);
        SaveAsAction saveAsAction = new SaveAsAction("Save As", new ImageIcon(getClass().getResource("/Pictures/Menu Items/SaveAs24.gif")), "Save Project As..", gui, truss);
        RotateAction rotateAction = new RotateAction("Rotate", new ImageIcon(getClass().getResource("/Pictures/Menu Items/Rotate24.gif")), "Rotate Elements (Ctrl+R)", gui, truss);
        ScaleAction scaleAction = new ScaleAction("Scale", new ImageIcon(getClass().getResource("/Pictures/Menu Items/Scale24.gif")), "Scale Elements (Ctrl+E)", gui, truss);
        SelectAction selectAction = new SelectAction("Select", "Select gui parts", gui, truss);

        ExitAction exitAction = new ExitAction("Exit", "Exit Application (Ctrl+E)", gui);

        ZoomInAction zoomInAction = new ZoomInAction("Zoom In", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/ZoomIn24.gif")), "Zoom In", gui);
        ZoomOutAction zoomOutAction = new ZoomOutAction("Zoom Out", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/ZoomOut24.gif")), "Zoom Out",gui);
        ZoomAllAction zoomAllAction = new ZoomAllAction("Zoom All", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/AlignCenter24.gif")), "Zoom All", gui, truss);
        ViewOptionsAction viewOptionsAction = new ViewOptionsAction(MyActionMap.VIEW_OPTIONS_ACTION_KEY, "View Display Options", gui, truss);
        ResultOptionsAction resultsOptionsAction = new ResultOptionsAction(MyActionMap.RESULTS_OPTIONS_ACTION_KEY, "View Results Display Options", gui, truss);

        CutAction cutAction = new CutAction("Cut", new ImageIcon(getClass().getResource("/Pictures/Menu Items/Cut24.gif")), "Cut (Ctrl+X)", gui, truss);
        CopyAction copyAction = new CopyAction("Copy", new ImageIcon(getClass().getResource("/Pictures/Menu Items/Copy24.gif")), "Copy (Ctrl+C)", gui, truss);
        PasteAction pasteAction = new PasteAction("Paste", new ImageIcon(getClass().getResource("/Pictures/Menu Items/Paste24.gif")), "Paste (Ctrl+V)", gui, truss);

        AnalyzeAction analyzeAction = new AnalyzeAction("Analyze", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/Play24.gif")), "Analayze (F6)", truss);
        OptimizeAction optimizeAction = new OptimizeAction("Optimize", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/StepForward24.gif")), "Optimize (F7)", gui);

        SelectModeAction selectModeAction = new SelectModeAction(MyActionMap.SELECT_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/sellectButton.GIF")), "Select Mode", gui.getGuiModeModel());
        NodeModeAction nodeModeAction = new NodeModeAction(MyActionMap.NODE_MODE_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/nodeButton.GIF")), "Node Mode", gui.getGuiModeModel());
        BarModeAction barModeAction = new BarModeAction(MyActionMap.BAR_MODE_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/barButton.GIF")), "Bar Mode", gui.getGuiModeModel());
        SectionModeAction sectionModeAction = new SectionModeAction(MyActionMap.SECTION_MODE_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/sectionButton.GIF")), "Section Mode", gui.getGuiModeModel());
        SupportModeAction supportModeAction = new SupportModeAction(MyActionMap.SUPPORT_MODE_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/supportButton.GIF")), "Support Mode", gui.getGuiModeModel());
        LoadModeAction loadModeAction = new LoadModeAction(MyActionMap.LOAD_MODE_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/loadButton.GIF")), "Load Mode", gui.getGuiModeModel());
        TrussModeAction trussModeAction = new TrussModeAction(MyActionMap.TRUSS_MODE_ACTION_KEY, new ImageIcon(getClass().getResource("/Pictures/Element Toolbar/trussButton.GIF")), "Truss Mode", gui.getGuiModeModel());

        UndoAction undoAction = new UndoAction("Undo", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/Undo24.gif")), "Undo (Ctrl+Z)", gui.getUndoManager());
        RedoAction redoAction = new RedoAction("Redo", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/Redo24.gif")), "Redo (Ctrl+Y)", gui.getUndoManager());
        HelpAction helpAction = new HelpAction("Help", new ImageIcon(getClass().getResource("/Pictures/Main Toolbar/Help24.gif")), "Help", truss);
        DeleteAction deleteAction = new DeleteAction(MyActionMap.DELETE_ACTION_KEY,"Used for deleting in CLI", truss, gui);


        ACTION_MAP.put(MyActionMap.DELETE_ACTION_KEY, deleteAction);
        ACTION_MAP.put(MyActionMap.DELETE_KEY_BINDING_KEY, deleteKeyBinding);
        ACTION_MAP.put(MyActionMap.SELECT_ALL_KEY_BINDING_KEY, selectAllKeyBinding);
        ACTION_MAP.put(MyActionMap.ESCAPE_KEY_BINDING_KEY, escapeKeyBinding);

        ACTION_MAP.put(MyActionMap.NEW_ACTION_KEY, newAction);
        ACTION_MAP.put(MyActionMap.OPEN_ACTION_KEY, openAction);
        ACTION_MAP.put(MyActionMap.SAVE_ACTION_KEY, saveAction);
        ACTION_MAP.put(MyActionMap.SAVE_AS_ACTION_KEY, saveAsAction);
        ACTION_MAP.put(MyActionMap.EXIT_ACTION_KEY, exitAction);

        ACTION_MAP.put(MyActionMap.CUT_ACTION_KEY, cutAction);
        ACTION_MAP.put(MyActionMap.COPY_ACTION_KEY, copyAction);
        ACTION_MAP.put(MyActionMap.PASTE_ACTION_KEY, pasteAction);

        ACTION_MAP.put(MyActionMap.ROTATE_ACTION_KEY, rotateAction);
        ACTION_MAP.put(MyActionMap.SCALE_ACTION_KEY, scaleAction);
        ACTION_MAP.put(MyActionMap.SELECT_ACTION_KEY, selectAction);
        ACTION_MAP.put(MyActionMap.ZOOM_IN_ACTION_KEY, zoomInAction);
        ACTION_MAP.put(MyActionMap.ZOOM_OUT_ACTION_KEY, zoomOutAction);
        ACTION_MAP.put(MyActionMap.SELECT_MODE_ACTION_KEY, selectModeAction);
        ACTION_MAP.put(MyActionMap.ZOOM_ALL_ACTION_KEY, zoomAllAction);

        ACTION_MAP.put(MyActionMap.VIEW_OPTIONS_ACTION_KEY, viewOptionsAction);
        ACTION_MAP.put(MyActionMap.RESULTS_OPTIONS_ACTION_KEY, resultsOptionsAction);
        ACTION_MAP.put(MyActionMap.ANALYZE_ACTION_KEY, analyzeAction);
        ACTION_MAP.put(MyActionMap.OPTIMIZE_ACTION_KEY, optimizeAction);


        ACTION_MAP.put(MyActionMap.SELECT_MODE_ACTION_KEY, selectModeAction);
        ACTION_MAP.put(MyActionMap.NODE_MODE_ACTION_KEY, nodeModeAction);
        ACTION_MAP.put(MyActionMap.BAR_MODE_ACTION_KEY, barModeAction);
        ACTION_MAP.put(MyActionMap.SUPPORT_MODE_ACTION_KEY, supportModeAction);
        ACTION_MAP.put(MyActionMap.SECTION_MODE_ACTION_KEY, sectionModeAction);
        ACTION_MAP.put(MyActionMap.LOAD_MODE_ACTION_KEY, loadModeAction);
        ACTION_MAP.put(MyActionMap.TRUSS_MODE_ACTION_KEY, trussModeAction);

        ACTION_MAP.put(MyActionMap.UNDO_ACTION_KEY, undoAction);
        ACTION_MAP.put(MyActionMap.REDO_ACTION_KEY, redoAction);
        ACTION_MAP.put(MyActionMap.HELP_ACTION_KEY, helpAction);

    }
}

