import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wingify{
    public static void main(String[] args) {
        // Set up ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();
        
        try {
            // Step 1: Open the website and maximize the window
            driver.get("https://sakshingp.github.io/assignment/login.html");
            driver.manage().window().maximize();

            // Step 2: Perform login
            login(driver);
            emptyFieldsTest(driver);
            invalidCredentialsTest(driver);

            // Step 3: Verify sorting by amount
            verifySorting(driver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 4: Close the browser
            driver.quit();
        }
    }

// Login method
//_____________________________________________________________________________________________________________________________________________________

    public static void login(WebDriver driver) {
        // Enter username
        driver.findElement(By.id("username")).sendKeys("testuser");

        // Enter password
        driver.findElement(By.id("password")).sendKeys("password123");

        // Wait for login button (Implicit Wait)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Click on Login button
        driver.findElement(By.id("log-in")).click();

        // Verify successful login
        boolean isLoggedIn = driver.findElement(By.id("transactionsTable")).isDisplayed();
        if (isLoggedIn) {
            System.out.println("✅ Login successful.");
        } else {
            System.out.println("❌ Login failed!");
        }
    }
    
 //Checks for Empty fields
 //_____________________________________________________________________________________________________________________________________________________
 
    public static void emptyFieldsTest(WebDriver driver) {
        System.out.println("🔹 Running Empty Fields Test...");

        // Open login page (to reset fields)
        driver.get("https://sakshingp.github.io/assignment/login.html");

        // Click login without entering anything
        driver.findElement(By.id("log-in")).click();

        // Check for the expected error message
        List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'Both Username and Password must be present')]"));

        if (!errorMessages.isEmpty()) {
            System.out.println("✅ Empty Fields Test Passed.");
        } else {
            System.out.println("❌ Empty Fields Test Failed - Error message not found.");
        }
    }

    
//Invalid details 
//_____________________________________________________________________________________________________________________________________________________
    
    public static void invalidCredentialsTest(WebDriver driver) {
        System.out.println("🔹 Running Invalid Credentials Test...");

        // Open login page (to reset fields)
        driver.get("https://sakshingp.github.io/assignment/login.html");

        // Enter invalid username and password
        driver.findElement(By.id("username")).sendKeys("wronguser");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");

        // Click login button
        driver.findElement(By.id("log-in")).click();

        // Check if an error message is displayed for incorrect login
        List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'Invalid username or password')]"));

        if (!errorMessages.isEmpty()) {
            System.out.println("✅ Invalid Credentials Test Passed.");
        } else {
            System.out.println("❌ Invalid Credentials Test Failed - Error message not found.");
        }
    }

    
    
    

// Verify sorting method
//____________________________________________________________________________________________________________________________________________________________    
    public static void verifySorting(WebDriver driver) {
        // Click on "AMOUNT" column header
        driver.findElement(By.xpath("//th[contains(text(),'Amount')]")).click();
        System.out.println("✅ Clicked on Amount sucessfullyl.");

        // Get the list of amounts
        List<WebElement> amountElements = driver.findElements(By.xpath("//table[@id='transactionsTable']//td[5]"));
        
        List<Double> actualAmounts = new ArrayList<>();
        for (WebElement element : amountElements) {
            String text = element.getText().replaceAll("[^0-9.-]", ""); // Remove currency symbols
            actualAmounts.add(Double.parseDouble(text));
        }

        // Print the original order of amounts
        System.out.println("\n🔹 **Amounts Before Sorting:**");
        for (double amount : actualAmounts) {
            System.out.println(amount);
        }

        // Create a sorted copy
        List<Double> expectedAmounts = new ArrayList<>(actualAmounts);
        Collections.sort(expectedAmounts);

        // Print the expected sorted order of amounts
        System.out.println("\n🔹 **Amounts After Expected Sorting:**");
        for (double amount : expectedAmounts) {
            System.out.println(amount);
        }

        // Check if sorting is correct
        if (actualAmounts.equals(expectedAmounts)) {
            System.out.println("\n✅ Amount column is sorted correctly.");
        } else {
            System.out.println("\n❌ Amount column is NOT sorted correctly.");
        }
    }
}
