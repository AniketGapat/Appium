package mobile.test;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class eCommerce_tc_1 extends BaseTest{

    @Test
    public void FillForm(){
        generalStoreLogin("Aniket Gapat", "India");
        // Verify the title of the page
        String title = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")).getText();
        Assert.assertEquals(title, "Products");
    }

    @Test
    public void errorMessage() {
        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        driver.findElement(By.id("android:id/text1")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"India\"));")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='India']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        String errorMessage = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
        Assert.assertEquals(errorMessage, "Please enter your name");
    }

    @Test
    public void addProductsToCart() {
        generalStoreLogin("Aniket Gapat", "India");

        // Scroll to find the product and add it to the cart
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Jordan 6 Rings\"));"));

        // Get the product count and add the above product to the cart
        int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        for(int i=0; i<productCount; i++) {
            String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if(productName.equalsIgnoreCase("Jordan 6 Rings")) {
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
                break;
            }
        }
        // Verify the product in the cart
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        // Wait till page fully loads otherwise test gets fail
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));
        String productName = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        Assert.assertEquals(productName, "Jordan 6 Rings");
    }

}
