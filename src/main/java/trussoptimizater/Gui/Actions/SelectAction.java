package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.*;

/**
 * This action is used for combo box in main toolbar to select certain parts of structure
 * @author Chris
 */
public class SelectAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;

    public SelectAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text, null, desc, gui, truss);
    }

    public SelectAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text, icon, desc, null, gui, truss);

    }

    public SelectAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui = gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        switch (cb.getSelectedIndex()) {
            case 0:
                selectAll(false);
                break;
            case 1:
                selectAll(false);
                //selectElementArray((ElementModel) truss.getNodeModel(), true);
                truss.getNodeModel().selectAll(true);
                break;
            case 2:
                selectAll(false);
                selectIsolatedNodes();
                break;
            case 3:
                selectAll(false);
                //selectElementArray((ElementModel) truss.getBarModel(), true);
                truss.getBarModel().selectAll(true);
                break;
            case 4:
                selectAll(false);
                selectPinnedBars();
                break;
            case 5:
                selectAll(false);
                selectFixedBars();
                break;
        }

    }

    private ArrayList<Integer> getNumberRange(String from, String to) {
        ArrayList<Integer> range = new ArrayList<Integer>();
        int start = Integer.valueOf(from);
        //System.out.println(start);
        range.add(start);
        if (to == null) {
            return range;
        }
        int end = Integer.valueOf(to);
        for (int i = start + 1; i <= end; i++) {
            range.add(i);
        }
        return range;
    }

    private ArrayList<Integer> parseNumberSelection(String data) {
        ArrayList<Integer> numberArray = new ArrayList<Integer>();
        Matcher m = Pattern.compile("(\\d+)(?:\\s*-\\s*(\\d+))?").matcher(data);
        while (m.find()) {
            numberArray.addAll(getNumberRange(m.group(1), m.group(2)));
        }
        return numberArray;
    }



    public void selectSpecificElements(ElementModel<Element> elements, boolean select, String elementIndexes) {
        ArrayList<Integer> numberArray = parseNumberSelection(elementIndexes);
        gui.getUndoManager().setCompoundUnadoableEdit(true);
        for (int i = 0; i < numberArray.size(); i++) {
            int elementndex = numberArray.get(i) - 1;
            elements.get(elementndex).setSelected(select);
        }
        gui.getUndoManager().setCompoundUnadoableEdit(false);
    }

    public void selectAll(boolean select) {
        gui.getUndoManager().setCompoundUnadoableEdit(true);
        truss.getNodeModel().selectAll(select);
        truss.getBarModel().selectAll(select);
        truss.getSupportModel().selectAll(select);
        truss.getLoadModel().selectAll(select);
        gui.getUndoManager().setCompoundUnadoableEdit(false);
    }

    public void selectIsolatedNodes() {
        selectAll(false);
        gui.getUndoManager().setCompoundUnadoableEdit(true);
        int barCount = 0;
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            for (int j = 0; j < truss.getBarModel().size(); j++) {
                if (truss.getBarModel().get(j).getNode1().getNumber() == truss.getNodeModel().get(i).getNumber() || truss.getBarModel().get(j).getNode2().getNumber() == truss.getNodeModel().get(i).getNumber()) {
                    barCount++;
                }
            }
            if (barCount == 0) {
                truss.getNodeModel().get(i).setSelected(true);
            }
            barCount = 0;
        }
        gui.getUndoManager().setCompoundUnadoableEdit(false);
    }

    public void selectFixedBars() {

        gui.getUndoManager().setCompoundUnadoableEdit(true);
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            if (truss.getBarModel().get(i).getRestraint().equals(Bar.FIXED_FIXED_RESTRAINT)) {
                truss.getBarModel().get(i).setSelected(true);
            }
        }
        gui.getUndoManager().setCompoundUnadoableEdit(false);

    }

    public void selectPinnedBars() {

        gui.getUndoManager().setCompoundUnadoableEdit(true);
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            if (truss.getBarModel().get(i).getRestraint().equals(Bar.PINNED_PINNED_RESTRAINT)) {
                truss.getBarModel().get(i).setSelected(true);
            }
        }
        gui.getUndoManager().setCompoundUnadoableEdit(false);
    }
}
