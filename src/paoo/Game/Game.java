package paoo.Game;


import paoo.Items.Cannon;
import paoo.Items.Item;
import paoo.Items.Rock;
import java.awt.*;
import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Game implements Runnable {
    private Board b;
    private int  level;
    private Boolean setat=false;
    private GameView gw=null;
    private int state;
    public long delaygameover=0;
    public int  gameover;
    private static Game instance=null;
    public int idPlayer=0;
    public static Game getInstance()
    {
        if(instance!=null)
        {
            return instance;
        }
        else
        {
            return new Game();
        }
    }
    Thread t;
    public Game(){
        level=1;
        gw=new GameView();
        b=Board.getInstance();
        state=0;
        instance=this;
    }
    public synchronized void startGame()
    {
        CollisionUtility.set(b.getItems());
        t=new Thread(this);
        t.start();
    }
    public void run()
    {
        while(true)
        {

            long timestart = System.currentTimeMillis();
            Update();
            Graphics g= b.getGraphics();
            render(g);
            if(System.currentTimeMillis()-delaygameover>3000) {
                if (gameover == 1) {
                    b.reset();
                    String sql = "SELECT * FROM `user` WHERE id = "+b.idPlayer+";";
                    try {
                        ResultSet rs= DataBase.getStm().executeQuery(sql);
                        if(rs.next())
                        {
                            String s = rs.getString("save");
                            int sc = rs.getInt("Score");
                            int lvl = rs.getInt("Level");
                            if(s.compareTo("-")!=0)
                            {
                                String path = "saves/"+b.idPlayer+".txt";
                                b.initBlocks(path,sc,lvl);
                            }
                            else
                            {
                                b.initBlocks("levels/1.txt",0,1);
                            }
                        }
                    }
                    catch (Exception e){System.out.println(e.getMessage());}
                    gameover = 0;
                } else if (gameover == 2) {
                    int sc= b.monster.getScore();
                    String sql = "UPDATE `user` set `Score`="+sc+",save = 'saves/"+b.idPlayer+".txt' WHERE id = "+b.idPlayer+";";
                    try {
                        DataBase.getStm().executeUpdate(sql);
                    }catch (Exception e){System.out.println(e.getMessage());}
                    b.reset();
                    if (level < Map.MAX_LEVEL) {
                        level++;
                        String s = "levels/" + level + ".txt";
                        try {
                            FileReader f = new FileReader(s);
                            int c;
                            String str = "";
                            while ((c = f.read()) != -1) {
                                str += (char) c;
                            }
                            s = "saves/" + b.idPlayer + ".txt.";
                            FileWriter out = new FileWriter(s);
                            out.write(str);
                            f.close();
                            out.close();
                            sql = "UPDATE `user` set `Level` = " + level + " WHERE id = " + b.idPlayer + ";";
                            DataBase.getStm().executeUpdate(sql);
                        }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        b.initBlocks(s, sc,level);
                    }
                    else
                    {
                        sql = "UPDATE `user` set `Completed`=1 WHERE id = "+b.idPlayer+";";
                        try {
                            DataBase.getStm().executeUpdate(sql);
                        }catch (Exception e){System.out.println(e.getMessage());}
                        state=1;
                        b.removeAll();
                        b.setMenu();
                    }
                    gameover = 0;
                }
            }
            long timeframe = System.currentTimeMillis() - timestart;
            int delay = 1000 / 30;
            if (timeframe < delay) {
                try {
                    sleep(delay - timeframe);
                } catch (Exception e) {
                }
            }
            if(state!=0)
            {
                break;
            }

        }
    }
    public synchronized void Update()
    {
        for(Item a : b.items)
        {
            a.Update();
        }
        for(Cannon c: b.cannons) {
            c.Update();
        }

        ArrayList<Rock> r=null;
        if(b.monster!=null)
        {
            if(b.getMonster().getScore()>90)
                Rock.upgrade();
            r=b.monster.getBullets();
            if(r!=null){
                for(Rock it:r)
                {
                    if(it.isVisible()) {
                        it.move();
                    }
                }
            }
        }
    }
    public synchronized void render(Graphics g)
    {
        b.paintComponents(g);
        //Graphics g = b.getGraphics();
        //b.paintComponent(g);
    }
    public int getLevel(){ return level; }
    public void setLevel(int lvl){ level=lvl;}
    public void setBoard(){
        gw.getPanel().add(b);
        b.revalidate();
        b.repaint();
        b.requestFocusInWindow();
        gw.setVisible(true);
    }
    public void setState(int s){ state=s;}
    public int getState() {return state;}
}
