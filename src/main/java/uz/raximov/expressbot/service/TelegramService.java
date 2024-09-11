package uz.raximov.expressbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.raximov.expressbot.bot.Data;
import uz.raximov.expressbot.dto.DocumentSend;
import uz.raximov.expressbot.dto.MessageEdit;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.exception.FailedSendMessageException;


@Service
public class TelegramService extends DefaultAbsSender {

    protected TelegramService() {
        super(new DefaultBotOptions(), Data.token);
    }

    public Message sendMessage(MessageSend message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(message.getText());
        sendMessage.setParseMode("HTML");
        if (message.getKeyboard() != null) {
            sendMessage.setReplyMarkup(message.getKeyboard());
        }
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new FailedSendMessageException(String.format("Failed send text message %s", message), e);
        }
    }

    public void deleteMessage(DeleteMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    public Message sendDocument(DocumentSend documentSend) {
        SendDocument document = new SendDocument();
        document.setChatId(documentSend.getChatId());
        document.setCaption(documentSend.getCaption());
        document.setParseMode("HTML");
        document.setDocument(new InputFile(documentSend.getDocument()));
        if (documentSend.getKeyboard() != null) {
            document.setReplyMarkup(documentSend.getKeyboard());
        }
        try {
            return execute(document);
        } catch (TelegramApiException e) {
            throw new FailedSendMessageException(String.format("Failed send text message %s", document), e);
        }
    }

    public void editMessageText(MessageEdit message) {
        EditMessageText editMessageText = new EditMessageText();
        if (message.getMessageId() != null) {
            editMessageText.setChatId(message.getChatId());
            editMessageText.setMessageId(message.getMessageId());
        } else {
            editMessageText.setInlineMessageId(message.getInlineMessageId());
        }
        editMessageText.setText(message.getText());
        editMessageText.setParseMode("HTML");
        if (message.getKeyboard() != null) {
            editMessageText.setReplyMarkup(message.getKeyboard());
        }
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new FailedSendMessageException(String.format("Failed edit text message %s", message), e);
        }
    }
}

