package trussoptimizater.Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is made up of random static methods which are of general use to many classes.
 * @author Chris
 */
public class SwingUtils {

    private final static int ROW_HEIGHT = 25;

    /**
     *
     * @param p double precision point
     * @return int precision point
     */
    public static Point point2DtoPoint(Point2D p) {
        return new Point((int) p.getX(), (int) p.getY());
    }

    public static JPanel getComponentColumn(JComponent[] components) {
        JPanel columnPanel = new JPanel();
        columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < components.length; i++) {
            components[i].setMinimumSize(new Dimension(components[i].getPreferredSize().width, ROW_HEIGHT));
            components[i].setPreferredSize(new Dimension(components[i].getPreferredSize().width, ROW_HEIGHT));
            components[i].setMaximumSize(new Dimension(components[i].getPreferredSize().width, ROW_HEIGHT));
            components[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
            columnPanel.add(components[i]);
        }
        return columnPanel;
    }

    /**
     *
     * @param strs an array of strings, where each string will be made into a JLabel
     * @return a panel containing a column of JLabels
     */
    public static JPanel getJLabelColumn(String[] strs) {
        JLabel[] labels = new JLabel[strs.length];

        for (int i = 0; i < strs.length; i++) {
            labels[i] = new JLabel(strs[i]);
        }
        return getComponentColumn(labels);
    }


}
