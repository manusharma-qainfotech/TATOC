package TestPackage;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ActionPackage.TatocAdvance;
import ActionPackage.TatocMain;
import ActionPackage.Tatoc_Adv_JS;

public class TatocTestAdvance {
	WebDriver driver;
	//TatocAdvance main;
	Tatoc_Adv_JS main;

	@BeforeClass
	public void Beforeclass() throws IOException {
		//main = new TatocAdvance();
	  main = new Tatoc_Adv_JS();
		driver = main.launchUrl();
	}

	@Test(priority = 0)
	public void testHoverMenu() throws InterruptedException {
		driver.findElement(main.locReader.getWebElement("advance")).click();
		Reporter.log("click on advance button");
		main.hoverMenu();
		System.out.println(driver.getTitle());
		assertEquals(driver.getTitle(), "Query Gate - Advanced Course - T.A.T.O.C");
	}

	@Test(priority = 1)
	public void testDatabaseQuery() throws ClassNotFoundException, SQLException, InterruptedException {
		main.databaseQuery();
		System.out.println(driver.getTitle());
		assertEquals(driver.getTitle(), "Video Player - Advanced Course - T.A.T.O.C");
	}

	@Test(priority = 2)
	public void testOoyalaPlayerD() {
		main.OoyalaPlayer();
		System.out.println(driver.getTitle());
		assertEquals(driver.getTitle(), "Restful - Advanced Course - T.A.T.O.C");
	}

	@Test(priority = 3)
	public void testRestFulApi() throws InterruptedException {
		main.RestFulApi();
		System.out.println(driver.getTitle());
		assertEquals(driver.getTitle(), "File Handle - Advanced Course - T.A.T.O.C");
	}

	@Test(priority = 4)
	public void testFileHandle() throws InterruptedException, IOException {
		main.FileHandle();
		System.out.println(driver.getTitle());
		assertEquals(driver.getTitle(), "End - T.A.T.O.C");
	}

	@AfterClass
	public void Afterclass() {
		driver.quit();
	}
}
