package iOS.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class iOSbaseTest {

    public IOSDriver driver;
    public AppiumDriverLocalService service;

    @BeforeClass
    public void startAppiumService() {
        // This method can be used to start the Appium service if needed
        // For BrowserStack, the service is managed by their infrastructure
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\LENOVO\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723).build();
        service.start();
    }

    @BeforeMethod
    public void configureAppiumDriver() throws MalformedURLException {
        HashMap<String,Object> bstOptions = new HashMap<String,Object>();
        bstOptions.put("userName", "aniketgapat_Sz8RPk");
        bstOptions.put("accessKey", "514TvvmqLkxtwNPwzu4i");
        bstOptions.put("appiumVersion", "2.4.1");

        XCUITestOptions options = new XCUITestOptions();
        // options.setCapability("browserstack.user", "aniketgapat_Sz8RPk");
        // options.setCapability("browserstack.key", "514TvvmqLkxtwNPwzu4i");
        options.setDeviceName("Iphone 13");
        options.setPlatformVersion("15.5");
        options.setWdaLaunchTimeout(Duration.ofSeconds(20));
        //options.setApp("C:\\Users\\LENOVO\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\UIKitCatalog.app");
        options.setApp("C:\\Users\\LENOVO\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\TestApp 3.app");

        // Replace with your uploaded app id if want to test on cloud
        //options.setApp("bs://6958621c9cab87dfc3ff2bd30247acd8806fa7cc"); //general store app on BrowserStack
        //options.setApp("bs://588e96d854f9de9aa78428a68ccd99ddb1e32adf"); //api demos app on BrowserStack
        options.setCapability("bstack:options", bstOptions);

        driver = new IOSDriver(new URL("https://hub.browserstack.com/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        // This method can be used to perform cleanup actions after tests
        driver.quit();
    }

    @AfterClass
    public void stopAppiumService() {
        service.stop();
    }

    public void iosLongPress(WebElement ele, int duration) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        params.put("duration", duration); // Duration in seconds
        driver.executeScript("mobile: touchAndHold", params);
    }

    public void iOSScroll(WebElement ele, String direction) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        params.put("direction", direction);
        driver.executeScript("mobile: scroll", params);
    }
}
