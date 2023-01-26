package it.unitn.disi.sdeproject.db;

import it.unitn.disi.sdeproject.beans.Collaboration;
import it.unitn.disi.sdeproject.beans.Diet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.*;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.*;

@SuppressWarnings({"DuplicatedCode", "CommentedOutCode", "unused"})
public final class MySQL_DB {
    private static Connection con = null;
    private static void init() {
        try {
            if(con == null || !con.isValid(3))
            {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://db4free.net/thefitnessguru", "marypoppins", "SLdZUQCvGNQ2KJq");
                    System.out.println("\n***** Connected to MySQL database! ***** \n");
            }
        } catch (Exception e) {
            System.err.println("\n***** Connection to MySQL database failed! ***** \n");
            e.printStackTrace();
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

    public static Connection getCon(){
        init();

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

        System.out.println("\n**** Creation and Autentication Users ****");
        //Creations
        List<Integer> athletes = new ArrayList<>();
        athletes.add(CreateAthlete("Stefano", "Faccio", "2000-11-04", "M", "stefanofa2000@gmail.com","stefanotrick", "stefanotrick",  "Pallavolo", "1.75", "75"));
        athletes.add(CreateAthlete("Massimo", "Michelutti", "2000-04-15", "M", "stefanofa2000@gmail.com","maxmiche", "maxmiche",  "Calcio", "1.85", "75"));
        athletes.add(CreateAthlete("Giulia", "Testa", "2002-05-10", "F", "stefanofa2000@gmail.com","giulytesta", "giulytesta",  "Lettura", "1.60", "45"));
        List<Integer> nutritionists = new ArrayList<>();
        nutritionists.add(CreateNutritionist("Giovanni", "Rigotti", "1998-01-01", "M", "giovanni.rigotti@studenti.unitn.it", "giovannirigotti", "giovannirigotti", "Grande Esperienza", "Sono il meglio nutritionista!"));
        nutritionists.add(CreateNutritionist("Paolo", "Aliprandi", "2000-01-01", "M", "stefanofa2000@gmail.com", "paoloali", "paoloali", "Super Nutrizionista", "Grazie al potere della fisica quantistica la tua pancia sparice in un secondo"));
        nutritionists.add(CreateNutritionist("Simone", "Marocco", "2000-01-01", "M", "stefanofa2000@gmail.com", "simomaro", "simomaro", "Giudicami per i miei successi!", "Le mie diete sono severe e devono essere seguite alla lettera!"));
        nutritionists.add(CreateNutritionist("Camilla", "Pelagalli", "1993-01-01", "F", "stefanofa2000@gmail.com", "camipela", "camipela", "Molto impegnata", "Non accetto nuovi atleti!"));
        nutritionists.add(CreateNutritionist("Stefano", "Genetti", "2000-01-01", "M", "stefanofa2000@gmail.com", "stegene", "stegene", "Sono il leader dei nutritionisti", "La migliore dieta la trovi solo con me!"));
        List<Integer> trainers = new ArrayList<>();
        trainers.add(CreateTrainer("Federico", "Dal Molin", "2001-02-02", "M", "stefanofa2000@gmail.com", "fededalmo", "fededalmo", "Più forte di Bud Spencer", "Grazie alla mia laurea in Scienze Motorie alleno atleti professionisti"));
        trainers.add(CreateTrainer("Greta", "Padovan", "2005-06-20", "F", "stefanofa2000@gmail.com", "gretapado", "gretapado", "La migliore allenatrice a mani basse!", "Sono la migliore, non c'è dubbio alcuno!"));
        trainers.add(CreateTrainer("Polin", "Caldonazzo", "2000-11-01", "F", "stefanofa2000@gmail.com", "polincald", "polincald", "Allenatrice severa e capace", "Alleno da 10 anni persone alle prime armi"));
        trainers.add(CreateTrainer("Simone", "Compri", "2000-01-01", "M", "stefanofa2000@gmail.com", "simocompri", "simocompri", "Allenatore spaccaossa", "Dico solo che mi piacciono i pesi pesanti"));
        trainers.add(CreateTrainer("Mauro", "DaMantova", "1970-01-01", "M", "stefanofa2000@gmail.com", "maurodamantova", "maurodamantova", "Allenamento, Allenamento, Allenamento!", "Forza, Forza, Forza!"));

        //Autenticate
        System.out.println("Auth: " + Authenticate("stefanotrick", "stefanotrick"));
        System.out.println("Auth: " + Authenticate("maxmiche", "maxmiche"));
        System.out.println("Auth: " + Authenticate("giulytesta", "giulytesta"));

        System.out.println("Auth: " + Authenticate("giovannirigotti", "giovannirigotti"));
        System.out.println("Auth: " + Authenticate("paoloali", "paoloali"));
        System.out.println("Auth: " + Authenticate("simomaro", "simomaro"));
        System.out.println("Auth: " + Authenticate("camipela", "camipela"));
        System.out.println("Auth: " + Authenticate("stegene", "stegene"));

        System.out.println("Auth: " + Authenticate("fededalmo", "fededalmo"));
        System.out.println("Auth: " + Authenticate("gretapado", "gretapado"));
        System.out.println("Auth: " + Authenticate("polincald", "polincald"));
        System.out.println("Auth: " + Authenticate("simocompri", "simocompri"));
        System.out.println("Auth: " + Authenticate("maurodamantova", "maurodamantova"));

        System.out.println("\n**** Trainer collaborations and Workout request ****");
        //Trainer collaborations
        List<Boolean> trainerCollab = new ArrayList<>();
        trainerCollab.add(CreateTrainerCollaboration(athletes.get(0), trainers.get(0), true));
        trainerCollab.add(CreateTrainerCollaboration(athletes.get(0), trainers.get(1), true));
        trainerCollab.add(CreateTrainerCollaboration(athletes.get(0), trainers.get(2), false));
        trainerCollab.add(CreateTrainerCollaboration(athletes.get(1), trainers.get(0), false));
        trainerCollab.add(CreateTrainerCollaboration(athletes.get(2), trainers.get(0), false));

        List<Collaboration> trainerCollaborationAthlet0 = GetTrainerCollaboration(athletes.get(0));
        int trainer_collab1 = trainerCollaborationAthlet0.get(0).getCollaboration_id();
        int trainer_collab2 = trainerCollaborationAthlet0.get(1).getCollaboration_id();
        //trainerCollab.set(0, AcceptTrainerAthleteCollaboration(trainers.get(0), trainer_collab1));
        //trainerCollab.set(0, AcceptTrainerAthleteCollaboration(trainers.get(1),trainer_collab2));
        System.out.println("Collab: " + trainerCollab.get(0));
        System.out.println("Collab: " + trainerCollab.get(1));
        System.out.println("Collab: " + trainerCollab.get(2));
        System.out.println("Collab: " + trainerCollab.get(3));
        System.out.println("Collab: " + trainerCollab.get(4));

        System.out.println("Possible Trainer: " + GetNewPossibleTrainers(athletes.get(0)).size());
        System.out.println("Possible Trainer: " + GetNewPossibleTrainers(athletes.get(1)).size());

        System.out.println("Workout: " + CreateWorkoutRequest(trainer_collab1, "Fisico da sogno", 5, "Super Sano"));
        System.out.println("Workout: " + CreateWorkoutRequest(trainer_collab1, "Potenziamento Muscolare", 2, "Tanti steroidi"));
        System.out.println("Workout: " + CreateWorkoutRequest(trainer_collab1, "Addominali scolpiti", 3, "Diabetico"));
        System.out.println("Workout: " +  CreateWorkoutRequest(trainer_collab2, "Poco sforzo, massima resa", 6, "Sano"));

        System.out.println("\n**** Nutritionist collaborations and Diet request ****");
        //Nutritionist collaborations
        List<Boolean> nutritionistCollab = new ArrayList<>();
        nutritionistCollab.add(CreateNutritionistCollaboration(athletes.get(0), nutritionists.get(0), false));
        nutritionistCollab.add(CreateNutritionistCollaboration(athletes.get(0), nutritionists.get(1), false));
        nutritionistCollab.add(CreateNutritionistCollaboration(athletes.get(0), nutritionists.get(2), false));
        nutritionistCollab.add(CreateNutritionistCollaboration(athletes.get(1), nutritionists.get(0), false));
        nutritionistCollab.add(CreateNutritionistCollaboration(athletes.get(2), nutritionists.get(0), false));

        List<Collaboration> nutritionistCollaborationAthlet0 = GetNutritionistCollaboration(athletes.get(0));
        int nutritionist_collab1 = nutritionistCollaborationAthlet0.get(0).getCollaboration_id();
        int nutritionist_collab2 = nutritionistCollaborationAthlet0.get(1).getCollaboration_id();

        nutritionistCollab.set(0, AcceptNutritionistAthleteCollaboration(nutritionists.get(0), nutritionist_collab1));
        nutritionistCollab.set(1, AcceptNutritionistAthleteCollaboration(nutritionists.get(1), nutritionist_collab2));
        System.out.println("Collab: " + nutritionistCollab.get(0));
        System.out.println("Collab: " + nutritionistCollab.get(1));
        System.out.println("Collab: " + nutritionistCollab.get(2));
        System.out.println("Collab: " + nutritionistCollab.get(3));
        System.out.println("Collab: " + nutritionistCollab.get(4));

        System.out.println("Possible Nutritionist: " + GetNewPossibleNutritionists(athletes.get(0)).size());
        System.out.println("Possible Nutritionist: " + GetNewPossibleNutritionists(athletes.get(1)).size());

        System.out.println("Diet: " + CreateDietRequest(nutritionist_collab1, "None", "None", 1800, "Perdita pancia", 3));
        System.out.println("Diet: " + CreateDietRequest(nutritionist_collab1, "None", "Lievito", 1900, "Potenziamento muscolare", 4));
        System.out.println("Diet: " + CreateDietRequest(nutritionist_collab1, "None", "lattosio", 2000, "Dieta per allenamento intensivo", 5));
        System.out.println("Diet: " +  CreateDietRequest(nutritionist_collab1, "None", "None", 2000, "Mantenimento", 3));
        System.out.println("Diet: " +  CreateDietRequest(nutritionist_collab2, "Arachidi, Latte", "None", 1500, "Perdita massa grassa", 1));

        String diet1 = "{\"name\":\"Dieta Ipocalorica\",\"allergies\":\"None\",\"bmr\":\"1800\",\"intolerances\":\"None\",\"goals\":\"Perdita pancia\",\"lifestyle\":\"3\",\"athlete_full_name\":\"Stefano Faccio\",\"days\":[{\"name\":\"day_1\",\"recipes\":[{\"title\":\"Elegant Baked Fish\",\"ingredients\":\"8 Fish fillets|1 1/2 c Hellmann's® Mayonnaise|1 tb Creole mustard|1 tb Lemon juice|1 tb Tabasco|1 tb Worcestershire sauce|2 ts Garlic powder|3/4 ts Curry powder|Ritz crackers\",\"instructions\":\"Mix well and spread over fish fillets. Sprinkle with crumbled Ritz crackers and bake at 400°F for about 20 minutes uncovered. Fish is done when it flakes easily with a fork.\",\"servings\":\"8 Servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_2\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_3\",\"recipes\":[{\"title\":\"Elegant and Easy Gourmet Gefilte Fish Pate\",\"ingredients\":\"--dwigans fwds07a---|3 lb Fish fillets; whitefish, and Pike, 1 1/2 lbs each|4 md Bermuda onions; Peeled and diced about 2#|3 tb Vegetable or canola oil|4 lg Eggs|2 c Cold water|6 tb Matzah meal|1 tb Salt or to taste|2 ts Ground white pepper|2 tb Sugar|2 lg Carrots; peeled|Parsley for garnish\",\"instructions\":\"Grind fish fine. Saute onions in oil till soft but not brown. Put the fish, onions, eggs, water, matzah meal, salt, white pepper, and sugar in a electric mixer bowl and beat on medium speed for 15 minutes. Grate the carrots and mix well. Pour the mixture into a gerased 12 cup bundt pan. Smooth the top with a spatula and bake in a preheated 325 degree oven for 1 hour in a larger pan filled with 2 inches water. Cover with alum foil and continue baking for 1 hour or until the center is solid. Cool for 5 minutes and then invert onto a flat serving plate. Refrigerate for several hours or over night. Slice as you would a torte and serve as an appetizer, garnished with parsley and served with red horseradish.\",\"servings\":\"20 Servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_4\",\"recipes\":[{\"title\":\"Fresh Vegetable and Chicken Stew\",\"ingredients\":\"1 (4-5 pound) chicken (up to)|3 tb Shortening|2 c Hot water|1 tb Salt|3/4 ts Black pepper|12 sm White onions|1 c Sliced carrots|1 c Fresh peas; lima beans, or snapbeans|1 c Diced potatoes|1/3 c All-purpose flour|1/2 c Water\",\"instructions\":\"Cut chicken into serving-size pieces and brown on all sides in hot shortening. Place in Dutch oven or saucepan with water, salt, and pepper. Cover and cook for 1 hour, or until chicken is tender. Add vegetables about 30 minutes before cooking time is up. Combine flour with the 1/2 cup water and stir into stew. Cook until medium thickness. Yield: 6 servings.\",\"servings\":\"6 Servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_5\",\"recipes\":[{\"title\":\"Elegant Baked Fish\",\"ingredients\":\"8 Fish fillets|1 1/2 c Hellmann's® Mayonnaise|1 tb Creole mustard|1 tb Lemon juice|1 tb Tabasco|1 tb Worcestershire sauce|2 ts Garlic powder|3/4 ts Curry powder|Ritz crackers\",\"instructions\":\"Mix well and spread over fish fillets. Sprinkle with crumbled Ritz crackers and bake at 400°F for about 20 minutes uncovered. Fish is done when it flakes easily with a fork.\",\"servings\":\"8 Servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_6\",\"recipes\":[{\"title\":\"Fresh Tomato Pizza\",\"ingredients\":\"2/3 c Warm water; (105-115¡F)|1 pk Active dry yeast|2 ts Sugar|1/2 ts Salt|3 tb Olive oil|2 c Flour|2 md Onions; finely chopped|1 Clove garlic; minced|1 ts Oregano; crushed|1/4 lb Dry salami; thinly sliced|3 md Tomatoes; sliced 1/4\\\" thick|2 c Monterey jack cheese; shredded\",\"instructions\":\"~ --- Layers of good things Ñ plenty of cheese, sliced fresh tomatoes, pungent salami and onions saut?ed with garlic and oregano Ñ top a puffy yeast crust to make a memorable pizza. This is a perfect supper dish, served with mixed green salad. - --- 1) Place water in a large bowl. Sprinkle with yeast. Let stand about 3 to 5 minutes. Stir in sugar, salt and 1 Tbsp. of the olive oil. Add 1 1/3 cups flour. Mix to blend. Beat about 3 minutes until dough pulls away from bowl. 2) Stir in 1/2 cup remaining flour to make a soft dough. Turn dough out onto floured board. Knead about 8 to 10 minutes until smooth and springy. Place dough in greased bowl; turn once to coat all sides. Cover and let rise in warm place about 30 minutes or until doubled. 3) Meanwhile, preheat oven to 450¡F and saut? onions until limp in 2 Tbsp. of the oil. Stir in garlic and oregano. 4) Punch down the dough. Roll or pat it into a greased 13 inch pan. Spread with onion mixture. Top with a layer of salami, single layer of tomato slices, and sprinkle with cheese. 5) Bake about 15 to 20 minutes or until crust is well browned.\",\"servings\":\"6 Servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_7\",\"recipes\":[{\"title\":\"Goan Fish Curry\",\"ingredients\":\"50 g Dried tamarind; chopped (and 150ml (1/4pint) boiling water) or 4 tomatoes, skinned and chopped roughly (2oz)|2 Garlic cloves; crushed|1 ts Ground coriander|1 ts Ground cumin|2 1/2 Cm; (1inch) piece of fresh root ginger, peeled and grated|1/2 ts Freshly ground black pepper|1/2 ts Ground turmeric|A pinch of salt|1 Onion; chopped|1 tb Vegetable oil|75 g Creamed coconut; grated (3oz)|375 g Coley or cod fillets; skined and cut into large chunks or strips (12oz)\",\"instructions\":\"If using dried tamarind, put it in a small bowl and cover with the boiling water. Leave to soak for 30 minutes. Strain through a sieve, pressing through as much of the tamarind pulp as possible. Lightly fry the garlic and spices, salt and onion in the oil for about 3 minutes until the onion has softened. Stir in the tamarind pulp and liquid, or the tomatoes, and the coconut, and continue to cook for 2-3 minutes. Lower the heat, add the fish to the pan and cook for about 10 minutes or until the fish is cooked, stirring occasionally during cooking.\",\"servings\":\"4 servings\"},{\"title\":\"Fresh Marinated Vegetables\",\"ingredients\":\"1 1/2 c Red wine vinegar|3/4 c Olive oil|1/4 c Granulated sugar|1 ts Salt|1 ts TABASCO pepper sauce|2 Cloves garlic; minced|1 lb Green beans; halved lengthwise|1 lg Sweet onion; sliced|2 lg Red bell peppers; cut into 1-inch chunks|2 lg Green bell peppers; cut into l-inch chunks|1 Head cauliflower; cut into flowerets|1 bn Broccoli; cut into flowerets|1 lb Fresh mushrooms; sliced|1 pt Cherry tomatoes\",\"instructions\":\"In medium non-aluminum saucepan, over medium-high heat, combine first 6 ingredients and bring to a boil. Remove from heat and cool. In medium saucepan, over medium-high heat, blanch green beans 1 minute. Combine all vegetables in large non-aluminum bowl. Pour in marinade and mix until vegetables are thoroughly coated; cover. Allow to marinate in refrigerator several hours. Vegetables can be left covered in refrigerator to marinate up to 5 days.\",\"servings\":\"6 Servings\"},{\"title\":\"Honey Vanilla Ice Cream\",\"ingredients\":\"3 Eggs|1/2 c Honey|2 c Milk|2 c Cream|2 ts Vanilla\",\"instructions\":\"Beat eggs and milk together in a large saucepan. Add honey. Cook over low heat stirring constantly until thickened. ( approx. 10 mins). Mixture should smoothly coat the spoon. Cool. Add cream and vanilla. Refrigerate overnight.\",\"servings\":\"1 Servings\"}]}]}";
        String diet2 = "{\"name\":\"Dieta per allenamento intensivo\",\"allergies\":\"None\",\"bmr\":\"1900\",\"intolerances\":\"Lievito\",\"goals\":\"Potenziamento muscolare\",\"lifestyle\":\"4\",\"athlete_full_name\":\"Stefano Faccio\",\"days\":[{\"name\":\"day_1\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"Green Curry of Pork And Choko\",\"ingredients\":\"2 tb Oil|500 g Diced pork|1 lg Onion; chopped|2 Cloves garlic; crushed|2 ts Green curry paste|2 Chokoes; peeled, seeds removed, roughly chopped|1 400 g. can coconut milk|1 ts Caster sugar|2 ts Fish sauce; (2 to 3)\",\"instructions\":\"1. Using a wok or large saucepan, heat half the oil and quickly seal the pork in batches and set aside in a bowl for later. 2. Heat the remaining oil in the wok and saute the onion and garlic. Add the curry paste and stir through before adding the choko, coconut milk, sugar and fish sauce. Bring to the boil, then reduce to a simmer and cook until the choko is almost tender. 3. Add pork to wok and continue cooking until pork is cooked through and choko is tender. Taste and adjust salt content with fish sauce. Serve with steamed jasmine rice. Per serving: 867 Calories (kcal); 86g Total Fat; (84% calories from fat); 7g Protein; 27g Carbohydrate; 1mg Cholesterol; 41mg Sodium Food Exchanges: 0 Grain(Starch); 0 Lean Meat; 2 Vegetable; 1/2 Fruit; 17 1/2 Fat; 0 Other Carbohydrates\",\"servings\":\"1 servings\"}]},{\"name\":\"day_2\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"East Meets West Chicken\",\"ingredients\":\"24 Chicken drumettes|1 tb Dry Vermouth (may substitute orange juice for vermouth)|1/2 ts Ground ginger|1/3 c Soy sauce|2 tb Honey|1/3 c Water\",\"instructions\":\"Place drumettes in skillet. Mix remaining ingredients and pour over chicken. Simmer slowly for 40 mins, drain wings and refrigerate for 24 hours. Serve cold or at room temp.\",\"servings\":\"4 Servings\"}]},{\"name\":\"day_3\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"Meethi Golian\",\"ingredients\":\"100 g Cornflour|50 g Sugar|1 Banana|1 Apple; (200 g.)|Oil for deep frying|200 ml Condensed milk|100 ml Fresh cream; beaten|30 ml Peach schnapps|A few plums; stoned and sliced for garnishing\",\"instructions\":\"MIX the cornflour, sugar and enough water to obtain a thick batter. Peel the banana and apple and scoop out even sized balls. Heat oil in a kadai. Dip the fruits in the batter and deep fry till light brown. Heat the condensed milk and cool it. Mix in the fresh cream and peach schnapps. Line a glass bowl with banana leaf triangles and pour the condensed milk mixture into it. Put the deep fried fruit balls in it. Garnish with plum slices. NOTES : Deep fried fruit balls in condensed milk\",\"servings\":\"4 servings\"}]},{\"name\":\"day_4\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"Meetha Kaddoo (Braised Butternut Squash with Jaggery)\",\"ingredients\":\"1 Squash; butternut|3 tb Oil|1/2 ts Fenugreek seeds|1/2 ts Hing|1 tb Coriander; ground|1/2 ts Cayenne pepper|Salt; kosher, to taste|1 ts Mango powder|3 tb Jaggery; powdered\",\"instructions\":\"Place the squash on the side on a cutting board. Using a sharp knife, cut its stem end off, then halve it (if necessary use a mallet to force the blade of the knife into the squash). Scoop out the seeds and fibers from each squash half, the scrape the flesh to remove clinging fibers. Cut each half into 1 1/2 inch wedges and peel the hard skin off. Cut the meat into 1 1/2 inch pieces and set aside. Measure out the spices and place them right next to the stove in separate piles. Heat the oil in a large skillet over medium-high heat until very hot. Add the fenugreek seeds and fry until they turn dark brown (about 10 seconds) add the asafetida, coriander, and pepper. Stir once and immediately add the squash. Stir rapidly to distribute the spices (about 3 to 4 minutes). Sprinkle on salt to taste; stir. Lower the heat and cook, covered, until the squash is cooked but still holding its shape (about 15 minutes). Uncover and sprinkle on the mango powder. Reduce the heat to medium and turn-fry for 2 minutes. Add the jaggery and cook until it melts and coats the vegetables (3-4 minutes). Serve warm, at room temperature, or cold.\",\"servings\":\"4 Servings\"},{\"title\":\"Mike's Chocolate Chip Cookies\",\"ingredients\":\"1 c Margarine|2/3 c Brown sugar|1 1/3 c Sugar|1 1/2 ts Vanilla|2 Extra large eggs|3 c Flour|1 ts Baking soda|1 ts Salt|2 c Chopped walnuts|24 oz Semisweet chocolate chips\",\"instructions\":\"Preheat oven to 375 degrees and line a few baking sheets with parchment paper. Cream margarine and sugar until light. Beat in vanilla and eggs until smooth. Mix together flour, soda, and salt but do not sift; beat into batter. Add chocolate chips and nuts; stir to mix thoroughly. Drop onto lined baking sheets, using a small ice cream scoop or a heaping tablespoonful. Bake for 12 minutes, turning sheets end for ends and trading oven racks at the halfway point. Remove from oven and slide parchment off sheets (with cookies) and leave until the cookies are barely cool enough to handle. Then transfer to racks to cool. From Mike Pedrazzini; makes 60 cookies\",\"servings\":\"1 servings\"}]},{\"name\":\"day_5\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"After Meeting Mike I Thought I'd Become a Prawn Again C P\",\"ingredients\":\"See part 1\",\"instructions\":\"2 Add the honey and mustard to the pan and continue cooking until the sausages are cooked through. Serve. PAN FRIED CHICKEN WITH CHERRY CABBAGE: Preheat the oven to 220c/Gas Mark 7. 1 Heat the oil in a small frying pan. Add the chicken and brown on all sides. Cook in the oven for 10-12 minutes. 2 Heat the cherry juice in a medium sized pan, add the cabbage and gently simmer. 3 When the cabbage has softened, add the wine and sugar. Bring to the boil, reduce down and serve with the chicken. CAULIFLOWER: 1 Cut the cauliflower into florets and cook in boiling salted water until tender. Drain the cooked cauliflower and mix with the herbs. Serve with the prawns. PRAWNS: 1 Remove the heads and tails from the prawns. Reserve for the soup. Remove any visible black veins and butterfly each prawn by cutting the back with a sharp knife. Heat the oil in a wok, add the prawns and fry for three minutes. 2 Add the garlic and lemon zest. Cook for a further 3-4 minutes or until cooked through. Season and serve. CABBAGE: Combine all the ingredients together in a bowl. Season and serve. SOUP: 1 Cut the chicken into escalopes. Heat 15ml/1tbsp oil in a shallow pan and fry the chicken until cooked through. 2 Heat the remaining oil in a pan and fry the reserved prawn heads and tails with the garlic, lemon juice, tabasco and tomato ketchup. 3 After two minutes, add the wine, stock cubes, spices and 300ml/ 1/2 pt boiling water. 4 Simmer gently for 8-10 minutes, then strain through a sieve. Return to the pan and add the coriander and chicken before serving. NOTES : Chef - Anthony Worrall Thompson\",\"servings\":\"1 servings\"}]},{\"name\":\"day_6\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"East Meets West Pepper Steak\",\"ingredients\":\"3 tb Butter, or vegetable oil|1 1/2 lb Beef round steak, or flank steak, cut into 1/4\\\" strips|1 1/2 c Onion, thinly sliced|1 c Diced celery|2 c Chopped tomatoes|1 1/2 ts Salt|1/2 ts Black pepper|1 ts Granulated sugar|2 Bay leaves|1/2 ts Dried thyme|3 lg Green bell peppers, julienned|1 1/2 ts Cornstarch|2 ts Soy souce|1/4 c Cold water|4 1/2 c Hot cooked rice\",\"instructions\":\"Heat butter or oil in heavy skillet or wok over high heat. Add meat 2 or 3 batches, so that each batch just covers the bottom of skillet without crowding; brown meat. Remove browned meat from skillet and set aside. Reduce heat. Add onions; saute 5 minutes. Return meat to skillet. Add celery, tomatoes, salt, pepper, sugar, bay leaves and thyme. Simmer 30 minutes. Add bell peppers; simmer 10 minutes. In a small bowl, blend cornstarch, soy sauce and water. Stir into meat mixture. Cook 1 minute, or until sauce is thick and clear. Serve over hot rice.\",\"servings\":\"6 Servings\"}]},{\"name\":\"day_7\",\"recipes\":[{\"title\":\"Elegant Rice and Chicken\",\"ingredients\":\"2 c Minute rice|2 c Water|1 Envelope dry onion soup mix|6 Chicken breasts - boneless & skinless|Flour|Salt|Paprika|1 cn Cream of mushroom soup|1/2 lb Fresh mushrooms\\\\|1 tb Butter\",\"instructions\":\"Bring water to boil, stir in the dry onion soup mix & then add rice. Turn off heat & let stand. Meanwhile, shake chicken in enough flour, salt & paprika to coat. Fry until golden brown. Put cooked rice mixture in large casserole. Arrange cooked chicken breasts on top. Coverf with the mushroom soup. Top with the mushrooms that have been sauteed in the 1 tablespoon butter. Dust with paprika. Bake uncovered in 350 degree oven for 30 minutes until thoroughly heated.\",\"servings\":\"1 Servings\"},{\"title\":\"Swedish Meet Balls (Kottbullar)\",\"ingredients\":\"1 lb Beef|1/2 lb Pork|1/2 lb Veal|2 Eggs|2/3 c Bread crumbs|1 c Milk|2 ts Salt|1/8 ts Pepper|1 ts Ground nutmeg|3 tb Shortening\",\"instructions\":\"flour water or stock Grind meats together 3 times. Mix with eggs, bread crumbs, milk, salt, pepper and nutmeg and make into small balls. Brown in skillet in hot shortening. Remove meat balls to larger larger pan. Add 1/2 cup flour to skillet and brown, stirring to prevent scorching. Add enough water or stock to make a thin gravy. Pour gravy over meat balls. Cook gently for 1 hour, stirring often to prevent sticking. Flavor improves with long simmering. Serve in gravy.\",\"servings\":\"6 Servings\"},{\"title\":\"Mike's Chocolate Chip Cookies\",\"ingredients\":\"1 c Margarine|2/3 c Brown sugar|1 1/3 c Sugar|1 1/2 ts Vanilla|2 Extra large eggs|3 c Flour|1 ts Baking soda|1 ts Salt|2 c Chopped walnuts|24 oz Semisweet chocolate chips\",\"instructions\":\"Preheat oven to 375 degrees and line a few baking sheets with parchment paper. Cream margarine and sugar until light. Beat in vanilla and eggs until smooth. Mix together flour, soda, and salt but do not sift; beat into batter. Add chocolate chips and nuts; stir to mix thoroughly. Drop onto lined baking sheets, using a small ice cream scoop or a heaping tablespoonful. Bake for 12 minutes, turning sheets end for ends and trading oven racks at the halfway point. Remove from oven and slide parchment off sheets (with cookies) and leave until the cookies are barely cool enough to handle. Then transfer to racks to cool. From Mike Pedrazzini; makes 60 cookies\",\"servings\":\"1 servings\"}]}]}";

        List<Diet> dietList = GetDietRequest(nutritionist_collab1);
        System.out.println("Update Diet: " + UpdateDietRequest(dietList.get(0).getRequest_id(), diet1));
        System.out.println("Update Diet: " + UpdateDietRequest(dietList.get(1).getRequest_id(), diet2));

        System.out.println("**** Fine ****");
    }
}
