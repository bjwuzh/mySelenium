package jenkins.iSelenium.webui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @program: JavaProject
 * @description:
 * @author: bjwuzh
 * @create: 2020-08-14 15:08
 **/
public class domeTest {
    @Test
    void test(){
        System.setProperty("webdriver.firefox.bin","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://www.baidu.com");
    }
    @Test
    void test2(){
        System.setProperty("webdriver.chrome.driver","D:\\\\driver\\\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.baidu.com");
    }
}