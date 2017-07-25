package ActionPackage;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import utility.SpecsReader;

public class TatocAdvance {
	WebDriver driver;
	SpecsReader locReader;

	public TatocAdvance() throws IOException {
		locReader = new SpecsReader("advlocators");
	}

	public static void main(String[] args)
			throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		TatocAdvance main = new TatocAdvance();
		main.navigateToUrl();
		main.hoverMenu();
		main.databaseQuery();
	}

	public void navigateToUrl() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\\\\\\\Users\\\\manusharma\\\\Downloads\\\\chromedriver_win32\\\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc");
		Reporter.log("navigate to url");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

	}

	public void hoverMenu() throws InterruptedException {
		driver.findElement(locReader.getWebElement("advance")).click();
		Reporter.log("click on advance button");
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locReader.getWebElement("menu"))).perform();
		Reporter.log("Hover on menu option");
		driver.findElement(locReader.getWebElement("next")).click();
		Reporter.log("click on go next option");
	}

	public void databaseQuery() throws SQLException, ClassNotFoundException, InterruptedException {
		String name = "";
		String passKey = "";
		String symbol1 = "";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://10.0.1.86:3306/tatoc", "tatocuser", "tatoc01");
		Reporter.log("database connection established");
		Statement statement = (Statement) con.createStatement();
		String symbol = driver.findElement(locReader.getWebElement("symbol")).getText().trim();
		System.out.println(symbol);
		/*
		 * ResultSet set = statement.executeQuery("select *  from identity;");
		 * while (set.next()) { if (set.getString(2).equalsIgnoreCase(symbol)) {
		 * symbol1 = set.getString(2); break; } } set.close();
		 */
		ResultSet set1 = statement.executeQuery("Select * from credentials;");
		while (set1.next()) {
			if (symbol.startsWith(set1.getString(2))) {
				name = set1.getString(2);
				passKey = set1.getString(3);
			}
		}

		set1.close();
		driver.findElement(locReader.getWebElement("name")).sendKeys(name);
		Reporter.log("send keys to input name");
		driver.findElement(locReader.getWebElement("passkey")).sendKeys(passKey);
		Reporter.log("send keys to input pass key");
		driver.findElement(locReader.getWebElement("proceed")).click();
		Reporter.log("click on proceed button");
		con.close();
		Thread.sleep(3000);
		driver.quit();
	}
}
