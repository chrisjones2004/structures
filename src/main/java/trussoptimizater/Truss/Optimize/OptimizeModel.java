package trussoptimizater.Truss.Optimize;

import java.util.Observable;
import java.util.prefs.Preferences;

/**
 * All optimization algorithm classes should extend this class. This class defines what and by how much should be
 * optimized.
 * @author Chris
 */
public abstract class OptimizeModel extends Observable {

    //Default options
    public static final boolean DEFAULT_KEEP_SYMMETRY = false;
    public static final boolean DEFAULT_OPTIMIZE_SECTIONS = true;
    public static final boolean DEFAULT_OPTIMIZE_SUPPORTED_NODES_X = false; //optimizeSupportedNodesX
    public static final boolean DEFAULT_OPTIMIZE_SUPPORTED_NODES_Y = false; //optimizeSupportedNodesY
    public static final boolean DEFAULT_OPTIMIZE_LOADED_NODES_X = false;//optimizeLoadedNodesX
    public static final boolean DEFAULT_OPTIMIZE_LOADED_NODES_Y = true; //optimizeLoadedNodesY
    public static final boolean DEFAULT_OPTIMIZE_FREE_NODES_X = false; //optimizeFreeNodesX
    public static final boolean DEFAULT_OPTIMIZE_FREE_NODES_Y = true;  //optimizeFreeNodesY
    public static final int DEFAULT_MAX_NODAL_X_DISPLACEMENT = 100;//cm  //maxNodalXDisplacement
    public static final int DEFAULT_MAX_NODAL_Y_DISPLACEMENT = 200;//cm  maxNodalYDisplacement
    public static final int DEFAULT_NODAL_GRID = 50;//cm   nodalGrid
    public static final double DEFAULT_MAX_DEFLCETION = 30;//mm   maxDeflection
    public static final int DEFAULT_MIN_SECTION_INDEX = 0;
    public static final int DEFAULT_MAX_SECTION_INDEX = 81;
    public static final double DEFAULT_MIN_NODE_SPACING = 0.20;//m   //minBarLength
    public static final double DEFAULT_MAX_BAR_LENGTH = 5;//m  //maxBarLength
    /**
     * Prefereneces is used to store selected values
     */
    private Preferences prefs;
    public static final String KEEP_SYMMETRY_PREF_KEY = "KEEP_SYMMETRY_PREF_KEY";
    public static final String OPTIMIZE_SECTIONS_PREF_KEY = "OPTIMIZE_SECTIONS_PREF_KEY";
    public static final String OPTIMIZE_SUPPORTED_NODES_X_PREF_KEY = "OPTIMIZE_SUPPORTED_NODES_X_PREF_KEY"; //optimizeSupportedNodesX
    public static final String OPTIMIZE_SUPPORTED_NODES_Y_PREF_KEY = "OPTIMIZE_SUPPORTED_NODES_Y_PREF_KEY"; //optimizeSupportedNodesY
    public static final String OPTIMIZE_LOADED_NODES_X_PREF_KEY = "OPTIMIZE_LOADED_NODES_X_PREF_KEY";//optimizeLoadedNodesX
    public static final String OPTIMIZE_LOADED_NODES_Y_PREF_KEY = "OPTIMIZE_LOADED_NODES_Y_PREF_KEY"; //optimizeLoadedNodesY
    public static final String OPTIMIZE_FREE_NODES_X_PREF_KEY = "OPTIMIZE_FREE_NODES_X_PREF_KEY"; //optimizeFreeNodesX
    public static final String OPTIMIZE_FREE_NODES_Y_PREF_KEY = "OPTIMIZE_FREE_NODES_Y_PREF_KEY";  //optimizeFreeNodesY
    public static final String MAX_NODAL_X_DISPLACEMENT_PREF_KEY = "MAX_NODAL_X_DISPLACEMENT_PREF_KEY";//cm  //maxNodalXDisplacement
    public static final String MAX_NODAL_Y_DISPLACEMENT_PREF_KEY = "MAX_NODAL_Y_DISPLACEMENT_PREF_KEY";//cm  maxNodalYDisplacement
    public static final String NODAL_GRID_PREF_KEY = "NODAL_GRID_PREF_KEY";//cm   nodalGrid
    public static final String MAX_DEFLCETION_PREF_KEY = "MAX_DEFLCETION_PREF_KEY";//mm   maxDeflection
    public static final String MIN_SECTION_INDEX_PREF_KEY = "MIN_SECTION_INDEX_PREF_KEY";
    public static final String MAX_SECTION_INDEX_PREF_KEY = "MAX_SECTION_INDEX_PREF_KEY";
    public static final String MIN_NODE_SPACING_PREF_KEY = "MIN_NODE_SPACING_PREF_KEY";//m   //minBarLength
    public static final String MAX_BAR_LENGTH_PREF_KEY = "MAX_BAR_LENGTH_PREF_KEY";//m  //maxBarLength
    public static final String SYMMETRY_X1_PREF_KEY = "SYMMETRY_X1_PREF_KEY";//m  //maxBarLength
    public static final String SYMMETRY_X2_PREF_KEY = "SYMMETRY_X2_PREF_KEY";//m  //maxBarLength
    public static final String SYMMETRY_Y1_PREF_KEY = "SYMMETRY_Y1_PREF_KEY";//m  //maxBarLength
    public static final String SYMMETRY_Y2_PREF_KEY = "SYMMETRY_Y2_PREF_KEY";//m  //maxBarLength
    private boolean keepSymmetry;
    private boolean optimizeSections;
    private boolean optimizeSupportedNodesX;
    private boolean optimizeSupportedNodesY;
    private boolean optimizeLoadedNodesX;
    private boolean optimizeLoadedNodesY;
    private boolean optimizeFreeNodesX;
    private boolean optimizeFreeNodesY;
    private int maxNodalXDisplacement;//cm
    private int maxNodalYDisplacement;//cm
    private int nodalGrid;//cm
    private double maxDeflection;//mm
    private int minSectionIndex;
    /**
     * This is the total number of CHS sections in the database
     */
    private int maxSectionIndex;
    private MirrorLine symmetryAxis;
    private double minNodeSpacing;//cm
    private double maxBarLength;//cm

