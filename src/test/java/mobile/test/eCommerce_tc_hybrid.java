package mobile.test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class eCommerce_tc_hybrid extends BaseTest {
    // This class is intended to be used for hybrid testing of eCommerce functionalities
    // It can include methods that combine both native and web views in the app
    // For example, it can test scenarios where the app interacts with a web view for payment processing or product details

    @Test
    public void validateAmount() throws InterruptedException {
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

        // Accept the terms and conditions
        WebElement ele = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longPress(ele);
        driver.findElement(By.id("android:id/button1")).click(); // Click on the CLOSE button in the terms and conditions dialog

        // Select teh checkbox and Proceed to checkout
        driver.findElement(By.className("android.widget.Checkbox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(6000); // Wait for the web view to load, adjust as necessary

        // Switch to the web view context
        Set<String> contexts = driver.getContextHandles();
        for(int i=0; i<contexts.size(); i++) {
            System.out.println("Context: " + contexts.toArray()[i]);
        }
        driver.context("WEBVIEW_com.androidsample.generalstore");
        // Now you can interact with web elements
        //add path of browser dependency in base text.
        driver.findElement(By.name("q")).sendKeys("Appium");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        // Switch back to the native app context
        driver.context("NATIVE_APP");


    }
}
