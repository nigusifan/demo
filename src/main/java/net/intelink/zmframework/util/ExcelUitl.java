package net.intelink.zmframework.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.synth.Region;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.intelink.zmframework.model.ExcelItemModel;
import net.intelink.zmframework.model.ExcelReadModel;
import net.intelink.zmframework.model.ExcelWriteModel;

public class ExcelUitl {
	private static Logger logger = LoggerFactory.getLogger(ExcelUitl.class);
	
	public void setMethodValue(Object classs, String methodName, Object value) {
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
						newMethodName = newMethodName + "set"
								+ methodName1.substring(0, 1).toUpperCase()
								+ methodName1.substring(1);
						getMethodName = newMethodName + "get"
								+ methodName1.substring(0, 1).toUpperCase()
								+ methodName1.substring(1);
					} else {
						newMethodName = newMethodName + "get"
								+ methodName1.substring(0, 1).toUpperCase()
								+ methodName1.substring(1) + "().";
					}
				}

			}

		} else {
			newMethodName = "set" + methodName.substring(0, 1).toUpperCase()
					+ methodName.substring(1);
			getMethodName = "get" + methodName.substring(0, 1).toUpperCase()
					+ methodName.substring(1);
		}

		if ((newMethodName != null) && (!"".equals(newMethodName))) {
			try {
				Method method = classs.getClass().getMethod(getMethodName,new Class[0]);
				Class returnValue = method.getReturnType();
				method = classs.getClass().getMethod(newMethodName,	new Class[] { returnValue });

				if (returnValue.getName().equals(Date.class.getName())) {
					if ((value instanceof Double)) {
						Date date = HSSFDateUtil.getJavaDate(((Double) value).doubleValue());
						method.invoke(classs, new Object[] { date });
					}
				} else if ((value != null) && (!"".equals(value))) {
					if ((returnValue.getName().equals("int")) || (returnValue.getName().equals(Integer.class.getName()))) {
						if ((value instanceof Double)) {
							method.invoke(classs, new Object[] { Integer.valueOf(((Double) value).intValue()) });
						}
					} else if ((returnValue.getName().equals(Double.class.getName())) || (returnValue.getName().equals("double"))) {
						if ((value instanceof Double)) {
							method.invoke(classs,new Object[] { returnValue.cast(value) });
						} else {
							Double dbValue = Double.valueOf(value.toString());
							method.invoke(classs, new Object[] { dbValue });
						}
					} else if ((returnValue.getName().equals(Long.class.getName()))	|| (returnValue.getName().equals("long"))) {
						if ((value instanceof Double)) {
							method.invoke(classs,new Object[] {  ((Double) value).longValue() });
						} else {
							Long longValue = Long.valueOf(value.toString());
							method.invoke(classs, new Object[] { longValue });
						}
					}else if (returnValue.getName().equals(String.class.getName())) {
						/*if ((value instanceof Double)) {
							int value1 = ((Double) value).intValue();
							method.invoke(classs,new Object[] { String.valueOf(value1) });
						} else {
							method.invoke(classs,new Object[] { String.valueOf(value) });
						}*/
						method.invoke(classs,new Object[] { value.toString() });
					} else {
						method.invoke(classs,new Object[] { returnValue.cast(value) });
					}
				}

			} catch (SecurityException e) {
				logger.error("ExcelUitl in setMethodValue", e);
			} catch (NoSuchMethodException e) {
				logger.error("ExcelUitl in setMethodValue", e);
			} catch (IllegalArgumentException e) {
				logger.error("ExcelUitl in setMethodValue", e);
			} catch (IllegalAccessException e) {
				logger.error("ExcelUitl in setMethodValue", e);
			} catch (InvocationTargetException e) {
				logger.error("ExcelUitl in setMethodValue", e);
			} catch (Exception ex) {
				logger.error("ExcelUitl in setMethodValue", ex);
			}
		}
	}

	public Object changePropertyToGetProperty(String propertyName,
			Object mainModel, Class returnClass) {
		String propertyName1 = "";
		if (StringUtil.isNotEmpty(propertyName)) {
			Object obj = null;
			String[] methodArr = propertyName.split("\\.");
			if ((methodArr != null) && (methodArr.length > 0)) {
				for (int i = 0; i < methodArr.length; i++) {
					String methodName1 = methodArr[i];
					if ((methodName1 == null) || ("".equals(methodName1))) {
						continue;
					}
					propertyName1 = "get"
							+ methodName1.substring(0, 1).toUpperCase()
							+ methodName1.substring(1);
					try {
						Method method = mainModel.getClass().getMethod(
								propertyName1, new Class[0]);
						returnClass = method.getReturnType();
						obj = method.invoke(mainModel, null);
						mainModel = obj;
					} catch (SecurityException e) {
						logger.error("ExcelUitl in changePropertyToGetProperty", e);
					} catch (NoSuchMethodException e) {
						logger.error("ExcelUitl in changePropertyToGetProperty", e);
					} catch (IllegalArgumentException e) {
						logger.error("ExcelUitl in changePropertyToGetProperty", e);
					} catch (IllegalAccessException e) {
						logger.error("ExcelUitl in changePropertyToGetProperty", e);
					} catch (InvocationTargetException e) {
						logger.error("ExcelUitl in changePropertyToGetProperty", e);
					}

				}

			}

			return obj;
		}

		return null;
	}

	private String changePropertyToSetProperty(String propertyName) {
		if ((propertyName != null) && (propertyName.indexOf(".") > -1)) {
			String[] methodArr = propertyName.split("\\.");
			if ((methodArr != null) && (methodArr.length > 0)) {
				for (int i = 0; i < methodArr.length; i++) {
					String methodName1 = methodArr[i];
					if ((methodName1 == null) || ("".equals(methodName1)))
						continue;
					if (i == methodArr.length - 1) {
						propertyName = propertyName + "set"
								+ methodName1.substring(0, 1).toUpperCase()
								+ methodName1.substring(1);
					} else {
						propertyName = propertyName + "get"
								+ methodName1.substring(0, 1).toUpperCase()
								+ methodName1.substring(1) + "().";
					}
				}

			}

		} else {
			propertyName = "set" + propertyName.substring(0, 1).toUpperCase()
					+ propertyName.substring(1);
		}

		return propertyName;
	}

	private Object getFieldValue(String methodName, Object o) {
		Object object = null;
		try {
			Method method = o.getClass().getMethod(methodName, null);
			object = method.invoke(o, null);
		} catch (Exception e) {
			logger.error("ExcelUitl in getFieldValue", e);
		}
		return object;
	}

	public List getListModelValue(ExcelReadModel excelModel, Sheet sheet,
			Object classObj) {
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
			if(row != null){
				for (int j = 0; j < excelModel.getItemModel().size(); j++) {
					String methodName = ((ExcelItemModel) excelModel.getItemModel()
							.get(j)).getMethodName();
					int coloumIndex = ((ExcelItemModel) excelModel.getItemModel()
							.get(j)).getColumsIndex();
	
					if ((methodName == null) || ("".equals(methodName))
							|| (excelModel.getColumnLen() < coloumIndex))
						continue;
					coloumIndex += excelModel.getStartLeftIndex();
					Cell cell = row.getCell((short) coloumIndex);
	
					if (cell == null)
						continue;
					Object objValue = null;
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							//  如果是date类型则 ，获取该cell的date值     
							if (HSSFDateUtil.isCellDateFormatted(cell)) {    
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date dd = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());   
								objValue = format.format(dd);
							}else{
								//防止字符串是数值且超过Integer类型最大值自动转换成最大值2147483647
								objValue = Double.valueOf(cell.getNumericCellValue());
								//if((Double)objValue > 2147483647){
									objValue = new DecimalFormat("#.##").format(objValue);
								//}
							}
							break;
						case Cell.CELL_TYPE_STRING:
							objValue = cell.getStringCellValue();
							break;
						default:
							objValue = cell.getStringCellValue();
					}
					if ((objValue != null) && (!"".equals(objValue))) {
						setMethodValue(classObj, methodName, objValue);
					}
				}
			}
			returnList.add(classObj);
		}

		return returnList;
	}

	private HSSFWorkbook getWorkBook(ExcelWriteModel excelModel) {
		if (excelModel == null)
			return null;
		HSSFWorkbook wb = new HSSFWorkbook();

		return wb;
	}

	private HSSFSheet getWorkBookSheet(HSSFWorkbook wb,
			ExcelWriteModel excelModel) {
		HSSFSheet sheet1 = null;

		sheet1 = wb.createSheet();
		if ((excelModel.getExcellSheelName() != null)
				&& (!"".equals(excelModel.getExcellSheelName())))
			wb.setSheetName(0, excelModel.getExcellSheelName());
		HSSFRow row = sheet1.createRow(0);
		if (excelModel.getColumnLen() > 0) {
			for (int i = 0; i < excelModel.getColumnLen(); i++) {
				HSSFCell localHSSFCell1 = row.createCell((short) i);
			}
		}
		if ((excelModel.getMainTitle() != null)
				&& (!"".equals(excelModel.getMainTitle()))) {
			HSSFCell cell = row.createCell(0);

			cell.setCellValue(excelModel.getMainTitle());
		}
		return sheet1;
	}

	public HSSFWorkbook getWorkBook(ExcelWriteModel excelModel,
			List mainModelList) {
		HSSFWorkbook wb = getWorkBook(excelModel);
		HSSFSheet sheet1 = getWorkBookSheet(wb, excelModel);

		int excelRowCount = excelModel.getStartRowIndex();

		if ((excelModel.getItemTitleList() != null)
				&& (excelModel.getItemTitleList().size() > 0)) {
//			excelRowCount += 2;
			HSSFCellStyle cellStyle = wb.createCellStyle();
			HSSFRow row = sheet1.createRow(excelRowCount);
			for (ExcelItemModel itemModel : excelModel.getItemTitleList()) {
				if (excelRowCount < itemModel.getRowIndex()) {
					excelRowCount = itemModel.getRowIndex();
					row = sheet1.createRow(excelRowCount);
				}

				int columnIndex = itemModel.getColumsIndex();
				HSSFCell cell = row.createCell((short) columnIndex);

				if ((itemModel.getColumsSpan() > 1)
						|| (itemModel.getRowsSpan() > 1)) {
					sheet1.addMergedRegion(new CellRangeAddress((short) excelRowCount,
							(short) columnIndex,
							(short) excelRowCount + itemModel.getRowsSpan() - 1,
							(short) (columnIndex + itemModel.getColumsSpan() - 1)));
					/*sheet1.addMergedRegion(new Region(
							(short) excelRowCount,
							(short) columnIndex,
							(short) excelRowCount + itemModel.getRowsSpan() - 1,
							(short) (columnIndex + itemModel.getColumsSpan() - 1)));*/
				}

				cellStyle.setAlignment((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(itemModel.getLabelValue());
			}
		}

		if ((mainModelList == null) || (mainModelList.size() == 0))
			return wb;
		if ((excelModel.getItemModel() != null)
				&& (excelModel.getItemModel().size() > 0)) {
			Map cellStyleList = new HashMap();
			HSSFRow row = null;
			for (int i = 0; i < mainModelList.size(); i++) {
				Object obj = mainModelList.get(i);

				excelRowCount++;

				row = sheet1.createRow(excelRowCount);

				for (int k = 0; k < excelModel.getItemModel().size(); k++) {
					ExcelItemModel itemModel = (ExcelItemModel) excelModel
							.getItemModel().get(k);
					excelRowCount = setMainModelValue(wb, excelModel, sheet1,
							excelRowCount, obj, itemModel, 0, row, k,
							cellStyleList);
				}
			}

		}

		return wb;
	}

	public HSSFWorkbook getWorkBook(ExcelWriteModel excelModel,
			List mainModelList, int startRow, HSSFWorkbook wb) {
		HSSFSheet sheet1 = wb.getSheetAt(0);
		if (sheet1 == null)
			sheet1 = getWorkBookSheet(wb, excelModel);
		int excelRowCount = startRow;
		if ((mainModelList == null) || (mainModelList.size() == 0))
			return wb;
		if ((excelModel.getItemModel() != null)
				&& (excelModel.getItemModel().size() > 0)) {
			Map cellStyleList = new HashMap();
			HSSFRow row = null;
			for (int i = 0; i < mainModelList.size(); i++) {
				Object obj = mainModelList.get(i);
				excelRowCount++;
				row = sheet1.getRow(excelRowCount);
				if (row == null)
					row = sheet1.createRow(excelRowCount);
				for (int k = 0; k < excelModel.getItemModel().size(); k++) {
					ExcelItemModel itemModel = (ExcelItemModel) excelModel
							.getItemModel().get(k);
					excelRowCount = setMainModelValue(wb, excelModel, sheet1,
							excelRowCount, obj, itemModel, 0, row, k,
							cellStyleList);
				}
			}
		}

		return wb;
	}

	public HSSFWorkbook getWorkBook(ExcelWriteModel excelModel, Object mainModel) {
		HSSFWorkbook wb = getWorkBook(excelModel);
		HSSFSheet sheet1 = getWorkBookSheet(wb, excelModel);

		int excelRowCount = 1;

		Map cellStyleList = new HashMap();
		ExcelItemModel itemModel;
		if ((excelModel.getMainWriteModelList() != null)
				&& (excelModel.getMainWriteModelList().size() > 0)) {
			HSSFRow row = null;
			for (int k = 0; k < excelModel.getItemModel().size(); k++) {
				itemModel = (ExcelItemModel) excelModel.getItemModel().get(k);
				if (excelRowCount < itemModel.getRowIndex())
					excelRowCount = itemModel.getRowIndex();
				row = sheet1.createRow(excelRowCount);
				excelRowCount = setMainModelValue(wb, excelModel, sheet1,
						excelRowCount, mainModel, itemModel, 1, row, k,
						cellStyleList);
			}

		}

		if ((excelModel.getItemTitleList() != null)
				&& (excelModel.getItemTitleList().size() > 0)) {
			excelRowCount += 2;
			HSSFRow row = sheet1.createRow(excelRowCount);
			for (ExcelItemModel itemModel1 : excelModel.getItemTitleList()) {
				int columnIndex = itemModel1.getColumsIndex();
				HSSFCell cell = row.createCell((short) columnIndex);
				HSSFCellStyle cellStyle = wb.createCellStyle();

				cellStyle.setAlignment((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(itemModel1.getLabelValue());
			}
		}

		if ((excelModel.getItemPropertyName() != null)
				&& (!"".equals(excelModel.getItemPropertyName()))
				&& (excelModel.getItemModel() != null)
				&& (excelModel.getItemModel().size() > 0)) {
			Class returnClass = null;
			Object itemObj = changePropertyToGetProperty(
					excelModel.getItemPropertyName(), mainModel, returnClass);

			if ((itemObj instanceof List)) {
				HSSFRow row = null;
				List list = (List) itemObj;

				for (int i = 0; i < list.size(); i++) {
					excelRowCount++;
					Object itemEntity = list.get(i);
					row = sheet1.createRow(excelRowCount);

					for (int k = 0; k < excelModel.getItemModel().size(); k++) {
						ExcelItemModel itemModel1 = (ExcelItemModel) excelModel
								.getItemModel().get(k);
						excelRowCount = setMainModelValue(wb, excelModel,
								sheet1, excelRowCount, itemEntity, itemModel1,
								0, row, k, cellStyleList);
					}
				}

			}

		}

		return wb;
	}

	private int setMainModelValue(HSSFWorkbook wb, ExcelWriteModel excelModel,
			HSSFSheet sheet1, int excelRowCount, Object mainModel,
			ExcelItemModel itemModel, int ifMain, HSSFRow row, int k,
			Map cellStyleList) {
		HSSFCell cell = null;

		if ((itemModel.getMethodName() == null)
				|| ("".equals(itemModel.getMethodName()))) {
			return excelRowCount;
		}
		String itemMethodName = itemModel.getMethodName();
		int maxLen = 0;
		Class returnClass = null;
		Object obj = null;
		int columnIndex = itemModel.getColumsIndex();

		if (itemMethodName.startsWith("List:")) {
			String[] methNameArr = itemMethodName.split(":");
			if (methNameArr.length > 0)
				maxLen = Integer.valueOf(methNameArr[1]).intValue();
			itemMethodName = methNameArr[2];

			String[] itemMethodNameArr = itemMethodName.split("\\.");
			if ((itemMethodNameArr != null) && (itemMethodNameArr.length > 0)) {
				for (int i = 0; i < itemMethodNameArr.length; i++) {
					obj = changePropertyToGetProperty(itemMethodNameArr[i],
							mainModel, returnClass);
					if (((obj instanceof Set)) || ((obj instanceof List))) {
						Object[] list = (Object[]) null;
						if ((obj instanceof Set))
							list = ((Set) obj).toArray();
						else if ((obj instanceof List))
							list = ((List) obj).toArray();
						int listSize = list.length;
						if (maxLen < listSize) {
							listSize = maxLen;
						}
						for (int j = 0; j < listSize; j++) {
							Object listObj = list[j];
							obj = changePropertyToGetProperty(
									itemMethodNameArr[(i + 1)], listObj,
									returnClass);
							cell = row.createCell((short) columnIndex);
							if (obj != null) {
								returnClass = obj.getClass();

								setCellValue(wb, cell, obj, itemModel,
										returnClass, cellStyleList);
							}

							columnIndex++;
						}

						if (maxLen <= listSize)
							break;
						for (int j = listSize; j < maxLen; j++) {
							cell = row.createCell((short) columnIndex);
							columnIndex++;
						}

						break;
					}

					mainModel = obj;
				}

			}

		} else {
			obj = changePropertyToGetProperty(itemMethodName, mainModel,
					returnClass);
			if (obj != null)
				returnClass = obj.getClass();
			cell = row.createCell((short) columnIndex);

			if (ifMain == 1) {
				if (StringUtil.isNotEmpty(itemModel.getLabelValue())) {
					cell.setCellValue(itemModel.getLabelValue());
				}

				cell = row.createCell((short) (columnIndex + 1));
			}

			if (obj != null) {
				setCellValue(wb, cell, obj, itemModel, returnClass,
						cellStyleList);
			}

		}

		return excelRowCount;
	}

	private HSSFCellStyle getCellStyle(HSSFWorkbook wb, Map map, String align,
			String valueType, String formatString) {
		align = align + "-" + valueType + "-" + formatString;
		HSSFCellStyle cellStyle = (HSSFCellStyle) map.get(align);
		if (cellStyle == null) {
			cellStyle = wb.createCellStyle();

			if ("center".equals(align))
				cellStyle.setAlignment((short) 2);
			else if ("left".equals(align))
				cellStyle.setAlignment((short) 1);
			else if ("right".equals(align)) {
				cellStyle.setAlignment((short) 3);
			}
			cellStyle.setDataFormat(HSSFDataFormat
					.getBuiltinFormat(formatString));
			map.put(align, cellStyle);
		}

		return cellStyle;
	}

	private void setCellValue(HSSFWorkbook wb, HSSFCell cell, Object obj,
			ExcelItemModel itemModel, Class returnClass, Map cellStyleList) {
		HSSFCellStyle cellStyle = null;
		SimpleDateFormat formatDateTimeAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		if ((returnClass.getName().equals(Date.class.getName()))
				|| (returnClass.getName().equals(Timestamp.class.getName()))) {
			cellStyle = getCellStyle(wb, cellStyleList, itemModel.getAlign(),
					"date", itemModel.getFormatString());
			if (cellStyle != null) {
				cell.setCellStyle(cellStyle);
			}

			if (obj != null) {
				switch (itemModel.getFormatString()) {
				case "yyyy-MM-dd HH:mm:ss":
					cell.setCellValue(formatDateTimeAll.format((Date) obj));
					break;
				case "yyyy-MM-dd HH:mm":
					cell.setCellValue(formatDateTime.format((Date) obj));
					break;
				case "yyyy-MM-dd":
					cell.setCellValue(formatDate.format((Date) obj));
					break;
				default:
					cell.setCellValue((Date) obj);
					break;
				}
				/*if (("h:mm".equals(itemModel.getFormatString()))
						|| ("hh:mm".equals(itemModel.getFormatString()))) {
					((Date) obj).setSeconds(0);

					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime((Date) obj);
					cal.set(11, ((Date) obj).getHours());
					cal.set(12, ((Date) obj).getMinutes());
					cal.set(14, 0);

					cell.setCellValue(cal.getTime());
				} else {
					cell.setCellValue((Date) obj);
				}*/
			}
		} else if ((returnClass.getName().equals(Double.class.getName()))
				|| (returnClass.getClass().getName().equals(Float.class
						.getName()))) {
			cellStyle = getCellStyle(wb, cellStyleList, itemModel.getAlign(),
					"FloatDouble", itemModel.getFormatString());

			if (obj != null) {
				if ((obj instanceof Double))
					cell.setCellValue(((Double) obj).doubleValue());
				else {
					cell.setCellValue(((Float) obj).floatValue());
				}
			}
		} else if ((returnClass.getName().equals(Integer.class.getName()))
				|| (returnClass.getName().equals("int"))) {
			cellStyle = getCellStyle(wb, cellStyleList, itemModel.getAlign(),
					"int", itemModel.getFormatString());
			if (cellStyle == null) {
				cell.setCellStyle(cellStyle);
			}
			if (obj != null) {
				if (("whether".equals(itemModel.getFormatString()))) {
					Integer cellInteger = (Integer) obj;
					if(cellInteger!=null && cellInteger.intValue() ==1){
						cell.setCellValue("是");
					}else{
						cell.setCellValue("否");
					}
				}else{
					cell.setCellValue(((Integer) obj).intValue());
				}
			}
			
			

		} else if((returnClass.getName().equals(Long.class.getName()))
				|| (returnClass.getName().equals("long"))){
			cellStyle = getCellStyle(wb, cellStyleList, itemModel.getAlign(),
					"int", itemModel.getFormatString());
			if (cellStyle == null) {
				cell.setCellStyle(cellStyle);
			}
			if (obj != null) {
				cell.setCellValue(((Long) obj).intValue());
			}
		}else if (obj != null) {
			cell.setCellValue(obj.toString());
		}

		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}

	public List creatItemModels(int startIndex, String[] cellNameArr, int flag) {
		if ((cellNameArr != null) && (cellNameArr.length > 0)) {
			List list = new ArrayList();
			int cellIndex = startIndex;
			for (int i = 0; i < cellNameArr.length; i++) {
				String columnName = cellNameArr[i];

				if (columnName.startsWith("List:")) {
					int maxLen = 0;
					String[] cellNameA = columnName.split(":");
					if ((cellNameA != null) && (cellNameA.length > 2)) {
						columnName = cellNameA[2];
						maxLen = Integer.valueOf(cellNameA[1]).intValue();
					}
					ExcelItemModel itemModel = new ExcelItemModel();
					list.add(creatItemModel(cellIndex, cellNameArr[i], flag,
							itemModel));
					cellIndex += maxLen;
				} else {
					ExcelItemModel itemModel = new ExcelItemModel();
					list.add(creatItemModel(cellIndex, cellNameArr[i], flag,
							itemModel));
					cellIndex++;
				}
			}

			return list;
		}
		return null;
	}

	public ExcelItemModel creatItemModel(int cellIndex, String cellName,
			int flag, ExcelItemModel itemModel) {
		itemModel.setColumsIndex(cellIndex);
		if (flag == 0) {
			itemModel.setLabelValue(cellName);
		} else if ((cellName != null) && (cellName.indexOf("$") > -1)) {
			String[] cellNameArr = cellName.split("\\$");
			if (cellNameArr.length > 0)
				itemModel.setMethodName(cellNameArr[0]);
			if ((cellNameArr.length > 1) && (cellNameArr[1] != null)
					&& (!"".equals(cellNameArr[1])))
				itemModel.setFormatString(cellNameArr[1]);
		} else {
			itemModel.setMethodName(cellName);
		}

		return itemModel;
	}
	
	public static Row getRow(Sheet sheet, int index) {
		// 取得行
		Row row = sheet.getRow(index);
		// 如果单元格不存在
		if (row == null) {
			// 创建行
			row = sheet.createRow(index);
		}

		return row;
	}

	public static Cell getCell(Row row, int index){
		// 取得单元格
		Cell cell = row.getCell(index);
		// 如果单元格不存在
		if (cell == null)
			// 创建行
			cell = row.createCell(index);

		return cell;
	}

}