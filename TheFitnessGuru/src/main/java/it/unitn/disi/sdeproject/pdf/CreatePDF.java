package it.unitn.disi.sdeproject.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;

public class CreatePDF {
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public static void CreatePDFWorkout(String json, String athleteName, String pathImg, OutputStream myStream) {

        JSONObject jsonObj = new JSONObject(json.trim());
        JSONArray days = (JSONArray) jsonObj.get("days");
        //printJsonObject(jsonObj);

        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, myStream);
            document.open();
            Paragraph content = new Paragraph();

            //Add meta
            document.addTitle("Workout schedule");
            document.addSubject("Workout");
            document.addKeywords("Workout");
            document.addAuthor("Stefano Faccio, Giovanni Rigotti");
            document.addCreator("Stefano Faccio, Giovanni Rigotti");

            Paragraph title = new Paragraph("Workout schedule " + jsonObj.get("name"), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            content.add(title);

            Image image = Image.getInstance(pathImg);
            image.setAbsolutePosition(480f, 730f);
            image.scaleToFit(100f, 100f);
            document.add(image);

            addEmptyLine(content, 1);
            content.add(new Paragraph("Athlete: " + athleteName, subFont));
            content.add(new Paragraph("Goals: " + jsonObj.get("goals"), subFont));
            content.add(new Paragraph("Total days: " + jsonObj.get("day"), subFont));
            addEmptyLine(content, 3);

            for(int i = 0; i < days.length(); i++)
            {
                JSONObject day = days.getJSONObject(i);
                JSONArray exercises = (JSONArray) day.get("exercises");
                //printJsonObject(day);

                content.add(new Paragraph("Day " + (i+1) + " : " + day.get("name"), smallBold));
                addEmptyLine(content, 1);

                PdfPTable table = new PdfPTable(4);
                PdfPCell c1 = new PdfPCell(new Phrase("Name", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Reps", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Rest", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Sets", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(1);

                for(int y = 0; y < exercises.length(); y++)
                {
                    JSONObject exercise = exercises.getJSONObject(y);
                    //printJsonObject(exercise);

                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("name"), smallFont)));
                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("reps"), smallFont)));
                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("rest"), smallFont)));
                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("sets"), smallFont)));

                }
                content.add(table);
                addEmptyLine(content, 2);
            }

            document.add(content);
            document.newPage();
            document.close();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void CreatePDFDiet(String json, String pathImg, OutputStream myStream) {
        JSONObject jsonObj = new JSONObject(json.trim());
        JSONArray days = (JSONArray) jsonObj.get("days");
        //printJsonObject(jsonObj);

        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, myStream);
            document.open();
            Paragraph content = new Paragraph();

            //Add meta
            document.addTitle("Diet schedule");
            document.addSubject("Diet");
            document.addKeywords("Diet");
            document.addAuthor("Stefano Faccio, Giovanni Rigotti");
            document.addCreator("Stefano Faccio, Giovanni Rigotti");

            Paragraph title = new Paragraph("Diet schedule: " + jsonObj.get("name"), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            content.add(title);

            Image image = Image.getInstance(pathImg);
            image.setAbsolutePosition(480f, 730f);
            image.scaleToFit(100f, 100f);
            document.add(image);

            addEmptyLine(content, 1);
            content.add(new Paragraph("Athlete: " + jsonObj.get("athlete_full_name"), subFont));
            content.add(new Paragraph("Goals: " + jsonObj.get("goals"), subFont));
            content.add(new Paragraph("Lifestyle: " + jsonObj.get("lifestyle"), subFont));
            content.add(new Paragraph("Basal Metabolic Rate: " + jsonObj.get("bmr"), subFont));
            content.add(new Paragraph("Intolerances: " + jsonObj.get("intolerances"), subFont));
            content.add(new Paragraph("Allergies: " + jsonObj.get("allergies"), subFont));

            addEmptyLine(content, 3);

            for(int i = 0; i < days.length(); i++)
            {
                JSONObject day = days.getJSONObject(i);
                JSONArray recipes = (JSONArray) day.get("recipes");
                //printJsonObject(day);

                content.add(new Paragraph("Day " + (i+1) + " : " + day.get("name"), smallBold));
                addEmptyLine(content, 1);

                PdfPTable table = new PdfPTable(4);
                PdfPCell c1 = new PdfPCell(new Phrase("Title", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Ingredients", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Instructions", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Servings", smallFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(1);

                for(int y = 0; y < recipes.length(); y++)
                {
                    JSONObject exercise = recipes.getJSONObject(y);
                    //printJsonObject(exercise);

                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("title"), smallFont)));
                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("ingredients"), smallFont)));
                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("instructions"), smallFont)));
                    table.addCell(new PdfPCell(new Phrase((String) exercise.get("servings"), smallFont)));

                }
                content.add(table);
                addEmptyLine(content, 2);
            }

            document.add(content);
            document.newPage();
            document.close();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static void printJsonObject(JSONObject jsonObj) {
        jsonObj.keySet().forEach(keyStr ->
        {
            Object keyvalue = jsonObj.get((String) keyStr);
            System.out.println("key: "+ keyStr + " value: " + keyvalue);
        });
    }
}
