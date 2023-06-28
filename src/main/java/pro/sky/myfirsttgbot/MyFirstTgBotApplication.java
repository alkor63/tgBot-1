package pro.sky.myfirsttgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class MyFirstTgBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstTgBotApplication.class, args);
    }

}
