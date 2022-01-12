package pages;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArgylePage {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public SideNav sideNav;
	protected final Page page;

	public ArgylePage(Page page) {
		this.page = page;
		this.sideNav = new SideNav(page);
	}

	//	generic method to assert Argyle page is loaded
	public boolean isLoaded() {
		log.info("Checking page {} for visible elements", this.getClass().getSimpleName());

		Field[] fields = this.getClass().getDeclaredFields();

//		collect all declared fields that are of type String and contain "Selector" in the name
//		convention is made that selectors with names that start with "_" are not to be checked, they might not be visible unless some action was triggered
		boolean isLoaded = true;
		for (Field field : fields) {
			String name = field.getName();
			String type = field.getType().getSimpleName();
			if (name.contains("Selector") && !name.startsWith("_") && type.equals(String.class.getSimpleName())) {
				log.debug("field with name {} will be checked", name);
				try {
					String value = field.get(this).toString();
					page.locator(value).waitFor();
					if (!page.isVisible(value)) {
						log.error("field with name {} WAS NOT FOUND", name);
						isLoaded = false;
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				log.debug("field with name {} will NOT be checked", name);
			}
		}

		return isLoaded;
	}


}
