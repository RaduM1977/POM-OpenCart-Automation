package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
		
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private Map<String,String> productInfoMap;
	
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMessage = By.cssSelector("div.alert.alert-success");

	
	//constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	//====== methods =========
	
	public String getProductHeaderValue() {
		String productHeaderVal = elementUtil.doElementGetText(productHeader);
		System.out.println("product header: " + productHeaderVal);
		return productHeaderVal;
	}
	
	public int getProductImagesCount() {
		int imagesCount = elementUtil.waitForElementsPresence(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("The product images count : " + imagesCount);
		return imagesCount;
	}
	
	
	
	
	public void enterQuantity(int qty) {
		System.out.println("Product quantity: " + qty);
		elementUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	
	
	public String addProductToCart() {
		elementUtil.doClick(addToCartBtn);
		
		String message = elementUtil.waitForElementVisible(cartSuccessMessage, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		StringBuilder sb = new StringBuilder(message);

		String succesMessage = sb.substring(0,message.length()-1).replace("\n","").toString();
		System.out.println("Cart Success Message: " + succesMessage);
		
		return succesMessage;
	}
	
	
	
	public Map<String, String> getProductInfo() {

		//productInfoMap =new HashMap<String,String>();
		productInfoMap =new LinkedHashMap<String,String>();
		//productInfoMap =new TreeMap<String,String>();
		
		//add the header to the map:
		productInfoMap.put("productname",getProductHeaderValue());
		
		//add the meta data to the map
		getProductMetaData();
		
		//add the price data to the map
		getProductPriceData();
		
		System.out.println(productInfoMap);
		return productInfoMap;
		
	}
	
	
	// ========== fetching meta data:
	private void getProductMetaData(){
		//meta data:
		List<WebElement> metaList = elementUtil.getElements(productMetaData);
		
		//meta data
//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock
		
		for(WebElement e: metaList) {
			String meta = e.getText(); //Brand: Apple
			
			String metaInfo[] = meta.split(":"); // segregate the data 
			
			//store in a HashMap 
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		
		}
		
	}
	
	
	// =========== fetching price data
	private void getProductPriceData() {
		
		//price data 
//		$2,000.00
//		Ex Tax: $2,000.00 --> index 1
		
		List<WebElement> priceList = elementUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();//$2,000.00
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();
		
		productInfoMap.put("productprice", price);//create a custom key to store the value in a mao
		productInfoMap.put("exTax", exTaxVal);
	}
	
	
	
}
