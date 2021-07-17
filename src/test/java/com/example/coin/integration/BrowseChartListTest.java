package com.example.coin.integration;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class BrowseChartListTest {
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
    public void browseChartListTest(){
        Assert.assertEquals("知识图谱01",browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[1]/div/div/span")).getText());
        Assert.assertEquals("01",browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[2]/div/div/span")).getText());
        Assert.assertEquals("02",browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[3]/div/div/span")).getText());
        Assert.assertEquals("知识图谱",browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[4]/div/div/span")).getText());
    }


}
