package trussoptimizater.Gui.Dialogs;

import java.util.Observable;
import trussoptimizater.Truss.TrussModel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;


import trussoptimizater.Truss.Optimize.OptimizeModel;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIModeModel;
import trussoptimizater.Truss.Optimize.GAModel;
import trussoptimizater.Truss.Optimize.MirrorLine;
import trussoptimizater.Truss.Sections.CHS;
import trussoptimizater.Gui.SwingUtils;
import trussoptimizater.Truss.SectionLibrary;

//should have recommendations in 
//need to add units
public class OptimzeOptionsDialog implements Observer {

    private TrussModel truss;
    /**
     * This boolean is used so that this dialog is not updated when the apply method is being exectued 
     */
    private boolean update = true;
    private GUI gui;
    private JDialog dialog;
    //private JPanel panel;
    /*Buttons*/
    private JButton cancelButton;
    private JButton applyButton;
    private JButton helpButton;
    private JButton defaultButton;
    /* options*/
    private JTextField[] symmetryTextFields = new JTextField[4];
    private JCheckBox symmetryCheckBox;
    private JTextField[] lengthTextFields = new JTextField[2];
    private final String[] lengthTextFieldLabels = {"Min Node Spacing: ", "Max Bar Length: "};
    private JTextField nodeTextField = new JTextField(10);
    private JSpinner[] nodeJSpinners = new JSpinner[2];
    private String[] nodeTextFieldLabels = {"Nodal Grid: ", "Max Nodal X Displacment: ", "Max Nodal Y Displacment: "};
    private JTextField[] deflectionTextFields = new JTextField[1];
    private String[] deflectionTextFieldLabels = {"Max Deflection: "};
    private JCheckBox[] nodeCheckBoxes = new JCheckBox[6];
    private String[] nodeCheckBoxLabels = {"Free Nodes X-cords: ",
        "Free Nodes Y-cords: ",
        "Loaded Nodes X-cords: ",
        "Loaded Nodes Y-cords: ",
        "Supported Nodes X-cords: ",
        "Supported Nodes Y-cords: "
    };
    private JCheckBox[] sectionCheckBoxes = new JCheckBox[1];
    private String[] sectionCheckBoxLabels = {"Sections: "};
    private JComboBox[] sectionComboBoxes = {new JComboBox(), new JComboBox()};
    private String[] sectionCombokBoxLabels = {"Min Section: ", "Max Section: "};
    private JTextField[] gaTextFields = new JTextField[2];
    private String[] gaTextFieldLabels = {"Population Size: ", "No. Evolutions: "};
    private String[] penaltyTextFieldLabels = {"Deflection: ", "Weight: ", "Stress: ", "Lengths: ", "Compression: "};
    private JTextField[] penaltyTextFields = new JTextField[5];
    private LineBorder lineborder = new LineBorder(Color.black, 1, true);

    public OptimzeOptionsDialog(GUI gui, TrussModel truss) {
        this.truss = truss;
        this.gui = gui;
        dialog = new JDialog(gui.getFrame(), "Optimization Options", true);
        truss.getOptimizeMethods().getGAOptimizer().getGAModel().addObserver(this);
    }

    public void createGui() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        tabbedPane.add("General", getGeneralPanel());
        tabbedPane.add("Methods", this.getMethodsPanel());

        JPanel panel = new JPanel();
        panel.add(tabbedPane);
        dialog.setResizable(false);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(getButtonPanel(), BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();

    }

