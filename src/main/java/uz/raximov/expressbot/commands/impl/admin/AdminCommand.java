package uz.raximov.expressbot.commands.impl.admin;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.TelegramService;
import uz.raximov.expressbot.util.KeyboardUtils;

import java.util.ArrayList;

@Component
public class AdminCommand implements Command<Long> {

    @Resource
    private TelegramService telegramService;

    @Override
    public void execute(Long chatId,Boolean isAdmin) {
        telegramService.sendMessage(new MessageSend(chatId,"\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB Admin", createGeneralMenuKeyboard(isAdmin)));
    }

    public ReplyKeyboardMarkup createGeneralMenuKeyboard(boolean isAdmin) {
            return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
                add(new KeyboardRow() {{
                    add("➕ Admin qo'shish");
                    add("\uD83D\uDCC1 Adminlar ro'yxati");
                }});
                add(new KeyboardRow() {{
                    add("\uD83D\uDDC2 Foydalanuvchilar ro'yxati");
                }});
                add(new KeyboardRow() {{
                    add("⬅ Orqaga");
                }});
            }});
    }
}
