package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * Service for sending email notifications via SMTP (Gmail).
 */
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private String fromEmail;
    private String password;

    public void promptForCredentials() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter sender email: ");
        fromEmail = scanner.nextLine();
        System.out.print("Enter password (App Password if Gmail): ");
        password = scanner.nextLine();
    }

    public void setCredentials(String email, String password) {
        this.fromEmail = email;
        this.password = password;
    }

    public boolean isConfigured() {
        return fromEmail != null && password != null;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        if (!isConfigured()) {
            promptForCredentials();
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            logger.info("Email sent to {}", toEmail);
        } catch (MessagingException e) {

            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());

        }
    }
}
