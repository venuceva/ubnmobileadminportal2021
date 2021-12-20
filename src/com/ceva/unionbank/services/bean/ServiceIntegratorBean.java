package com.ceva.unionbank.services.bean;

public class ServiceIntegratorBean {
private String serviceUserName;
private String servicePassword;
private String databaseType;

public String getDatabaseType() {
	return databaseType;
}
public void setDatabaseType(String databaseType) {
	this.databaseType = databaseType;
}
public String getServiceUserName() {
	return serviceUserName;
}
public void setServiceUserName(String serviceUserName) {
	this.serviceUserName = serviceUserName;
}
public String getServicePassword() {
	return servicePassword;
}
public void setServicePassword(String servicePassword) {
	this.servicePassword = servicePassword;
}


}
