package uz.raximov.expressbot.commands.impl.admin;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.commands.impl.Commands;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.TelegramService;

@Component
public class RequestPhoneNumberCommand implements Command<Long> {

    @Resource
    private TelegramService telegramService;

    @Override
    public void execute(Long chatId, Boolean isAdmin) {
        telegramService.sendMessage(new MessageSend(chatId, "\uD83D\uDCF1 Telefon raqamini kiriting: \n +998991234567", Commands.backOnly()));
    }
}
