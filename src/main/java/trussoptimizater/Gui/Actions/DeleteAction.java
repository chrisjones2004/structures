package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import trussoptimizater.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.Elements.Element;

public class DeleteAction extends AbstractAction {

    private TrussModel truss;
    private GUI gui;

    public DeleteAction(String text, String desc, TrussModel truss, GUI gui) {
        super(text);
        this.truss = truss;
        this.gui = gui;
        putValue(SHORT_DESCRIPTION, desc);
    }

    public void actionPerformed(ActionEvent e) {
        //do nothig currently
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


    private ArrayList getSpecificElements(ElementModel<? extends Element> model,ArrayList<Integer> elementIndexes){
        ArrayList<Element> elements = new ArrayList<Element>();
        for(int i = 0;i<elementIndexes.size();i++){
            elements.add(model.get(elementIndexes.get(i)));
        }
        return elements;
    }


    public void deleteSpecificElements(ElementModel<? extends Element> model, String elementIndexes) {
        ArrayList<Integer> numberArray = parseNumberSelection(elementIndexes);
        ArrayList elementsToBeDeleted = getSpecificElements(model, numberArray);
        model.removeAll(elementsToBeDeleted);
    }



    public void deleteAllSelectedElements() {
        gui.getUndoManager().setCompoundUnadoableEdit(true);
        deleteSelectedElements(truss.getBarModel(), truss.getBarModel().getSelectedElements());
        deleteSelectedElements(truss.getSupportModel(), truss.getSupportModel().getSelectedElements());
        deleteSelectedElements(truss.getLoadModel(), truss.getLoadModel().getSelectedElements());
        deleteSelectedElements(truss.getNodeModel(), truss.getNodeModel().getSelectedElements());
        gui.getUndoManager().setCompoundUnadoableEdit(false);
    }

    public void deleteSelectedElements(ElementModel<? extends Element> model,ArrayList elements) {
        if (elements.isEmpty()) {
            return;
        }
        model.removeAll(elements);
    }
}
