package uz.raximov.expressbot.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.raximov.expressbot.commands.Actions;
import uz.raximov.expressbot.commands.impl.BackCommand;
import uz.raximov.expressbot.commands.impl.Commands;
import uz.raximov.expressbot.commands.impl.GeneralCommand;
import uz.raximov.expressbot.commands.impl.StartCommand;
import uz.raximov.expressbot.commands.impl.admin.AdminCommand;
import uz.raximov.expressbot.commands.impl.admin.LoadUserListToExcelCommand;
import uz.raximov.expressbot.commands.impl.admin.RequestPhoneNumberCommand;
import uz.raximov.expressbot.commands.impl.order.RequestPhotoCommand;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.handlers.Handler;
import uz.raximov.expressbot.service.ClientActionService;
import uz.raximov.expressbot.service.ClientService;

@Service
@RequiredArgsConstructor
class MessageHandler implements Handler<Message> {

    private final ActionHandler actionHandler;

    private final LoadUserListToExcelCommand loadUserListToExcelCommand;

    private final ClientService clientService;

    private final ClientActionService clientActionService;

    private final GeneralCommand generalCommand;

    private final AdminCommand adminCommand;

    private final BackCommand backCommand;

    private final RequestPhoneNumberCommand requestPhoneNumberCommand;

    private final RequestPhotoCommand requestPhotoCommand;

//    private final InfoCommand infoCommand;

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();

        boolean isAdmin = true;
        ClientDto client = null;
        if (message.getFrom() != null) {
            client = clientService.findClientByUserId(message.getFrom().getId());
            isAdmin = client != null && client.isAdmin();
        }

//      TODO Entities Command handler
        if (text.startsWith(Commands.START_COMMAND)) {
            clientService.saveUser(message.getFrom());
//            startCommand.execute(chatId,isAdmin);
            generalCommand.execute(chatId,isAdmin);
            clientActionService.clearActions(chatId);
            return;
        }

        ClientActionDto action = clientActionService.findLastActionByChatId(chatId);

        //TODO General handlers
        if (text.equals("⬅ Orqaga")) {
            backCommand.execute(message,isAdmin);
            return;
        }

        //TODO Admin handler
        if (text.equals("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB Admin")) {
            clientActionService.saveAction(Actions.ADMIN, chatId, client.getId());
            adminCommand.execute(chatId,isAdmin);
            return;
        }

        //TODO Add Order
        if (text.equals("\uD83D\uDECD Buyurtma qo'shish")) {
            clientActionService.saveAction(Actions.REQUEST_ORDER_PHOTO_ACTION, chatId, client.getId());
            requestPhotoCommand.execute(chatId,isAdmin);
            return;
        }

        //TODO User list handler
        if (text.equals("\uD83D\uDDC2 Foydalanuvchilar ro'yxati")) {
            clientActionService.saveAction(Actions.ADMIN, chatId, client.getId());
            loadUserListToExcelCommand.execute(chatId,isAdmin);
            return;
        }

        //TODO Add Admin handler
        if (text.equals("➕ Admin qo'shish")) {
            clientActionService.saveAction(Actions.ADD_ADMIN, chatId, client.getId());
            requestPhoneNumberCommand.execute(chatId,isAdmin);
            return;
        }

//        if (text.equals(R.bundle(locale).getString("label.command.settings"))) {
//            clientActionService.saveAction(Actions.SETTING_ACTION, chatId, client.getId());
//            settingsCommand.execute(chatId, locale);
//            return;
//        }

//        if (text.equals(R.bundle(locale).getString("label.info"))) {
//            clientActionService.saveAction(Actions.INFO_ACTION, chatId, client.getId());
//            infoCommand.execute(chatId, locale);
//            return;
//        }

        if (action != null) {
            actionHandler.handle(message);
            return;
        }

        if (text.equals("\uD83C\uDFE0 Bosh menyu")) {
            generalCommand.execute(chatId,isAdmin);
            return;
        }

        generalCommand.execute(chatId,isAdmin);
    }
}


