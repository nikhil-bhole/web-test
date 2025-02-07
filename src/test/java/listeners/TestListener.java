package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        // This is called before a test method starts
        Reporter.log("Test Started: " + result.getName(), true);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // This is called when a test method is passed
        Reporter.log("Test Passed: " + result.getName(), true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // This is called when a test method fails
        Reporter.log("Test Failed: " + result.getName(), true);
        Reporter.log("Error: " + result.getThrowable(), true);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // This is called when a test method is skipped
        Reporter.log("Test Skipped: " + result.getName(), true);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // This is called when a test fails but within the success percentage
        Reporter.log("Test Failed but within Success Percentage: " + result.getName(), true);
    }

    @Override
    public void onStart(ITestContext context) {
        // This is called before any test method is run
        Reporter.log("Test Suite Started: " + context.getName(), true);
    }

    @Override
    public void onFinish(ITestContext context) {
        // This is called after all test methods have run
        Reporter.log("Test Suite Finished: " + context.getName(), true);
    }
}
