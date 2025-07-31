package mobile.test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class BaseTest {

    AndroidDriver driver;
    AppiumDriverLocalService service;

    @BeforeClass
    public void startAppiumService() {
        // Set up the Appium service
//        service = new AppiumServiceBuilder()
//                .withAppiumJS(new File("C:\\Users\\LENOVO\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
//                .usingAnyFreePort()
//                .build();
//        service.start();

        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\LENOVO\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                // .withTimeout(Duration.ofMillis(60000))
                .withIPAddress("127.0.0.1")
                .usingPort(4723).build();
        service.start();
    }
    @BeforeMethod
    public void configureAppiumDriver() throws MalformedURLException, URISyntaxException {

        // Set up the desired capabilities for the Android driver
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("AniketEmulator");
        options.setUiautomator2ServerLaunchTimeout(Duration.ofMillis(60000));
        options.setApp("C:\\Users\\LENOVO\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        // This method can be used to perform cleanup actions after tests
        driver.quit();
    }

    @AfterClass
    public void stopAppiumService() {
        // Stop the Appium service
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

    public void jumpToPage(String appPackage, String appActivity) {
        // Jump to the settings app
        driver.executeScript("mobile: startActivity",
                ImmutableMap.of("appPackage",appPackage,
                        "appActivity",appActivity));
    }

    public void rotateScreen(String orientation) {
        if (orientation.equalsIgnoreCase("landscape")) {
            driver.rotate(new DeviceRotation(0, 0, 90));
        } else if (orientation.equalsIgnoreCase("portrait")) {
            driver.rotate(new DeviceRotation(0, 0, 0));
        }
    }

    public void longPress(WebElement element){
        ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement)element).getId(),
                        "duration", 2000));
    }

    public void swipeElement(WebElement element, String direction) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
                        "direction", direction,
                        "percent", 0.75));
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) source).getId(),
                        "toElementId", ((RemoteWebElement) target).getId(),
                        "duration", 1000));
    }
}
