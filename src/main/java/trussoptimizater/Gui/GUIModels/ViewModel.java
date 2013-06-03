package trussoptimizater.Gui.GUIModels;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.prefs.Preferences;
import trussoptimizater.Truss.Elements.Node;


public class ViewModel extends java.util.Observable {
    
    /**
     * Prefereneces is used to store selected values
     */
    private Preferences prefs;
    private static final String NODES_VISIBLE_PREF_KEY = "NODES_VISIBLE_PREF_KEY";
    private static final String NODE_NUMBERS_VISIBLE_PREF_KEY = "NODE_NUMBERS_VISIBLE_PREF_KEY";
    private static final String SUPPORTS_VISIBLE_PREF_KEY = "SUPPORTS_VISIBLE_PREF_KEY";
    private static final String SUPPORT_NUMBERS_VISIBLE_PREF_KEY = "SUPPORT_NUMBERS_VISIBLE_PREF_KEY";
    private static final String BARS_VISIBLE_PREF_KEY = "BARS_VISIBLE_PREF_KEY";
    private static final String BAR_NUMBERS_VISIBLE_PREF_KEY = "BAR_NUMBERS_VISIBLE_PREF_KEY";
    private static final String BAR_AXIS_VISIBLE_PREF_KEY = "BAR_AXIS_VISIBLE_PREF_KEY";
    private static final String LOADS_VISIBLE_PREF_KEY = "LOADS_VISIBLE_PREF_KEY";
    private static final String LOAD_NUMBERS_VISIBLE_PREF_KEY = "LOAD_NUMBERS_VISIBLE_PREF_KEY";
    private static final String LOAD_VALUES_VISIBLE_PREF_KEY = "LOAD_VALUES_VISIBLE_PREF_KEY";

    
    
    /*View options*/
    private boolean nodeNumbersVisible;
    private boolean barNumbersVisible;
    private boolean barsVisible;
    private boolean barAxisVisible;
    private boolean supportNumbersVisible;
    private boolean loadNumbersVisible;
    private boolean loadValuesVisible;
    private boolean nodesVisible;    
    private boolean supportsVisible;
    private boolean loadsVisible;

    
    /*ANALYSIS VIEW OPTIONS*/
    private boolean deflectionsVisible = false;
    private boolean colorTensionCompression = false;
    private boolean colorAxialForces = false;
    private boolean axialForcesVisible = false;
    private boolean reactionsVisible = false;
    private boolean momentsVisible = false;
    private boolean shearVisible = false;
    private double deflectionZoomScale = 1;
    private double momentZoomScale = 1;
    private double shearZoomScale = 1;
    /*Mode to ViewModel*/
    private boolean currentlyDrawingBar = false;
    private Node barStartNode;
    private Point2D.Double dragStartScreen = new Point2D.Double();
    private boolean currentlyDragSelecting = false;
    private String dragDirection = null;
    public static final String RIGHT_DRAG = "RIGHT_DRAG";
    public static final String LEFT_DRAG = "lEFT_DRAG";
    private Rectangle2D selectionRectangle = new Rectangle2D.Double();
    
   
    
    public ViewModel(){

        prefs = Preferences.userNodeForPackage(this.getClass());
        nodesVisible = prefs.getBoolean(NODES_VISIBLE_PREF_KEY, true);
        nodeNumbersVisible = prefs.getBoolean(NODE_NUMBERS_VISIBLE_PREF_KEY, true);
        supportsVisible = prefs.getBoolean(SUPPORTS_VISIBLE_PREF_KEY, true);
        supportNumbersVisible = prefs.getBoolean(SUPPORT_NUMBERS_VISIBLE_PREF_KEY, true);
        barsVisible = prefs.getBoolean(BARS_VISIBLE_PREF_KEY, true);
        barNumbersVisible = prefs.getBoolean(BAR_NUMBERS_VISIBLE_PREF_KEY, true);
        barAxisVisible = prefs.getBoolean(BAR_AXIS_VISIBLE_PREF_KEY, false);
        loadNumbersVisible = prefs.getBoolean(LOAD_NUMBERS_VISIBLE_PREF_KEY, true);
        loadsVisible = prefs.getBoolean(LOADS_VISIBLE_PREF_KEY, true);
        loadValuesVisible = prefs.getBoolean(LOAD_VALUES_VISIBLE_PREF_KEY, true);
        
        
        
    }

    public boolean isCurrentlyDragSelecting() {
        return currentlyDragSelecting;
    }

    public boolean isCurrentlyDrawingBar() {
        return currentlyDrawingBar;
    }

    public boolean isLoadValuesVisible() {
        return loadValuesVisible;
    }

    public boolean isLoadNumbersVisible() {
        return loadNumbersVisible;
    }

    public boolean isSupportNumbersVisible() {
        return supportNumbersVisible;
    }

    public boolean isBarNumbersVisible() {
        return barNumbersVisible;
    }

    public boolean isNodeNumbersVisible() {
        return nodeNumbersVisible;
    }

    public boolean isDeflectionsVisible() {
        return deflectionsVisible;
    }

    public boolean isLoadsVisible() {
        return loadsVisible;
    }

    public boolean isSupportsVisible() {
        return supportsVisible;
    }

    public boolean isBarsVisible() {
        return barsVisible;
    }

    public boolean isNodesVisible() {
        return nodesVisible;
    }

    public boolean isColorTensionCompression() {
        return colorTensionCompression;
    }

    public boolean isMomentsVisible() {
        return momentsVisible;
    }

    public boolean isShearVisible() {
        return shearVisible;
    }

    public boolean isReactionsVisible() {
        return reactionsVisible;
    }

    public boolean isAxialForcesVisible() {
        return axialForcesVisible;
    }

    public boolean isColorAxialForces() {
        return colorAxialForces;
    }

