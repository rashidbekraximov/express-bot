package uz.raximov.expressbot.commands.impl.order;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.commands.impl.Commands;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.TelegramService;

@Component
public class RequestPhotoCommand implements Command<Long> {

    @Resource
    private TelegramService telegramService;

    @Override
    public void execute(Long chatId, Boolean isAdmin) {
        telegramService.sendMessage(new MessageSend(chatId, "\uD83D\uDDBC Rasmni yuklang:", Commands.backOnly()));
    }
}
