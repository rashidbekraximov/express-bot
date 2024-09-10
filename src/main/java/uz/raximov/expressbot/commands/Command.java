package uz.raximov.expressbot.commands;

import java.util.Locale;

public interface Command<T> {

    void execute(T response,Boolean isAdmin);

}
