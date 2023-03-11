package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//========= finding the elements =========
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	
	
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']/input[@type='radio']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']/input[@type='radio']");
	
	private By registerSuccesMesg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	// ======= constructor ==========
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		
	}
	
	public boolean registerUser(String firstName,String lastName,
						String email,String telephone,String password,String subscribe ) {
		
		elementUtil.waitForElementVisible(this.firstName,AppConstants.DEFAULT_SHORT_TIME_OUT ).sendKeys(firstName);
		elementUtil.doSendKeys(this.lastName,lastName);
		elementUtil.doSendKeys(this.email,email);
		elementUtil.doSendKeys(this.telephone,telephone);
		elementUtil.doSendKeys(this.password,password);
		elementUtil.doSendKeys(this.confirmPassword,password);
			
			if(subscribe.equalsIgnoreCase("yes")) {
				elementUtil.doClick(subscribeYes);
			}
			else {
				elementUtil.doClick(subscribeNo);
			}

			elementUtil.doActionsClick(agreeCheckBox);
			elementUtil.doClick(continueButton);
			
			String successMesg = elementUtil.waitForElementVisible(registerSuccesMesg, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
			
			System.out.println("user reg succcess message is: " + successMesg);
			
			if(successMesg.contains(AppConstants.USER_REG_SUCCESS_MESSG)) {
				
				//before returning we need to log out and click register again for the second user to be created 
				elementUtil.doClick(logoutLink);
				elementUtil.doClick(registerLink);
				return true;
			}
				return false;
			
	}
	
	
	
	
	
	
}
