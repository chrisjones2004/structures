package trussoptimizater.Gui;

import java.awt.*;
import javax.swing.SwingUtilities;
import trussoptimizater.Truss.TrussModel;


/**
 * Currently not used
 * Main Class: trussoptimizater.Gui.MySplashScreen
 * Vm Options:  -splash:splash.gif
 * @author Chris
 */
/*
public class MySplashScreen {//extends Frame implements ActionListener

    final static String[] comps =
        {"Reading Sections",
        "Creating Node Dialog",
        "Creating Bar Dialog",
        "Creating Support Dialog",
        "Creating Load Dialog",
        "Creating Typical Truss Dialog",
        "Creating Section Dialog",
        "Creating View Dialog",
        "Creating Results Dialog",
        "Creating Optimization Dialog",
        "Creating CommandLineInterface Dialog",
        "Creating Properties Dialog"};

    static void renderSplashFrame(Graphics2D g, int frame) {

        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading " + comps[(frame / 5) % 3] + "...", 120, 150);
    }

    public MySplashScreen() {

        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            startApplication();
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for (int i = 0; i < comps.length; i++) {
            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Thread sleep problem in SplashDemo "+e);
            }
        }
        splash.close();
        startApplication();

    }

    public void startApplication(){
                SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                TrussModel truss = new TrussModel();
                new GUI(truss);

            }
        });
    }


    public static void main(String args[]) {
        new MySplashScreen();
    }
}
*/