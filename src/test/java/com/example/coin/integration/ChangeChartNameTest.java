package com.example.coin.integration;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class ChangeChartNameTest {
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
    public void changeChartNameTest(){
        String afterChangeName="afterChangeName";
        browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[2]/div/div/div/button[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"sideMenu\"]/ul/li[3]/div")).click();
        browser.findElement(By.xpath("//*[@id=\"sideMenu\"]/ul/li[3]/ul/li/ul/li[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"chartNameChange\"]/div/div/div[2]/div/input")).clear();
        browser.findElement(By.xpath("//*[@id=\"chartNameChange\"]/div/div/div[2]/div/input")).sendKeys(afterChangeName);
        browser.findElement(By.xpath("//*[@id=\"chartNameChange\"]/div/div/div[3]/span/button[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"sideMenu\"]/ul/li[3]/ul/li/ul/li[1]")).click();
        String chartName=browser.findElement(By.xpath("//*[@id=\"chartNameChange\"]/div/div/div[2]/div/input")).getAttribute("placeholder");
        Assert.assertEquals(afterChangeName,chartName);
    }
}
