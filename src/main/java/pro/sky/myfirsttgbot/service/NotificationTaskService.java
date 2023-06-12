package pro.sky.myfirsttgbot.service;

import org.springframework.stereotype.Service;
import pro.sky.myfirsttgbot.entity.NotificationTask;
import pro.sky.myfirsttgbot.repository.NotificationTaskRepository;

@Service
public class NotificationTaskService {
 private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }
    public void save(NotificationTask notificationTask) {
        notificationTaskRepository.save(notificationTask);
    }
}
