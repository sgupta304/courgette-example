package suites.Setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {
  private Properties propFile;

  public Properties getPropFile() {
    return propFile;
  }

  public ConfigFileReader(String fileName) {
    openAndReadPropFile(fileName);
  }

  private void openAndReadPropFile(String fileName) {
    propFile = new Properties();
    try (InputStream input = new FileInputStream(fileName + ".properties")) {
      propFile.load(input);
    } catch (IOException e) {
      System.out.println("ERROR");
    }
  }
}
