package steps;

import BasePage.PageGenerator;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import suites.Setup.Driver;

import java.net.MalformedURLException;

public class BaseSetupStepDef implements En {
  static WebDriver driver;
  static PageGenerator page;

  public BaseSetupStepDef() {
    Before(BaseSetupStepDef::setupDriver);
    After(BaseSetupStepDef::tearDownDriver);
  }

  private static void setupDriver(Scenario scenario) {
    try {
      Driver.getInstance().setDriver(getDriverBrowser(scenario));
      driver = Driver.getInstance().getDriver();
      page = new PageGenerator(driver);
    } catch (MalformedURLException e) {
      System.err.println("Error with driver!");
    }
  }

  private static Driver.WebDriverType getDriverBrowser(Scenario scenario) {
    String scenarioName = scenario.getUri().split("_")[3].toUpperCase();
    return Driver.WebDriverType.valueOf(scenarioName);
  }

  private static void tearDownDriver(Scenario scenario) {
    if (scenario.isFailed()) {
      scenario.write("Scenario failed so capturing a screenshot");

      TakesScreenshot screenshot = (TakesScreenshot) driver;
      scenario.embed(screenshot.getScreenshotAs(OutputType.BYTES), "image/png");
    }
    if (driver != null) {
      driver.quit();
    }
  }
}
