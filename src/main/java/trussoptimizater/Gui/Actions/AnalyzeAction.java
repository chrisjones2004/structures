package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;


public class AnalyzeAction extends AbstractAction {

    private TrussModel truss;

    
    public AnalyzeAction( TrussModel truss){
        this(null,null,null,truss);
    }
    
    public AnalyzeAction(String text, String desc,  TrussModel truss) {
        this(text,null,desc,truss);
    }

    public AnalyzeAction(String text, ImageIcon icon, String desc,  TrussModel truss) {
        this(text,icon,desc,null,truss);

    }

    public AnalyzeAction(String text, ImageIcon icon, String desc, Integer mnemonic, TrussModel truss) {
        super(text, icon);
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke("F6"));
        //analyzeMenuItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
    }

    public void actionPerformed(ActionEvent e) {
        /*if(truss.getAnalysisModel().getAnalysisMethod() == AnalysisMethods.FRAME_STIFFNESS_METHOD){
            truss.getFrameAnalyzer().fullAnalysis();
        }else if(truss.getAnalysisModel().getAnalysisMethod() == AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD){
            truss.getPinJointedAnalyzer().fullAnalysis();
        }*/
        truss.getAnalysisMethods().getAnalyzer().fullAnalysis();
        
    }
    

}
