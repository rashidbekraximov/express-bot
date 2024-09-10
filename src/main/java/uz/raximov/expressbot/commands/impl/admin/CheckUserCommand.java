package uz.raximov.expressbot.commands.impl.admin;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.raximov.expressbot.commands.CollectorCommand;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.ClientService;
import uz.raximov.expressbot.service.TelegramService;
import uz.raximov.expressbot.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CheckUserCommand implements CollectorCommand<Long> {

    @Resource
    private TelegramService telegramService;

    @Resource
    private ClientService clientService;

    @Resource
    private AdminCommand adminCommand;

    @Override
    public void execute(Long chatId,String phone,String variable,Boolean isAdmin) {
        ClientDto clientDto = clientService.findByPhone(phone);
        if (clientDto != null){
            telegramService.sendMessage(new MessageSend(chatId,
                    "<strong>\uD83C\uDD94 : " + clientDto.getTelegramId()
                            + "\n\uD83D\uDE4D\u200D♂️ F.I.SH: " + clientDto.getUserInfo()
                            + "\n\uD83D\uDCDE Tel: " + clientDto.getPhone()
                            + "\n⛳️ Hudud: " + clientDto.getRegion()
                            + "\n❗️ Haqiqatdan ham ushbu foydalanuvchini\n" +
                            "  admin qilishni xoxlaysizmi ?</strong>",
                    createCatalogInlineKeyboard(clientDto.getTelegramId())));
        }else{
            telegramService.sendMessage(new MessageSend(chatId,"❌ Foydalanuvchi topilmadi", adminCommand.createGeneralMenuKeyboard(isAdmin)));
        }
    }

    private InlineKeyboardMarkup createCatalogInlineKeyboard(String telegramId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setCallbackData("action:admin_ha_" + telegramId);
        inlineKeyboardButton1.setText("✅ Ha");
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setCallbackData("action:admin_yo'q_" + telegramId);
        inlineKeyboardButton2.setText("❌ Yo'q");
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(inlineKeyboardButton1,inlineKeyboardButton2)));
        return inlineKeyboardMarkup;
    }
}
