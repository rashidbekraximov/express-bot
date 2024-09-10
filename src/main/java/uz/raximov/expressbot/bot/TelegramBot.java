package uz.raximov.expressbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.raximov.expressbot.handlers.impl.UpdateHandler;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UpdateHandler updateHandler;

    public TelegramBot(UpdateHandler updateHandler){
        this.updateHandler = updateHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handle(update);
    }

    @Override
    public String getBotUsername() {
        return Data.username;
    }

    @Override
    public String getBotToken() {
        return Data.token;
    }

}
