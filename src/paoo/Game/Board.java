package paoo.Game;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import paoo.Items.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Board class of the game
 */
public class Board extends JPanel implements ActionListener {

    protected ArrayList<Item> items = null;
    protected Monster monster = null;
    protected Town town=null;
    protected ArrayList<Cannon> cannons =null;
    private static Board instance=null;
    private KeyAdapter keyESC=null;
    protected int idPlayer=0;
    private Board() {
        cannons=new ArrayList<>();
        items= new ArrayList<>();
        instance=this;
        initBoard();
    }
    public static Board getInstance()
    {
        if(instance==null)
        {
            return new Board();
        }
        else
        {
            return instance;
        }
    }
    /**
     * Initialize the board.
     */
    private void initBoard() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Map.BOARD_WIDTH, Map.BOARD_HEIGHT));
        //initBlocks("levels/1.txt");
        setLogin();
    }

    /**
     * Initialize blocks according to the map.
     */
    void initBlocks(String path, int sscore, int level) {
        int type;
        try {
            Factory factory = new Factory();
            FileReader f = new FileReader(path);
            Scanner sc = new Scanner(f);
            Map.loadLevel(sc);
            for (int y = 0; y < Map.level.length; y++) {
                for (int x = 0; x < Map.level[0].length; x++) {
                    type = Map.level[y][x];
                    BlockType bType = BlockType.getTypeFromInt(type);
                    items.add(factory.getItem(x*48,y*48, bType));
                }
            }
            int x,y, health,score;
            x=sc.nextInt();
            y=sc.nextInt();
            health=sc.nextInt();
            score=sscore;
            monster = (Monster)factory.getItem(x,y, BlockType.Monster);
            monster.setHealth(health);
            monster.setScore(score);
            Game.getInstance().setLevel(level);
            addKeyListener(monster);
            keyESC=new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent keyEvent) {
                   int key = keyEvent.getKeyCode();
                   if(key==KeyEvent.VK_ESCAPE)
                   {
                       if(Game.getInstance().getState()==0) {
                           setEscape();
                       }
                       else{
                           Game.getInstance().setState(0);
                           Game.getInstance().startGame();
                       }
                   }
               }
            };
            addKeyListener(keyESC);
            x = sc.nextInt();
            y=sc.nextInt();
            health = sc.nextInt();
            town = (Town)factory.getItem(x,y,BlockType.TOWN);
            town.setHealth(health);
            int nrcannons = sc.nextInt();
            Cannon c=null;
            for(int i=0;i<nrcannons;i++) {
                 x=sc.nextInt();
                 y=sc.nextInt();
                 health=sc.nextInt();
                 c= (Cannon)factory.getItem(x,y,BlockType.Cannon);
                 c.setHealth(health);
                 cannons.add(c);
            }

        }
        catch (Exception e){}
        CollisionUtility.set(items);
    }
    public Monster getMonster()
    {
        return monster;
    }
    public ArrayList<Item> getItems(){
        return items;
    }


    public void paintComponents(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draw objects on the board.
     */
    private void drawObjects(Graphics g) {
        Game game = Game.getInstance();
        for (Item a : items) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }
        ArrayList<Rock> rocks=null;
        if (monster != null && monster.isVisible()) {
            g.drawImage(monster.getImage(), monster.getX(), monster.getY(), this);
            rocks= monster.getBullets();
        }


        if (rocks != null) {
            for (Rock r : rocks) {
                if (r.isVisible()) {
                    g.drawImage(r.getImage(), r.getX(), r.getY(), this);
                }
            }
        }

        g.setFont(new Font("TimesRomans", Font.PLAIN, 12));
        g.setColor(Color.WHITE);

        if(town!=null)
        {
            g.drawString("" + town.getHealth() + "/100", town.getX(), town.getY());
            g.drawImage(town.getImage(), town.getX(), town.getY(), this);

        }

        if (cannons != null) {
            for (Cannon c : cannons) {
                g.drawString("" + c.getHealth() + "/100", c.getX(), c.getY());
                Graphics2D g2d = (Graphics2D) g;
                AffineTransform backup = g2d.getTransform();
                AffineTransform tr = AffineTransform.getRotateInstance(c.getRotate(), c.getX() + 24, c.getY() + 24);
                g2d.setTransform(tr);
                g2d.drawImage(c.getImage(), c.getX(), c.getY(), this);
                g2d.setTransform(backup);
                ArrayList<Projectile> prj = c.getProjectiles();
                if (prj != null) {
                    for (Projectile p : prj) {
                        if (p.isVisible()) {
                            g.drawImage(p.getImage(), p.getX(), p.getY(), this);
                        }
                    }
                }
            }
        }
        if(monster!=null) {
            g.setFont(new Font("TimesRomans", Font.PLAIN, 14));
            g.drawString("Score: " + monster.getScore(), 48, 48);
            g.drawString("Health: " + monster.getHealth() + "/100", 48, 62);
            g.drawString("Level: " + game.getLevel(), 48, 76);
        }
        g.setFont(new Font("TimesRomans", Font.BOLD, 30));
        if(game.gameover==1){
            g.drawString("Ai pierdut acest nivel, vei continua de la salvare", Map.BOARD_WIDTH/2-10*30, Map.BOARD_HEIGHT/2-15);
        }
        else if(game.gameover==2){
            if(game.getLevel()==Map.MAX_LEVEL){
                g.drawString("Ai castigat acest nivel si ai terminat jocul.", Map.BOARD_WIDTH/2-10*30,Map.BOARD_HEIGHT/2-15);
            }
            else
                g.drawString("Ai castigat acest nivel.", Map.BOARD_WIDTH/2-3*30,Map.BOARD_HEIGHT/2-15);
        }
    }
    public void reset()
    {
        removeKeyListener(monster);
        if(keyESC!=null){
            removeKeyListener(keyESC);
        }
        items=new ArrayList<>();
        cannons=new ArrayList<>();
        town=null;
        monster=null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void setLogin()
    {
        removeAll();
        repaint();
        JLabel jl1= new JLabel("Username:");
        JTextField name= new JTextField(30);
        JLabel jl2= new JLabel("Password:");
        JTextField pass= new JPasswordField(30);
        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String sql = "SELECT * FROM `user` WHERE `Name` = '"+name.getText()+"' AND `Password` = '"+pass.getText()+"';";
                try {
                    ResultSet rs = DataBase.getStm().executeQuery(sql);
                    if(rs.next())
                    {
                        idPlayer = rs.getInt("id");
                    }
                    else
                    {
                        sql="INSERT INTO `user` (Name, Password, save, Score) VALUES ('"+name.getText()+"', '"+pass.getText()+"', '-', 0);";
                        DataBase.getStm().executeUpdate(sql);
                        sql = "SELECT * FROM `user` WHERE `Name` = '"+name.getText()+"' AND `Password` = '"+pass.getText()+"';";
                        rs=DataBase.getStm().executeQuery(sql);
                        if(rs.next())
                            idPlayer = rs.getInt("id");
                    }
                    removeAll();
                    setMenu();
                }
                catch (Exception e){//initBlocks("levels/1.txt");
                     System.out.println(e.getMessage());}
            }
        });
        setLayout(new FlowLayout());
        add(jl1);
        add(name);
        add(jl2);
        add(pass);
        add(login);
        setBackground(Color.GRAY);
        Game.getInstance().setBoard();
    }
    public void setEscape()
    {
        Game.getInstance().setState(1);
        removeAll();
        repaint();
        JButton resume = new JButton("Resume");
        add(resume);
        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAll();
                Game.getInstance().setState(0);
                Game.getInstance().startGame();
            }
        });
        JButton save = new JButton("Save");
        add(save);
        Board je=this;
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Map.save(je,idPlayer);
                String sql = "UPDATE user set save = 'saves/"+idPlayer+".txt' WHERE id = "+idPlayer+";";
                try {
                    DataBase.getStm().executeUpdate(sql);
                }
                catch (Exception e){}
            }
        });
        JButton menu = new JButton("Menu");
        add(menu);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
                removeAll();
                setMenu();
            }
        });
        setLayout(new FlowLayout());
        revalidate();
        repaint();
        setVisible(true);
    }
    public void setTop(){
        removeAll();
        repaint();
        String[] colHead= {"Name", "Score"};
        String[][] data={};
        DefaultTableModel model = new DefaultTableModel(data, colHead);
        JTable table = new JTable(model);
        add(table);
        //DefaultTableModel md = (DefaultTableModel)table.getModel();
        String sql = "SELECT * FROM `user` ORDER BY `Score` DESC;";
        try {
            ResultSet rs = DataBase.getStm().executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("Name");
                int sc= rs.getInt("Score");
                model.addRow(new Object[]{name, Integer.toString(sc)});
            }
        }catch (Exception e){System.out.println(e.getMessage());}
        JButton menu = new JButton("Menu");
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setMenu();
            }
        });
        setLayout(new FlowLayout());
        //add(table);
        add(menu);
        revalidate();
        repaint();
        setVisible(true);

    }
    public void setMenu() {
        removeAll();
        repaint();
        JButton loadgame = new JButton("Load Game");
        JButton newgame = new JButton("New Game");
        JButton top = new JButton("Top");
        JButton exit = new JButton("Exit");
        add(loadgame);
        add(newgame);
        add(top);
        add(exit);
        loadgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String sql = "SELECT * FROM `user` WHERE id = "+idPlayer+";";
                try {
                    ResultSet rs= DataBase.getStm().executeQuery(sql);
                    if(rs.next())
                    {
                        String s = rs.getString("save");
                        int completed = rs.getInt("Completed");
                        int sc = rs.getInt("Score");
                        int lvl = rs.getInt("Level");
                        if(completed==0) {
                            if (s.compareTo("-") != 0) {
                                String path = "saves/" + idPlayer + ".txt";
                                initBlocks(path, sc,lvl);
                            } else {
                                initBlocks("levels/1.txt", 0,1);
                            }
                        }
                        else{
                            sql = "UPDATE user set save = '-', Score = 0, Completed = 0, `Level` = 1 WHERE id = "+idPlayer+";";
                            try {
                                DataBase.getStm().executeUpdate(sql);
                            }
                            catch (Exception e){System.out.println(e.getMessage());}
                            initBlocks("levels/1.txt",0,1);
                        }
                    }
                    removeAll();
                    Game.getInstance().setState(0);
                    Game.getInstance().gameover=0;
                    Game.getInstance().startGame();
                }
                catch (Exception e){System.out.println(e.getMessage());}

            }
        });
        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String sql = "UPDATE user set save = '-', Score = 0, Completed = 0, `Level` = 1 WHERE id = "+idPlayer+";";
                try {
                    DataBase.getStm().executeUpdate(sql);
                }
                catch (Exception e){System.out.println(e.getMessage());}
                initBlocks("levels/1.txt",0,1);
                removeAll();
                Game.getInstance().setState(0);
                Game.getInstance().gameover=0;
                Game.getInstance().startGame();
            }
        });
        top.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setTop();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataBase.close();
                System.exit(0);
            }
        });
        setLayout(new FlowLayout());
        revalidate();
        repaint();
        setVisible(true);

    }

}
