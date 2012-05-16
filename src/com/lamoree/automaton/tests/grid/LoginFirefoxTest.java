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

		WebElement loginLink = driver.findElement(By.id("loginLink"));
        loginLink.click();
        
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys(rb.getString("test.username"));
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(rb.getString("test.password"));
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();
		
		Assert.assertEquals("The Automaton : Login", driver.getTitle());
		
	}
    
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
    
}
