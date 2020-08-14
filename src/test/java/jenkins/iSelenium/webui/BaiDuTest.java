package jenkins.iSelenium.webui;

import jenkins.iSelenium.base.WebUIBase;
import jenkins.iSelenium.base.WebUITasks;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @program: JavaProject
 * @description:
 * @author: bjwuzh
 * @create: 2020-08-14 14:51
 **/
public class BaiDuTest extends WebUIBase {
    private Logger logger = Logger.getLogger(BaiDuTest.class);


    @Test()
    void baiduSearch01Test() throws Exception {
        String checkString = "今日头条";
        baiduSearch(checkString);
    }
    @Test()
    void baiduSearch02Test() throws Exception {
        String checkString = "软件测试";
        baiduSearch(checkString);
    }

    private void baiduSearch(String checkString) throws Exception {
        testcaseName = "Baidu search UI automation test.";
        logger.info("Start the automation test: " + testcaseName);

        //浏览器中打开百度
        logger.info("Open the www.baidu.com");
        navigation.to("http://www.baidu.com");
        wait2s();

        WebUITasks.inputText(checkString, driver);
        wait2s();

        WebUITasks.clickSearchBtn(driver);
        wait2s();

        String browserTitle = driver.getTitle();
        Assertions.assertTrue(browserTitle.contains(checkString));

    }
}