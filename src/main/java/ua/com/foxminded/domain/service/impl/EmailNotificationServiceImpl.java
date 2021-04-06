package ua.com.foxminded.domain.service.impl;//package ua.com.foxminded.domain.service.impl;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import ua.com.foxminded.domain.entity.Mail;
//import ua.com.foxminded.domain.service.EmailNotificationService;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class EmailNotificationServiceImpl implements EmailNotificationService {
//
//    private final JavaMailSender mailSender;
//    private final Configuration freeMarkerConfig;
//
//    @Autowired
//    public EmailNotificationServiceImpl(JavaMailSender mailSender, Configuration freeMarkerConfig) {
//        this.mailSender = mailSender;
//        this.freeMarkerConfig = freeMarkerConfig;
//    }
//
//    @Override
//    public void sendEmail(Mail mail) {
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message,
//                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                    StandardCharsets.UTF_8.name());
//
//            Template template = freeMarkerConfig.getTemplate("email-template.ftl");
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
//
//            helper.setTo(mail.getTo());
//            helper.setFrom(mail.getFrom());
//            helper.setSubject(mail.getSubject());
//            helper.setText(html, true);
//
//            mailSender.send(message);
//        } catch (MessagingException | IOException | TemplateException e) {
//            e.printStackTrace();
//        }
//    }
//}
