package net.sourceforge.seleniumflexapi.cal;

import net.sourceforge.seleniumflexapi.AbstractPageDriver;
import net.sourceforge.seleniumflexapi.FlexSelenium;

public class DefaultPageDriver extends AbstractPageDriver {

	public static final int APP_LOAD_TIMEOUT_MS = 50000;
	
	public DefaultPageDriver(FlexSelenium flexSelenium) {
		super(flexSelenium);
	}

}
