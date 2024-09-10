package uz.raximov.expressbot.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.raximov.expressbot.commands.Actions;
import uz.raximov.expressbot.commands.impl.admin.AdminCommand;
import uz.raximov.expressbot.commands.impl.admin.AssignAdminCommand;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.handlers.Handler;
import uz.raximov.expressbot.service.ClientActionService;
import uz.raximov.expressbot.service.ClientService;
import uz.raximov.expressbot.service.TelegramService;

@Service
@RequiredArgsConstructor
class CallbackHandler implements Handler<CallbackQuery> {


    private final ClientService clientService;

    private final TelegramService telegramService;

    private final AssignAdminCommand assignAdminCommand;

    private final AdminCommand adminCommand;

    private final ClientActionService clientActionService;

    @Override
    public void handle(CallbackQuery callbackQuery) {
            handleMessage(callbackQuery);
    }

    private void handleMessage(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        Long chatId = message.getChatId();
        Integer messageId = message.getMessageId();
        long userId = callbackQuery.getFrom().getId();
        boolean isAdmin = true;
        ClientDto client = null;
        if (message.getFrom() != null)
            client = clientService.findClientByUserId(userId);

        String data = callbackQuery.getData();

        if (data.startsWith("action:admin_")) {
            clientActionService.saveAction(Actions.ADMIN, chatId, client.getId());
            telegramService.deleteMessage(new DeleteMessage(String.valueOf(chatId), messageId));
            if (data.startsWith("action:admin_ha_")){
                assignAdminCommand.execute(chatId, data.replace("action:admin_ha_",""), "",isAdmin);
            }else{
                adminCommand.execute(chatId,isAdmin);
            }
        }
    }
}
