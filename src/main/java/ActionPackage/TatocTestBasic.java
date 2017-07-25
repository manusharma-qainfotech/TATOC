package ActionPackage;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class TatocTestBasic {
  WebDriver driver;
  @BeforeClass
  public void beforeClass() throws IOException {
		TatocMain main = new TatocMain();
       driver =  main.launchUrl();
  }

  
  @AfterClass
  public void afterClass() {
  driver.quit();
  }

}
