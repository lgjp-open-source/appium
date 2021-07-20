package com.sample.appium.pages;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class SelectAddressPage {

    @AndroidFindBy(accessibility = "add-new-address")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`label == \"add-new-address\"`][9]")
    private MobileElement addNewAddress;

    @AndroidFindBy(accessibility = "Order Summary, back")
    @iOSXCUITFindBy(id = "Order Summary, back")
    private MobileElement backButton;


    private AppiumService appium;

    public SelectAddressPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public AddNewAddressPage addNewAddress() {
        appium.click(addNewAddress);
        return new AddNewAddressPage(appium);
    }

    public void selectAddress(String name) {
        MobileElement address = appium.findElementContainText(name);
        appium.click(address);
    }

    public OrderSumaryPage goBack () {
        appium.click(backButton);
        return new OrderSumaryPage(appium);
    }

    public Boolean checkAddressExists (String name) {
        MobileElement address = appium.findElementContainText(name);
        if (address != null)
            return true;
        return false;
    }
}
