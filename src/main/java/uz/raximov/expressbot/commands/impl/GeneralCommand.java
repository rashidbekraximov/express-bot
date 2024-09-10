package uz.raximov.expressbot.commands.impl;

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
public class GeneralCommand implements Command<Long> {

    @Resource
    private TelegramService telegramService;

    @Override
    public void execute(Long chatId,Boolean isAdmin) {
        telegramService.sendMessage(new MessageSend(chatId,"\uD83C\uDFE0 Bosh menyu", createGeneralMenuKeyboard(isAdmin)));
    }

    public ReplyKeyboardMarkup createGeneralMenuKeyboard(boolean isAdmin) {
        if (isAdmin){
            return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
                add(new KeyboardRow() {{
                    add("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB Admin");
                    add("\uD83D\uDCCA Statistika");
                }});
                add(new KeyboardRow() {{
                    add("\uD83D\uDECD Buyurtma qo'shish");
                }});
                add(new KeyboardRow() {{
                    add("\uD83D\uDCDD Buyurtma statusini o’zgartirish");
                }});
            }});
        }else{
            return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
                add(new KeyboardRow() {{
                    add("\uD83D\uDCCB Ma'lumotlarim");
                    add("\uD83D\uDECD Buyurtmalarim");
                }});
                add(new KeyboardRow() {{
                    add("\uD83D\uDCF1 A'loqa");
                }});
            }});
        }
    }
}
