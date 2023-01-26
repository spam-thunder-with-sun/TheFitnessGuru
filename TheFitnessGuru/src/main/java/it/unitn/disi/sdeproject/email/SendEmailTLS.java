package it.unitn.disi.sdeproject.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailTLS {

    public static void main(String[] args) {

        final String username = "alligather.events@gmail.com";
        final String password = "tyyvvfdtnbujefrp";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(username, password);}
                });

        try {
            Message message = new MimeMessage(session);
            //message.setFrom(new InternetAddress("alligather.events@gmail.com"));
            message.setFrom(new InternetAddress("thefitnessguru@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("stefanofa2000@gmail.com"));

            message.setSubject("Testing Gmail TLS");
            message.setText("Email inviata automaticamente da Java");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}