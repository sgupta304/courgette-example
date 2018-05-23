package suites.Setup;

import io.github.pramcharan.wd.binary.downloader.WebDriverBinaryDownloader;
import io.github.pramcharan.wd.binary.downloader.enums.BrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Driver {
	private static Driver instance;
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
	private DesiredCapabilities capabilities = new DesiredCapabilities();
	private Driver() {}

	public enum WebDriverType {
		CHROME,
		FIREFOX
	}

	/**
	 * Thread safe singleton instance of the Driver class
	 * @return
	 */
	public static Driver getInstance() {
		if (instance == null) {
			synchronized (Driver.class) {
				if (instance == null) {
					instance = new Driver();
				}
			}
		}
		return instance;
	}

	public void setDriver(WebDriverType driverType) {
		switch (driverType) {
			case CHROME:
				WebDriverBinaryDownloader.create().downloadLatestBinaryAndConfigure(BrowserType.CHROME);
				webDriver.set(new ChromeDriver(getChromeOptions()));
				break;
			case FIREFOX:
				WebDriverBinaryDownloader.create().downloadLatestBinaryAndConfigure(BrowserType.FIREFOX);
				webDriver.set(new FirefoxDriver(getFireFoxOptions()));
				break;
		}
	}

	public WebDriver getDriver() {
		return this.webDriver.get();
	}

	/**
	 * @author sgupta
	 * @return ChromeOptions
	 */
	private ChromeOptions getChromeOptions(){
		ChromeOptions options = new ChromeOptions();
		return options;
	}

	/**
	 * @author sgupta
	 * @return FirefoxOptions
	 */
	private FirefoxOptions getFireFoxOptions(){
		FirefoxOptions options = new FirefoxOptions();
		return options;
	}
}

