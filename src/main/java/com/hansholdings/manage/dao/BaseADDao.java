package com.hansholdings.manage.dao;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.utils.ConfigUtil;
import com.hansholdings.basic.utils.JsonTools;
import com.hansholdings.manage.entity.*;
import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * @author maozz11347
 * @date 2017/7/17
 */
@Component
public class BaseADDao {
    //线上环境
  /* String adminName = null;
    String adminPassword = null;
    String ldapURL = null;*/

    @Value("${ad.username}")
    private String adminName;

    @Value("${ad.password}")
    private String adminPassword;

    @Value("${ad.ldapURL}")
    private String ldapURL;

    @Value("${ad.baseDN}")
    private String baseDN;

    @Value("${ad.keystore}")
    private String keystore;

    @Value("${ad.keyPassword}")
    private String keyPassword;

    /**
     * 获取LDAP连接
     *
     * @return LdapContext
     * @throws NamingException
     */
    public LdapContext getContext() throws NamingException {
        LdapContext ctx = null;
        //线上环境
      /* adminName=ConfigUtil.readParam("username");
        adminPassword=ConfigUtil.readParam("password");
        ldapURL=ConfigUtil.readParam("ldapURL");*/

        Hashtable env = new Hashtable();
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.Net.ssl.trustStorePassword", keyPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.PROVIDER_URL, ldapURL);
        //  **解决 乱码 的关键一句
        env.put("java.naming.ldap.attributes.binary","objectGUID");
        try {
            System.out.println("Start InitialLdapContext");
            ctx = new InitialLdapContext(env, null);
            System.out.println("InitialLdapContext1 succeed");
        } catch (NamingException e) {
            System.out.println("Problem initial_Ldap NamingException: " + e);
        }
        return ctx;
    }

    /**
     * 获取AD域用户
     *
     * @param query
     * @return
     */

