package lr6.mail.javamailserver.service.impl;

import com.sun.mail.imap.IMAPFolder;
import lr6.mail.javamailserver.dto.EmailDto;
import lr6.mail.javamailserver.exception.MailNotSentException;
import lr6.mail.javamailserver.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender javaMailSender;

  private final IMAPFolder inbox;

  public EmailServiceImpl(JavaMailSender javaMailSender,
                          Store store) throws MessagingException {
    this.javaMailSender = javaMailSender;

    inbox = (IMAPFolder) store.getFolder("INBOX");
    inbox.open(Folder.READ_WRITE);
  }

  @Override
  public void sendSimpleMail(EmailDto entity) throws MailNotSentException {
    try {
      SimpleMailMessage mailMessage
          = new SimpleMailMessage();

      mailMessage.setFrom(entity.getSender());
      mailMessage.setTo(entity.getRecipient());
      mailMessage.setText(entity.getMessage());
      mailMessage.setSubject(entity.getSubject());

      javaMailSender.send(mailMessage);

    } catch (Exception e) {
      e.printStackTrace();
      throw new MailNotSentException("Mail not send");
    }
  }

  @Override
  public List<EmailDto> receiveMail(Integer amount) throws MessagingException {

    List<EmailDto> emails = new ArrayList<>();
    Message[] messages = inbox.getMessages(1,Math.min(inbox.getMessageCount(),5));
      System.out.println("Length " + messages.length);
    for (int i = 0; i < amount; ++i) {
      System.out.println(i);
      EmailDto emailDto = new EmailDto();
      emailDto.setSender(messages[i].getFrom()[0].toString());
      emailDto.setSubject(messages[i].getSubject());
      emailDto.setDate(messages[i].getSentDate());
      emails.add(emailDto);
    }

    return emails;
  }

  @Override
  public Integer getMessageCount() throws MessagingException {
    return inbox.getMessageCount();
  }
}
