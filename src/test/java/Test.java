/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import trussoptimizater.Gui.TrussFactory;
import trussoptimizater.Truss.TrussModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class Test {

    public Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        TrussModel truss = new TrussModel();
        
        TrussFactory.create15BarCantilever(truss);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}