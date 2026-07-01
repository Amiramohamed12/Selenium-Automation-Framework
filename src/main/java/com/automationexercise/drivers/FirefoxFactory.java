package com.automationexercise.drivers;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class FirefoxFactory extends AbstractDriver {

        private FirefoxOptions getOptions(){
            FirefoxOptions options=new FirefoxOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            Map<String, Object> prefs = new HashMap<>();
            String userDir = System.getProperty("user.dir");
            String downloadPath = userDir + "\\src\\test\\resources\\downloads";
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            options.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);
            options.setAcceptInsecureCerts(true);
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            if(PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")||
                    PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote"))
                options.addArguments("--headless");
            options.addArguments("--disable-extensions");
            return options;

        }
        public WebDriver createDriver() {
            if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")||
                    PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadlees"))
            return new FirefoxDriver(getOptions());

            else if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")){
                try {
                    return new RemoteWebDriver(
                            new URI("http://" + remoteHost + ":" + remotePort + "/wd/hub").toURL(), getOptions()
                    );
                } catch (Exception e) {
                    LogsManager.error("Error creating RemoteWebDriver: " , e.getMessage());
                    throw new RuntimeException("Failed to create RemoteWebDriver", e);
                }
            }

            else{
                LogsManager.error("Invalid execution type");
                throw new IllegalArgumentException("Invalid execution type: " + PropertyReader.getProperty("executionType"));
            }
        }
}
