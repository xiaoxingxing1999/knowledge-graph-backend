package com.example.coin.integration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Configuration {
    private static ChromeDriver browser;

    private static String url="http://localhost:80";

    private static String mail="123@qq.com";

    private static String password="123123";
    //记得需要的时候修改
    private static String driverUrl="E:\\Ricky\\ChromeDriver\\chromedriver.exe";

    public static void openBrowser(){
        System.setProperty("webdriver.chrome.driver",driverUrl);
        browser=new ChromeDriver();
        browser.manage().deleteAllCookies();
        browser.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
        login();
    }

    public static void closeBrowser(){browser.quit();}

    public static void login(){
        browser.get(url);
        browser.findElement(By.xpath("//*[@id=\"loginBox\"]/button[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/section/main/div/div[2]/div/div/form/div[1]/div/div/input")).sendKeys(mail);
        browser.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/section/main/div/div[2]/div/div/form/div[2]/div/div/input")).sendKeys(password);
        browser.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/section/main/div/div[2]/div/div/form/div[3]/div/button[1]")).click();
    }

    public static ChromeDriver getBrowser() {
        return browser;
    }

    public static void setBrowser(ChromeDriver browser) {
        Configuration.browser = browser;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Configuration.url = url;
    }
}
