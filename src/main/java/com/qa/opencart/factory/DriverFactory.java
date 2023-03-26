package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	
	
	public WebDriver driver; //initially this is null
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	
	
	/**
	 * this method is initializing the driver on the basis of given browser name
	 * @param browserName
	 * @return this returns the driver 
	 */
	public WebDriver initDriver(Properties prop) {
		
		optionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight").trim();
		
		String browserName = prop.getProperty("browser").toLowerCase().trim();//if they are passing spaces at the front or/and the end we use trim()
		//String browserName = System.getProperty("browser").toLowerCase().trim();
		
		//You can pass the browser from the command line(terminal)
		//String browserName = System.getProperty("browser");
		
		System.out.println("browser name is: " + browserName);
		
		//chrome
		if(browserName.equalsIgnoreCase("chrome")) {
			// check if remote or local
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				//run on remote/grid:
				init_remoteDriver("chrome");
				
			}else {
				//local execution
			
				//we run the driver with the options ex: headless or incognito if they are true or if false runs without 
				//driver = new ChromeDriver(optionsManager.getChromeOptions());	
				
				//initialize the driver with ThreadSafe
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
				
			
		}
		//firefox
		else if(browserName.equalsIgnoreCase("firefox")) {
			// check if remote or local
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				//run on remote/grid:
				init_remoteDriver("firefox");
				
			}else {
				//local execution
			
				//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				//initialize the driver  with ThreadSafe
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			
		}
		//safari
		else if(browserName.equalsIgnoreCase("safari")) {
			//safari can not be run on Docker
			
			//driver = new SafariDriver();
			//initialize the driver  with ThreadSafe
			tlDriver.set(new SafariDriver());
			
		}
		//edge
		else if(browserName.equalsIgnoreCase("edge")) {
			// check if remote or local
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				//run on remote/grid:
				init_remoteDriver("edge");
				
			}else {
				//local execution
			
				//driver = new EdgeDriver(optionsManager.getEdgeOptions());
				//initialize the driver  with ThreadSafe
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			
		}
		else {
			System.out.println("please pass the right browser name ..."+ browserName);
			throw new FrameworkException("NO BROWSER FOUND EXCEPTION");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		
		return getDriver();
		
	}
	
	
	/**
	 * this method is called internally to initialize the driver with RemoteWebDriver
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {
		
		System.out.println("running test on grid server::: "+browser);
		
	try {
		switch (browser.toLowerCase()) {
		case "chrome": //the hub URL in the RemoteWebDriver object 
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
		break;
			
		case "firefox":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getFirefoxOptions()));
		break;
			
		case "edge":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getEdgeOptions()));
		break;	
			
		default:
			System.out.println("plz pass the right browser for remote execution..." + browser);
			throw new FrameworkException("NOREMOTEBROWSEREXCEPTION");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		 
	}
	
	
	
	/*
	 * get the local thread copy of the driver,
	 * synchronized so each driver will get their own respective copy 
	 */
	public synchronized static WebDriver  getDriver() {
		return tlDriver.get();
	}
	

	
	/**
	 * this method is reading the properties from the .properties file 
	 * @return
	 */
	public Properties initializeProp() {
		
		//mvn clean install -Denv="stage"
		//mvn clean install
		
		prop = new Properties();
		FileInputStream ip = null;
		
		
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);
		
		try {
			
		if(envName == null ) { // if no environment is passed
			System.out.println("no env is passed ... Running tests on QA env ....");
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		else {
			
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
				
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
				
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
				
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;
			default:
				System.out.println("....Wrong env is passed ... No need to run test cases.........");
				throw new FrameworkException("WRONG ENV IS PASSED....");
				//break;
				}
			}
		
		}
		catch(FileNotFoundException e) {
			
		}
		
//		prop = new Properties();
//		try {
//			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");//from Java used to interact with the file from src and put the. in front
//															// the . said to go to the file directly
//		
//				prop.load(ip);
//			
//		} catch (FileNotFoundException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	
	/**
	 *  take screenshot
	 * @return 
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		//Use the FileUtil
		//user.dir -- current user directory 
		String path = System.getProperty("user.dir")+ "/screenshot/" + System.currentTimeMillis()+".png";
		
		File destination = new File(path);
		try {
			//u can use FileUtil from allure 
			//FileHandler.copy(srcFile, destination);
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
//	public static String getScreenshot() {
//		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
//		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
//		File destination = new File(path);
//		try {
//			FileUtil.copyFile(srcFile, destination);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return path;
//	}
	
	
	

}
