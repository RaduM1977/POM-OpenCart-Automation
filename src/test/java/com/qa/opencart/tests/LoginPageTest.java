package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100: design login for open cart app")
@Story("US-Login: 101: design login page features for open cart")
public class LoginPageTest extends BaseTest{
	
	@Severity(SeverityLevel.TRIVIAL)
	@Description("......checking the title of the page .....tester:Radu M")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle,AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("......checking the url of the page .....tester:Radu M")
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("......checking forgot pwd link exist.....tester:Radu M")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	// this should be last test case because we want to make sure the tests in the login page should be run before moving to the accounts page 
	//another way to make sure this is the last test case we could use --> Integer.MAX_VALUE
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("......checking user is able to login to the app with correct username and password.....tester:Radu M")
	@Test(priority = 4)
	public void loginTest() {
		
		//loginPage.doLogin("naveen@gmail.com", "test@123");
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim()); 
		//accountsPage = loginPage.doLogin(prop.getProperty("username").trim(),System.getProperty("password").trim()); 
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	
	}
	
	
	
}
