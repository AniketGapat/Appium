package mobile.test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppiumBasics extends BaseTest {

    @Test
    public void WifiSettingsName() {
        //android or ios driver initialization
        //appium code -> appium server -> mobile device
        // Appium server is running on port 4723
        // Perform actions on the app
        //adb shell dumpsys window | find "mCurrentFocus"

        jumpToPage("io.appium.android.apis", ".preferences.PreferenceDependencies");
       // driver.findElement(AppiumBy.accessibilityId("Preference")).click();
       // driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(By.id("android:id/checkbox")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='WiFi settings']")).click();
        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        System.out.println("Alert Title: " + alertTitle);
        Assert.assertEquals(alertTitle, "WiFi settings"); // Uncomment if you want to assert the alert title
        driver.findElement(By.id("android:id/edit")).sendKeys("Aniket");
        driver.findElement(By.id("android:id/button1")).click();

    }

    @Test
    public void longPress(){
        driver.findElement(AppiumBy.accessibilityId("views")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
        WebElement ele = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='People Names']"));
        longPress(ele);
        String menuText = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Sample menu']")).getText();
        Assert.assertEquals(menuText, "Sample menu");
        Assert.assertTrue(driver.findElement(By.id("android:id/title")).isDisplayed());

    }

    @Test
    public void scrollTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"WebView\"));")).click();
        Assert.assertTrue(driver.findElement(AppiumBy.accessibilityId("WebView")).isDisplayed());
    }

    @Test
    public void swipeDemo(){
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        WebElement firstImage = driver.findElement(By.xpath("(//android.widget.ImageView)[1]"));
        Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
        swipeElement(firstImage, "left");

    }

    @Test
    public void dragAndDropWithCordinates() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
        WebElement source = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));

        driver.executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) source).getId(),
                "endX", 500,
                "endY", 1000
        ));
        Thread.sleep(3000);
        String droptext = driver.findElement(By.id("io.appium.android.apis:id/drag_result_text")).getText();
        Assert.assertEquals(droptext, "Dropped!");
    }

    @Test
    public void dragAndDropWithElement() throws InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
        WebElement source = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement target = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_2"));

        dragAndDrop(source, target);
        Thread.sleep(3000);
        String droptext = driver.findElement(By.id("io.appium.android.apis:id/drag_result_text")).getText();
        Assert.assertEquals(droptext, "Dropped!");
    }

    @Test
    public void miscellanous() {
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
        driver.findElement(By.id("android:id/checkbox")).click();

        //Turing device to 90 degrees in landscape mode and then perform actions.
        //DeviceRotation landscape = new DeviceRotation(0, 0, 90);
        //driver.rotate(landscape);

        rotateScreen("landscape");
        // Perform actions after rotating the device
        driver.findElement(By.xpath("//android.widget.TextView[@text='WiFi settings']")).click();
        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "WiFi settings"); // Uncomment if you want to assert the alert title

        //Copy - Paste the name in the text field
        driver.setClipboardText("Aniket");
        driver.findElement(By.id("android:id/edit")).sendKeys(driver.getClipboardText());
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.findElement(By.id("android:id/button1")).click();

        //Press back and then home button
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.HOME));

    }
}