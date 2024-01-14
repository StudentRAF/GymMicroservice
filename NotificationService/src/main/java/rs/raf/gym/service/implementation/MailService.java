/*
 * Copyright (C) 2024. Lazar Dobrota and Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.gym.service.implementation;

import jakarta.jms.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import rs.raf.gym.commons.dto.notification.NotificationBodyDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.commons.message_broker.Converter;
import rs.raf.gym.commons.message_broker.MailFormat;
import rs.raf.gym.exception.ExceptionType;
import rs.raf.gym.model.Notification;
import rs.raf.gym.model.NotificationType;
import rs.raf.gym.repository.NotificationRepository;
import rs.raf.gym.repository.NotificationTypeRepository;
import rs.raf.gym.service.IMailService;

import java.text.MessageFormat;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class MailService implements IMailService {

    private final JavaMailSender mailSender;
    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationRepository notificationRepository;
    private final String gymName = "Gym Invader";
    private final Converter converter;

    @Override
    public String registerMail(NotificationBodyDto notificationBodyDto) throws GymException {
        NotificationType notificationType = notificationTypeRepository.findByName(MailFormat.REGISTER_MAIL.getName()).
                orElseThrow(() -> new GymException(ExceptionType.MAIL_FORMAT_UNKNOWN, MailFormat.REGISTER_MAIL.getName()));

        UserDto userDto = notificationBodyDto.getUser();
        String token = notificationBodyDto.getToken();
        //todo send it to client /active instead of user
        String mailBody = MessageFormat.format(notificationType.getPattern(),
                userDto.getFirstname(), gymName, "http://localhost:8000/api/user/activate/" + token, gymName);
        sendMail(userDto.getEmail(), notificationBodyDto.getSubject(), mailBody);

        //Create new notification
        createNotification(notificationBodyDto.getUserId(), notificationType);

        return mailBody;
    }

    @Override
    public String requestTraining(NotificationBodyDto notificationBodyDto) throws GymException {
        NotificationType notificationType = notificationTypeRepository.findByName(MailFormat.REQUEST_TRAINING.getName()).
                orElseThrow(() -> new GymException(ExceptionType.MAIL_FORMAT_UNKNOWN, MailFormat.REQUEST_TRAINING.getName()));

        UserDto userDto = notificationBodyDto.getUser();
        String trainingName = notificationBodyDto.getTrainingName();
        LocalDate date = notificationBodyDto.getTrainingDate();
        String mailBody = MessageFormat.format(notificationType.getPattern(),
                userDto.getFirstname(), trainingName, date);
        sendMail(userDto.getEmail(), notificationBodyDto.getSubject(), mailBody);

        //Create new notification
        createNotification(notificationBodyDto.getUserId(), notificationType);

        return mailBody;
    }

    @Override
    public String changePassword(NotificationBodyDto notificationBodyDto) throws GymException{
        NotificationType notificationType = notificationTypeRepository.findByName(MailFormat.CHANGE_PASSWORD.getName()).
                orElseThrow(() -> new GymException(ExceptionType.MAIL_FORMAT_UNKNOWN, MailFormat.CHANGE_PASSWORD.getName()));

        UserDto userDto = notificationBodyDto.getUser();
        String mailBody = MessageFormat.format(notificationType.getPattern(),
                userDto.getFirstname(), gymName);
        sendMail(userDto.getEmail(), notificationBodyDto.getSubject(), mailBody);

        //Create new notification
        createNotification(notificationBodyDto.getUserId(), notificationType);

        return mailBody;
    }

    @Override
    public String cancelTraining(NotificationBodyDto notificationBodyDto) throws GymException{
        NotificationType notificationType = notificationTypeRepository.findByName(MailFormat.CANCELED_TRAINING.getName()).
                orElseThrow(() -> new GymException(ExceptionType.MAIL_FORMAT_UNKNOWN, MailFormat.CANCELED_TRAINING.getName()));

        UserDto userDto = notificationBodyDto.getUser();
        String trainingName = notificationBodyDto.getTrainingName();
        LocalDate date = notificationBodyDto.getTrainingDate();
        String mailBody = MessageFormat.format(notificationType.getPattern(),
                userDto.getFirstname(), trainingName, date);
        sendMail(userDto.getEmail(), notificationBodyDto.getSubject(), mailBody);

        //Create new notification
        createNotification(notificationBodyDto.getUserId(), notificationType);

        return mailBody;
    }

    @Override
    public String trainingReminder(NotificationBodyDto notificationBodyDto) throws GymException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        NotificationType notificationType = notificationTypeRepository.findByName(MailFormat.REMINDER.getName()).
                orElseThrow(() -> new GymException(ExceptionType.MAIL_FORMAT_UNKNOWN, MailFormat.REMINDER.getName()));

        UserDto userDto = notificationBodyDto.getUser();
        String trainingName = notificationBodyDto.getTrainingName();
        LocalDate date = notificationBodyDto.getTrainingDate();
        String mailBody = MessageFormat.format(notificationType.getPattern(),
                userDto.getFirstname(), trainingName,
                date);
        sendMail(userDto.getEmail(), notificationBodyDto.getSubject(), mailBody);

        //Create new notification
        createNotification(notificationBodyDto.getUserId(), notificationType);

        return mailBody;
    }

    @SneakyThrows
    private void sendMail(String email, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(mimeMessage);
    }

    private void createNotification(Long userId, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(notificationType);
        notificationRepository.save(notification);
    }

    @JmsListener(destination = "${queue.location}", concurrency = "5-10")
    private void listener(Message message) {
        NotificationBodyDto notificationBodyDto = converter.deserialize(message, NotificationBodyDto.class);

        switch (notificationBodyDto.getMailFormat()) {
            case REGISTER_MAIL -> ExceptionUtils.handleException(() -> registerMail(notificationBodyDto));
            case REQUEST_TRAINING -> ExceptionUtils.handleException(() -> requestTraining(notificationBodyDto));
            case CHANGE_PASSWORD -> ExceptionUtils.handleException(() -> changePassword(notificationBodyDto));
            case CANCELED_TRAINING -> ExceptionUtils.handleException(() -> cancelTraining(notificationBodyDto));
            case REMINDER -> ExceptionUtils.handleException(() -> trainingReminder(notificationBodyDto));
        }
    }

}
