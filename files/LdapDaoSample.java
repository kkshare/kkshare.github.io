//http://www.novell.com/documentation/developer/samplecode/jldap_sample/
package cn.eben4a.test;

import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;

public class LdapDaoSample {
    private static final String LDAP_URL = "ldap://42.121.96.124:389/";
    private static final String LDAP_DOMAIN = "dc=eben,dc=cn";
    private static final String LDAP_PRINCIPAL = "cn=manager,"+LDAP_DOMAIN;
    private static final String LDAP_PASSWORD = "eben132";
    private static final String LDAP_USER_BASE = "o=user,"+LDAP_DOMAIN;
    private static final String LDAP_ACCOUNT_BASE = "o=account,"+LDAP_DOMAIN;

    private static final boolean anoymous = false;

    private static LdapContext connetLDAP() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if(!anoymous){
            env.put(Context.SECURITY_PRINCIPAL, LDAP_PRINCIPAL);
            env.put(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);
        }
        env.put(Context.REFERRAL, "follow");
        //表示属性objectSid objectGUID 对 Attribute.get() 返回的是byte[](默认是String)
        //env.put("java.naming.ldap.attributes.binary", "objectSid objectGUID");
        return new InitialLdapContext(env, null);
    }
    public void testAdd(LdapContext ldapContext) throws Exception {
        Attributes attrs = new BasicAttributes(true);
        Attribute objclass = new BasicAttribute("objectclass");
        // 添加ObjectClass
        String[] attrObjectClassPerson = { "user", "organizationalPerson", "person"};
        Arrays.sort(attrObjectClassPerson);
        for (String ocp : attrObjectClassPerson) {
            objclass.add(ocp);
        }
        attrs.put(objclass);
        String uid = "zhangsan";
        String userDN = "uid=" + uid + "," + LDAP_USER_BASE;
        attrs.put("uid", uid);
        attrs.put("mail", "abc@163.com");
        attrs.put("description", "hh");
        attrs.put("userPassword", "Passw0rd".getBytes("UTF-8"));
        //增加多值方法
        Attribute loginNames = new BasicAttribute("cn");
        loginNames.add("abc");
        loginNames.add(uid);
        attrs.put(loginNames);
        ldapContext.createSubcontext(userDN, attrs);
    }
    public void testRemoveEntry(LdapContext ldapContext) throws Exception {
        String userDN = "uid=zhangsan," + LDAP_USER_BASE;
        ldapContext.destroySubcontext(userDN);

    }
    public boolean testModify(LdapContext ldapContext) throws Exception {
        boolean result = true;
        String userDN = "uid=zhangsan," + LDAP_USER_BASE;
        Attributes attrs = new BasicAttributes(true);
        attrs.put("mail", "zhangsan@163.com");
        ldapContext.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attrs);
        return result;

    }
    public boolean testRemoveAttr(LdapContext ldapContext) throws Exception {
        boolean result = true;
        String userDN = "uid=zhangsan," + LDAP_USER_BASE;
        Attributes attrs = new BasicAttributes(true);
        Attribute attr = new BasicAttribute("perm");
        attrs.put(attr);
        ldapContext.modifyAttributes(userDN, DirContext.REMOVE_ATTRIBUTE, attrs);
        return result;

    }
    public void testSearch(LdapContext ldapContext, String[] returnAttrs) throws Exception {
        String uid = "zhangsan";
        //String filter = "(&(objectClass=ebenUser)(uid=1))";
        String filter = "objectclass=*";//单个条件可以不加括号
        String seachBase = "o=org,dc=eben,dc=cn";
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        if(returnAttrs!=null)
            searchControls.setReturningAttributes(returnAttrs);
        NamingEnumeration<SearchResult> answer = ldapContext.search(seachBase, filter, searchControls);
        while (answer.hasMore()) {//answer.hasMoreElements()
            SearchResult entry = answer.next();
            System.out.println("dn:"+entry.getNameInNamespace());
            NamingEnumeration<? extends Attribute> attrs = entry.getAttributes().getAll();
            while (attrs.hasMore()) {
                Attribute attr = attrs.next();
                //System.out.println(attr.getID() + "=" + attr.get());//只有单个属性
                for(int i=0; i<attr.size(); i++){//有些属性可能有多个值
                    System.out.println("  "+attr.getID() + "=" + attr.get(i));
                }
            }
        }
        System.out.println("============");
    }
    public void testRenameEntry(LdapContext ldapContext) throws Exception {
        //String oldDN = "gid=901,gid=603,o=org,dc=eben,dc=cn";
        String oldDN = "gid=1300,gid=5,o=org,dc=eben,dc=cn";
        String newDN = "gid=1300,o=org,dc=eben,dc=cn";
        ldapContext.rename(oldDN, newDN);
    }
   
    public static void testReconnect(LdapContext ldapContext) throws Exception{
        ldapContext.addToEnvironment(Context.SECURITY_PRINCIPAL, LDAP_PRINCIPAL);
        ldapContext.addToEnvironment(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);
        ldapContext.reconnect(null);
    }
    public static void testGetAttributes(LdapContext ldapContext) throws Exception{
        Attributes attributes = ldapContext.getAttributes("uid=10,o=user,dc=eben,dc=cn");
        NamingEnumeration<? extends Attribute> attrs = attributes.getAll();
        while(attrs.hasMore()){
            Attribute a = attrs.next();
            for(int i=0; i<a.size(); i++){//有些属性可能有多个值
                System.out.println("  "+a.getID() + "=" + a.get(i));
            }
        }
    }

    public static void main(String[] args) {
        LdapDaoSample dao = new LdapDaoSample();
        LdapContext ldapContext = null;
        try {
            ldapContext = connetLDAP();
            //dao.testRemoveEntry(ldapContext);
            //dao.testAdd(ldapContext);
            //dao.testModify(ldapContext);
            //dao.testRenameEntry(ldapContext);
            //dao.testRemoveAttr(ldapContext);
            //dao.testSearch(ldapContext, new String[]{ATTR.GID,ATTR.NAME});
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (ldapContext != null) {
                try {
                    ldapContext.close();
                } catch (NamingException e) {
                }
            }
        }
    }
}
