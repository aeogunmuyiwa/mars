package com.mars;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TestSelenium {
	private WebDriver driver;
	private String url;
	private boolean acceptNextAlert = true;
	private String validUsername = "jpesci@mars.com";
	private String validPassword = "L@b!che";

	@Before
	public void setUp() throws Exception {
		setDriver("chrome");
		url = "http://localhost:9000";
		driver.get(url);
	}

	private void login() {
		login(validUsername, validPassword);
	}

	private void login(String username, String password){
		driver.get(url);
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);

		driver.findElement(By.id("password")).submit();
	}

	@Test
	public void headerIsCorrect() throws Exception {
		assertEquals("MARS SECURITY SYSTEM", driver.findElement(By.tagName("h1")).getText().toUpperCase());
	}

	@Test
	public void logsUserWithCorrectCredentials() {
		assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
		login(validUsername, validPassword);
		assertTrue(isElementPresent(By.id("account-menu")));
	}

	@Test
	public void doesNotLogUserWithIncorrectCredentials() {
		assertFalse(isElementPresent(By.id("account-menu"))); // starts logged out
		login(validUsername, validPassword+"toMakeInvalid");
		assertFalse(isElementPresent(By.id("account-menu")));
	}

	@Test
	public void createMarsVaultAccount() {
		login();
		driver.get(url+"/#/mars-vault/new");

		String siteName = "Site"+ RandomStringUtils.randomAlphanumeric(8);
		String login = "juju";
		String password = "mozzarella";

		driver.findElement(By.id("field_site")).sendKeys(siteName);
		driver.findElement(By.id("field_login")).sendKeys(login);
		driver.findElement(By.id("field_password")).sendKeys(password);

		driver.findElement(By.cssSelector("form[name='editForm']")).submit();

		//after creation ensure the account is in the list

		driver.findElement(By.cssSelector("table > tbody > tr")); // implicit wait
		WebElement siteNameCell = driver.findElement(By.xpath("//td[contains(text(),'" + siteName + "')]"));
		assertEquals(siteName, siteNameCell.getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private void setDriver(String driverName) {
		driverName = driverName.toLowerCase();

		long timeout = 5;

		switch (driverName) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "/opt");
				driver = new FirefoxDriver(new FirefoxBinary(new File("/usr/bin/firefox-esr")), new FirefoxProfile());
				driver.manage().window().maximize();
				break;
			case "chrome":
			    System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--start-maximized");
				driver = new ChromeDriver(chromeOptions);
				break;
			default:
				throw new IllegalArgumentException("Only supported drivers are 'chrome' and 'firefox'");
		}

		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
	}
}
