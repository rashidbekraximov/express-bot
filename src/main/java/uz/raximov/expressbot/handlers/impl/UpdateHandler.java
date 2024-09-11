package uz.raximov.expressbot.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.handlers.Handler;
import uz.raximov.expressbot.service.TelegramService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateHandler implements Handler<Update> {

    private final TelegramService telegramService;

    private final MessageHandler messageHandler;

    private final PhotoHandler photoHandler;

    private final CallbackHandler callbackHandler;

    @Override
    public void handle(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()){
                messageHandler.handle(update.getMessage());
            }else if (message.hasPhoto()){
                photoHandler.handle(message);
            }
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handle(update.getCallbackQuery());
        }
    }
}

