package com.hansholdings.basic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MyExcelWriter {
	
	public MyExcelWriter(){
		
	}
	
	public boolean write2Excel(List<String> mod , JSONArray jsary , String path) throws Exception{
		boolean rslt = false;
		HSSFWorkbook wbook = new HSSFWorkbook();
		HSSFSheet sheet = wbook.createSheet();
		
		HSSFRow head = sheet.createRow(0);
		for(int idx = 0 ; idx < mod.size() ; idx++){
			HSSFCell cell = head.createCell(idx,HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(mod.get(idx));
		}
		
		Iterator itor = jsary.iterator();
		while(itor.hasNext()){
			JSONObject tmp = (JSONObject)itor.next();
			HSSFRow dta = sheet.createRow(sheet.getLastRowNum()+1);
			for(int idx = 0 ; idx < mod.size() ; idx++){
				HSSFCell cell = dta.createCell(idx,HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(tmp.getString(mod.get(idx)));
			}
		}
		
		try{
			File file = new File(path);
			if(file.exists() && file.isDirectory()){
				throw new Exception("[MyExcelWriter]:can't write data to a directory path,check the path.");
			}else if(!file.exists()){
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(file);
		}catch(Exception e){
			throw e;
		}
		
		OutputStream os = new FileOutputStream(new File(path),true);
		wbook.write(os);
		rslt = true;
		return rslt;
	}
	
	public String[] getRowName(String str) {
		String s[] = str.split(",");
		return s;
	}
	
	public static void main(String[] args) throws Exception{
		MyExcelWriter xlsWriter = new MyExcelWriter();
		String[] cols = new String[]{"姓名","身份证号","联系电话","现工作单位"};
		List mod = Arrays.asList(cols);
		HashMap hm = new HashMap();
		hm.put("姓名", "张三");
		hm.put("身份证号", "420503198402281812");
		hm.put("联系电话", "18671702764");
		hm.put("现工作单位", "湖北海盟科技有限责任公司");
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(hm));
		JSONArray jsary = new JSONArray();
		jsary.add(json);
		xlsWriter.write2Excel(mod, jsary, "e:/ERR_111.xls");
	}
}