    //private TrussModel truss;
    public OptimizeModel() { //TrussModel truss

        prefs = Preferences.userNodeForPackage(this.getClass());

        optimizeSections = prefs.getBoolean(OPTIMIZE_SECTIONS_PREF_KEY, DEFAULT_OPTIMIZE_SECTIONS);
        optimizeSupportedNodesX = prefs.getBoolean(OPTIMIZE_SUPPORTED_NODES_X_PREF_KEY, DEFAULT_OPTIMIZE_SUPPORTED_NODES_X);
        optimizeSupportedNodesY = prefs.getBoolean(OPTIMIZE_SUPPORTED_NODES_Y_PREF_KEY, DEFAULT_OPTIMIZE_SUPPORTED_NODES_Y);

        optimizeLoadedNodesX = prefs.getBoolean(OPTIMIZE_LOADED_NODES_X_PREF_KEY, DEFAULT_OPTIMIZE_LOADED_NODES_X);
        optimizeLoadedNodesY = prefs.getBoolean(OPTIMIZE_LOADED_NODES_Y_PREF_KEY, DEFAULT_OPTIMIZE_LOADED_NODES_Y);

        optimizeFreeNodesX = prefs.getBoolean(OPTIMIZE_FREE_NODES_X_PREF_KEY, DEFAULT_OPTIMIZE_FREE_NODES_X);
        optimizeFreeNodesY = prefs.getBoolean(OPTIMIZE_FREE_NODES_Y_PREF_KEY, DEFAULT_OPTIMIZE_FREE_NODES_Y);

        maxNodalXDisplacement = prefs.getInt(MAX_NODAL_X_DISPLACEMENT_PREF_KEY, DEFAULT_MAX_NODAL_X_DISPLACEMENT);
        maxNodalYDisplacement = prefs.getInt(MAX_NODAL_Y_DISPLACEMENT_PREF_KEY, DEFAULT_MAX_NODAL_Y_DISPLACEMENT);

        nodalGrid = prefs.getInt(NODAL_GRID_PREF_KEY, DEFAULT_NODAL_GRID);
        maxDeflection = prefs.getDouble(MAX_DEFLCETION_PREF_KEY, DEFAULT_MAX_DEFLCETION);

        minSectionIndex = prefs.getInt(MIN_SECTION_INDEX_PREF_KEY, DEFAULT_MIN_SECTION_INDEX);
        maxSectionIndex = prefs.getInt(MAX_SECTION_INDEX_PREF_KEY, DEFAULT_MAX_SECTION_INDEX);



        minNodeSpacing = prefs.getDouble(MIN_NODE_SPACING_PREF_KEY, DEFAULT_MIN_NODE_SPACING);
        maxBarLength = prefs.getDouble(MAX_BAR_LENGTH_PREF_KEY, DEFAULT_MAX_BAR_LENGTH);

        keepSymmetry = prefs.getBoolean(KEEP_SYMMETRY_PREF_KEY, DEFAULT_KEEP_SYMMETRY);
        symmetryAxis = new MirrorLine(
                prefs.getDouble(SYMMETRY_X1_PREF_KEY, 0),
                prefs.getDouble(SYMMETRY_Y1_PREF_KEY, 0),
                prefs.getDouble(SYMMETRY_X2_PREF_KEY, 0),
                prefs.getDouble(SYMMETRY_Y2_PREF_KEY, 0));

    }

