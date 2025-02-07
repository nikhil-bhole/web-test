package listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.driver.DriverFactory;
import org.testng.IReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class JsonReporter implements IReporter, ITestListener {
    private static final String REPORT_DIR = "test-reports";
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final Queue<Map<String, Object>> testResults = new ConcurrentLinkedQueue<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final ReentrantLock lock = new ReentrantLock();
    WebDriver driver = DriverFactory.getInstance().getDriver();

    static {
        new File(REPORT_DIR).mkdirs();
        new File(SCREENSHOT_DIR).mkdirs();
    }

    @Override
    public void onTestStart(ITestResult result) {}

    @Override
    public void onTestSuccess(ITestResult result) {
        saveTestResult(result, "PASSED", null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = takeScreenshot(result.getName());
        saveTestResult(result, "FAILED", screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        saveTestResult(result, "SKIPPED", null);
    }

    @Override
    public void onFinish(ITestContext context) {
        generateJsonReport(context.getSuite().getName());
    }

    private void saveTestResult(ITestResult result, String status, String screenshotPath) {
        Map<String, Object> testDetails = new HashMap<>();
        testDetails.put("testcaseId", result.getMethod().getDescription());
        testDetails.put("productGroup", getTestGroup(result));
        testDetails.put("priority", getPriority(result));
        testDetails.put("packageName", result.getTestClass().getName());
        testDetails.put("className", result.getTestClass().getRealClass().getSimpleName());
        testDetails.put("methodName", result.getMethod().getMethodName());
        testDetails.put("status", status);
        testDetails.put("runtime", (result.getEndMillis() - result.getStartMillis()) / 1000.0 + "s");
        testDetails.put("suiteName", result.getTestContext().getSuite().getName());
        if (status.equals("FAILED")) {
            testDetails.put("failureReason", result.getThrowable().getMessage());
            testDetails.put("failureLine", getFailureLine(result.getThrowable()));
            testDetails.put("stackTrace", Arrays.toString(result.getThrowable().getStackTrace()));
            testDetails.put("screenshot", screenshotPath);
        }
        testResults.add(testDetails);
    }

    private void generateJsonReport(String suiteName) {
        lock.lock();
        try {
            String fileName = REPORT_DIR + "/test_report_" + suiteName + "_" + System.currentTimeMillis() + ".json";
            try (FileWriter writer = new FileWriter(fileName)) {
                gson.toJson(testResults, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    private String takeScreenshot(String testName) {
        if (driver == null) return null;
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = SCREENSHOT_DIR + "/" + testName + "_" + System.currentTimeMillis() + ".png";
        try {
            Files.copy(src.toPath(), Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private int getFailureLine(Throwable throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains("com.example")) {
                return element.getLineNumber();
            }
        }
        return -1;
    }

    private String getTestGroup(ITestResult result) {
        return result.getMethod().getGroups().length > 0 ? result.getMethod().getGroups()[0] : "General";
    }

    private String getPriority(ITestResult result) {
        String priority = "P2";
        if (result.getMethod().getDescription() != null) {
            if (result.getMethod().getDescription().contains("P0")) priority = "P0";
            else if (result.getMethod().getDescription().contains("P1")) priority = "P1";
        }
        return priority;
    }


}

