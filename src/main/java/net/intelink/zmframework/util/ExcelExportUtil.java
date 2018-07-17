package net.intelink.zmframework.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.intelink.zmframework.annotation.ExportAnnotation;
import net.intelink.zmframework.model.ExcelItemModel;
import net.intelink.zmframework.model.ExcelWriteModel;

/**
 * Excel导出工具类  Excel版本2007
 * @author feng
 *
 */
public class ExcelExportUtil {
	
	private void setSXSSCellValue(XSSFWorkbook wb, XSSFCell cell, Object obj, ExcelItemModel itemModel,
			Class<?> returnClass) {
		SimpleDateFormat formatDateTimeAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat formatDateTime2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		if (returnClass.getName().equals(Date.class.getName())
				|| returnClass.getName().equals(Timestamp.class.getName())) {
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
				case "yyyy/MM/dd":
					cell.setCellValue(formatDate2.format((Date) obj));
					break;
				case "yyyy/MM/dd HH:mm":
					cell.setCellValue(formatDateTime2.format((Date) obj));
					break;
				default:
					cell.setCellValue((Date) obj);
					break;
				}
			}
		} else if (returnClass.getName().equals(Double.class.getName())
				|| returnClass.getClass().getName().equals(Float.class.getName())) {
			if (obj != null) {
				if ((obj instanceof Double))
					cell.setCellValue(((Double) obj).doubleValue());
				else {
					cell.setCellValue(((Float) obj).floatValue());
				}
			}
		} else if (returnClass.getName().equals(Integer.class.getName()) || returnClass.getName().equals("int")) {
			if (obj != null) {
				if (("whether".equals(itemModel.getFormatString()))) {
					Integer cellInteger = (Integer) obj;
					if (cellInteger != null && cellInteger.intValue() == 1) {
						cell.setCellValue("是");
					} else {
						cell.setCellValue("否");
					}
				} else {
					cell.setCellValue(((Integer) obj).intValue());
				}
			}
		} else if (returnClass.getName().equals(Long.class.getName()) || returnClass.getName().equals("long")) {
			if (obj != null) {
				cell.setCellValue(((Long) obj).intValue());
			}
		} else if (obj != null) {
			cell.setCellValue(obj.toString());
		}
	}
	
	/**
	 * 设置表头
	 * @param wb
	 * @param entityObj
	 * @return
	 * @throws Exception
	 */
	public ExcelWriteModel setXSSFWorkBookHeader(XSSFWorkbook wb,Class<?> t) throws Exception {
//		SXSSFWorkbook wb = new SXSSFWorkbook();
		ExcelWriteModel excelModel = null;
		if (wb != null) {
			// 创建一个sheet
			XSSFSheet sheet = wb.createSheet();
			
			excelModel = getWriteModel(t);
			// 设置sheet名称
			if (StringUtil.isNotEmpty(excelModel.getExcellSheelName()))
				wb.setSheetName(0, excelModel.getExcellSheelName());
			// 获取开始行行号，默认为0
			int excelRowCount = excelModel.getStartRowIndex();
			// 设置表头
			if (excelModel.getItemTitleList() != null && excelModel.getItemTitleList().size() > 0) {
				XSSFCellStyle cellStyle = wb.createCellStyle();
				XSSFRow row = sheet.createRow(excelRowCount);
				for (ExcelItemModel itemModel : excelModel.getItemTitleList()) {
					if (excelRowCount < itemModel.getRowIndex()) {
						excelRowCount = itemModel.getRowIndex();
						row = sheet.createRow(excelRowCount);
					}
					int columsIndex = itemModel.getColumsIndex();
					XSSFCell cell = row.createCell(columsIndex);
					cellStyle.setAlignment((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(itemModel.getLabelValue());
				}
			}
		}
		return excelModel;
	}
	
	/**
	 * 设置传入的表头
	 * @param wb
	 * @param headerTitles 表头
	 * @return
	 * @throws Exception
	 */
	public XSSFWorkbook setXSSFWorkBookHeader(List<String> headerTitles) {
		XSSFWorkbook wb = null;
		if (headerTitles != null && headerTitles.size() > 0) {
			wb = new XSSFWorkbook();
			// 创建一个sheet
			XSSFSheet sheet = wb.createSheet();
			XSSFRow row = sheet.createRow(0);
			for(int i = 0;i < headerTitles.size();i++){
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(headerTitles.get(i));
			}
		}
		return wb;
	}
	
	/** 
	 * 设置导出内容
	 * @param wb
	 * @param excelModel
	 * @param beginRow
	 * @param mainModelList
	 * @throws Exception
	 */
	public void setXSSFWorkBookBody(XSSFWorkbook wb,ExcelWriteModel excelModel,int beginRow,List<?> mainModelList) throws Exception {
		if (mainModelList == null || mainModelList.size() == 0)
			return ;
		if (excelModel.getItemModel() != null && excelModel.getItemModel().size() > 0) {
			Map<String, Method> methodMap = new HashMap<>();
			Object x = mainModelList.get(0);
			for (ExcelItemModel em : excelModel.getItemModel()) {
				if (StringUtil.isNotEmpty(em.getMethodName())) {
					Method method = x.getClass().getMethod("get" + em.getMethodName().substring(0, 1).toUpperCase()
							+ em.getMethodName().substring(1), new Class[0]);
					methodMap.put(em.getMethodName(), method);
				}
			}
			XSSFSheet sheet = wb.getSheetAt(0);
			for (int i = 0; i < mainModelList.size(); i++) {
				XSSFRow row = sheet.createRow(beginRow);
				for (int k = 0; k < excelModel.getItemModel().size(); k++) {
					ExcelItemModel excelItemModel = excelModel.getItemModel().get(k);
					Method method = methodMap.get(excelItemModel.getMethodName());
					Class<?> filedType = method.getReturnType();
					Object invoke = method.invoke(mainModelList.get(i), null);
					XSSFCell cell = row.createCell(k + excelModel.getStartLeftIndex());
					setSXSSCellValue(wb, cell, invoke, excelItemModel, filedType);
					
				}
				beginRow ++;
			}
		}
		
	}
	
	
	/**
	 * 获取Excel写入对象
	 * @param objcla
	 * @return
	 * @throws Exception
	 */
	private ExcelWriteModel getWriteModel(Class<?> t)throws Exception{
		ExcelWriteModel excelModel = new ExcelWriteModel();
		excelModel.setStartRowIndex(0);
		excelModel.setStartLeftIndex(0);
//		Class<?> cla = objcla.getClass();
		Method cs = t.getMethod("excelMap",null);
		Map<String,String> mmp =(Map<String,String>) cs.invoke(t.newInstance(), null);
		excelModel.setColumnLen(mmp.size()); 
		ExcelItemModel 	item = null;
		Field [] fields =  t.getDeclaredFields(); 
		int count = 0;
		for(Field field:fields){
			if(mmp.get(field.getName())!=null){
				item = this.getItemModel(field.getName(), "center", mmp.get(field.getName()), excelModel, count);
				ExportAnnotation annotation = field.getAnnotation(ExportAnnotation.class);
				if(annotation!=null){
					item.setFormatString(annotation.value());
				}
				count ++;
			}
		}
		item.setRowIndex(0);
		return excelModel;
	}
	
	
	/**
	 * Excel导出公共方法
	 * @param response
	 * @param list
	 * @param fileName
	 * @param objcla
	 * @return
	 * @throws Exception
	 */
	public HttpServletResponse exporExcel(XSSFWorkbook book,HttpServletResponse response,String fileName)throws Exception{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		fileName = fileName + year + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day)  + ".xlsx";
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("GBK"), "ISO8859-1") + "\"");
		OutputStream responseStream = response.getOutputStream();
		book.write(responseStream);
		//写入临时文件
		/*String filepath = PathCommonUtil.getUploadFileTempPath();
		File f = new File(filepath);
		File fe = new File(f,UUID.randomUUID()+".xlsx");
		if(!fe.exists()){
			fe.createNewFile();
		}
		OutputStream responseStreamTemp = new FileOutputStream(fe);
		book.write(responseStreamTemp);*/
		responseStream.close();
//		responseStreamTemp.close();
		return response;
	}
	
	private ExcelItemModel getItemModel(String methodName,String align,String title,ExcelWriteModel excelModel,int i){ 
		ExcelItemModel itemModel = new ExcelItemModel();
		itemModel.setAlign(align);
		itemModel.setColumsIndex(i); 
		itemModel.setMethodName(methodName); 
		itemModel.setLabelValue(title); 
		if(methodName != null && !"".equals(methodName))
		excelModel.getItemModel().add(itemModel);
		excelModel.getItemTitleList().add(itemModel);
		return itemModel;
	}
}
