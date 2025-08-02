package mobile.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class eCommerce_tc_2 extends BaseTest {

    @Test
    public void validateAmount(){
        generalStoreLogin("Aniket Gapat", "India");
        driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
        //driver.findElement(By.xpath("//android.widget.TextView[@text='ADD TO CART'][1]")).click();
        driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();

        // Navigate to the cart page
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        // Wait for the cart page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        double total = 0.0;
        for(int i=0; i<productPrices.size(); i++){
            String amountString = productPrices.get(i).getText();
            double amount = Double.parseDouble(amountString.substring(1)); // Remove the dollar sign and parse the amount into double
            total += amount; // Add the amount to the total
        }
        // Get the total amount displayed in the cart
        String totalAmountText = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        double totalAmount = Double.parseDouble(totalAmountText.replace("$", "")); // Remove the dollar sign and parse the total amount into double

        // Verify the total amount is correct
        // Note: Replace 123.45 with the actual expected total amount based on the products added
        Assert.assertEquals(totalAmount, 123.45, "Total amount does not match expected value");

        // Accept the terms and conditions
        WebElement ele = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longPress(ele);
        driver.findElement(By.id("android:id/button1")).click(); // Click on the CLOSE button in the terms and conditions dialog

        // Select the checkbox and Proceed to checkout
        driver.findElement(By.className("android.widget.Checkbox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
    }
}
