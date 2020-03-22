package paoo.Items;

import paoo.Game.BlockType;
import paoo.Game.Board;
import paoo.Game.Game;
import paoo.Game.ImageLoader;
import java.util.ArrayList;

import static java.lang.StrictMath.asin;

public class Cannon extends Block {
    private Monster monster;
    private double rotate=0;
    private long lastfire=0;
    private ArrayList<Projectile> projectiles=null;
    public Cannon(int x, int y, BlockType tp) {
        super(x, y, tp);
        image= ImageLoader.getInstance().getCannon();
        projectiles = new ArrayList<>();
        getImageDimensions();
        setHealth(100);
       // setType(3);
        lastfire=System.currentTimeMillis();
        this.monster= Board.getInstance().getMonster();
    }
    @Override
    public void setHealth(int health)
    {
        super.setHealth(health);
        if(getHealth()<=0){
            updateAnimation();
        }
    }
    public void updateAnimation() {
        image= ImageLoader.getInstance().getCannonDestroyed();
        getImageDimensions();
    }
    public void Update(){
        if(getHealth()>0) {
            double distanceOnY = getY()+height/2.0 - (monster.getY() + monster.height/2.0);
            double distanceOnX = getX()+width/2.0 - (monster.getX() + monster.width/2.0);
            double distance = Math.sqrt(distanceOnY*distanceOnY + distanceOnX*distanceOnX);
            double r = Math.atan2(distanceOnX,distanceOnY);
            rotate = -r-22.0/14;
            if (System.currentTimeMillis() - lastfire > 4000 && distance < 500 && monster.getHealth() > 0 && Game.getInstance().gameover==0) {
                lastfire = System.currentTimeMillis();
                fire(rotate);
            }
        }
        else if(getHealth()<0)
        {health=0;}
        if(projectiles!=null)
        {
            for(Projectile p:projectiles)
            {
                if(p.isVisible()) {
                    p.move();
                }
            }
        }
    }
    public void fire(double direction)
    {
        projectiles.add(new Projectile(x+width/2,y+height/2,BlockType.Projectile, direction,this,monster.getX(), monster.getY()));
    }
    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }
    public double getRotate()
    {
        return rotate;
    }
}
