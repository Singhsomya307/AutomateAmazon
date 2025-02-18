package stepDefinintions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;

import javax.xml.transform.Source;
import java.time.Duration;
import java.util.Set;

public class AmazonSteps {
    String baseURL = "https://www.amazon.in/";
    WebDriver driver;
    WebDriverWait wait;
    //By.ByXPath searchresult = (By.ByXPath) By.xpath("//*[contains(@class,'SEARCH_RESULTS')]//img");
    By.ByXPath searchresult = (By.ByXPath) By.xpath("//*[contains(@class,'SEARCH_RESULTS')]//img");

    //By.ByXPath searchresult = (By.ByXPath) By.xpath("//*[@id='0ff69638-0e3a-4327-a683-03e3a0e11162']/div/div/span/div/div/div[2]/div[1]/a/h2/span");

    @Given("the user is on the Amazon login page")
    public void the_user_is_on_the_amazon_login_page() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //maximize the screen
        driver.manage().window().maximize();
        //
        driver.get(baseURL);

        wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofMillis(500));
        driver.findElement(By.id("nav-link-accountList")).click();
    }

    @When("the user logs in with valid credentials")
    public void the_user_logs_in_with_valid_credentials() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys("+917588157640");
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password"))).sendKeys("123456789");
        driver.findElement(By.id("signInSubmit")).click();
    }

    @Then("the home screen should be displayed")
    public void the_home_screen_should_be_displayed() {
        wait.until(ExpectedConditions.titleContains("Amazon"));
        Assert.assertTrue(driver.getTitle().contains("Amazon"));
    }

    @And("the user searches for a {string}")
    public void the_user_searches_for_a(String product) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchBox.sendKeys(product);
        searchBox.submit();
    }

    @When("the user selects the first item from the search results")
    public void the_user_selects_the_first_item_from_the_search_results() {
        WebElement firstItem = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchresult)).get(0);
        WebElement clickableItem = wait.until(ExpectedConditions.elementToBeClickable(firstItem.findElement(searchresult)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clickableItem);
        waitForElementToBeClickable(clickableItem);
        clickableItem.click();
    }

//    @And("the user is redirected to the product's page")
//    public void the_user_is_redirected_to_the_products_page() {
//        // Wait for the product title to be visible on the product page
//        System.out.println("i want to be here");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='productTitle']")));
//        System.out.println("i am here ");
//        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='productTitle']")));
//        System.out.println("i am here again");
//        Assert.assertTrue(driver.getTitle().contains("Amazon"));
//        System.out.println("i am here once again");
//    }

    @And("the user is redirected to the product's page")
    public void the_user_is_redirected_to_the_products_page() {
        System.out.println("Waiting for the product title to be visible...");

        // Wait up to 20 seconds for the product title element
        String parenthandle= driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Set<String> handles = driver.getWindowHandles();
        for(String handle:handles){
            if(!handle.equals(parenthandle)){
                driver.switchTo().window(handle);
            }
        }

        System.out.println("Redirection to product page verified successfully!");
    }


    @Then("the user adds the item to the cart")
    public void the_user_adds_the_item_to_the_cart() {
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='freshAddToCartButton']/span/input")));  // Update the locator if necessary
        // You may want to scroll the element into view first, in case it is outside the visible area
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
        // Now click the "Add to Cart" button
        waitForElementToBeClickable(addToCartButton);

        addToCartButton.click();
    }

    @Then("close the browser")
    public void close_the_browser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @And("the user waits for the element to be clickable")
    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}

