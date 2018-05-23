package suites.Setup;

public class EnvironmentOption {
	private String Browser;
	private String BrowserVersion;
	private String Platform;

	public EnvironmentOption(String browser, String browserVersion, String platform) {
		Browser = browser;
		BrowserVersion = browserVersion;
		Platform = platform;
	}

	public String getBrowser() {
		return Browser;
	}

	public void setBrowser(String browser) {
		Browser = browser;
	}

	public String getBrowserVersion() {
		return BrowserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		BrowserVersion = browserVersion;
	}

	public String getPlatform() {
		return Platform;
	}

	public void setPlatform(String platform) {
		Platform = platform;
	}
}
