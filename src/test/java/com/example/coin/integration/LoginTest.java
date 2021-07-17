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
public class LoginTest {
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
    public void loginTest(){
        Assert.assertEquals("Test",browser.findElement(By.xpath("//*[@id=\"user\"]/div/i")).getText());
    }

}
