package net.siudek.smtpsender;

import java.util.Properties;

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

        var port = Integer.toString(587); // 25 default on other servers
        var server = "smtp.gmail.com";

        var systemProps = System.getProperties();
        var prop = (Properties) systemProps.clone();
        prop.put("mail.smtp.host", server);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.transport.protocol", "smtp");

        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.starttls.required", "true");
        
        // to solve: javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // to see in logs more SMTP-related info
        prop.put("mail.debug", "true");


        var session = Session.getInstance(prop);
        var msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailFrom));
        msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(mailTo)
        );
        
        msg.setSubject("Testing SMTP over TLS");
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