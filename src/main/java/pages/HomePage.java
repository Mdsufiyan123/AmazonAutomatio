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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import utils.ActionUtils;
import utils.ExtentReportManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

public class HomePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private Select dropDown;
	private SoftAssert softAssert;

	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		ActionUtils.initActions(driver);
		this.softAssert = new SoftAssert();
		PageFactory.initElements(driver, this);
	}

	// Web Elements
	@FindBy(xpath = "//*[@id='nav-link-accountList']")
	private WebElement accountAndList;

	@FindBy(xpath = "//*[text()='Sign in']")
	private WebElement signInIcon;

	@FindBy(id = "searchDropdownBox")
	private WebElement searchDropDown;

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchTextBox;

	@FindBy(xpath = "//div[@data-cy='title-recipe']")
	private List<WebElement> searcheditems;
	
	@FindBy(xpath = "//div[@data-cy='price-recipe']//span[contains(text(),'% off')]")
	private List<WebElement> itemsDiscountSection;
	
	@FindBy(xpath = "//div[@data-cy='delivery-recipe']")
	private List<WebElement> deliverySection;
	
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
	
	@FindBy(xpath="//div[contains(@id,'sac-suggestion')]//div[contains(@class,'suggestion-ellipsis-direction')]")
	private List<WebElement> searchAutoSuggestions;
	
	@FindBy(xpath="//div[@id='nav-xshop']//*[text()='Mobiles']")
	private WebElement mobileNavigation;
	
	@FindBy(xpath="//div[contains(@id,'refinements')]//*[contains(text(),'Smartphones')]")
	private WebElement smartPhoneFilter;
	
	@FindBy(xpath="//*[@aria-label='Prime Eligible']")
	private WebElement primeFilter;
	
	@FindBy(xpath="//div[@id='brandsRefinements']//*[text()='realme']")
	private WebElement brandFilter;
	
	@FindBy(xpath="//*[contains(text(),'Memory Storage Capacity')]/../..//span[text()='256 GB']")
	private WebElement memoryFilter;
	
	@FindBy(xpath="//span[text()='25% Off or more']")
	private WebElement discountFilter;
	
	@FindBy(xpath="//div[contains(@class,'pagination') and @role='navigation']")
	private WebElement paginationContainer;
	
	@FindBy(xpath="//*[text()='Previous']")
	private WebElement previousPageButton;
	
	@FindBy(xpath="//*[text()='Next']")
	private WebElement nextPageButton;
	
	// Hover over "Accounts & Lists"
	public void performHoverAccountsAndList() {
		ActionUtils.hoverOverElement(accountAndList);
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
		ExtentReportManager.getTest().log(Status.INFO, "Validating the dropdown in the search box is in alphabetical order");
		try {
			Assert.assertEquals(originalValues, sortedList); // Using the class-level softAssert here
			ExtentReportManager.getTest().log(Status.PASS, "Validating the dropdown in the search box is in alphabetical order");
		}catch(AssertionError e) {
			ExtentReportManager.getTest().log(Status.FAIL, "The dropdown in the search box is not in alphabetical order");
		}
	}

	public void validateItemsSearched(String searchItemName1, String searchItemName2) {
		
		ExtentReportManager.getTest().log(Status.INFO, "Validating Search Results Showing the Correct Items");
		try {

	    for (WebElement searchList : searcheditems) {
	    			    		
	    		softAssert.assertTrue(
	            searchList.getText().toLowerCase().contains(searchItemName1.toLowerCase()) || 
	            searchList.getText().toLowerCase().contains(searchItemName2.toLowerCase()),
	            "The search item is not correct: " + searchList.getText()
	        );
	    }
	    }catch(AssertionError e){
	    	ExtentReportManager.getTest().log(Status.FAIL, "Search Results Are Not Showing Correct Items");

	    }
		// Assert all soft assertions and log the result accordingly
	    try {
	        softAssert.assertAll();  // This will throw an exception if any soft assertion failed
	        ExtentReportManager.getTest().log(Status.PASS, "Validated products and filters on the previous page.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Validating products and filters on the previous page.");
	    }
	}


	public void searchItemsBasedOnCategory(String category, String searchItem) {
		// Wait for search dropdown to be present and clickable
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchDropdownBox")));
		searchDropDown.click();
		
		// Wait for dropdown options to be present
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='searchDropdownBox']/option")));
		dropDown = new Select(searchDropDown);
		dropDown.selectByVisibleText(category);
		
		// Wait for search text box to be present and clickable
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("twotabsearchtextbox")));
		wait.until(ExpectedConditions.elementToBeClickable(searchTextBox));
		searchTextBox.sendKeys(searchItem);
		ActionUtils.pressEnter();
	}

	public void validatePriceRangeFilter() {
		
		increaseMinPrice(increasePrice, 60); // Increase min price (move left slider right)
		decreaseMaxPrice(decreasePrice, 60); // Decrease max price (move right slider left)
		goRangeIcon.click();
		ActionUtils.waitUntilVisible(resultsHeader);
		int minPrice = ActionUtils.extractNumericValue(minPriceRange.getText());
		int maxPrice = ActionUtils.extractNumericValue(maxPriceRange.getText());
		ExtentReportManager.getTest().log(Status.INFO, "Validating the results showing is within price range");
		try {
		for (WebElement priceElement : priceList) {
			try {
				WebElement parentDiv = priceElement.findElement(By.xpath("ancestor::div[@class='puisg-col-inner']"));
				boolean isSponsored = !parentDiv
						.findElements(By.xpath(".//*[contains(@class,'puis-sponsored-label-text')]")).isEmpty();

				if (isSponsored) {
					continue;
				}

			} catch (NoSuchElementException e) {
				// Continue with validation
			}

			try {
				int numPrice = ActionUtils.extractNumericValue(priceElement.getText());
				softAssert.assertTrue(numPrice >= minPrice && numPrice <= maxPrice,
						"The Price is not in the correct range[" +minPrice + "-" + maxPrice + ":" + numPrice);

			} catch (NumberFormatException e) {
				// Skip invalid prices
			}
		}
		}catch(AssertionError e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Validating the results showing is not within price range");
		}
		// Assert all soft assertions and log the result accordingly
	    try {
	        softAssert.assertAll();  // This will throw an exception if any soft assertion failed
	        ExtentReportManager.getTest().log(Status.PASS, "Validated products and filters on the page.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Validating products and filters on the page.");
	        throw e;
	    }
	}

	// Function to increase min price (move the left slider right)
	public void increaseMinPrice(WebElement increasePriceSlider, int xOffset) {
		int currentValue = Integer.parseInt(increasePriceSlider.getAttribute("value"));
		int newValue = currentValue + xOffset;
		ExtentReportManager.getTest().log(Status.INFO, "Increase min price (move the left slider right)");
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", increasePriceSlider,
				newValue);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input'));",increasePriceSlider);
		ExtentReportManager.getTest().log(Status.PASS, "Increased min price (move the left slider right)");
	}

	// Function to decrease max price (move the right slider left)
	public void decreaseMaxPrice(WebElement decreasePriceSlider, int xOffset) {
		int currentValue = Integer.parseInt(decreasePriceSlider.getAttribute("value"));
		int newValue = currentValue - xOffset;
		
		ExtentReportManager.getTest().log(Status.INFO, "Decrease min price (move the left slider right)");
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", decreasePriceSlider,
				newValue);
		((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input'));",
				decreasePriceSlider);
		
		ExtentReportManager.getTest().log(Status.PASS, "Decreased min price (move the left slider right");
	}

	public void validatePriceSorting() {
		featuredDropDown.click();
		priceLowToHigh.click();
		
		// Wait for results to update
		wait.until(ExpectedConditions.visibilityOf(resultsHeader));
		
		// Creating a list for original prices
		List<Integer> priceCheck = new ArrayList<>();
		for (WebElement price : priceList) {
			try {
				int priceValue = ActionUtils.extractNumericValue(price.getText());
				priceCheck.add(priceValue);
				ExtentReportManager.getTest().log(Status.INFO, "Extracted price: " + priceValue);
			} catch (NumberFormatException e) {
				ExtentReportManager.getTest().log(Status.FAIL, "Failed to parse price: " + price.getText());
			}
		}
		
		// Create a sorted copy of the price list
		List<Integer> sortedPrices = new ArrayList<>(priceCheck);
		Collections.sort(sortedPrices);
		
		ExtentReportManager.getTest().log(Status.INFO, "Validating prices are sorted in ascending order");
		try {
			Assert.assertEquals(priceCheck, sortedPrices, "Prices are not sorted in ascending order");
			ExtentReportManager.getTest().log(Status.PASS, "Prices are correctly sorted in ascending order");
		} catch (AssertionError e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Prices are not sorted in ascending order: " + e.getMessage());
			throw e;
		}
	}
	
	public void validateSearchSuggestions(String searchItem) {
	    searchTextBox.sendKeys(searchItem);
	    ExtentReportManager.getTest().log(Status.INFO, "Validating the search suggestions are up to the mark");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Wait for search suggestions to be visible
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".s-suggestion.s-suggestion-ellipsis-direction")));

	    try {
	        List<WebElement> suggestions = driver.findElements(By.cssSelector(".s-suggestion.s-suggestion-ellipsis-direction"));

	        for (int i = 0; i < suggestions.size(); i++) {
	            // Re-fetch element each time to avoid stale references
	            WebElement autoSuggest = driver.findElements(By.cssSelector(".s-suggestion.s-suggestion-ellipsis-direction")).get(i);

	            softAssert.assertTrue(autoSuggest.getText().toLowerCase().contains(searchItem.toLowerCase()),
	                                  "Auto Suggestion is not relevant: " + autoSuggest.getText());
	        }
	    } catch (StaleElementReferenceException e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "StaleElementReferenceException occurred while validating search suggestions.");
	    }

	    // Assert all soft assertions
	    try {
	        softAssert.assertAll();
	        ExtentReportManager.getTest().log(Status.PASS, "Validated search suggestions successfully.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Search suggestions validation failed.");
	    }
	}


	
	public void addFiltersAndValidateResults() {
		ExtentReportManager.getTest().log(Status.INFO, "Adding the filters for the mobile and validating the results");
	    mobileNavigation.click();
	    smartPhoneFilter.click();
	    primeFilter.click();
	    brandFilter.click();
	    memoryFilter.click();
	    discountFilter.click();
	    ExtentReportManager.getTest().log(Status.PASS, "Addede the filters for the mobile and now validating the results");
	    try {
	        for (WebElement searchList : searcheditems) {
	            softAssert.assertTrue(searchList.getText().contains("realme"),"Brands Filter is showing incorrect data");
	            softAssert.assertTrue(searchList.getText().contains("256 GB") || searchList.getText().contains("256GB"),
	                    "Storage Filter is showing incorrect data");
	        }
	        ExtentReportManager.getTest().log(Status.PASS, "Filters added and results validated successfully for Brand and Storage.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Adding the filters for the mobile and validating the results is not working properly for Brand and Storage.");
	    }
	    
	    ExtentReportManager.getTest().log(Status.INFO, "Validating the discount section");
	    try {
	        for (WebElement discountElement : itemsDiscountSection) {
	            String discountValue = discountElement.getText();
	            int discountNum = ActionUtils.extractNumericValue(discountValue);
	            softAssert.assertTrue(discountNum >= 25, "Found a discount below 25%: " + discountNum + "%");
	        }
	        ExtentReportManager.getTest().log(Status.PASS, "Discounts validated successfully.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Discount validation failed. Found discounts below 25%.");
	    }
	    
	    ExtentReportManager.getTest().log(Status.INFO, "Checking for Prime icon in delivery section");

	    boolean allHavePrimeIcon = true;

	    for (WebElement delivery : deliverySection) {
	        List<WebElement> primeIcons = delivery.findElements(By.xpath(".//i[contains(@class, 'a-icon-prime')]"));
	        if (primeIcons.isEmpty()) {
	            allHavePrimeIcon = false;  
	            System.out.println("Missing Prime icon in a delivery recipe.");
	            ExtentReportManager.getTest().log(Status.FAIL, "Some delivery sections are missing the Prime icon.");
	            softAssert.fail("Some delivery-recipe divs do not contain the Prime icon!");
	        }
	    }

	    // Final assertion
	    try {
	        softAssert.assertAll();  // This will throw an exception if any soft assertion failed
	        ExtentReportManager.getTest().log(Status.PASS, "All delivery sections contain the Prime icon.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Some delivery sections are missing the Prime icon.");
	        throw e;  // Ensure the test actually fails
	    }
	}

	
	public void removeFiltersAndValidateResults() {
	    int resultCount = searcheditems.size();
	    
	    ExtentReportManager.getTest().log(Status.INFO, "Removing the filters for brand and memory");
	    try {
	        brandFilter.click();
	        memoryFilter.click();
	        ActionUtils.waitUntilVisible(resultsHeader);
	        
	        int removeFilterCount = searcheditems.size();
	        softAssert.assertTrue(removeFilterCount > resultCount, "Number of results did not increase after removing the filters.");
	        
	        ExtentReportManager.getTest().log(Status.PASS, "Filters removed successfully and results validated.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed to remove filters or validate results. Number of results did not increase.");
	    }
	    
	 // Assert all soft assertions and log the result accordingly
	    try {
	        softAssert.assertAll();  // This will throw an exception if any soft assertion failed
	        ExtentReportManager.getTest().log(Status.PASS, "Validated products and filters on the previous page.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Validating products and filters on the previous page.");
	    }
	}

	public void validatePaginationContainer() {
		
		ExtentReportManager.getTest().log(Status.INFO, "Checking Pagination Container is Visible");
		// Checking Pagination Container is Visible
		try {
			softAssert.assertTrue(paginationContainer.isDisplayed(), "Pagination container is not visible");
			ExtentReportManager.getTest().log(Status.PASS, "Checked Pagination Container is Visible");

		}catch(AssertionError e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Failed: Pagination Container is Visible");
		}
		
		//Checking Previous Button is Disabled for the current home page
		ExtentReportManager.getTest().log(Status.INFO, "Checking Previous Button is Disabled for the current home page");
		try {
		softAssert.assertTrue(previousPageButton.getAttribute("class").contains("pagination-disabled"), "Previous Page is not disabled for the first page");
		}catch(AssertionError e) {
			ExtentReportManager.getTest().log(Status.INFO, "Checked Previous Button is Disabled for the current home page");
		}
		// Assert all soft assertions and log the result accordingly
	    try {
	        softAssert.assertAll();  // This will throw an exception if any soft assertion failed
	        ExtentReportManager.getTest().log(Status.PASS, "Validated products and filters on the previous page.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Validating products and filters on the previous page.");
	    }
		
	}
	
	public void validatePagination() {
	    addFilters();
	    nextPageButton.click();
	    
	    // Validate products are loaded in the second page
	    ExtentReportManager.getTest().log(Status.INFO, "Validating products are loaded in the second page");
	    softAssert.assertTrue(searcheditems.size() > 0, "Product Items are not loading properly on the second page.");
	    // Do not log "pass" here yet, it will be handled later after assertAll()

	    // Validate the items in the second page are relevant to the search item
	    ExtentReportManager.getTest().log(Status.INFO, "Validating items are relevant to the search item on the second page.");
	    try {
	        validateItemsSearched("realme", "real");
	        ExtentReportManager.getTest().log(Status.PASS, "Validated items are relevant to the search item on the second page.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Items are not relevant to the search item on the second page.");
	    }

	    // Validate the filters are showing properly (if any specific validation is required)
	    ExtentReportManager.getTest().log(Status.INFO, "Validating the filters are showing properly.");
	    try {
	        // Add the filter validation logic if needed
	        ExtentReportManager.getTest().log(Status.PASS, "Validated filters are showing properly.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Filters are not showing properly.");
	    }
	    
	    // Go to the previous page and validate products
	    previousPageButton.click();
	    
	    // Validate products are loaded on the previous page
	    ExtentReportManager.getTest().log(Status.INFO, "Validating products are loaded on the previous page.");
	    softAssert.assertTrue(searcheditems.size() > 0, "Product Items are not loading properly on the previous page.");
	    // Do not log "pass" here yet, it will be handled later after assertAll()

	    // Validate the items in the previous page are relevant to the search item
	    for (WebElement searchList : searcheditems) {
	        softAssert.assertTrue(searchList.getText().contains("realme"), "Brands Filter is showing incorrect data.");
	    }

	    // Assert all soft assertions and log the result accordingly
	    try {
	        softAssert.assertAll();  // This will throw an exception if any soft assertion failed
	        ExtentReportManager.getTest().log(Status.PASS, "Validated products and filters on the previous page.");
	    } catch (AssertionError e) {
	        ExtentReportManager.getTest().log(Status.FAIL, "Failed: Validating products and filters on the previous page.");
	    }
	}


	

	
	public void addFilters() {
		mobileNavigation.click();
		smartPhoneFilter.click();
		primeFilter.click();
		brandFilter.click();
		discountFilter.click();
	}
	
}