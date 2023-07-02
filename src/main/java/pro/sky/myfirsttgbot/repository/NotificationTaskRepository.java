package pro.sky.myfirsttgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.myfirsttgbot.entity.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findAllByNotificationDateTime(LocalDateTime localDateTime);
//    List<NotificationTask> findAllByNotificationDateTimeAAndChatId(LocalDateTime localDateTime, long chatId);


//    @Query(value = "SELECT nt FROM notification_task nt WHERE nt.chat_id user u ON nt.user_id = u.id WHERE u.name like %:nameLike%", nativeQuery = true)
//    List<NotificationTask> findAllByUserNameLike(@Param("nameLike") String nameLike);
//    @Query(value = "SELECT nt.* FROM notification_task nt INNER JOIN user u ON nt.user_id = u.id WHERE u.name like %:nameLike%", nativeQuery = true)
//    List<NotificationTask> findAllByNameLike(@Param("nameLike") String nameLike);
//    @Modifying
//    @Query("DELETE FROM NotificationTask WHERE message like %:nameLike%")
//    void removeAllLike(@Param("nameLike") String nameLike);


}
