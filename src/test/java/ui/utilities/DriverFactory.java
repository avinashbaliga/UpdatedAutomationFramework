package ui.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import commonUtilities.ProjectProperties;
import commonUtilities.ScenarioContext;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.*;
import io.cucumber.plugin.event.Result;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static ExtentReporter extentReporter;
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private ScreenshotUtils screenshotUtils = new ScreenshotUtils();

    @BeforeAll
    public static void initiateReporting(){
        extentReporter = new ExtentSparkReporter("target/report/report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter((ExtentObserver) extentReporter);
    }

    @Before
    public void setUp(Scenario scenario){
        WebDriver driver = initiateDriver();
        driverThreadLocal.set(driver);
        extentTestThreadLocal.set(extentReports.createTest(scenario.getName()));
    }


    @AfterStep
    public void afterStep(Scenario scenario){
        writeToReport(ScenarioContext.get("stepName").toString(), "INFO");
        if(scenario.isFailed()){
            writeToReport(ScenarioContext.get("stepName").toString(), "FAILED");
            writeToReport(getFailureMessage(scenario), "FAILED");
        }
        else{
            writeToReport(ScenarioContext.get("stepName").toString(), "PASSED");
        }
        extentTestThreadLocal.get().addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(screenshotUtils.getScreenshot(driverThreadLocal.get())), ScenarioContext.get("stepName").toString());
    }

    @After
    public void tearDown(){
        driverThreadLocal.get().close();
        driverThreadLocal.get().quit();
    }

    @AfterAll
    public static void flushReport(){
        extentReports.flush();
    }

    private WebDriver initiateDriver(){
        String browser = ProjectProperties.getProperty("browser");
        WebDriver driver;

        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
        }

        return driver;
    }

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    public static void writeToReport(String message, String status){
        switch (status){
            case "FAILED":
                extentTestThreadLocal.get().fail("Step failed<p style='color:red'>"+message+"</p>");
                break;
            case "PASSED":
                extentTestThreadLocal.get().pass("Step passed<p style='color:green'>"+message+"</p>");
                break;
            default:
                extentTestThreadLocal.get().info("Info<p style='color:blue'>"+message+"</p>");
                break;
        }
    }

    public String getFailureMessage(Scenario scenario) {
        Result failResult = null;

        try {
            // Get the delegate from the scenario
            Field delegate = scenario.getClass().getDeclaredField("delegate");
            delegate.setAccessible(true);
            TestCaseState testCaseState = (TestCaseState) delegate.get(scenario);

            // Get the test case results from the delegate
            Field stepResults = testCaseState.getClass().getDeclaredField("stepResults");
            stepResults.setAccessible(true);
            List<Result> results = (List<Result>) stepResults.get(testCaseState);

            for(Result result : results) {
                if(result.getStatus().name().equalsIgnoreCase("FAILED")) {
                    failResult = result;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (failResult != null) ? failResult.getError().getMessage() : "";
    }
}
