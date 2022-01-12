package pages.emulator;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.commons.lang3.StringUtils;
import pages.ArgyleBasePage;

import java.util.List;

public class EmulatorPage extends ArgyleBasePage {

	String searchInputSelector = "[data-hook=\"search-input\"]";
	String _searchResultNameSelector = "[data-hook=\"partner-item-name\"]";


	String _bobBtnSelector = "[alt=\"bob-portrait\"]";
	String _sarahBtnSelector = "[alt=\"sarah-portrait\"]";
	String _joeBtnSelector = "[alt=\"joe-portrait\"]";

	String _sandboxEmailSelector = "[data-hook=\"test-credentials-email\"]>div:nth-of-type(2)";
	String _sandboxUsernameSelector = "[data-hook=\"test-credentials-username\"]>div:nth-of-type(2)";
	String _sandboxPassSelector = "[data-hook=\"test-credentials-password\"]>div:nth-of-type(2)";
	String _sandboxCodeSelector = "[data-hook=\"test-credentials-sms-code\"]>div:nth-of-type(2)";

	String _partnerUserInputSelector = "[data-hook=\"auth-input\"]";
	String _partnerpassInputSelector = "[name=\"password\"]";
	String _connectBtnSelector = "[data-hook=\"connect-button\"]";

	String _loadingScreenSelector = "[data-hook=\"connecting\"]";

	String _smsCodeInputSelector = "[data-hook=\"mfa-code-input\"]";
	String _continueBtnSelector = "[data-hook=\"connect-button\"]";

	String _successTextAreaSelector = "[data-hook=\"connection-success\"]";


	public EmulatorPage(Page page) {
		super(page);
	}

	public void searchFor(String term) {
		log.info("searching for partner {}", term);
		page.fill(searchInputSelector, term);
		page.locator(_loadingScreenSelector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
	}

	public void clickResult(String partnerName) throws Exception {
		page.waitForSelector(_searchResultNameSelector);
		List<ElementHandle> results = page.querySelectorAll(_searchResultNameSelector);

		log.info("Sifting trough results");
		for (ElementHandle result : results) {
			if(StringUtils.equalsIgnoreCase(result.textContent().trim(), partnerName)){
				result.click();
				log.info("Result found and clicked");
				return;
			}
		}
		log.error("Desired result was NOT FOUND!!");
		throw new Exception("Desired result was NOT FOUND!!");
	}

	private String getInfoForUser(String user, String infoName) throws Exception {
		log.debug("identifing user in sandbox");
		switch(user) {
			case "Bob Jones":
				page.click(_bobBtnSelector);
				break;
			case "Sarah Longfield":
				page.click(_sarahBtnSelector);
				break;
			case "Joe Burnam":
				page.click(_joeBtnSelector);
				break;
			default:
				throw new Exception("User not found in sandbox");
		}

		log.debug("identifing required info");
		switch(infoName) {
			case "email":
				return page.textContent(_sandboxEmailSelector);
			case "username":
				return page.textContent(_sandboxUsernameSelector);
			case "pass":
				return page.textContent(_sandboxPassSelector);
			case "code":
				return page.textContent(_sandboxCodeSelector);
			default:
				throw new Exception("Info not found in sandbox");
		}



	}

	public void fillCredentialsAndConnect(String user) throws Exception {
		fillPartnerCredentials(getInfoForUser(user, "email"),getInfoForUser(user, "pass"));
		pressConnect();
	}

	public void fillPartnerCredentials(String user, String pass){
		log.info("partner login attempt with user {} and pass {}", user, pass);

		page.fill(_partnerUserInputSelector, user);
		page.fill(_partnerpassInputSelector, pass);
	}

	public void pressConnect(){
		page.click(_connectBtnSelector);
		log.debug("waiting for loading screen");
		page.locator(_loadingScreenSelector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
	}

	public boolean isCodeRequested() {
		page.locator(_smsCodeInputSelector).waitFor();
		return page.isVisible(_smsCodeInputSelector);
	}

	public void fillCode(String user) throws Exception {
		log.info("filling code for user {}", user);
		page.fill(_smsCodeInputSelector, getInfoForUser(user, "code"));
		page.click(_connectBtnSelector);

		page.locator(_loadingScreenSelector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
	}

	public boolean isCompleted() {
		return page.textContent(_successTextAreaSelector).contains("Completed");
	}




}
