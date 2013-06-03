package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.Utils;
import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.TableColumn;


import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.BarTableModel;
import trussoptimizater.Gui.GUIModels.SectionComboBoxModel;
import trussoptimizater.Gui.Tables.MyComboBoxRenderer;
import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Truss.Elements.Bar;

public class OpenAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;

    public OpenAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text, null, desc, gui, truss);
    }

    public OpenAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text, icon, desc, null, gui, truss);

    }

    public OpenAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui = gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {

        if (gui.getFileChooser().showOpenFileChooser()) {
            //only want to exectue if user open something and doesnt click cancel
            gui.setSavePath(gui.getFileChooser().getChoosenFilePath());
            try {
                Object[] programData = (Object[]) Utils.readObjectfromFile(gui.getFileChooser().getChoosenFilePath());

                ArrayList<Object[]> elementArrays = (ArrayList<Object[]>) programData[0];

                /*SectionComboBoxModel scbm = (SectionComboBoxModel) programData[1];
                gui.setSectionComboBoxModel(scbm);
                gui.getSectionDialog().setListModel(scbm);*/
                gui.getUndoManager().setCompoundUnadoableEdit(true);
                truss.setElementArrays(elementArrays);
                gui.getUndoManager().setCompoundUnadoableEdit(false);
                MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY).actionPerformed(null);

                //TableColumn col = gui.getBarTable().getTable().getColumnModel().getColumn(BarTableModel.SECTION_COLUMN_INDEX);
                //col.setCellRenderer(new MyComboBoxRenderer(gui.getSectionComboBoxModel()));

                for (int i = 0; i < truss.getBarModel().size(); i++) {
                    truss.getBarModel().get(i).getNode1().addObserver(truss.getBarModel().get(i));
                    truss.getBarModel().get(i).getNode2().addObserver(truss.getBarModel().get(i));
                }

                if (truss.getAnalysisMethods().getAnalysisMethod() == AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD) {
                    for (int i = 0; i < truss.getBarModel().size(); i++) {
                        if (truss.getBarModel().get(i).getRestraint().equals(Bar.FIXED_FIXED_RESTRAINT)) {
                            Object[] options = {"Pinned Stiffness Method", "Frame Stiffness Method"};
                            int n = JOptionPane.showOptionDialog(gui.getFrame(), "You current analysis method is \"Pin Jointed\" and certain Bars are Fixed.\nPlease choose your desired analysis method. \nNote:If you want to analyze this as a Pin Jointed truss all bars will be converted to Pinned", "Alert", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                            if (n == 0) {
                                truss.getAnalysisMethods().setAnalysisMethod(AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD);
                                for (int j = 0; j < truss.getBarModel().size(); j++) {
                                    truss.getBarModel().get(j).setRestraint(Bar.PINNED_PINNED_RESTRAINT);
                                }
                                gui.getBarTable().getBarTableModel().getEditableColumns()[BarTableModel.RESTRAINTS_COLUMN_INDEX] = false;
                            } else {
                                truss.getAnalysisMethods().setAnalysisMethod(AnalysisMethods.FRAME_STIFFNESS_METHOD);
                                gui.getBarTable().getBarTableModel().getEditableColumns()[BarTableModel.RESTRAINTS_COLUMN_INDEX] = true;
                            }
                            break;
                        }
                    }
                }


                gui.getFrame().setTitle("Truss Optimization - " + gui.getFileChooser().getFileName());
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(gui.getFrame(), gui.getFileChooser().getFileName() + " is not compatible with this version", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
