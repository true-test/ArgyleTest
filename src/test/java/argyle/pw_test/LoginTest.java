package argyle.pw_test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.EnvData;

public class LoginTest extends BaseTest {

	EnvData data = new EnvData();

	@DataProvider(name = "loginTestCredentials")
	public Object[][] loginTestCredentials() {
		return new Object[][]{
				{data.getUser(), data.getPass(), true, ""},
				{"asdasd@sdfklj.com", "password2", false, "Invalid email and password combination"},
				{"username2", "password2", false, "Please enter a valid email address"},
				{"", "password2", false, "This field is required."},


		};
	}


	@Test(dataProvider = "loginTestCredentials")
	public void loginTest(String username, String password, Boolean expectSuccess) {
		SoftAssert soft = new SoftAssert();

		LoginPage loginPage = new LoginPage(page);

		loginPage.login(username, password);



		soft.assertAll();
	}



}
