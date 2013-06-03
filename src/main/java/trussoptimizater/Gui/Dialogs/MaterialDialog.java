package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Truss.TrussModel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.HashMap;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.SwingUtils;

import trussoptimizater.Truss.Materials.Material;
import trussoptimizater.Truss.Materials.Steel;

public class MaterialDialog {
    private JDialog dialog;
    private JButton cancelButton;
    private JButton okButton;
    private JButton defaultButton;
    private JButton helpButton;
    private LineBorder lineBorder = new LineBorder(Color.black, 1, true);
    private Material steel;
    private JTextField[] elasticityTextFields;
    private JTextField[] miscTextFields;

    public MaterialDialog(HashMap<String,Material> materials, GUI gui) {
        steel = materials.get(Steel.MATERIAL_NAME);
        dialog = new JDialog(gui.getFrame(), "Material Definition", true);
    }

    public void createGui() {

        JTabbedPane materialTabPane = new JTabbedPane();
        materialTabPane.add(getSteelTab(), "Steel");
        materialTabPane.add(new JPanel(), "Concrete");
        materialTabPane.add(new JPanel(), "Aluminium");
        materialTabPane.add(new JPanel(), "Timber");
        materialTabPane.add(new JPanel(), "Other");

        dialog.setResizable(false);
        dialog.add(materialTabPane, BorderLayout.CENTER);
        dialog.add(getButtonPanel(), BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();

    }

    private JPanel getSteelTab() {


        String[] elasticityLabels = {"Youngs modulus (E)", "Possions ratio (v)", "Shear modulus (G)"};
        elasticityTextFields = new JTextField[]{new JTextField(10), new JTextField(10), new JTextField(10)};
        String[] elasticityUnits = {"KN/m^2", "", "KN/m^2"};

        JPanel elasticityLabelPanel = SwingUtils.getJLabelColumn(elasticityLabels);
        JPanel elasticityTextFieldPanel = SwingUtils.getComponentColumn(elasticityTextFields);
        JPanel elasticityUnitsPanel = SwingUtils.getJLabelColumn(elasticityUnits);

        JPanel elasticityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        elasticityPanel.add(elasticityLabelPanel);
        elasticityPanel.add(elasticityTextFieldPanel);
        elasticityPanel.add(elasticityUnitsPanel);
        elasticityPanel.setBorder(new TitledBorder(lineBorder, "Elasticity"));

        String[] miscLabels = {"Gravity (g)", "Density (p)", "Yield Strength (fyo)"};
        miscTextFields = new JTextField[]{new JTextField(10), new JTextField(10), new JTextField(10)};
        String[] miscUnits = {"m/s^2", "Kg/m^3", "KN/mm^2"};

        JPanel miscLabelPanel = SwingUtils.getJLabelColumn(miscLabels);
        JPanel miscTextFieldPanel = SwingUtils.getComponentColumn(miscTextFields);
        JPanel miscUnitsPanel = SwingUtils.getJLabelColumn(miscUnits);

        JPanel miscPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        miscPanel.add(miscLabelPanel);
        miscPanel.add(miscTextFieldPanel);
        miscPanel.add(miscUnitsPanel);
        miscPanel.setBorder(new TitledBorder(lineBorder, "Misc"));



        elasticityTextFields[0].setText(Double.toString(steel.getYoungsModulus()));
        elasticityTextFields[1].setText(Double.toString(steel.getPoissonsRatio()));
        elasticityTextFields[2].setText(Double.toString(steel.getShearModulus()));
        elasticityTextFields[1].setEnabled(false);
        elasticityTextFields[2].setEnabled(false);

        miscTextFields[0].setText(Double.toString(steel.getGravity()));
        miscTextFields[1].setText(Double.toString(steel.getDensity()));
        miscTextFields[2].setText(Double.toString(steel.getYieldStrength()));

        //Elasticity Tab
        JPanel steelPanel = new JPanel();
        steelPanel.setLayout(new BoxLayout(steelPanel, BoxLayout.PAGE_AXIS));
        //steelPanel.add(namePanel);
        steelPanel.add(elasticityPanel);
        steelPanel.add(miscPanel);


        return steelPanel;
    }

    private JPanel getButtonPanel() {
        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        okButton = new JButton("Apply");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                apply();
            }
        });

        defaultButton = new JButton("Reset Defaults");
        defaultButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                resetSteelDefaults();
            }
        });

        helpButton = new JButton("Help");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        buttonPanel.add(defaultButton);
        buttonPanel.add(helpButton);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        return buttonPanel;
    }

    public void apply() {
        try {
            this.steel.setYoungsModulus(Double.parseDouble(elasticityTextFields[0].getText()));
            this.steel.setPoissonsRatio(Double.parseDouble(elasticityTextFields[1].getText()));
            this.steel.setShearModulus(Double.parseDouble(elasticityTextFields[2].getText()));

            this.steel.setGravity(Double.parseDouble(miscTextFields[0].getText()));
            this.steel.setDensity(Double.parseDouble(miscTextFields[1].getText()));
            this.steel.setYieldStrength(Double.parseDouble(miscTextFields[2].getText()));
        } catch (NumberFormatException ex) {
            System.out.println("Trying to enter incorrect number format");
            JOptionPane.showMessageDialog(null, "Input error " + ex, "Alert", JOptionPane.ERROR_MESSAGE);
        }

        dialog.dispose();
    }

    public void resetSteelDefaults() {
        elasticityTextFields[0].setText(Double.toString(Steel.DEFAULT_YOUNG_MODULUS));
        elasticityTextFields[1].setText(Double.toString(Steel.DEFAULT_POSSIONS_RATIO));
        elasticityTextFields[2].setText(Double.toString(Steel.DEFAULT_SHEAR_MODULUS));

        miscTextFields[0].setText(Double.toString(Steel.DEFAULT_GRAVITY));
        miscTextFields[1].setText(Double.toString(Steel.DEFAULT_DENSITY));
        miscTextFields[2].setText(Double.toString(Steel.DEFAULT_YIELD_STRENGTH));

        this.steel.setYoungsModulus(Steel.DEFAULT_YOUNG_MODULUS);
        this.steel.setPoissonsRatio(Steel.DEFAULT_POSSIONS_RATIO);
        this.steel.setShearModulus(Steel.DEFAULT_SHEAR_MODULUS);

        this.steel.setGravity(Steel.DEFAULT_GRAVITY);
        this.steel.setDensity(Steel.DEFAULT_DENSITY);
        this.steel.setYieldStrength(Steel.DEFAULT_YIELD_STRENGTH);
    }

    public JDialog getDialog() {
        return dialog;
    }
}
