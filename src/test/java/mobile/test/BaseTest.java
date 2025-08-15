package mobile.test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {

    AndroidDriver driver;
    AppiumDriverLocalService service;

//    @BeforeClass
//    public void startAppiumService() {
//        // Set up the Appium service
////        service = new AppiumServiceBuilder()
////                .withAppiumJS(new File("C:\\Users\\LENOVO\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
////                .usingAnyFreePort()
////                .build();
////        service.start();
//
//        service = new AppiumServiceBuilder()
//                .withAppiumJS(new File("C:\\Users\\LENOVO\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
//                .withTimeout(Duration.ofMillis(60000))
//                .withIPAddress("127.0.0.1")
//                .usingPort(4723).build();
//        service.start();
//    }
    @BeforeMethod
    public void configureAppiumDriver() throws MalformedURLException, URISyntaxException {

        // Set up the desired capabilities for the Android driver
        UiAutomator2Options options = new UiAutomator2Options();
        //options.setDeviceName("AniketEmulator");
        options.setDeviceName("Android Device"); // When connected real device to laptop
        options.setUiautomator2ServerLaunchTimeout(Duration.ofMillis(60000));
        //options.setApp("C:\\Users\\LENOVO\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
        options.setApp("C:\\Users\\LENOVO\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\General-Store.apk");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Below methods used to run testd on browserstack and above comment out methods used to run tests on local emulator (Android Studio)

//@BeforeMethod
//public void configureAppiumDriver() throws MalformedURLException {
//    HashMap<String,Object> bstOptions = new HashMap<String,Object>();
//    bstOptions.put("userName", "aniketgapat_Sz8RPk");
//    bstOptions.put("accessKey", "514TvvmqLkxtwNPwzu4i");
//    bstOptions.put("appiumVersion", "2.4.1");
//
//    UiAutomator2Options options = new UiAutomator2Options();
//   // options.setCapability("browserstack.user", "aniketgapat_Sz8RPk");
//  // options.setCapability("browserstack.key", "514TvvmqLkxtwNPwzu4i");
//    options.setDeviceName("GooglePixel6");
//    options.setPlatformName("Android");
//    options.setPlatformVersion("12.0");
//    // Replace with your uploaded app id
//    options.setApp("bs://6958621c9cab87dfc3ff2bd30247acd8806fa7cc"); //general store app on BrowserStack
//    //options.setApp("bs://588e96d854f9de9aa78428a68ccd99ddb1e32adf"); //api demos app on BrowserStack
//    // BrowserStack credentials
//    options.setCapability("bstack:options", bstOptions);
////    options.setCapability("project", "Your Project Name");
////    options.setCapability("build", "Build 1.0");
////    options.setCapability("name", "Sample Test");
//
//    driver = new AndroidDriver(new URL("https://hub.browserstack.com/wd/hub"), options);
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//}

    @AfterMethod
    public void tearDown() {
        // This method can be used to perform cleanup actions after tests
        driver.quit();
    }

//    @AfterClass
//    public void stopAppiumService() {
//        // Stop the Appium service
//        if (service != null && service.isRunning()) {
//            service.stop();
//        }
//    }

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

    public void generalStoreLogin(String name, String country){
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys(name);
        driver.hideKeyboard();
        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        driver.findElement(By.id("android:id/text1")).click();
//        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"India\"));"));
//        driver.findElement(By.xpath("//android.widget.TextView[@text='India']")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+country+"\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@text='"+country+"']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
    }
}
