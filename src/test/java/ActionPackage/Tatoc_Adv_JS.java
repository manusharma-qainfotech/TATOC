	package ActionPackage;

	import java.sql.Statement;
	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
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

	import io.restassured.RestAssured;
	import io.restassured.http.ContentType;
	import io.restassured.path.json.JsonPath;

	import org.json.simple.JSONObject;
	import io.restassured.response.Response;
	import utility.SpecsReader;

public class Tatoc_Adv_JS {

		WebDriver driver;
		public SpecsReader locReader;

		public Tatoc_Adv_JS() throws IOException {
			locReader = new SpecsReader("advlocators");
		}

		public WebDriver launchUrl() {
			System.setProperty("webdriver.chrome.driver",
					"C:\\\\\\\\Users\\\\manusharma\\\\Downloads\\\\chromedriver_win32\\\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.get("http://10.0.1.86/tatoc");
			Reporter.log("navigate to url");
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			return driver;
		}

		public void hoverMenu() throws InterruptedException {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(locReader.getWebElement("menu"))).perform();
			Reporter.log("Hover on menu option");
			driver.findElement(locReader.getWebElement("next")).click();
			Reporter.log("click on go next option");
			Thread.sleep(2000);
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
		}

		public void OoyalaPlayer() {
			driver.get("http://10.0.1.86/tatoc/advanced/rest/#");
		}

		public void RestFulApi() throws InterruptedException {
			String data = driver.findElement(locReader.getWebElement("session_id")).getText();
			String[] data1 = data.split(": ");
			String signature = RestAssured.when().get("http://10.0.1.86/tatoc/advanced/rest/service/token/" + data1[1])
					.jsonPath().getString("token");
			RestAssured.given().given().parameters("id", data1[1], "signature", signature, "allow_access", 1).when()
					.post("http://10.0.1.86/tatoc/advanced/rest/service/register").then().assertThat().statusCode(200);
			driver.findElement(locReader.getWebElement("proceed1")).click();
			Thread.sleep(3000);
		}

		public void FileHandle() throws InterruptedException, IOException {
			driver.findElement(locReader.getWebElement("download")).click();
			Thread.sleep(6000);
			FileReader reader = new FileReader("C:\\Users\\manusharma\\Downloads\\file_handle_test.dat");
			BufferedReader breader = new BufferedReader(reader);
			String line = " ";
			String signature = "";
			while ((line = breader.readLine()) != null) {
				String[] dd = line.split(":");
				if (dd[0].trim().equals("Signature"))
					signature = dd[1].trim();
			}
			driver.findElement(locReader.getWebElement("signature")).sendKeys(signature);
			driver.findElement(locReader.getWebElement("proceed2")).click();
		}

	}


