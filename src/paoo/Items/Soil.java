package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.ImageLoader;

public class Soil extends Item {
    public Soil(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getSoil();
        getImageDimensions();
    }
}
