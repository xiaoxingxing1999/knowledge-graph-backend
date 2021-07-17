package com.example.coin.integration;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class FuseChartTest {
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
    public void fuseChartTest(){
        browser.findElement(By.xpath("//*[@id=\"app\"]/div/section/aside/div[4]/button")).click();
        browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[3]/div/div/div/label")).click();
        browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[2]/div/div/div/label")).click();
        browser.findElement(By.xpath("//*[@id=\"confirmMergeChart\"]/button[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"sideMenu\"]/ul/li[3]/div")).click();
        browser.findElement(By.xpath("//*[@id=\"sideMenu\"]/ul/li[3]/ul/li/ul/li[1]")).click();
        String chartName=browser.findElement(By.xpath("//*[@id=\"chartNameChange\"]/div/div/div[2]/div/input")).getAttribute("placeholder");
        Assert.assertEquals("知识图谱",chartName);

    }
}
