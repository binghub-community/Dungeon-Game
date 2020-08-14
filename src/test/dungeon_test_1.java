package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.*;
import unsw.dungeon.entity.*;
import unsw.dungeon.state.GameContext;

public class dungeon_test_1 {

    // MACRO
    public void p_coord(Entity e) {
        System.out.println(e.getClass() + " at (" + e.getX() + "," + e.getY() + ")");
    }



    // EPIC 1 - As a user, I want to be able to move my player into adjacent valid squares...
    // AC: I should be able to control my player's movement in a linear direction

    @Test
    public void movement_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        p.moveUp();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 0);
        // player should be in (1, 0)

        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
        // player should be in (1, 1)

        p.moveLeft();
        assertEquals(p.getX(), 0);
        assertEquals(p.getY(), 1);
        // player should be in (0, 1)

        p.moveRight();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
        // player should be back in (1, 1)
    }

    // EPIC 1 - As a user, I want to be able to move my player into adjacent valid squares...
    // AC: I should be bound by walls
    @Test
    public void wall_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        // Create a wall, player should not be able to move as he is boxed in by walls
        //     0 1 2
        //  0    w
        //  1  w p w
        //  2    w

        Wall w1 = new Wall(1, 0);
        d.addEntity(w1);
        Wall w2 = new Wall(0, 1);
        d.addEntity(w2);
        Wall w3 = new Wall(2, 1);
        d.addEntity(w3);
        Wall w4 = new Wall(1, 2);
        d.addEntity(w4);

        p.registerObserver(w1);
        p.registerObserver(w2);
        p.registerObserver(w3);
        p.registerObserver(w4);

        p.moveUp();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);

        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);

        p.moveLeft();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);

        p.moveRight();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
    }

    // EPIC 1 - As a user, I want to be able to open door with a key in order...
    // AC: I want to be able to pick up a key when moving on the square
    // AC: I want to be able to only carry one key at a time

    @Test
    public void key_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        Key k1 = new Key(2, 1, "blah");
        d.addEntity(k1);
        Key k2 = new Key(3, 1, "blah");
        d.addEntity(k2);

        p.registerObserver(k1);
        p.registerObserver(k2);

        //   0 1 2 3 4
        // 0 * * * * *
        // 1 * p k k *
        // 2 * * * * *

        // 1 move to the right to pickup the 1st key

        p.moveRight();

        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        assertTrue(p.isInInventory(k1));     // add assertion for picking up the key (should appear in player inventory list)


        p.moveRight();

        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 1);

        // 1 move to the rihg to pickup the 2nd key

        assertFalse(p.isInInventory(k2));    // add assertion that player should not be able to pick up the key because already 1 in inventory

    }


    // EPIC 1 - As a user, I want to be able to navigate to the exit point...
    // AC: I should have an exit point in the current dungeon
    // AC: I should be able to navigate to the exit point to complete the current dungeon
    
    @Test
    public void exit_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);

        Exit e = new Exit(2, 1);
        d.addEntity(e);

        //   0 1 2 3
        // 0 * * * *
        // 1 * p e *
        // 2 * * * *

        // 1 move to the right to get to the exit
    
        p.moveRight();

        // add assertion for what happens when we complete the dungeon

    }

    

    // EPIC 1 - As a user, I want to be able to open door with a key in order...
    // AC: I want to be able to only open a door with its corresponding key
    // AC: I want the key to disappear when it unlocks its corresponding door
    // AC: I want to be able to move through up to 3 different doors
    @Test
    public void door_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 4, 2);
        Key k1 = new Key(4, 3, "blah");
        d.addEntity(k1);
        Door d1 = new Door(4, 1, "blah1");
        Door d2 = new Door(5, 2, "blah2");
        Door d3 = new Door(3, 2, "blah3");
        Door d4 = new Door(4, 4, "blah");
        d.addEntity(d1);
        d.addEntity(d2);
        d.addEntity(d3);
        d.addEntity(d4);

        p.registerObserver(k1);
        p.registerObserver(d1);
        p.registerObserver(d2);
        p.registerObserver(d3);
        p.registerObserver(d4);

        // Here, k1 corresponds to d4 which is closed and all other doors are open
        //   0 1 2 3 4 5 6 7 8 9
        // 0 * * * * * * * * * *
        // 1 *       d         * 
        // 2 *     d p d       *
        // 3 *       k         *
        // 4 *       d         *
        // 5 *                 *
        // 6 * * * * * * * * * * 

        System.out.println("should be empty");
        for (Holdable h : p.getInventory()){
            System.out.println(h.getClass());
        }
        System.out.println("-------");

        // p_coord(p);
        p.moveDown();       // pick up the key
        // p_coord(p);
        System.out.println("should have a key");
        for (Holdable h : p.getInventory()){
            System.out.println(h.getClass());
        }
        System.out.println("-------");
        assertTrue(p.isInInventory(k1));

        // for (Observer o : p.getObservers()){
        //     Entity oh = (Entity) o;
        //     if (oh.getIsVisible())
        //         System.out.println(o.toString());
        // }

        p.moveDown();       // should be able to open the door since he has the key
        System.out.println("should be empty");
        for (Holdable h : p.getInventory()){
            System.out.println(h.getClass());
        }
        System.out.println("-------");
        // p_coord(p);
        assertTrue(d4.getIsOpen());  // add assertion that the door status is open
        assertFalse(p.isInInventory(k1));        // add assertion that the key has disappeared from inventory
        // p.moveDown();
        // p.moveDown();
        // assertEquals(p.getX(), 4);
        // assertEquals(p.getY(), 5);      // this means he has walked 1 space past the door ie. able to walk through the door
        // p.moveUp();
        // p.moveUp();
        // p.moveUp();
        // p.moveUp();     // player should have walked onto d1(4,1)
        // assertEquals(p.getX(), 4);
        // assertEquals(p.getY(), 1);

        // p.moveDown();
        // p.moveRight();
        // p.moveRight();  // player should have walked 1 space right past d2
        // assertEquals(p.getX(), 6);
        // assertEquals(p.getY(), 2);

        // p.moveLeft();
        // p.moveLeft();
        // p.moveLeft();
        // p.moveLeft();   // player should have walked 1 space left past d3
        // assertEquals(p.getX(), 2);
        // assertEquals(p.getY(), 2);

    }


    // EPIC 1 - As a user, I want to be able to open door with a key in order...
    // AC: I should be able to move into a portal and have my player appear on the square of the corresponding portal
    
    @Test
    public void portal_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        Portal P1 = new Portal(2, 1, "blah", "blah");
        Portal P2 = new Portal(6, 4, "blah", "blah");
        d.addEntity(P1);
        d.addEntity(P2);
        P1.setLinkedPortal(P2);
        P2.setLinkedPortal(P1);

        p.registerObserver(P1);
        p.registerObserver(P2);


        //   0 1 2 3 4 5 6 7 8 9
        // 0 * * * * * * * * * *
        // 1 * p P             *
        // 2 *                 *
        // 3 *                 *
        // 4 *           P     *
        // 5 *                 *
        // 6 * * * * * * * * * * 

        p.moveRight();      // player should be on the portal1 and teleported to the other portal
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        assertEquals(p.getX(), 6);
        assertEquals(p.getY(), 4);
        p.moveLeft();
        System.out.println("moveDown");
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        p.moveUp();
        System.out.println("moveRight");
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        p.moveDown();
        System.out.println("moveUp");
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        p.moveRight();
        System.out.println("moveLeft");     // player should go back onto the portal2 and teleported back to portal1
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        p.x().set(1);
        p.y().set(1);

        // TODO: Still buggy,location won't update after setting X,Y coords
        p.moveRight();
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        assertEquals(p.getX(), 6);
        assertEquals(p.getY(), 4);
        
    }
}