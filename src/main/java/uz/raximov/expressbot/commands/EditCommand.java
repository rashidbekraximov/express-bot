package uz.raximov.expressbot.commands;

import uz.raximov.expressbot.dto.MessageEdit;

import java.util.Locale;

public interface EditCommand<T> {

    void execute(T response, Locale locale, MessageEdit edit, String data);

}
