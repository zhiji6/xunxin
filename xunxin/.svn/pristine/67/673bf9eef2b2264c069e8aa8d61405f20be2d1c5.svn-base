package com.xunxin.util.app.excel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;



import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author: Libra
 * Date:  2017年2月13日	
 * Version:  1.0
 * @Description:  使用POI将从数据库搜索出来的记录导出成EXCEL文件并弹出下载框
 *		导出工具测试类
 *	HSSFWorkbook excell的文档对象
	HSSFSheet excell的表单
	HSSFRow excell的行
	HSSFCell excell的格子单元
	HSSFFont excell字体
	HSSFName 名称
	HSSFDataFormat 日期格式
 *
 */
public class ExportToExcel {

	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void resultSetToExcel(ResultSet rs,String xlsName,String sheetName) throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, sheetName);
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell;
		ResultSetMetaData md = rs.getMetaData();
		int nColumn = md.getColumnCount(); 
		/** 写入各字段的名称  */
		for(int i = 1;i <= nColumn;i++){
			cell = row.createCell((short) (i-1));
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(md.getColumnLabel(i));
		}
		
		int iRow = 1; 
		/** 写入各条记录，每条记录对应Excel中的一行    */
		while (rs.next()) {
			row = sheet.createRow((short) iRow);
			for(int j = 1;j <= nColumn;j++){
				cell = row.createCell((short) (j - 1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if (rs.getObject(j) != null) {
					cell.setCellValue(rs.getObject(j).toString());
				}else{
					cell.setCellValue("");
				}
			}
			iRow++;
		}
		
		
		
		
	}
	
	
	
	
	
	
	
}
