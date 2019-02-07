package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginFormPage extends BasePage {

    public LoginFormPage(WebDriver navegador) {
        super(navegador);
    }

    public LoginFormPage digitarLogin(String login){
        navegador.findElement(By.id("signinbox")).findElement(By.name("login")).sendKeys(login);
        return this;
    }
    public LoginFormPage digitarSenha(String passord){
        navegador.findElement(By.id("signinbox")).findElement(By.name("password")).sendKeys(passord);
        return this;
}
    public SecretaPage clicarSignin(){
        navegador.findElement(By.linkText("Sign in")).click();
        return new SecretaPage(navegador);
    }

    public SecretaPage fazerLogin(String login, String senha){
            digitarLogin(login);
            digitarSenha(senha);
            clicarSignin();

            return new SecretaPage(navegador);

    }
}
