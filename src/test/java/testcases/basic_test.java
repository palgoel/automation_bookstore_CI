package testcases;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class basic_test{
static ExtentReports report;
public static ExtentTest logger;
public WebDriver driver;
String methodname="";

@BeforeSuite
public void setup() {
	report = new ExtentReports("./Reports/extentreport.html");
}


@BeforeClass
public void setbrowser() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("http://localhost:9090/rps/");
	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		
}
@BeforeMethod
public void method_name(Method method) { //to get name of method of test
	methodname = method.getName();
	logger = report.startTest(methodname);
}

@Test
public void testurl() {
	logger.setDescription("This test is to check the current URL");
	String cururl= driver.getCurrentUrl();
	assertTrue(cururl.contains("rps"));
}
@Test
public void testtitle() {
	logger.setDescription("This test is to check the page title");
	String title= driver.getTitle();
	assertTrue(title.contains("Book"));
}

@Test
public void page_handle() {
	String winhdl = driver.getWindowHandle();
	assertTrue(winhdl != null);
}

@AfterMethod
public void tear(ITestResult result) {
	if (result.getStatus()==ITestResult.FAILURE) {
		logger.log(LogStatus.FAIL,logger.getTest().getName()+" is failed"+ logger.addScreenCapture("./download.jpg"));
	}
	
	if (result.getStatus()==ITestResult.SUCCESS) {
		logger.log(LogStatus.PASS, logger.getTest().getName()+" is passed");
	}
	report.flush();
}
@AfterClass
public void teardown() {
	driver.quit();

}
@AfterSuite
public void stop_test() {
	  report.endTest(logger);
}
}