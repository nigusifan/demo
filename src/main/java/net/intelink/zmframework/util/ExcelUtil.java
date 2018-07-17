package net.intelink.zmframework.util;


import net.intelink.zmframework.exception.BaseException;
import net.intelink.zmframework.model.User;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.event.ListDataEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 处理excel读入的工具类
 * Created by Liujishuai on 2015/8/5.
 */
public class ExcelUtil {

    public static <T> List<T> read2List(File file, Map<String,String> fields,Class<T> clz) throws Exception {
        if (!file.exists()) {
            throw new BaseException("找不到文件");
        }

        return read2List(new FileInputStream(file), fields, clz);
    }

    public static <T> List<T> read2List(InputStream is,Map<String,String> fields,Class<T> clz) throws Exception{
        List<T> list = new LinkedList<>();
        XSSFWorkbook xwb = new XSSFWorkbook(is);
        // 读取第一张表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);

        Map<Integer, String> fieldsNameIndex = getFieldsNameIndex(sheet, fields);

        XSSFRow row = null;
        XSSFCell cell = null;
        for (int i = (sheet.getFirstRowNum() + 1); i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            T t = clz.newInstance();
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }

                Object value = cell.toString();

                if (value != null && !value.equals("")) {
                    if(fieldsNameIndex.containsKey(j))
                        setObjectVal(t,fieldsNameIndex.get(j),value);
                }
            }
            list.add(t);
        }
        return list;
    }

    private static void setObjectVal(Object obj,String field,Object value){
        try {
            Class<?> clz = obj.getClass();
            Field fieldObj = getField(clz,field);
            Class<?> type = fieldObj.getType();

            switch (type.getSimpleName()) {
                case "String":
                    value = String.valueOf(value);
                    break;
                case "Integer":
                    value = Double.valueOf(value.toString()).intValue();
                    break;
                case "Boolean":
                    value = Boolean.valueOf(value.toString());
                    break;
                case "Short":
                    value = Double.valueOf(value.toString()).shortValue();
                    break;
                case "Long":
                    value = Double.valueOf(value.toString()).longValue();
                    break;
                case "Float":
                    value = Double.valueOf(value.toString()).floatValue();
                    break;
                case "Double":
                    value = Double.valueOf(value.toString());
                    break;
                case "Date":

                    break;
                default:

            }

            Method method = getMethod(1, clz, fieldObj);

            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取getter／setter方法
     * @param sign 1:set方法  2:get方法
     * @param clz
     * @param field
     * @return
     * @throws NoSuchMethodException
     */
    private static Method getMethod(int sign, Class<?> clz, Field field) throws NoSuchMethodException {
        String prefix = "";
        Class<?> fieldType = null;
        String fieldName = field.getName();
        if(sign == 1) {//set方法
            prefix = "set";
            fieldType = field.getType();
            return clz.getMethod(prefix+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1),fieldType);
        }else { //get方法
            prefix = "get";
            return clz.getMethod(prefix+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1));
        }

    }


    private static Field getField(Class clz, String field){
        Field[] fields = clz.getDeclaredFields();
        for(Field f : fields){
            if (f.getName().equals(field)) {
                return f;
            }
        }

        throw new BaseException("没有找到此字段["+field+"]");

    }

    private static Map<Integer,String> getFieldsNameIndex(XSSFSheet sheet,Map<String,String> fields){
        if (fields == null || fields.size() == 0) {
            throw new BaseException("Excel header field is null.");
        }

        XSSFRow row = sheet.getRow(0);

        Map<Integer, String> fieldIndexMap = new HashMap<>();
        for(int i=row.getFirstCellNum();i<row.getPhysicalNumberOfCells();i++){
            XSSFCell cell = row.getCell(i);
            String fieldName = cell.getStringCellValue();
            if (fields.containsKey(fieldName)) {
                fieldIndexMap.put(i, fields.get(fieldName));
            }
        }
        return fieldIndexMap;
    }

    /**
     * 导出excel
     *
     * @param fileName 导出的excel路径（需要带.xlsx)
     * @param headList   excel的标题备注名称
     * @param fieldList  excel的标题字段（与数据中map中键值对应）
     * @param dataList   excel数据
     * @throws Exception
     */
    public static void createExcel(String fileName, String[] headList,
                                   String[] fieldList, List<Map<String, Object>> dataList)
            throws Exception {
        // 创建新的Excel 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值
        XSSFSheet sheet = workbook.createSheet(fileName);

        // 在索引0的位置创建行（最顶端的行）
        XSSFRow row = sheet.createRow(0);
        // 设置excel头（第一行）的头名称
        for (int i = 0; i < headList.length; i++) {

            // 在索引0的位置创建单元格（左上端）
            XSSFCell cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            // 在单元格中输入一些内容
            cell.setCellValue(headList[i]);
        }
        // ===============================================================
        //添加数据
        for (int n = 0; n < dataList.size(); n++) {
            // 在索引1的位置创建行（最顶端的行）
            XSSFRow row_value = sheet.createRow(n + 1);
            Map<String, Object> dataMap = dataList.get(n);
            // ===============================================================
            for (int i = 0; i < fieldList.length; i++) {

                // 在索引0的位置创建单元格（左上端）
                XSSFCell cell = row_value.createCell(i);
                // 定义单元格为字符串类型
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                // 在单元格中输入一些内容
                cell.setCellValue((dataMap.get(fieldList[i])).toString());
            }
            // ===============================================================
        }
        // 新建一输出文件流
        FileOutputStream fos = new FileOutputStream(fileName);
        // 把相应的Excel 工作簿存盘
        workbook.write(fos);
        fos.flush();
        // 操作结束，关闭文件
        fos.close();
    }


    public static <T> void createExcel1(String fileName, LinkedHashMap<String,String> fieldMapping, List<T> dataList)
            throws Exception {

        if(fieldMapping == null || fieldMapping.size() ==0)
            throw new BaseException("Field mapping is null.");

        // 创建新的Excel 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值
        XSSFSheet sheet = workbook.createSheet(fileName);

        // 在索引0的位置创建行（最顶端的行）
        XSSFRow row = sheet.createRow(0);

        // 设置excel头（第一行）的头名称
        Set<String> headers = fieldMapping.keySet();
        int i = 0;
        for (String header : headers) {
            XSSFCell cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            // 在单元格中输入一些内容
            cell.setCellValue(header);
            i++;
        }

        //添加数据
        for (int n = 0; n < dataList.size(); n++) {
            // 在索引1的位置创建行（最顶端的行）
            XSSFRow row_value = sheet.createRow(n + 1);
            T t = dataList.get(n);

            Collection<String> values = fieldMapping.values();

            int j = 0;
            for (String field : values) {
                // 在索引0的位置创建单元格（左上端）
                XSSFCell cell = row_value.createCell(j);
                // 定义单元格为字符串类型
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);

                Object val = getObjectVal(t,field);

                // 在单元格中输入一些内容
                cell.setCellValue(val.toString());
                j++;
            }

        }
        // 新建一输出文件流
        FileOutputStream fos = new FileOutputStream(fileName);
        // 把相应的Excel 工作簿存盘
        workbook.write(fos);
        fos.flush();
        // 操作结束，关闭文件
        fos.close();
    }

    private static <T> Object getObjectVal(T t, String field) {
        Class<?> clz = t.getClass();

        Field fieldOjb = getField(clz, field);
        try {
            Method method = getMethod(2, clz, fieldOjb);
            return method.invoke(t, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/suzhongqiang/Desktop/suzhongqiang/1.xlsx");
        Map<String, String> map = new HashMap<>();
        map.put("id","id");
        map.put("姓名", "name");
        map.put("年龄", "age");
        map.put("地址", "address");
        List<User> lists = ExcelUtil.read2List(file,map, User.class);

        System.out.println(JsonUtil.toJson(lists));

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> data1 = new HashMap<>();
        data1.put("name", "李依");
        data1.put("age" ,40);
        data1.put("address","南山区");
        list.add(data1);

        Map<String,Object> data2 = new HashMap<>();
        data2.put("name", "李依1");
        data2.put("age" ,42);
        data2.put("address","南山区11111");
        list.add(data2);
        ExcelUtil.createExcel("2.xlsx",new String[]{"姓名","年龄","地址"},new String[]{"name","age","address"},list);


        LinkedHashMap<String, String> fieldmapping = new LinkedHashMap<>();
        fieldmapping.put("姓名", "name");
        fieldmapping.put("年龄", "age");
        fieldmapping.put("地址", "address");


        User user1 = new User();
        user1.setAge(30);
        user1.setName("秦五");
        user1.setAddress("深圳市南山区");

        User user2 = new User();
        user2.setAge(30);
        user2.setName("秦五");
        user2.setAddress("深圳市南山区");

        User user3 = new User();
        user3.setAge(30);
        user3.setName("秦五");
        user3.setAddress("深圳市南山区");

        User user4 = new User();
        user4.setAge(30);
        user4.setName("秦五");
        user4.setAddress("深圳市南山区");

        List<User> listdata = new ArrayList<>();
        listdata.add(user1);
        listdata.add(user2);
        listdata.add(user3);
        listdata.add(user4);
        ExcelUtil.createExcel1("3.xlsx", fieldmapping, listdata);
    }
}