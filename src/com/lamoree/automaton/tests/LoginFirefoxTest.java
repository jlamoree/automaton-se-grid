package com.lamoree.automaton.tests;

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

	@Before
	public void setUp() throws Exception {
		ResourceBundle rb = ResourceBundle.getBundle("test");
		Properties p = System.getProperties();
		
		if (p.containsKey("hubUrl")) {
			hubUrl = p.getProperty("hubUrl");
		} else if (rb.containsKey("hubUrl")) {
			hubUrl = rb.getString("hubUrl");
		}
		if (p.containsKey("baseUrl")) {
			baseUrl = p.getProperty("baseUrl");
		} else if (rb.containsKey("baseUrl")) {
			baseUrl = rb.getString("baseUrl");
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
		username.sendKeys("testuser");
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("password");
		username.submit();
		
		Assert.assertEquals("The Automaton : Login", driver.getTitle());
		
	}
    
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
    
}
