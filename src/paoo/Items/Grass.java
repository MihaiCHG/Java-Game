package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.ImageLoader;

public class Grass extends Item{
    public Grass(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getGrass();
        getImageDimensions();
    }
}
