package com.hansholdings.basic.utils;

import com.hansholdings.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by maozz11347 on 2017/7/27.
 */
//@Configuration
//@Component
public class ADConfig {
/*
    @Value("${ad.username}")
    private  String adminName;

    @Value("${ad.password}")
    private  String adminPassword;

    @Value("${ad.ldapURL}")
    private  String ldapURL;

   // @Bean
    public LdapContext readConf() {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        LdapContext ctx=null;
        try {
          ctx = new InitialLdapContext(env, null);
            System.out.println("222");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ctx;
    }*/
}
