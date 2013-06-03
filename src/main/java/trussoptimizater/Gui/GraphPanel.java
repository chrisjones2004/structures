package trussoptimizater.Gui;

import trussoptimizater.Truss.TrussModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Observable;
import javax.swing.*;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.Actions.SelectAction;
import trussoptimizater.Gui.GUIModels.GUIPanelModel;
import trussoptimizater.Truss.Elements.Bar;

public class GraphPanel extends OptionPanel implements java.util.Observer {

    private TrussModel truss;
    private GUI gui;
    private static final String TITLE = "Bar Graphs";
    /*Graphs*/
    private Graph bmGraph = new Graph("BM", "Length (m)", "BM (KNm)");
    private Graph FXGraph = new Graph("FX", "Length (m)", "FX (KN)");
    private Graph FZGraph = new Graph("FZ", "Length (m)", "FZ (KN)");
    private JComboBox barComboBox;

    public GraphPanel(GUI gui, TrussModel truss) {
        this.truss = truss;
        this.gui = gui;
        truss.addObserver(this);
    }

    public void updateGraphs(Bar bar) {

        double[] xCords = {0, bar.getLength() / 100};
        double[] axialArrayList = {bar.getAxialForce(), bar.getAxialForce()};

        /*update cords*/
        bmGraph.setXcords(xCords);
        bmGraph.setYcords(bar.getMomentForce());
        FXGraph.setXcords(xCords);
        FXGraph.setYcords(axialArrayList);
        FZGraph.setXcords(xCords);
        FZGraph.setYcords(bar.getShearForce());

        /*Repaint graphs*/
        bmGraph.repaint();
        FXGraph.repaint();
        FZGraph.repaint();


    }

    public String[] getBarArray() {
        String[] barArray = new String[truss.getBarModel().size()];
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            barArray[i] = "Bar " + Integer.toString(truss.getBarModel().get(i).getNumber());
        }
        return barArray;
    }

    private JPanel createBarGraphPanel() {

        /*Creating Combo box containing all bars*/

        barComboBox = new JComboBox(getBarArray());
        barComboBox.setPreferredSize(new Dimension(100, barComboBox.getPreferredSize().height));
        barComboBox.setSelectedIndex(0);

        /*ActionListener to highlight respective bar in barTable*/
        barComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox jcb = (JComboBox) e.getSource();

                if (jcb.hasFocus()) {
                    Bar bar = truss.getBarModel().get(jcb.getSelectedIndex());
                    updateGraphs(bar);
                    SelectAction sa = (SelectAction)MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ACTION_KEY);
                    sa.selectAll(false);
                    truss.getBarModel().get(jcb.getSelectedIndex()).setSelected(true);
                }

            }
        });


        JPanel barGraphPanel = new JPanel(new GridLayout(3, 1));
        updateGraphs(truss.getBarModel().get(0));
        barGraphPanel.add(bmGraph);
        barGraphPanel.add(FXGraph);
        barGraphPanel.add(FZGraph);


        /*Control Panel is north panel containg comboBox*/
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        controlPanel.setBackground(Color.DARK_GRAY);
        JLabel label = new JLabel("Bar: ");
        label.setForeground(Color.WHITE);
        controlPanel.add(label);
        controlPanel.add(barComboBox);

        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(controlPanel, BorderLayout.NORTH);
        sidePanel.add(barGraphPanel, BorderLayout.CENTER);

        return sidePanel;
    }

    @Override
    protected JComponent getMainPanel() {
        return new JScrollPane(createBarGraphPanel());
    }

    @Override
    protected String getPanelTitle() {
        return TITLE;
    }

    @Override
    protected Action getCloseAction() {
        return new closeAction();
    }

    public void update(Observable o, Object arg) {
        if (gui.getGuiPanelModel().isBarGraphModelVisible()) {

            if (barComboBox.getModel().getSize() != truss.getBarModel().size()) {
                this.barComboBox.setModel(new DefaultComboBoxModel(getBarArray()));
            }

            //barComboBox.setSelectedIndex(0);
            if (!barComboBox.hasFocus()) {
                for (int i = 0; i < truss.getBarModel().size(); i++) {
                    if (truss.getBarModel().get(i).isSelected()) {
                        barComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }


    }

    class closeAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            //gui.getGuiPanelModel().setBarGraphModelVisible(false);
            gui.getGuiPanelModel().setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
        }
    }
}


