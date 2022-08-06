package com.pccontroll.backend;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {



    public String sendMail(List<User> users, boolean attach, String myMessage) {

        String from = "abrahamjoys98@gmail.com";
        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "usseymmoqlsvrija");

            }

        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            users.forEach(user -> {
                try {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getContactEmail()));
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

            // Set Subject: header field
            message.setSubject("LKCYL Camp Registration");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();

            try {
                textPart.setText(myMessage);
                multipart.addBodyPart(textPart);
                if (attach) {
                    File f =new File(new ExcelHandler().THEFILE);
                    attachmentPart.attachFile(f);
                    multipart.addBodyPart(attachmentPart);
                }


            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return  "Sent message successfully....";
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "Email not sent";
    }

    public static void main(String[] args) {
        User myUser1 = new User("Mat", "jesvinjoril98@yahoo.co.in", "07555374636", "384367467583", "1998-02-24", "23", "nwl", Gender.Male, 25.0);
        List<User> users = new ArrayList<>();
        users.add(myUser1);
        new SendMail().sendMail(users, false, "potato");

    }

}