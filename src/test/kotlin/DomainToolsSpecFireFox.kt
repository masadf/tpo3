import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.interactions.Actions
import java.time.Duration
import kotlin.test.assertEquals


class DomainToolsSpecFireFox {
    private var driver: WebDriver = FirefoxDriver()

    @BeforeEach
    fun setUp() {
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    fun closeDriver() {
        driver.close()
    }

    @Test
    fun `test use cases`() {
        with(driver) {
            get("https://www.domaintools.com/")
            val actions = Actions(driver)
            actions.scrollByAmount(0, 700).perform()

            for (j in 1..6) {
                findElement(By.xpath("//*[@id=\"av_section_1\"]/div/main/div/div/div[4]/div/div/div/div[1]/div/div/div/div[$j]/div")).click()
                actions.scrollByAmount(0, 50).perform()
                for (i in 1..6) {
                    assertEquals(
                        findElement(By.xpath("//*[@id=\"av_section_1\"]/div/main/div/div/div[4]/div/div/div/div[1]/div/div/div/div[$i]/div/div[2]")).isDisplayed,
                        i == j
                    )
                }
            }
        }
    }

    @Test
    fun `searching info`() {
        with(driver) {
            get("https://www.domaintools.com/")
            findElement(By.xpath("//*[@id=\"menu-item-28626\"]/a")).click()

            findElement(By.xpath("//*[@id=\"s\"]")).sendKeys("info")
            assertEquals(findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/main/div")).text, "To search the site please enter a valid term.")

            findElement(By.xpath("//*[@id=\"searchform\"]/div/button")).click()
            assertEquals(findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/main/div")).text, "1080 Results for: info")
        }
    }

    @Test
    fun `searching info with no results`() {
        with(driver) {
            get("https://www.domaintools.com/")
            findElement(By.xpath("//*[@id=\"menu-item-28626\"]/a")).click()

            findElement(By.xpath("//*[@id=\"s\"]")).sendKeys("skdsjfksdjfksjfksdf")
            assertEquals(findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/main/div")).text, "To search the site please enter a valid term.")

            findElement(By.xpath("//*[@id=\"searchform\"]/div/button")).click()
            assertEquals(findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/main/div")).text, "No results for: skdsjfksdjfksjfksdf")
        }
    }

    @Test
    fun `newsletter subscribing with valid email`() {
        with(driver) {
            get("https://www.domaintools.com/newsletter/")

            findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys("test@itmo-mail.ru")
            findElement(By.xpath("//*[@id=\"mktoForm_2395\"]/div[8]/span/button")).click()

            val thank = findElement(By.xpath("//*[@id=\"av_section_1\"]/div/main/div/div/div[1]/div/div/div[1]/h1"))

            assertEquals(thank.text, "Thanks for Subscribing!")
        }
    }

    @Test
    fun `newsletter subscribing with invalid email`() {
        with(driver) {
            get("https://www.domaintools.com/newsletter/")

            findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys("test@mail.ru")
            findElement(By.xpath("//*[@id=\"mktoForm_2395\"]/div[8]/span/button")).click()

            assertEquals(findElement(By.xpath("//*[@id=\"ValidMsgEmail\"]")).text, "Must be a valid business email address.")
        }
    }
}