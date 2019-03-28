package com.hansholdings.basic.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
 /*   private final static String file = "adconfig.properties";
    private static Properties prop = new Properties();

    static {
        try {
            //InputStream ips = new BufferedInputStream (new FileInputStream(file));
            //System.out.println(Properties1Util.class.getResource(file).getPath());
            //String path = Properties1Util.class.getClassLoader().getResource(file).getPath();
            //prop.load(new FileInputStream(Properties1Util.class.getResource(file).getPath()));
            //System.out.println(System.getProperty("user.dir"));
            System.out.println(PropertiesUtil.class.getClass().getResource("/").getPath().toString().substring(1));
            //System.out.println(ClassLoader.getSystemResource("adconfig.properties").toString().substring(6));
            String p=PropertiesUtil.class.getClass().getResource("/").getPath().toString().substring(1)+file;
         //  String p=ClassLoader.getSystemResource(file).toString().substring(6);
            prop.load(new FileInputStream(p));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static boolean setProper(JSONObject ldap) {
        try {
            Set<String> set = ldap.keySet();
            for(String key : set){
                if(ldap.get(key)!=null) {
                    System.out.println("key==" + key);
                   //searchClass.append("(" + key + "=" + (String) ldap.get(key) + ")");
                    prop.setProperty(key, (String) ldap.get(key));
                }

            }
            FileOutputStream fos = new FileOutputStream(ClassLoader.getSystemResource(file).toString().substring(6));
            prop.store(fos, null);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public static void main(String[] args) {
      //  System.out.println(Class.class.getClass().getResource("/").getPath());
        System.out.println(ClassLoader.getSystemResource("adconfig.properties").toString().substring(6));
        System.out.println(System.getProperty("user.dir"));
     //   System.out.println("修改前key为startTime的value的值ֵ" + Properties1Util.getProperty("password"));
       // PropertiesUtil.setProper("password", "300");
      // System.out.println("修改后key为startTime的value的值" + PropertiesUtil.getProperty("password"));
    }
}
