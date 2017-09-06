package main;

import java.io.*;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.StuDataStruct;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
    StuDataStruct sds;

    public static ArrayList<Integer> transformStringToInteger(ArrayList<String> strList){
        ArrayList<Integer> intList = new ArrayList<Integer>();
        for (String str: strList) {
            try {
                intList.add(Integer.parseInt(str));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

        }
        return intList;
    }

    public ExcelUtil() throws IOException{
        sds = new StuDataStruct(1911);
        this.getXLSLoaded("2013.xls");
        this.getXLSLoaded("2014.xls");
        this.getXLSLoaded("2015.xls");
        this.getXLSLoaded("2016.xls");
        this.getXLSLoaded("2017.xls");
    }

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

    public ArrayList<String> getCertainCol(String fileName, int _colNo) throws InvalidFormatException, IOException{
        String target = "questionFile/" + fileName;
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println(classLoader.getResource(target).getFile());
        FileInputStream ips = new FileInputStream(classLoader.getResource(target).getFile());
        XSSFWorkbook xwb = new XSSFWorkbook(ips);
        XSSFSheet xsheet = xwb.getSheetAt(0);
        int totalRows = xsheet.getPhysicalNumberOfRows();

        ArrayList<String> ret = new ArrayList<String>();
        String res;
        XSSFRow row;
        for(int rowNum = 1; rowNum < totalRows; rowNum++){

            row = xsheet.getRow(rowNum);
//            try {
//                res = Double.toString( row.getCell(_colNo).getNumericCellValue());
//            }catch (IllegalStateException e){
//                res = row.getCell(_colNo).getRawValue();
//            }
            res = row.getCell(_colNo).getRawValue();
            res.replace(" ", "");
            ret.add(res);

        }
        ips.close();
        return ret;
    }

    public ArrayList<String> markCertainCol(ArrayList<String> anoList, int compareCol, int markCol, String fileName)
            throws IOException{
        String target = "questionFile/" + fileName;
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println(classLoader.getResource(target).getFile());
        FileInputStream ips = new FileInputStream(classLoader.getResource(target).getFile());
        XSSFWorkbook xwb = new XSSFWorkbook(ips);
        XSSFSheet xsheet = xwb.getSheetAt(0);

        XSSFRow row = xsheet.getRow(0);
        row.getCell(0).setCellValue("change");
        ips.close();
        return null;
    }


    void getXLSLoaded(String fileName) throws IOException{
        String target = "origin-studata/" + fileName;
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream ips = new FileInputStream(classLoader.getResource(target).getFile());
        HSSFWorkbook wb = new HSSFWorkbook(ips);
        HSSFSheet sheet=wb.getSheetAt(0);
        int stuId;
        String stuName;
        String depName;
        String majName;
        String claName;

        for(Iterator ite=sheet.rowIterator();ite.hasNext();){
            HSSFRow row=(HSSFRow)ite.next();
//            System.out.println();
//            System.out.println(multiFormatHandler(row.getCell(0)));
            stuId = Integer.parseInt(multiFormatHandler(row.getCell(0)).toString());

//            System.out.println(multiFormatHandler(row.getCell(1)));
            stuName = multiFormatHandler(row.getCell(1)).toString();

//            System.out.println(multiFormatHandler(row.getCell(7)));
            depName = multiFormatHandler(row.getCell(7)).toString();

//            System.out.println(multiFormatHandler(row.getCell(8)));
            majName = multiFormatHandler(row.getCell(8)).toString();

//            System.out.println(multiFormatHandler(row.getCell(10)));
            claName = multiFormatHandler(row.getCell(10)).toString();
            sds.addStu(depName, claName, majName, stuName, stuId);
        }
    }

    void saveTest() throws IOException{
        String fileName = "mid-store/SDS.bin";
        ClassLoader classLoader = getClass().getClassLoader();
//        String url = classLoader.getResource(fileName).getFile();
//        System.out.println(url);
    }

    public static void main(String[] args) throws IOException, InvalidFormatException{
        ExcelUtil eu = new ExcelUtil();
//        eu.anytimeTest();
//        eu.saveTest();
        System.out.println("hello");

        ArrayList<String> tempRes;
        tempRes = eu.getCertainCol("0904zyzxYCSJ.xlsx",0);
        System.out.println(tempRes);
        ArrayList<Integer> intList = ExcelUtil.transformStringToInteger(tempRes);
        eu.sds.searchByIds(intList);
//        eu.markCertainCol(null, 0, 0,"0904ZZB02.xlsx");
    }

}
