package uz.raximov.expressbot.commands.impl.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import uz.raximov.expressbot.commands.Command;
import uz.raximov.expressbot.commands.impl.Commands;
import uz.raximov.expressbot.dto.DocumentSend;
import uz.raximov.expressbot.dto.MessageSend;
import uz.raximov.expressbot.excel.ExcelUsers;
import uz.raximov.expressbot.service.TelegramService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class LoadUserListToExcelCommand implements Command<Long> {

    @Resource
    private TelegramService telegramService;

    @Resource
    private ExcelUsers excelUsersReportSheet;

    @Resource
    private AdminCommand adminCommand;

    @Override
    public void execute(Long chatId, Boolean isAdmin) {
        telegramService.sendDocument(new DocumentSend(chatId, "\uD83D\uDDC2 Foydalanuvchilar ro'yxati", getFile(),adminCommand.createGeneralMenuKeyboard(isAdmin)));
    }

    private File getFile() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            excelUsersReportSheet.getUsersInExcel(workbook);
            OutputStream fileOut = new FileOutputStream("/opt/report.xlsx");
            System.out.println("Excel File has been created successfully.");
            workbook.write(fileOut);
            workbook.close();
            return new File("/opt/report.xlsx");
        }catch (IOException io){
            log.error("User list excelni yuklashda xatolik !");
        }
        return null;
    }
}
