package com.xunxin.util.app.excel;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author: Libra
 * Date: 2017年1月17日 上午11:12:19
 * Version: 1.0
 * @Description:  读取Excel文件工具类
 */
@SuppressWarnings({ "deprecation", "resource", "null" })
public class ReadExcel {

	/**
	 * 对外提供读取excel 的方法
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readExcel(File file,int index) throws IOException{
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if("xls".equals(extension)){
			return read2003Excel(file,index);
		} else if("xlsx".equals(extension)){
			return read2007Excel(file,index);
		} else {
			throw new IOException("不支持文件类型");
		}

	}

	/**
	 * 读取 office 2003 Excel文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static List<List<Object>> read2003Excel(File file,int index) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(index);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int counter = 0;
		for(int i = sheet.getFirstRowNum();counter < sheet.getPhysicalNumberOfRows();i++){
			row = sheet.getRow(i);
			if(null != row){
				continue;
			}else {
				counter++;
			}
			
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				if(null != cell){
					continue;
				}
				DecimalFormat df = new DecimalFormat("0"); //格式化 number String 字符
				//格式化日期字符串
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DecimalFormat nf = new DecimalFormat("0.00"); //格式化数字
			
				switch (cell.getCellType()){
				case XSSFCell.CELL_TYPE_STRING:
					System.out.println(i + "行" + j + "列 is String Type");
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					System.out.println(i + "行" + j + "列 is Number type ; DateFormat:");
					if("0".equals(cell.getCellStyle().getDataFormatString())){
						value = df.format(cell.getNumericCellValue()); 
					}else if("General".equals(cell.getCellStyle().getDataFormatString())){
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					System.out.println(i + "行" + j + "列 is boolean type");
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:	
					System.out.println(i + "行" + j + "列 is Blank type");
					value = "";
					break;
				default :
					System.out.println(i + "行" + j + "列 is default type");
					value = cell.toString();
				}
				if(null == value || "".equals(value)){
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}

	/**
	 * 读取 office 2007 Excel文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static List<List<Object>> read2007Excel(File file,int index) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>(); 
		// 构造 XSSFWorkbook 对象, strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file)); 
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(index);
		Object value = null;
		XSSFRow row = null;   //行
		XSSFCell cell = null;  //列
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if(null == row){
				continue;
			} else {
				counter++;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if(null == cell){
					continue;
				}
				//格式化  number String 字符
				DecimalFormat df = new DecimalFormat("0");
				//格式化日期字符串
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//格式化数字
				DecimalFormat nf = new DecimalFormat("0.00");
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue(); 
//					System.out.println(i + "行" + j + "列  is :" + value);
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if("@".equals(cell.getCellStyle().getDataFormatString())){
						value = df.format(cell.getNumericCellValue());
//						System.out.println(i + "行" + j + "列  is :" + value);
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())){
						value = nf.format(cell.getNumericCellValue());
						long longVal = Math.round(cell.getNumericCellValue());  
						if (Double.parseDouble(longVal + ".0") == Double.parseDouble((String) value)) { 
							value = longVal;  
						}else{
//							value = value;  
						}
//						System.out.println(i + "行" + j + "列  is :" + value);
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
//						System.out.println(i + "行" + j + "列  is :" + value);
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
//					System.out.println(i + "行" + j + "列  is :" + value);
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
//					System.out.println(i + "行" + j + "列  is :" + value);
					break;
				default:
					value = cell.toString(); 
//					System.out.println(i + "行" + j + "列  is :" + value);
				}
				if(null == value || "".equals(value)){
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}

	
	
	
}
