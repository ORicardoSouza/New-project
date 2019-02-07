package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")
public class InformacoesUsuarioTest {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp() throws InterruptedException {
    navegador = Web.createChrome();

        // Clicar no texto que possui o texto "singin"
        navegador.findElement(By.linkText("Sign in")).click();

        // Digitar no campo com o name "Login" que esta dentro do formulário
        WebElement formularioSignInbox = navegador.findElement(By.id("signinbox"));

        // Digitar  no campo name "login" julio0001
        formularioSignInbox.findElement(By.name("login")).sendKeys("julio0001");

        // Digitar no campo name "password" senha --> 123456
        formularioSignInbox.findElement(By.name("password")).sendKeys("123456");

        Thread.sleep( 3000);

        //Clicar no link SIGNIN
        navegador.findElement( By.xpath(".//div[@id='signinbox']//a") ).click();

        //Validar que dfentro do elemento com class "me"  está o texto "Hi,Júlio"
        //WebElement me = navegador.findElement(By.className("me"));
        //String textoNoElementoMe = me.getText();
        //assertEquals( "Hi, Julio", textoNoElementoMe);

        //Clicar em um link que possui a class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar um link que tenha "More data about you"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test //O @param abaixo é usado para que possamos fazer testes com dados diferentes,
    // os dados são alimentados do aqruivo informaçõesUsuarioTestData na pasta java/resources.
    public void testAdicionarUmaInformacaoAdicionalDoUsuario (@Param(name="tipo") String tipo,
                                                              @Param(name="contato") String contato,
                                                              @Param(name="mensagemEsperada") String mensagemEsperada) throws InterruptedException {


        //Clicar no botão atraves do xpath "Add more data"
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        //Identificar a popup  ojnde esta o formulário  de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));


        //Na combo de name type escolher  a opção "phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo  de name contact  digitar "+5521999999999"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        //Clicar no  link  de text "SAVE" que esta no popup.
        navegador.findElement(By.linkText("SAVE")).click();

        //Na mensagem  de id "toast-container" validar que o texto é "Your  contact has  been added!"
        WebElement mensagemPop = navegador.findElement(  By.xpath(".//div[@class='toast rounded']")  );
        String mensagem  = mensagemPop.getText();
        System.out.println(mensagem);
        assertEquals(mensagemEsperada, mensagem);

    }

    @Test
    public void removerUmContatoDeUmUsuario(){
            // Clicar  no elemento pelo xpath //xpath[text()="+551133334444"]/following-sibling::a
            navegador.findElement(By.xpath("//xpath[text()=\"+551133334444\"]/following-sibling::a"));

            // Confirmar a janelam JavaScript
            navegador.switchTo().alert().accept();

            // Validar que a mensagem apresenta foi  rest  in peace  dear phone.
            WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
            String mensagem  = mensagemPop.getText();
            assertEquals("Rest in peace, dear phone!", mensagem);


            String screenshotArquivo = "C:\\Users\\ricardo souza\\Desktop\\webdriver-Java\\Sreenshots"
            + Generator.dataHoraDataArquivo() + test.getMethodName()+ ".png";
            Screenshot.tirar(navegador, screenshotArquivo);

            // Aguardar ate 10 seg para a tela desaparecer
            WebDriverWait aguardar = new WebDriverWait(navegador, 10);
            aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));


            // Clicar  no lonk logout
            navegador.findElement(By.linkText("Logout")).click();
    }
    @After
    public  void tearDown(){
        navegador.quit();

    }
}

