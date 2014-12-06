package com.monsterClicker.webdriver;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:9000";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void energyShouldBeAbsorbed() throws Exception {
    // Open home page
    driver.get(baseUrl + "/");
    // Open monster
    driver.findElement(By.name("submit")).click();
    // Click the Energy button 20 times
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    // Absorb energy
    driver.findElement(By.cssSelector("h2 > form > input[name=\"submit\"]")).click();
  }

  @Test
  public void skillShouldBePurchased() throws Exception {
    // Open home page
    driver.get(baseUrl + "/");
    // Open monster
    driver.findElement(By.name("submit")).click();
    // Click the Energy button 20 times    
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    // Click the button to absorb the energy
    driver.findElement(By.cssSelector("h2 > form > input[name=\"submit\"]")).click();
    // Click the button to open the Skills shop
    driver.findElement(By.xpath("(//input[@name='submit'])[5]")).click();
    // Click the button to buy the first skill
    driver.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    // Click the button to go back to the monster
    driver.findElement(By.name("submit")).click();
  }

  @Test
  public void generatorShouldBePurchased() throws Exception {
    // Open home page
    driver.get(baseUrl + "/");
    // Open monster
    driver.findElement(By.name("submit")).click();
    // Click the Energy button 20 times    
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    // Click the button to absorb the energy
    driver.findElement(By.cssSelector("h2 > form > input[name=\"submit\"]")).click();
    // Click the button to open the Generators shop
    driver.findElement(By.xpath("(//input[@name='submit'])[6]")).click();
    // Click the button to buy the first generator
    driver.findElement(By.cssSelector("h2 > form > input[name=\"submit\"]")).click();
    // Click the button to go back to the monster
    driver.findElement(By.name("submit")).click();
  }

  @Test
  public void attributesShouldBeTrained() throws Exception {
    // Open home page
    driver.get(baseUrl + "/");
    // Open monster
    driver.findElement(By.name("submit")).click();
    // Click the Energy button 30 times  
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    driver.findElement(By.cssSelector("button")).click();
    // Click the button to absorb the energy
    driver.findElement(By.cssSelector("h2 > form > input[name=\"submit\"]")).click();
    // Click the button to train the attribute 4 times
    driver.findElement(By.id("bStr")).click();
    driver.findElement(By.id("bStr")).click();
    driver.findElement(By.id("bStr")).click();
    driver.findElement(By.id("bStr")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
