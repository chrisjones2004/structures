package trussoptimizater.Gui.Properties;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIPanelModel;
import trussoptimizater.Gui.OptionPanel;
import trussoptimizater.Gui.StackedBox;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.TrussModel;


public class PropertyPanel extends OptionPanel  {//


   
    
    private GUI gui;
    protected static final String TITLE = "Properties";
    private NodesPropertiesPanel nodePanel;

    private BarsPropertiesPanel barPanel;
    private LoadsPropertiesPanel loadPanel;
    private SupportsPropertiesPanel supportPanel;


    public PropertyPanel(TrussModel truss, GUI gui) {
        super();
        this.gui = gui;
        nodePanel = new NodesPropertiesPanel(truss,gui);
        barPanel = new BarsPropertiesPanel(truss,gui);
        loadPanel = new LoadsPropertiesPanel(truss,gui);
        supportPanel = new SupportsPropertiesPanel(truss,gui);
    }

    public JScrollPane getGeometryPanel() {
        StackedBox box = new StackedBox();

        box.addBox("Nodes", nodePanel.getPanel());
        box.addBox("Bars", barPanel.getPanel());
        box.addBox("Loads", loadPanel.getPanel());
        box.addBox("Supports", supportPanel.getPanel());
        return new JScrollPane(box);
    }


    @Override
    protected  JComponent getMainPanel() {
        return getGeometryPanel();
    }

    @Override
    protected  String getPanelTitle() {
        return TITLE;
    }

    @Override
    protected Action getCloseAction() {
        return new closeAction();
    }

    class closeAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            //gui.getGuiPanelModel().setPropertyPanelVisible(false);
            gui.getGuiPanelModel().setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
        }
    }
}
