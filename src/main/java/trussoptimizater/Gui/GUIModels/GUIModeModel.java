package trussoptimizater.Gui.GUIModels;



public class GUIModeModel extends java.util.Observable{

    /*Drawing Modes*/
    private int mode = 0;
    public static final int SELECT_MODE = 0;
    public static final int NODE_MODE = 1;
    public static final int BAR_MODE = 2;
    public static final int SECTION_MODE = 3;
    public static final int LOAD_MODE = 4;
    public static final int SUPPORT_MODE = 5;
    public static final int TRUSS_MODE = 6;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        setChanged();
        notifyObservers(this);
    }
}
