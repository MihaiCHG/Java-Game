package paoo.Game;


import paoo.Items.Cannon;
import paoo.Items.Item;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class Map {
    public static final int BOARD_WIDTH = 48 * 25;
    public static final int BOARD_HEIGHT = 48 * 19;
    public static final int MAX_LEVEL = 3;

    static int[][] level = new int[19][25];
    public static void loadLevel(Scanner sc)
    {
        for(int i=0;i<19;i++)
        {
            for(int j=0;j<25;j++)
            {
                level[i][j]=sc.nextInt();
            }
        }

    }
    public static void save(Board b, int id)
    {
        String s="saves/"+id+".txt";
        try {
            FileWriter fis = new FileWriter(s);
            BufferedWriter bf = new BufferedWriter(fis);
            int[][] lvl = new int[19][25];
            for(Item it:b.items){
                int x=it.getX()/48;
                int y=it.getY()/48;
                lvl[y][x]=it.type.getValue();
            }
            bf.write("");
            for(int y=0;y<lvl.length;y++)
            {
                for(int x=0;x<lvl[y].length;x++)
                {
                    s=lvl[y][x]+" ";
                    bf.append(s);
                    //System.out.println(""+lvl[y][x]+" ");
                }
                bf.append("\n");
            }

            if(b.monster!=null){
                s=b.monster.getX()+" "+b.monster.getY()+" "+b.monster.getHealth()+"\n";
                bf.append(s);
            }
            if(b.town!=null)
            {
                s=b.town.getX()+" "+b.town.getY()+" "+b.town.getHealth()+"\n";
                bf.append(s);
            }
            if(b.cannons!=null) {
                s=b.cannons.size()+"\n";
                bf.append(s);
                for(Cannon it:b.cannons){
                    s=it.getX()+" "+it.getY()+" "+it.getHealth()+"\n";
                    bf.append(s);
                }
            }
            bf.close();
        }
        catch (Exception e){}
    }
}
