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

public class dungeon_test_3 {

    // EPIC 3 - As a user, I want to be able to push a boulder into adjacent squares to progress...
    // AC: I want there to be boulders able to be moved around
    // AC: I want my movements bound by boulders
    // AC: I should be able to push only one boulder at a time
    @Test
    public void boulder_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        Boulder b1 = new Boulder(d, 2, 1);
        Boulder b2 = new Boulder(d, 1, 2);
        Boulder b3 = new Boulder(d, 1, 3);
        d.addEntity(b1);
        d.addEntity(b2);
        d.addEntity(b3);

        p.registerObserver(b1);
        p.registerObserver(b2);
        p.registerObserver(b3);

        //   0 1 2 3 4 5 6 7 8 9
        // 0 * * * * * * * * * *
        // 1 * p b             *
        // 2 * b               *
        // 3 * b               *
        // 4 *                 *
        // 5 *                 *
        // 6 * * * * * * * * * * 

        // p.moveDown();
        // System.out.println(p.getX());
        // System.out.println(p.getY());
        // System.out.println(b2.getX());
        // System.out.println(b2.getY());
        // System.out.println(b3.getX());
        // System.out.println(b3.getY());
        // assertEquals(p.getX(), 1);
        // assertEquals(p.getY(), 1);      // player should be in same spot
        // assertEquals(b2.getX(), 1);
        // assertEquals(b2.getY(), 2);     // b2(1,2) should be in same spot
        // assertEquals(b3.getX(), 1);
        // assertEquals(b3.getY(), 3);     // b3(1,3) should be in same spot

        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);      // player should be where boulder previously was
        assertEquals(b1.getX(), 3);
        assertEquals(b1.getY(), 1);     // b1 should be pushed 1 spot to the right
    }

    // EPIC 3 - As a user, I want to be able to push a boulder onto a floor switch and trigger it...
    // AC: I want to be able to push a boulder onto a floor switch
    // AC: I want to be able to push a boulder off a floor switch
    // AC: I want to be able to walk over switches like empty squares

    // @Test
    // public void boulder_switch_test() {

    //     Dungeon d = new Dungeon(10, 10);
    //     Player p = new Player(d, 1, 1);
    //     FloorSwitch s1 = new FloorSwitch(2, 1);
    //     FloorSwitch s2 = new FloorSwitch(4, 1);
    //     Boulder b = new Boulder(3, 1);
    //     d.addEntity(s1);
    //     d.addEntity(s2);
    //     d.addEntity(b);

    //     //   0 1 2 3 4 5 6 7 8 9
    //     // 0 * * * * * * * * * *
    //     // 1 * p s b s         * 
    //     // 2 *                 *
    //     // 3 *                 *
    //     // 4 *                 *
    //     // 5 *                 *
    //     // 6 * * * * * * * * * *

    //     p.moveRight();
    //     assertFalse(s1.getIsDepressed());     // floor switch not triggered
    //     assertEquals(p.getX(), 2);
    //     assertEquals(p.getY(), 1);          // player is on the floor switch

    //     p.moveRight();
    //     assertEquals(b.getX(), 4);
    //     assertEquals(b.getY(), 1);
    //     assertTrue(s2.getIsDepressed());

    //     p.moveRight();
    //     assertEquals(b.getX(), 5);
    //     assertEquals(b.getY(), 1);
    //     assertFalse(s2.getIsDepressed());


    // }
}