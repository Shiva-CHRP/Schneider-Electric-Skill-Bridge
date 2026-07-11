package schneider.reports;

import org.openqa.selenium.BuildInfo;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import schneider.utils.ConfigReader;

public class ExtentReportNG {

	private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            spark.config().setReportName("Skill Bridge Automation Report");
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setTimelineEnabled(true);
            spark.config().setEncoding("UTF-8");
            

            extent = new ExtentReports();
            extent.setReportUsesManualConfiguration(false);
            extent.setAnalysisStrategy(AnalysisStrategy.TEST);
            extent.attachReporter(spark);
            extent.setSystemInfo("Project",ConfigReader.get("project"));
            extent.setSystemInfo("Release",
                    ConfigReader.get("release"));
            extent.setSystemInfo("Environment",
                    ConfigReader.get("environment"));
            extent.setSystemInfo("OS",
                    System.getProperty("os.name"));
            extent.setSystemInfo("Java",
                    System.getProperty("java.version"));
            BuildInfo buildInfo = new BuildInfo();
            extent.setSystemInfo("Selenium Version", buildInfo.getReleaseLabel());
            extent.setSystemInfo("Framework",
                    ConfigReader.get("framework"));
            extent.setSystemInfo("Executed By",
                    ConfigReader.get("tester"));
            
            
            
            //extent.setSystemInfo("Tester", "QC Shiva");
        }
        return extent;
    }
}
