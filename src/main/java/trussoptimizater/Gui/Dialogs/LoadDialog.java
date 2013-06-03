package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Truss.TrussModel;
import java.util.Observable;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Elements.*;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.Events.TrussEvent;

public class LoadDialog implements java.util.Observer {

    private TrussModel truss;
    private JDialog dialog;
    private JTextField loadTextField;
    
    private JButton cancelButton;
    private JButton addButton;
    private JTextPane textPane;
    private ButtonGroup radioButtons;
    private LineBorder lineborder = new LineBorder(Color.black, 1, true);

    public LoadDialog(GUI gui, TrussModel truss) {
        this.truss = truss;
        dialog = new JDialog(gui.getFrame(), "Point Load", false);
        truss.getNodeModel().addObserver(this);
    }

    public JPanel getDirectionPanel(){
        JRadioButton verticalButton = new JRadioButton(Load.VERTICAL_LOAD);
        verticalButton.setSelected(true);
        verticalButton.setActionCommand(Load.VERTICAL_LOAD);

        JRadioButton horizontalButton = new JRadioButton(Load.HORIZOANTAL_LOAD);
        horizontalButton.setActionCommand(Load.HORIZOANTAL_LOAD);

        radioButtons = new ButtonGroup();
        radioButtons.add(verticalButton);
        radioButtons.add(horizontalButton);

        JPanel directionPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        directionPanel.add(verticalButton);
        directionPanel.add(horizontalButton);
        directionPanel.setBorder(new TitledBorder(lineborder, "Direction"));
        return directionPanel;
    }

    public JPanel getLoadPanel(){
        loadTextField = new JTextField(5);
        loadTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addLoad();
            }
        });

        JPanel loadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        loadPanel.add(loadTextField);
        loadPanel.add(new JLabel("KN"));
        loadPanel.setBorder(new TitledBorder(lineborder, "Load"));
        return loadPanel;

    }

    public JPanel getButtonPanel(){

        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addLoad();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);
        buttonPanel.add(new JButton("Help"));
        return buttonPanel;
    }

    public JScrollPane getTextPanePanel(){
        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(80, 40));
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(new TitledBorder(lineborder, "Selected Nodes"));
        return scrollPane;
    }



    public void createGui() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(getDirectionPanel());
        panel.add(getLoadPanel());
        //panel.add(getTextPanePanel());
        getTextPanePanel();
        panel.add(textPane);
        panel.add(getButtonPanel());

        dialog.setResizable(false);
        dialog.add(panel);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();
        //dialog.setVisible(true);
    }

    public void updateTextPane() {
        textPane.setText("");
        String selectedNodes = "";
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            //System.out.println("Node "+i+ " is selected "+truss.getNodeModel().get(i).isSelected());
            if (truss.getNodeModel().get(i).isSelected()) {
                selectedNodes += Integer.toString(truss.getNodeModel().get(i).getNumber()) + ",";
            }
        }
        textPane.setText(selectedNodes);
    }

    public void addLoad() {
        try {

            for (int i = 0; i < truss.getNodeModel().size(); i++) {
                if (!truss.getNodeModel().get(i).isSelected()) {
                    continue;
                }

                double load = Double.parseDouble(loadTextField.getText());
                String direction;
                if (radioButtons.getSelection().getActionCommand().equals(Load.VERTICAL_LOAD)) {
                    direction = Load.VERTICAL_LOAD;
                } else {
                    direction = Load.HORIZOANTAL_LOAD;
                }
                truss.getLoadModel().add(new Load(truss.getLoadModel().size() + 1, truss.getNodeModel().get(i),load, direction, Load.USER_DEFINED));
            }
            //gui.getView().repaint();
        } catch (java.lang.NumberFormatException nfe) {
            System.out.println("Load Error " + nfe);
        }
        loadTextField.setText("");
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof TrussEvent) {
            updateTextPane();
        }
    }
}
