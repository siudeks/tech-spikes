package net.siudek.smtpsender;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import lombok.Cleanup;

@Component
class SendEmailTask implements ApplicationRunner, ApplicationContextAware {

    @Value("${mailFrom}")
    private String mailFrom;

    @Value("${mailTo}")
    private String mailTo;

    @Value("${password}")
    private String password;
    
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.starttls.required", "true");
        prop.put("mail.transport.protocol", "smtp");

        var session = Session.getInstance(prop);
        var msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailFrom));
        msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(mailTo)
        );
        
        msg.setSubject("Testing Gmail TLS");
        msg.setText("Dear Mail Crawler, Please do not spam my email!");
        msg.saveChanges();

        @Cleanup
        var transport = session.getTransport();
        transport.connect(mailFrom, password);
        transport.sendMessage(msg, msg.getAllRecipients());

        System.out.println("Done");

        applicationContext.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

}