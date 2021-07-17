package com.sample.appium.pages;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderSumaryPage {
    @AndroidFindBy(accessibility = "add-select-address")
    @iOSXCUITFindBy(id = "")
    private MobileElement addAddress;

    @AndroidFindBy(accessibility = "place-order")
    @iOSXCUITFindBy(id = "")
    private MobileElement placeOrder;

    private AppiumService appium;

    public OrderSumaryPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public SelectAddressPage addSelectAddress() {
        appium.click(addAddress);
        return new SelectAddressPage(appium);
    }

    public void checkout() {
        appium.click(placeOrder);
    }
}
