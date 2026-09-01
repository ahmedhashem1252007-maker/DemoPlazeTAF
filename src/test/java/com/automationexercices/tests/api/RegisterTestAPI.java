package com.automationexercices.tests.api;

import com.automationexercices.apis.UserManagementAPI;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import com.automationexercices.utils.logs.TimeManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTestAPI extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();
    @Test
    public void registerNewAccount() {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();
    }


    @BeforeClass
    protected void setUp() {
        testData = new JsonReader("checkout-data");
    }

}
