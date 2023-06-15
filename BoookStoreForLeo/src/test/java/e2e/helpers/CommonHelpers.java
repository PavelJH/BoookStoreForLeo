package e2e.helpers;


import e2e.TestBase;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

@Getter

public class CommonHelpers extends TestBase{
    WebDriver driver;
    String userName = "JH";
    String password = "Jeffry080%";

    public CommonHelpers(WebDriver driver) {
        this.driver = driver;
    }
}
