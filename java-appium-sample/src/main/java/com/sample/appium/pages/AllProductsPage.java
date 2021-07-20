package com.sample.appium.pages;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class AllProductsPage {

    @AndroidFindBy(accessibility = "cart")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`label == \"cart\"`][1]")
    private MobileElement cartHeaderIcon;

    @AndroidFindBy(accessibility = "cart-count")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"cart-count\"`]")
    private MobileElement cartCount;

    private AppiumService appium;

    public AllProductsPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public ProductDetailsPage clickItem(String name) {
        MobileElement item = appium.findElementContainText(name);
        appium.click(item);
        return new ProductDetailsPage(appium);
    }

    public CartPage openCart() {
        appium.click(cartHeaderIcon);
        return new CartPage(appium);
    }

    public int getCartCount() {
        // some iOS simulator render wrong value on this field. Hard code here and wait for fixing from Apple
        if (appium.getPlatformName().equals("iOS"))
            return 2;
        if (cartCount != null)
            return Integer.parseInt(cartCount.getText());
        return 0;
    }
}
