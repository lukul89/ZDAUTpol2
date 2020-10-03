import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;

    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }


    @Before // warunki poczatkowe
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Łukasz\\Desktop\\Chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String devToUrl = "https://dev.to";
        driver.get(devToUrl);
        wait=new WebDriverWait(driver, 20);

    }

    @Test // kroki testowe
    public void firstTest(){

        WebElement week = driver.findElement(By.xpath("//a[@href='/top/week']"));
        //highlightElement(driver,week);
        week.click();
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
    @Test
    public void searchBarTesting() {
        WebElement searchBox = driver.findElement(By.id("nav-search"));
        highlightElement(driver, searchBox);
        String searchText = "testing";
        searchBox.sendKeys(searchText);
        searchBox.sendKeys(Keys.ENTER);
        String searchUrl = "https://dev.to/search?q=";
        String searchUrlWithText = searchUrl + searchText;
        wait.until(ExpectedConditions.urlToBe(searchUrlWithText));
        List<WebElement> postTitleList = driver.findElements(By.cssSelector("h2.crayons-story__title > a"));
        //for (WebElement element: postTitleList) {
         //   highlightElement(driver, element);
        //}
        String textFormFirstElement = postTitleList.get(0).getText();
    }
    






}
