package trussoptimizater.Gui.Dialogs;


import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import trussoptimizater.Gui.GUIModels.ViewModel;



public class ViewOptionsDialog  extends WindowAdapter {

    private javax.swing.JDialog dialog;
    private JPanel panel;
    private int checkBoxCount = 10;
    private boolean[] checkBoxesSelectArray;
    private JCheckBox[] checkBoxArray;
    //private GUI gui;
    private ViewModel viewModel;

    public ViewOptionsDialog(ViewModel viewModel, JFrame parentFrame) {//
        //this.gui = gui;
        this.viewModel = viewModel;
        checkBoxesSelectArray = new boolean[checkBoxCount];
        checkBoxArray = new JCheckBox[checkBoxCount];
        dialog = new javax.swing.JDialog(parentFrame, "Display Options", false);
        dialog.addWindowListener(this);
        //dialog.setAlwaysOnTop(true);
    }

    public void createGui() {

        LineBorder lineborder = new LineBorder(Color.black, 1, true);


        JCheckBox viewNodes = new JCheckBox("Nodes");
        checkBoxArray[0] = viewNodes;
        viewNodes.setSelected(viewModel.isNodesVisible());
        viewNodes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setNodesVisible(jcbm.isSelected());
                viewModel.setNodeNumbersVisible(jcbm.isSelected());
                checkBoxArray[1].setEnabled(jcbm.isSelected());
                checkBoxArray[1].setSelected(jcbm.isSelected());
            }
        });


        JCheckBox viewNodeNumber = new JCheckBox("Node Numbers");
        checkBoxArray[1] = viewNodeNumber;
        viewNodeNumber.setSelected(viewModel.isNodeNumbersVisible());
        viewNodeNumber.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setNodeNumbersVisible(jcbm.isSelected());
            }
        });


        JPanel nodePanel = new JPanel();
        nodePanel.setLayout(new BoxLayout(nodePanel, BoxLayout.PAGE_AXIS));
        nodePanel.add(viewNodes);
        nodePanel.add(viewNodeNumber);
        nodePanel.setBorder(new TitledBorder(lineborder, "Node Options"));


        JCheckBox viewBars = new JCheckBox("Bars");
        checkBoxArray[2] = viewBars;
        viewBars.setSelected(viewModel.isBarsVisible());
        viewBars.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setBarsVisible(jcbm.isSelected());
                viewModel.setBarNumbersVisible(jcbm.isSelected());
                viewModel.setBarAxisVisible(jcbm.isSelected());
                checkBoxArray[3].setEnabled(jcbm.isSelected());
                checkBoxArray[3].setSelected(jcbm.isSelected());
                checkBoxArray[4].setEnabled(jcbm.isSelected());
                checkBoxArray[4].setSelected(jcbm.isSelected());
            }
        });

        JCheckBox viewBarNumber = new JCheckBox("Bar Numbers");
        checkBoxArray[3] = viewBarNumber;
        viewBarNumber.setSelected(viewModel.isBarNumbersVisible());
        viewBarNumber.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setBarNumbersVisible(jcbm.isSelected());
                
            }
        });

        JCheckBox viewBarAxis = new JCheckBox("Bar Axis");
        checkBoxArray[4] = viewBarAxis;
        viewBarAxis.setSelected(viewModel.isBarAxisVisible());
        viewBarAxis.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setBarAxisVisible(jcbm.isSelected());
            }
        });
        

        JPanel barPanel = new JPanel();
        barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.PAGE_AXIS));
        barPanel.add(viewBars);
        barPanel.add(viewBarNumber);
        barPanel.add(viewBarAxis);
        barPanel.setBorder(new TitledBorder(lineborder, "Bar Options"));

        JCheckBox viewSupports = new JCheckBox("Supports");
        checkBoxArray[5] = viewSupports;
        viewSupports.setSelected(viewModel.isSupportsVisible());
        viewSupports.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setSupportsVisible(jcbm.isSelected());
                viewModel.setSupportNumbersVisible(jcbm.isSelected());
                checkBoxArray[6].setEnabled(jcbm.isSelected());
                checkBoxArray[6].setSelected(jcbm.isSelected());
            }
        });

        JCheckBox viewSupportNumber = new JCheckBox("Support Numbers");
        checkBoxArray[6] = viewSupportNumber;
        viewSupportNumber.setSelected(viewModel.isSupportsVisible());
        viewSupportNumber.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setSupportNumbersVisible(jcbm.isSelected());
                
            }
        });

        JPanel supportPanel = new JPanel();
        supportPanel.setLayout(new BoxLayout(supportPanel, BoxLayout.PAGE_AXIS));
        supportPanel.add(viewSupports);
        supportPanel.add(viewSupportNumber);
        supportPanel.setBorder(new TitledBorder(lineborder, "Support Options"));

        JCheckBox viewLoads = new JCheckBox("Loads");
        checkBoxArray[7] = viewLoads;
        viewLoads.setSelected(viewModel.isSupportsVisible());
        viewLoads.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setLoadsVisible(jcbm.isSelected());
                viewModel.setLoadNumbersVisible(jcbm.isSelected());
                viewModel.setLoadValuesVisible(jcbm.isSelected());
                checkBoxArray[8].setEnabled(jcbm.isSelected());
                checkBoxArray[9].setEnabled(jcbm.isSelected());
                checkBoxArray[8].setSelected(jcbm.isSelected());
                checkBoxArray[9].setSelected(jcbm.isSelected());
            }
        });

        JCheckBox viewLoadNumber = new JCheckBox("Load Numbers");
        checkBoxArray[8] = viewLoadNumber;
        viewLoadNumber.setSelected(viewModel.isLoadsVisible());
        viewLoadNumber.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setLoadNumbersVisible(jcbm.isSelected());
            }
        });

        JCheckBox viewLoadvalue = new JCheckBox("Load Values");
        checkBoxArray[9] = viewLoadvalue;
        viewLoadvalue.setSelected(viewModel.isLoadsVisible());
        viewLoadvalue.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                viewModel.setLoadValuesVisible(jcbm.isSelected());
            }
        });

        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.PAGE_AXIS));
        loadPanel.add(viewLoads);
        loadPanel.add(viewLoadNumber);
        loadPanel.add(viewLoadvalue);
        loadPanel.setBorder(new TitledBorder(lineborder, "Load Options"));

        panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(nodePanel);
        panel.add(supportPanel);
        panel.add(barPanel);
        panel.add(loadPanel);
        


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cancel(e);
            }
        });
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(buttonPanel);


        dialog.setResizable(false);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonContainer, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
    //dialog.setVisible(true);
    }

    public void cancel(AWTEvent e) {
        for (int i = 0; i < checkBoxCount; i++) {
            checkBoxArray[i].setSelected(checkBoxesSelectArray[i]);
            ActionEvent event = new ActionEvent(checkBoxArray[i],e.getID(),e.toString());
            checkBoxArray[i].getActionListeners()[0].actionPerformed(event);
        }
        //gui.getView().repaint();
        dialog.setVisible(false);
    }

    public void showGui() {
        for (int i = 0; i < this.checkBoxCount; i++) {
            checkBoxesSelectArray[i] = checkBoxArray[i].isSelected();
        }
        this.dialog.setVisible(true);
    }

    public JDialog getDialog() {
        return dialog;
    }
    @Override
    public void windowClosed(WindowEvent e) {
        cancel(e) ;
    }
}//end of view gui class