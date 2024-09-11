package uz.raximov.expressbot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import uz.raximov.expressbot.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Commands {

    public static final String START_COMMAND = "/start";
    public static final String MENU_COMMAND = "/menu";

    public static final String LANGUAGE_UZ = "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha";
    public static final String LANGUAGE_KR = "\uD83C\uDDFA\uD83C\uDDFF Ўзбекча (Крилл)";
    public static final String LANGUAGE_RU = "\uD83C\uDDF7\uD83C\uDDFA Русский";

    private Commands() {
    }

    public static ReplyKeyboardMarkup backOnly() {
        return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{
                add("⬅ Orqaga");
            }});
        }});
    }

    public static InlineKeyboardMarkup createCatalogInlineKeyboard(String action) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setCallbackData(action + "_ha");
        inlineKeyboardButton1.setText("✅ To'langan");
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setCallbackData(action + "_yo'q");
        inlineKeyboardButton2.setText("❌ To'lanmagan");
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(inlineKeyboardButton1,inlineKeyboardButton2)));
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createCatalogInlineKeyboardConfirm(String action) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setCallbackData(action + "_ha");
        inlineKeyboardButton1.setText("✅ Ha");
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setCallbackData(action + "_yo'q");
        inlineKeyboardButton2.setText("❌ Yo'q");
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(inlineKeyboardButton1,inlineKeyboardButton2)));
        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup createLanguageKeyboard() {
        return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{
                add(LANGUAGE_UZ);
                add(LANGUAGE_RU);
            }});
            add(new KeyboardRow() {{
                add(LANGUAGE_KR);
            }});
        }});
    }

    public static String getLanguage(String command) {
        if (command.equals(LANGUAGE_UZ)) return "uz";
        if (command.equals(LANGUAGE_RU)) return "ru";
        if (command.equals(LANGUAGE_KR)) return "kr";
        return null;
    }
}

