package trussoptimizater.Gui;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.LineBorder;
import trussoptimizater.Gui.Actions.MyActionMap;



public class ElementToolBar {

    private JToolBar toolBar;
    private GUI gui;

    public ElementToolBar(GUI gui){
        this.gui = gui;
    }

    public void createtoolBar() {
        UIManager.put("Button.margin", new Insets(0, 0, 0, 0));


        JButton selectModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_MODE_ACTION_KEY));
        JButton nodeModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.NODE_MODE_ACTION_KEY));
        JButton barModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.BAR_MODE_ACTION_KEY));
        JButton trussModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.TRUSS_MODE_ACTION_KEY));
        JButton sectionModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.SECTION_MODE_ACTION_KEY));
        JButton supportModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.SUPPORT_MODE_ACTION_KEY));
        JButton loadModeButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.LOAD_MODE_ACTION_KEY));
        JButton deleteButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.DELETE_KEY_BINDING_KEY));


        gui.getStatusToolBar().getHintManager().addHintFor(selectModeButton, selectModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(nodeModeButton, nodeModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(barModeButton, barModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(trussModeButton, trussModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(sectionModeButton, sectionModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(supportModeButton, supportModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(loadModeButton, loadModeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(deleteButton, deleteButton.getToolTipText());


        toolBar = new JToolBar("My ToolBar");
        toolBar.add(selectModeButton);
        toolBar.add(nodeModeButton);
        toolBar.add(barModeButton);
        toolBar.add(trussModeButton);
        toolBar.add(sectionModeButton);
        toolBar.add(supportModeButton);
        toolBar.add(loadModeButton);
        toolBar.add(deleteButton);

        toolBar.setOrientation(JToolBar.VERTICAL);
        toolBar.setBackground(Color.LIGHT_GRAY);
        toolBar.setForeground(Color.LIGHT_GRAY);
        toolBar.setBorder(new LineBorder(Color.DARK_GRAY));
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

}
