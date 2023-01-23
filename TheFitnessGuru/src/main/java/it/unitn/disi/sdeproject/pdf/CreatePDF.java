package it.unitn.disi.sdeproject.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePDF {
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public static void CreatePDFWorkout(String json) {

        JSONObject jsonObj = new JSONObject(json.trim());
        JSONArray days = (JSONArray) jsonObj.get("days");
        //printJsonObject(jsonObj);

        Document document = new Document();
        try
        {
            File file = new File(".tmp.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
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

            Image image = Image.getInstance("UniOfTrento.png");
            image.setAbsolutePosition(480f, 730f);
            image.scaleToFit(100f, 100f);
            document.add(image);

            addEmptyLine(content, 1);
            content.add(new Paragraph("Athlete: " , subFont));
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
        } catch (DocumentException | IOException e)
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

    public static void main(String[] args) {
        String json = "{\"day\": \"3\", \"days\": [{\"name\": \"day_1\", \"exercises\": [{\"name\": \"Dumbbell Bench Press - chest\", \"reps\": \"10\", \"rest\": \"60\", \"sets\": \"4\"}, {\"name\": \"Close-grip bench press - chest\", \"reps\": \"10\", \"rest\": \"60\", \"sets\": \"4\"}, {\"name\": \"Barbell Bench Press - Medium Grip - chest\", \"reps\": \"15\", \"rest\": \"90\", \"sets\": \"3\"}]}, {\"name\": \"day_2\", \"exercises\": [{\"name\": \"Barbell glute bridge - glutes\", \"reps\": \"15\", \"rest\": \"90\", \"sets\": \"3\"}, {\"name\": \"Glute bridge - glutes\", \"reps\": \"15\", \"rest\": \"90\", \"sets\": \"3\"}, {\"name\": \"Hip Circles (Prone) - abductors\", \"reps\": \"10\", \"rest\": \"60\", \"sets\": \"5\"}]}, {\"name\": \"day_3\", \"exercises\": [{\"name\": \"Weighted pull-up - lats\", \"reps\": \"15\", \"rest\": \"90\", \"sets\": \"3\"}, {\"name\": \"EZ-bar spider curl - biceps\", \"reps\": \"10\", \"rest\": \"120\", \"sets\": \"5\"}, {\"name\": \"EZ-Bar Curl - biceps\", \"reps\": \"15\", \"rest\": \"90\", \"sets\": \"3\"}]}], \"name\": \"test\", \"goals\": \"strenght\"}";
        CreatePDFWorkout(json);
    }
}
