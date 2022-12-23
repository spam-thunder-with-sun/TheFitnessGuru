package it.unitn.disi.sdeproject.db;
import it.unitn.disi.sdeproject.beans.Athlete;
import it.unitn.disi.sdeproject.beans.Nutritionist;
import it.unitn.disi.sdeproject.beans.Trainer;
import it.unitn.disi.sdeproject.beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MySQL_DB {
    private static Connection con = null;
    private static void init() {
        if(con == null)
        {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                //con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/2YLoomalqj", "2YLoomalqj", "8lF7C5A1MT");
                con = DriverManager.getConnection("jdbc:mysql://db4free.net/thefitnessguru", "marypoppins", "SLdZUQCvGNQ2KJq");
                System.out.println("\n***** Connected to MySQL database! ***** \n");
            } catch (Exception e) {
                System.err.println("\n***** Connection to MySQL database failed! ***** \n");
                e.printStackTrace();
            }
        }
    }

    private static void destroy(){
        if(con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
        PreparedStatement stmt = null;
        ResultSet rs = null;
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

    private static int CreateUser(String name, String surname, String birthday, String gender, String username, String password, String account_type)
    {
        String query = "INSERT INTO USERS (USER_TYPE, NAME, SURNAME, BIRTHDATE, GENDER, USERNAME, PASSWORD) VALUES (?, ?, ?, ? , ?, ?, ?)";
        PreparedStatement stmt = null;
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hash = passwordAuthentication.hash(password.toCharArray());
        int success = -1;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setString(1, account_type);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, birthday);
            stmt.setString(5, gender);
            stmt.setString(6, username);
            stmt.setString(7, hash);
            int ris = stmt.executeUpdate();
            stmt.close();

            //Return the id of the user just inserted
            if(ris == 1)
                success = Authenticate(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public static int CreateAthlete(String name, String surname, String birthday, String gender, String username, String password, String sport, String height, String weight) {
        int user_id = CreateUser(name, surname, birthday, gender, username, password, "A");

        if(user_id != -1)
        {
            String query = "INSERT INTO ATHLETES (ATHLETE_ID, HEIGHT, WEIGHT, SPORT) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = null;

            try {
                stmt = getCon().prepareStatement(query);
                stmt.setInt(1, user_id);
                stmt.setString(2, height);
                stmt.setString(3, weight);
                stmt.setString(4, sport);
                int ris = stmt.executeUpdate();
                if(ris != 1)
                    user_id = -1;

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return user_id;
    }

    public static int CreateNutritionist(String name, String surname, String birthday, String gender, String username, String password, String title, String description) {
        int user_id = CreateUser(name, surname, birthday, gender, username, password, "N");

        if(user_id != -1)
        {
            String query = "INSERT INTO NUTRITIONISTS (NUTRITIONIST_ID, TITLE, DESCRIPTION) VALUES (?, ?, ?)";
            PreparedStatement stmt = null;

            try {
                stmt = getCon().prepareStatement(query);
                stmt.setInt(1, user_id);
                stmt.setString(2, title);
                stmt.setString(3, description);
                int ris = stmt.executeUpdate();
                if(ris != 1)
                    user_id = -1;

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user_id;
    }

    public static int CreateTrainer(String name, String surname, String birthday, String gender, String username, String password, String title, String description) {
        int user_id = CreateUser(name, surname, birthday, gender, username, password, "T");

        if(user_id != -1)
        {
            String query = "INSERT INTO TRAINERS (TRAINER_ID, TITLE, DESCRIPTION) VALUES (?, ?, ?)";
            PreparedStatement stmt = null;

            try {
                stmt = getCon().prepareStatement(query);
                stmt.setInt(1, user_id);
                stmt.setString(2, title);
                stmt.setString(3, description);
                int ris = stmt.executeUpdate();
                if(ris != 1)
                    user_id = -1;

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user_id;
    }

    public static User getUser(int user_id)
    {
        String query = "SELECT * FROM USERS WHERE USER_ID LIKE ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int success = -1;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                //Success
                ;

            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Trainer getTrainer(int user_id)
    {
        return null;
    }

    public static Nutritionist getNutritionist(int user_id)
    {
        return null;
    }

    public static Athlete getAthlete(int user_id)
    {
        return null;
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

        PreparedStatement stmt = null;

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

        System.out.println("Create Ath: " + CreateAthlete("Stefano", "Faccio", "2000-11-04", "M", "stefanotrick", "stefanotrick",  "Calcio", "1.75", "75"));
        System.out.println("Create User dup: " + CreateUser("Stefano", "Faccio", "2000-11-04", "M", "stefanotrick", "stefanotrick", "A"));
        System.out.println("Create nutri: " + CreateNutritionist("Giovanni", "Rigotti", "1998-01-01", "M", "giovannirigotti", "giovannirigotti", "Super Nutrizionista", "E' il meglio nutrizionista!"));
        System.out.println("Create trai: " + CreateTrainer("Test", "Test", "1998-01-01", "F", "test", "testtest", "Super Allenatore", "E' il meglio allenatore!"));

        System.out.println("Auth: " + Authenticate("stefanotrick", "stefanotrick"));
        System.out.println("Auth: " + Authenticate("giovannirigotti", "giovannirigotti"));

        System.out.println("Auth: " + Authenticate("roba che non ce", "giovanni"));
        System.out.println("Auth: " + Authenticate("giovanni", "roba che non ce"));
        System.out.println("Auth: " + Authenticate("", ""));
    }

}
