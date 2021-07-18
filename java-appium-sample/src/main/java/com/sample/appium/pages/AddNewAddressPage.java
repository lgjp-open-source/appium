package com.sample.appium.pages;

import com.sample.appium.utils.AppiumService;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class AddNewAddressPage {

    @AndroidFindBy(accessibility = "name")
    @iOSXCUITFindBy(id = "name")
    private MobileElement name;

    @AndroidFindBy(accessibility = "mobile-number")
    @iOSXCUITFindBy(id = "mobile-number")
    private MobileElement mobile;

    @AndroidFindBy(accessibility = "pincode")
    @iOSXCUITFindBy(id = "pincode")
    private MobileElement pincode;

    @AndroidFindBy(accessibility = "house-no")
    @iOSXCUITFindBy(id = "house-no")
    private MobileElement house;

    @AndroidFindBy(accessibility = "road-name")
    @iOSXCUITFindBy(id = "road-name")
    private MobileElement road;

    @AndroidFindBy(accessibility = "city")
    @iOSXCUITFindBy(id = "city")
    private MobileElement city;

    @AndroidFindBy(accessibility = "save-update-address")
    @iOSXCUITFindBy(id = "save-update-address")
    private MobileElement saveUpdateAddress;

    private AppiumService appium;

    public AddNewAddressPage(AppiumService appium) {
        this.appium = appium;
        PageFactory.initElements(new AppiumFieldDecorator(appium.getDriver()), this);
    }

    public void setName(String name2) {
        appium.sendKeys(name, name2);
    }

    public void setPincode(String pincode2) {
        appium.sendKeys(pincode, pincode2);
    }

    public void setMobile(String mobile2) {
        appium.sendKeys(mobile, mobile2);
    }

    public void setHouse(String houseNo) {
        appium.sendKeys(house, houseNo);
    }

    public void setRoad(String road2) {
        appium.sendKeys(road, road2);
    }

    public void setCity(String city2) {
        appium.sendKeys(city, city2);
    }

    public SelectAddressPage saveUpdateAddress() {
        appium.click(saveUpdateAddress);
        return new SelectAddressPage(appium);
    }
}
