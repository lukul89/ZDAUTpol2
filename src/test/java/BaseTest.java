import devToPages.DevToMainPage;
import devToPages.DevToSinglePostPage;
import devToPages.DevToWeekPage;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    //    WebDriverWait explicitWait;
    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Łukasz\\Desktop\\Chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
//        explicitWait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // poczekaj zanim wywalisz error 10 sekund i sprawdzaj co sekunde czy elemen sie nie pojawil
    }
    @Test
    public void selectFirstPostOnWeek(){
        DevToMainPage devToMainPage = new DevToMainPage(driver, wait);
        DevToWeekPage devToWeekPage = devToMainPage.goToWeekPage();


        String firstPostLink = devToWeekPage.firstPostOnWeek.getAttribute("href");
        String firstPostText = devToWeekPage.firstPostOnWeek.getText();

        DevToSinglePostPage devToSinglePostPage = devToWeekPage.selectFirstPost();
        wait.until(ExpectedConditions.urlToBe(firstPostLink));
        String postTitleText = devToSinglePostPage.postTitle.getText();
        String currentUrl = driver.getCurrentUrl();

        assertEquals("Urls aren't the same", currentUrl, firstPostLink);
        assertEquals("Titles aren't the same",postTitleText,firstPostText);

    }
    @Test
    public void searchBarTesting() {
        WebElement searchBox = driver.findElement(By.id("nav-search"));
        String searchText = "testing";
        String searchUrl = "https://dev.to/search?q=";
        String cssSelector = "h2.crayons-story__title a";
        searchBox.sendKeys(searchText + Keys.ENTER);
        wait.until(ExpectedConditions.urlToBe(searchUrl + searchText));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        List<WebElement> postTilesList = driver.findElements(By.cssSelector(cssSelector));
        for (int i = 0; i < 3; i++) {
            String message = String.format("Header %d doesn't contain text %s", i, searchText);
            Assert.assertTrue(message, postTilesList.get(i).getText().toLowerCase().contains(searchText));
        }
    }
    @Test
    public void findJavaInNavBar(){
        WebElement java = driver.findElement(By.cssSelector("div#default-sidebar-element-java > a"));
        highlightElement(driver, java);
        java.click();
    }

    @Test
    public void testPodcast() {
        WebElement podcast = driver.findElement(By.xpath("//a[@href='/pod']"));
        podcast.click();
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h3")));
        List<WebElement> podcastList = driver.findElements(By.tagName("h3"));
        highlightElement(driver, podcastList.get(3));
        podcastList.get(3).click();

        wait.until(ExpectedConditions.urlContains("stackpodcast"));

        // znajdujemy cale pole mozliwe do odpalenie podcastu
        WebElement playArea = driver.findElement(By.className("record-wrapper"));
        playArea.click();

        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        String playAreaClassAttribute = playArea.getAttribute("class");
        boolean isPlaying =playAreaClassAttribute.contains("playing");

        assertTrue("Podcast ins't playing", isPlaying);


    }
    // Wersja Krzysztofa
        @Test
        public void playFourthPodcast(){
            WebElement podcastdLink = driver.findElement(By.cssSelector("a[href=\"/pod\"]"));
            highlightElement(driver, podcastdLink);
            podcastdLink.click();
            String targetUrl = "https://dev.to/pod";
            String cssSelector = "h3";
            waitForElements(targetUrl, cssSelector);
            List<WebElement> postTilesList = driver.findElements(By.cssSelector(cssSelector));
            postTilesList.get(3).click();
            targetUrl = "stackpodcast";
            cssSelector = "img[alt=\"Play Button\"]";
            waitForElements(targetUrl, cssSelector);

            WebElement playButton = driver.findElement(By.className("record-wrapper"));
            playButton.click();


        }
        private void waitForElements(String targetUrl, String cssSelector) {
            wait.until(ExpectedConditions.urlContains(targetUrl));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        }


    @After
    public void cleanUp() {
//        driver.quit();
    }
}
