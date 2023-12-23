package lr6.mail.javamailserver.controller;

import lombok.AllArgsConstructor;
import lr6.mail.javamailserver.dto.EmailDto;
import lr6.mail.javamailserver.exception.MailNotSentException;
import lr6.mail.javamailserver.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@AllArgsConstructor
public class EmailController {

  private EmailService emailService;

  @PostMapping(
      value = "/sendmail",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> sendMail(@RequestBody EmailDto entity)
      throws MailNotSentException {

    emailService.sendSimpleMail(entity);
    return new ResponseEntity<>(
        HttpStatus.CREATED
    );
  }

  @GetMapping(value = "/emails",
  produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> getMail(@RequestParam Integer amount) throws MessagingException, IOException {
    JSONObject response = new JSONObject();
    response.put("countAll", emailService.getMessageCount());
    response.put("messages", emailService.receiveMail(amount));

    return new ResponseEntity<>(
        response.toString(),
        HttpStatus.OK
    );
  }

  @ExceptionHandler(MailNotSentException.class)
  public ResponseEntity<Object> handleMailNotSentExc(Exception e) {
    return new ResponseEntity<>(
        new JSONObject("Error", e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }
}