package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetup() {
		//accountsPage = loginPage.doLogin("qatestertest@gmail.com","Test@123");
		
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}
	
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
//		return new Object[][] {
//			{"MacBook","MacBook Pro",4},
//			{"iMac","iMac",3},
//			{"Apple","Apple Cinema 30\"",6},
//			{"Samsung","Samsung SyncMaster 941BW",1},		
//		};
		
		Object searchData[][] = ExcelUtil.getTestData("product");
		return searchData;
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey,String productName,String imagesCount) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		
		int actualImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actualImagesCount, Integer.parseInt(imagesCount));
		
	}


	@Test
	public void productInformationTest() {
		searchPage = accountsPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		
		Map<String,String> actualProductInfoMap = productInfoPage.getProductInfo();
		//System.out.println(actualProductInfoMap); --> we move it in the method it is looking better
		
		softAssert.assertEquals(actualProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actualProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actualProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actualProductInfoMap.get("productprice"), "$2,000.00");
		
		softAssert.assertAll();//it will tell us the information on which tests is failing 
	}
	
	//assert vs verify(soft assertion)  --> TestNG
	
	//Homework data provider 
	@DataProvider 
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"MacBook","MacBook Pro",1},
			{"MacBook","MacBook Air",2},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",4},
			{"Samsung","Samsung Galaxy Tab 10.1",5}	
			
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void addToCartTest(String searchKey, String productname,int quantity) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productname);
		productInfoPage.enterQuantity(quantity);
		String actualCartMessage = productInfoPage.addProductToCart();
		
		//Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actualCartMessage.contains(AppConstants.SUCCES_MESSAGE_FRACTION_VALUE));
		softAssert.assertTrue(actualCartMessage.contains(productname));
		
		softAssert.assertEquals(actualCartMessage,AppConstants.SUCCES_MESSAGE_FRACTION_VALUE + ": You have added "+productname+" to your shopping cart!");
		
		softAssert.assertAll();
	}
}
