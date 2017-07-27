package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import models.locators;

public class SpecsReader {
	List<locators> listcred;

	public SpecsReader(String FileName) throws IOException {
		String path = System.getProperty("user.dir");
		FileReader reader = new FileReader(new File(path + "\\src\\test\\resources\\"+FileName+".spec"));

		BufferedReader bReader = new BufferedReader(reader);
		String line;
		listcred = new ArrayList<locators>();
		while ((line = bReader.readLine()) != null) {
			StringTokenizer tokens = new StringTokenizer(line, "%");
			if (tokens.countTokens() == 3) {
				String elementName = tokens.nextElement().toString().trim();
				String type = tokens.nextElement().toString().trim();
				String locators = tokens.nextElement().toString().trim();
				locators obj = new locators(elementName, type, locators);
				listcred.add(obj);
			}
		}

	}

	public  By getWebElement(String elementName) {
		locators loc = null;
		Iterator<locators> itr = listcred.iterator();
		int flag = 0;
		while (itr.hasNext()) {
			loc = itr.next();
			String a = elementName;
			String b = loc.element;
			if (a.equalsIgnoreCase(b)) {
				break;
			}
		}
		WebElement element;
		String type = loc.locatorType;
		if (type.equals("css")) 
      return By.cssSelector(loc.locatorValue);
		 else 
			return By.xpath(loc.locatorValue);

	}
	public String getJavaScriptQuery(String elementName)
	{
		locators loc = null;
		String query ="";
		Iterator<locators> itr = listcred.iterator();
		int flag = 0;
		while (itr.hasNext()) {
			loc = itr.next();
			String a = elementName;
			String b = loc.element;
			if (a.equalsIgnoreCase(b)) {
				break;
			}
		}
		WebElement element;
		String type = loc.locatorType;
		if (type.equals("css")) 
		{
			query = "return document.evaluate( \""+loc.locatorValue+"\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;";
			return query;			
		}
		 else 
		 {  query = "return document.evaluate( \""+loc.locatorValue+"\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;";
			return query;
		 }
	}
}
