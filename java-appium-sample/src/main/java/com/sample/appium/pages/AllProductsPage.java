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
    @iOSXCUITFindBy(id = "cart")
    private MobileElement cartHeaderIcon;

    @AndroidFindBy(accessibility = "cart-count")
    @iOSXCUITFindBy(id = "cart-count")
    private MobileElement cartCount;

    private AppiumService appium;

    public AllProductsPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public ProductDetailsPage clickItem(String name) {
        By xpath = MobileBy.xpath(String.format("//*[@text='%s' or @label='%s']", name, name));
        MobileElement shoe = appium.findElement(xpath);
        appium.click(shoe);
        return new ProductDetailsPage(appium);
    }

    public CartPage openCart() {
        appium.click(cartHeaderIcon);
        return new CartPage(appium);
    }

    public int getCartCount() {
        if (cartCount != null)
            return Integer.parseInt(cartCount.getText());
        return 0;
    }
}