    public boolean isOptimizeSupportedNodesY() {
        return optimizeSupportedNodesY;
    }

    public boolean isOptimizeSupportedNodesX() {
        return optimizeSupportedNodesX;
    }

    public boolean isOptimizeSections() {
        return optimizeSections;
    }

    public boolean isOptimizeLoadedNodesY() {
        return optimizeLoadedNodesY;
    }

    public boolean isOptimizeLoadedNodesX() {
        return optimizeLoadedNodesX;
    }

    public boolean isOptimizeFreeNodesY() {
        return optimizeFreeNodesY;
    }

    public boolean isOptimizeFreeNodesX() {
        return optimizeFreeNodesX;
    }

    public boolean isKeepSymmetry() {
        return keepSymmetry;
    }

    public MirrorLine getSymmetryAxis() {
        return symmetryAxis;
    }

    public int getNodalGrid() {
        return nodalGrid;
    }

    public double getMinNodeSpacing() {
        return minNodeSpacing;
    }

    public int getMaxNodalYDisplacement() {
        return maxNodalYDisplacement;
    }

    public int getMaxNodalXDisplacement() {
        return maxNodalXDisplacement;
    }

    public double getMaxDeflection() {
        return maxDeflection;
    }

    public double getMaxBarLength() {
        return maxBarLength;
    }

    public int getMaxSectionIndex() {
        return maxSectionIndex;
    }

    public int getMinSectionIndex() {
        return minSectionIndex;
    }

    public void setMaxBarLength(double maxBarLength) {
        this.maxBarLength = maxBarLength;
        prefs.put(MAX_BAR_LENGTH_PREF_KEY, Double.toString(maxBarLength));
        setChanged();
        notifyObservers(this);
    }

