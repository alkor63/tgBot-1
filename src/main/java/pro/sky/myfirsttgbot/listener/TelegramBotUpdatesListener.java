package pro.sky.myfirsttgbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.sky.myfirsttgbot.entity.NotificationTask;
import pro.sky.myfirsttgbot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final Pattern pattern = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2})\\s+([A-я\\d\\s*.,@!%:;_?&#$-]+)");
//    private final Pattern pattern = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{2})\\s+([A-я\\d\s+.,@!*%:;?&#$]+)");
//    private final Pattern pattern = Pattern.compile("([0-9\\s.:]{16})(\\s)(\\W+]+)");

//    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
    private final TelegramBot telegramBot;
    private final NotificationTaskService notificationTaskService;
    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskService notificationTaskService)
{        this.telegramBot = telegramBot;
    this.notificationTaskService = notificationTaskService;
}

//    @Autowired
//    private TelegramBot telegramBot;   // через конструктор как на 5 строк выше, понятнее
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates
                    .stream().filter(update -> update.message() != null)
        .forEach(update -> {
    logger.info("Обрабатываем обновление (update): {}",update);
                // Process your updates here
        Message message = update.message();
    Long chatId = message.chat().id();
        String text = message.text();

    if("/start".equals(text)) {
        sendMessage(chatId, """
                 ~:) Привет!
                Я помогу тебе запланировать задачу.
                Отправь её в формате: 28.06.2023 12:35
                """);
    } else if (text != null) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            LocalDateTime dateTime = parse(matcher.group(1));
//            Instant dateTime = parse(matcher.group(1));
            System.out.println("first group from message = "+ matcher.group(1));
            System.out.println("dataTime from message = "+ dateTime);

            dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            System.out.println("dataTime from method NOW() = "+ dateTime);

            if (isNull(dateTime)) {
                sendMessage(chatId, " Некорректный формат даты и/или времени. должно быть - 28.06.2023 11:53 ");
            } else {
                String txt = matcher.group(2);
                System.out.println("на входе получили txt = "+txt);
                NotificationTask notificationTask = new NotificationTask();
                notificationTask.setChatId(chatId);
                notificationTask.setMessage(txt);
                notificationTask.setNotificationDateTime(dateTime);
                notificationTaskService.save(notificationTask);
                sendMessage(chatId, " Мы успешно запланировали задачу !");
            }
            }else{
                sendMessage(chatId, " *** Что-то не то!!! Некорректный формат/язык сообщения ");
            }
        }
    });
        }        catch (Exception e){
            logger.error(e.getMessage(), e);
        }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    @Nullable
    private LocalDateTime parse(String dateTime) {
//    private Instant parse(String dateTime) {
        System.out.println(" в метод parse пришло значение dateTime = "+dateTime);
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//            return Instant.parse(dateTime);
        } catch (DateTimeParseException e){
        return null;
    }
    }
    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId,message);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Ошибка при попытке отправить сообщение: {}",sendResponse.description());
        }
    }
}