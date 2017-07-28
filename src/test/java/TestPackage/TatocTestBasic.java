package TestPackage;

import org.testng.annotations.Test;

import ActionPackage.TatocMain;
import ActionPackage.Tatoc_Basic_JS;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class TatocTestBasic {
	WebDriver driver;
	//TatocMain main;
	Tatoc_Basic_JS main;

	@BeforeClass
	public void beforeClass() throws IOException {
	//	main = new TatocMain();
		main = new Tatoc_Basic_JS();
		driver = main.launchUrl();
	}

	@Test(priority = 0)
	public void testGreebBox() throws InterruptedException {
		assertEquals(driver.getTitle(), "Welcome - T.A.T.O.C");
		driver.findElement(main.locReader.getWebElement("basic_course")).click();
		Reporter.log("click on basic_course");
		assertEquals(driver.getTitle(), "Grid Gate - Basic Course - T.A.T.O.C");
		main.clickOnGreenBox();
		assertEquals(driver.getTitle(), "Frame Dungeon - Basic Course - T.A.T.O.C");
	}

	@Test(priority = 1)
	public void testFrameDungeon() {
		main.Frame_Dungeon();
		assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
	}

	@Test(priority = 2)
	public void testDragAround() {
		main.dragAround();
		assertEquals(driver.getTitle(), "Windows - Basic Course - T.A.T.O.C");
	}

	@Test(priority = 3)
	public void testPopUpWindow() throws InterruptedException {
		main.popupWindow();
		assertEquals(driver.getTitle(), "Cookie Handling - Basic Course - T.A.T.O.C");
	}

	@Test(priority = 4)
	public void testCookies() throws InterruptedException {
		main.setCookies();
		assertEquals(driver.getTitle(), "End - T.A.T.O.C");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
