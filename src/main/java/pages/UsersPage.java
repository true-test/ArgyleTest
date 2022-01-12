package pages;

import com.microsoft.playwright.Page;

public class ConsolePage extends ArgylePage{

	String inviteUsersBtnSelector = "[data-hook=\"connect-user\"]";


	public ConsolePage(Page page) {
		super(page);
	}



}
