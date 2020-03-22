package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.ImageLoader;

public class Mountain extends Item {
    public Mountain(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getMountain();
        getImageDimensions();
    }
}
