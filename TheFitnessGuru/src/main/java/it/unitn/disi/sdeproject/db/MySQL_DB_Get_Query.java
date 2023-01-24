package it.unitn.disi.sdeproject.db;

import it.unitn.disi.sdeproject.beans.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static it.unitn.disi.sdeproject.db.MySQL_DB.getCon;

public final class MySQL_DB_Get_Query {
    public static User GetUser(int user_id)
    {
        //String query = "SELECT * FROM USERS WHERE USER_ID LIKE ?";
        String query = "SELECT USER_TYPE FROM USERS WHERE USER_ID LIKE ?";
        PreparedStatement stmt;
        ResultSet rs;
        User myuser = null;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                char user_type = rs.getString(1).charAt(0);
                //Success
                switch (user_type)
                {
                    case 'A':
                        myuser = GetAthlete(user_id);
                        break;
                    case 'T':
                        myuser = GetTrainer(user_id);
                        break;
                    case 'N':
                        myuser = GetNutritionist(user_id);
                        break;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myuser;
    }

    private static Trainer GetTrainer(int user_id)
    {
        String query = "SELECT USER_ID, USER_TYPE, NAME, SURNAME, BIRTHDATE, GENDER, USERNAME, TITLE, DESCRIPTION FROM USERS JOIN TRAINERS WHERE USER_ID = ? AND USER_ID = TRAINER_ID";
        PreparedStatement stmt;
        ResultSet rs;
        Trainer myuser = null;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                //Success
                myuser = new Trainer(rs.getInt(1), rs.getString(2).charAt(0), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6).charAt(0),
                        rs.getString(7), rs.getString(8), rs.getString(9));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myuser;
    }

