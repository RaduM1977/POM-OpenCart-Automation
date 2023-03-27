package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accountsPage;
	protected SearchPage searchPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	
	protected SoftAssert softAssert;

	@Parameters({"browser","browserversion","testcasename"})
	@BeforeTest
	public void setup(String browserName,String browserVersion,String testCaseName) {
		
		df = new DriverFactory();//create the object of the DriverFactory
		
		prop = df.initializeProp();
		
		//select the browser from runner xml file for the remote/parallel testing
		// if the remote browser is not null we override the browser in the property files 
		// with the value of browser from the parameter in the runner xml file
		
		if(browserName!= null) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);
			prop.setProperty("testcasename", testCaseName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		
		softAssert = new SoftAssert();
	
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
