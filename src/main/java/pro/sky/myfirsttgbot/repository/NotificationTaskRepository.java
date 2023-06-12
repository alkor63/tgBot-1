package pro.sky.myfirsttgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.myfirsttgbot.entity.NotificationTask;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

}
