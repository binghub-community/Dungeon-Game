package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import unsw.dungeon.*;
import unsw.dungeon.entity.*;
import unsw.dungeon.state.GameContext;
import unsw.dungeon.state.NewGameState;

public class dungeon_test_2 {
    
    // EPIC 2 - As a user, I want to be able to kill enemies when I am invincible...
    // AC: I want to be able to pickup an invincibility which will enable my invincibility status
    // AC: I want the invincibility potion to last for a limited amount of time
    @Test
    public void invincibility_potion_test() {
        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        Potion I = new Potion(2, 1);
        d.addEntity(I);

        p.registerObserver(I);


        //   0 1 2 3 4 
        // 0 * * * * *
        // 1 * p I   *
        // 2 *       *
        // 3 * * * * *

        assertFalse(p.getIsInvincible());    // player should not be invincible
        p.moveRight();      // player should have picked up the potion and invincibility triggered
        assertTrue(p.getIsInvincible());     // player is invincible

        new Timer().schedule(       
            new TimerTask(){
                @Override
                public void run() {
                    assertFalse(p.getIsInvincible());       //assertion that player is no longer invincible after set period
                } 
            },
            15000
        );
    }


    // // EPIC 2 - As a user, I want to be able to kill enemies when I am invincible...
    // // AC: I want the enemies to run away from my player while the player is invincible
    // @Test
    // public void enemy_run_away_test() {
    //     Dungeon d = new Dungeon(10, 10);
    //     Player p = new Player(d, 1, 1);
    //     Potion I = new Potion(2, 1);
    //     d.addEntity(I);
    //     Enemy e = new Enemy(3, 2);

    //     //   0 1 2 3 4 
    //     // 0 * * * * *
    //     // 1 * p I   *
    //     // 2 *     e *
    //     // 3 * * * * *

    //     // add assertion for enemy to run away somehow...I have no clue

    // }

    // // EPIC 2 - As a user, I want the player to be able to pick up swords in order to kill enemies
    // // AC: I want the player to only be able to pick up one sword at a time
    // // AC: I want the sword to last 5 uses before disappearing
    // // AC: I want the sword to be able to kill an enemy in one hit (one use)
    // @Test
    // public void sword_test() {
    //     Dungeon d = new Dungeon(10, 10);
    //     Player p = new Player(d, 1, 1);
    //     Sword s1 = new Sword(2, 1);
    //     Sword s2 = new Sword(3, 1);
    //     d.addEntity(s1);
    //     d.addEntity(s2);
    //     Enemy e1 = new Enemy(3, 2);
    //     Enemy e2 = new Enemy(3, 3);
    //     Enemy e3 = new Enemy(3, 4);
    //     Enemy e4 = new Enemy(3, 5);
    //     Enemy e5 = new Enemy(3, 6);
    //     d.addEntity(e1);
    //     d.addEntity(e2);
    //     d.addEntity(e3);
    //     d.addEntity(e4);
    //     d.addEntity(e5);

    //     //   0 1 2 3 4 5 6 7 8 9
    //     // 0 * * * * * * * * * *
    //     // 1 * p s s           * 
    //     // 2 *     e           *
    //     // 3 *     e           *
    //     // 4 *     e           *
    //     // 5 *     e           *
    //     // 6 *     e           *
    //     // 7 * * * * * * * * * * 

    //     p.moveRight();      // player should pick up sword
    //     p.moveRight();      // player should not be able to pick up sword
    //     // add assertion only 1 sword in player inventory
    //     p.moveDown();
    //     p.strike();
    //     // add assertion enemy died
    //     p.moveDown();
    //     p.strike();
    //     // add assertion enemy died
    //     p.moveDown();
    //     p.strike();
    //     // add assertion enemy died
    //     p.moveDown();
    //     p.strike();
    //     // add assertion enemy died
    //     p.moveDown();
    //     p.strike();
    //     // add assertion enemy died

    //     // add assertion sword has disappeared from inventory
    // }

    // EPIC 2 - As a user, I want to have an enemy constantly moving towards me...
    // AC: I want enemies to stop moving if they cannot move any closer
    // AC: I want the player to die after colliding with an enemy
    @Test
    public void enemy_run_toward_test() {

        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        Enemy e1 = new Enemy(d, 7, 2);
        Enemy e2 = new Enemy(d, 1, 6);
        d.addEntity(e1);
        d.addEntity(e2);
        Wall w1 = new Wall(6, 1);
        Wall w2 = new Wall(6, 2);
        Wall w3 = new Wall(6, 3);
        Wall w4 = new Wall(7, 3);
        Wall w5 = new Wall(8, 3);
        d.addEntity(w1);
        d.addEntity(w2);
        d.addEntity(w3);
        d.addEntity(w4);
        d.addEntity(w5);

        List<Observer> observersList = Arrays.asList(p, e1, e2, w1, w2, w3, w4, w5);

        p.registerObserver(observersList);
        e1.registerObserver(observersList);
        e2.registerObserver(observersList);


        //   0 1 2 3 4 5 6 7 8 9
        // 0 * * * * * * * * * *
        // 1 * p         w     * 
        // 2 *           w e   *
        // 3 *           w w w *
        // 4 *                 *
        // 5 *                 *
        // 6 * e               *
        // 7 * * * * * * * * * *

        // Note: movement tests should be more implementation-agnostic
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);
        assertEquals(e1.getX(), 7);
        assertEquals(e1.getY(), 1);
        assertEquals(e2.getX(), 1);
        assertEquals(e2.getY(), 5);
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);
        assertEquals(e1.getX(), 7);
        assertEquals(e1.getY(), 2);
        assertEquals(e2.getX(), 1);
        assertEquals(e2.getY(), 4);
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);
        assertEquals(e2.getX(), 1);
        assertEquals(e2.getY(), 3);
        p.moveRight();

        // Player would have encountered enemy by now and should end game.
        assertTrue(p.getGameContext().getState() instanceof NewGameState);

    }

}