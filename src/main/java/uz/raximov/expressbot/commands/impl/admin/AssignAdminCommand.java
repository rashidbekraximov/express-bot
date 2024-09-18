package uz.raximov.expressbot.commands.impl.admin;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.CollectorCommand;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.service.ClientService;
import uz.raximov.expressbot.service.TelegramService;


@Component
public class AssignAdminCommand implements CollectorCommand<Long> {

    @Resource
    private TelegramService telegramService;

    @Resource
    private ClientService clientService;

    @Resource
    private AdminCommand adminCommand;

    @Override
    public void execute(Long chatId,String id,String variable,Boolean isAdmin) {
        ClientDto adminDto = clientService.findClientByUserId(chatId);
        ClientDto clientDto = clientService.makeAdmin(Long.valueOf(id),chatId);    // TODO beda shu bow string ni Longa ozgartirib barvardi
        if (clientDto != null){
            telegramService.sendMessage(new MessageSend(Long.valueOf(clientDto.getTelegramId()),
                    "<strong>\uD83C\uDD94 : " + clientDto.getTelegramId()
                            + "\n\uD83D\uDE4D\u200D♂️ F.I.SH: " + clientDto.getUserInfo()
                            + "\n\uD83D\uDCDE Tel: " + clientDto.getPhone()
                            + "\n⛳️ Hudud: " + clientDto.getRegion()
                            + "\n✅ Siz " + adminDto.getUserInfo() + " tomonidan 'admin' qilib tayinlandingiz \uD83C\uDF8A\uD83C\uDF8A\uD83C\uDF89 </strong>"));
            telegramService.sendMessage(new MessageSend(chatId,
                    "<strong>\uD83C\uDD94 : " + clientDto.getTelegramId()
                            + "\n\uD83D\uDE4D\u200D♂️ F.I.SH: " + clientDto.getUserInfo()
                            + "\n\uD83D\uDCDE Tel: " + clientDto.getPhone()
                            + "\n⛳️ Hudud: " + clientDto.getRegion()
                            + "\n✅ Admin tayinlandi </strong>", adminCommand.createGeneralMenuKeyboard(isAdmin)));
        }else{
            telegramService.sendMessage(new MessageSend(chatId,"❌ Foydalanuvchi topilmadi", adminCommand.createGeneralMenuKeyboard(isAdmin)));
        }
    }
}
