package trussoptimizater.Gui;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * This is
 * @author Chris
 */
public class CloseButton extends JButton {

    public static int TAB_BUTTON_WIDTH = 14;

        public CloseButton(Action action) {
            super(action);
            this.setText("");
            this.setBackground(Color.lightGray);
            setPreferredSize(new Dimension(TAB_BUTTON_WIDTH, TAB_BUTTON_WIDTH));
            setUI(new BasicButtonUI());
            setContentAreaFilled(true);
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
        }//end of constructor

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.WHITE);
            int delta = 4;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
        private final MouseListener buttonMouseListener = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                    //button.setBackground(Color.pink);
                    button.setBackground(Color.pink);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBackground(Color.lightGray);
                    button.setBorderPainted(false);
                }
            }
        };
    }//end of tabbutton class
