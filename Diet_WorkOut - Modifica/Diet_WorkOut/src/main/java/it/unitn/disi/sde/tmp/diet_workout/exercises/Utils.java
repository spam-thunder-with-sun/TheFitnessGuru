package it.unitn.disi.sde.tmp.diet_workout.exercises;

public class Utils
{
    public enum Types
    {
        cardio,
        olympic_weightlifting,
        plyometrics,
        powerlifting,
        strength,
        stretching,
        strongman
    }

    public enum Muscles
    {
        abdominals,
        abductors,
        adductors,
        biceps,
        calves,
        chest,
        forearms,
        glutes,
        hamstrings,
        lats,
        lower_back,
        middle_back,
        neck,
        quadriceps,
        traps,
        triceps
    }

    public enum Difficulties
    {
        beginner,
        intermediate,
        expert
    }

    public static Types strToTypes(String str){
        switch (str){
            case "cardio":                  return Types.cardio;
            case "olympic_weightlifting":   return Types.olympic_weightlifting;
            case "plyometrics":             return Types.plyometrics;
            case "powerlifting":            return Types.powerlifting;
            case "strength":                return Types.strength;
            case "stretching":              return Types.stretching;
            case "strongman":               return Types.strongman;
            default: return null;
        }
    }

    public static Muscles strToMuscles(String str)
    {
        switch (str){
            case "abdominals":  return Muscles.abdominals;
            case "abductors":   return Muscles.abductors;
            case "adductors":   return Muscles.adductors;
            case "biceps":      return Muscles.biceps;
            case "calves":      return Muscles.calves;
            case "chest":       return Muscles.chest;
            case "forearms":    return Muscles.forearms;
            case "glutes":      return Muscles.glutes;
            case "hamstrings":  return Muscles.hamstrings;
            case "lats":        return Muscles.lats;
            case "lower_back":  return Muscles.lower_back;
            case "middle_back": return Muscles.middle_back;
            case "neck":        return Muscles.neck;
            case "quadriceps":  return Muscles.quadriceps;
            case "traps":       return Muscles.traps;
            case "triceps":     return Muscles.triceps;
            default: return null;
        }
    }

    public static Difficulties strToDifficulties(String str)
    {
        switch (str){
            case "beginner":        return Difficulties.beginner;
            case "intermediate":    return Difficulties.intermediate;
            case "expert":          return Difficulties.expert;
            default: return null;
        }
    }
}
