package com.currencycoversion;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class VerifyCurrencyDetail {

	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://www.xe.com/currencyconverter/");
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		// Find the "From" currency input field and enter the currency code
		WebElement currFrom = driver.findElement(By.xpath("//input[@id='midmarketFromCurrency']"));
		currFrom.sendKeys("EUR");
		// currFrom.sendKeys(Keys.ENTER);

		// Find the "To" currency input field and enter the currency code
		WebElement currTo = driver.findElement(By.xpath("//input[@id='midmarketToCurrency']"));
		currTo.sendKeys("USD");
		Thread.sleep(3000);
		// currTo.sendKeys(Keys.ENTER);

		driver.findElement(By.xpath("//button[@class='button__BaseButton-sc-1qpsalo-0 haqezJ']")).click(); // accept the
																											// cookies
		driver.findElement(By.xpath("//html[@class='js-focus-visible']")).click(); // Close the pop up

		// Find the "Amount" input field and enter the amount to be converted
		WebElement amountInput = driver.findElement(By.xpath(
				"//div[@class='text-input__TextInput-sc-17mujrb-0 amount-input__Wrapper-sc-1gq6pic-0 ikyxCJ ezbfAz']"));
		amountInput.sendKeys("100");

		// Find and click the "Convert" button
		WebElement convertButton = driver
				.findElement(By.xpath("//button[@class='button__BaseButton-sc-1qpsalo-0 clGTKJ'][2]"));
		convertButton.click();

		// Wait for the conversion to complete and verify the result
		WebElement result = driver.findElement(By.xpath("//p[@class='result__BigRate-sc-1bsijpp-1 iGrAod']"));
		String actualResult = result.getText();
		CurrencyConverter eurtousd = new CurrencyConverter();
		double expectedResult = eurtousd.eurtoUSD(100);
		double actualResult1 = Double.parseDouble(actualResult);

		if (actualResult1 == expectedResult) {
			System.out.println("Currency conversion from EUR to USD is correct.");
		} else {
			System.out.println("Currency conversion from EUR to USD is incorrect.");
		}

	}

	public static void AcceptCookies() {
		// Find the element that corresponds to the "Accept" or "Agree" button for
		// cookies
		WebElement acceptButton = driver
				.findElement(By.xpath("//button[@class='button__BaseButton-sc-1qpsalo-0 haqezJ']"));
		// Check if the button is displayed
		if (acceptButton.isDisplayed()) {
			// Click the button to accept the cookies
			acceptButton.click();
		}
	}

	public static void handlePopup() {
		// Click on the button or element that triggers the pop-up
		driver.findElement(By.xpath("//html[@class='js-focus-visible']")).click();
		// Wait for the pop-up to appear
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Switch to the pop-up window
		Alert alert = driver.switchTo().alert();

		// Get the text of the pop-up message
		String alertText = alert.getText();
		System.out.println("Pop-up message: " + alertText);
		// Accept or dismiss the pop-up based on your needs
		// To accept:
		alert.accept();

		// To dismiss:
		alert.dismiss();
		// Switch back to the main window
		driver.switchTo().defaultContent();

	}

}