    public List<User> getADUsers(QueryCondition query) {
        System.out.println("========================================");
        LdapContext ctx;
        List<User> users = new ArrayList<User>();
        try {
            ctx = getContext();
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchClass = "(name=*)";
            if (query != null && query.getSearch() != null && !"".equals(query.getSearch())) {
                searchClass = "(|(name=*" + query.getSearch() + "*)(sAMAccountName=*" + query.getSearch() + "*)(telephoneNumber=*" + query.getSearch() + "*)(mail=*" + query.getSearch() + "*))";
            }
            if(query!=null&&query.getUserAccountControl()!=null&&!"".equals(query.getUserAccountControl())){
                searchClass=searchClass+"(userAccountControl="+query.getUserAccountControl()+")";
            }
            String searchFilter = "(&(objectCategory=person)(objectClass=user)" + searchClass + ")";
            // 按sAMAccountName排序
            SortControl sctl = new SortControl("sAMAccountName",Control.CRITICAL);
            // 分页设置
            VirtualListViewControl vctl;
            if (query != null) {
                vctl = new VirtualListViewControl(query.getStart()!=null? (query.getStart()+ 1):1, 0, 0, query.getLength()!=null?(query.getLength()- 1):2000, Control.CRITICAL);
            }else{
                vctl = new VirtualListViewControl(1, 0, 0, 9, Control.CRITICAL);
            }
            ctx.setRequestControls(new Control[]{vctl, sctl});
            //定制返回属性
            String returnedAtts[] = {"name", "objectGUID","userPrincipalName","userAccountControl", "distinguishedName", "sAMAccountName", "mail", "telephoneNumber", "physicalDeliveryOfficeName", "initials","pwdLastSet"};
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration<SearchResult> answer = null;
            if(query==null|| query.getOu() == null||"".equals(query.getOu())){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                Set<Ourole> ouroles= (Set<Ourole>)session.getAttribute(Constants.OUROLES_SESSION_ID);
                for (Ourole ourole :ouroles){
                    answer=ctx.search(ourole.getOudn(), searchFilter, searchCtls);
                    users=getUserList(answer,users);
                    break;
                }
            }else if(query!=null&&query.getOu()!= null&&!"".equals(query.getOu())){
                if(query.getServiceStype()!=null||searchOu(query.getOu())){
                    answer=ctx.search(query.getOu(), searchFilter, searchCtls);
                    users=getUserList(answer,users);
                }
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 组装用户
     * @param answer
     * @param users
     * @return
     * @throws NamingException
     */
    public  List<User> getUserList(NamingEnumeration<SearchResult> answer, List<User> users) throws NamingException {
        while (answer.hasMoreElements()) {
            SearchResult sr = (SearchResult) answer.next();
            Attributes Attrs = sr.getAttributes();
            if (Attrs != null) {
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
                    }else if ("userAccountControl".equals(Id)) {
                        user.setUserAccountControl(value.replace("66048", "正常").replace("66050", "禁用"));
                    }else if ("mail".equals(Id)) {
                        user.setMail(value);
                    } else if ("telephoneNumber".equals(Id)) {
                        user.setTelephoneNumber(value);
                    } else if ("physicalDeliveryOfficeName".equals(Id)) {
                        user.setPhysicalDeliveryOfficeName(value);
                    } else if ("distinguishedName".equals(Id)) {
                        user.setDistinguishedName(value);
                        user.setDepartment(value.substring(value.indexOf(",") + 1));//部门
                    } else if ("initials".equals(Id)) {
                        user.setInitials(value);
                    } else if("pwdLastSet".equals(Id)){
                        user.setPwdLastSet(new Date(JsonTools.getJavaDateByLdapNTTime(Long.parseLong(value))));
                    }
                }
                user.setObjectGUID(getObjectGUID((byte[])Attrs.get("objectGUID").get()));
                users.add(user);
            }
        }
        return users;
    }

    /**
     * 获取用户账号数量
     *
     * @param query
     * @return
     */
    public int getADUserSize(QueryCondition query) {
        LdapContext ctx;
        int totalResults=0;
        try {
            ctx = getContext();
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchClass = "(name=*)";
            if (query != null && query.getSearch() != null && !"".equals(query.getSearch())) {
                searchClass = "(|(name=*" + query.getSearch() + "*)(sAMAccountName=*" + query.getSearch() + "*)(telephoneNumber=*" + query.getSearch() + "*))";
            }
            if(query!=null&&query.getUserAccountControl()!=null&&!"".equals(query.getUserAccountControl())){
                searchClass=searchClass+"(userAccountControl="+query.getUserAccountControl()+")";
            }
            String searchFilter = "(&(objectCategory=person)(objectClass=user)" + searchClass + ")";
            NamingEnumeration<SearchResult> answer=null;
            //answer= ctx.search((query==null|| query.getOu() == null||"".equals(query.getOu())) ? "OU=大族集团,DC=hansholdings,DC=com" : query.getOu(), searchFilter, searchCtls);
            if((query==null|| query.getOu() == null||"".equals(query.getOu()))){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                Set<Ourole> ouroles= (Set<Ourole>)session.getAttribute(Constants.OUROLES_SESSION_ID);
                for (Ourole ourole :ouroles){
                    answer=ctx.search(ourole.getOudn(), searchFilter, searchCtls);
                    break;
                }
            }else if(query!=null&&query.getOu()!= null&&!"".equals(query.getOu())){
                if(query.getServiceStype()!=null||searchOu(query.getOu())){
                    answer=ctx.search(query.getOu(), searchFilter, searchCtls);
                }
            }
            while (answer!=null&&answer.hasMoreElements()) {
                answer.next();
                totalResults++;
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        }
        return totalResults;
    }

    /**
     * 导入用户信息
     *
     * @return
     */
    public boolean saveUser(User user) {

        LdapContext ctx;
        try {
            ctx = getContext();
            Attributes attrs = new BasicAttributes(true);
            Attribute objclass = new BasicAttribute("objectclass");
            // 添加ObjectClass
            String[] attrObjectClassPerson = {"user", "organizationalPerson", "person", "top"};
            Arrays.sort(attrObjectClassPerson);
            for (String ocp : attrObjectClassPerson) {
                objclass.add(ocp);
            }
            int UF_NORMAL_ACCOUNT = 0x0200;//512
            int DONT_EXPIRE_PASSWORD = 0x10000;//65536
            int ACCOUNTDISABLE = 0x10000;//65536
            attrs.put(objclass);
            attrs.put("sAMAccountName", user.getsAMAccountName());
            attrs.put("userPrincipalName", user.getUserPrincipalName());
            attrs.put("initials", user.getInitials());
            if (user.getUserAccountControl() != null&& !"".equals(user.getUserAccountControl())) {
                attrs.put("userAccountControl",user.getUserAccountControl());
            }
            attrs.put("mail", user.getMail());
            attrs.put("telephoneNumber", user.getTelephoneNumber());
            if (user.getPhysicalDeliveryOfficeName() != null&& !"".equals(user.getPhysicalDeliveryOfficeName())) {
                attrs.put("physicalDeliveryOfficeName", user.getPhysicalDeliveryOfficeName());
            }
            if (user.getUnicodePwd() != null && !"".equals(user.getUnicodePwd())) {
                attrs.put("unicodePwd", ("\"" + user.getUnicodePwd() + "\"").getBytes("UTF-16LE"));
            }
            attrs.put("displayName", user.getName());
            attrs.put("givenName", user.getName());
            ctx.createSubcontext(user.getDistinguishedName(), attrs);
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user) {

        LdapContext ctx;
        try {
            ctx = getContext();
            Attributes attrs = new BasicAttributes(true);
            Attribute objclass = new BasicAttribute("objectclass");
            // 添加ObjectClass
            String[] attrObjectClassPerson = {"user", "organizationalPerson", "person", "top"};
            Arrays.sort(attrObjectClassPerson);
            for (String ocp : attrObjectClassPerson) {
                objclass.add(ocp);
            }
            attrs.put(objclass);
            if (user.getMail() != null&&!"".equals(user.getMail())) {
                attrs.put("mail", user.getMail());
            }
            if (user.getTelephoneNumber() != null&& !"".equals(user.getTelephoneNumber())) {
                attrs.put("telephoneNumber", user.getTelephoneNumber());
            }
            if (user.getPhysicalDeliveryOfficeName() != null&& !"".equals(user.getPhysicalDeliveryOfficeName())) {
                attrs.put("physicalDeliveryOfficeName", user.getPhysicalDeliveryOfficeName());
            }
            if (user.getUnicodePwd() != null && !"".equals(user.getUnicodePwd())) {
                attrs.put("unicodePwd", ("\"" + user.getUnicodePwd() + "\"").getBytes("UTF-16LE"));
            }
            ctx.modifyAttributes(user.getDistinguishedName(), DirContext.REPLACE_ATTRIBUTE, attrs);
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 新增修改用户信息
     *
     * @return
     */
    public boolean saveOrUpdateUser(User user) {

        LdapContext ctx;
        try {
            ctx = getContext();
            Attributes attrs = new BasicAttributes(true);
            Attribute objclass = new BasicAttribute("objectclass");
            // 添加ObjectClass
            String[] attrObjectClassPerson = {"user", "organizationalPerson", "person", "top"};
            Arrays.sort(attrObjectClassPerson);
            for (String ocp : attrObjectClassPerson) {
                objclass.add(ocp);
            }
            int UF_NORMAL_ACCOUNT = 0x0200;//512
            int DONT_EXPIRE_PASSWORD = 0x10000;//65536
            int ACCOUNTDISABLE = 0x10000;//65536
            attrs.put(objclass);
            attrs.put("sAMAccountName", user.getsAMAccountName());
            attrs.put("userPrincipalName", user.getUserPrincipalName());
            attrs.put("initials", user.getInitials());
            if (user.getUserAccountControl() != null) {
                attrs.put("userAccountControl", "正常".equals(user.getUserAccountControl()) ? Integer.toString(0x10200) : Integer.toString(0x10202));
            }
            attrs.put("mail", user.getMail());
            attrs.put("telephoneNumber", user.getTelephoneNumber());
            if (user.getPhysicalDeliveryOfficeName() != null) {
                attrs.put("physicalDeliveryOfficeName", user.getPhysicalDeliveryOfficeName());
            }
            if (user.getUnicodePwd() != null && !"".equals(user.getUnicodePwd())) {
                attrs.put("unicodePwd", ("\"" + user.getUnicodePwd() + "\"").getBytes("UTF-16LE"));
            }
            attrs.put("displayName", user.getName());
            attrs.put("givenName", user.getName());
            String newUid = "CN=" + user.getName() + "," + user.getDepartment();
            if (user.getDistinguishedName() != null && !"".equals(user.getDistinguishedName())) {
                if (!newUid.equals(user.getDistinguishedName())) {
                    ctx.rename(user.getDistinguishedName(), newUid);
                }
                ctx.modifyAttributes(newUid, DirContext.REPLACE_ATTRIBUTE, attrs);

            } else {
                ctx.createSubcontext(newUid, attrs);
            }
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    public boolean chagePasword(User user) {
        LdapContext ctx ;
        try {
            ctx = getContext();
            ModificationItem[] mods = new ModificationItem[1];
            String newQuotedPassword = "\"" + user.getUnicodePwd() + "\"";
            System.out.println("JSON:" + JSON.toJSONString(user));
            if (user.getUnicodePwd() == null || user.getUnicodePwd().equals("")) {
                return false;
            }
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

    /**
     * 刪除用戶
     *
     * @return
     */
    public boolean deleteUser(String uid) {

        LdapContext ctx;
        try {
            ctx = getContext();
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

    /**
     * 条件查找账号是否存在
     *
     * @param us
     * @return
     */
    public List<User> getUserByQuery(User us) {
        LdapContext ctx;
        List<User> users = new ArrayList<User>();
        try {
            ctx = getContext();
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            StringBuffer searchClass = new StringBuffer();
            JSONObject json = (JSONObject) JSON.toJSON(us);
            Set<String> set = json.keySet();
            for (String key : set) {
                if (json.get(key) != null) {
                    searchClass.append("(" + key + "=" + (String) json.get(key) + ")");
                }

            }
            String searchFilter = "(&(objectCategory=person)(objectClass=user)" + searchClass + ")";
            NamingEnumeration<SearchResult> answer = ctx.search("OU=大族集团,DC=hansholdings,DC=com", searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes Attrs = sr.getAttributes();
                if (Attrs != null) {
                    User user = new User();
                    for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
                        Attribute Attr = (Attribute) ne.next();
                        String Id = Attr.getID().toString();
                        String value = "";
                        //读取属性值
                        for (NamingEnumeration e = Attr.getAll(); e.hasMore(); ) {
                            value = e.next().toString();
                            String s=value;
                        }
                        if ("name".equals(Id)) {
                            user.setName(value);
                        } else if ("sAMAccountName".equals(Id)) {
                            user.setsAMAccountName(value);
                        } else if ("userPrincipalName".equals(Id)) {
                            user.setUserPrincipalName(value);
                        } else if ("userAccountControl".equals(Id)) {
                            user.setUserAccountControl(value.replace("66048", "正常").replace("66050", "禁用"));
                        } else if ("mail".equals(Id)) {
                            user.setMail(value);
                        } else if ("telephoneNumber".equals(Id)) {
                            user.setTelephoneNumber(value);
                        } else if ("physicalDeliveryOfficeName".equals(Id)) {
                            user.setPhysicalDeliveryOfficeName(value);
                        } else if ("distinguishedName".equals(Id)) {
                            user.setDistinguishedName(value);
                            user.setDepartment(value.substring(value.indexOf(",") + 1));//部门
                        } else if ("initials".equals(Id)) {
                            user.setInitials(value);
                        }
                    }
                    users.add(user);
                }
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        }
        return users;
    }

    /**
     * 获取节点下OU数量
     *
     * @return
     */
    public int getOuSize(QueryCondition query) {
        LdapContext ctx;
        String objectCategory = "objectCategory=cn=Organizational-Unit,cn=Schema,cn=Configuration," + baseDN;
        String searchClass = "(name=*)";
        if (query != null && query.getSearch() != null && !"".equals(query.getSearch())) {
            searchClass = "(|(name=*" + query.getSearch() + "*)(street=*" + query.getSearch() + "*)(postalCode=*" + query.getSearch() + "*)(managedBy=*" + query.getSearch() + "*))";
        }
        String searchFilter = "(&(" + objectCategory + ")(objectClass=top)" + searchClass +"(&(objectClass=organizationalUnit)))";
        int total = 0;
        // 实例化一个搜索器
        SearchControls searchCtls = new SearchControls();
        // 搜索范围： 1、平级检索；2、树形检索
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //设置为false时返回结果占用内存减少
        searchCtls.setReturningObjFlag(true);
        try {
            ctx = getContext();
            VirtualListViewControl vctl = new VirtualListViewControl( 1, 0, 0, 1000,  Control.CRITICAL);
            // 按CN排序
            SortControl sctl = new SortControl("street",Control.CRITICAL);
            ctx.setRequestControls(new Control[]{vctl, sctl});
            // 执行查询
            NamingEnumeration<SearchResult> sEnum = null;
         // sEnum = ctx.search(("".equals(query.getOu()) || query.getOu() == null) ? query.getRootOU() + "," + baseDN : query.getOu() , searchFilter, searchCtls);
            if((query==null|| query.getOu() == null||"".equals(query.getOu()))){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                Set<Ourole> ouroles= (Set<Ourole>)session.getAttribute(Constants.OUROLES_SESSION_ID);
                if((query.getRootOU()!=null&&!"".equals(query.getRootOU()))){
                    sEnum = ctx.search(query.getRootOU()+","+ baseDN , searchFilter, searchCtls);
                }else {
                    if (ouroles.size() == 0) {
                        sEnum = ctx.search((query.getRootOU() == null || "".equals(query.getRootOU())) ? "OU=大族集团," + baseDN : query.getRootOU(), searchFilter, searchCtls);
                    } else {
                        for (Ourole ourole : ouroles) {
                            sEnum = ctx.search(ourole.getOudn(), searchFilter, searchCtls);
                            break;
                        }
                    }
                }
            }else if(query!=null&&query.getOu()!= null&&!"".equals(query.getOu())){
                if(searchOu(query.getOu())){
                    sEnum=ctx.search(query.getOu(), searchFilter, searchCtls);
                }
            }
            while (sEnum!=null&&sEnum.hasMoreElements()) {
                sEnum.next();
                total++;
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 获取节点下OU
     *
     * @return
     */
    public List<OU> getOuByQuery(QueryCondition query) {
        LdapContext ctx;
        List<OU> ous = new ArrayList<OU>();
        //部门
        String objectCategory = "objectCategory=cn=Organizational-Unit,cn=Schema,cn=Configuration," + baseDN;
        String searchClass = "(name=*)";
        if (query != null && query.getSearch() != null && !"".equals(query.getSearch())) {
            searchClass = "(|(distinguishedName="+ query.getSearch() +")(name=*" + query.getSearch() + "*)(street=*" + query.getSearch() + "*)(postalCode=*" + query.getSearch() + "*)(managedBy=*" + query.getSearch() + "*))";
        }
        String searchFilter = "(&(" + objectCategory + ")(objectClass=top)" + searchClass +"(&(objectClass=organizationalUnit)))";
        int total = 0;
        // 实例化一个搜索器
        SearchControls searchCtls = new SearchControls();
        // 搜索范围： 1、平级检索；2、树形检索
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //设置为false时返回结果占用内存减少
        searchCtls.setReturningObjFlag(true);
        try {
            ctx = getContext();
            VirtualListViewControl vctl;
            if (query != null) {
                vctl = new VirtualListViewControl(query.getStart()!=null? (query.getStart()+ 1):1, 0, 0, query.getLength()!=null?(query.getLength()- 1):1000, Control.CRITICAL);
            }else{
                vctl = new VirtualListViewControl(1, 0, 0, 9, Control.CRITICAL);
            }
            // 按CN排序
            SortControl sctl = new SortControl("street",
                    Control.CRITICAL);
            ctx.setRequestControls(new Control[]{vctl, sctl});
            String returnedAtts[] = {"name", "distinguishedName", "description", "managedBy", "postalCode", "street"}; //定制返回属性
            searchCtls.setReturningAttributes(returnedAtts);
            // 执行查询
            NamingEnumeration<SearchResult> sEnum = null;
            if(query==null|| query.getOu() == null||"".equals(query.getOu())){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                Set<Ourole> ouroles= (Set<Ourole>)session.getAttribute(Constants.OUROLES_SESSION_ID);
                if((query.getRootOU()!=null&&!"".equals(query.getRootOU()))){
                    sEnum = ctx.search( query.getRootOU()+","+ baseDN , searchFilter, searchCtls);
                }else {
                    if (ouroles.size() == 0) {
                        sEnum = ctx.search((query.getRootOU() == null || "".equals(query.getRootOU())) ? "OU=大族集团," + baseDN : query.getRootOU(), searchFilter, searchCtls);
                    } else {
                        for (Ourole ourole : ouroles) {
                            sEnum = ctx.search(ourole.getOudn(), searchFilter, searchCtls);
                            break;
                        }
                    }
                }
            }else if(query!=null&&query.getOu()!= null&&!"".equals(query.getOu())){
                if(searchOu(query.getOu())){
                    sEnum=ctx.search(query.getOu(), searchFilter, searchCtls);
                }
            }
          //  sEnum = ctx.search(("".equals(query.getOu()) || query.getOu() == null) ? query.getRootOU() + "," + baseDN : query.getOu() , searchFilter, searchCtls);
            int size = 0;
            while (sEnum!=null&&sEnum.hasMoreElements()) {
                SearchResult sr = sEnum.nextElement();
                String DN = sr.getName();
                Attributes attrs = sr.getAttributes();
                // 取到所有属性
                NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
                OU ou = new OU();
                while (aEnum.hasMoreElements()) {
                    Attribute attr = aEnum.nextElement();
                    if (attr == null) {
                        continue;
                    }
                    // 打印属性名和属性值，属性值可以为多个
                    String Id = attr.getID().toString();
                    String value = "";
                    for (int i = 0; i < attr.size(); i++) {
                        value = attr.get(i).toString();
                    }
                    if ("name".equals(Id)) {
                        ou.setName(value);
                    } else if ("distinguishedName".equals(Id)) {
                        ou.setUid(value);//唯一标识符
                        String pid = value.substring(value.indexOf(",") + 1);
                        if (!pid.contains("OU")) {
                            ou.setPid("");
                        } else {
                            ou.setPid(pid);
                        }
                    } else if ("managedBy".equals(Id)) {
                        ou.setManagedBy(value);
                        ou.setManagedByName(value.substring(3, value.indexOf(",")));
                    } else if ("postalCode".equals(Id)) {
                        ou.setPostalCode(value);
                    } else if ("street".equals(Id)) {
                        ou.setStreet(value);
                    } else if ("description".equals(Id)) {
                        ou.setDescription(value);
                    }
                }
                ous.add(ou);
                size++;
            }
            ctx.setRequestControls(null);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ous;
    }

    /**
     * 新增修改ou
     */
    public boolean saveOrUpdateOu(OU ou) {
        LdapContext ctx;
        try {
            ctx = getContext();
            Attributes attrs = new BasicAttributes(true);
            Attribute objclass = new BasicAttribute("objectclass");
            // 添加ObjectClass
            String[] attrObjectClassPerson = {"top", "organizationalUnit"};
            Arrays.sort(attrObjectClassPerson);
            for (String ocp : attrObjectClassPerson) {
                objclass.add(ocp);
            }
            attrs.put(objclass);
            if (ou.getPostalCode() != null&&ou.getPostalCode() !="") {
                attrs.put("postalCode", ou.getPostalCode());
            }
            if(ou.getDescription() != null&&ou.getDescription() !=""){
                attrs.put("description", ou.getDescription());
            }
            if(ou.getStreet() != null&&ou.getStreet() !=""){
                attrs.put("street", ou.getStreet());
            }

            if(ou.getManagedBy()!=null&&ou.getManagedBy() !="") {
                attrs.put("managedBy", ou.getManagedBy() );
            }else{
                if(ou.getUid() != null && !"".equals(ou.getUid())){
                    attrs.put("managedBy",  null);
                }
            }


            if (ou.getUid() != null && !"".equals(ou.getUid())) {
                ctx.modifyAttributes(ou.getUid(), DirContext.REPLACE_ATTRIBUTE, attrs);
                ctx.rename(ou.getUid(), "OU=" + ou.getName() + "," + ou.getPid());
            } else {
                ctx.createSubcontext("OU=" + ou.getName() + "," + ou.getPid(), attrs);
            }
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取所有的OU
     *
     * @return
     */
    public List<OU> getAllOU(String rootOU) {
        LdapContext ctx;
        List<OU> ous = new ArrayList<OU>();
        //部门
        String objectCategory = "objectCategory=cn=Organizational-Unit,cn=Schema,cn=Configuration," + baseDN;
        String filter = "(&(" + objectCategory + ")(objectClass=top)(&(objectClass=organizationalUnit)))";
        int total = 0;
        // 实例化一个搜索器
        SearchControls cons = new SearchControls();
        // 搜索范围： 1、平级检索；2、树形检索
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //设置为false时返回结果占用内存减少
        cons.setReturningObjFlag(true);
        String returnedAtts[] = {"name", "distinguishedName", "managedBy", "l"}; //定制返回属性
        cons.setReturningAttributes(returnedAtts);
        // 执行查询
        NamingEnumeration<SearchResult> sEnum = null;
        try {
            ctx = getContext();
            VirtualListViewControl vctl = new VirtualListViewControl(1, 0, 0, 1000,Control.CRITICAL);
            // 按street排序
            SortControl sctl = new SortControl("street",Control.CRITICAL);
            ctx.setRequestControls(new Control[]{vctl, sctl});
            sEnum = ctx.search(rootOU + "," + baseDN, filter, cons);
            while (sEnum.hasMoreElements()) {
                SearchResult sr = sEnum.nextElement();
                Attributes attrs = sr.getAttributes();
                // 取到所有属性
                NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
                OU ou = new OU();
                while (aEnum.hasMoreElements()) {
                    Attribute attr = aEnum.nextElement();
                    if (attr == null) {
                        continue;
                    }
                    // 打印属性名和属性值，属性值可以为多个
                    String Id = attr.getID().toString();
                    String value = "";
                    for (int i = 0; i < attr.size(); i++) {
                        value = attr.get(i).toString();
                    }
                    if ("name".equals(Id)) {
                        ou.setName(value);
                    } else if ("distinguishedName".equals(Id)) {
                        ou.setUid(value);
                        String pid = value.substring(value.indexOf(",") + 1);
                        if (!pid.contains("OU")) {
                            ou.setPid("");
                        } else {
                            ou.setPid(pid);
                        }
                    } else if ("managedBy".equals(Id)) {
                        ou.setManagedBy(value);
                    }
                }
                ous.add(ou);
                System.out.println("---------------------------------------");
            }
            ctx.setRequestControls(null);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ous;
    }

    /**
     * 按公司刷选账号
     * @return
     */
    public List<OU> getOUBydes(String rootOU,String description) {
        LdapContext ctx;
        List<OU> ous = new ArrayList<OU>();
        String objectCategory = "objectCategory=cn=Organizational-Unit,cn=Schema,cn=Configuration," + baseDN;
        String filter = "(&(" + objectCategory + ")(description="+description+")(objectClass=top)(&(objectClass=organizationalUnit)))";
        int total = 0;
        // 实例化一个搜索器
        SearchControls cons = new SearchControls();
        // 搜索范围： 1、平级检索；2、树形检索
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //设置为false时返回结果占用内存减少
        cons.setReturningObjFlag(true);
        String returnedAtts[] = {"name", "distinguishedName","postalCode"}; //定制返回属性
        cons.setReturningAttributes(returnedAtts);
        // 执行查询
        NamingEnumeration<SearchResult> sEnum = null;
        try {
            ctx = getContext();
            VirtualListViewControl vctl = new VirtualListViewControl(1, 0, 0, 1000,Control.CRITICAL);
            // 按CN排序
            SortControl sctl = new SortControl("postalCode",Control.CRITICAL);
            ctx.setRequestControls(new Control[]{vctl, sctl});
            sEnum = ctx.search(rootOU, filter, cons);
                while (sEnum.hasMoreElements()) {
                    SearchResult sr = sEnum.nextElement();
                    Attributes attrs = sr.getAttributes();
                    // 取到所有属性
                    NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
                    OU ou = new OU();
                    while (aEnum.hasMoreElements()) {
                        Attribute attr = aEnum.nextElement();
                        if (attr == null) {
                            continue;
                        }
                        // 打印属性名和属性值，属性值可以为多个
                        String Id = attr.getID().toString();
                        String value = "";
                        for (int i = 0; i < attr.size(); i++) {
                            value = attr.get(i).toString();
                        }
                        if ("name".equals(Id)) {
                            ou.setName(value);
                        } else if ("distinguishedName".equals(Id)) {
                            ou.setDistinguishedName(value);
                        } else if ("postalCode".equals(Id)) {
                            ou.setPostalCode(value);
                        }
                    }
                ous.add(ou);
           }
            ctx.setRequestControls(null);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ous;
    }

    /**
     * 条件查询ou
     *
     * @param query
     * @return
     */
    public boolean getOuByQuery(OU query) {
        LdapContext ctx;
        boolean isUsed = false;
        try {
            ctx = getContext();
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            StringBuffer searchClass = new StringBuffer();
            JSONObject json = (JSONObject) JSON.toJSON(query);
            System.out.println("user:" + json.toString());
            Set<String> set = json.keySet();
            for (String key : set) {
                if (json.get(key) != null) {
                    searchClass.append("(" + key + "=" + (String) json.get(key) + ")");
                }

            }
            String searchFilter = "(&(objectClass=top)(" + searchClass + ")(&(objectClass=organizationalUnit)))";
            NamingEnumeration<SearchResult> answer = ctx.search("OU=大族集团,DC=hansholdings,DC=com", searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                if (sr != null) {
                    isUsed = true;
                    break;
                }
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        }
        return isUsed;
    }

    public boolean searchOu(String oudn) {
        LdapContext ctx;
        boolean isPermit = false;
        try {
            ctx = getContext();
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectClass=top)(distinguishedName="+oudn+")(&(objectClass=organizationalUnit)))";
            NamingEnumeration<SearchResult> answer =null;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            Set<Ourole> ouroles= (Set<Ourole>)session.getAttribute(Constants.OUROLES_SESSION_ID);
            for (Ourole ourole :ouroles) {
                answer=ctx.search(ourole.getOudn(), searchFilter, searchCtls);
                while (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult) answer.next();
                    if (sr != null) {
                        isPermit = true;
                        break;
                    }
                }
                if(isPermit==true){
                    break;
                }
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        }
        return isPermit;
    }

    /**
     * 获取岗位
     *
     * @return
     */
    public List<Position> getPositions() {
        LdapContext ctx;
        List<Position> positions = new ArrayList<Position>();
        //部门
        String objectCategory = "objectCategory=cn=Group,cn=Schema,cn=Configuration," + baseDN;
        String filter = "(&(" + objectCategory + ")(objectClass=top)(&(objectClass=group)))";
        int total = 0;
        // 实例化一个搜索器
        SearchControls cons = new SearchControls();
        // 搜索范围： 1、平级检索；2、树形检索
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //设置为false时返回结果占用内存减少
        cons.setReturningObjFlag(true);
        String returnedAtts[] = {"name", "distinguishedName"}; //定制返回属性
        cons.setReturningAttributes(returnedAtts);
        // 执行查询
        NamingEnumeration<SearchResult> sEnum = null;
        try {
            ctx = getContext();
            sEnum = ctx.search("OU=岗位," + baseDN, filter, cons);
            while (sEnum.hasMoreElements()) {
                SearchResult sr = sEnum.nextElement();
                Attributes attrs = sr.getAttributes();
                // 取到所有属性
                NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
                Position pos = new Position();
                while (aEnum.hasMoreElements()) {
                    Attribute attr = aEnum.nextElement();
                    if (attr == null) {
                        continue;
                    }
                    // 打印属性名和属性值，属性值可以为多个
                    String Id = attr.getID().toString();
                    String value = "";
                    for (int i = 0; i < attr.size(); i++) {
                        value = attr.get(i).toString();
                    }
                    if ("name".equals(Id)) {
                        pos.setName(value);
                    } else if ("distinguishedName".equals(Id)) {
                        pos.setUid(value);
                    }
                }
                positions.add(pos);
                System.out.println("---------------------------------------");
            }
            ctx.setRequestControls(null);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return positions;

    }

    /**
     * 获取岗位成员用户
     *
     * @param uid
     * @return
     */
    public List<User> getMembersByPos(String uid) {
        LdapContext ctx;
        List<User> users = new ArrayList<User>();
        //部门
        String objectCategory = "objectCategory=cn=Group,cn=Schema,cn=Configuration," + baseDN;
        String filter = "(&(" + objectCategory + ")(objectClass=top)(distinguishedName=" + uid + ")(&(objectClass=group)))";
        SearchControls cons = new SearchControls();
        // 搜索范围： 1、平级检索；2、树形检索
        cons.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        //设置为false时返回结果占用内存减少
        cons.setReturningObjFlag(true);
        String returnedAtts[] = {"member"}; //定制返回属性
        cons.setReturningAttributes(returnedAtts);
        // 执行查询
        NamingEnumeration<SearchResult> sEnum;
        try {
            ctx = getContext();
            sEnum = ctx.search("OU=岗位," + baseDN, filter, cons);
            while (sEnum.hasMoreElements()) {
                SearchResult sr = sEnum.nextElement();
                Attributes attrs = sr.getAttributes();
                // 取到所有属性
                NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
                while (aEnum.hasMoreElements()) {
                    Attribute attr = aEnum.nextElement();
                    if (attr == null) {
                        continue;
                    }
                    // 打印属性名和属性值，属性值可以为多个
                    String Id = attr.getID().toString();
                    String value = "";
                    if ("member".equals(Id)) {
                        for (int i = 0; i < attr.size(); i++) {
                            User user = new User();
                            value = attr.get(i).toString();
                            user.setName(value);
                            users.add(user);
                        }
                    }
                }
                System.out.println("---------------------------------------");
            }
            ctx.setRequestControls(null);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 新增修改岗位
     */
    public boolean saveOrUpdatePosition(Position pos) {
        LdapContext ctx;
        try {
            ctx = getContext();
            ModificationItem[] mods = new ModificationItem[1];
            Attributes attrs = new BasicAttributes(true);
            Attribute objclass = new BasicAttribute("objectclass");
            // 添加ObjectClass
            String[] attrObjectClassPerson = {"top", "group"};
            Arrays.sort(attrObjectClassPerson);
            for (String ocp : attrObjectClassPerson) {
                objclass.add(ocp);
            }
            attrs.put(objclass);
            attrs.put("sAMAccountName", pos.getName());
            if (pos.getUid() != null && !"".equals(pos.getUid())) {
                ctx.modifyAttributes(pos.getUid(), DirContext.REPLACE_ATTRIBUTE, attrs);
                ctx.rename(pos.getUid(), "CN=" + pos.getName() + ",OU=岗位,DC=hansholdings,DC=com");
            } else {
                ctx.createSubcontext("CN=" + pos.getName() + ",OU=岗位,DC=hansholdings,DC=com", attrs);
            }
            ctx.close();//关闭连接
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取ldap账号信息
     *
     * @return
     */
    public JSONObject getLdapInfo() {
        JSONObject obj = new JSONObject();
        obj.put("username", ConfigUtil.readParam("username"));
        obj.put("password", ConfigUtil.readParam("password"));
        obj.put("ldapURL", ConfigUtil.readParam("ldapURL"));
        System.out.println("LDAP:"+obj.toString());
        return obj;
    }

    /**
     * 修改ldap连接信息
     *
     * @param ldap
     * @return
     */
    public boolean saveOrUpdateLdap(JSONObject ldap) {

        return ConfigUtil.writeParam(ldap);
    }

    /**
     * 蓝凌入职新增用户
     *
     * @param jsonUser
     * @return
     */
    public JSONObject addUser(JSONObject jsonUser) {
        LdapContext ctx;
        JSONObject json = JsonTools.getSucessResult();
        Attributes attrs = new BasicAttributes(true);
        Attribute objclass = new BasicAttribute("objectclass");
        // 添加ObjectClass
        String[] attrObjectClassPerson = {"user", "organizationalPerson", "person", "top"};
        Arrays.sort(attrObjectClassPerson);
        for (String ocp : attrObjectClassPerson) {
            objclass.add(ocp);
        }
        attrs.put(objclass);
        int UF_NORMAL_ACCOUNT = 0x0200;
        int DONT_EXPIRE_PASSWORD = 0x10000;
        try {
            ctx = getContext();
            String newQuotedPassword = "\"" + jsonUser.get("unicodePwd") + "\"";
            String givenName = jsonUser.get("givenName").toString();
            String sAMAccountName = jsonUser.get("sAMAccountName").toString();
            attrs.put("givenName", givenName);
            attrs.put("displayName", givenName);
            //邮箱
            if(jsonUser.get("mail")!=null&&!"".equals(jsonUser.get("mail"))) {
                attrs.put("mail", jsonUser.get("mail"));
            }
            //工号
            attrs.put("sAMAccountName", sAMAccountName);
            //域账号
            attrs.put("userPrincipalName", sAMAccountName + "@hansholdings.com");

            //性别
            attrs.put("initials", jsonUser.get("initials"));
            //员工状态Description枚举值
            attrs.put("description", jsonUser.get("description"));
            //手机号
            attrs.put("telephoneNumber", jsonUser.get("telephoneNumber"));
            //办公电话
            if(jsonUser.get("physicalDeliveryOfficeName")!=null&&!"".equals(jsonUser.get("physicalDeliveryOfficeName"))){
                attrs.put("physicalDeliveryOfficeName", jsonUser.get("physicalDeliveryOfficeName"));
            }
            //所属部门
            String distinguishedName = "CN=" + givenName + ",OU=" + JsonTools.ouToString(jsonUser.get("distinguishedName").toString()) + "," + baseDN;
            //域账号状态
            attrs.put("userAccountControl", Integer.toString(DONT_EXPIRE_PASSWORD + UF_NORMAL_ACCOUNT));
            // 设置账户密码
            attrs.put("unicodePwd", newQuotedPassword.getBytes("UTF-16LE"));
            User us = new User();
            us.setsAMAccountName(sAMAccountName);
            List<User> s = getUserByQuery(us);
            if (s.size() > 0) {
                us.setDistinguishedName(distinguishedName);
                if (getUserByQuery(us).size()== 0) {
                    ctx.rename(s.get(0).getDistinguishedName(), distinguishedName);
                }
                ctx.modifyAttributes(distinguishedName, DirContext.REPLACE_ATTRIBUTE, attrs);
            } else {
                ctx.createSubcontext(distinguishedName, attrs);
            }
        } catch (NamingException e) {
            json = JsonTools.getFailtResult(Constants.SERVER_INNEL_ERROE, "创建用户失败！");
        } catch (NullPointerException e) {
            json = JsonTools.getFailtResult(Constants.REQUEST_ERROR, "参数列表错误！");
        } catch (UnsupportedEncodingException e) {
            json = JsonTools.getFailtResult(Constants.REQUEST_ERROR, "用户密码参数错误！");
        } catch (Exception e) {
            json = JsonTools.getFailtResult(Constants.SERVER_INNEL_ERROE, e.toString());
        }
        return json;
    }

    /**
     * 蓝凌离职修改用户状态
     *
     * @param jsonUser
     * @return
     */
    public JSONObject updateUserState(JSONObject jsonUser) {
        LdapContext ctx;
        JSONObject json = JsonTools.getSucessResult();
        try {
            ctx = getContext();
            System.out.println("updating1...\n");
            Attributes attrs = new BasicAttributes(true);
            attrs.put("userAccountControl", Integer.toString(0x10202));
            String distinguishedName = "CN=" + jsonUser.get("givenName").toString() + ",OU=" + JsonTools.ouToString(jsonUser.get("distinguishedName").toString()) + "," + baseDN;
            /* 修改属性 */
            ctx.modifyAttributes(distinguishedName, DirContext.REPLACE_ATTRIBUTE, attrs);
            ctx.close();//关闭连接
        } catch (NullPointerException e) {
            json = JsonTools.getFailtResult(Constants.REQUEST_ERROR, "参数列表错误！");
        } catch (NamingException e) {
            json = JsonTools.getFailtResult(Constants.DATA_ERROR, "未找到相关用户账号！！");
        } catch (Exception e) {
            json = JsonTools.getFailtResult(Constants.SERVER_INNEL_ERROE, e.toString());
        }
        return json;
    }

    /**
     * 蓝凌调职修改用户OU
     *
     * @param jsonUser
     * @return
     */
    public JSONObject updateUserOU(JSONObject jsonUser) {
        LdapContext ctx;
        JSONObject json = JsonTools.getSucessResult();
        try {
            ctx = getContext();
            System.out.println("updating2...\n");
            String oldDN = "CN=" + jsonUser.get("givenName").toString() + ",OU=" + JsonTools.ouToString(jsonUser.get("oldDN").toString()) + "," + baseDN;
            String newDN = "CN=" + jsonUser.get("givenName").toString() + ",OU=" + JsonTools.ouToString(jsonUser.get("newDN").toString()) + "," + baseDN;
             ctx.rename(oldDN, newDN);
            ctx.close();//关闭连接
        } catch (NullPointerException e) {
            json = JsonTools.getFailtResult(Constants.REQUEST_ERROR, "参数格式错误！");
        } catch (NamingException e) {
            json = JsonTools.getFailtResult(Constants.DATA_ERROR, "未找到相关用户账号！");
        } catch (Exception e) {
            json = JsonTools.getFailtResult(Constants.SERVER_INNEL_ERROE, e.toString());
        }
        return json;
    }

    /**
     * 蓝凌修改个人信息接口
     * @param jsonUser
     * @return
     */
    public JSONObject updateUserInfo(JSONObject jsonUser){
        LdapContext ctx;
        JSONObject json = JsonTools.getSucessResult();
        try {
            ctx = getContext();
            System.out.println("updating3...\n");
            Attributes attrs = new BasicAttributes(true);
            attrs.put("telephoneNumber", jsonUser.get("telephoneNumber"));
            if(jsonUser.get("physicalDeliveryOfficeName")!=null&&!"".equals(jsonUser.get("physicalDeliveryOfficeName"))){
                attrs.put("physicalDeliveryOfficeName", jsonUser.get("physicalDeliveryOfficeName"));
            }
            attrs.put("initials", jsonUser.get("initials"));
            String distinguishedName = "CN=" + jsonUser.get("givenName").toString() + ",OU=" + JsonTools.ouToString(jsonUser.get("distinguishedName").toString()) + "," + baseDN;
            /* 修改属性 */
            ctx.modifyAttributes(distinguishedName, DirContext.REPLACE_ATTRIBUTE, attrs);
            ctx.close();//关闭连接
        } catch (NullPointerException e) {
            json = JsonTools.getFailtResult(Constants.REQUEST_ERROR, "参数列表错误！");
        } catch (NamingException e) {
            json = JsonTools.getFailtResult(Constants.DATA_ERROR, "未找到相关用户账号！！");
        } catch (Exception e) {
            json = JsonTools.getFailtResult(Constants.SERVER_INNEL_ERROE, e.toString());
        }
        return json;
    }

    /**
     * 登录验证
     * @param sAMAccountName
     * @param password
     * @return
     */
    public boolean loginValidate(String sAMAccountName,String password){
        Hashtable env = new Hashtable();
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.Net.ssl.trustStorePassword", keyPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, sAMAccountName+"@hansholdings.com");
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.PROVIDER_URL, "ldaps://172.18.254.210:636");
        LdapContext ctx=null;
        Boolean loginstate=false;
        try {
            ctx = new InitialLdapContext(env, null);
            loginstate=true;
        } catch (NamingException e) {
            loginstate=false;
        }
        return loginstate;
    }


    public String getObjectGUID(byte[] GUID ){
        String strGUID = "";
        String byteGUID = "";
        for (int c=0;c<GUID.length;c++) {
            byteGUID = byteGUID + "\\" + AddLeadingZero((int)GUID[c] & 0xFF);
        }
        strGUID = "{";
        strGUID = strGUID + AddLeadingZero((int)GUID[3] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[2] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[1] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[0] & 0xFF);
        strGUID = strGUID + "-";
        strGUID = strGUID + AddLeadingZero((int)GUID[5] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[4] & 0xFF);
        strGUID = strGUID + "-";
        strGUID = strGUID + AddLeadingZero((int)GUID[7] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[6] & 0xFF);
        strGUID = strGUID + "-";
        strGUID = strGUID + AddLeadingZero((int)GUID[8] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[9] & 0xFF);
        strGUID = strGUID + "-";
        strGUID = strGUID + AddLeadingZero((int)GUID[10] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[11] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[12] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[13] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[14] & 0xFF);
        strGUID = strGUID + AddLeadingZero((int)GUID[15] & 0xFF);
        strGUID = strGUID + "}";
        return strGUID.toUpperCase();
    }

    public  String AddLeadingZero(int k) {
        return (k <= 0xF) ? "0" + Integer.toHexString(k) : Integer
                .toHexString(k);
    }
}
