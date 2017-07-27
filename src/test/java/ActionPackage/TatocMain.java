package ActionPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

import utility.SpecsReader;

public class TatocMain {
	WebDriver driver;
	public SpecsReader locReader;

	public TatocMain() throws IOException {
		locReader = new SpecsReader("locators");
	}

	public WebDriver launchUrl() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\\\\\\\Users\\\\manusharma\\\\Downloads\\\\chromedriver_win32\\\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("http://10.0.1.86/tatoc");
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		return driver;
	}

	public void clickOnGreenBox() {
		driver.findElement(locReader.getWebElement("green_box")).click();
		Reporter.log("click on green box icon");
	}

	public void Frame_Dungeon() {
		driver.switchTo().frame("main");
		Reporter.log("driver switch to main frame");
		String classValue = driver.findElement(locReader.getWebElement("box")).getAttribute("class");
		while (true) {
			driver.switchTo().frame("child");
			Reporter.log("driver switch to child frame");
			WebElement element = driver.findElement(locReader.getWebElement("box"));
			if (element.getAttribute("class").equals(classValue)) {
				driver.switchTo().parentFrame();
				Reporter.log("driver switch to parent frame");
				driver.findElement(locReader.getWebElement("proceed")).click();
				Reporter.log("click on proceed buttton");
				driver.switchTo().defaultContent();
				break;
			} else
				driver.switchTo().parentFrame();
			driver.findElement(locReader.getWebElement("repaint")).click();
			Reporter.log("click on repaint box 2 ");
		}

	}

	public void dragAround() {
		WebElement source = driver.findElement(locReader.getWebElement("drag_box"));
		WebElement target = driver.findElement(locReader.getWebElement("drop_box"));
		Actions action = new Actions(driver);
		action.dragAndDrop(source, target).build().perform();
		Reporter.log("drag drag_box to drop_box");
		driver.findElement(locReader.getWebElement("proceed")).click();
		Reporter.log("click on proceed button");
	}

	public void popupWindow() {
		String parentWindow = driver.getWindowHandle();
		driver.findElement(locReader.getWebElement("launchWindow")).click();
		Reporter.log("click on launch window");
		ArrayList<String> list = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(list.get(1));
		Reporter.log("driver switch to child window");
		driver.findElement(locReader.getWebElement("text_Box")).sendKeys("hii all");
		Reporter.log("send keys to input text_box");
		driver.findElement(locReader.getWebElement("submit_button")).click();
		Reporter.log("click on submit_button");
		driver.switchTo().window(parentWindow);
		Reporter.log("driver switch to parent window");
		driver.findElement(locReader.getWebElement("proceed")).click();
		Reporter.log("click on proceed button");
	}

	public void setCookies() {
		driver.findElement(locReader.getWebElement("generate_Token")).click();
		Reporter.log("click on generate token");
		String token = driver.findElement(locReader.getWebElement("token")).getText();
		Reporter.log("get text from token");
		String[] dataToken = token.split(":");
		Cookie cookie = new Cookie(dataToken[0].trim(), dataToken[1].trim());
		driver.manage().addCookie(cookie);
		Reporter.log("add cookies");
		driver.findElement(locReader.getWebElement("proceed")).click();
		Reporter.log("click on proceed button");
	}
}
