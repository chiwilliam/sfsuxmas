/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package k_means;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.NodeList;

/**
 *
 * @author bdalziel
 */
public class KMeansTest {

    public KMeansTest() {
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

//    /**
//     * Test of cluster method, of class KMeans.
//     */
//    @Test
//    public void cluster() {
//        System.out.println("cluster");
//        NodeList nl = null;
//        String fileName = "";
//        KMeans instance = new KMeans();
//        Document expResult = null;
//        Document result = instance.cluster((Probeset) nl,fileName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }

    /**
     * Test of getProbeIDs method, of class KMeans.
     */
    @Test
    public void getProbeIDs() {
        System.out.println("getProbeIDs");
        NodeList probeList = null;
        KMeans instance = new KMeans();
        int[] expResult = null;
        int[] result = instance.getProbeIDs(probeList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorrelationScore method, of class KMeans.
     */
    @Test
    public void getCorrelationScore() {
        System.out.println("getCorrelationScore");
        double[] a = new double[0];
        double[] b = new double[0];
        double expResult = 0;
        double result = KMeans.getCorrelationScore(a, b);
        assertEquals(expResult, result);
        
        a = new double[] {1, 2, 3, 5, 8};
        b = new double[] {0.11, 0.12, 0.13, 0.15, 0.18};
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
            b[i] = i;
        }
        expResult = 1;
        result = KMeans.getCorrelationScore(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDotProduct method, of class KMeans.
     */
    @Test
    public void getDotProduct() {
        System.out.println("getDotProduct");
        int vectorSize = 10;
        double[] a = new double[vectorSize];
        double[] b = new double[vectorSize];
        for (int i = 0; i < vectorSize; i++) {
            a[i] = i;
            b[i] = vectorSize - i;
        }
        double expResult = 165;
        assertEquals(expResult, KMeans.getDotProduct(a, b));
    }

    /**
     * Test of getSumOfSquares method, of class KMeans.
     */
    @Test
    public void getSumOfSquares() {
        System.out.println("getSumOfSquares");
        // Test null vector
        double[] a = new double[0];
        double expResult = 0;
        double result = KMeans.getSumOfSquares(a);
        assertEquals(expResult, result);
        
        // Simple
        a = new double[1];
        a[0] = 1;
        expResult = 1;
        result = KMeans.getSumOfSquares(a);
        assertEquals(expResult, result);
        
        // Complex
        
        // Simple
        a = new double[2];
        a[0] = 5;
        a[1] = 4;
        expResult = 41;
        result = KMeans.getSumOfSquares(a);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getNumberOfExpressionValues method, of class KMeans.
     */
    @Test
    public void getNumberOfExpressionValues() {
        System.out.println("getNumberOfExpressionValues");
        HashMap cluster = null;
        KMeans instance = new KMeans();
        int expResult = 0;
        int result = instance.getNumberOfExpressionValues(cluster);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getK method, of class KMeans.
     */
    @Test
    public void getK() {
        System.out.println("getK");
        // Test constructor specifying K
        int k = 4;
        KMeans instance = new KMeans(k);
        int expResult = k;
        int result = instance.getK();
        assertEquals(expResult, result);
        
        // Test constructor not specifying K
        KMeans instance2 = new KMeans();
        expResult = 1;
        result = instance2.getK();
        assertEquals(expResult, result);
    }

    /**
     * Test of setK method, of class KMeans.
     */
    @Test
    public void setK() {
        System.out.println("setK");
        // Test constructor specifying K
        int k = 4;
        KMeans instance = new KMeans();
        assertEquals(1, instance.getK());
        instance.setK(k);
        int expResult = k;
        int result = instance.getK();
        assertEquals(expResult, result);
    }
}
