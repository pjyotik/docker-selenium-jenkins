package com.demoproject.searchmodule;

import com.demoproject.pages.searchmodule.SearchPage;
import com.demoproject.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest{


    @Test(dataProvider = "search-keywords")
    public void googleTest(String searchKeyword) {
        SearchPage searchPage= new SearchPage(driver);
        searchPage.goTo();
        searchPage.searchFor(searchKeyword);
        Assert.assertTrue(searchPage.getResults().size()>0);
    }

    @DataProvider(name = "search-keywords")
    public static Object[][] credentials() {
        return new Object[][] {
                { "test automation guru" },
                { "selenium webdriver" },
                { "dockerized selenium grid" },
                { "test automation blog" },
                { "jmeter docker" } ,
                { "test automation guru" },
                { "selenium webdriver" },
                { "dockerized selenium grid" },
                { "test automation blog" },
                { "jmeter docker" }
        };
    }

}
