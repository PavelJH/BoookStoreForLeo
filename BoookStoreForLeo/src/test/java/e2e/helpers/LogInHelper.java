package e2e.helpers;

import e2e.pages.LoginPage;
import org.openqa.selenium.WebDriver;


public class LogInHelper extends CommonHelpers{
    public LogInHelper(WebDriver driver) {
        super(driver);
    }

    LoginPage loginPage;

    public void login(){
        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.loginFillField(userName);
        loginPage.passwordFillField(password);
        loginPage.clickLoginButton();
    }


}
