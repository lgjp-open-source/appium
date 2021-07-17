package com.sample.appium.test_scripts;

import com.sample.appium.abstract_scripts.AbstractTest;

import com.sample.appium.pages.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class E2EDemoTest extends AbstractTest {

    @Override
    public void testSetup() {
    }

    @BeforeClass
    public void beforeClass() {
        service.closeApp();
        service.launchApp();
    }

    @Test(description = "User can checkout the shopping cart with valid information")
    public void buyAndCheckoutCart() {
        // Navigate to All Products page
        AllProductsPage homePage = new AllProductsPage(service);

        // Add Item 1 to cart
        homePage = addItem(homePage, "Cotton Black Cap");

        // Add Item 2 to cart
        homePage = addItem(homePage, "Golden Pilot Jacket");

        // Verify 2 item added to cart
        //assertEquals(2,homePage.getCartCount());

        // Open Cart
        CartPage cartPage = homePage.openCart();

        // Verify Items appear in cart
        assertTrue(cartPage.checkItemExists("Cotton Black Cap"));
        assertTrue(cartPage.checkItemExists("Golden Pilot Jacket"));

        // Place Order (go to Order Summary)
        OrderSumaryPage orderSumaryPage = cartPage.placeOrder();

        // Add new address
        SelectAddressPage selectAddressPage = orderSumaryPage.addSelectAddress();

        // Fill new address information and save it
        AddNewAddressPage addNewAddressPage = selectAddressPage.addNewAddress();
        addNewAddressPage.setName("Iris Watson");
        addNewAddressPage.setMobile("3725872335");
        addNewAddressPage.setPincode("20620");
        addNewAddressPage.setHouse("Azusa");
        addNewAddressPage.setRoad("Fusce Rd.");
        addNewAddressPage.setCity("New York");
        selectAddressPage = addNewAddressPage.saveUpdateAddress();

        // Verify saved address appear in address lists
        assertTrue(selectAddressPage.checkAddressExists("Iris Watson"));

        // Select added address
        selectAddressPage.selectAddress("Iris Watson");

        // Check out
        orderSumaryPage = selectAddressPage.goBack();
        orderSumaryPage.checkout();

        // Verify that check out successful ( Pop up message show)
        assertTrue(service.checkPopupWithTitleExists("Success!"));

        // Close popup
        service.closePopup();
    }

    private AllProductsPage addItem(AllProductsPage home, String itemName) {
        ProductDetailsPage detailsPage = home.clickItem(itemName);
        detailsPage.addToCart();
        return detailsPage.goBack();
    }
}
