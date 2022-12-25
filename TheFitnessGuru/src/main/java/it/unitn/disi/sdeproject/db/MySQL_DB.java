package it.unitn.disi.sdeproject.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.*;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.*;

@SuppressWarnings({"DuplicatedCode", "CommentedOutCode"})
public final class MySQL_DB {
    private static Connection con = null;
    private static void init() {
        if(con == null)
        {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://db4free.net/thefitnessguru", "marypoppins", "SLdZUQCvGNQ2KJq");
                System.out.println("\n***** Connected to MySQL database! ***** \n");
            } catch (Exception e) {
                System.err.println("\n***** Connection to MySQL database failed! ***** \n");
                e.printStackTrace();
            }
        }
    }

    /*
    private static void destroy(){
        if(con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     */

    public static Connection getCon() {
        if(con == null) {
            init();
        }

        return con;
    }

    public static ResultSet execute(String query) {
        ResultSet rs = null;

        try {
            Statement stmt = getCon().createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public static int Authenticate(String username, String password) {

        String query = "SELECT USER_ID, PASSWORD FROM USERS WHERE USERNAME LIKE ?";
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        PreparedStatement stmt;
        ResultSet rs;
        int success = -1;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                int user_id = rs.getInt(1);
                String password_hash = rs.getString(2);

                if(passwordAuthentication.authenticate(password.toCharArray(), password_hash))
                    success = user_id;
                    //Authentication success
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    private static int DeleteDB() {
        int success = 0;
        List<String> query = new ArrayList<>();
        query.add("DELETE FROM DIET_REQUESTS");
        query.add("DELETE FROM WORKOUT_REQUESTS");
        query.add("DELETE FROM NUTRITIONIST_COLLABORATIONS");
        query.add("DELETE FROM TRAINER_COLLABORATIONS");
        query.add("DELETE FROM NUTRITIONISTS");
        query.add("DELETE FROM ATHLETES");
        query.add("DELETE FROM TRAINERS");
        query.add("DELETE FROM USERS");

        PreparedStatement stmt;

        for(int i = 0; i < 8; i++)
            try {
                stmt = getCon().prepareStatement(query.get(i));
                success += stmt.executeUpdate();

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        return success;
    }

    public static void main(String[] args)
    {
        System.out.println("Delete DB: " + DeleteDB());

        int ath = CreateAthlete("Stefano", "Faccio", "2000-11-04", "M", "stefanotrick", "stefanotrick",  "Calcio", "1.75", "75");
        System.out.println("Create Ath: " + ath);
        int nutri = CreateNutritionist("Giovanni", "Rigotti", "1998-01-01", "M", "giovannirigotti", "giovannirigotti", "Super Nutrizionista", "E' il meglio nutrizionista!");
        System.out.println("Create nutri: " + nutri);
        int tra = CreateTrainer("Test", "Test", "1998-01-01", "F", "test", "testtest", "Super Allenatore", "E' il meglio allenatore!");
        System.out.println("Create trai: " + tra);
        int ath2 = CreateAthlete("Stefano", "Faccio", "2000-11-04", "M", "stefanotrick2", "stefanotrick",  "Calcio", "1.75", "75");
        System.out.println("Create Ath: " + ath2);
        int nutri2 = CreateNutritionist("Giovanni", "Rigotti", "1998-01-01", "M", "giovannirigotti2", "giovannirigotti", "Super Nutrizionista", "E' il meglio nutrizionista!");
        System.out.println("Create nutri: " + nutri2);
        int tra2 = CreateTrainer("Test", "Test", "1998-01-01", "F", "test2", "testtest", "Super Allenatore", "E' il meglio allenatore!");
        System.out.println("Create trai: " + tra2);
        int tra3 = CreateTrainer("Dio", "Cane", "1998-01-01", "F", "diocane", "testtest", "Super Allenatore", "E' il meglio allenatore!");
        System.out.println("Create trai: " + tra3);
        int tra4 = CreateTrainer("Dio", "Porco", "1998-01-01", "F", "dioporco", "testtest", "Super Allenatore", "E' il meglio allenatore!");
        System.out.println("Create trai: " + tra4);
        int tra5 = CreateTrainer("Dio", "Bestia", "1998-01-01", "F", "diobestia", "testtest", "Super Allenatore", "E' il meglio allenatore!");
        System.out.println("Create trai: " + tra5);
        int tra6 = CreateTrainer("Dio", "Ludro", "1998-01-01", "F", "dioludro", "testtest", "Super Allenatore", "E' il meglio allenatore!");
        System.out.println("Create trai: " + tra6);

        System.out.println("Auth: " + Authenticate("stefanotrick", "stefanotrick"));
        System.out.println("Auth: " + Authenticate("giovannirigotti", "giovannirigotti"));
        System.out.println("Auth: " + Authenticate("test", "testtest"));
        System.out.println("Auth: " + Authenticate("stefanotrick2", "stefanotrick"));
        System.out.println("Auth: " + Authenticate("giovannirigotti2", "giovannirigotti"));
        System.out.println("Auth: " + Authenticate("test2", "testtest"));

        System.out.println("Collab: " + CreateTrainerCollaboration(ath, tra));
        System.out.println("Collab: " + CreateTrainerCollaboration(ath, tra2));

    }
}
