package com.ceva.util;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class QueryactiveDirectory {

	private Logger logger = Logger.getLogger(QueryactiveDirectory.class);

	private Hashtable<Object, Object> env = null;
	private JSONObject userData = null;
	private JSONArray userInfo = null;
	private String userName;
	private String password;
	private String url;
	private DirContext dirContext = null;

	public QueryactiveDirectory(String userName, String password, String url) {
		this.userName = userName;
		this.password = password;
		this.url = url;

		env = new Hashtable<Object, Object>();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");

		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, this.userName);
		env.put(Context.SECURITY_CREDENTIALS, this.password);
		// env.put("java.naming.ldap.attributes.binary",
		// "objectSid objectGUID");
		// env.put("com.sun.jndi.ldap.trace.ber", System.out);

		// specify use of ssl
		env.put(Context.SECURITY_PROTOCOL, "simple");

		// connect to my domain controller
		env.put(Context.PROVIDER_URL, this.url);

		// Create the initial directory context
		try {
			dirContext = new InitialLdapContext(env, null);
		} catch (NamingException e) {
			logger.debug("Problem while Initilizing Directory : "
					+ e.getMessage());
		}
	}

	public void closeDirectoryContext() {
		try {
			if (getDirContext() != null) {
				dirContext.close();
			}
		} catch (NamingException e) {
			logger.debug("Problem while Closing Directory : " + e.getMessage());
		}

	}
	
	public static void testUsers() {

		String userName = "county@nationalbank.local";
		userName="cevabo@nationalbank.local";
		String password = "Cnty123**";
		password = "nbk1234*";
		String url = "ldap://172.16.2.99:389/DC=nationalbank,DC=local";

		QueryactiveDirectory qrd = new QueryactiveDirectory(userName, password,
				url);

		System.out.println(qrd == null);
		
		qrd.searchUser();

	}

	public static void main(String args[]) {
		testUsers();
	}

	public boolean searchUser() {
		DirContext ctx = null;
		boolean flag = false;
		try {
			ctx = getDirContext();
			if (ctx != null) {
				flag = true;
				logger.debug("Context Initilized..... User Authorized... Login Check."); 
				System.out.println("Context Initilized..... User Authorized... Login Check.");
			}
		} catch (Exception e) {
			logger.debug("Problem searching directory Exception is : " + e);
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
		return flag;
	}

	public boolean searchUser(String userToCheck) {
		DirContext ctx = null;
		boolean flag = false;
		String searchFilter = "";
		SearchResult search = null;
		try {
			ctx = getDirContext();
			logger.debug("Context Initilized.....");
			searchFilter = "(&(objectcategory=person)(objectClass=user)(sAMAccountName="
					+ userToCheck + "))";
			search = findAccountByAccountName1(ctx, "", getUserName(),
					searchFilter);
			if (search != null) {
				flag = true;
			}
			ctx.close();

		} catch (NamingException e) {
			logger.debug("Problem searching directory NamingException having Arg : "
					+ e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Problem searching directory Exception is having Arg : "
					+ e);
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
		return flag;
	}

	public boolean getUsersID() {
		DirContext ctx = null;
		boolean flag = false;
		String searchFilter = "";
		String ldapSearchBase = "";
		SearchResult search = null;
		try {
			ctx = getDirContext();
			logger.debug("Context Initilized.....");
			searchFilter = "(&(&(objectcategory=person)(objectClass=user))(!(objectCategory=SQLDebugger)))";
			ldapSearchBase = "";
			search = findAccountByAccountName(ctx, ldapSearchBase,getUserName(), searchFilter);
			if (search != null) {
				flag = true;
			}
			ctx.close();

		} catch (NamingException e) {
			logger.debug("Problem searching directory NamingException having Arg : "
					+ e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Problem searching directory Exception is having Arg : "
					+ e);
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
		return flag;
	}

	public SearchResult findAccountByAccountName1(DirContext ctx,
			String ldapSearchBase, String accountName, String searchFilter) {

		SearchControls searchControls = null;
		NamingEnumeration<SearchResult> results = null;
		SearchResult searchResult = null;
		try {
			searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			results = ctx.search(ldapSearchBase, searchFilter, searchControls);
			// Loop through the objects returned in the search
			while (results.hasMoreElements()) {
				// Each item is a SearchResult object
				SearchResult match = (SearchResult) results.nextElement();

				// Print out the node name
				logger.debug("Found :: " + match.getName() + ":");

				// Get the node's attributes
				Attributes attrs = match.getAttributes();

				NamingEnumeration<? extends Attribute> e = attrs.getAll();

				// Loop through the attributes
				while (e.hasMoreElements()) {
					// Get the next attribute
					Attribute attr = e.nextElement();

					// Print out the attribute's value(s)
					System.out.print(attr.getID() + " = ");
					for (int i = 0; i < attr.size(); i++) {
						if (i > 0)
							System.out.print(",,");
						System.out.print(attr.get(i));
					}
					logger.debug("\n");
				}
				logger.debug("---------------------------------------");
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		return searchResult;
	}

	public SearchResult findAccountByAccountName(DirContext ctx,
			String ldapSearchBase, String accountName, String searchFilter) {
		String returnedAtts[] = { "sn", "givenname", "mail", "cn",
				"sAMAccountName", "mobile", "title", "department" };

		String sn = "";
		String givenName = "";
		String sAMAccountName = "";

		SearchResult sr = null;
		Attributes attrs = null;

		SearchControls searchControls = null;
		NamingEnumeration<SearchResult> results = null;
		SearchResult searchResult = null;
		try {
			searchControls = new SearchControls();
			searchControls.setReturningAttributes(returnedAtts);
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			results = ctx.search(ldapSearchBase, searchFilter, searchControls);

			userInfo = new JSONArray();

			// Loop through the search results
			while (results.hasMoreElements()) {
				sr = results.next();

				// Print out some of the attributes, catch the exception if the
				// attributes have no values
				attrs = sr.getAttributes();
				if (attrs != null) {
					try {

						try {
							sn = (String) attrs.get("sn").get();
						} catch (Exception e) {
						}

						try {
							sAMAccountName = (String) attrs.get(
									"sAMAccountName").get();
						} catch (Exception e) {
						}

						try {
							givenName = (String) attrs.get("givenName").get();
						} catch (Exception e) {
						}

						if (!givenName.equalsIgnoreCase("")
								&& !sn.equalsIgnoreCase("")
								&& !sAMAccountName.equalsIgnoreCase("")
								&& !sAMAccountName.equalsIgnoreCase("DUP")) {

							if (!checkSpecChar(sAMAccountName.toUpperCase())) {
								userInfo.add(sAMAccountName.toUpperCase());
							} else {
								logger.debug(sAMAccountName.toUpperCase());
							}
						}

					} catch (NullPointerException e) {
					} catch (Exception e) {
					}
				}
			}
			logger.debug("Fetching User's Done.");

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		return searchResult;
	}

	private boolean checkSpecChar(String inputString) {
		String patternToMatch = "[\\\\!\"#$%&()*+,/:;<=>?@\\[\\]^_{|}~]+";
		Pattern p = Pattern.compile(patternToMatch);
		Matcher m = p.matcher(inputString);
		boolean characterFound = m.find();

		return characterFound;
	}

	public void getUsersDetails() {

		DirContext ctx = null;

		SearchControls searchCtls = null;

		String searchBase = "";

		String searchFilter = "";

		// Specify the attributes to return
		/*
		 * givenName -> First Name sn -> Sur Name cn -> Customer Name
		 * sAMAccountName -> User Id
		 * 
		 * mail -> Email Address
		 */
		String returnedAtts[] = { "sn", "givenname", "mail", "cn",
				"sAMAccountName", "mobile", "title", "department" };

		String sn = "";
		String givenName = "";
		String sAMAccountName = "";
		String email = "";
		String title = "";
		String department = "";

		NamingEnumeration<SearchResult> answer = null;
		SearchResult sr = null;
		Attributes attrs = null;

		try {

			ctx = getDirContext();

			logger.debug("Context Initilized.....");

			// Create the search controls
			searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Specify the Base for the search
			searchBase = "";

			// specify the LDAP search filter
			searchFilter = "(&(objectcategory=person)(objectClass=user))";

			// Search for objects using the filter
			answer = ctx.search(searchBase, searchFilter, searchCtls);

			userData = new JSONObject();
			userInfo = new JSONArray();

			// Loop through the search results
			while (answer.hasMoreElements()) {
				sr = answer.next();

				// userData.put("CN", sr.getName());

				// Print out some of the attributes, catch the exception if the
				// attributes have no values
				attrs = sr.getAttributes();
				if (attrs != null) {
					try {

						try {
							sn = (String) attrs.get("sn").get();
						} catch (Exception e) {
						}

						try {
							sAMAccountName = (String) attrs.get(
									"sAMAccountName").get();
						} catch (Exception e) {
						}

						try {
							givenName = (String) attrs.get("givenName").get();
						} catch (Exception e) {
						}

						if (!givenName.equalsIgnoreCase("")
								&& !sn.equalsIgnoreCase("")
								&& !sAMAccountName.equalsIgnoreCase("")) {
							try {
								email = (String) attrs.get("mail").get();
							} catch (Exception e) {
							}

							try {
								title = (String) attrs.get("title").get();
							} catch (Exception e) {
							}

							try {
								department = (String) attrs.get("department")
										.get();
							} catch (Exception e) {
							}

							userData.put("sn", sn);
							userData.put("sAMAccountName", sAMAccountName);
							userData.put("givenName", givenName);
							userData.put("mail", email);
							userData.put("title", title);
							userData.put("department", department);

							userInfo.add(userData);
							userData.clear();
						}

					} catch (NullPointerException e) {
					} catch (Exception e) {
					}
				}
			}
			logger.debug(userInfo);
		} catch (NamingException e) {
			logger.debug("Problem searching directory: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception Problem searching directory: " + e);
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

	public void getUserDetails(String samAccount) {

		DirContext ctx = null;

		SearchControls searchCtls = null;

		String searchBase = "";

		String searchFilter = "";

		// Specify the attributes to return
		/*
		 * givenName -> First Name sn -> Sur Name cn -> Customer Name
		 * sAMAccountName -> User Id
		 * 
		 * mail -> Email Address
		 */
		String returnedAtts[] = { "sn", "givenname", "mail", "cn",
				"sAMAccountName", "mobile", "title", "department" };

		String sn = "";
		String givenName = "";
		String sAMAccountName = "";
		String email = "";
		String title = "";
		String department = "";

		NamingEnumeration<SearchResult> answer = null;
		SearchResult sr = null;
		Attributes attrs = null;

		try {

			ctx = getDirContext();

			logger.debug("Context Initilized.....");

			// Create the search controls
			searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Specify the Base for the search
			searchBase = "";

			// specify the LDAP search filter
			searchFilter = "(&(objectcategory=person)(objectClass=user)(sAMAccountName="
					+ samAccount + "))";

			// Search for objects using the filter
			answer = ctx.search(searchBase, searchFilter, searchCtls);

			userData = new JSONObject();
			userInfo = new JSONArray();

			// Loop through the search results
			while (answer.hasMoreElements()) {
				sr = answer.next();

				// userData.put("CN", sr.getName());

				// Print out some of the attributes, catch the exception if the
				// attributes have no values
				attrs = sr.getAttributes();
				if (attrs != null) {
					try {

						try {
							sn = (String) attrs.get("sn").get();
						} catch (Exception e) {
						}

						try {
							sAMAccountName = (String) attrs.get(
									"sAMAccountName").get();
						} catch (Exception e) {
						}

						try {
							givenName = (String) attrs.get("givenName").get();
						} catch (Exception e) {
						}

						if (!givenName.equalsIgnoreCase("")
								&& !sn.equalsIgnoreCase("")
								&& !sAMAccountName.equalsIgnoreCase("")) {
							try {
								email = (String) attrs.get("mail").get();
							} catch (Exception e) {
							}

							try {
								title = (String) attrs.get("title").get();
							} catch (Exception e) {
							}

							try {
								department = (String) attrs.get("department")
										.get();
							} catch (Exception e) {
							}

							userData.put("sn", sn);
							userData.put("sAMAccountName", sAMAccountName);
							userData.put("givenName", givenName);
							userData.put("mail", email);
							userData.put("title", title);
							userData.put("department", department);

							userInfo.add(userData);
							userData.clear();
						}

					} catch (NullPointerException e) {
					} catch (Exception e) {
					}
				}
			}
			logger.debug(userInfo);
		} catch (NamingException e) {
			logger.debug("Problem searching directory: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception Problem searching directory: " + e);
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

	public void getObject(String samAccount) {

		DirContext ctx = null;

		SearchControls searchCtls = null;

		String searchBase = "";

		String searchFilter = "";

		// Specify the attributes to return
		/*
		 * givenName -> First Name sn -> Sur Name cn -> Customer Name
		 * sAMAccountName -> User Id
		 * 
		 * mail -> Email Address
		 */
		String returnedAtts[] = { "sn", "givenname", "mail", "cn",
				"sAMAccountName", "mobile", "title", "department" };

		String sn = "";
		String givenName = "";
		String sAMAccountName = "";
		String email = "";
		String title = "";
		String department = "";

		NamingEnumeration<SearchResult> answer = null;
		SearchResult sr = null;
		Attributes attrs = null;

		try {

			ctx = getDirContext();

			logger.debug("Context Initilized.....");

			// Create the search controls
			searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Specify the Base for the search
			searchBase = "";

			// specify the LDAP search filter
			searchFilter = "(&(objectcategory=person)(objectClass=user)(sAMAccountName="
					+ samAccount + "))";

			// Search for objects using the filter
			answer = ctx.search(searchBase, searchFilter, searchCtls);

			userData = new JSONObject();
			userInfo = new JSONArray();

			// Loop through the search results
			while (answer.hasMoreElements()) {
				sr = answer.next();

				// userData.put("CN", sr.getName());

				// Print out some of the attributes, catch the exception if the
				// attributes have no values
				attrs = sr.getAttributes();
				if (attrs != null) {
					try {

						try {
							sn = (String) attrs.get("sn").get();
						} catch (Exception e) {
						}

						try {
							sAMAccountName = (String) attrs.get(
									"sAMAccountName").get();
						} catch (Exception e) {
						}

						try {
							givenName = (String) attrs.get("givenName").get();
						} catch (Exception e) {
						}

						if (!givenName.equalsIgnoreCase("")
								&& !sn.equalsIgnoreCase("")
								&& !sAMAccountName.equalsIgnoreCase("")) {
							try {
								email = (String) attrs.get("mail").get();
							} catch (Exception e) {
							}

							try {
								title = (String) attrs.get("title").get();
							} catch (Exception e) {
							}

							try {
								department = (String) attrs.get("department")
										.get();
							} catch (Exception e) {
							}

							userData.put("sn", sn);
							userData.put("sAMAccountName", sAMAccountName);
							userData.put("givenName", givenName);
							userData.put("mail", email);
							userData.put("title", title);
							userData.put("department", department);

							userInfo.add(userData);
							userData.clear();
						}

					} catch (NullPointerException e) {
					} catch (Exception e) {
					}
				}
			}
		} catch (NamingException e) {
			logger.debug("Problem searching directory: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Exception Problem searching directory: " + e);
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

	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public JSONArray getUserInfo() {
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

	public DirContext getDirContext() {
		return dirContext;
	}

}