package uz.raximov.expressbot.commands.impl;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.TelegramService;

@Component
public class ComplaintCommand implements Command<Long> {

    @Resource
    private TelegramService telegramService;

    @Override
    public void execute(Long chatId, Boolean isAdmin) {
        telegramService.sendMessage(new MessageSend(chatId, "⚠️ Kiritilgan format noto'g'ri berilgan formatda kiriting",Commands.backOnly()));
    }
}
