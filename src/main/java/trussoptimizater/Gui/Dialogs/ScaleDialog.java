package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Truss.TrussModel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.Actions.ScaleAction;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIModeModel;

public class ScaleDialog {

    private TrussModel truss;
    private GUI gui;
    private JDialog dialog;
    private JPanel panel;
    private JTextField scaleXTextField;
    private JTextField scaleZTextField;
    //private JTextField nodeTextField;
    private JComboBox nodeComboBox;
    private JRadioButton allRButton;
    private JRadioButton selectedRButton;
    private JButton cancelButton;
    private JButton applyButton;
    private JButton helpButton;

    public ScaleDialog(GUI gui, TrussModel truss) {
        this.truss = truss;
        this.gui = gui;
        dialog = new JDialog(gui.getFrame(), "Scale", true);

    }
    
    //Used for node comboBox
    private String[] getNodeStringArray(){
        String[] nodes = new String[truss.getNodeModel().size()];
        for(int i =0;i<truss.getNodeModel().size();i++){
            nodes[i] = "Node" + truss.getNodeModel().get(i).getNumber();
        }
        return nodes;
    }

    public void createGui() {
        LineBorder lineborder = new LineBorder(Color.black, 1, true);
        panel = new JPanel(new GridLayout(2, 1));

        /*Anchor Panel*/
        JPanel anchorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        anchorPanel.add(new JLabel("Node: "));
        nodeComboBox = new JComboBox();
        nodeComboBox.setPreferredSize(new Dimension(80,nodeComboBox.getPreferredSize().height));

        anchorPanel.add(nodeComboBox );
        anchorPanel.setBorder(new TitledBorder(lineborder, "Anchor Point"));

        /*rotate All or selected Panel*/
        JPanel objectsPanel = new JPanel();
        objectsPanel.setLayout(new BoxLayout(objectsPanel, BoxLayout.PAGE_AXIS));
        allRButton = new JRadioButton("All");
        allRButton.setSelected(true);
        allRButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        selectedRButton = new JRadioButton("Selected");
        selectedRButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        ButtonGroup objectRButtons = new ButtonGroup();
        objectRButtons.add(allRButton);
        objectRButtons.add(selectedRButton);

        objectsPanel.add(allRButton);
        objectsPanel.add(selectedRButton);
        objectsPanel.setBorder(new TitledBorder(lineborder, "Objects"));

        /*Scale Panel*/
        JPanel scalePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        scaleXTextField = new JTextField(5);
        scaleZTextField = new JTextField(5);
        scalePanel.add(new JLabel("x: "));
        scalePanel.add(scaleXTextField);
        scalePanel.add(new JLabel("z: "));
        scalePanel.add(scaleZTextField);

        scalePanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        scalePanel.setBorder(new TitledBorder(lineborder, "Scale"));


        panel.add(anchorPanel);
        panel.add(objectsPanel);
        panel.add(scalePanel);

        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                //gui.setCurrentMode(GUI.SELECT_MODE);
                //gui.getMyGlassPane().setDrawCursor(false);
                gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
            }
        });
        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    ScaleAction scaleAction = (ScaleAction)MyActionMap.ACTION_MAP.get(MyActionMap.SCALE_ACTION_KEY);
                    double scaleX = Double.parseDouble(scaleXTextField.getText());
                    double scaleZ = Double.parseDouble(scaleXTextField.getText());

                    scaleAction.setXScale(scaleX);
                    scaleAction.setZScale(scaleZ);
                    scaleAction.setNodeAnchor(truss.getNodeModel().get(nodeComboBox.getSelectedIndex()));
                    if (allRButton.isSelected()) {
                        MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ALL_KEY_BINDING_KEY).actionPerformed(null);
                    }
                    scaleAction.scale();
                    dialog.dispose();
                    //gui.setCurrentMode(GUI.SELECT_MODE);
                    //gui.getMyGlassPane().setDrawCursor(false);
                    gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
                    /*for(int i=0;i<truss.getBarModel().size();i++){
                        truss.getBarModel().get(i).updateBar();
                    }*/
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input error " + ex, "Alert", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        helpButton = new JButton("Help");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(helpButton);



        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        dialog.setResizable(false);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();
    }

    public JDialog getDialog() {
        this.nodeComboBox.setModel(new DefaultComboBoxModel(getNodeStringArray()));
        return dialog;
    }
}