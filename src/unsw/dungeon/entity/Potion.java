package unsw.dungeon.entity;

import java.util.Timer;
import java.util.TimerTask;

public class Potion extends Entity implements Observer {
    private int duration;

    public Potion(int x, int y) {
        super(x, y);
        duration = 15000;
    }

    public Potion(int x, int y, int duration) {
        super(x, y);
        this.duration = duration;
    }

    private void setInvincible(Player player) {
        player.setIsInvincible(true);
        new Timer().schedule(
            new TimerTask(){
                @Override
                public void run() {
                    player.setIsInvincible(false);
                    setIsVisible(false);
                } 
            },
            duration
        );
    }


    public void interact(Player player) throws Exception {
        if (player.willOverlapWith(this)) {
            setInvincible(player);
            setIsVisible(false);
        }
    }

    public void interact(Boulder boulder) throws UnobstructableTerrainException {
        if (boulder.willOverlapWith(this))
            throw new UnobstructableTerrainException("Can't push boulder over this entity.");
    }
    public void interact(Enemy enemy) throws UnobstructableTerrainException {
    
    }

    @Override
    public String toString() {
        return "Potion " + super.toString();
    }
}