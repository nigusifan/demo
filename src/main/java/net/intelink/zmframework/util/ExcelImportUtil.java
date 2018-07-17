package net.intelink.zmframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import net.intelink.zmframework.model.ExcelItemModel;
import net.intelink.zmframework.model.ExcelReadModel;

public class ExcelImportUtil {

	
	/**
	 * @param file excel文件
	 * @param titleArrs 表头字段
	 * @param columnlen 总列输
	 * @param leftindex 从第几列开始  0
	 * @param rowindex 从第几行开始 0
	 * @param classObj
	 * @return
	 */
	public static List getExcelImportList(MultipartFile file,String[] titleArrs,Integer columnlen,
			Integer leftindex, Integer rowindex,Object classObj){
		try {
			if(file == null || titleArrs == null || titleArrs.length == 0 || classObj == null){
				return null;
			}
			InputStream inp = file.getInputStream();
			Workbook wb = (Workbook) WorkbookFactory.create(inp);

			// 读取第一章表格内容
			Sheet sheet = wb.getSheetAt(0);
			List list = ExcelImportUtil.returnlist( titleArrs, sheet, 
					(columnlen != null ? 200 : null), (leftindex != null ? leftindex : 0), 
					(rowindex != null ? rowindex : 1) ,classObj);
			
			inp.close(); 
			
			return list;
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取excel表头
	 * @param file
	 * @param titleArrs
	 * @param columnlen
	 * @param leftindex
	 * @param rowindex
	 * @param classObj
	 * @return
	 */
	public static List<String> getExcelImportHeader(MultipartFile file){
		List<String> titlelist = new ArrayList<>();
		try {
			if(file == null ){
				return null;
			}
			InputStream inp = file.getInputStream();
			Workbook wb = (Workbook) WorkbookFactory.create(inp);
			// 读取第一章表格内容
			Sheet sheet = wb.getSheetAt(0);
			// 获取行数
			int totalRows = sheet.getPhysicalNumberOfRows();
			int totalCells = 0;
			if (totalRows >= 1 && sheet.getRow(0) != null) {
				totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
			}
			Row row = sheet.getRow(0);
	
			for (int c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;
					default:
						cellValue = "未知类型";
						break;
					}
				}
				titlelist.add(cellValue);
			}
			inp.close(); 
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return titlelist;
	}
	
	
	
	
	
	
	public static List returnlist(String[] titleArrs, Sheet sheet, int columnlen,
			int leftindex, int rowindex,Object classObj) {
		ExcelUitl util = new ExcelUitl();
		List invoicelist = new ArrayList();
		ExcelReadModel excelModel = new ExcelReadModel();
//		excelModel.setClasss(classs);
		excelModel.setColumnLen(columnlen);
		excelModel.setStartLeftIndex(leftindex);
		excelModel.setStartRowIndex(rowindex);
		getExcelModel(titleArrs, excelModel);
		invoicelist = getListModelValue(excelModel, sheet, classObj);
		return invoicelist;
	}
	
	private static void getExcelModel(String[] titleArr, ExcelReadModel excelModel) {
		for (int i = 0; i < titleArr.length; i++) {
			ExcelItemModel itemModel = new ExcelItemModel();
			itemModel.setColumsIndex(i);
			itemModel.setMethodName(titleArr[i]);
			excelModel.getItemModel().add(itemModel);
		}
	}
	
	public static List getListModelValue(ExcelReadModel excelModel, Sheet sheet,Object classObj) {
		if ((classObj == null) || ("".equals(classObj))) {
			return null;
		}
		List itemList = excelModel.getItemModel();
		if ((itemList == null) || (itemList.size() == 0))
			return null;
		int dataLen = sheet.getLastRowNum();
		if ((dataLen < 0) || (dataLen < excelModel.getStartRowIndex())) {
			return null;
		}
		dataLen++;
		List returnList = new ArrayList();

		for (int i = excelModel.getStartRowIndex(); i < dataLen; i++) {
			try {
				classObj = classObj.getClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			if (classObj == null) {
				continue;
			}
			Row row = sheet.getRow(i);

			for (int j = 0; j < excelModel.getItemModel().size(); j++) {
				String methodName = ((ExcelItemModel) excelModel.getItemModel().get(j)).getMethodName();
				int coloumIndex = ((ExcelItemModel) excelModel.getItemModel().get(j)).getColumsIndex();
				if ((methodName == null) || ("".equals(methodName)) || (excelModel.getColumnLen() <= coloumIndex))
					continue;
				coloumIndex += excelModel.getStartLeftIndex();
				Cell cell = row.getCell((short) coloumIndex);

				if (cell == null)
					continue;
				Object objValue = null;
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					String str = "";
                	cell.setCellType(Cell.CELL_TYPE_STRING);
                    String temp = cell.getStringCellValue();
                    if (temp.indexOf(".") > -1) {
                        str = String.valueOf(new Double(temp)).trim();
                    } else {
                        str = temp.trim();
                    }
                    objValue = str;
					break;
				case Cell.CELL_TYPE_STRING:
					objValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					objValue = cell.getBooleanCellValue();
					break;
				default:
					objValue = "";
				}

				if ((objValue != null) && (!"".equals(objValue))) {
					setMethodValue(classObj, methodName, objValue);
				}
			}
			returnList.add(classObj);
		}

		return returnList;
	}
	
	/**
	 * 当匹配类型为String 判断时候有多个栏位匹配同一个字段，如果多个栏位匹配同一个字段已“,”进行隔开
	 * 
	 * @param classs
	 * @param methodName
	 * @param value
	 */
	public static void setMethodValue(Object classs, String methodName, Object value) {
		String newMethodName = "";
		String getMethodName = "";
		if ((methodName != null) && (methodName.indexOf(".") > -1)) {
			String[] methodArr = methodName.split("\\.");
			if ((methodArr != null) && (methodArr.length > 0)) {
				for (int i = 0; i < methodArr.length; i++) {
					String methodName1 = methodArr[i];
					if ((methodName1 == null) || ("".equals(methodName1)))
						continue;
					if (i == methodArr.length - 1) {
						newMethodName = newMethodName + "set"+ methodName1.substring(0, 1).toUpperCase()+ methodName1.substring(1);
						getMethodName = newMethodName + "get"+ methodName1.substring(0, 1).toUpperCase()+ methodName1.substring(1);
					} else {
						newMethodName = newMethodName + "get"+ methodName1.substring(0, 1).toUpperCase()+ methodName1.substring(1) + "().";
					}
				}

			}

		} else {
			newMethodName = "set" + methodName.substring(0, 1).toUpperCase()+ methodName.substring(1);
			getMethodName = "get" + methodName.substring(0, 1).toUpperCase()+ methodName.substring(1);
		}

		if ((newMethodName != null) && (!"".equals(newMethodName))) {
			try {
				Method method = classs.getClass().getMethod(getMethodName,new Class[0]);
				Class returnValue = method.getReturnType();
				Object obj = method.invoke(classs);
				method = classs.getClass().getMethod(newMethodName,new Class[] { returnValue });

				if (returnValue.getName().equals(Date.class.getName())) {
					if ((value instanceof Double)) {
						Date date = HSSFDateUtil.getJavaDate(((Double) value).doubleValue());
						method.invoke(classs, new Object[] { date });
					}

				} else if ((value != null) && (!"".equals(value))) {
					if ((returnValue.getName().equals("int"))|| (returnValue.getName().equals(Integer.class.getName()))) {
						Integer intValue = Integer.valueOf(value.toString());
						if ((intValue instanceof Integer)) {
							method.invoke(classs, new Object[] { intValue });
						}
					} else if ((returnValue.getName().equals(Double.class.getName())) || (returnValue.getName().equals("double"))) {
						if ((value instanceof Double)) {
							method.invoke(classs,new Object[] { returnValue.cast(value) });
						} else {
							Double dbValue = Double.valueOf(value.toString());
							method.invoke(classs, new Object[] { dbValue });
						}
					} else if ((returnValue.getName().equals(BigDecimal.class.getName())) || (returnValue.getName().equals("bigDecimal"))) {
						BigDecimal bigValue = new BigDecimal(value.toString());
//						if ((value instanceof BigDecimal)) {
							method.invoke(classs,new Object[] { bigValue });
//						} else {
//							Double dbValue = Double.valueOf(value.toString());
//							method.invoke(classs, new Object[] { dbValue });
//						}
					} else if (returnValue.getName().equals(String.class.getName())) {
						if ((value instanceof Double)) {
							int value1 = ((Double) value).intValue();
							if (obj != null && !"".equals(obj.toString())) {
								method.invoke(classs,new Object[] { obj.toString() + ","+ String.valueOf(value1) });
							} else {
								method.invoke(classs,new Object[] { String.valueOf(value1) });
							}
						} else {
							if (obj != null && !"".equals(obj.toString())) {
								method.invoke(classs,new Object[] { obj.toString() + ","+ String.valueOf(value) });
							} else {
								method.invoke(classs,new Object[] { String.valueOf(value) });
							}
						}
					} else {
						method.invoke(classs,new Object[] { returnValue.cast(value) });
					}
				}

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
