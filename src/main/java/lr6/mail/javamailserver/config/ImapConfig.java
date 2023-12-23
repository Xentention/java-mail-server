package lr6.mail.javamailserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.*;
import java.util.Properties;

@Configuration
public class ImapConfig {

  @Value("${mail.imaps.host}")
  private String emailHost;

  @Value("${mail.imaps.port}")
  private String emailPort;

  @Value("${mail.store.protocol}")
  private String protocol;

  @Value("${mail.imap.ssl.enable}")
  private String ssl;

  @Value("${mail.imap.auth.mechanisms}")
  private String auth;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Bean
  public Session mailSession() {
    Properties props = new Properties();
    props.put("mail.store.protocol", protocol);
    props.put("mail.imaps.host", emailHost);
    props.put("mail.imaps.port", emailPort);
    props.put("mail.imap.ssl.enable", ssl);
    props.put("mail.imap.auth.mechanisms", auth);

    Session session = Session.getDefaultInstance(props, null);
    session.setDebug(true);

    return session;
  }

  @Bean
  public Store getStore(Session session) throws MessagingException {
    Store store = session.getStore("imaps");
    store.connect(emailHost, Integer.parseInt(emailPort), username, password);
    return store;
  }

}
