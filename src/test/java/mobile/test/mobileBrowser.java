package mobile.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class mobileBrowser extends BrowserBaseTest {

    @Test
    public void testMobileBrowser() {
        // This is a placeholder for the mobile browser test.
        // You can implement your mobile browser testing logic here.
//        driver.get("https://www.google.com");
//        driver.findElement(By.name("q")).sendKeys("Appium");
//        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.get("https://rahulshettyacademy.com/amgularAppdemo/");
        driver.findElement(By.xpath("//span[@class='navbar-toggler-icon']")).click();
        driver.findElement(By.cssSelector("a[router link*='products']")).click();
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0, 1000);");
        String text = driver.findElement(By.cssSelector("a[href*='products/3']")).getText();
        Assert.assertEquals(text, "Devops");
    }
}
