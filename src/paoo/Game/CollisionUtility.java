package paoo.Game;

import paoo.Items.*;

import java.util.ArrayList;
import java.awt.*;
public class CollisionUtility {

    private static ArrayList<Item> items;
    private CollisionUtility(){}
    public static void set(ArrayList<Item> i)
    {
        items=i;
    }
    public synchronized static Boolean checkCollisionTankBlocks(Rectangle r1){
        for(Item it:items) {
            if (it.type==BlockType.MOUNTAIN || it.type == BlockType.Cannon || it.type==BlockType.TREE || it.type==BlockType.TOWN){
                Rectangle r2 = it.getBounds();
                if (r1.intersects(r2)) {
                    return true;
                }
            }
        }
        return false;
    }
    public synchronized static Boolean checkCollisionRock(Rectangle rock)
    {
        Board b= Board.getInstance();
        Game g = Game.getInstance();
        if(b.town!=null)
        {
            Rectangle r2 = b.town.getBounds();
            if (rock.intersects(r2) && b.town.getHealth()>0) {
                b.town.damage(20);
                b.monster.setScore(b.monster.getScore()+10);
                if(b.town.getHealth()<=0 && g.gameover==0) {
                    g.gameover=2;
                    g.delaygameover=System.currentTimeMillis();
                    b.town.gameOver = true;
                }
                b.town.updateAnimation();
                return true;
            }
        }
        if(b.cannons!=null)
        {
            for(Cannon it:b.cannons)
            {
                Rectangle r2 = it.getBounds();
                if (rock.intersects(r2))
                {
                    if(it.getHealth()>0) {
                        it.damage(40);
                        b.monster.setScore(b.monster.getScore() + 5);
                        if (it.getHealth() <= 0) {
                            it.updateAnimation();
                        }
                        return true;
                    }
                }
            }
        }
        if(items!=null){
            for(Item it: items){
                if(it.type==BlockType.TREE || it.type==BlockType.MOUNTAIN){
                    Rectangle r = it.getBounds();
                    if(rock.intersects(r)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public synchronized static Boolean checkCollisionProjectile(Rectangle prj) {

        Board b = Board.getInstance();
        Monster m= b.getMonster();
        Rectangle rm= m.getBounds();
        if(prj.intersects(rm) && m.getHealth()>0){
            m.downHealth();
            Game g = Game.getInstance();
            if (m.getHealth() <= 0 && g.gameover == 0) {
                g.gameover = 1;
                g.delaygameover = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }
}
