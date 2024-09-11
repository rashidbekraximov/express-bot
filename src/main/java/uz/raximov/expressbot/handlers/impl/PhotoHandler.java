package uz.raximov.expressbot.handlers.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import uz.raximov.expressbot.bot.Data;
import uz.raximov.expressbot.commands.Actions;
import uz.raximov.expressbot.commands.impl.GeneralCommand;
import uz.raximov.expressbot.commands.impl.GlobalRequestCommand;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.entity.Client;
import uz.raximov.expressbot.entity.FileEntity;
import uz.raximov.expressbot.entity.Order;
import uz.raximov.expressbot.handlers.Handler;
import uz.raximov.expressbot.service.*;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoHandler implements Handler<Message> {

    private final GeneralCommand generalCommand;

    private final GlobalRequestCommand globalRequestCommand;

    private final ClientActionService clientActionService;

    private final ClientService clientService;

    private final TelegramService telegramService;

    private final FileService fileService;

    private final OrderService orderService;

    @Override
    public void handle(Message message) {
        boolean isAdmin = true;
        if (message.getFrom() != null) {
            Client client = clientService.findClientByTelegramId(message.getFrom().getId());
            ClientActionDto action = clientActionService.findLastActionByChatId(message.getChatId());
            if (action.getAction().equals(Actions.REQUEST_ORDER_PHOTO_ACTION)) {
                if (client != null) {
                    try {
                        orderService.saveOrder(new Order(message.getChatId(),client,client.getId(),savePhotoMessageToFile(message)));
                        clientActionService.saveAction(Actions.REQUEST_ORDER_COUNT_ACTION, message.getChatId(), client.getId());
                        globalRequestCommand.execute(message.getChatId(),"\uD83D\uDED2 Buyurtma soni:",null,isAdmin);
                        log.info("Buyurtmada muvaffaqiyatli yaratildi !");
                    }catch (Exception e){
                        log.error("Buyurtmada xatolik yuzaga keldi !");
                    }
                    return;
                }
            }
        }else{
            generalCommand.execute(message.getChatId(),true);
        }
    }


    private FileEntity savePhotoMessageToFile(Message message) {
        FileEntity fileEntity = new FileEntity();
        java.io.File outputFile = null;
        List<PhotoSize> photos = message.getPhoto();

        // Assuming you want the largest photo size
        if (photos == null || photos.isEmpty()) {
            System.err.println("No photos found in the message.");
            return fileEntity;
        }

        PhotoSize photo = photos.get(photos.size() - 1); // Get the largest photo

        try {
            // Get the file path
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            File file = telegramService.execute(getFileMethod);
            String filePath = file.getFilePath();

            // Download the file
            String fileUrl = "https://api.telegram.org/file/bot" + Data.token + "/" + filePath;
            try (InputStream is = new URL(fileUrl).openStream();
                 BufferedInputStream bis = new BufferedInputStream(is)) {

                // Save the file to the specified directory
                outputFile = Paths.get("/opt", "/order_photo_" + message.getFrom().getId()  + "_" + message.getMessageId() + ".jpg").toFile();
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = bis.read(buffer, 0, 1024)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }

            // Set attributes for the VoiceMessageEntity
            fileEntity.setFilePath(outputFile.getAbsolutePath());
            fileEntity.setFileName(outputFile.getName());
            fileEntity.setFileSize((int) outputFile.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileService.saveFile(fileEntity);
    }
}

