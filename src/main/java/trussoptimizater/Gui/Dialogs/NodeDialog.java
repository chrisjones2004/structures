package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Truss.TrussModel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIModeModel;
import trussoptimizater.Truss.Elements.Node;

public class NodeDialog{

    private TrussModel truss;
    private GUI gui;
    private JDialog dialog;
    private JTextField xTextField;
    private JTextField zTextField;
    private JPanel panel;
    private JButton cancelButton;
    private JButton addButton;
    private JButton helpButton;

    public NodeDialog(GUI gui, TrussModel truss) {
        this.truss = truss;
        this.gui = gui;
        dialog = new JDialog(gui.getFrame(), "Create Node", false);
    }

    public void createGui() {
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        xTextField = new JTextField(5);
        xTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addNode();
            }
        });
        zTextField = new JTextField(5);
        zTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addNode();
            }
        });

        panel.add(new JLabel("x:"));
        panel.add(xTextField);
        panel.add(new JLabel("m    "));
        panel.add(new JLabel("z:"));
        panel.add(zTextField);
        panel.add(new JLabel("m"));


        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
            }
        });
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addNode();
            }
        });

        helpButton = new JButton("Help");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);
        buttonPanel.add(helpButton);



        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        LineBorder lineborder = new LineBorder(Color.black, 1, true);
        panel.setBorder(new TitledBorder(lineborder, "Node position"));
        dialog.setResizable(false);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();
        //dialog.setVisible(true);
    }




    public void addNode() {
        try {

            double x = java.lang.Double.parseDouble(xTextField.getText());
            double z = java.lang.Double.parseDouble(zTextField.getText());
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1,x * 100, z * 100));
        } catch (java.lang.NumberFormatException nfe) {
            System.out.println("Node Number Format Exception " + nfe);
        }
        xTextField.setText("");
        zTextField.setText("");
        zTextField.transferFocus();
        cancelButton.transferFocus();
        addButton.transferFocus();
        helpButton.transferFocus();

    }



    public JDialog getDialog() {
        return dialog;
    }
}
