package trussoptimizater.Truss.Analysis;
import java.util.prefs.*;
import trussoptimizater.Truss.TrussModel;

/**
 * Class containing all Truss Analyzer's and keeps note of which analyzer is current
 * @author Chris
 */
public class AnalysisMethods extends java.util.Observable {
    

    /**
     * Prefereneces is used to store selected values
     */
    private Preferences prefs;

    /**
     * Preference key to store and locate analysis method preference
     */
    public static final String ANALYSIS_METHOD_PREF_KEY = "ANALYSIS_METHOD";


    /**
     * Integer representing pin jointed analysis stiffness method
     */
    public static final int PIN_JOINTED_STIFFNESS_METHOD = 1;

    /**
     * Integer representing frame analysis stiffness method
     */
    public static final int FRAME_STIFFNESS_METHOD = 2;

    /**
     * The method to use when analyzing the truss
     */
    private int analysisMethod;
    
    /**
     * Analyzer to analyze truss as frame
     */
    private FrameAnalyzer frameAnalyzer;

    /**
     * Analyzer to analyze truss as pin jointed truss
     */
    private PinJointedAnalyzer pinJointedAnalyzer;



    public AnalysisMethods(TrussModel truss){
        prefs = Preferences.userNodeForPackage(this.getClass());
        analysisMethod = prefs.getInt(ANALYSIS_METHOD_PREF_KEY , FRAME_STIFFNESS_METHOD);
        initAnalyzers(truss);
    }

    /**
     * Initilize all analyzers
     * @param truss TrussModel object nesseccary for creatign Analyzer objects
     */
    private void initAnalyzers(TrussModel truss){
        frameAnalyzer = new FrameAnalyzer(truss);
        pinJointedAnalyzer = new PinJointedAnalyzer(truss);
    }

    public int getAnalysisMethod() {
        return analysisMethod;
    }

    /**
     *
     * @return the current analyzer
     */
    public Analyzer getAnalyzer(){
        switch(analysisMethod){
            case FRAME_STIFFNESS_METHOD:
                return this.frameAnalyzer;
            case PIN_JOINTED_STIFFNESS_METHOD:
                return this.pinJointedAnalyzer;
            default:
                return null;
        }
    }

    /**
     *
     * @param analysisMethod an integer representing an analysis method. Should be
     * <ul>
     * <li>AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD
     * <li>AnalysisMethods.FRAME_STIFFNESS_METHOD
     * </ul>
     */
    public void setAnalysisMethod(int analysisMethod) {
        this.analysisMethod = analysisMethod;
        prefs.put( ANALYSIS_METHOD_PREF_KEY ,Integer.toString(analysisMethod));
        setChanged();
        notifyObservers(this);
    }


}
    




