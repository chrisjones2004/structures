package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Gui.TrussFactory;
import trussoptimizater.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import trussoptimizater.Gui.*;
import trussoptimizater.Truss.Elements.Node;

public class TrussDialogue {

    private JDialog dialog;
    private String picturePath;
    //private JFrame frame;
    private String trussName;
    private JTextField[] textFields;
    private JDialog parentDialog;
    private GUI gui;


    public TrussDialogue( String trussName, String picturePath, JDialog parentDialog, GUI gui) {
        dialog = new JDialog(gui.getFrame(), trussName, true);
        //dialog.setAlwaysOnTop(true);
        this.picturePath = picturePath;
        this.trussName = trussName;
        this.gui = gui;
        this.parentDialog = parentDialog;
    }

    public void createGui() {

        BufferedImage image = null;
        ImagePanel picture = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(picturePath));

            picture = new ImagePanel(image);
        } catch (IOException e) {
            System.err.println("\nTrying to read in image " + e);
        } catch (NullPointerException f) {
            System.err.println("\nTrying to read in image " + f);
        }


        JPanel inputPanel = new JPanel();

        JPanel inputLabelPanel = new JPanel(new GridLayout(4, 1));
        JPanel inputTextBoxPanel = new JPanel(new GridLayout(4, 1));
        JPanel inputUnitsPanel = new JPanel(new GridLayout(4, 1));

        String[] labels = {"Length L1 ", "Length L2 ", "Height H ", "Number Fields "};
        String[] units = {"m ", "m ", "m ", " "};
        textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {

            textFields[i] = new JTextField(10);
            textFields[i].addKeyListener(new KeyListener() {

                public void keyTyped(KeyEvent e) {
                }

                public void keyPressed(KeyEvent e) {
                }

                public void keyReleased(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                        apply();
                    }
                }
            });
            double preferredHeight = textFields[i].getPreferredSize().getHeight();

            JLabel label = new JLabel(labels[i]);
            label.setPreferredSize(new Dimension((int) label.getPreferredSize().getWidth(), (int) preferredHeight));
            inputLabelPanel.add(label);
            inputTextBoxPanel.add(textFields[i]);
            JLabel unit = new JLabel(units[i]);
            unit.setPreferredSize(new Dimension((int) unit.getPreferredSize().getWidth(), (int) preferredHeight));
            inputUnitsPanel.add(unit);
        }

        inputPanel.add(inputLabelPanel);
        inputPanel.add(inputTextBoxPanel);
        inputPanel.add(inputUnitsPanel);

        JButton applyButton = new JButton("  Apply  ");
        applyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                apply();
            }
        });
        JButton cancelButton = new JButton("  Close  ");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();

            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(new JButton("  Help  "));


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(picture, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setResizable(false);
        dialog.add(panel);
        dialog.pack();
        //dialog.setLocationRelativeTo(gui.getFrame());
        dialog.setVisible(true);
    }

    public void apply() {
        //check all textfield contain numeric values
        double L1;
        double L2;
        double H;
        int fields;
        try {
            L1 = Double.parseDouble(textFields[0].getText()) * 100;
            L2 = Double.parseDouble(textFields[1].getText()) * 100;
            H = Double.parseDouble(textFields[2].getText()) * 100;
            fields = Integer.parseInt(textFields[3].getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(dialog, "All field require numeric values\n\"Number of Field\" requies an integer", "Alert", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //calculate node cordinates
        try {
            if (trussName.equals("Pratt Truss")) {
                TrussFactory.createPrattTruss(L1, L2, H, fields, gui.getTruss());
            } else if (trussName.equals("Warren Truss")) {
                TrussFactory.createWarrenTruss(L1, L2, H, fields, gui.getTruss());
            } else if (trussName.equals("Trapezoid Truss")) {
                TrussFactory.createTrapezoidTruss(L1, L2, H, fields,gui.getTruss());
            }
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(gui.getFrame(), "Error in input\n" + ex, "Alert", JOptionPane.ERROR_MESSAGE);
        }
        dialog.dispose();
        parentDialog.dispose();
    }

    public void printNodes(ArrayList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(i + ") x: " + nodes.get(i).getX() + " z: " + nodes.get(i).getZ());
        }
    }
}