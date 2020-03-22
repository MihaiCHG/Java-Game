package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.ImageLoader;

public class Town extends Block {
    public boolean gameOver = false;

    public Town(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getTownGrass();
        getImageDimensions();
        setHealth(100);
    }

    public void updateAnimation() {
        if (gameOver == true) {
            image= ImageLoader.getInstance().getTownGrassDestryed();
            getImageDimensions();

        }
    }

}
