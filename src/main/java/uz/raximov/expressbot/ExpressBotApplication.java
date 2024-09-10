package uz.raximov.expressbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.raximov.expressbot.bot.TelegramBot;
import uz.raximov.expressbot.handlers.impl.UpdateHandler;

@EntityScan(value = {"uz.raximov.expressbot.entity"})
@EnableJpaRepositories(value = {"uz.raximov.expressbot.repository"})
@SpringBootApplication(scanBasePackages = {"uz.raximov.expressbot"})
public class  ExpressBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(ExpressBotApplication.class, args);
        UpdateHandler up = appContext.getBean(UpdateHandler.class);
        TelegramBot telegramBot = new TelegramBot(up);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            System.out.println("Bot ishga tushdi");
        }catch (TelegramApiException e){
            System.out.println("Bot ishga tushishda xatolik yuzaga keldi !");
        }
    }
}
