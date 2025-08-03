package iOS.test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

public class iOSBasics extends iOSbaseTest{

    @Test
    public void iosTest() {

        //Locators techniques - XPATH, id, classname, IOS, iosclassChain, iOS predicate string, accessibility id
        driver.findElement(AppiumBy.accessibilityId("Alert Views")).click();
        //driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Text Entry']")).click();
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`label=='text Entry'`]")).click();
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeTypeCell")).sendKeys("Aniket Gapat");
        driver.findElement(AppiumBy.accessibilityId("OK")).click();

        //driver.findElement(AppiumBy.iOSNsPredicateString("type == XCUIElementTypeStaticText' AND value == 'Confirm / Cancel'"));
        driver.findElement(AppiumBy.iOSNsPredicateString("type == XCUIElementTypeStaticText' AND value BEGINSWITH[C] 'Confirm'")).click();
       // driver.findElement(AppiumBy.iOSNsPredicateString("type == XCUIElementTypeStaticText' AND value ENDSSWITH[C] 'Cancel'"));
        String text = driver.findElement(AppiumBy.iOSNsPredicateString("name BEGINSWITH[C] 'A massage'")).getText();
        driver.findElement(AppiumBy.iOSNsPredicateString("label == 'Confirm'")).click();

    }

    @Test
    public void iosLongPress() {
        driver.findElement(AppiumBy.accessibilityId("Steppers")).click();
        WebElement ele =  driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label=='Increment'`]"));
        iosLongPress(ele,2);

    }

    @Test
    public void iosScrollTest() {
        WebElement ele = driver.findElement(AppiumBy.accessibilityId("Web View"));
        iOSScroll(ele, "down");
        driver.findElement(AppiumBy.accessibilityId("Web View")).click();
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='UIKitCatalog']")).click();
        driver.findElement(AppiumBy.accessibilityId("Picker View"));
        driver.findElement(AppiumBy.accessibilityId("Red color component value")).sendKeys("80");
        driver.findElement(AppiumBy.accessibilityId("Green color component value")).sendKeys("220");
        driver.findElement(AppiumBy.accessibilityId("Blue color component value")).sendKeys("105");
        String number = driver.findElement(AppiumBy.iOSNsPredicateString("label == 'Blue color component value'")).getText();
        Assert.assertEquals(number, "105");
    }

    @Test
    public void iosSliderTest() {
        driver.findElement(AppiumBy.accessibilityId("Sliders")).click();
        WebElement slider = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeSlider[`label == 'AppElem'`]"));
        slider.sendKeys("1"); // Set the slider to 100%
        System.out.println(slider.getAttribute("value")); // Print the current value of the slider
        Assert.assertEquals(slider.getAttribute("value"), "100%");

        driver.executeScript("mobile: slider", ImmutableMap.of("element", ((RemoteWebElement)slider).getId(), "value", 0.5));
        String value = driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND label BEGINSWITH 'Slider value'")).getText();
        Assert.assertEquals(value, "Slider value: 0.5");
    }

    @Test
    public void iosSwipePhotos() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bundleId", "com.apple.mobileslideshow");
        driver.executeScript("mobile: launchApp", params);
        driver.findElement(AppiumBy.iOSNsPredicateString("label == 'All Photos'")).click();
        List<WebElement> allPhotos = driver.findElements(AppiumBy.iOSClassChain("**/XCUIElementTypeCell"));
        driver.findElement(By.xpath("//XCUIElementTypeCell[1]")).click();
        for(int i=0; i<allPhotos.size(); i++) {
            HashMap<String, Object> params1 = new HashMap<>();
            params1.put("direction", "left");
            driver.executeScript("mobile: swipe", params1);
        }
        driver.navigate().back();
        driver.findElement(AppiumBy.accessibilityId("Albums")).click(); //cleanup

    }

}
