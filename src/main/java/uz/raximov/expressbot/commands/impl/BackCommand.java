package uz.raximov.expressbot.commands.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.raximov.expressbot.commands.Actions;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.commands.impl.admin.AdminCommand;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.service.ClientActionService;

import java.util.Objects;


@Component
public class BackCommand implements Command<Message> {

    @Resource
    private ClientActionService clientActionService;

    @Resource
    private GeneralCommand generalCommand;

    @Resource
    private AdminCommand adminCommand;

    @Transactional
    @Override
    public void execute(Message message, Boolean isAdmin) {
        Long chatId = message.getChatId();
        String text = message.getText();
        ClientActionDto action = clientActionService.findLastActionByChatId(chatId);
        if (action == null) {
            backAll(chatId, isAdmin);
        }

//        if (action.getAction().equals(Actions.SETTING_ACTION)) {
//            backAll(chatId, locale);
//            return;
//        }
//
//        if (Objects.equals(action.getAction(), Actions.CREDIT_GRAPH_ACTION) && action.getClient() != null) {
//            clientActionService.saveAction(Actions.MAIN_MENU_ACTION, chatId, action.getClient().getId());
//            backAll(chatId, isAdmin);
//            return;
//        }

        if (Objects.equals(action.getAction(), Actions.ADMIN) && action.getClient() != null) {
            backAll(chatId, isAdmin);
            return;
        }

        if (Objects.equals(action.getAction(), Actions.ADD_ADMIN) && action.getClient() != null) {
            clientActionService.saveAction(Actions.ADMIN, chatId, action.getClient().getId());
            adminCommand.execute(chatId, isAdmin);
            return;
        }

        if (Objects.equals(action.getAction(), Actions.ADMIN_LIST) && action.getClient() != null) {
            clientActionService.saveAction(Actions.ADMIN, chatId, action.getClient().getId());
            adminCommand.execute(chatId, isAdmin);
            return;
        }

        if (Objects.equals(action.getAction(), Actions.REQUEST_ADMIN_ID) && action.getClient() != null) {
            clientActionService.saveAction(Actions.ADMIN, chatId, action.getClient().getId());
            adminCommand.execute(chatId, isAdmin);
            return;
        }

        backAll(chatId, isAdmin);
    }

    private void backAll(Long chatId, Boolean isAdmin) {
        generalCommand.execute(chatId, isAdmin);
        return;
    }
}
