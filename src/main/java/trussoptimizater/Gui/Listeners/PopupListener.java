package trussoptimizater.Gui.Listeners;

import java.awt.event.*;
import java.util.Observable;
import javax.swing.*;

import javax.swing.JPopupMenu;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIModeModel;
import trussoptimizater.Truss.TrussModel;

public class PopupListener implements java.util.Observer {
    /*JPopUpMenu*/

    private JPopupMenu popupMenu;
    private JMenuItem resultsDisplayModeMenuItem;
    private JCheckBoxMenuItem selectModeMenuItem;
    private JCheckBoxMenuItem nodeModeMenuItem;
    private JCheckBoxMenuItem barModeMenuItem;
    private JCheckBoxMenuItem sectionModeMenuItem;
    private GUI gui;
    private TrussModel truss;

    public PopupListener(GUI gui) {
        this.gui = gui;
        this.truss = gui.getTruss();
        gui.getGuiModeModel().addObserver(this);
        truss.addObserver(this);
        createPopUpMenu();
    }

    /*POP UP METHODS*/
    public void createPopUpMenu() {
        popupMenu = new JPopupMenu();


        selectModeMenuItem = new JCheckBoxMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_MODE_ACTION_KEY));
        selectModeMenuItem.setSelected(true);
        nodeModeMenuItem = new JCheckBoxMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.NODE_MODE_ACTION_KEY));
        barModeMenuItem = new JCheckBoxMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.BAR_MODE_ACTION_KEY));
        sectionModeMenuItem = new JCheckBoxMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.SECTION_MODE_ACTION_KEY));


        ButtonGroup checkBoxes = new ButtonGroup();
        checkBoxes.add(selectModeMenuItem);
        checkBoxes.add(nodeModeMenuItem);
        checkBoxes.add(barModeMenuItem);
        checkBoxes.add(sectionModeMenuItem);


        JMenuItem zoominModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_IN_ACTION_KEY));
        JMenuItem zoomoutModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_OUT_ACTION_KEY));
        JMenuItem zoomallModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ZOOM_ALL_ACTION_KEY));

        JMenuItem displayModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.VIEW_OPTIONS_ACTION_KEY));

        resultsDisplayModeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.RESULTS_OPTIONS_ACTION_KEY));
        resultsDisplayModeMenuItem.setEnabled(false);


        JMenu modeMenu = new JMenu("Mode");
        JMenu zoomMenu = new JMenu("Zoom");
        JMenu viewMenu = new JMenu("View");

        modeMenu.add(selectModeMenuItem);
        modeMenu.add(nodeModeMenuItem);
        modeMenu.add(barModeMenuItem);
        modeMenu.add(sectionModeMenuItem);

        zoomMenu.add(zoominModeMenuItem);
        zoomMenu.add(zoomoutModeMenuItem);
        zoomMenu.add(zoomallModeMenuItem);

        viewMenu.add(displayModeMenuItem);
        viewMenu.add(resultsDisplayModeMenuItem);

        JMenuItem undoMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.UNDO_ACTION_KEY));
        //undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        JMenuItem redoMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.REDO_ACTION_KEY));
        //redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        JMenuItem analyzeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.ANALYZE_ACTION_KEY));
        //analyzeMenuItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
        /*JMenuItem optimizeMenuItem = new JMenuItem("Optimize");
        optimizeMenuItem.setAccelerator(KeyStroke.getKeyStroke("F7"));*/
        JMenuItem optimizeMenuItem = new JMenuItem(MyActionMap.ACTION_MAP.get(MyActionMap.OPTIMIZE_ACTION_KEY));


        popupMenu.add(modeMenu);
        popupMenu.add(zoomMenu);
        popupMenu.add(viewMenu);
        popupMenu.addSeparator();
        popupMenu.add(undoMenuItem);
        popupMenu.add(redoMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(analyzeMenuItem);
        popupMenu.add(optimizeMenuItem);
    }

    public boolean maybeShowPopup(MouseEvent e) {
        if(gui.getView().getViewModel().isCurrentlyDrawingBar() && SwingUtilities.isRightMouseButton(e)){
            gui.getView().getViewModel().setCurrentlyDrawingBar(false);
            return false;
        }else if (e.isPopupTrigger()) {
            popupMenu.show(gui.getView(), e.getX(), e.getY());
            return true;
        }
        return false;
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void update(Observable o, Object arg) {
        switch (gui.getGuiModeModel().getMode()) {
            case GUIModeModel.BAR_MODE:
                barModeMenuItem.setSelected(true);
                break;
            case GUIModeModel.NODE_MODE:
                nodeModeMenuItem.setSelected(true);
                break;
            case GUIModeModel.SECTION_MODE:
                sectionModeMenuItem.setSelected(true);
                break;
            case GUIModeModel.SELECT_MODE:
                selectModeMenuItem.setSelected(true);
                break;
        }
         resultsDisplayModeMenuItem.setEnabled(truss.isAnalysed());
    }
}
