package com.ceva.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.junit.Test;

public class TestAD {
	static DirContext ldapContext;

	public TestAD() {
	}

	@Test
	public void main() throws NamingException {
		try {
			System.out.println("Tsting Active Directory");

			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			/*ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL,"ldap://172.16.2.99:389/DC=nationalbank,DC=local");
			ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL,"county");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, " Cnty123**");*/

			
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL,"ldap://172.16.3.210:389/DC=nbk,DC=test");
			ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL,"mobi@nbk.test");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, "pass123*");

			ldapContext = new InitialDirContext(ldapEnv);

			System.out.println("After Initilizing AD.");

			// Create the search controls
			SearchControls searchCtls = new SearchControls();

			// Specify the attributes to return
			String returnedAtts[] = { "sn", "givenName", "samAccountName" };
			searchCtls.setReturningAttributes(returnedAtts);

			// Specify the search scope
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// specify the LDAP search filter
			// String searchFilter = "(&(objectClass=user))";
			String searchFilter = "(&(objectClass=user))";

			// Specify the Base for the search
//			String searchBase = " ,DC=nationalbank,DC=local";
			String searchBase = " ,DC=nbk,DC=test";
			// initialize counter to total the results
			int totalResults = 0;

			// Search for objects using the filter
			NamingEnumeration<SearchResult> answer = ldapContext.search(
					searchBase, searchFilter, searchCtls);

			// Loop through the search results
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();

				totalResults++;

				System.out.println(">>>" + sr.getName());
				Attributes attrs = sr.getAttributes();
				System.out.println(">>>>>>" + attrs.get("samAccountName"));
			}

			System.out.println("Total results: " + totalResults);
			ldapContext.close();
		} catch (Exception e) {
			System.out.println(" Search error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
}