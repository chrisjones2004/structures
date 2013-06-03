package trussoptimizater.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import trussoptimizater.Gui.Actions.MyActionMap;


public class MainToolBar {
    
    //private TrussModel truss;
    private GUI gui;
    private JToolBar toolBar;
    
    public MainToolBar(GUI gui){
        this.gui = gui;
    }

    public void createMainToolBar() {

        JLabel highLightLabel = new JLabel("Select: ");
        highLightLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
        String[] highLightOptions = {"Nothing", "All Nodes", "Isolated Nodes", "All Bars", "Pinned Bars", "Fixed Bars"};
        JComboBox highLightComboBox = new JComboBox(highLightOptions);
        highLightComboBox.setAction(MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ACTION_KEY));
        
        
        
        JButton newButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.NEW_ACTION_KEY));
        JButton openButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.OPEN_ACTION_KEY));
        JButton saveButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.SAVE_ACTION_KEY));
        JButton undoButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.UNDO_ACTION_KEY));
        JButton redoButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.REDO_ACTION_KEY));
        JButton zoomInButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_IN_ACTION_KEY));
        JButton zoomOutButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_OUT_ACTION_KEY));
        JButton zoomAllButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY));
        JButton analyzeButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.ANALYZE_ACTION_KEY));
        JButton optimizeButton =new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.OPTIMIZE_ACTION_KEY));
        JButton helpButton = new JButton(MyActionMap.ACTION_MAP.get(MyActionMap.HELP_ACTION_KEY));
        
        
        
        gui.getStatusToolBar().getHintManager().addHintFor(highLightComboBox, highLightComboBox.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(newButton, newButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(openButton, openButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(saveButton, saveButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(undoButton, undoButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(redoButton, redoButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(zoomInButton, zoomInButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(zoomOutButton, zoomOutButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(zoomAllButton, zoomAllButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(analyzeButton, analyzeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(optimizeButton, optimizeButton.getToolTipText());
        gui.getStatusToolBar().getHintManager().addHintFor(helpButton, helpButton.getToolTipText());

        toolBar = new JToolBar("File ToolBar");
        toolBar.add(newButton );
        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.add(undoButton);
        toolBar.add(redoButton );
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.add(zoomAllButton);
        toolBar.add(analyzeButton);
        toolBar.add(optimizeButton);
        toolBar.add(helpButton);
        toolBar.addSeparator();
        toolBar.add(highLightLabel);
        toolBar.add(highLightComboBox);


        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setRollover(true);
        toolBar.setBorder(new LineBorder(Color.DARK_GRAY));
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

}
