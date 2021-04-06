package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Mail;

public interface EmailNotificationService {

    public void sendEmail(final Mail mail);
}