    public void setMinNodeSpacing(double minNodeSpacing) {
        this.minNodeSpacing = minNodeSpacing;
        prefs.put(MIN_NODE_SPACING_PREF_KEY, Double.toString(minNodeSpacing));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeFreeNodesX(boolean optimizeFreeNodesX) {
        this.optimizeFreeNodesX = optimizeFreeNodesX;
        prefs.put(OPTIMIZE_FREE_NODES_X_PREF_KEY, Boolean.toString(optimizeFreeNodesX));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeFreeNodesY(boolean optimizeFreeNodesY) {
        this.optimizeFreeNodesY = optimizeFreeNodesY;
        prefs.put(OPTIMIZE_FREE_NODES_Y_PREF_KEY, Boolean.toString(optimizeFreeNodesY));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeLoadedNodesX(boolean optimizeLoadedNodesX) {
        this.optimizeLoadedNodesX = optimizeLoadedNodesX;
        prefs.put(OPTIMIZE_LOADED_NODES_X_PREF_KEY, Boolean.toString(optimizeLoadedNodesX));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeLoadedNodesY(boolean optimizeLoadedNodesY) {
        this.optimizeLoadedNodesY = optimizeLoadedNodesY;
        prefs.put(OPTIMIZE_LOADED_NODES_Y_PREF_KEY, Boolean.toString(optimizeLoadedNodesY));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeSections(boolean optimizeSections) {
        this.optimizeSections = optimizeSections;
        prefs.put(OPTIMIZE_SECTIONS_PREF_KEY, Boolean.toString(optimizeSections));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeSupportedNodesX(boolean optimizeSupportedNodesX) {
        this.optimizeSupportedNodesX = optimizeSupportedNodesX;
        prefs.put(OPTIMIZE_SUPPORTED_NODES_X_PREF_KEY, Boolean.toString(optimizeSupportedNodesX));
        setChanged();
        notifyObservers(this);
    }

    public void setOptimizeSupportedNodesY(boolean optimizeSupportedNodesY) {
        this.optimizeSupportedNodesY = optimizeSupportedNodesY;
        prefs.put(OPTIMIZE_SUPPORTED_NODES_Y_PREF_KEY, Boolean.toString(optimizeSupportedNodesY));
        setChanged();
        notifyObservers(this);
    }

    public void setNodalGrid(int nodalGrid) {
        this.nodalGrid = nodalGrid;
        prefs.put(NODAL_GRID_PREF_KEY, Integer.toString(nodalGrid));
        setChanged();
        notifyObservers(this);
    }

    public void setMaxNodalXDisplacement(int maxNodalXDisplacement) {
        this.maxNodalXDisplacement = maxNodalXDisplacement;
        prefs.put(MAX_NODAL_X_DISPLACEMENT_PREF_KEY, Integer.toString(maxNodalXDisplacement));
        setChanged();
        notifyObservers(this);
    }

    public void setMaxNodalYDisplacement(int maxNodalYDisplacement) {
        this.maxNodalYDisplacement = maxNodalYDisplacement;
        prefs.put(MAX_NODAL_Y_DISPLACEMENT_PREF_KEY, Integer.toString(maxNodalYDisplacement));
        setChanged();
        notifyObservers(this);
    }

    /**
     * 
     * @param maxDeflection in cm
     */
    public void setMaxDeflection(double maxDeflection) {
        this.maxDeflection = maxDeflection;
        prefs.put(MAX_DEFLCETION_PREF_KEY, Double.toString(maxDeflection));
        setChanged();
        notifyObservers(this);
    }

    public void setMaxSectionIndex(int maxSectionIndex) {
        this.maxSectionIndex = maxSectionIndex;
        prefs.put(MAX_SECTION_INDEX_PREF_KEY, Integer.toString(maxSectionIndex));
        setChanged();
        notifyObservers(this);
    }

    public void setMinSectionIndex(int minSectionIndex) {
        this.minSectionIndex = minSectionIndex;
        prefs.put(MIN_SECTION_INDEX_PREF_KEY, Integer.toString(minSectionIndex));
        setChanged();
        notifyObservers(this);
    }

    public void setKeepSymmetry(boolean keepSymmetry) {
        this.keepSymmetry = keepSymmetry;
        prefs.put(KEEP_SYMMETRY_PREF_KEY, Boolean.toString(keepSymmetry));
        setChanged();
        notifyObservers(this);
    }

    public void setSymmetryAxis(MirrorLine symmetryAxis) {
        this.symmetryAxis = symmetryAxis;

        prefs.put(SYMMETRY_X1_PREF_KEY, Double.toString(symmetryAxis.getX1()));
        prefs.put(SYMMETRY_X2_PREF_KEY, Double.toString(symmetryAxis.getX2()));
        prefs.put(SYMMETRY_Y1_PREF_KEY, Double.toString(symmetryAxis.getY1()));
        prefs.put(SYMMETRY_Y2_PREF_KEY, Double.toString(symmetryAxis.getY2()));
        setChanged();
        notifyObservers(this);
    }
}



