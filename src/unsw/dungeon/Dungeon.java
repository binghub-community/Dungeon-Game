/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.FloorSwitch;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Subject;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.state.GameContext;
import unsw.dungeon.state.NewGameState;
import unsw.dungeon.state.ProgressGameState;
import unsw.dungeon.state.WinGameState;
import unsw.dungeon.entity.Observer;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private List<Subject> subjects;

    private Player player;
    private GameContext gameContext;

    private int openSwitches;
    private int aliveEnemies;
    private int uncollectedTreasure;
    public Dungeon(int width, int height, GameContext gameContext) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.subjects = new ArrayList<>();
        openSwitches = 0;
        aliveEnemies = 0;
        uncollectedTreasure = 0;
        this.player = null;
        this.gameContext = gameContext;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity instanceof FloorSwitch) openSwitches++;
        if (entity instanceof Enemy) aliveEnemies++;
        if (entity instanceof Treasure) uncollectedTreasure++;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }
    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    public GameContext getGameContext() {
        return gameContext;
    }
    public void collectedTreasure() {
        uncollectedTreasure--;
        if (uncollectedTreasure <= 0) {
            gameContext.changeState(new WinGameState());
        }
    }
    public void killedEnemy() {
        aliveEnemies--;
        if (aliveEnemies <= 0) {
            gameContext.changeState(new WinGameState());
        }
    }
    public void killedByEnemy() {
        gameContext.changeState(new NewGameState());
    }
    public void depressSwitch() {
        openSwitches--;
        if (openSwitches <= 0) {
            gameContext.changeState(new WinGameState());
        }
    }
    public void openSwitch() {
        openSwitches++;
    }
    public void reachedExit() {
        gameContext.changeState(new WinGameState());
    }
    public void progressGame() {
        gameContext.changeState(new ProgressGameState());
    }
}
