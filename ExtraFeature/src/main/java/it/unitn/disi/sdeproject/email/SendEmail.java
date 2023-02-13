package it.unitn.disi.sdeproject.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendEmail {

    final static String username = "alligather.events@gmail.com";
    final static String password = "tyyvvfdtnbujefrp";

    public static void SendEmail(String to, String subject, String text)
    {
        //TLS
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(username, password);}});

        try {
            Message message = new MimeMessage(session);
            //message.setFrom(new InternetAddress("alligather.events@gmail.com"));
            message.setFrom(new InternetAddress("alligather.events@gmail.com", "The Fitness Guru"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        SendEmail("stefano.faccio@studenti.unitn.it", "Email di Prova", "Hello World!");
    }

}