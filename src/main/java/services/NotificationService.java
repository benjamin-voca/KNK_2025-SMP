package services;

import models.Notifications;
import models.dto.CreateNotificationDto;
import models.dto.UpdateNotificationDto;
import repository.NotificationRepository;

import java.util.List;

public class NotificationService extends BaseService<Notifications, CreateNotificationDto, UpdateNotificationDto> {

    private final NotificationRepository notificationRepository;

    public NotificationService() {
        super(new NotificationRepository());
        this.notificationRepository = (NotificationRepository) super.repository;
    }

    @Override
    public Notifications getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Notifications create(CreateNotificationDto createNotificationDto) throws Exception {
        return super.create(createNotificationDto);
    }

    @Override
    public Notifications update(UpdateNotificationDto updateNotificationDto) throws Exception {
        return super.update(updateNotificationDto);
    }

    public List<Notifications> fetchNotifications() {
        return notificationRepository.fetchNotifications();
    }
}