package paoo.Items;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import paoo.Game.BlockType;
import paoo.Game.CollisionUtility;
import paoo.Game.ImageLoader;
import paoo.Game.Map;


public class Monster extends Item implements KeyListener {
    private final int BOARD_WIDTH = Map.BOARD_WIDTH;
    private final int BOARD_HEIGHT = Map.BOARD_HEIGHT;
    private int dx;
    private int dy;
    private ArrayList<Rock> rocks=null;
    public int direction;
    private long lastFired = System.currentTimeMillis();
    private int health = 100;
    private int score;
    private int startscore;

    public int getHealth() {
        return health;
    }
    public void setHealth(int h)
    {
        health=h;
    }
    public int getStartScore() {return startscore;}

    public void setStartscore(int sc){startscore=sc;}

    public void downHealth() {
        this.health -= 15;
        if(health<0)
            health=0;
    }

    public Monster(int x, int y, BlockType tp) {
        super(x, y,tp);
        loadImage("images/monsterLeft.png");
        getImageDimensions();
        rocks = new ArrayList<>();
        direction = 0;
        score=0;
    }

    public void move() {

        Rectangle theMonster = new Rectangle(x+4*dx,y+4*dy,width,height);
       if (!CollisionUtility.checkCollisionTankBlocks(theMonster)) {
            x += 4*dx;
            y += 4*dy;
       }

        if (x > BOARD_WIDTH - width) {
            x = BOARD_WIDTH - width;
        }

        if (y > BOARD_HEIGHT - height) {
            y = BOARD_HEIGHT - height;
        }
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public ArrayList<Rock> getBullets() {

        return rocks;
    }
    public void Update()
    {
        for(Rock r: rocks)
        {
            r.move();
        }
    }

    public void fire() {
        Rock aRock;
        lastFired=System.currentTimeMillis();
        BlockType b = BlockType.getTypeFromInt(7);
        if (direction == 0) {
            aRock = new Rock(x+width/2, y+height/2, b, 0, this);
        } else if (direction == 1) {
            aRock = new Rock(x+width/2, y+height/2, b, 1, this);
        } else if (direction == 2) {
            aRock = new Rock(x+width/2, y+height/2, b, 2, this);
        } else {
            aRock = new Rock(x+width/2, y+height/2, b, 3, this);
        }
        rocks.add(aRock);
        //SoundUtility.fireSound();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

  /*  public Image getImage() {
        return image;
    }*/

    public void keyPressed(KeyEvent e) {
        int time;
        int key = e.getKeyCode();
        ImageLoader img = ImageLoader.getInstance();
        time = 1500;
        if(score>50)
            time=1000;
        if (key == KeyEvent.VK_SPACE && (System.currentTimeMillis() - lastFired) > time) {
            fire();
            lastFired = System.currentTimeMillis();
        }
        else if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            dy = 0;
            image = img.getMonsterLeft();
            direction = 3;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            dy = 0;
            image = img.getMonsterRight();
            direction = 1;
        }
        else if (key == KeyEvent.VK_UP) {
            image = img.getMonsterUp();
            dy = -1;
            dx = 0;
            direction = 0;
        }
        else if (key == KeyEvent.VK_DOWN) {
            image = img.getMonsterDown();
            dy = 1;
            dx = 0;
            direction = 2;
        }
        move();
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
    public void keyTyped(KeyEvent e){

    }
    public void upHealth() {
        this.health += 1;
    }
    public void setScore(int sc){ score=sc;}
    public int getScore(){ return score;}

}
