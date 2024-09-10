package uz.raximov.expressbot.excel;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.entity.Client;
import uz.raximov.expressbot.excel.style.CustomXSSFRow;
import uz.raximov.expressbot.excel.style.ExcelStyle;
import uz.raximov.expressbot.service.ClientService;

@Service
@RequiredArgsConstructor
public class ExcelUsers {

    CustomXSSFRow rowInTable;

    XSSFCellStyle cellStyleCenterBackgroundGreenWithBorder;
    XSSFCellStyle cellStyleCenterOnlyFontWithBorderWithBold;
    XSSFCellStyle cellStyleCenterBackgroundOrangeWithBorder;

    XSSFCell cell1;
    XSSFCell cell2;
    XSSFCell cell3;
    XSSFCell cell4;
    XSSFCell cell5;
    XSSFCell cell6;
    XSSFCell cell7;
    XSSFCell cell8;
    XSSFCell cell9;
    XSSFCell cell10;
    XSSFCell cell11;

    int counter = 0;

    private final ClientService clientService;

    public void getUsersInExcel(XSSFWorkbook workbook) {
        counter = 0;
        try {

            XSSFSheet sheet = workbook.createSheet("Foydalanuvchilar");

            sheet.setAutoFilter(CellRangeAddress.valueOf("C2:J2"));
            sheet.setZoom(75);
            sheet.addIgnoredErrors(new CellRangeAddress(0, 30000, 0, 25), IgnoredErrorType.NUMBER_STORED_AS_TEXT);
            cellStyleCenterBackgroundGreenWithBorder = ExcelStyle.cellStyleCenterBackgroundGreenWithBorder(workbook);
            cellStyleCenterOnlyFontWithBorderWithBold = ExcelStyle.cellStyleCenterOnlyFontWithBorderWithBold(workbook);
            cellStyleCenterBackgroundOrangeWithBorder = ExcelStyle.cellStyleCenterBackgroundOrangeWithBorder(workbook);

            int currColumnIndex = 1;
            for (int i = 1; i <= 11; i++) {
                if (i == 1) {
                    sheet.setColumnWidth(currColumnIndex++, 256 * 13);
                } else if (i == 9 || i == 2 || i == 10) {
                    sheet.setColumnWidth(currColumnIndex++, 256 * 50);
                } else {
                    sheet.setColumnWidth(currColumnIndex++, 256 * 24);
                }
            }
            counter++;

            rowInTable = new CustomXSSFRow(sheet.createRow(counter), cellStyleCenterBackgroundGreenWithBorder);
            rowInTable.setHeight((short) 650);
            cell1 = rowInTable.createCell(CellType.STRING);
            cell1.setCellValue("T/R");
            cell2 = rowInTable.createCell(CellType.STRING);
            cell2.setCellValue("Ism va Familya");
            cell10 = rowInTable.createCell(CellType.STRING);
            cell10.setCellValue("Username");
            cell8 = rowInTable.createCell(CellType.STRING);
            cell8.setCellValue("Tel.raqam");
            cell3 = rowInTable.createCell(CellType.STRING);
            cell3.setCellValue("Jinsi");
            cell4 = rowInTable.createCell(CellType.STRING);
            cell4.setCellValue("Tug'ulgan kuni");
            cell5 = rowInTable.createCell(CellType.STRING);
            cell5.setCellValue("Hudud");
            cell6 = rowInTable.createCell(CellType.STRING);
            cell6.setCellValue("Kasbi");
            cell7 = rowInTable.createCell(CellType.STRING);
            cell7.setCellValue("Nechi yildan beri kuzatish");
            cell11 = rowInTable.createCell(CellType.STRING);
            cell11.setCellValue("Qo'shilgan odamlar soni");
            counter++;

            int count = 0;
            for (Client client : clientService.getOnlyUserList()) {
                count++;
                rowInTable = new CustomXSSFRow(sheet.createRow(counter), cellStyleCenterOnlyFontWithBorderWithBold);
                cell1 = rowInTable.createCell(CellType.STRING);
                cell1.setCellValue(count + "");
                cell2 = rowInTable.createCell(CellType.STRING);
                cell2.setCellValue(client.getUserInfo());
                cell10 = rowInTable.createCell(CellType.STRING);
                cell10.setCellValue(client.getUsername());
                cell8 = rowInTable.createCell(CellType.STRING);
                cell8.setCellValue(client.getPhone());
//                cell3 = rowInTable.createCell(CellType.STRING);
//                cell3.setCellValue(doc.getGender());
//                cell4 = rowInTable.createCell(CellType.STRING);
//                cell4.setCellValue(doc.getDate());
//                cell5 = rowInTable.createCell(CellType.STRING);
//                cell5.setCellValue(doc.getRegion());
//                cell6 = rowInTable.createCell(CellType.STRING);
//                cell6.setCellValue(doc.getJob());
//                cell7 = rowInTable.createCell(CellType.STRING);
//                cell7.setCellValue(doc.getSeenYear());
//                cell11 = rowInTable.createCell(CellType.STRING);
//                cell11.setCellValue(String.valueOf(doc.getUserAdded()));
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
