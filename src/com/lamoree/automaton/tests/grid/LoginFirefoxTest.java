package com.lamoree.automaton.tests.grid;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginFirefoxTest {
	private String hubUrl;
	private String baseUrl;
	private WebDriver driver;
	private DesiredCapabilities capability;
	private ResourceBundle rb = ResourceBundle.getBundle("test");

	@Before
	public void setUp() throws Exception {
		Properties p = System.getProperties();
		
		if (p.containsKey("grid.hubUrl")) {
			hubUrl = p.getProperty("grid.hubUrl");
		} else if (rb.containsKey("grid.hubUrl")) {
			hubUrl = rb.getString("grid.hubUrl");
		}
		
		if (p.containsKey("grid.baseUrl")) {
			baseUrl = p.getProperty("grid.baseUrl");
		} else if (rb.containsKey("grid.baseUrl")) {
			baseUrl = rb.getString("grid.baseUrl");
		}

		capability = DesiredCapabilities.firefox();
        driver = new RemoteWebDriver(new URL(hubUrl), capability);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testLogin() throws Exception {
		driver.get(baseUrl + "Home/index");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
        // Create a wait instance for use with wait.until()
        Wait<WebDriver> wait = new WebDriverWait(driver, 10);

		WebElement loginLink = wait.until(visibilityOfElementLocated(By.id("loginLink")));
		System.out.println("Got a login link element.");
        loginLink.click();

        // Should see the login form now
        WebElement username = wait.until(visibilityOfElementLocated(By.id("username")));
		System.out.println("Got a username element.");
		username.sendKeys(rb.getString("test.username"));
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(rb.getString("test.password"));
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();

		// Wait until the home page is shown with our friendly message
        WebElement account = wait.until(visibilityOfElementLocated(By.id("account")));
		System.out.println("Got the account element.");
        System.out.println("Account innerText: " + account.getText());
        
        //Assert.assertTrue(account.getText().contains("Hello, Unit Test"));
		
	}
    
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement toReturn = driver.findElement(locator);
				if (toReturn.isDisplayed()) {
					return toReturn;
				}
				return null;
			}
		};
	}
}
