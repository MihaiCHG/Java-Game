package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.ImageLoader;

public class Water extends Item{
    public Water(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getWater();
        getImageDimensions();
    }
}
