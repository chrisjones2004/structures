/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trussoptimizater.Gui;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 */
public class TrussFactoryTest {

    public TrussFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create7BarWarrenTruss method, of class TrussFactory.
     */
    @Test
    public void testCreate7BarWarrenTruss() {
        System.out.println("create7BarWarrenTruss");
        TrussModel truss = null;
        TrussFactory.create7BarWarrenTruss(truss);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create9BarPrattTruss method, of class TrussFactory.
     */
    @Test
    public void testCreate9BarPrattTruss() {
        System.out.println("create9BarPrattTruss");
        TrussModel truss = null;
        TrussFactory.create9BarPrattTruss(truss);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create45BarPrattTruss method, of class TrussFactory.
     */
    @Test
    public void testCreate45BarPrattTruss() {
        System.out.println("create45BarPrattTruss");
        TrussModel truss = null;
        TrussFactory.create45BarPrattTruss(truss);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create15BarCantilever method, of class TrussFactory.
     */
    @Test
    public void testCreate15BarCantilever() {
        System.out.println("create15BarCantilever");
        TrussModel truss = null;
        TrussFactory.create15BarCantilever(truss);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create35BarCantilever method, of class TrussFactory.
     */
    @Test
    public void testCreate35BarCantilever() {
        System.out.println("create35BarCantilever");
        TrussModel truss = null;
        TrussFactory.create35BarCantilever(truss);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTrapezoidTruss method, of class TrussFactory.
     */
    @Test
    public void testCreateTrapezoidTruss() {
        System.out.println("createTrapezoidTruss");
        double L1 = 0.0;
        double L2 = 0.0;
        double H = 0.0;
        int fields = 0;
        TrussModel truss = null;
        TrussModel expResult = null;
        TrussModel result = TrussFactory.createTrapezoidTruss(L1, L2, H, fields, truss);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPrattTruss method, of class TrussFactory.
     */
    @Test
    public void testCreatePrattTruss() {
        System.out.println("createPrattTruss");
        double L1 = 0.0;
        double L2 = 0.0;
        double H = 0.0;
        int fields = 0;
        TrussModel truss = null;
        TrussModel expResult = null;
        TrussModel result = TrussFactory.createPrattTruss(L1, L2, H, fields, truss);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createWarrenTruss method, of class TrussFactory.
     */
    @Test
    public void testCreateWarrenTruss() {
        System.out.println("createWarrenTruss");
        double L1 = 0.0;
        double L2 = 0.0;
        double H = 0.0;
        int fields = 0;
        TrussModel truss = null;
        TrussModel expResult = null;
        TrussModel result = TrussFactory.createWarrenTruss(L1, L2, H, fields, truss);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}