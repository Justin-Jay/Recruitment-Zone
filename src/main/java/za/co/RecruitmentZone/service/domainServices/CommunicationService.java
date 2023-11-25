package za.co.RecruitmentZone.service.domainServices;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.domain.ContactMessage;

import java.io.IOException;
import java.util.Properties;


@Slf4j
@Component
public class CommunicationService {

    @Value("${spring.mail.username}")
    String kiungaMailAddress;
    @Value("${spring.mail.password}")
    String kiungaMailPassword;

    private final JavaMailSender javaMailSender;
    public CommunicationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleEmail(ContactMessage websiteMessage) {
        log.info("Sending simple Email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(kiungaMailAddress);
        message.setTo(websiteMessage.getToEmail());
        message.setText(websiteMessage.getMessageBody());
        log.info("websiteMessage subject"+websiteMessage.getSubject());
        message.setSubject(websiteMessage.getSubject());
        log.info("Sending message"+message);
        javaMailSender.send(message);
        log.info("Email Sent...");
    }

/*    public void forwardMessage(Message message) {
       try { String recipientAddress = "websitequeries@kiunga.co.za";

           JavaMailSenderImpl mailSender = getJavaMailSender();

           MimeMessage mimeMessage = mailSender.createMimeMessage();
           MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
           messageHelper.setFrom(kiungaMailAddress);
           messageHelper.setTo(recipientAddress);
           messageHelper.setSubject("Fwd: " + message.getSubject());
           messageHelper.setText("Forwarded message \n"
                   + "From: " + message.getFrom()[0] + "\n"
                   + "Date: " + message.getSentDate() + "\n"
                   + "Subject: " + message.getSubject() + "\n"
                   + "\n"
                   + message.getContent(), true);
           mailSender.send(mimeMessage);
       }
       catch (MessagingException | IOException exception){
           log.info(exception.getMessage());
       }
    }*/


/*    public void deleteEmailBySender(String senderEmail) {
      try {  String recipientAddress = "justinjay87@gmail.com";
          // Get a new Session object
          Session session = Session.getInstance(getMailProperties());
          // Connect to the store using the given username and password
          Store store = session.getStore("imap");
          store.connect(IMAP, kiungaMailAddress, kiungaMailPassword);
          // Get the Inbox folder and open it in READ-WRITE mode
          Folder inbox = store.getFolder("INBOX");
          inbox.open(Folder.READ_WRITE);
          // Get all messages from the Inbox folder sent by the given sender email
          Message[] messages;
          log.info("About to search inbox ");
          messages = inbox.search(new FromTerm(new InternetAddress(senderEmail)));
          log.info("Done Searching inbox ");

          if (messages!=null){
              // write the messages to a file
             // mailFileWriter(messages);
              for (Message message : messages) {
                  // Forward the message
                  log.info("Forwarding Email to archive");

                  forwardMessage(message);

                  log.info("Moving Email to Trash Folder");
                  Folder deleted = store.getFolder("[Gmail]/Trash");
                  inbox.copyMessages(new Message[]{message}, deleted);
                  log.info("Email moved to Trash");
                  // remove the break; statement in order to start the process of looping through more than one email.
                  // Leaving break; statement will only complete the process for the first email found in the mailbox.
                  break;
                  }
          }
          else {
              log.info("Email from {} Not found",senderEmail);
          }
          // Close the Inbox folder and the store
          log.info("Closing The inbox and store");
          inbox.close(false);
          store.close();
      }
      catch (MessagingException exception){
          log.debug(exception.getMessage());
      }
    }*/


/*   public void mailFileWriter(Message[] messages) throws MessagingException {
        log.info("Printing mail from inbox ");

        String[] header = {"date", "mailFrom", "Subject"};

        String[][] data = new String[messages.length][3];

        int i = 0;
        for(Message message: messages)
        {
            data[i][0] = String.valueOf(message.getSentDate());
            data[i][1] = Arrays.toString(message.getFrom());
            data[i][2] = message.getSubject();
            i++;
        }

        // Combine the header and data arrays into a single two-dimensional array
        List<String[]> csvData = new ArrayList<>();
        for(Message message: messages) {
            String[] row = new String[3];
            row[0] = String.valueOf(message.getSentDate());
            row[1] = Arrays.toString(message.getFrom());
            row[2] = message.getSubject();
            csvData.add(row);
        }
    }*/






}
