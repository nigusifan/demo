package net.intelink.zmframework.model;


import java.util.List;

/**
 * Excel单元对象
 * @author feng
 *
 */
public class ExcelItemModel {
	
  private int columsIndex = 0;

  private int rowIndex = 0;

  private String methodName = "";

  private String align = "center";

  private String formatString = "";

  private String labelValue = "";

  private List<ExcelItemModel> itemModel = null;

  private List<ExcelItemModel> itemTitleList = null;

  private int columsSpan = 1;

  private int rowsSpan = 1;

  public int getColumsIndex() {
    return this.columsIndex;
  }

  public void setColumsIndex(int columsIndex) {
    this.columsIndex = columsIndex;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getAlign() {
    return this.align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public String getFormatString() {
    return this.formatString;
  }

  public void setFormatString(String formatString) {
    this.formatString = formatString;
  }

  public int getRowIndex() {
    return this.rowIndex;
  }

  public void setRowIndex(int rowIndex) {
    this.rowIndex = rowIndex;
  }

  public String getLabelValue() {
    return this.labelValue;
  }

  public void setLabelValue(String labelValue) {
    this.labelValue = labelValue;
  }

  public List<ExcelItemModel> getItemModel() {
    return this.itemModel;
  }

  public void setItemModel(List<ExcelItemModel> itemModel) {
    this.itemModel = itemModel;
  }

  public List<ExcelItemModel> getItemTitleList() {
    return this.itemTitleList;
  }

  public void setItemTitleList(List<ExcelItemModel> itemTitleList) {
    this.itemTitleList = itemTitleList;
  }

  public int getColumsSpan() {
    return this.columsSpan;
  }

  public void setColumsSpan(int columsSpan) {
    this.columsSpan = columsSpan;
  }

  public int getRowsSpan() {
    return this.rowsSpan;
  }

  public void setRowsSpan(int rowsSpan) {
    this.rowsSpan = rowsSpan;
  }
}