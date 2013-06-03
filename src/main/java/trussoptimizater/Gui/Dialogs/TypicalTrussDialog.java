package trussoptimizater.Gui.Dialogs;


import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import trussoptimizater.Gui.*;
import trussoptimizater.Gui.GUIModels.GUIModeModel;


//improvements
//check L2/L1/H/n > 0 
//check L1>L2
public class TypicalTrussDialog extends WindowAdapter{

    //private JFrame frame;
    private JDialog dialog;
    private String[] picturePaths = {"/Pictures/Trusses/Trusses.bmp", "/Pictures/Trusses/Pratt Truss.bmp", "/Pictures/Trusses/Warren Truss.bmp", "/Pictures/Trusses/Trapezoid Truss.bmp"};
    private ButtonGroup radioButtons;
    private GUI gui;
    private TrussDialogue td;

    public TypicalTrussDialog(GUI gui) {
        this.gui = gui;
    }

    public void createGui() {
        dialog = new JDialog(gui.getFrame(), "Typical Trusses", true);
        JButton okButton = new JButton("  Set Up  ");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //open appropriate truss dialogue
                td = new TrussDialogue(radioButtons.getSelection().getActionCommand(), "/Pictures/Trusses/" + radioButtons.getSelection().getActionCommand() + ".bmp", dialog, gui);
                td.createGui();
            }
        });
        JButton cancelButton = new JButton("  Close  ");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        buttonPanel.add(new JButton("  Help  "));

        ImagePanel[] pictures = new ImagePanel[picturePaths.length];
        BufferedImage image = null;
        try {
            for (int i = 0; i < picturePaths.length; i++) {

                image = ImageIO.read(getClass().getResource(picturePaths[i]));
                pictures[i] = new ImagePanel(image);
            }
        } catch (IOException e) {
            System.err.println("\nTrying to read in image " + e);
        } catch (NullPointerException f) {
            System.err.println("\nTrying to read in image " + f);
        }



        JPanel radioButtonsPanel = new JPanel(new GridLayout(3, 1, 0, 50));
        JRadioButton prattButton = new JRadioButton("Pratt Truss");
        prattButton.setActionCommand("Pratt Truss");
        prattButton.setSelected(true);
        JRadioButton warrenButton = new JRadioButton("Warren Truss");
        warrenButton.setActionCommand("Warren Truss");
        JRadioButton trapezoidButton = new JRadioButton("Trapezoid Truss");
        trapezoidButton.setActionCommand("Trapezoid Truss");

        radioButtons = new ButtonGroup();
        radioButtons.add(prattButton);
        radioButtons.add(warrenButton);
        radioButtons.add(trapezoidButton);


        radioButtonsPanel.add(prattButton);
        radioButtonsPanel.add(warrenButton);
        radioButtonsPanel.add(trapezoidButton);

        JPanel topPanel = new JPanel();
        topPanel.add(radioButtonsPanel);

        topPanel.add(pictures[0]);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        

        dialog.setResizable(false);
        dialog.add(panel);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        //dialog.setLocationRelativeTo(frame);
        //dialog.setVisible(true);
    }


    @Override
    public void windowClosed(WindowEvent e) {
        gui.getGuiModeModel().setMode(GUIModeModel.SELECT_MODE);
    }



    public JDialog getDialog() {
        return dialog;
    }

    public TrussDialogue getTrussDialogue() {
        return td;
    }


}//end of class



