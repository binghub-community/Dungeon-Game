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

public class dungeon_test_misc {
    
    // As a user I want to be able to accomplish one or more goals towards completing the dungeon...
    // AC: Dungeons should consist of single or multiple goals to be solved by the player
    @Test
    public void accomplish_goals_test() {

    }

    // As a user I want to be able to collect all the treasure available to achieve...
    // AC: I should be able to move onto a square containing a treasure and subsequently collect it
    // AC: I should be able to pick up multiple treasures throughout the course of the dungeon
    @Test
    public void collect_treasure_test() {

        Dungeon d = new Dungeon(10, 10, new GameContext());
        Player p = new Player(d, 1, 1);
        Treasure t1 = new Treasure(1, 2);
        Treasure t2 = new Treasure(1, 3);
        Treasure t3 = new Treasure(1, 4);
        d.addEntity(t1);
        d.addEntity(t2);
        d.addEntity(t3);

        p.registerObserver(t1);
        p.registerObserver(t2);
        p.registerObserver(t3);

        //   0 1 2 3 4 5 6 7 8 9
        // 0 * * * * * * * * * *
        // 1 * p               * 
        // 2 * t               *
        // 3 * t               *
        // 4 * t               *
        // 5 *                 *
        // 6 * * * * * * * * * * 

        p.moveDown();
        assertTrue(p.getInventory().contains(t1));
        p.moveDown();
        assertTrue(p.getInventory().contains(t2));
        p.moveDown();
        assertTrue(p.getInventory().contains(t3));
    }
}