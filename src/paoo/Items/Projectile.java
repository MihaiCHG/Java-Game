package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.CollisionUtility;
import paoo.Game.ImageLoader;
import paoo.Game.Map;

import static java.lang.Math.*;

public class Projectile extends Item {
    private final int SPEED = 10;
    private final int BOARD_WIDTH = Map.BOARD_WIDTH;
    private final int BOARD_HEIGHT = Map.BOARD_HEIGHT;
    private double direction;
    private int xdest, ydest;
    private Cannon cannon;

    public Projectile(int x, int y, BlockType tp, double direction, Cannon c, int xdest, int ydest) {
        super(x, y,tp);
        image= ImageLoader.getInstance().getProjectile();
        this.direction = direction;
        this.xdest=xdest;
        this.ydest=ydest;
        this.cannon=c;
        getImageDimensions();
    }

    public void move() {
        if(CollisionUtility.checkCollisionProjectile(getBounds()))
        {
            vis=false;
        }
        else {
            x+=cos(direction)*SPEED;
            y+=sin(direction)*SPEED;
            if (x > BOARD_WIDTH) {
                vis = false;
            }
            if (x < -width) {
                vis = false;
            }
            if (y > BOARD_HEIGHT) {
                vis = false;
            }
            if (y < -height) {
                vis = false;
            }
        }
    }

    /*public void upgrade() {
        this.upgrade = true;
    }

    public boolean getUpgrade() {
        return this.upgrade;
    }*/
}
