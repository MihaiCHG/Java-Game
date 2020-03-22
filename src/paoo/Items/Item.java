package paoo.Items;

import paoo.Game.BlockType;

import javax.swing.*;
import java.awt.*;

public class Item {
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean vis;
    public Image image;
    public BlockType type;

    public Item(int x, int y, BlockType tp) {

        this.x = x;
        this.y = y;
        vis = true;
        type= tp;
    }

    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imageName) {
        ImageIcon i = new ImageIcon(imageName);
        image = i.getImage();
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public void Update(){}
}
