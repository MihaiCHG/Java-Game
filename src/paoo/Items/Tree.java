package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.ImageLoader;

public class Tree extends Item{
    public Tree(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getTree();
        getImageDimensions();
    }
}
