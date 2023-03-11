package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final int DEFAULT_SHORT_TIME_OUT = 5;
	public static final int DEFAULT_MEDIUM_TIME_OUT = 10;
	public static final int DEFAULT_LONG_TIME_OUT = 20;
	
	public static final String LOGIN_PAGE_TITLE_VALUE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTION_VALUE = "route=account/login";

	public static final String ACCOUNTS_PAGE_TITLE_VALUE = "My Account";
	public static final String ACCOUNTS_PAGE_URL_FRACTION_VALUE = "route=account/account";
	public static final int ACCOUNTS_PAGE_HEADERS_COUNT = 4;
	
	public static final List<String> EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST = Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	public static final int PRODUCT_QUANTITY = 2;
	public static final String SUCCES_MESSAGE_FRACTION_VALUE = "Success";
	public static final String USER_REG_SUCCESS_MESSG = "Your Account Has Been Created";
	
	//****************** SHEET NAMES *******************
	public static final String REGISTER_SHEET_NAME = "register";
	
	
}


//test data : data we need to fill --> Ex: registration, login,form, search  -> maintain in the EXCEL/DB/json/xml(not a good approach) --> we use a static test data
//constants data : --> constant class
//environment data : --> properties file Ex:url,usename and password , browser