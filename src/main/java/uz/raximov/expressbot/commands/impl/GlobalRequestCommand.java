package uz.raximov.expressbot.commands.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.CollectorCommand;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.TelegramService;

@Component
public class GlobalRequestCommand implements CollectorCommand<Long> {

    @Resource
    private TelegramService telegramService;

    @Override
    public void execute(Long chatId,String text, String variable, Boolean isAdmin) {
        telegramService.sendMessage(new MessageSend(chatId, text, Commands.backOnly()));
    }
}
