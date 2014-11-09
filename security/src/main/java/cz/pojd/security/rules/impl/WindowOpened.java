package cz.pojd.security.rules.impl;

import cz.pojd.security.controller.SecurityMode;
import cz.pojd.security.event.SecurityEvent;
import cz.pojd.security.event.Type;

public class WindowOpened extends AbstractRule {

    @Override
    public boolean isSecurityBreach(SecurityEvent event) {
	return Type.windowOpened == event.getType();
    }

    @Override
    public boolean isApplicable(SecurityMode securityMode) {
	return SecurityMode.EMPTY_HOUSE == securityMode;
    }

    @Override
    public String getDescription() {
	return "Window opened (empty house)";
    }
}
