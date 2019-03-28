package com.hansholdings.basic.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyExcelReader {
	
	public MyExcelReader(){
		
	}
	
	//将EXCEL文件流中的数据读取到JSON中  默认只读取第一个sheet
	//mod	:将要返回的json对象的模版，数据会按照模版进行填充
	//xls	:excel文件的2进制数据
	//ver	:所读取的excel的版本，值 	xls | xlsx
	public JSONArray read2JSONObject(List<String> mod , byte[] xls , String ver , Integer[] skipR , Integer[] skipC) throws Exception{
		JSONArray jsarys = new JSONArray();
		InputStream is = new ByteArrayInputStream(xls);
		List<Integer> sRow = null,sCol = null;
		if(skipR != null && skipR.length>0) {
			sRow = Arrays.asList(skipR);
		}
		if(skipC != null && skipC.length>0){
			sCol = Arrays.asList(skipC);
		}
		if(!StringUtils.isBlank(ver) && StringUtils.equals("xls", ver.toLowerCase())){
			jsarys = for2003Xls(mod,is,sRow,sCol);
		}else if(!StringUtils.isBlank(ver) && StringUtils.equals("xlsx", ver.toLowerCase())){
			jsarys = for2007Xlsx(mod,is,sRow,sCol);
		}else{
			throw new Exception("输入的Excel版本错误，仅限xls|xlsx文件");
		}
		return jsarys;
	}
	
	private JSONArray for2003Xls(List<String> mod , InputStream is , List<Integer> skipR , List<Integer> skipC) throws Exception{
		JSONArray jarys = new JSONArray();
		HSSFWorkbook wbook = new HSSFWorkbook(is);
		HSSFSheet sheet = wbook.getSheetAt(0);
		for(int i = 1; i < sheet.getLastRowNum()+1; i++){
			List<String> tmp = new ArrayList<String>();
			if(skipR != null && skipR.contains(i+1)) {
				continue;
			}
			HSSFRow row = sheet.getRow(i);
			JSONObject jobj = new JSONObject();
			//int size=row.getLastCellNum();
			for(int k = 0; k < mod.size(); k++){
				if(skipC != null && skipC.contains(k+1)) {
					continue;
				}
				HSSFCell cell = row.getCell(k);
				String val = get2003XlsValue(cell);
				tmp.add(val);
			}
			int idx = 0;
			for(String tkey : mod){
				jobj.put(tkey, tmp.get(idx));
				idx++;
			}
			jarys.add(jarys.size(),jobj);
		}
		return jarys;
	}
	
	private JSONArray for2007Xlsx(List<String> mod , InputStream is , List<Integer> skipR , List<Integer> skipC) throws Exception{
		JSONArray jsarys = new JSONArray();
		XSSFWorkbook wbook = new XSSFWorkbook(is);
		XSSFSheet sheet = wbook.getSheetAt(0);
		for(int i = 0; i < sheet.getLastRowNum()+1; i++){
			List<String> tmp = new ArrayList<String>();
			if(skipR != null && skipR.contains(i+1)) {
				continue;
			}
			XSSFRow row = sheet.getRow(i);
			JSONObject obj = new JSONObject();
			for(int k = 1; k < mod.size(); k++){
				if(skipC != null && skipC.contains(k+1)) {
					continue;
				}
				XSSFCell cell = row.getCell(k);
				String val = get2007XlsxValue(cell);
				tmp.add(val);
			}
			int idx = 0;
			for(String tkey : mod){
				obj.put(tkey, tmp.get(idx));
				idx++;
			}
			jsarys.add(jsarys.size(),obj);
		}
		return jsarys;
	}
	
	//EXCEL-2007  xlsx 适用
	//根据cell的类型进行取值和转换
	private String get2007XlsxValue(XSSFCell xssfCell) {
		if(xssfCell==null){
			return "";
		}else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}

	//EXCEL-2003  xls 适用
	//根据cell的类型进行取值和转换
	private String get2003XlsValue(HSSFCell hssfCell) {
		DecimalFormat df = new DecimalFormat("0");
		if(hssfCell == null){
			return "";
		}else if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}

	}
	
	public String[] getRowName(String str) {
		String s[] = str.split(",");
		return s;
	}
	
	public static void main(String[] args) throws Exception{
		
		String path = "D:/Workspace/kettle/export/1.xls";
		
		FileInputStream fis = new FileInputStream(path);
		byte[] bytes = new byte[fis.available()];
		fis.read(bytes);
		MyExcelReader reader = new MyExcelReader();
		List keys = Arrays.asList(new String[]{"xm","sfzh","hklx","mz","zzmm","lxdh"});
		JSONArray jsary = reader.read2JSONObject(keys,bytes,"xls",new Integer[]{0},null);
		System.out.println(jsary.toString());
	}

}
