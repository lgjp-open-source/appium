package com.sample.appium.pages;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage {

    @AndroidFindBy(accessibility = "All Products, back")
    @iOSXCUITFindBy(id = "")
    private MobileElement backHeaderButton;

    @AndroidFindBy(accessibility = "cart")
    @iOSXCUITFindBy(id = "")
    private MobileElement cartHeaderIcon;

    @AndroidFindBy(accessibility = "add-to-cart")
    @iOSXCUITFindBy(id = "")
    private MobileElement addToCart;

    private AppiumService appium;
    public ProductDetailsPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public void addToCart() {
        appium.click(addToCart);
    }

    public CartPage clickCart() {
        appium.click(cartHeaderIcon);
        return new CartPage(appium);
    }

    public AllProductsPage goBack() {
        appium.click(backHeaderButton);
        return new AllProductsPage(appium);
    }
}
