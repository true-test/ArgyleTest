package pages;

import com.microsoft.playwright.Page;

public class SideNav extends ArgylePage{

	String logouLnkSelector = "[data-hook=\"console-log-out\"]";
	String recruitSectionExpandSelector = "[data-hook=\"client-dropdown\"]";

	public SideNav(Page page) {
		super(page);
	}


}
