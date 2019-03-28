package com.hansholdings.basic.utils;

import com.alibaba.fastjson.JSON;
import com.hansholdings.manage.entity.OU;
import com.hansholdings.manage.entity.User;
import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import com.sun.jndi.ldap.ctl.VirtualListViewResponseControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

/**
 * Created by maozz11347 on 2017/7/17.
 */
//@Component
public class ADUserUtils {

   /* @Resource
    private  ADConfig adc;

    private  static ADConfig adc1;

    @PostConstruct
    public void init() {
        this.adc1 = adc;
    }

    public static List<User> getADUsers() {
        adc1.readConf();
        LdapContext ctx = null;
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://172.18.254.210:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "administrator@hansholdings.com");
        env.put(Context.SECURITY_CREDENTIALS, "hans123456");

        try {
            ctx = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        List<User> users = new ArrayList<User>();
        try {
         //   ctx =adc1.readConf(); //
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
            String searchBase = "DC=hansholdings,DC=com";
            Long totalResults = 0L;
            String returnedAtts[] = {
                      "name", "userPrincipalName","distinguishedName", "sAMAccountName","whenChanged", "whenCreated","mail","telephoneNumber", "physicalDeliveryOfficeName","initials"}; //定制返回属性
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration<SearchResult> answer = ctx.search("OU=大族集团,"+searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes Attrs = sr.getAttributes();
                totalResults++;
                if (Attrs != null) {
                    try {
                        User user = new User();
                        for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
                            Attribute Attr = (Attribute) ne.next();
                            String Id = Attr.getID().toString();
                            String value = "";
                            //读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e.hasMore(); ) {
                                value = e.next().toString();
                            }
                            if ("name".equals(Id)) {
                                user.setName(value);
                            } else if ("sAMAccountName".equals(Id)) {
                                user.setsAMAccountName(value);
                            } else if ("userPrincipalName".equals(Id)) {
                                user.setUserPrincipalName(value);
                            } else if ("whenChanged".equals(Id)) {
                                user.setWhenChanged(value);
                            } else if ("whenCreated".equals(Id)) {
                                user.setWhenCreated(value);
                            } else if ("mail".equals(Id)) {
                                user.setMail(value);
                            } else if ("telephoneNumber".equals(Id)) {
                                user.setTelephoneNumber(value);
                            } else if ("physicalDeliveryOfficeName".equals(Id)) {
                                user.setPhysicalDeliveryOfficeName(value);
                            } else if ("distinguishedName".equals(Id)) {
                                user.setUid(value);
                            } else if ("initials".equals(Id)) {
                                user.setInitials(value);
                            } else {

                            }
                        }
                        user.setId(totalResults);
                        users.add(user);
                    }
                    catch (NamingException e) {
                        System.err.println("Throw Exception : " + e);
                    }
                }
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
            users=null;
        }
        return users;
    }*/

    /**
     * 修改
     *
     * @return
     */
 /*   public static boolean updateUser(User user) {
        try {
            LdapContext ctx = null;
            Hashtable env = new Hashtable();
            String LDAP_URL = "ldap://172.18.254.210:389"; // LDAP访问地址
            String adminName = "administrator@hansholdings.com"; // 注意用户名的写法：domain\User或
            String adminPassword = "hans123456"; // 密码
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, adminName);
            env.put(Context.SECURITY_CREDENTIALS, adminPassword);
            try {
                ctx = new InitialLdapContext(env, null);
                System.out.println("认证成功");// 这里可以改成异常抛出。
            } catch (javax.naming.AuthenticationException e) {
                System.out.println("认证失败");
            } catch (Exception e) {
                System.out.println("认证出错：" + e);
            }
            //LdapContext dc =adc1.readConf(); //
            System.out.println("updating...\n");
            ModificationItem[] mods = new ModificationItem[1];
            Attributes attrs = new BasicAttributes(true);
          //  attrs.put("name",user.getName());
            attrs.put("sAMAccountName",user.getsAMAccountName());
            attrs.put("userPrincipalName",user.getUserPrincipalName());
            attrs.put("initials",user.getInitials());
            attrs.put("mail",user.getMail());
            attrs.put("telephoneNumber",user.getTelephoneNumber());
            attrs.put("physicalDeliveryOfficeName",user.getPhysicalDeliveryOfficeName());
            *//* 修改属性 *//*
            ctx.modifyAttributes(user.getDistinguishedName(),DirContext.REPLACE_ATTRIBUTE, attrs);
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    *//**
     * 刪除用戶
     *
     * @return
     *//*
    public static boolean deleteUser(String uid) {
        try {
            LdapContext ctx = null;
            Hashtable env = new Hashtable();
            String LDAP_URL = "ldap://172.18.254.210:389"; // LDAP访问地址
            String adminName = "administrator@hansholdings.com"; // 注意用户名的写法：domain\User或
            String adminPassword = "hans123456"; // 密码
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, LDAP_URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, adminName);
            env.put(Context.SECURITY_CREDENTIALS, adminPassword);
            try {
                //  ctx = new InitialDirContext(env);// 初始化上下文
                ctx = new InitialLdapContext(env, null);
                System.out.println("认证成功");// 这里可以改成异常抛出。
            } catch (javax.naming.AuthenticationException e) {
                System.out.println("认证失败");
            } catch (Exception e) {
                System.out.println("认证出错：" + e);
            }
            System.out.println("updating...\n");
            ctx.destroySubcontext(uid);
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public  static Boolean chagePasword(User user){
        LdapContext ctx = null;
        String adminName = "administrator@hansholdings.com";
        String adminpassword = "hans123456";
        // String keystore = "C:\\Program Files\\Java\\jdk1.8.0_131\\jre\\lib\\security\\cacerts";
        String keystore="C:\\Program Files\\Java\\jdk1.8.0_131\\bin\\security51.keystore";
        String keyPassword = "123456";
        String ldapURL = "ldaps://172.18.254.210:636";
        String searchBase = "DC=hansholdings,DC=com";
        String returnedAtts[] = { "distinguishedName" };

        Hashtable env = new Hashtable();
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.Net.ssl.trustStorePassword", keyPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminpassword);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.PROVIDER_URL, ldapURL);
        try {
            System.out.println("Start InitialLdapContext");
            ctx = new InitialLdapContext(env, null);
            System.out.println("InitialLdapContext succeed");
        } catch (NamingException e) {
            System.out.println("Problem initial_Ldap NamingException: " + e);
        }

        ModificationItem[] mods = new ModificationItem[1];
        String newQuotedPassword = "\"" + user.getUnicodePwd() + "\"";
        System.out.println("JSON:"+JSON.toJSONString(user));
        System.out.println("newQuotedPassword="+newQuotedPassword);
       if(user.getUnicodePwd()==null||user.getUnicodePwd().equals("")){
           return false;
       }
        try {
            System.out.println("Start reset password");
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            ctx.modifyAttributes(user.getDistinguishedName(), mods);
            System.out.println("Finish reset password" + user.getDistinguishedName());
            ctx.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Problem mod_Pwd UnsupportedEncodingException: " + e);
            return false;
        } catch (NamingException e) {
            System.out.println("Problem mod_Pwd NamingException: " + e);
            return false;
        }
        return true;
    }

*/
}
