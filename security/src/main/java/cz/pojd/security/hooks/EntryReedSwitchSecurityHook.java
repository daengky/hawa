package cz.pojd.security.hooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.pojd.homeautomation.model.rooms.Entry;
import cz.pojd.homeautomation.model.rooms.Room;
import cz.pojd.homeautomation.model.rooms.RoomsDAO;
import cz.pojd.security.controller.Controller;
import cz.pojd.security.event.Type;

/**
 * Hook for window/door reed switches - is responsible for making sure the reed switches are hooked up to the security system
 *
 * @author Lubos Housa
 * @since Dec 10, 2014 12:04:58 AM
 */
public class EntryReedSwitchSecurityHook {
    private static final Log LOG = LogFactory.getLog(EntryReedSwitchSecurityHook.class);

    public EntryReedSwitchSecurityHook(RoomsDAO roomsDAO, Controller controller) {
	LOG.info("Hooking up reed switches to the security system...");

	for (Room room : roomsDAO.queryRooms()) {
	    for (Entry entry : room.getEntries()) {
		setupReedSwitch(entry, controller);
	    }
	}

	LOG.info("Done");
    }

    private void setupReedSwitch(Entry entry, Controller controller) {
	LOG.info("Hooking up " + entry + " ...");
	Type type;
	switch (entry.getSpecification()) {
	case GARAGE_DOOR:
	    type = Type.garageOpened;
	    break;
	case MAIN_DOOR:
	    type = Type.doorOpened;
	    break;
	default:
	    type = Type.windowOpened;
	}
	entry.getReedSwitch().addObserver(
		new SensorSecurityObserver(entry, controller, type, false));
    }
}
