package trussoptimizater.Gui.Dialogs;

import java.util.Observable;
import trussoptimizater.Truss.TrussModel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observer;
import trussoptimizater.Gui.FitnessGraph;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.BarTableModel;
import trussoptimizater.Truss.Optimize.GAOptimizer;

public class OptimizeDialog implements Observer {

    private TrussModel truss;
    private GUI gui;
    private JDialog dialog;
    private JPanel panel;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton playButton;
    private JButton optionsButton;
    private FitnessGraph[] fitnessGraphs = new FitnessGraph[6];

    public OptimizeDialog(GUI gui, TrussModel truss) {
        this.truss = truss;
        this.gui = gui;
        dialog = new JDialog(gui.getFrame(), "Optimize", false);
        truss.getOptimizeMethods().getGAOptimizer().getObserableSwingWorker().addObserver(this);
    }

    public void createGui() {
        panel = new JPanel(new BorderLayout());
        panel.add(this.getGraphsPanel());

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonContainer.add(getButtonPanel());
        dialog.add(buttonContainer, BorderLayout.NORTH);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();
    }

    public void initGraphs() {

        String[] graphTitles = {"Total", "Stress", "Weight", "Deflection", "Length", "Compression"};
        for (int i = 0; i < fitnessGraphs.length; i++) {
            fitnessGraphs[i] = new FitnessGraph(graphTitles[i], "Evolutions", "Fitness (1000's)");
            fitnessGraphs[i].setGraphType(FitnessGraph.LINE_GRAPH_TYPE);
            fitnessGraphs[i].setMarkerShape(FitnessGraph.STAR_POINT_SHAPE);
            fitnessGraphs[i].setMajorXaxisGridLines(true);
            fitnessGraphs[i].setMajorYaxisGridLines(true);
            fitnessGraphs[i].setXAxisLimits(0, truss.getOptimizeMethods().getGAOptimizer().getGAModel().getMaxAllowedEvolutions());
            fitnessGraphs[i].setBackground(Color.BLACK);
            fitnessGraphs[i].setPreferredSize(new Dimension(500, 500));
        }
    }

    public JPanel getGraphsPanel() {
        initGraphs();
        JTabbedPane fitnessGraphTabbedPane = new JTabbedPane();

        fitnessGraphTabbedPane.addTab("Total", fitnessGraphs[0]);
        fitnessGraphTabbedPane.addTab("Stress", fitnessGraphs[1]);
        fitnessGraphTabbedPane.addTab("Weight", fitnessGraphs[2]);
        fitnessGraphTabbedPane.addTab("Deflection", fitnessGraphs[3]);
        fitnessGraphTabbedPane.addTab("Length", fitnessGraphs[4]);
        fitnessGraphTabbedPane.addTab("Compression", fitnessGraphs[5]);

        JPanel graphPanel = new JPanel(new BorderLayout());
        graphPanel.add(fitnessGraphTabbedPane, BorderLayout.CENTER);
        return graphPanel;
    }

    public JPanel getButtonPanel() {
        stopButton = new JButton(new ImageIcon(getClass().getResource("/Pictures/Optimize Toolbar/Stop24.gif")));
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                truss.getOptimizeMethods().getGAOptimizer().getObserableSwingWorker().stopEvloution();
            }
        });
        pauseButton = new JButton(new ImageIcon(getClass().getResource("/Pictures/Optimize Toolbar/Pause24.gif")));
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(true);
                truss.getOptimizeMethods().getGAOptimizer().getObserableSwingWorker().pauseEvolution();
            }
        });

        playButton = new JButton(new ImageIcon(getClass().getResource("/Pictures/Optimize Toolbar/Play24.gif")));
        playButton.setEnabled(true);
        playButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(truss.getOptimizeMethods().getGAOptimizer().getState()==GAOptimizer.STOP_STATE){
                    for(int i = 0;i<fitnessGraphs.length;i++){
                        fitnessGraphs[i].clear();

                    }
                }
                //truss.setOperation(TrussModel.OPERATION_OPTIMIZING);
                stopButton.setEnabled(true);
                pauseButton.setEnabled(true);
                playButton.setEnabled(false);
                truss.getOptimizeMethods().getGAOptimizer().getObserableSwingWorker().startEvolution();

            }
        });

        optionsButton = new JButton("Options");
        optionsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gui.getOptimizeOptionsDialog().getDialog().setLocationRelativeTo(gui.getFrame());
                gui.getOptimizeOptionsDialog().getDialog().setVisible(true);
            }
        });


        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(optionsButton);

        return buttonPanel;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public FitnessGraph[] getFitnessGraphs() {
        return fitnessGraphs;
    }

    /**
     * <p>
     * If user chooses to optimize sections, bars will be assigned various section sizes and these will
     * be stored in the model. However the range of bars will not be added to sectionComboBoxModel for all
     * section combo boxes and also the barTable comboBoxes.
     * </p>
     * <p>
     * gui.getSectionDialog().addSection() is just used as a convienance method as it updates both section
     * jlist and sectioncomboboxmodel.
     * </p>
     */
    public void updateAddedSectionAfterOptimization(){
            for (int i = 0; i < truss.getBarModel().size(); i++) {
                if (!truss.getSectionModel().contains(truss.getBarModel().get(i).getSection().getName())) {
                    gui.getSectionDialog().addSection(truss.getBarModel().get(i).getSection().getName());
                }
            }
            for (int i = 0; i < truss.getBarModel().size(); i++) {
                //System.out.println("Bar " + (i + 1) + " section " + truss.getBarModel().get(i).getSection().getName());
                gui.getBarTable().getBarTableModel().setValueAt(truss.getBarModel().get(i).getSection().getName(), i, BarTableModel.SECTION_COLUMN_INDEX);
            }
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof Point2D[] && truss.getOptimizeMethods().getGAOptimizer().getState() != GAOptimizer.STOP_STATE) {
            Point2D[] points = (Point2D[]) arg;
            //System.out.println("Added point");
            for (int i = 0; i < fitnessGraphs.length; i++) {
                this.fitnessGraphs[i].addPoint(points[i]);
            }
        } else if (truss.getOptimizeMethods().getGAOptimizer().getState() == GAOptimizer.STOP_STATE) {
            updateAddedSectionAfterOptimization();
            playButton.setEnabled(true);
            stopButton.setEnabled(false);
            pauseButton.setEnabled(false);
        }


    }
}
