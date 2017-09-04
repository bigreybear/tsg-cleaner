package main;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    void anytimeTest() {
        String target = "origin-studata/2013.xls";
        ClassLoader classLoader = getClass().getClassLoader();
        String excel = classLoader.getResource(target).getFile();
        System.out.println(excel);
    }

    Object multiFormatHandler(HSSFCell cell){
        switch(cell.getCellType()){
            case HSSFCell.CELL_TYPE_BOOLEAN:
                //得到Boolean对象的方法
                return cell.getBooleanCellValue();
            case HSSFCell.CELL_TYPE_NUMERIC:
                //先看是否是日期格式
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    //读取日期格式
                    return cell.getDateCellValue();
                }else{
                    //读取数字
                    return cell.getNumericCellValue();
                }
            case HSSFCell.CELL_TYPE_FORMULA:
                //读取公式
                return cell.getCellFormula();
            case HSSFCell.CELL_TYPE_STRING:
                //读取String
                return cell.getRichStringCellValue();
            default:
                return null;
        }
    }

    void readTest() throws IOException{
        String target = "origin-studata/2013.xls";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream ips = new FileInputStream(classLoader.getResource(target).getFile());
        HSSFWorkbook wb = new HSSFWorkbook(ips);
        HSSFSheet sheet=wb.getSheetAt(0);
        for(Iterator ite=sheet.rowIterator();ite.hasNext();){
            HSSFRow row=(HSSFRow)ite.next();
            System.out.println();
            for(Iterator itet = row.cellIterator(); itet.hasNext();){
                HSSFCell cell=(HSSFCell)itet.next();
                System.out.println(multiFormatHandler(cell));
                /*
                switch(cell.getCellType()){
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        //得到Boolean对象的方法
                        System.out.print(cell.getBooleanCellValue()+" ");
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        //先看是否是日期格式
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                            //读取日期格式
                            System.out.print(cell.getDateCellValue()+" ");
                        }else{
                            //读取数字
                            System.out.print(cell.getNumericCellValue()+" ");
                        }
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        //读取公式
                        System.out.print(cell.getCellFormula()+" ");
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        //读取String
                        System.out.print(cell.getRichStringCellValue().toString()+" ");
                        break;

                }*/
            }
        }
    }

    public static void main(String[] args) throws IOException{
        ExcelUtil eu = new ExcelUtil();
//        eu.anytimeTest();
        eu.readTest();


    }

}
