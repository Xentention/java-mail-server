package lr6.mail.javamailserver.service;

import lr6.mail.javamailserver.dto.EmailDto;
import lr6.mail.javamailserver.exception.MailNotSentException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface EmailService {
  void sendSimpleMail(EmailDto entity) throws MailNotSentException;

  List<EmailDto> receiveMail(Integer amount) throws MessagingException, IOException;

  Integer getMessageCount() throws MessagingException;

}
