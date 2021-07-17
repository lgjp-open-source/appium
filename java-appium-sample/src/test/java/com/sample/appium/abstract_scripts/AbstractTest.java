package com.sample.appium.abstract_scripts;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class AbstractTest {
    protected AppiumService service;

    @BeforeClass(alwaysRun = true, description = "Start the Appium Driver")
    @Parameters({"os", "appium-url", "ios-capabilities", "android-capabilities"})
    public void startApplication(String os, String appiumUrl, @Optional String iOSCapabilitiesFile, @Optional String androidCapabilitiesFile) throws Exception {
        if (os.equals("ios"))
            service = new AppiumService(iOSCapabilitiesFile, appiumUrl, "iOS");
        else
            service = new AppiumService(androidCapabilitiesFile, appiumUrl, "Android");
        testSetup();
    }

    @AfterClass(description = "Stop the Application", alwaysRun = true)
    public void stopApplication() {
        service.stopDriver();
    }

    public abstract void testSetup();
}
