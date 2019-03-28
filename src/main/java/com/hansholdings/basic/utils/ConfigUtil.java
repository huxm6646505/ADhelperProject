package com.hansholdings.basic.utils;

import java.io.*;
import java.util.Properties;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @author Administrator
 *
 */
public class ConfigUtil {

	public static String rootPath;
	private static String fileName = "WEB-INF/classes/adconfig.properties";
	private static Properties systemPro;
	
	
	public static String readParam(String name){
		if(systemPro == null){
			systemProInit();
		}
		return systemPro.getProperty(name);
	}
	
	public static boolean writeParam(JSONObject ldap){
		try {
			if(systemPro == null){
				systemProInit();
			}
			Set<String> set = ldap.keySet();
			for(String key : set){
				if(ldap.get(key)!=null) {
					System.out.println("key==" + key);
					systemPro.setProperty(key, (String) ldap.get(key));
				}
			}
			FileOutputStream oFile = new FileOutputStream(new File(rootPath+"/"+fileName));
			systemPro.store(oFile, "修改");
			oFile.close();
			return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	private static void systemProInit() {
			rootPath=System.getProperty("user.dir").replace("bin","webapps/ROOT/");

		BufferedReader br = null;
		File f = new File(rootPath+fileName);
		try {
			FileInputStream fis = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			systemPro = new Properties();
			systemPro.load(new FileInputStream(rootPath+fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
