package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver; //default is null
	private ElementUtil elementUtil;

	//1.Private BY locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	//2. page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver; 
		elementUtil = new ElementUtil(driver);
	}
	
	//3. page actions/methods ============
	@Step("........getting the login page title......")
	public String getLoginPageTitle() {
//		String title = driver.getTitle();
		
		String title = elementUtil.waitForTitleAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login page title: "+title);
		return title;
	}
	@Step("........getting the login page url......")
	public String getLoginPageURL() {
//		String url = driver.getCurrentUrl();
		
		String url = elementUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login page url: "+url);
		return url;
	}
	@Step("........getting the forgot pwd link ......")
	public boolean isForgotPwdLinkExist() {
		//return driver.findElement(forgotPwdLink).isDisplayed();
		return elementUtil.waitForElementVisible(forgotPwdLink,AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String un, String pwd) {
//		driver.findElement(emailId).sendKeys(un);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		
		System.out.println("App credentials are : " + un + " <=> " + pwd );
		//use the element Util class to perform the login 
		elementUtil.waitForElementVisible(emailId,AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginBtn);
		
		//should return the the next landing page class object --> TDD approach -->page chaining model
		return new AccountsPage(driver);
	}
	
	@Step("navigating to register page ")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

//	public void doLoginWithWrongData(String un,String pwd) {
//		System.out.println("App credentials are : " + un + " : " + pwd);
//		elementUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
//		elementUtil.doSendKeys(password, pwd);
//		elementUtil.doClick(loginBtn);
//		return the appropriate message 
//	}
}
