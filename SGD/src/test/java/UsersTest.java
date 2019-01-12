/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** 
 *
 * @author ai265149
 */
public class UsersTest {
    
    Users us;
    int idJeu, idUser;
    Avis av;
    
    public UsersTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.idUser = 0;
        this.idJeu = 6;
        us = new Users(idUser);
        av=new Avis(idUser, idJeu);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of hasComment method, of class Users.
     */
    @Test
    public void testHasComment() {
        
        boolean expResult = true;
        boolean result = us.hasComment(idJeu);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getAvis method, of class Users.
     */
    @Test
    public void testGetAvis() {
        String expResult = "c'est bien";
        String result = us.getAvis(idJeu);
        assertEquals(expResult, result);
    }

    /**
     * Test of isFavori method, of class Users.
     */
    @Test
    public void testIsFavori() {
        String nomJeu = "PES 2005";
        boolean expResult = true;
        boolean result = us.isFavori(nomJeu);
        assertEquals(expResult, result);
    }

    /**
     * Test of isLike method, of class Users.
     */
    @Test
    public void testIsLike() {
        String nomJeu = "PES 2005";
        boolean expResult = true;
        boolean result = us.isLike(nomJeu);
        assertEquals(expResult, result);
    }

    /**
     * Test of isDislike method, of class Users.
     */
    @Test
    public void testIsDislike() {
        String nomJeu = "Dragon Ball Legends";
        boolean expResult = true;
        boolean result = us.isDislike(nomJeu);
        assertEquals(expResult, result);
    }

    /**
     * Test of addFavori method, of class Users.
     */
    @Test
    public void testAddFavori() {
        String nomJeu = "NBA 2K 92";
        us.addFavori(nomJeu);
        boolean expResult = true;
        boolean result = us.isFavori(nomJeu);
        assertEquals(expResult, result);
        us.removeFavori(nomJeu);
    }

    /**
     * Test of removeFavori method, of class Users.
     */
    @Test
    public void testRemoveFavori() {
        String nomJeu = "PES 2005";
        us.removeFavori(nomJeu);
        boolean expResult = false;
        boolean result = us.isFavori(nomJeu);
        assertEquals(expResult, result);
        us.addFavori(nomJeu);
    }

    /**
     * Test of addJeuLike method, of class Users.
     */
    @Test
    public void testAddJeuLike() {
        String nomJeu = "Bully";
        us.addJeuLike(nomJeu);
        boolean expResult = true;
        boolean result = us.isLike(nomJeu);
        assertEquals(expResult, result);
        us.removeJeuLike(nomJeu);
    }

    /**
     * Test of addJeuDislike method, of class Users.
     */
    @Test
    public void testAddJeuDislike() {
        String nomJeu = "Tetris";
        us.addJeuDislike(nomJeu);
        boolean expResult = true;
        boolean result = us.isDislike(nomJeu);
        assertEquals(expResult, result);
        us.removeJeuDislike(nomJeu);
    }

    /**
     * Test of removeJeuLike method, of class Users.
     */
    @Test
    public void testRemoveJeuLike() {
        String nomJeu = "GTA V";
        us.removeJeuLike(nomJeu);
        boolean expResult = false;
        boolean result = us.isLike(nomJeu);
        assertEquals(expResult, result);
        us.addJeuLike(nomJeu);
    }

    /**
     * Test of removeJeuDislike method, of class Users.
     */
    @Test
    public void testRemoveJeuDislike() {
        String nomJeu = "Dragon Ball Legends";
        us.removeJeuDislike(nomJeu);
        boolean expResult = false;
        boolean result = us.isDislike(nomJeu);
        assertEquals(expResult, result);
        us.addJeuDislike(nomJeu);
    }
    
}
