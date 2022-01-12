package pages;

import com.microsoft.playwright.Page;

public class LoginPage extends ArgylePage{

	String userInputSelector = "#sign-in-email-input";
	String passInputSelector = "#sign-in-password-input";
	String loginBtnSelector = "#sign-in-submit-button";

	String userErrMessageSelector = "#sign-in-email-input-helper";
	String passErrMessageSelector = "#sign-in-password-input-helper-text";

	String resetPassLnkSelector = "[data-hook=\"sign-in-reset-password-button\"]";

	public LoginPage(Page page) {
		super(page);
	}


	public void login(String username, String password){

		log.debug("Attempting login with username {} and pass {}", username, password);
		page.fill(userInputSelector, username);
		page.fill(passInputSelector, password);

		log.debug("clicking login button");
		page.querySelector(loginBtnSelector).click();

	}


}
