package paoo.Items;

import paoo.Game.BlockType;

public class Block extends Item{
    public int health = 3;
    private int type;

    public Block(int x, int y, BlockType tp) {
        super(x, y, tp);
    }
    public void damage(int damage) {
        health -= damage;
    }

    public int currentHealth() {
        return health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void updateAnimation() {

    }
}
