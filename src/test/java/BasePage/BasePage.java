package BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BasePage extends PageGenerator {

  public BasePage(WebDriver driver) {
    super(driver);
  }

  WebDriverWait wait = new WebDriverWait(this.driver, 20);
  String baseURL = "http://yahoo.com/";

  public String getBaseURL() {
    return baseURL;
  }

  public <T> void click(T elementAttr) {
    if (elementAttr.getClass().getName().contains("By")) {
      driver.findElement((By) elementAttr).click();
    } else {
      ((WebElement) elementAttr).click();
    }
  }

  public <T> void sendText(T elementAttr, String text) {
    if (elementAttr.getClass().getName().contains("By")) {
      driver.findElement((By) elementAttr).sendKeys(text);
    } else {
      ((WebElement) elementAttr).sendKeys(text);
    }
  }

  public <T> String readText(T elementAttr) {
    if (elementAttr.getClass().getName().contains("By")) {
      return driver.findElement((By) elementAttr).getText();
    } else {
      return ((WebElement) elementAttr).getText();
    }
  }

  public void handlePopup(By by) throws InterruptedException {
    List<WebElement> popup = driver.findElements(by);
    if (!popup.isEmpty()) {
      popup.get(0).click();
      Thread.sleep(200);
    }
  }
}
