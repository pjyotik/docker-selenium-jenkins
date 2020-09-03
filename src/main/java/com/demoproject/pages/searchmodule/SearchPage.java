package com.demoproject.pages.searchmodule;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(name = "btnK")
    private List<WebElement> searchBtns;

    @FindBy(className = "rc")
    private List<WebElement> searchResults;

    @FindBy(id = "foot")
    private WebElement footer;

    public SearchPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 30);
    }

    public void goTo() {
        this.driver.get("https://www.google.com");
        System.out.println("Browser launched and navigated to Google");
    }

    public void searchFor(String keyword) {
        this.searchBox.sendKeys(keyword);
        System.out.println(keyword + " - entered for search");

        this.searchBox.sendKeys(Keys.TAB);
        this.searchBtns
                .stream()
                .filter(element -> element.isDisplayed() && element.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
        System.out.println("Search button clicked");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rc")));
        System.out.println("Results appeared");
    }

    public List<WebElement> getResults() {
        System.out.println("Results Count : " + this.searchResults.size());
        System.out.println("---------------------------------------------");
        return this.searchResults;
    }
}
