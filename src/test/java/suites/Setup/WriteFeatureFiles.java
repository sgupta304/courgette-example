package suites.Setup;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import gherkin.events.PickleEvent;
import org.testng.annotations.Test;
import suites.Setup.ConfigFileReader;
import suites.Setup.EnvironmentOption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@CucumberOptions(
				tags = {"@regression"},
				features = "src/test/resources/features"
)
public class WriteFeatureFiles {
	private TestNGCucumberRunner testNGCucumberRunner;
	private Object[][] scenarios;
	private String PRESENT_WORKING_DIRECTORY = System.getProperty("user.dir");

	@Test
	public void test() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		writeFeaturesToBuildDirectory();
		testNGCucumberRunner.finish();
	}

	private List<EnvironmentOption> getEnvOption() {
		String configFile = "/src/test/resources/Properties/config";
		List<EnvironmentOption> options = new ArrayList<>();
		ConfigFileReader cReader = new ConfigFileReader(PRESENT_WORKING_DIRECTORY+configFile);
		String envNum = cReader.getPropFile().getProperty("RunEnvrionmentOption");
		String[] envCombo = cReader.getPropFile().getProperty("EnvironmentOption_" + envNum).split(",");
		for (String combo : envCombo) {
			String[] option = cReader.getPropFile().getProperty(combo).split("\\|");
			options.add(new EnvironmentOption(option[1], option[2], option[0]));
		}
		return options;
	}

	private void writeFeaturesToBuildDirectory() {
		scenarios = testNGCucumberRunner.provideScenarios();
		List<EnvironmentOption> options = getEnvOption();
		for (int i = 0; i <= scenarios.length - 1; i++) {
			PickleEvent scenario = ((PickleEventWrapper) scenarios[i][0]).getPickleEvent();
			for (EnvironmentOption option : options) {
				String fName =
								String.format(
												"%s_%s_%s_%s.feature",
												scenario.uri.split("/")[4].replace(".feature", ""),
												option.getPlatform(),
												option.getBrowser(),
												option.getBrowserVersion());
				writeScenarioToFile(
								readAndModifyScenario(scenario, option), "/tmp/" + fName.replaceAll(" ", "_"));
			}
		}
	}

	private String readAndModifyScenario(PickleEvent scenario, EnvironmentOption option) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader =
						     Files.newBufferedReader(Paths.get(PRESENT_WORKING_DIRECTORY + "/" + scenario.uri))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Scenario")) {
					sb.append("Scenario: ")
									.append(scenario.pickle.getName())
									.append(" on ")
									.append(
													String.format(
																	"%s %s %s",
																	option.getBrowser(), option.getBrowserVersion(), option.getPlatform()))
									.append("\n");
				} else {
					sb.append(line).append("\n");
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		return sb.toString();
	}

	private void writeScenarioToFile(String content, String fileName) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
			writer.write(content);
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
}
