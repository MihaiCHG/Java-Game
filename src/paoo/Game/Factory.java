package paoo.Game;

import paoo.Items.*;

public class Factory {
    public Item getItem(int x,int y,BlockType btype){
        Item it=null;
        switch (btype)
        {
            case GRASS:
                it= new Grass(x,y,btype);
                break;
            case Monster:
                it= new Monster(x,y,btype);
                break;
            case Cannon:
                it= new Cannon(x,y,btype);
                break;
            case SOIL:
                it= new Soil(x,y,btype);
                break;
            case TOWN:
                it= new Town(x,y,btype);
                break;
            case TREE:
                it= new Tree(x,y,btype);
                break;
            case WATER:
                it= new Water(x,y,btype);
                break;
            case MOUNTAIN:
                it= new Mountain(x,y,btype);
                break;
            default:
                break;
        }
        return it;
    }
}
