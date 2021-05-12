package cz.czechitas.selenium;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void RodicSUctemSeMusiPrihlasit() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/prihlaseni");
        prihlaseniRodice();
        String jmenoPrihlaseneho = prohlizec.findElement(By.xpath("//a[@title='Tata Tester']")).getText();
        Assertions.assertEquals("Tata Tester", jmenoPrihlaseneho);
    }

    public void prihlaseniRodice () {
        WebElement poleZadaniEmailu = prohlizec.findElement(By.id("email"));
        poleZadaniEmailu.sendKeys("tester@testuje.cz");
        WebElement poleZadaniHesla = prohlizec.findElement(By.id("password"));
        poleZadaniHesla.sendKeys("Tester007");
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//button[contains(text(),'Přihlásit')]"));
        tlacitkoPrihlasit.click();
    }

    @Test
    public void RodicSUctemMusiBytSchopemZalozitPrihlaskuNaKurzV1() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
        vyberKurzu();
        prihlaseniRodice();
        vyplneniPrihlaskyDitete();
        WebElement StahnoutPotvrzeni = prohlizec.findElement(By.xpath("//a[@title = 'Stáhnout potvrzení o přihlášení']"));
        Assertions.assertNotNull(StahnoutPotvrzeni);
    }

    public void vyberKurzu () {
        WebElement TlacitkoViceInformaciKurz3 = prohlizec.findElement(By.xpath("//a[@href='https://cz-test-jedna.herokuapp.com/31-trimesicni-kurzy-programova']"));
        TlacitkoViceInformaciKurz3.click();
        WebElement TlacitkoPrihlaskaKurz1 = prohlizec.findElement(By.xpath("//a[@href='https://cz-test-jedna.herokuapp.com/zaci/pridat/71-java-1']"));
        TlacitkoPrihlaskaKurz1.click();
    }

    public void vyplneniPrihlaskyDitete () {
        WebElement poleVyberTerminu = prohlizec.findElement(By.xpath("//div[contains(text(),'Vyberte termín')]"));
        poleVyberTerminu.click();
        WebElement poleZadaniTerminu = prohlizec.findElement(By.xpath("//input[@type ='search']"));
        poleZadaniTerminu.sendKeys("28.06.");
        poleZadaniTerminu.sendKeys(Keys.ENTER);
        WebElement poleJmenoZaka = prohlizec.findElement(By.id("forename"));
        poleJmenoZaka.sendKeys("Teodor");
        WebElement polePrijmeniZaka = prohlizec.findElement(By.id("surname"));
        polePrijmeniZaka.sendKeys("Tester");
        WebElement poleDatumNarozeniZaka = prohlizec.findElement(By.id("birthday"));
        poleDatumNarozeniZaka.sendKeys("01.04.2012");
        WebElement zatrzitkoPlatbaHotove = prohlizec.findElement(By.xpath("//label[@for = 'payment_cash']"));
        zatrzitkoPlatbaHotove.click();
        WebElement souhlasSPodminkami = prohlizec.findElement(By.xpath("(//span[@class='custom-control custom-checkbox'])[2]"));
        souhlasSPodminkami.click();
        WebElement tlacitkoVytvoritPrihlasku = prohlizec.findElement(By.xpath("//input[@value='Vytvořit přihlášku']"));
        tlacitkoVytvoritPrihlasku.click();
    }

    @Test
    public void RodicSUctemMusiBytSchopemZalozitPrihlaskuNaKurzV2() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/prihlaseni");
        prihlaseniRodice();
        WebElement zalozkaProRodice = prohlizec.findElement(By.xpath("//a[text() ='\n" +
                "                        Pro rodiče                    ']"));
        zalozkaProRodice.click();
        WebElement VytvoritPrihlasku = prohlizec.findElement(By.xpath("//a[@href='https://cz-test-jedna.herokuapp.com/zaci/pridat']"));
        VytvoritPrihlasku.click();
        vyberKurzu();
        vyplneniPrihlaskyDitete();
        WebElement StahnoutPotvrzeni = prohlizec.findElement(By.xpath("//a[@title = 'Stáhnout potvrzení o přihlášení']"));
        Assertions.assertNotNull(StahnoutPotvrzeni);
    }

    @Test
    public void PoKliknutiNaOdkazVKOntaktechSeNacteStrankaCzechitas() {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/kontakt");
        WebElement odkazNaWebCzechitas = prohlizec.findElement(By.xpath("//a[@href='https://www.czechitas.cz/']"));
        odkazNaWebCzechitas.click();
        String url = prohlizec.getCurrentUrl();
        Assertions.assertEquals("https://www.czechitas.cz/cs/", url);
    }

    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }
}
