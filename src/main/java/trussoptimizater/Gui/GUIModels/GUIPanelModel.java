package trussoptimizater.Gui.GUIModels;

import java.util.prefs.Preferences;

public class GUIPanelModel extends java.util.Observable {
    /**
     * Prefereneces is used to store selected values
     */
    private Preferences prefs;
    public static final String CLI_TOOLBAR_VISIBLE_PREF_KEY = "CLI_TOOLBAR_VISIBLE_PREF_KEY";
    public static final String MAIN_TOOLBAR_VISIBLE_PREF_KEY = "MAIN_TOOLBAR_VISIBLE_PREF_KEY";
    public static final String ELEMENT_TOOLBAR_VISIBLE_PREF_KEY = "ELEMENT_TOOLBAR_VISIBLE_PREF_KEY";
    public static final String SIDE_PANEL_VISIBLE_PREF_KEY = "SIDE_PANEL_VISIBLE_PREF_KEY";
    /*ToolBar*/
    private boolean CLIToolBarVisible;
    private boolean mainToolBarVisible;
    private boolean elementToolBarVisible;

    /*Side Panels*/
    public final static int SIDE_PANEL_NOT_VISIBLE = 0;
    public final static int GRAPH_PANEL_VISIBLE = 1;
    public final static int PROPERTY_PANEL_VISIBLE = 2;
    public final static int OBJECT_INSEPECTOR_PANEL_VISIBLE = 3;
    private int sidePanel;

    public GUIPanelModel() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        CLIToolBarVisible = prefs.getBoolean(CLI_TOOLBAR_VISIBLE_PREF_KEY, false);
        mainToolBarVisible = prefs.getBoolean(MAIN_TOOLBAR_VISIBLE_PREF_KEY, true);
        elementToolBarVisible = prefs.getBoolean(ELEMENT_TOOLBAR_VISIBLE_PREF_KEY, true);
        sidePanel = prefs.getInt(SIDE_PANEL_VISIBLE_PREF_KEY, 0);
    }

    public boolean isMainToolBarVisible() {
        return mainToolBarVisible;
    }

    public boolean isElementToolBarVisible() {
        return elementToolBarVisible;
    }

    public boolean isCLIPanelVisible() {
        return CLIToolBarVisible;
    }

    public boolean isPropertyPanelVisible() {
        if (sidePanel == GUIPanelModel.PROPERTY_PANEL_VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBarGraphModelVisible() {
        if (sidePanel == GUIPanelModel.GRAPH_PANEL_VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isObjectInspectorVisible() {
        if (sidePanel == GUIPanelModel.OBJECT_INSEPECTOR_PANEL_VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSidePanelVisible() {
        if (sidePanel != GUIPanelModel.SIDE_PANEL_NOT_VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public void setSidePanel(int sidePanel) {
        this.sidePanel = sidePanel;
        prefs.put(SIDE_PANEL_VISIBLE_PREF_KEY, Integer.toString(sidePanel));
        setChanged();
        notifyObservers(this);
    }


    /*ToolBars*/
    public void setCLIPanelVisible(boolean CLIPanelVisible) {
        this.CLIToolBarVisible = CLIPanelVisible;
        prefs.put(CLI_TOOLBAR_VISIBLE_PREF_KEY, Boolean.toString(CLIPanelVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setElementToolBarVisible(boolean elementToolBarVisible) {
        this.elementToolBarVisible = elementToolBarVisible;
        prefs.put(ELEMENT_TOOLBAR_VISIBLE_PREF_KEY, Boolean.toString(elementToolBarVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setMainToolBarVisible(boolean mainToolBarVisible) {
        this.mainToolBarVisible = mainToolBarVisible;
        prefs.put(MAIN_TOOLBAR_VISIBLE_PREF_KEY, Boolean.toString(mainToolBarVisible));
        setChanged();
        notifyObservers(this);
    }
}
