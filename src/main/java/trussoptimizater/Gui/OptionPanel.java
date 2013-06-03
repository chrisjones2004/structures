package trussoptimizater.Gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public abstract class OptionPanel extends MouseAdapter{ 
    private static final String titleIndent = "  ";
    private JPanel optionPanel;
    private JPanel highLightPanel;
    private JPanel titleBar;

    public static final Color BACKGROUND_COLOR = new Color(255,153,51);
    
    public OptionPanel(){
        super();
        createGui();
    }

    public void createGui(){


        CloseButton closeButton = new CloseButton(getCloseAction());
        JLabel titleLabel = new JLabel(titleIndent + getPanelTitle());
        //titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, titleLabel.getFont().getSize()));
        closeButton.addMouseListener(this);
        highLightPanel = new JPanel();
        highLightPanel.setPreferredSize(new Dimension(highLightPanel.getPreferredSize().width,3));

        titleBar = new JPanel(new BorderLayout(4,4));
        titleBar.addMouseListener(this);
        
        titleBar.add(highLightPanel,BorderLayout.NORTH);
        titleBar.add(titleLabel,BorderLayout.CENTER);
        titleBar.add(closeButton,BorderLayout.EAST);
        titleBar.setBorder(new EmptyBorder(3,5,3,5));




    }

    protected abstract JComponent getMainPanel();
    protected abstract String getPanelTitle();
    protected abstract Action getCloseAction();


    @Override
    public void mouseEntered(MouseEvent e) {
        highLightPanel.setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void mouseExited(MouseEvent e) {
         highLightPanel.setBackground(new Color(214,217,223));
    }

    public JPanel getOptionPanel() {
        optionPanel = new JPanel(new BorderLayout());
        optionPanel.add(titleBar,BorderLayout.NORTH);
        optionPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        optionPanel.add(getMainPanel(),BorderLayout.CENTER);
        return optionPanel;
    }


}
