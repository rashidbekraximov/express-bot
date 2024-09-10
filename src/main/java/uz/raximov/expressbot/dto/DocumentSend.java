package uz.raximov.expressbot.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;
import java.util.Objects;

public class DocumentSend {
    private final Long chatId;
    private final String caption;
    private final File document;
    private final ReplyKeyboard keyboard;

    public DocumentSend(Long chatId, String caption, File document, ReplyKeyboard keyboard) {
        this.chatId = chatId;
        this.caption = caption;
        this.document = document;
        this.keyboard = keyboard;
    }

    public DocumentSend(Long chatId, File document) {
        this.chatId = chatId;
        this.caption = null;
        this.document = document;
        this.keyboard = null;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getCaption() {
        return caption;
    }

    public File getDocument() {
        return document;
    }

    public ReplyKeyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, caption, document, keyboard);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentSend that = (DocumentSend) o;
        return Objects.equals(chatId, that.chatId) &&
                Objects.equals(caption, that.caption) &&
                Objects.equals(document, that.document) &&
                Objects.equals(keyboard, that.keyboard);
    }

    @Override
    public String toString() {
        return "PhotoSend{" +
                "chatId=" + chatId +
                ", caption='" + caption + '\'' +
                ", photo='" + document + '\'' +
                ", keyboard=" + keyboard +
                '}';
    }

}

