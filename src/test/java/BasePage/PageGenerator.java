package BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageGenerator {
  public WebDriver driver;

  public PageGenerator(WebDriver driver) {
    this.driver = driver;
  }

  public <TPage extends BasePage> TPage getInstance(Class<TPage> pageClass) {
    try {
      return PageFactory.initElements(driver, pageClass);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
