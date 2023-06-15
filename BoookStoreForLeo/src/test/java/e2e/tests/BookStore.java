package e2e.tests;

import e2e.TestBase;
import e2e.helpers.LogInHelper;
import e2e.helpers.TextReduction;
import e2e.pages.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class BookStore extends TestBase {
    LoginPage loginPage;
    ProfilePage profilePage;
    e2e.pages.BookStore bookStore;
    BookPage bookPage;
    LeftMenu leftMenu;
    DeleteAllBooks deleteAllBooks;
    LogInHelper logInHelper;
    TextReduction textReduction;

    Number expectedCountRow = 5;
    String rowsFive = "5 rows";
    String firstPage = "1";
    String secondPage = "2";

    List<WebElement> elements;
    List<String> bookTitles;
    List<WebElement> elementsReverse;
    List<String> bookTitlesReverse;

    @Test
    public void addAndDeleteBooks(){
        loginPage = new LoginPage(app.driver);
        profilePage = new ProfilePage(app.driver);
        bookStore = new e2e.pages.BookStore(app.driver);
        bookPage = new BookPage(app.driver);
        leftMenu = new LeftMenu(app.driver);
        deleteAllBooks = new DeleteAllBooks(app.driver);
        logInHelper = new LogInHelper(app.driver);


        loginPage.waitForLoading();
        loginPage.loginFillField(userName);
        loginPage.passwordFillField(password);
        loginPage.clickLoginButton();

        profilePage.waitForLoading();
        leftMenu.clickBookStoreLeftMenu();

        WebElement[] books = { bookStore.getEightBook(), bookStore.getSeventhBook(), bookStore.getSixthBook(), bookStore.getFifthBook(), bookStore.getFourthBook(), bookStore.getThirdBook(), bookStore.getSecondBook(), bookStore.getFirstBook()};
        for (WebElement book : books){
            bookStore.waitForLoading();
            bookStore.clickBook(book);
            bookPage.waitForLoading();
            bookPage.clickAddToYourCollectionButton();
            bookPage.assertBook();//проверка на правильный alert
            bookPage.clickGoBackToBookStoreButton();
        }
        leftMenu.clickProfileLeftMenu();

        profilePage.waitForLoading();
        profilePage.clickDeleteAllBooksButton();

        deleteAllBooks.waitForLoading();
        deleteAllBooks.clickOkDeleteAllBooks();
        deleteAllBooks.acceptAlert();
        //проверка удалились ли
    }
    @Test
    public void transitionPageWithButton(){
        logInHelper = new LogInHelper(app.driver);
        profilePage = new ProfilePage(app.driver);
        leftMenu = new LeftMenu(app.driver);
        bookStore = new e2e.pages.BookStore(app.driver);

        logInHelper.login();

        profilePage.waitForLoading();

        leftMenu.clickBookStoreLeftMenu();

        bookStore.waitForLoadingChoseRows();
        bookStore.choseRows(rowsFive);
        bookStore.clickNextBookStorePageButton();

        bookStore.checkNumberPage(secondPage);

        bookStore.checkCountRows(expectedCountRow);

        bookStore.clickPreviousBookStorePageButton();
        bookStore.checkNumberPage(firstPage);
        bookStore.checkCountRows(expectedCountRow);
    }
    @Test
    public void transitionPageWithPageField(){
        logInHelper = new LogInHelper(app.driver);
        profilePage = new ProfilePage(app.driver);
        leftMenu = new LeftMenu(app.driver);
        bookStore = new e2e.pages.BookStore(app.driver);

        logInHelper.login();

        profilePage.waitForLoading();

        leftMenu.clickBookStoreLeftMenu();

        bookStore.waitForLoadingChoseRows();
        bookStore.choseRows(rowsFive);
        bookStore.getNumberPageCheck().sendKeys(secondPage);
        bookStore.getNumberPageCheck().sendKeys(Keys.ENTER);

        bookStore.checkNumberPage(secondPage);
        bookStore.checkCountRows(expectedCountRow);

        bookStore.click(bookStore.getNumberPageCheck());
        bookStore.getNumberPageCheck().sendKeys(Keys.DELETE);
        bookStore.getNumberPageCheck().sendKeys(firstPage);
        bookStore.getNumberPageCheck().sendKeys(Keys.ENTER);
        bookStore.checkNumberPage(firstPage);
        bookStore.checkCountRows(expectedCountRow);
    }
    @Test
    public void checkWorkTittleHeaderButton(){
        logInHelper = new LogInHelper(app.driver);
        bookStore = new e2e.pages.BookStore(app.driver);
        leftMenu = new LeftMenu(app.driver);

        logInHelper.login();

        leftMenu.clickBookStoreLeftMenu();
        bookStore.waitForLoading();
        elements = bookStore.getElements();
        bookTitles = bookStore.getElementsText();

        bookStore.doubleClickTittleHeader();
        elementsReverse = bookStore.getElements();
        bookTitlesReverse = bookStore.getElementsText();

        Assert.assertNotSame(bookTitles, bookTitlesReverse, "True");
    }
    @Test// тест проходит, если при его прохождении не выскакивает реклама внизу страницы, которую нельзя закрыть
    public void checkTwoPageTittleBooks() throws AWTException {
        logInHelper = new LogInHelper(app.driver);
        profilePage = new ProfilePage(app.driver);
        leftMenu = new LeftMenu(app.driver);
        bookStore = new e2e.pages.BookStore(app.driver);
        textReduction = new TextReduction(app.driver);

        logInHelper.login();
        profilePage.waitForLoading();
        leftMenu.clickBookStoreLeftMenu();

        bookStore.waitForLoadingChoseRows();

        bookStore.choseRows(rowsFive);

        bookStore.clickNextBookStorePageButton();

        bookStore.checkNumberPage(secondPage);

        bookStore.checkCountRows(expectedCountRow);

        bookStore.clickPreviousBookStorePageButton();
        bookStore.checkNumberPage(firstPage);
        bookStore.checkCountRows(expectedCountRow);
    }
    //Поиск(и без логина)
    // search -> book -> go to book -> assert correctBook
}

