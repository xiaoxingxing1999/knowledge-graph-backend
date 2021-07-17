package com.example.coin.integration;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class DeleteChartTest {
    private static ChromeDriver browser;
    @BeforeClass
    public static void before(){
        Configuration.openBrowser();
        browser=Configuration.getBrowser();
        browser.get(Configuration.getUrl());
        Configuration.login();
    }
    @AfterClass
    public static void after(){
        Configuration.closeBrowser();
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChartTest() throws InterruptedException {
        browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[5]/div/div/div/button[2]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[3]/button[2]")).click();
        Thread.sleep(1000);
        String nextChartName=browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[5]/div/div/span")).getText();
        Assert.assertEquals("知识图谱03",nextChartName);
    }
}
