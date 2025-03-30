package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import base.ActionUtils;
import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

public class HomePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private ActionUtils actionUtils;
	private Select dropDown;
	private SoftAssert softAssert; // Declare softAssert here for class-wide access

	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.actionUtils = new ActionUtils(driver);
		this.softAssert = new SoftAssert(); // Initialize softAssert in the constructor
		PageFactory.initElements(driver, this);
	}

	// Web Elements
	@FindBy(xpath = "//*[@id='nav-link-accountList']")
	private WebElement accountAndList;

	@FindBy(xpath = "//a[@class='nav-action-signin-button']//span[text()='Sign in']")
	private WebElement signInIcon;

	@FindBy(id = "searchDropdownBox")
	private WebElement searchDropDown;

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchTextBox;

	@FindBy(xpath = "//div[@data-cy='title-recipe']")
	private List<WebElement> searcheditems;

	@FindBy(xpath = "//input[@aria-label='Minimum price']")
	private WebElement increasePrice;

	@FindBy(xpath = "//input[@aria-label='Maximum price']")
	private WebElement decreasePrice;

	@FindBy(xpath = "//div[@class='a-section s-range-input-container s-lower-bound aok-relative']")
	private WebElement sample;

	@FindBy(xpath = "//div[contains(@class,'submit-range-button')]")
	private WebElement goRangeIcon;

	@FindBy(xpath = "//label[contains(@class,'lower-bound')]/span")
	private WebElement minPriceRange;

	@FindBy(xpath = "//label[contains(@class,'upper-bound')]/span")
	private WebElement maxPriceRange;

	@FindBy(xpath = "//a[@aria-describedby='price-link']//span[@class='a-price-whole']")
	private List<WebElement> priceList;

	@FindBy(xpath = "//*[contains(@data-component-type,'results')]//*[text()='Results']")
	private WebElement resultsHeader;

	@FindBy(xpath = "//span[text()='Featured']")
	private WebElement featuredDropDown;

	@FindBy(xpath = "//a[text()='Price: Low to High']")
	private WebElement priceLowToHigh;

	// Hover over "Accounts & Lists"
	public void performHoverAccountsAndList() {
		actionUtils.hoverOverElement(accountAndList);
	}

	// Click on "Sign In" button
	public void clickSignInIcon() {
		signInIcon.click();
	}

	public void disrciptionAlphabetical() {
		dropDown = new Select(searchDropDown);
		List<WebElement> options = dropDown.getOptions();

		List<String> originalValues = new ArrayList<>();
		for (int i = 1; i <= originalValues.size(); i++) {
			originalValues.add(options.get(i).getText().trim());
		}

		List<String> sortedList = new ArrayList<>(originalValues);
		Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);

		softAssert.assertEquals(originalValues, sortedList); // Using the class-level softAssert here
	}

	public void validateItemsSearched() {
		for (WebElement searchList : searcheditems) {
			softAssert.assertTrue(searchList.getText().contains("MacBook") || searchList.getText().contains("Mac")
					|| searchList.getText().contains("Apple"));
		}
	}

	public void searchItemsBasedOnCategory(String category, String searchItem) {
		searchDropDown.click();
		dropDown = new Select(searchDropDown);
		dropDown.selectByVisibleText(category);
		searchTextBox.sendKeys(searchItem);
		actionUtils.pressEnter();
	}

	public void validatePriceRangeFilter() {
		increaseMinPrice(increasePrice, 40); // Increase min price (move left slider right)
		decreaseMaxPrice(decreasePrice, 50); // Decrease max price (move right slider left)
		goRangeIcon.click();
		actionUtils.waitUntilVisible(resultsHeader);
		int minPrice = actionUtils.extractNumericValue(minPriceRange.getText());
		int maxPrice = actionUtils.extractNumericValue(maxPriceRange.getText());

		System.out.println(minPrice);
		System.out.println(maxPrice);

		for (WebElement priceElement : priceList) {
			try {
				WebElement parentDiv = priceElement.findElement(By.xpath("ancestor::div[@class='puisg-col-inner']"));
				boolean isSponsored = !parentDiv
						.findElements(By.xpath(".//*[contains(@class,'puis-sponsored-label-text')]")).isEmpty();

				if (isSponsored) {
					System.out.println("Skipping sponsored item price validation.");
					continue;
				}

			} catch (NoSuchElementException e) {
				System.out.println("No sponsored tag found. Proceeding with validation.");
			}

			try {
				int numPrice = actionUtils.extractNumericValue(priceElement.getText());

				System.out.println(numPrice);
				softAssert.assertTrue(numPrice >= minPrice && numPrice <= maxPrice,
						"The Price is not in the correct range: " + numPrice);

			} catch (NumberFormatException e) {
				System.out.println("Skipping invalid price: " + priceElement.getText());
			}
		}
		// Assert all soft assertions at the end
		softAssert.assertAll();
	}

	// Function to increase min price (move the left slider right)
	public void increaseMinPrice(WebElement increasePriceSlider, int xOffset) {
		int currentValue = Integer.parseInt(increasePriceSlider.getAttribute("value"));
		int newValue = currentValue + xOffset;

		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", increasePriceSlider,
				newValue);
		((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input'));",
				increasePriceSlider);
	}

	// Function to decrease max price (move the right slider left)
	public void decreaseMaxPrice(WebElement decreasePriceSlider, int xOffset) {
		int currentValue = Integer.parseInt(decreasePriceSlider.getAttribute("value"));
		int newValue = currentValue - xOffset;

		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", decreasePriceSlider,
				newValue);
		((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input'));",
				decreasePriceSlider);
	}

	public void validatePriceSorting() {
		featuredDropDown.click();
		priceLowToHigh.click();

		// Creating a list for prices
		List<Integer> priceCheck = new ArrayList<>();
		for (WebElement price : priceList) {
			priceCheck.add(actionUtils.extractNumericValue(price.getText()));
			System.out.println(priceList);
		}

		// Create a new Sorted list and asserting with the priceList
		List<Integer> sortedList = new ArrayList<>(priceCheck);
		Collections.sort(sortedList);
		Assert.assertEquals(priceCheck, sortedList);

	}

}
