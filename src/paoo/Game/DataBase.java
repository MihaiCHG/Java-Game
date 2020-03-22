package paoo.Game;

import java.sql.*;

public class DataBase {
    private static Connection c;
    private static Statement stm;
    public static void init()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(true);
            stm=c.createStatement();
        }
        catch(Exception e){ System.out.println(e.getMessage());}
    }
    public static void close()
    {
        try {
            c.close();
            stm.close();
        }
        catch (Exception e){System.out.println(e.getMessage());}
    }
    public static Statement getStm()
    {
        return stm;
    }

}
