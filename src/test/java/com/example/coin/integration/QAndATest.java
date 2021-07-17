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
public class QAndATest {
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
    public void qAndATest(){
        String expectedAnswer="乙肝，发病人群有所有人群、青少年。";
        browser.findElement(By.xpath("//*[@id=\"chartList\"]/div/div[2]/div/div/div/button[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"chatBox\"]/div/div/div[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"chatBox\"]/div/div/div[2]/div[3]/form/div[1]")).sendKeys("乙肝的发病人群");
        browser.findElement(By.xpath("//*[@id=\"chatBox\"]/div/div/div[2]/div[3]/form/div[2]/div[2]")).click();
        String answer=browser.findElement(By.xpath("//*[@id=\"chatBox\"]/div/div/div[2]/div[2]/div[3]/div/div[2]/p")).getText();
        Assert.assertEquals(expectedAnswer,answer);
    }
}
