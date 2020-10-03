import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class BaseTest {
    WebDriver driver;

    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }

    @Before // warunki poczatkowe
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Łukasz\\Desktop\\Chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test // kroki testowe
    public void firstTest(){
        String devToUrl = "https://dev.to";
        driver.get(devToUrl);
        WebElement week = driver.findElement(By.xpath("//a[@href='/top/week']"));
        //highlightElement(driver,week);
        week.click();
        WebDriverWait wait=new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe("https://dev.to/top/week")); // zanim zaczniesz szukac poczekaj az url bedzie mial wartosc jak w cudzysłowiu
       // highlightElement(driver,firstPostOnWeek);

        WebElement firstPostOnWeek = driver.findElement(By.cssSelector("h2.crayons-story__title > a"));
        //highlightElement(driver,firstPostOnWeek);
        String firstPostOnWeekText = firstPostOnWeek.getText();
        String firstPostLink = firstPostOnWeek.getAttribute("href");
        firstPostOnWeek.click();
        wait.until(ExpectedConditions.urlToBe(firstPostLink));
        WebElement postTitle = driver.findElement(By.cssSelector("div.crayons-article__header__meta > h1"));
        //highlightElement(driver,postTitle);
        String postUrl = driver.getCurrentUrl();
        String postTitleText = postTitle.getText();
        assertEquals("Urls don't maches", postUrl,firstPostLink);
        assertEquals("Titles aren't the same",postTitleText,firstPostOnWeekText);







    }





}