    public boolean isDraggingRight(){
        if(ViewModel.RIGHT_DRAG.equals(this.dragDirection)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isDraggingLeft(){
        if(ViewModel.LEFT_DRAG.equals(this.dragDirection)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isBarAxisVisible() {
        return barAxisVisible;
    }


    public double getShearZoomScale() {
        return shearZoomScale;
    }

    public double getMomentZoomScale() {
        return momentZoomScale;
    }

    public double getDeflectionZoomScale() {
        return deflectionZoomScale;
    }

    public Rectangle2D getSelectionRectangle() {
        return selectionRectangle;
    }

    public Point2D.Double getDragStartScreen() {
        return dragStartScreen;
    }

    public Node getBarStartNode() {
        return barStartNode;
    }

    public void setColorTensionCompression(boolean colorTensionCompression) {
        this.colorTensionCompression = colorTensionCompression;
        setChanged();
        notifyObservers(this);
    }

    public void setAxialForcesVisible(boolean axialForcesVisible) {
        this.axialForcesVisible = axialForcesVisible;
        setChanged();
        notifyObservers(this);
    }

    public void setReactionsVisible(boolean reactionsVisible) {
        this.reactionsVisible = reactionsVisible;
        setChanged();
        notifyObservers(this);
    }

    public void setMomentsVisible(boolean momentsVisible) {
        this.momentsVisible = momentsVisible;
        setChanged();
        notifyObservers(this);
    }

    public void setShearVisible(boolean shearVisible) {
        this.shearVisible = shearVisible;
        setChanged();
        notifyObservers(this);
    }

    public void setDeflectionZoomScale(double deflectionZoomScale) {
        this.deflectionZoomScale = deflectionZoomScale;
        setChanged();
        notifyObservers(this);
    }

    public void setShearZoomScale(double shearZoomScale) {
        this.shearZoomScale = shearZoomScale;
        setChanged();
        notifyObservers(this);
    }

    public void setMomentZoomScale(double momentZoomScale) {
        this.momentZoomScale = momentZoomScale;
        setChanged();
        notifyObservers(this);
    }

    public void setNodeNumbersVisible(boolean nodeNumbersVisible) {
        this.nodeNumbersVisible = nodeNumbersVisible;
        prefs.put(NODE_NUMBERS_VISIBLE_PREF_KEY, Boolean.toString(nodeNumbersVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setBarNumbersVisible(boolean barNumbersVisible) {
        this.barNumbersVisible = barNumbersVisible;
        prefs.put(BAR_NUMBERS_VISIBLE_PREF_KEY, Boolean.toString(barNumbersVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setSupportNumbersVisible(boolean supportNumbersVisible) {
        this.supportNumbersVisible = supportNumbersVisible;
        prefs.put(SUPPORT_NUMBERS_VISIBLE_PREF_KEY, Boolean.toString(supportNumbersVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setLoadNumbersVisible(boolean loadNumbersVisible) {
        this.loadNumbersVisible = loadNumbersVisible;
        prefs.put(LOAD_NUMBERS_VISIBLE_PREF_KEY, Boolean.toString(loadNumbersVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setLoadValuesVisible(boolean loadValuesVisible) {
        this.loadValuesVisible = loadValuesVisible;
        prefs.put(LOAD_VALUES_VISIBLE_PREF_KEY, Boolean.toString(loadValuesVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setNodesVisible(boolean nodesVisible) {
        this.nodesVisible = nodesVisible;
        prefs.put(NODES_VISIBLE_PREF_KEY, Boolean.toString(supportsVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setBarsVisible(boolean barsVisible) {
        this.barsVisible = barsVisible;
        prefs.put(BARS_VISIBLE_PREF_KEY, Boolean.toString(barsVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setSupportsVisible(boolean supportsVisible) {
        this.supportsVisible = supportsVisible;
        prefs.put(SUPPORTS_VISIBLE_PREF_KEY, Boolean.toString(supportsVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setLoadsVisible(boolean loadsVisible) {
        this.loadsVisible = loadsVisible;
        prefs.put(LOADS_VISIBLE_PREF_KEY, Boolean.toString(loadsVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setDeflectionsVisible(boolean deflectionsVisible) {
        this.deflectionsVisible = deflectionsVisible;
        setChanged();
        notifyObservers(this);
    }

    public void setColorAxialForces(boolean colorAxialForces) {
        this.colorAxialForces = colorAxialForces;
        setChanged();
        notifyObservers(this);
    }

    public void setDragStartScreen(Point dragStartScreen) {//2D.Double
        this.dragStartScreen = new Point2D.Double(dragStartScreen.x,dragStartScreen.y);
        setChanged();
        notifyObservers(this);
    }

    public void setCurrentlyDragSelecting(boolean currentlyDragSelecting) {
        this.currentlyDragSelecting = currentlyDragSelecting;
        setChanged();
        notifyObservers(this);
    }

    public void setBarStartNode(Node barStartNode) {
        this.barStartNode = barStartNode;
        setChanged();
        notifyObservers(this);
    }

    public void setSelectionRectangle(Rectangle2D selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
        setChanged();
        notifyObservers(this);
    }

    public void setCurrentlyDrawingBar(boolean currentlyDrawingBar) {
        this.currentlyDrawingBar = currentlyDrawingBar;
        setChanged();
        notifyObservers(this);
    }

    public void setBarAxisVisible(boolean barAxisVisible) {
        this.barAxisVisible = barAxisVisible;
        prefs.put(BAR_AXIS_VISIBLE_PREF_KEY, Boolean.toString(barAxisVisible));
        setChanged();
        notifyObservers(this);
    }

    public void setDragDirection(String dragDirection) {
        this.dragDirection = dragDirection;
    }

}
