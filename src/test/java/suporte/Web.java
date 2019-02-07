package suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Web {
    public static WebDriver createChrome(){
        // Abrindo navegador
        System.setProperty("webdriver.chrome.driver","C:\\drivers_browsers\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();

        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //navegando no site
        navegador.get("http://www.juliodelima.com.br/taskit");

        return navegador;
    }
}
