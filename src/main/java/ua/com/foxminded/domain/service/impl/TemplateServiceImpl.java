package ua.com.foxminded.domain.service.impl;//package ua.com.foxminded.domain.service.impl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Service;
//import ua.com.foxminded.domain.entity.Mail;
//import ua.com.foxminded.domain.service.EmailNotificationService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class TemplateServiceImpl implements ApplicationRunner {
//
//    private static Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);
//    private final EmailNotificationService emailNotificationService;
//
//    @Autowired
//    public TemplateServiceImpl(EmailNotificationService emailNotificationService) {
//        this.emailNotificationService = emailNotificationService;
//    }
//
//    @Override
//    public void run(ApplicationArguments applicationArguments) throws Exception {
//        log.info("Sending Email with Freemarker HTML Template");
//
//        Mail mail = new Mail();
//        mail.setFrom("shesteroid@gmail.com");
//        mail.setTo("shesteroid@gmail.com");
//        mail.setSubject("Sending Email with Freemarker HTML Template Example");
//
//        Map model = new HashMap();
//        for (int i = 0; i < 0; i++) {
//            model.put("scheduleItem.class", 0000);
//            model.put("scheduleItem.schoolSubject", 0000);
//            model.put("scheduleItem.audience", 0000);
//            model.put("scheduleItem.day", 0000);
//        }
//        mail.setModel(model);
//
//        emailNotificationService.sendEmail(mail);
//    }
//}
