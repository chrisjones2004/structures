package trussoptimizater;

import javax.swing.SwingUtilities;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.TrussModel;

/**
 * This is the main class which launches the application.
 * @author Chris
 */
public class Main {

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                TrussModel truss = new TrussModel();
                new GUI(truss);
            }
        });
    }
}
