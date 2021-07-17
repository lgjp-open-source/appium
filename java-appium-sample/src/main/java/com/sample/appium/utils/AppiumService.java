package com.sample.appium.utils;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AppiumService {
    protected static AppiumDriver driver;
    protected static String platformName;

    // Driver executable files directory.
    private static final String CAPABILITIES_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator + "suites";

    public AppiumService(String capabilitiesFile, String appiumUrl, String platform) throws Exception {
        DesiredCapabilities desiredCapabilities = TestUtils.parseStringJSON(CAPABILITIES_DIR + File.separator + capabilitiesFile);
        platformName = platform;
        URL url = new URL(appiumUrl);
        switch(platformName) {
            case "Android":
                driver = new AndroidDriver(url, desiredCapabilities);
                break;
            case "iOS":
                driver = new IOSDriver(url, desiredCapabilities);
                break;
            default:
                throw new Exception("Invalid platform! - " + platformName);
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        int a = 0;
    }

    public AppiumDriver getDriver() { return driver; }

    public void setDriver(AppiumDriver driver2) { driver = driver2; }

    public AppiumService() {
        //PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
        boolean isAppiumServerRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            isAppiumServerRunning = true;
        } finally {
            socket = null;
        }
        return isAppiumServerRunning;
    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void clear(MobileElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(MobileElement e) {
        if (getDriver().getKeyboard() != null)
            getDriver().hideKeyboard();
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getText(MobileElement e, String msg) {
        String txt = null;
        switch(platformName) {
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
        }
        return txt;
    }

    public void closeApp() {
        ((InteractsWithApps) getDriver()).closeApp();
    }

    public void launchApp() {
        ((InteractsWithApps) getDriver()).launchApp();
    }

    public void stopDriver() {
        ((InteractsWithApps) getDriver()).closeApp();
        getDriver().quit();
    }

    public void closePopup() {
        By id = MobileBy.id("android:id/button1");
        MobileElement okBtn = findElement(id);
        click(okBtn);
    }

    public Boolean checkPopupWithTitleExists(String title) {
        By id = MobileBy.id("android:id/alertTitle");
        MobileElement titleTextbox = findElement(id);
        if ( titleTextbox!= null && titleTextbox.getText().equals(title))
            return true;
        return false;
    }

    public MobileElement findElement(By by) {
        return (MobileElement) driver.findElement(by);
    }
}
