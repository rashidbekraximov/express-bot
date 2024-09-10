package uz.raximov.expressbot.commands;

import java.util.Locale;

public interface CollectorCommand<T> {

    void execute(T response, String variable1,String variable2,Boolean isAdmin);

}