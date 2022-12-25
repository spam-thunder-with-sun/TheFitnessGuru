package it.unitn.disi.sdeproject.db;


import java.sql.PreparedStatement;
import java.util.Date;

import static it.unitn.disi.sdeproject.db.MySQL_DB.Authenticate;
import static it.unitn.disi.sdeproject.db.MySQL_DB.getCon;

public final class MySQL_DB_Set_Query {
    private static int CreateUser(String name, String surname, String birthday, String gender, String username, String password, String account_type)
    {
        String query = "INSERT INTO USERS (USER_TYPE, NAME, SURNAME, BIRTHDATE, GENDER, USERNAME, PASSWORD) VALUES (?, ?, ?, ? , ?, ?, ?)";
        PreparedStatement stmt;
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
            PreparedStatement stmt;

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
            PreparedStatement stmt;

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
            PreparedStatement stmt;

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

    public static boolean CreateTrainerCollaboration(int athlete_id, int trainer_id, boolean accepted)
    {
        String query = "INSERT INTO TRAINER_COLLABORATIONS (ATHLETE_ID, TRAINER_ID, INIT_DATE, STATUS) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt;
        boolean res = false;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, athlete_id);
            stmt.setInt(2, trainer_id);
            stmt.setDate(3, new java.sql.Date(new Date().getTime()));
            stmt.setBoolean(4, accepted);
            int ris = stmt.executeUpdate();
            if(ris == 1)
                res = true;

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
