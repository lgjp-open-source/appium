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
    public String getPlatformName() { return platformName; }

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
        String id="";
        switch(platformName) {
            case "Android":
                id = "android:id/button1";
                break;
            case "iOS":
                id = "Ok";
                break;
        }
        MobileElement okBtn = findElement(MobileBy.id(id));
        click(okBtn);
    }

    public Boolean checkPopupWithTitleExists(String title) {
        switch(platformName) {
            case "Android":
                By id = MobileBy.id("android:id/alertTitle");
                MobileElement titleTextbox = findElement(id);
                if ( titleTextbox!= null && titleTextbox.getText().equals(title))
                    return true;
                break;
            case "iOS":
                MobileElement popup =  findElementContainText(title);
                if (popup != null)
                    return true;
                break;
        }
        return false;
    }

    public MobileElement findElement(By by) {
        return (MobileElement) driver.findElement(by);
    }

    // driver.hideKeyboard() not work on some iOS simulator/ devices
    public void hideIOSKeyboard() {
        if(platformName.equals("iOS")) {
            MobileElement element = findElement(MobileBy.id("Return"));
            if (element != null)
                element.click();
        }
    }

    public MobileElement findElementContainText(String text) {
        String by="";
        WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
        if(platformName.equals("Android"))
            by = String.format("//*[@text='%s']", text, text);
        else if (platformName.equals("iOS"))
            by =String.format("(//*[contains(@label, '%s')])[last()]",  text);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(MobileBy.xpath(by), text));
        return findElement(MobileBy.xpath(by));
    }
}
