package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.CollisionUtility;
import paoo.Game.ImageLoader;
import paoo.Game.Map;

import java.util.ArrayList;

public class Rock extends Item {
    private static int ROCK_SPEED = 3;
    private final int BOARD_WIDTH = Map.BOARD_WIDTH;
    private final int BOARD_HEIGHT = Map.BOARD_HEIGHT;
    private int direction;
    private int distance;
    // private boolean upgrade = false;
    private Monster monster;
    //public boolean isEnemy;

    public Rock(int x, int y, BlockType tp, int direction, Monster monster) {
        super(x, y, tp);
        this.direction = direction;
        distance = 250;
        if (direction == 0) {
            image = ImageLoader.getInstance().getRockUp();
        }
        if (direction == 1) {
            image = ImageLoader.getInstance().getRockRight();
        }
        if (direction == 2) {
            image = ImageLoader.getInstance().getRockDown();
        }
        if (direction == 3) {
            image = ImageLoader.getInstance().getRockLeft();
        }
        this.monster = monster;
        getImageDimensions();
    }

    public void move() {
        if (CollisionUtility.checkCollisionRock(getBounds())) {
            vis = false;
        } else if (distance < 0) {
            vis = false;
        } else {
            distance -= ROCK_SPEED;
            if (direction == 0) {
                y -= ROCK_SPEED;
            } else if (direction == 1) {
                x += ROCK_SPEED;
            } else if (direction == 2) {
                y += ROCK_SPEED;
            } else if (direction == 3) {
                x -= ROCK_SPEED;
            }
            if (x > BOARD_WIDTH + 100 + width) {
                vis = false;
            }
            if (x < -width - 100) {
                vis = false;
            }
            if (y > BOARD_HEIGHT + height + 100) {
                vis = false;
            }
            if (y < -height - 100) {
                vis = false;
            }
        }
    }

    public static void upgrade() {
        ROCK_SPEED = 5;
    }
}
