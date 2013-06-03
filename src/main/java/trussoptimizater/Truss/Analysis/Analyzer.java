package trussoptimizater.Truss.Analysis;

import javax.swing.JOptionPane;
import trussoptimizater.Truss.TrussModel;


public abstract class Analyzer {
    
    protected AnalysisModel analysisModel;
    protected boolean verbose = false;
    public Analyzer(){
        this.analysisModel = new AnalysisModel();
    }

    public AnalysisModel getAnalysisModel() {
        return analysisModel;
    }


    public abstract boolean quickAnalysis();

    public abstract boolean fullAnalysis();

        /**
     * returns true if the the following conditions are met
     * <p>
     * <ul>
     * <li>The structure must be continous and rigid - TO DO
     * <li>No isolated nodes
     * <li>All bars must be assigned section sizes
     * <li>The structure must be stable ie supports.size() >0
     * </ul>
     * @param truss
     * @return true is structural is analyzable
     */
    public boolean isStructureAnalzable(TrussModel truss) {

        //Check there are no issolated nodes
        int barCount = 0;
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            for (int j = 0; j < truss.getBarModel().size(); j++) {
                if (truss.getBarModel().get(j).getNode1().getNumber() == truss.getNodeModel().get(i).getNumber() || truss.getBarModel().get(j).getNode2().getNumber() == truss.getNodeModel().get(i).getNumber()) {
                    barCount++;
                }
            }
            if (barCount == 0) {
                JOptionPane.showMessageDialog(null, "There are issolated nodes in structure", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            barCount = 0;
        }


        //Check all bars have a section
        /*for (int i = 0; i < truss.getBarModel().size(); i++) {
            //System.out.println("Bar "+i+" Section "+bars.get(i).getSection().getName());
            if (Bar.DEFAULT_SECTION.equals(truss.getBarModel().get(i).getSection().getName())) {
                JOptionPane.showMessageDialog(null, "You have not assigned a section to all bars", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }*/

        //Check that there are supports
        if (truss.getSupportModel().size() == 0) {
            JOptionPane.showMessageDialog(null, "You have not added any supports", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }// end of isStructureAnalzable method

}
