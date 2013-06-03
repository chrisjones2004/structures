package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Truss.TrussModel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.Actions.RotateAction;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIModeModel;

public class RotateDialog {

    private TrussModel truss;
    private GUI gui;
    private JDialog dialog;
    private JPanel panel;
    private JTextField angleTextField;
    private JComboBox nodeComboBox;
    private JRadioButton rotateAntiClockwiseRButton;
    private JRadioButton rotateClockwiseRButton;
    private JRadioButton allRButton;
    private JRadioButton selectedRButton;
    private JButton cancelButton;
    private JButton applyButton;
    private JButton helpButton;
    
    

    public RotateDialog(GUI gui, TrussModel truss) {
        this.truss = truss;
        this.gui = gui;
        dialog = new JDialog(gui.getFrame(), "Rotate", true);

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
        panel = new JPanel(new GridLayout(2, 2));

        /*Node Panel*/
        JPanel anchorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        anchorPanel.add(new JLabel("Node: "));
        nodeComboBox = new JComboBox();
        nodeComboBox.setPreferredSize(new Dimension(80,nodeComboBox.getPreferredSize().height));

        anchorPanel.add(nodeComboBox );
        anchorPanel.setBorder(new TitledBorder(lineborder, "Anchor Point"));

        /*Left or Right Panel*/
        JPanel directionPanel = new JPanel();
        directionPanel.setLayout(new BoxLayout(directionPanel, BoxLayout.PAGE_AXIS));
        rotateAntiClockwiseRButton = new JRadioButton("Anti-Clockwise");

        rotateAntiClockwiseRButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        rotateClockwiseRButton = new JRadioButton("Clockwise");
        rotateClockwiseRButton.setSelected(true);
        rotateClockwiseRButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        ButtonGroup directionRButtons = new ButtonGroup();
        directionRButtons.add(rotateAntiClockwiseRButton);
        directionRButtons.add(rotateClockwiseRButton);


        directionPanel.add(rotateClockwiseRButton);
        directionPanel.add(rotateAntiClockwiseRButton);
        directionPanel.setBorder(new TitledBorder(lineborder, "Direction"));

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


        /*Angle Panel*/
        JPanel anglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        angleTextField = new JTextField(5);
        angleTextField.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        anglePanel.add(new JLabel("Degrees: "));
        anglePanel.add(angleTextField);
        anglePanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        anglePanel.setBorder(new TitledBorder(lineborder, "Angle"));


        panel.add(directionPanel);
        panel.add(objectsPanel);
        panel.add(anchorPanel);

        panel.add(anglePanel);

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
                    RotateAction rotateAction = (RotateAction)MyActionMap.ACTION_MAP.get(MyActionMap.ROTATE_ACTION_KEY);

                    double angle = Double.parseDouble(angleTextField.getText());

                    if (rotateAntiClockwiseRButton.isSelected()) {
                        angle = -angle;
                    }
                    if (allRButton.isSelected()) {
                        MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ALL_KEY_BINDING_KEY).actionPerformed(null);
                    }

                    rotateAction.setAngle(Math.toRadians(angle));
                    rotateAction.setNodeAnchor(truss.getNodeModel().get(nodeComboBox.getSelectedIndex()));
                    rotateAction.rotate();
                    dialog.dispose();
                    gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
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