package com.ceva.util;

import java.util.Hashtable;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

public class QueryactiveDirectory_NBK_Chn_03JUN2015 {

	private Hashtable<Object, Object> env = null;
	private Hashtable<String, String> userData = null;
	private Vector<Hashtable<String, String>> userInfo = null;
	private String userName;
	private String password;
	private String url;

	public QueryactiveDirectory_NBK_Chn_03JUN2015(String userName, String password, String url) {
		this.userName = userName;
		this.password = password;
		this.url = url;

		initilization();
	}

	public void initilization() {
		env = new Hashtable<Object, Object>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		// set security credentials
		// env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
		// env.put(Context.SECURITY_AUTHENTICATION, "SHA-2");
		// env.put("javax.security.sasl.policy.noactive", "true");
		// env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5 GSSAPI");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, getUserName());
		env.put(Context.SECURITY_CREDENTIALS, getPassword());
		// env.put("com.sun.jndi.ldap.trace.ber", System.out);

		// specify use of ssl
		env.put(Context.SECURITY_PROTOCOL, "simple");

		// connect to my domain controller
		env.put(Context.PROVIDER_URL, getUrl());
	}

	public static void testUsers() {

		String userName = "county@nationalbank.local";
		String password = "Cnty123**";
		String url = "ldap://172.16.2.99:389/DC=nationalbank,DC=local";

		QueryactiveDirectory_NBK_Chn_03JUN2015 qrd = new QueryactiveDirectory_NBK_Chn_03JUN2015(userName, password,
				url);

		qrd.searchUser();

	}

	public static void main(String args[]) {
		testUsers();
	}

	public boolean searchUser() {
		DirContext ctx = null;
		boolean flag = false;
		try {

			// Create the initial directory context
			ctx = new InitialLdapContext(this.env, null);
			System.out.println("Context Initilized.....");
			String searchFilter = "(&(objectCategory=person)(objectClass=user)(sAMAccountName=county))";
			// String searchFilter =
			// "(&(objectCategory=person)(sAMAccountName=*))";
			SearchResult search = findAccountByAccountName(ctx, "",
					getUserName(), searchFilter);
			if (search != null) {
				flag = true;
			}

			ctx.close();

		} catch (NamingException e) {
			System.err.println("Problem searching directory: " + e);
			e.printStackTrace();
			return false;

		} finally {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public SearchResult findAccountByAccountName(DirContext ctx,
			String ldapSearchBase, String accountName, String searchFilter) {

		SearchControls searchControls = null;
		NamingEnumeration<SearchResult> results = null;
		SearchResult searchResult = null;
		try {
			searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			results = ctx.search(ldapSearchBase, searchFilter, searchControls);

			if (results.hasMore()) {
				Attributes attrs = results.next().getAttributes();

				System.out.println("sAMAccountName :: "
						+ attrs.get("sAMAccountName"));
				System.out.println("givenname :: " + attrs.get("givenname"));
				System.out.println("sn :: " + attrs.get("sn"));
				System.out.println("mail :: " + attrs.get("mail"));
				System.out.println("homePhone :: " + attrs.get("mobile"));

				System.out.println("IDS :: " + attrs.getIDs());
				/*
				 * NamingEnumeration<String> enumeration = attrs.getIDs(); while
				 * (enumeration.hasMoreElements()) { //
				 * System.out.println("elements..:"+enumeration.nextElement());
				 * // System.out.println(enumeration.nextElement()+"...:: " + //
				 * attrs.get(enumeration.nextElement())); }
				 * printAttrs(((SearchResult) results.nextElement())
				 * .getAttributes());
				 */

			} else {
				throw new Exception("Invalid User");
			}

			if (results.hasMoreElements()) {
				searchResult = (SearchResult) results.nextElement();

				System.out
						.println("======================== START ===================================");
				// make sure there is not another item available, there should
				// be
				// only 1 match
				if (results.hasMoreElements()) {
					System.err
							.println("Matched multiple users for the accountName: "
									+ accountName);
					// return null;
				}

				/*
				 * String givenName = (searchResult.getAttributes()
				 * .get("givenname")).toString(); String fullName =
				 * (searchResult.getAttributes().get("cn")) .toString(); String
				 * userName = (searchResult.getAttributes()
				 * .get("sAMAccountName")).toString(); String email =
				 * (searchResult.getAttributes().get("mail")) .toString();
				 */

				System.out.println(userName);

				System.out.println(searchResult.getName());
				System.out.println(searchResult.getNameInNamespace());

				System.out
						.println("=========================== END ====================================");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return searchResult;
	}

	private void printAttrs(Attributes attrs) {
		if (attrs == null) {
			System.out.println("No attributes");
		} else {
			/* Print each attribute */

			try {
				for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();
					System.out.print("attribute: " + attr.getID());

					/* print each value */
					for (NamingEnumeration e = attr.getAll(); e.hasMore(); System.out
							.print("\t value: " + e.next() + "\n"))
						;
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}

	}

	public void getUsers() {

		DirContext ctx = null;

		SearchControls searchCtls = null;

		String searchBase = "";

		String searchFilter = "";

		// Specify the attributes to return
		String returnedAtts[] = { "distinguishedName", "sn", "givenname",
				"mail", "telephonenumber", "cn", "sAMAccountName" };

		// initialize counter to total the results
		int totalResults = 0;

		NamingEnumeration<SearchResult> answer = null;
		SearchResult sr = null;
		Attributes attrs = null;

		try {

			// Create the initial directory context
			ctx = new InitialLdapContext(this.env, null);

			System.out.println("Context Initilized.....");

			// Create the search controls
			searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Specify the Base for the search
			searchBase = "";

			// specify the LDAP search filter
			searchFilter = "(&(objectCategory=person)(objectClass=user)(sAMAccountName=ceva))";

			// Search for objects using the filter
			answer = ctx.search(searchBase, searchFilter, searchCtls);

			userData = new Hashtable<String, String>();
			userInfo = new Vector<Hashtable<String, String>>();
			// Loop through the search results
			while (answer.hasMoreElements()) {
				sr = answer.next();

				totalResults++;

				userData.put("CN", sr.getName());

				// Print out some of the attributes, catch the exception if the
				// attributes have no values
				attrs = sr.getAttributes();
				if (attrs != null) {
					try {

						System.out.println("   surname: "
								+ attrs.get("sn").get());
						System.out.println("   sAMAccountName: "
								+ attrs.get("sAMAccountName").get());

						System.out.println("   firstname: "
								+ attrs.get("givenName").get());
						System.out.println("   mail: "
								+ attrs.get("mail").get());
						System.out.println("   Customer Name: "
								+ attrs.get("cn").get());
						System.out.println("   lastLogon : "
								+ attrs.get("lastLogon").get());

					} catch (NullPointerException e) {
						System.out.println("Errors listing attributes: " + e);
					}
				}

			}

			System.out.println("Total results: " + totalResults);
			ctx.close();

		} catch (NamingException e) {
			System.err.println("Problem searching directory: " + e);
			e.printStackTrace();
		} finally {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

	private String nullCheck(String data) {
		return (data == null ? " " : data);
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Vector<Hashtable<String, String>> getUserInfo() {
		return userInfo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void clearEnvHashData() {
		if (env != null)
			this.env.clear();
	}
}