    public JPanel getButtonPanel() {
        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
            }
        });
        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                apply();
            }
        });

        defaultButton = new JButton("Default Options");
        defaultButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                resetDefaultOptions();
            }
        });

        helpButton = new JButton("Help");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(cancelButton);
        buttonPanel.add(defaultButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(helpButton);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        return buttonPanel;
    }

    public void initGAMethodComponents() {
        gaTextFields[0].setText(Integer.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getPopulationSize()));
        gaTextFields[1].setText(Integer.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxAllowedEvolutions()));

        penaltyTextFields[0].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getDeflectionPenatly()));
        penaltyTextFields[1].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getWeightPenalty()));
        penaltyTextFields[2].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getStressPenalty()));
        penaltyTextFields[3].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getLengthPenatly()));
        penaltyTextFields[4].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getCompressionPenatly()));
    }

    /**
     * Initlize all components in the General Tab in optimization options. Set all values to those in the model
     */
    public void initGeneralComponents() {
        /* Tab*/
        /*Section Panel*/
        sectionCheckBoxes[0].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSections());
        sectionComboBoxes[0].setSelectedIndex(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMinSectionIndex());
        sectionComboBoxes[1].setSelectedIndex(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxSectionIndex());

        /*Bar Panel*/
        lengthTextFields[0].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMinNodeSpacing()));
        lengthTextFields[1].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxBarLength()));

        /*Node Panel*/
        nodeTextField.setText(Integer.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid()));

        //Node checkboxes
        nodeCheckBoxes[0].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeFreeNodesX());
        nodeCheckBoxes[1].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeFreeNodesY());
        nodeCheckBoxes[2].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeLoadedNodesX());
        nodeCheckBoxes[3].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeLoadedNodesY());
        nodeCheckBoxes[4].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSupportedNodesX());
        nodeCheckBoxes[5].setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSupportedNodesY());

        /*Deflections Panel*/
        deflectionTextFields[0].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxDeflection()));
    }

    private ArrayList<String> getCHSSectionNames() {
        ArrayList<String> sectionNames = new ArrayList<String>();
        for (int i = 0; i < SectionLibrary.SECTIONS.size(); i++) {
            if (SectionLibrary.SECTIONS.get(i) instanceof CHS) {
                sectionNames.add(SectionLibrary.SECTIONS.get(i).getName());
            }
        }
        return sectionNames;
    }

    public JPanel getMethodsPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(getGATab(), "GA");
        JPanel methodsPanel = new JPanel(new BorderLayout());
        methodsPanel.add(tabbedPane, BorderLayout.CENTER);
        initGAMethodComponents();
        return methodsPanel;
    }

    public JPanel getGeneralPanel() {

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(getSectionPanel(), "Sections");
        tabbedPane.add(getBarPanel(), "Lengths");
        tabbedPane.add(getNodePanel(), "Nodes");
        tabbedPane.add(getDeflectionPanel(), "Deflections");
        tabbedPane.add(getSymmetryPanel(), "Symmetry");

        JPanel generalPanel = new JPanel(new BorderLayout());
        generalPanel.add(tabbedPane, BorderLayout.CENTER);
        this.initGeneralComponents();
        return generalPanel;
    }

    private int round(double value, Integer significance) {
        if (value % significance == 0) {
            return (int) value;
        }
        int floor = (int) ((value / significance)) * significance;
        return floor;
    }

    public JPanel getNodePanel() {

        //init textboxes
        nodeTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int grid = Integer.parseInt(nodeTextField.getText());
                nodeJSpinners[0].setModel(new SpinnerNumberModel(
                        round(Integer.parseInt(nodeJSpinners[0].getValue().toString()), grid),
                        0,
                        1000 * grid,
                        grid));
                nodeJSpinners[1].setModel(new SpinnerNumberModel(
                        round(Integer.parseInt(nodeJSpinners[1].getValue().toString()), grid),
                        0,
                        1000 * grid,
                        grid));
            }
        });

        nodeJSpinners[0] = new JSpinner(new SpinnerNumberModel(
                truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalXDisplacement(),
                0,
                1000 * truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid(),
                truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid()));

        //System.out.println("nodal grid is "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid());
        nodeJSpinners[1] = new JSpinner(new SpinnerNumberModel(
                truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalYDisplacement(),
                0,
                1000 * truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid(),//
                truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid()));

        /*nodeJSpinners[1] = new JSpinner(new SpinnerNumberModel(
        truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalYDisplacement(),
        0,
        1000,//
        0));*/


        JPanel displacementPanel = new JPanel();
        displacementPanel.setBorder(new TitledBorder(lineborder, "Displacements"));

        //JPanel columnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        displacementPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        displacementPanel.add(SwingUtils.getJLabelColumn(nodeTextFieldLabels));
        displacementPanel.add(SwingUtils.getComponentColumn(new JComponent[]{nodeTextField, nodeJSpinners[0], nodeJSpinners[1]}));
        displacementPanel.add(SwingUtils.getJLabelColumn(new String[]{"cm", "cm", "cm"}));
        //nodePanel.add(columnPanel);

        JPanel nodeToOptimizePanel = new JPanel();
        nodeToOptimizePanel.setLayout(new BoxLayout(nodeToOptimizePanel, BoxLayout.PAGE_AXIS));
        nodeToOptimizePanel.setBorder(new TitledBorder(lineborder, "Nodes to Optimize"));

        for (int i = 0; i < this.nodeCheckBoxes.length; i++) {
            nodeCheckBoxes[i] = new JCheckBox(nodeCheckBoxLabels[i]);
            nodeCheckBoxes[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
            nodeToOptimizePanel.add(nodeCheckBoxes[i]);
        }


        JPanel nodePanel = new JPanel(new BorderLayout());
        nodePanel.add(displacementPanel, BorderLayout.NORTH);
        nodePanel.add(nodeToOptimizePanel, BorderLayout.CENTER);

        return nodePanel;
    }

    public JPanel getSymmetryPanel() {
        symmetryCheckBox = new JCheckBox("Keep Symmetry");
        symmetryCheckBox.setSelected(truss.getOptimizeMethods().getGAOptimizer().getGAModel().isKeepSymmetry());
        symmetryCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        symmetryCheckBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                for (int i = 0; i < symmetryTextFields.length; i++) {
                    symmetryTextFields[i].setEnabled(cb.isSelected());
                }
            }
        });


        for (int i = 0; i < symmetryTextFields.length; i++) {
            symmetryTextFields[i] = new JTextField(8);
            symmetryTextFields[i].setEnabled(symmetryCheckBox.isSelected());
        }

        symmetryTextFields[0].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getSymmetryAxis().getX1() / 100));
        symmetryTextFields[1].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getSymmetryAxis().getX2() / 100));
        symmetryTextFields[2].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getSymmetryAxis().getY1() / 100));
        symmetryTextFields[3].setText(Double.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getSymmetryAxis().getY2() / 100));

        JPanel pointPanel = new JPanel(new GridLayout(2, 1));
        JLabel X1Label = new JLabel("X1:");
        X1Label.setPreferredSize(new Dimension((int) X1Label.getPreferredSize().getWidth(), (int) symmetryTextFields[0].getPreferredSize().getHeight()));
        pointPanel.add(X1Label);

        JLabel X2Label = new JLabel("X2:");
        X2Label.setPreferredSize(new Dimension((int) X1Label.getPreferredSize().getWidth(), (int) symmetryTextFields[0].getPreferredSize().getHeight()));
        pointPanel.add(X2Label);

        JPanel xPanel = new JPanel(new GridLayout(2, 1));
        xPanel.add(symmetryTextFields[0]);
        xPanel.add(symmetryTextFields[1]);

        JPanel unit1Panel = new JPanel(new GridLayout(2, 1));
        JLabel Z1Label = new JLabel("Z1:");
        Z1Label.setPreferredSize(new Dimension((int) X1Label.getPreferredSize().getWidth(), (int) symmetryTextFields[0].getPreferredSize().getHeight()));
        unit1Panel.add(Z1Label);
        JLabel Z2Label = new JLabel("Z2:");
        Z2Label.setPreferredSize(new Dimension((int) X1Label.getPreferredSize().getWidth(), (int) symmetryTextFields[0].getPreferredSize().getHeight()));
        unit1Panel.add(Z2Label);

        JPanel yPanel = new JPanel(new GridLayout(2, 1));
        yPanel.add(symmetryTextFields[2]);
        yPanel.add(symmetryTextFields[3]);

        JPanel unit2Panel = new JPanel(new GridLayout(2, 1));
        JLabel unit1Label = new JLabel("m");
        unit1Label.setPreferredSize(new Dimension((int) X1Label.getPreferredSize().getWidth(), (int) symmetryTextFields[0].getPreferredSize().getHeight()));
        unit2Panel.add(unit1Label);
        JLabel unit2Label = new JLabel("m");
        unit2Label.setPreferredSize(new Dimension((int) X1Label.getPreferredSize().getWidth(), (int) symmetryTextFields[0].getPreferredSize().getHeight()));
        unit2Panel.add(unit2Label);


        JPanel axisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        axisPanel.setBorder(new TitledBorder(this.lineborder, "Symmetry axis"));
        axisPanel.add(pointPanel);
        axisPanel.add(pointPanel);
        axisPanel.add(xPanel);
        axisPanel.add(unit1Panel);
        axisPanel.add(yPanel);
        axisPanel.add(unit2Panel);
        axisPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JPanel symmetryPanel = new JPanel();
        symmetryPanel.setLayout(new BoxLayout(symmetryPanel, BoxLayout.PAGE_AXIS));
        symmetryPanel.add(symmetryCheckBox);
        symmetryPanel.add(axisPanel);

        return symmetryPanel;
    }

    public JPanel getDeflectionPanel() {
        JPanel deflectionPanel = new JPanel();
        deflectionPanel.setLayout(new BoxLayout(deflectionPanel, BoxLayout.PAGE_AXIS));
        /*Deflection Panel*/
        for (int i = 0; i < deflectionTextFields.length; i++) {
            deflectionTextFields[i] = new JTextField(10);
        }
        JPanel columnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        columnPanel.add(SwingUtils.getJLabelColumn(deflectionTextFieldLabels));
        columnPanel.add(SwingUtils.getComponentColumn(deflectionTextFields));
        columnPanel.add(SwingUtils.getJLabelColumn(new String[]{"mm"}));

        deflectionPanel.add(columnPanel);
        deflectionPanel.setBorder(new TitledBorder(lineborder, "Deflection Options"));


        return deflectionPanel;
    }

    public JPanel getBarPanel() {
        JPanel barPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        //add labels and textboxes
        for (int i = 0; i < lengthTextFields.length; i++) {
            lengthTextFields[i] = new JTextField(10);
        }
        barPanel.add(SwingUtils.getJLabelColumn(lengthTextFieldLabels));
        barPanel.add(SwingUtils.getComponentColumn(lengthTextFields));
        barPanel.add(SwingUtils.getJLabelColumn(new String[]{"m", "m"}));
        barPanel.setBorder(new TitledBorder(lineborder, "Length Options"));
        return barPanel;

    }

    public JPanel getSectionPanel() {
        JPanel sectionsPanel = new JPanel(); //new GridLayout(SectionCheckBoxes.length, 1)
        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.PAGE_AXIS));
        /*Section Panel*/
        sectionComboBoxes[0].setModel(new DefaultComboBoxModel(getCHSSectionNames().toArray()));
        sectionComboBoxes[1].setModel(new DefaultComboBoxModel(getCHSSectionNames().toArray()));

        for (int i = 0; i < sectionCheckBoxes.length; i++) {
            sectionCheckBoxes[i] = new JCheckBox(sectionCheckBoxLabels[i]);
            sectionCheckBoxes[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
            sectionsPanel.add(sectionCheckBoxes[i]);
        }

        sectionCheckBoxes[0].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                for (int i = 0; i < sectionComboBoxes.length; i++) {
                    sectionComboBoxes[i].setEnabled(cb.isSelected());
                }
            }
        });

        JPanel sectionColumnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sectionColumnPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        sectionColumnPanel.add(SwingUtils.getJLabelColumn(sectionCombokBoxLabels));
        sectionColumnPanel.add(SwingUtils.getComponentColumn(sectionComboBoxes));
        sectionsPanel.add(sectionColumnPanel);

        sectionsPanel.add(sectionColumnPanel);
        sectionsPanel.setBorder(new TitledBorder(lineborder, "Section Options"));
        return sectionsPanel;

    }

    public JPanel getGATab() {

        //add labels and textboxes
        for (int i = 0; i < gaTextFields.length; i++) {
            gaTextFields[i] = new JTextField(10);
        }

        JPanel geneticsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        geneticsPanel.add(SwingUtils.getJLabelColumn(gaTextFieldLabels));
        geneticsPanel.add(SwingUtils.getComponentColumn(gaTextFields));
        geneticsPanel.setBorder(new TitledBorder(lineborder, "Genetics"));


        for (int i = 0; i < penaltyTextFields.length; i++) {
            penaltyTextFields[i] = new JTextField(10);
        }
        JPanel penaltyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        penaltyPanel.add(SwingUtils.getJLabelColumn(this.penaltyTextFieldLabels));
        penaltyPanel.add(SwingUtils.getComponentColumn(penaltyTextFields));
        penaltyPanel.setBorder(new TitledBorder(lineborder, "Penalties"));


        JPanel gaPanel = new JPanel(new BorderLayout());
        gaPanel.add(geneticsPanel, BorderLayout.NORTH);
        gaPanel.add(penaltyPanel, BorderLayout.CENTER);

        return gaPanel;
    }

    public void resetDefaultOptions() {
        //GA Tab
        gaTextFields[0].setText(Integer.toString(GAModel.DEFAULT_POPULATION_SIZE));
        gaTextFields[1].setText(Integer.toString(GAModel.DEFAULT_MAX_ALLLOWED_EVOLUTIIONS));
        //{"Deflection","Weight","Stress","Bar Lengths","Compression"};
        penaltyTextFields[0].setText(Double.toString(GAModel.DEFAULT_DEFLECTION_PENALTY));
        penaltyTextFields[1].setText(Double.toString(GAModel.DEFAULT_WEIGHT_PENALTY));
        penaltyTextFields[2].setText(Double.toString(GAModel.DEFAULT_STRESS_PENALTY));
        penaltyTextFields[3].setText(Double.toString(GAModel.DEFAULT_LENGTH_PENALTY));
        penaltyTextFields[4].setText(Double.toString(GAModel.DEAFULT_COMPRESSION_PENALTY));

        /* Tab*/
        /*Section Panel*/
        sectionCheckBoxes[0].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_SECTIONS);
        sectionComboBoxes[0].setSelectedIndex(OptimizeModel.DEFAULT_MIN_SECTION_INDEX);
        sectionComboBoxes[1].setSelectedIndex(OptimizeModel.DEFAULT_MAX_SECTION_INDEX);

        /*Bar Panel*/
        lengthTextFields[0].setText(Double.toString(OptimizeModel.DEFAULT_MIN_NODE_SPACING));
        lengthTextFields[1].setText(Double.toString(OptimizeModel.DEFAULT_MAX_BAR_LENGTH));

        /*Node Panel*/
        nodeTextField.setText(Integer.toString(OptimizeModel.DEFAULT_NODAL_GRID));
        nodeJSpinners[0].setValue(OptimizeModel.DEFAULT_MAX_NODAL_X_DISPLACEMENT);
        nodeJSpinners[1].setValue(OptimizeModel.DEFAULT_MAX_NODAL_Y_DISPLACEMENT);

        //Node checkboxes
        nodeCheckBoxes[0].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_FREE_NODES_X);
        nodeCheckBoxes[1].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_FREE_NODES_Y);
        nodeCheckBoxes[2].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_LOADED_NODES_X);
        nodeCheckBoxes[3].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_LOADED_NODES_Y);
        nodeCheckBoxes[4].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_SUPPORTED_NODES_X);
        nodeCheckBoxes[5].setSelected(OptimizeModel.DEFAULT_OPTIMIZE_SUPPORTED_NODES_Y);

        /*Deflections Panel*/
        deflectionTextFields[0].setText(Double.toString(OptimizeModel.DEFAULT_MAX_DEFLCETION));

        //Symmetry Panel
        this.symmetryCheckBox.setSelected(OptimizeModel.DEFAULT_KEEP_SYMMETRY);
        for (int j = 0; j < symmetryTextFields.length; j++) {
            symmetryTextFields[j].setEnabled(OptimizeModel.DEFAULT_KEEP_SYMMETRY);
        }

    }

    public void apply() {
        update = false;
        try {
            /*GA Tab*/
            int popSize = Integer.parseInt(gaTextFields[0].getText());
            int evolutions = Integer.parseInt(gaTextFields[1].getText());

            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setpopulationSize(popSize);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMaxAllowedEvolutions(evolutions);

            double deflectionPenalty = Double.parseDouble(penaltyTextFields[0].getText());
            double weightPenalty = Double.parseDouble(penaltyTextFields[1].getText());
            double stressPenalty = Double.parseDouble(penaltyTextFields[2].getText());
            double barLengthPenalty = Double.parseDouble(penaltyTextFields[3].getText());
            double compressionPenalty = Double.parseDouble(penaltyTextFields[4].getText());
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setCompressionPenatly(compressionPenalty);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setWeightPenalty(weightPenalty);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setStressPenalty(stressPenalty);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setLengthPenatly(barLengthPenalty);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setDeflectionPenatly(deflectionPenalty);

            /*Length  Tab*/
            double minBarLength = Double.parseDouble(lengthTextFields[0].getText());
            double maxBarLength = Double.parseDouble(lengthTextFields[1].getText());
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMinNodeSpacing(minBarLength);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMaxBarLength(maxBarLength);

            /*Section  Tab*/
            boolean sections = sectionCheckBoxes[0].isSelected();
            int minSectionIndex = sectionComboBoxes[0].getSelectedIndex();
            int maxSectionIndex = sectionComboBoxes[1].getSelectedIndex();
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeSections(sections);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMinSectionIndex(minSectionIndex);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMaxSectionIndex(maxSectionIndex);

            /*Section Deflection Tab*/
            double maxDeflection = Double.parseDouble(deflectionTextFields[0].getText());
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMaxDeflection(maxDeflection);


            /*Node Tab*/
            int nodalGrid = Integer.parseInt(nodeTextField.getText());
            int maxNodalXDisplacement = Integer.parseInt(nodeJSpinners[0].getValue().toString());
            int maxNodalYDisplacement = Integer.parseInt(nodeJSpinners[1].getValue().toString());

            /*System.out.println("applying");
            System.out.println("Nodal grid "+nodalGrid);
            System.out.println("X dis "+maxNodalXDisplacement);
            System.out.println("y DIs "+maxNodalYDisplacement);*/

            boolean FreeNodesXCords = nodeCheckBoxes[0].isSelected();
            boolean FreeNodesYCords = nodeCheckBoxes[1].isSelected();
            boolean LoadedNodesXCords = nodeCheckBoxes[2].isSelected();
            boolean LoadedNodesYCords = nodeCheckBoxes[3].isSelected();
            boolean SupportedNodesXCords = nodeCheckBoxes[4].isSelected();
            boolean SupportedNodesYCords = nodeCheckBoxes[5].isSelected();
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setNodalGrid(nodalGrid);

            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMaxNodalXDisplacement(maxNodalXDisplacement);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setMaxNodalYDisplacement(maxNodalYDisplacement);

            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeFreeNodesX(FreeNodesXCords);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeFreeNodesY(FreeNodesYCords);

            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeLoadedNodesX(LoadedNodesXCords);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeLoadedNodesY(LoadedNodesYCords);

            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeSupportedNodesX(SupportedNodesXCords);
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setOptimizeSupportedNodesY(SupportedNodesYCords);

            //Symmetry Tab
            boolean keepSymmetry = this.symmetryCheckBox.isSelected();
            truss.getOptimizeMethods().getGAOptimizer().getGAModel().setKeepSymmetry(keepSymmetry);
            if (keepSymmetry) {
                double x1 = Double.parseDouble(this.symmetryTextFields[0].getText()) * 100;
                double x2 = Double.parseDouble(this.symmetryTextFields[1].getText()) * 100;
                double z1 = Double.parseDouble(this.symmetryTextFields[2].getText()) * 100;
                double z2 = Double.parseDouble(this.symmetryTextFields[3].getText()) * 100;
                MirrorLine symmetryAxis = new MirrorLine(x1, z1, x2, z2);
                truss.getOptimizeMethods().getGAOptimizer().getGAModel().setSymmetryAxis(symmetryAxis);
            }

            dialog.dispose();
            gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui.getFrame(), "Input error " + ex, "Alert", JOptionPane.ERROR_MESSAGE);
        }

        /*System.out.println("fr x "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeFreeNodesX());
        System.out.println("fr y "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeFreeNodesY());

        System.out.println("lo x "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeLoadedNodesX());
        System.out.println("lo y "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeLoadedNodesY());

        System.out.println("sup x "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSupportedNodesX());
        System.out.println("sup y "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSupportedNodesY());*/
        update = true;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void update(Observable o, Object arg) {
        if (!update) {
            return;
        }
        /*System.out.println("\n\nupdating");
        System.out.println("Nodal grid "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid());
        System.out.println("X dis "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalXDisplacement());
        System.out.println("y DIs "+truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalYDisplacement());*/

        this.nodeTextField.setText(Integer.toString(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid()));
        this.nodeJSpinners[0].setValue(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalXDisplacement());
        this.nodeJSpinners[1].setValue(truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalYDisplacement());

    }
}
