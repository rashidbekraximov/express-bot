package uz.raximov.expressbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.TelegramService;


@Component
public class StartCommand implements Command<Long> {

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private GeneralCommand generalCommand;

    @Override
    public void execute(Long chatId ,Boolean isAdmin) {
            telegramService.sendMessage(new MessageSend(chatId, "hI", generalCommand.createGeneralMenuKeyboard(isAdmin)));
    }
}

