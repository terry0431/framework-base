/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WangBo
 */
public class ConfigTest {
    
    public ConfigTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   

    /**
     * Test of getInstance method, of class Config.
     */
    @Test
    public void testGetInstance() throws IOException {
        System.out.println("getInstance");
        Config expResult = null;
       
        System.out.println(System.getProperty("user.dir") );
        System.out.println(new File("").getCanonicalPath() );
        System.out.println(new File("").getAbsolutePath() );
        URL resource;
        resource = getClass().getResource("/");
        System.out.println(resource.getPath());
        
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));  
        System.out.println(ClassLoader.getSystemResource(""));  
        System.out.println(ConfigTest.class.getResource(""));  
        System.out.println(ConfigTest.class.getResource("/"));  
        
        PathBean.testInit(System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes");
        Config result = Config.getInstance();
        assertNotNull("123");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
