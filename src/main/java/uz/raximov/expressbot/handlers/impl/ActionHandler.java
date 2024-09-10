package uz.raximov.expressbot.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.raximov.expressbot.commands.Actions;
import uz.raximov.expressbot.commands.impl.ComplaintCommand;
import uz.raximov.expressbot.commands.impl.admin.CheckUserCommand;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.handlers.Handler;
import uz.raximov.expressbot.service.ClientActionService;
import uz.raximov.expressbot.util.PhoneNumberValidator;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class ActionHandler implements Handler<Message> {

    private final CheckUserCommand checkUserCommand;

    private final ComplaintCommand complaintCommand;

    private final ClientActionService clientActionService;

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

    }

}

