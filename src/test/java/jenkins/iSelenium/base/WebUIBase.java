package jenkins.iSelenium.base;


import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @program: JavaProject
 * @description:
 * @author: bjwuzh
 * @create: 2020-08-14 13:19
 **/
public abstract class WebUIBase {
    private Logger logger = Logger.getLogger(WebUIBase.class);
    private String propFileName = "iselenium.properties";

    protected String testcaseName = "";
    protected String curBrowser = "firefox"; //默认浏览器是Firefox
    protected WebDriver driver;
    protected WebDriver.Navigation navigation;
    protected String firefoxPath = "";
    protected String chromePath = "";

    protected int waitTime = 15;

    @BeforeEach
    private void begin(){
        //加载配置文件
        logger.info("Load properties file: " + propFileName);
        Properties prop = loadFromEnvProperties(propFileName);

        //获取浏览器driver路径
        logger.info("Loadd webdriver path");
        firefoxPath = prop.getProperty("FIREFOX_PATH");
        chromePath = prop.getProperty("CHROME_PATH");
        logger.info("firefoxPath = " + firefoxPath);
        logger.info("chromePath = " + chromePath);

        //设定当前运行的浏览器
        //需要在环境变量currentBrowser中配置当前运行什么浏览器，可选“firefox、chrome、nogui”
        setCurBrowser();
        System.out.println("当前浏览器是：" + curBrowser);
        //构造webdriver
        if (curBrowser.equalsIgnoreCase("firefox")){
            //System.setProperty("webdriver.firefox.bin", firefoxPath);
            //System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            driver = new FirefoxDriver();
        } else if (curBrowser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver",chromePath);
            driver = new ChromeDriver();
        } else if (curBrowser.equalsIgnoreCase("nogui")){
            System.setProperty("webdriver.chrome.driver",chromePath);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");//无界面
            driver = new ChromeDriver(chromeOptions);
        } else {
            System.setProperty("webdriver.firefox.bin", firefoxPath);
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            driver = new FirefoxDriver();
        }
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        timeouts.setScriptTimeout(waitTime, TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(waitTime, TimeUnit.SECONDS);
        timeouts.implicitlyWait(waitTime, TimeUnit.SECONDS);

        navigation = driver.navigate();
    }

    @AfterEach
    public void tearDown(){
        logger.info("Automation test " + testcaseName + " finish!");
        if (driver == null){
            return;
        }
        driver.quit();
    }
    private void setCurBrowser() {
        String value = System.getenv("currentBrowser");
       // 如果不指定currentBrowser，就使用默认的firefox浏览器
        if (value == null || value.equalsIgnoreCase("")){
            return;
        }
        if (value.equalsIgnoreCase("firefox") || value.equalsIgnoreCase("chrome") || value.equalsIgnoreCase("nogui")) {
            curBrowser = value.toLowerCase();
        }
    }

    //加载配置文件的方法
    //参数：文件名称
    private Properties loadFromEnvProperties(String propFileName) {
        Properties prop = null;
        //Windows的user.name：C:\Users\Administrator，所以需要把propFileName放在该目录下
        String path = System.getProperty("user.home");
        System.out.println(path);
        prop = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(
                    new FileInputStream(path + File.separator + propFileName));
                    prop.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Load config file fail, please check" + path + "to confirm if the" + propFileName + " file exist!");
        }
        return prop;
    }
    protected void wait2s() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}