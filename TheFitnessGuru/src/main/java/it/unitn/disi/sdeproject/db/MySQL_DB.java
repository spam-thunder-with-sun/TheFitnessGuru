package it.unitn.disi.sdeproject.db;
import java.sql.*;

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

    public static int CreateAthlete(String name, String surname, String birthday, String gender, String username, String password, String account_type, String sport, String height, String weight) {
        int user_id = CreateUser(name, surname, birthday, gender, username, password, account_type);

        return user_id;
    }

    public static int CreateNutritionist(String name, String surname, String birthday, String gender, String username, String password, String account_type, String title, String description) {
        int user_id = CreateUser(name, surname, birthday, gender, username, password, account_type);

        return user_id;
    }

    public static int CreateTrainer(String name, String surname, String birthday, String gender, String username, String password, String account_type, String title, String description) {
        int user_id = CreateUser(name, surname, birthday, gender, username, password, account_type);

        return user_id;
    }

    public static void main(String[] args)
    {
        System.out.println("Create: " + CreateUser("Stefano", "Faccio", "2000-11-04", "M", "stefanotrick", "stefanotrick", "A"));
        System.out.println("Create: " + CreateUser("Giovanni", "Rigotti", "1998-01-01", "F", "giovannirigotti", "giovannirigotti", "N"));

        System.out.println("Auth: " + Authenticate("stefanotrick", "stefanotrick"));
        System.out.println("Auth: " + Authenticate("giovannirigotti", "giovannirigotti"));

        System.out.println("Auth: " + Authenticate("roba che non ce", "giovanni"));
        System.out.println("Auth: " + Authenticate("giovanni", "roba che non ce"));
        System.out.println("Auth: " + Authenticate("", ""));
    }

}
