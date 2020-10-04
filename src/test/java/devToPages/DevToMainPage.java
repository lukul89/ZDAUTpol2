package devToPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.ExecutionException;

public class DevToMainPage {

     WebDriver driver;
     String url = "https://dev.to/";
     WebDriverWait wait;
     @FindBy(xpath = "//a[@href='/top/week']")
     WebElement week;

     public DevToMainPage(WebDriver driver, WebDriverWait wait){
         this.driver = driver;
         this.wait = wait;
         driver.get(url);
         PageFactory.initElements(driver, this);
     }

     public DevToWeekPage goToWeekPage(){
         week.click();
         wait.until(ExpectedConditions.urlToBe(week.getAttribute("href")));
         return new DevToWeekPage(this.driver,this.wait);
     }
}
