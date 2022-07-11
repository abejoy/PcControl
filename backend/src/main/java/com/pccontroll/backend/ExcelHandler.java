package com.pccontroll.backend;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.*;

//import statements
public class ExcelHandler
{

    String FILEPATH = "/home/docker/data/";

    private boolean doesEmailExist (XSSFSheet mySheet, String email) {
        Iterator<Row> rowIterator = mySheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (email.equals(cell.getStringCellValue())) {
                    return true;
                };

            }
        }

        return false;

    }

    public String addUser(User user){
        try
        {
            FileInputStream myxls = new FileInputStream(FILEPATH + "registration.xls");


            XSSFWorkbook sheet = new XSSFWorkbook(myxls);
            XSSFSheet worksheet = sheet.getSheetAt(0);

            boolean emailExists = doesEmailExist(worksheet, user.getContactEmail());
            if (emailExists) {
                return "email already exists";
            }
            int lastRow=worksheet.getLastRowNum();
            Row row = worksheet.createRow(++lastRow);

            List<String> stringUser = user.getArrayUser();
            for(int cellNum = 0; cellNum < stringUser.size(); cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(stringUser.get(cellNum));
            }
            myxls.close();
            FileOutputStream output_file =new FileOutputStream(new File(FILEPATH + "/home/docker/data/registration.xls"));
            //write changes
            sheet.write(output_file);
            output_file.close();
            System.out.println("is successfully written!");
        }
        catch (FileNotFoundException e) {
            createUserWithHeader(user);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return "Your message was sent, thank you!";
    }


    private void createUserWithHeader(User myUser) {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Camp Registered Data");


        List<User> users = new ArrayList<>();
        users.add(new User("header"));
        users.add(myUser);

        for(int rowNum = 0; rowNum < users.size(); rowNum++) {
            User currentUser = users.get(rowNum);
            List<String> currentStringUser = currentUser.getArrayUser();
            Row row = sheet.createRow(rowNum);
            for(int cellNum = 0; cellNum < currentStringUser.size(); cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(currentStringUser.get(cellNum));
            }

        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("registration.xls"));
            workbook.write(out);
            out.close();
            System.out.println("registration.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public static void main(String[] args)
    {
        User myUser1 = new User("Mat", "Mddat@gmail.com", "07555374636", "384367467583", "1998-02-24", "nwl");
        new ExcelHandler().addUser(myUser1);
    }
}