    private static Nutritionist GetNutritionist(int user_id)
    {
        String query = "SELECT USER_ID, USER_TYPE, NAME, SURNAME, BIRTHDATE, GENDER, USERNAME, TITLE, DESCRIPTION FROM USERS JOIN NUTRITIONISTS WHERE USER_ID = ? AND USER_ID = NUTRITIONIST_ID";
        PreparedStatement stmt;
        ResultSet rs;
        Nutritionist myuser = null;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                //Success
                myuser = new Nutritionist(rs.getInt(1), rs.getString(2).charAt(0), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6).charAt(0),
                        rs.getString(7), rs.getString(8), rs.getString(9));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myuser;
    }

    private static Athlete GetAthlete(int user_id)
    {
        String query = "SELECT USER_ID, USER_TYPE, NAME, SURNAME, BIRTHDATE, GENDER, USERNAME, SPORT, HEIGHT, WEIGHT FROM USERS JOIN ATHLETES WHERE USER_ID = ? AND USER_ID = ATHLETE_ID";
        PreparedStatement stmt;
        ResultSet rs;
        Athlete myuser = null;

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                //Success
                myuser = new Athlete(rs.getInt(1), rs.getString(2).charAt(0), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6).charAt(0),
                        rs.getString(7),  rs.getString(8),  rs.getFloat(9),  rs.getFloat(10));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myuser;
    }
    
    //---------------------------AthletePage -> Trainer/Workout---------------------------------

    public static List<Collaboration> GetTrainerCollaboration(int athlete_id)
    {
        String query = "SELECT COLLABORATION_ID, INIT_DATE, STATUS, TRAINER_ID FROM TRAINER_COLLABORATIONS WHERE ATHLETE_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        User trainer;
        List<Collaboration> collabList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, athlete_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                trainer = GetUser(rs.getInt(4));
                collabList.add(new Collaboration(rs.getInt(1), trainer.getName(), trainer.getSurname(), trainer.getUser_id(), rs.getDate(2), rs.getBoolean(3)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collabList;
    }

    public static List<Workout> GetWorkoutRequest(int collaboration_id)
    {
        String query = "SELECT REQUESTS_ID, REQUEST_DATE, HEALTH_NOTES, WORKOUT_GOAL, WORKOUT_DAYS, WORKOUT_JSON FROM WORKOUT_REQUESTS WHERE COLLABORATION_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        List<Workout> workoutList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, collaboration_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                workoutList.add(new Workout(rs.getInt(1), rs.getDate(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workoutList;
    }

    public static List<Professional> GetNewPossibleTrainers(int athlete_id)
    {
        String query = "SELECT TRAINER_ID, NAME, SURNAME, TITLE, DESCRIPTION FROM ( SELECT T2.TRAINER_ID, T2.TITLE, T2.DESCRIPTION FROM ( SELECT TRAINER_ID AS TRAINER_ID FROM TRAINERS EXCEPT SELECT TRAINER_ID AS TRAINER_ID FROM TRAINER_COLLABORATIONS WHERE ATHLETE_ID LIKE ? ) AS T1 JOIN TRAINERS AS T2 WHERE T1.TRAINER_ID LIKE T2.TRAINER_ID) AS TT1 JOIN USERS AS TT2 WHERE TT1.TRAINER_ID LIKE TT2.USER_ID;";
        PreparedStatement stmt;
        ResultSet rs;
        List<Professional> professionalList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, athlete_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                professionalList.add(new Professional(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return professionalList;
    }

    public static String GetWorkoutResponse(int request_id)
    {
        String query = "SELECT WORKOUT_JSON FROM WORKOUT_REQUESTS WHERE REQUESTS_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        String ris = "";

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, request_id);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                //Success
                ris = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ris;
    }
    
    //----------------------------AthletePage -> Nutritionis/Diet-------------------------
    
    public static List<Collaboration> GetNutritionistCollaboration(int athlete_id)
    {
        String query = "SELECT COLLABORATION_ID, INIT_DATE, STATUS, NUTRITIONIST_ID FROM NUTRITIONIST_COLLABORATIONS WHERE ATHLETE_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        User nutritionist;
        List<Collaboration> collabList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, athlete_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                nutritionist = GetUser(rs.getInt(4));
                collabList.add(new Collaboration(rs.getInt(1), nutritionist.getName(), nutritionist.getSurname(), nutritionist.getUser_id(), rs.getDate(2), rs.getBoolean(3)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collabList;
    }

    public static List<Diet> GetDietRequest(int collaboration_id)
    {
        String query = "SELECT REQUESTS_ID, REQUEST_DATE, ALLERGIES, INTOLERANCES, BASAL_METABOLIC_RATE, DIET_GOAL, LIFESTYLE, DIET_JSON FROM DIET_REQUESTS WHERE COLLABORATION_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        List<Diet> dietList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, collaboration_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                dietList.add(new Diet(rs.getInt(1), rs.getDate(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7),
                        rs.getString(8)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dietList;
    }

    public static List<Professional> GetNewPossibleNutritionists(int athlete_id)
    {
        String query = "SELECT NUTRITIONIST_ID, NAME, SURNAME, TITLE, DESCRIPTION FROM ( SELECT T2.NUTRITIONIST_ID, T2.TITLE, T2.DESCRIPTION FROM ( SELECT NUTRITIONIST_ID AS NUTRITIONIST_ID FROM NUTRITIONISTS EXCEPT SELECT NUTRITIONIST_ID AS NUTRITIONIST_ID FROM NUTRITIONIST_COLLABORATIONS WHERE ATHLETE_ID LIKE ? ) AS T1 JOIN NUTRITIONISTS AS T2 WHERE T1.NUTRITIONIST_ID LIKE T2.NUTRITIONIST_ID) AS TT1 JOIN USERS AS TT2 WHERE TT1.NUTRITIONIST_ID LIKE TT2.USER_ID;";
        PreparedStatement stmt;
        ResultSet rs;
        List<Professional> professionalList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, athlete_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                professionalList.add(new Professional(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return professionalList;
    }

    public static String GetDietResponse(int request_id)
    {
        String query = "SELECT DIET_JSON FROM DIET_REQUESTS WHERE REQUESTS_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        String ris = "";

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, request_id);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                //Success
                ris = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ris;
    }

    //----------------------------NutritionistPage -> Athlete-------------------------

    public static List<Collaboration> GetNutritionistAthleteCollaboration(int nutritionist_id)
    {
        String query = "SELECT COLLABORATION_ID, INIT_DATE, STATUS, ATHLETE_ID FROM NUTRITIONIST_COLLABORATIONS WHERE NUTRITIONIST_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        User athlete;
        List<Collaboration> collabList = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, nutritionist_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                athlete = GetUser(rs.getInt(4));
                collabList.add(new Collaboration(rs.getInt(1), athlete.getName(), athlete.getSurname(), athlete.getUser_id(), rs.getDate(2), rs.getBoolean(3)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collabList;
    }

    public static List<Diet> GetNutritionistAthleteDietRequests(int collaboration_id)
    {
        String query = "SELECT REQUESTS_ID, REQUEST_DATE, ALLERGIES, INTOLERANCES, BASAL_METABOLIC_RATE, DIET_GOAL, LIFESTYLE, DIET_JSON  FROM DIET_REQUESTS WHERE COLLABORATION_ID = ?";
        PreparedStatement stmt;
        ResultSet rs;
        User athlete;
        List<Diet> diets = new ArrayList<>();

        try {
            stmt = getCon().prepareStatement(query);
            stmt.setInt(1, collaboration_id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                //Success
                diets.add(new Diet(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return diets;
    }

}
