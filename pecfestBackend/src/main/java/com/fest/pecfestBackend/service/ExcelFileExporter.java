package com.fest.pecfestBackend.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.repository.UserRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fest.pecfestBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelFileExporter {

    @Autowired
    private UserRepo userRepo;

    public  ByteArrayInputStream contactListToExcelFile(List<User> customers) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Participants");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("year of education");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Gender");
            cell.setCellStyle(headerCellStyle);


            for(int i = 0; i < customers.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(customers.get(i).getName());
                dataRow.createCell(1).setCellValue(customers.get(i).getEmail());
                dataRow.createCell(2).setCellValue(customers.get(i).getYearofeducation());
                dataRow.createCell(3).setCellValue(customers.get(i).getGender());
            }


            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public  ByteArrayInputStream contactTeamListToExcelFile(Map<Team,List<User>> teams) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Teams");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("Team Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Leader");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("year of education");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Gender");
            cell.setCellStyle(headerCellStyle);
            int i=1;

            for(Map.Entry<Team,List<User>> entry : teams.entrySet()){
                List<User> u = entry.getValue();
                String leaderName=null;
                if(userRepo.findById(entry.getKey().getLeaderId()).isPresent())
                 leaderName = userRepo.findById(entry.getKey().getLeaderId()).get().getName();
                for(int j = 0; j < u.size(); j++) {
                    Row innerDataRow = sheet.createRow(i);
                    if(j==0){
                        innerDataRow.createCell(0).setCellValue(entry.getKey().getTeamName());
                        innerDataRow.createCell(1).setCellValue(leaderName);
                    }
                    innerDataRow.createCell(2).setCellValue(entry.getValue().get(j).getName());
                    innerDataRow.createCell(3).setCellValue(entry.getValue().get(j).getEmail());
                    innerDataRow.createCell(4).setCellValue(entry.getValue().get(j).getYearofeducation());
                    innerDataRow.createCell(5).setCellValue(entry.getValue().get(j).getGender());
                    i++;
                 }
                i++;
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
