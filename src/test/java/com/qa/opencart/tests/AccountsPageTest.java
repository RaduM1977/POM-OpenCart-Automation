package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	
	//we use before class to login 
	@BeforeClass
	public void accountsPageSetup() {
		//accountsPage = loginPage.doLogin("qatestertest@gmail.com","Test@123");
		
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}
	
	
	@Test
	public void accountsPageTitleTest() {
		String actualTitle = accountsPage.getAccountsTitle();
		Assert.assertEquals(actualTitle,AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}

	@Test
	public void accountsPageURLTest() {
		String actualURL = accountsPage.getAccountsURL();
		Assert.assertTrue(actualURL.contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}
	
	@Test
	public void accountsPageHeadersCountTest() {
		List<String> actualAccountsPageHeadersList = accountsPage.getAccountsPageHeadersList();
		System.out.println("Accounts Page headers List :" + actualAccountsPageHeadersList);
		Assert.assertEquals(actualAccountsPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accountsPageHeadersValueTest() {
		List<String> actualAccountsPageHeadersList = accountsPage.getAccountsPageHeadersList();
		
		System.out.println("Actual accounts Page headers List: " + actualAccountsPageHeadersList);
		System.out.println("Expected accounts Page headers List: " + AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccountsPageHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	
	//SearchPage tests
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"},
			{"Naveen"} //assertion is failing --> negative testing 
			
		};
	}
	
	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accountsPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductCount()>0);
		
	}
	
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"MacBook","MacBook Pro"},
			{"MacBook","MacBook Air"},
			{"iMac","iMac"},
			{"Apple","Apple Cinema 30\""},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"}	
			
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void searchProductTest(String searchKey,String productname) {
		searchPage = accountsPage.performSearch(searchKey);
		if(searchPage.getSearchProductCount()>0) {
			productInfoPage = searchPage.selectProduct(productname);
			String actualProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actualProductHeader, productname);
		}
		
	}
	
	
}
