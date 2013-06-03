package trussoptimizater.Gui.Dialogs;


import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import trussoptimizater.Gui.GUI;


public class ResultsOptionsDialog  extends WindowAdapter{ //implements WindowListener

    private javax.swing.JDialog dialog;
    private GUI gui;
    private JPanel panel;
    private int checkBoxCount = 7;
    private boolean[] checkBoxesSelectArray;
    private JCheckBox[] checkBoxArray;
    
    private int sliderCount = 3;
    private JSlider[] sliders = new JSlider[sliderCount];
    private int[] sliderValues = new int[sliderCount];


    public ResultsOptionsDialog(GUI gui) {
        this.gui = gui;
        checkBoxesSelectArray = new boolean[checkBoxCount];
        checkBoxArray = new JCheckBox[checkBoxCount];
        dialog = new javax.swing.JDialog(gui.getFrame(), "Results Display Options", false);
        dialog.addWindowListener(this);
        //dialog.setAlwaysOnTop(true);
    }

    public void createGui() {
        
        int checkboxIndex = 0;
        checkBoxArray[checkboxIndex] = new JCheckBox("<html>Color <font color=#FF0080>T</font> and \n<font color=#00FFFF>C</font> Members</html>");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setColorTensionCompression(jcbm.isSelected());
            }
        });
        checkboxIndex++;
        checkBoxArray[checkboxIndex] = new JCheckBox("Reactions");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setReactionsVisible(jcbm.isSelected());
            }
        });
        checkboxIndex++;
        checkBoxArray[checkboxIndex] = new JCheckBox("Axial Forces");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setAxialForcesVisible(jcbm.isSelected());
            }
        });
        
        checkboxIndex++;
        checkBoxArray[checkboxIndex] = new JCheckBox("Stress Map");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setColorAxialForces(jcbm.isSelected());
            }
        });


        checkboxIndex++;
        checkBoxArray[checkboxIndex] = new JCheckBox("Deflections");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setDeflectionsVisible(jcbm.isSelected());
            }
        });


        checkboxIndex++;
        checkBoxArray[checkboxIndex] = new JCheckBox("BM Diagrams");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setMomentsVisible(jcbm.isSelected());

            }
        });
        
        checkboxIndex++;
        checkBoxArray[checkboxIndex] = new JCheckBox("SF Diagrams");
        checkBoxArray[checkboxIndex].setSelected(false);
        checkBoxArray[checkboxIndex].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox jcbm = (JCheckBox) e.getSource();
                gui.getView().getViewModel().setShearVisible(jcbm.isSelected());
            }
        });
        



        initSliders();
        
        for (int i = 0; i < checkBoxArray.length; i++) {
            checkBoxArray[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
            checkBoxArray[i].setPreferredSize(new Dimension(checkBoxArray[i].getPreferredSize().width,sliders[0].getPreferredSize().height));
        }

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        int sliderIndex = 0;
        for(int i =0;i<checkBoxCount;i++){
            if(i<=3){
                panel.add(checkBoxArray[i]);
            }else{
                JPanel tempPanel = new JPanel(new GridLayout(1,2));
                tempPanel.add(checkBoxArray[i]);
                tempPanel.add(sliders[sliderIndex]);
                sliderIndex++;
                tempPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
                panel.add(tempPanel);
            }        
        }

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
    }
    
    public void initSliders(){
        for(int i =0 ;i<sliderCount;i++){
            sliders[i] = new JSlider();
            sliders[i].setMajorTickSpacing(20);
            sliders[i].setValue(10);
            sliders[i].setPaintTicks(true);
            sliders[i].setPreferredSize(new Dimension(100, (int) sliders[i].getPreferredSize().getHeight()));
        }


        sliders[0].addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                double zoomValue = (double) (source.getValue()) * 100.0;
                gui.getView().getViewModel().setDeflectionZoomScale(zoomValue);
            }
        });
        sliders[1].addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                double zoomValue = (double) (source.getValue());
                gui.getView().getViewModel().setMomentZoomScale(zoomValue);
            }
        });
        sliders[2].addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                double zoomValue = (double) (source.getValue());
                gui.getView().getViewModel().setShearZoomScale(zoomValue);
            }
        });
    }

    public void cancel(AWTEvent e) {
        for (int i = 0; i < checkBoxCount; i++) {
            checkBoxArray[i].setSelected(checkBoxesSelectArray[i]);
            ActionEvent event = new ActionEvent(checkBoxArray[i],e.getID(),e.toString());
            checkBoxArray[i].getActionListeners()[0].actionPerformed(event);
        }
        
        for(int i =0 ;i<sliderCount;i++){
            sliders[i].setValue(sliderValues[i]);
        }

        dialog.setVisible(false);
    }

    public void showGui() {
        for (int i = 0; i < this.checkBoxCount; i++) {
            checkBoxesSelectArray[i] = checkBoxArray[i].isSelected();
        }
        
        for(int i =0 ;i<sliderCount;i++){
            sliderValues[i] = sliders[i].getValue();
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


