package trussoptimizater.Truss.Optimize;
import java.util.prefs.*;
import trussoptimizater.Truss.TrussModel;


public class OptimizeMethods extends java.util.Observable{

    /**
     * Prefereneces is used to store selected values
     */
    private Preferences prefs;

    /**
     * Preference key used for storing and reading optimize method preference
     */
    public static final String OPTIMIZE_METHOD_PREF_KEY = "OPTIMIZE_METHOD_PREF_KEY";

    /**
     * Integer representing genetic optimization method
     */
    public static final int GA_METHOD = 1;

    /**
     * The method to use when optmizing the truss
     */
    private int optimizeMethod;

    /**
     * Genetic Algorithm Optimizer
     */
    private GAOptimizer gaOptimizer;


    public OptimizeMethods(TrussModel truss){
        prefs = Preferences.userNodeForPackage(this.getClass());
        optimizeMethod = prefs.getInt(OPTIMIZE_METHOD_PREF_KEY , GA_METHOD);
        initOptimizers(truss);
    }

    /**
     *
     * @return current optimization method
     */
    public int getOptimizeMethod() {
        return optimizeMethod;
    }

    /**
     * Initilize all optimizers
     * @param truss Truss model needed for creating optimizer objects
     */
    public void initOptimizers(TrussModel truss){
        gaOptimizer = new GAOptimizer(truss);
    }

    /**
     *
     * @param optimizeMethod an integer representing an analysis method. Should be
     * <ul>
     * <li>OptimizeMethods.GA_METHOD
     * </ul>
     */
    public void setOptimizeMethod(int optimizeMethod) {
        this.optimizeMethod = optimizeMethod;
        prefs.put(OPTIMIZE_METHOD_PREF_KEY ,Integer.toString(optimizeMethod));
        setChanged();
        notifyObservers(this);
    }

    public GAOptimizer getGAOptimizer() {
        return gaOptimizer;
    }

}