package com.sample.appium.pages;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    @AndroidFindBy(accessibility = "place-order")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`label == \"place-order\"`]")
    private MobileElement placeOrder;

    private AppiumService appium;

    public CartPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public OrderSumaryPage placeOrder() {
        appium.click(placeOrder);
        return new OrderSumaryPage(appium);
    }

    public Boolean checkItemExists(String itemName) {
        MobileElement item = appium.findElementContainText(itemName);
        if(item != null)
            return true;
        return false;
    }
}
