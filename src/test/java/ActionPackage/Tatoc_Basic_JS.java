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

public class Tatoc_Basic_JS {

		WebDriver driver;
		public SpecsReader locReader;
		JavascriptExecutor js;

		public Tatoc_Basic_JS() throws IOException {
			locReader = new SpecsReader("locators");
		}

		public WebDriver launchUrl() {
			System.setProperty("webdriver.chrome.driver",
					"C:\\\\\\\\Users\\\\manusharma\\\\Downloads\\\\chromedriver_win32\\\\chromedriver.exe");
			driver = new ChromeDriver();
			js = (JavascriptExecutor) driver;
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.get("http://10.0.1.86/tatoc");
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			return driver;
		}

		public void clickOnGreenBox() {
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("green_box"))).click();
			//driver.findElement(locReader.getWebElement("green_box")).click();
			Reporter.log("click on green box icon");
		}

		public void Frame_Dungeon() {
			driver.switchTo().frame("main");
			Reporter.log("driver switch to main frame");
			String classValue = ((WebElement)js.executeScript(locReader.getJavaScriptQuery("box"))).getAttribute("class");
			//String classValue = driver.findElement(locReader.getWebElement("box")).getAttribute("class");
			while (true) {
				driver.switchTo().frame("child");
				Reporter.log("driver switch to child frame");
				WebElement element = (WebElement)js.executeScript(locReader.getJavaScriptQuery("box"));
			//	WebElement element = driver.findElement(locReader.getWebElement("box"));
				if (element.getAttribute("class").equals(classValue)) {
					driver.switchTo().parentFrame();
					Reporter.log("driver switch to parent frame");
					((WebElement)js.executeScript(locReader.getJavaScriptQuery("proceed"))).click();
//					driver.findElement(locReader.getWebElement("proceed")).click();
					Reporter.log("click on proceed buttton");
					driver.switchTo().defaultContent();
					break;
				} else
					driver.switchTo().parentFrame();
				((WebElement)js.executeScript(locReader.getJavaScriptQuery("repaint"))).click();
				//			driver.findElement(locReader.getWebElement("repaint")).click();
				Reporter.log("click on repaint box 2 ");
			}

		}

		public void dragAround() {
			WebElement source = (WebElement)js.executeScript(locReader.getJavaScriptQuery("drag_box"));
		WebElement target = (WebElement)js.executeScript(locReader.getJavaScriptQuery("drop_box"));
//			WebElement source = driver.findElement(locReader.getWebElement("drag_box"));
	//		WebElement target = driver.findElement(locReader.getWebElement("drop_box"));
			Actions action = new Actions(driver);
			action.dragAndDrop(source, target).build().perform();
			Reporter.log("drag drag_box to drop_box");
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("proceed"))).click();
//			driver.findElement(locReader.getWebElement("proceed")).click();
			Reporter.log("click on proceed button");
		}

		public void popupWindow() {
			String parentWindow = driver.getWindowHandle();
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("launchWindow"))).click();
//			driver.findElement(locReader.getWebElement("launchWindow")).click();
			Reporter.log("click on launch window");
			ArrayList<String> list = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(list.get(1));
			Reporter.log("driver switch to child window");
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("text_Box"))).click();
//			driver.findElement(locReader.getWebElement("text_Box")).sendKeys("hii all");
			Reporter.log("send keys to input text_box");
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("submit_button"))).click();
//			driver.findElement(locReader.getWebElement("submit_button")).click();
			Reporter.log("click on submit_button");
			driver.switchTo().window(parentWindow);
			Reporter.log("driver switch to parent window");
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("proceed"))).click();
//			driver.findElement(locReader.getWebElement("proceed")).click();
			Reporter.log("click on proceed button");
		}

		public void setCookies() {
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("generate_Token"))).click();
//			driver.findElement(locReader.getWebElement("generate_Token")).click();
			Reporter.log("click on generate token");
	String token = ((WebElement)js.executeScript(locReader.getJavaScriptQuery("token"))).getText();
//			String token = driver.findElement(locReader.getWebElement("token")).getText();
			Reporter.log("get text from token");
			String[] dataToken = token.split(":");
			Cookie cookie = new Cookie(dataToken[0].trim(), dataToken[1].trim());
			driver.manage().addCookie(cookie);
			Reporter.log("add cookies");
			((WebElement)js.executeScript(locReader.getJavaScriptQuery("proceed"))).click();
	//		driver.findElement(locReader.getWebElement("proceed")).click();
			Reporter.log("click on proceed button");
		}
	}


