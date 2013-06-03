
package trussoptimizater.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/*
 * This class paints a picture onto a JPanel
 * It is used in the following instances
 *      displaying the typical truss pictures
 *      displaying the section diagrams in the section property dialog
 * 
 */
public class ImagePanel extends JPanel {
    private BufferedImage image;
    private Dimension size = new Dimension(100,250);

    public ImagePanel(BufferedImage image) {
        this.image = image;
        this.setPreferredSize(size);
        size.setSize(image.getWidth(), image.getHeight());
        this.setBackground(Color.WHITE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        // Center image in this component.
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        g.drawImage(image, x, y, this);
    }
    @Override
    public Dimension getPreferredSize() { return size; }
    
}