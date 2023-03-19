package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By accountsHeaders  = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	
	public String getAccountsTitle() {
//		String title = driver.getTitle();
		
		String title = elementUtil.waitForTitleAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
		System.out.println("Acc page title is: " + title);
		return title;
				
	}
	
	
	public String getAccountsURL() {
//		String url = driver.getCurrentUrl();
		
		String url = elementUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE);
		System.out.println("Acc page URL : " + url);
		return url;
				
	}
	

	public boolean isLogoutLinkExist() {
//		return driver.findElement(logoutLink).isDisplayed();
		return elementUtil.waitForElementVisible(logoutLink,AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public boolean isSearchExist() {
//		return driver.findElement(search).isDisplayed();
		
		return elementUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeadersList() {
		
		List<WebElement> accountsHeadersList = elementUtil.waitForElementsVisible(accountsHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		
		//List<WebElement> accountsHeadersList = driver.findElements(accountsHeaders);
		List<String> accountHeadersValList = new ArrayList<String>();
		
		for(WebElement e:accountsHeadersList ) {
			String text = e.getText();
			accountHeadersValList.add(text);	
		}
		return accountHeadersValList;
	}
	
	
	
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchExist()) {
			elementUtil.doSendKeys(search, searchKey);
			elementUtil.doClick(searchIcon);
			
			return new SearchPage(driver);
		}
		else {
			System.out.println("Search field is not presented on the page...");
			return null;
		}
	}
}
