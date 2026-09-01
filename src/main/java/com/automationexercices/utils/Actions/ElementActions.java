package com.automationexercices.utils.Actions;

import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private WaitManager waitManager;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Clicking
    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);

                // 1. حفظ المكان الأول
                Point initialLocation = element.getLocation();
                LogsManager.info("initialLocation: " + initialLocation);

                // 2. توقف زمني لحظي (50 مللي ثانية) عشان ندي فرصة للمتصفح يغير مكان العنصر لو بيتحرك
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}

                // 3. حفظ المكان التاني بعد التوقف
                Point finalLocation = element.getLocation();
                LogsManager.info("finalLocation: " + finalLocation);

                if (!initialLocation.equals(finalLocation)) {
                    return false; // العنصر لسه بيتحرك، هنرجع false عشان الـ FluentWait يحاول تاني
                }

                // 4. محاولة الضغط الطبيعي
                element.click();
                LogsManager.info("Clicked on element:" + locator);
                return true;

            } catch (Exception e) {
                // 5. نورنا الضلمة: طباعة الخطأ الحقيقي اللي خلى الـ click يفشل بدل ما نكتمه
                LogsManager.error("Normal click failed for: " + locator + " | Reason: " + e.getMessage());

                // 6. الخطة البديلة (Fallback): استخدام الجافا سكريبت لو الزرار متغطي بـ Overlay أو مخفي
                try {
                    LogsManager.info("Attempting JavaScript Click as fallback...");
                    JavascriptExecutor js = (JavascriptExecutor) d;
                    js.executeScript("arguments[0].click();", d.findElement(locator));
                    LogsManager.info("JS Click succeeded on element: " + locator);
                    return true;
                } catch (Exception jsException) {
                    LogsManager.error("JS Click also failed: " + jsException.getMessage());
                    return false; // الطريقتين فشلوا، نرجع false عشان الـ wait يكمل لف
                }
            }
        });

        return this;
    }
    //Typing
    public ElementActions type(By locator, String text) {

        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed to element:" + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );

        return this;
    }
    //hovering
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered on element:" + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );

        return this;
    }

    //Getting Text
    public String getText(By locator) {
        return waitManager.fluentWait().until(d ->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                String msg = element.getText();
                LogsManager.info("Got text from element:" + locator);
                return !msg.isEmpty() ? msg : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    //Upload file
    public ElementActions uploadFile(By locator, String filePath) {
        String fileAbsolute = System.getProperty("user.dir") + File.separator + filePath;


        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file to element:" + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    //find an element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //function to scroll to an element using js
    public void scrollToElementJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(behavior='auto', block='center', inline='center');", findElement(locator));
    }

    // Select From dropDown
    public ElementActions selectFromDropDown(By locator, String value) {
        waitManager.fluentWait().until(d -> {
            WebElement element = d.findElement(locator);
            scrollToElementJS(locator);
            Select select = new Select(element);
            try {
                select.selectByValue(value);
            } catch (Exception e) {
                // لو الفاليو مش نافعة، جرب تختار بالـ Visible Text كخطة بديلة
                select.selectByVisibleText(value);
            }
            LogsManager.info("Selected value from dropdown: " + locator);
            return true;
        });
        return this;
    }
}




