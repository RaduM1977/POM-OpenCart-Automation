package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void regPageSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	//to generate the email or any other field we need it random
	public String getRandomEmail() {
		Random random = new Random();
		//this uses the random class
		//String email = "automation" + random.nextInt(1000)+"@gmail.com";
		
		// this approach is more unique 
		int  size = String.valueOf(System.currentTimeMillis()).length();
		
		String time = String.valueOf(System.currentTimeMillis()).substring(size-4);
		
		String email = "automation" + time +"@gmail.com";
		System.out.println(email);
		return email;
		
	}
	
	@DataProvider
	public Object[][] getRegTestdata() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	@Test(dataProvider = "getRegTestdata")
	public void userRegisterTest(String firstName,String lastName,
			String telephone,String password,String subscribe) {
		
		 Assert.assertTrue(registerPage.registerUser(firstName,lastName,
				 getRandomEmail(),telephone,password,subscribe));
	}

}
