package uz.raximov.expressbot.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.raximov.expressbot.commands.Actions;
import uz.raximov.expressbot.commands.impl.Commands;
import uz.raximov.expressbot.commands.impl.ComplaintCommand;
import uz.raximov.expressbot.commands.impl.GlobalRequestCommand;
import uz.raximov.expressbot.commands.impl.admin.CheckUserCommand;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.handlers.Handler;
import uz.raximov.expressbot.service.ClientActionService;
import uz.raximov.expressbot.service.TelegramService;
import uz.raximov.expressbot.util.PhoneNumberValidator;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class ActionHandler implements Handler<Message> {

    private final CheckUserCommand checkUserCommand;

    private final ComplaintCommand complaintCommand;

    private final GlobalRequestCommand globalRequestCommand;

    private final ClientActionService clientActionService;

    private final TelegramService telegramService;

    @Transactional
    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();
        boolean isAdmin = true;
        ClientActionDto action = clientActionService.findLastActionByChatId(chatId);

        //TODO Currency Conversation Value Handle
        if (Objects.equals(action.getAction(), Actions.ADD_ADMIN) && action.getClient() != null) {
            if (PhoneNumberValidator.isValidPhoneNumber(text)){
                checkUserCommand.execute(chatId,text,null,isAdmin);
            }else{
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ORDER_COUNT_ACTION) && action.getClient() != null) {
            String number = text.replaceAll(" ", "").replaceAll(",", "");
            Double objectQty = null;
            try {
                objectQty = Double.valueOf(number);
                clientActionService.saveAction(Actions.REQUEST_ORDER_CLIENT_ID_ACTION, chatId, action.getClient().getId());
                //Buyurtma soni
                globalRequestCommand.execute(chatId,"\uD83C\uDD94 Klient ID",null, isAdmin);
            } catch (Exception e) {
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ORDER_CLIENT_ID_ACTION) && action.getClient() != null) {
            String number = text.replaceAll(" ", "").replaceAll(",", "");
            Double objectQty = null;
            try {
                objectQty = Double.valueOf(number);
                clientActionService.saveAction(Actions.REQUEST_ORDER_WEIGHT_ACTION, chatId, action.getClient().getId());
                //Klient ID
                globalRequestCommand.execute(chatId,"⚖️ Vazni:",null, isAdmin);
            } catch (Exception e) {
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ORDER_WEIGHT_ACTION) && action.getClient() != null) {
            String number = text.replaceAll(" ", "").replaceAll(",", "");
            Double objectQty = null;
            try {
                objectQty = Double.valueOf(number);
                clientActionService.saveAction(Actions.REQUEST_ORDER_VOLUME_ACTION, chatId, action.getClient().getId());
                //Vazn
                globalRequestCommand.execute(chatId,"\uD83D\uDD30 Hajmi:",null, isAdmin);
            } catch (Exception e) {
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ORDER_VOLUME_ACTION) && action.getClient() != null) {
            String number = text.replaceAll(" ", "").replaceAll(",", "");
            Double objectQty = null;
            try {
                objectQty = Double.valueOf(number);
                clientActionService.saveAction(Actions.REQUEST_ORDER_PRICE_ACTION, chatId, action.getClient().getId());
                //Hajm
                globalRequestCommand.execute(chatId,"\uD83D\uDCB0 Narxi:",null, isAdmin);
            } catch (Exception e) {
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ORDER_PRICE_ACTION) && action.getClient() != null) {
            String number = text.replaceAll(" ", "").replaceAll(",", "");
            Double objectQty = null;
            try {
                objectQty = Double.valueOf(number);
                clientActionService.saveAction(Actions.REQUEST_ORDER_FLIGHT_ACTION, chatId, action.getClient().getId());
                //Narxi
                globalRequestCommand.execute(chatId,"\uD83D\uDEE9 Reys raqami:",null, isAdmin);
            } catch (Exception e) {
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ORDER_FLIGHT_ACTION) && action.getClient() != null) {
            String number = text.replaceAll(" ", "").replaceAll(",", "");
            Double objectQty = null;
            try {
                objectQty = Double.valueOf(number);
                clientActionService.saveAction(Actions.REQUEST_ORDER_STATUS_ACTION, chatId, action.getClient().getId());
                //Reys
                telegramService.sendMessage(new MessageSend(chatId, "<strong>\uD83D\uDD30 Statusni tanlang ?</strong>", Commands.createCatalogInlineKeyboard(Actions.REQUEST_ORDER_STATUS_ACTION)));
            } catch (Exception e) {
                complaintCommand.execute(chatId, isAdmin);
            }
            return;
        }
    }

}

