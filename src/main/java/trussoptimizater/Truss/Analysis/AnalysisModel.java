package trussoptimizater.Truss.Analysis;

import java.util.prefs.Preferences;


public class AnalysisModel {

    public static final String SELF_WEIGHT_INCLUSION = "SELF_WEIGHT";
    /**
     * Prefereneces is used to store selected values
     */
    private Preferences prefs;
    private boolean includeSelfWeight;

    public AnalysisModel(){
        prefs = Preferences.userNodeForPackage(this.getClass());
        includeSelfWeight = prefs.getBoolean(SELF_WEIGHT_INCLUSION, true);
    }

    public boolean isSelfWeightIncluded() {
        return includeSelfWeight;
    }

    public void setIncludeSelfWeight(boolean includeSelfWeight) {
        this.includeSelfWeight = includeSelfWeight;
        prefs.put(SELF_WEIGHT_INCLUSION,Boolean.toString(includeSelfWeight) );
    }

}
