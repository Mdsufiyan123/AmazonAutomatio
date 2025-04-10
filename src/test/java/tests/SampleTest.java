package tests;

import org.testng.annotations.Test;

public class SampleTest {
	@Test(dataProvider="loginTestData")
	public void performLogin(String email,String password) {
		System.out.println("This is for testing purpose");
	}
}
