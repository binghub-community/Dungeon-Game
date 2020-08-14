package unsw.dungeon.entity;

public class Door extends Entity implements Observer {
    private String matchingKeyId;
    private Boolean isOpen;

    public Door(int x, int y, String matchingKeyId) {
        super(x, y);
        this.matchingKeyId = matchingKeyId;
        isOpen = false;
    }

    public Key getMatchingKey(Player player, String matchingkeyid) {
        for (Holdable h : player.getInventory()){
            if (h instanceof Key){
                Key k = (Key) h;
                if (k.getId().equals(matchingkeyid)){
                    return k;
                }
            }
        }

        System.out.println("error getting key");
        return null;
    }

    public void interact(Player player) throws Exception {
        if (player.willOverlapWith(this)) {

            if (isOpen == true){
                return;
            } else if (player.isInInventory(matchingKeyId)) {
                setIsOpen(true);
                player.removeInventory(getMatchingKey(player, matchingKeyId));
                System.out.println("door opened successfully");
            } else {
                throw new KeyException("No matching key to open door.");
            }
        }   
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void interact(Boulder boulder) throws UnobstructableTerrainException {
        if (boulder.willOverlapWith(this))
            throw new UnobstructableTerrainException("Can't push boulder over this entity.");
    }

    public void interact(Enemy enemy) throws UnobstructableTerrainException {
    
    }
    @Override
    public String toString() {
        return "Door " + super.toString();
    }
}