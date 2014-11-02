package cz.pojd.homeautomation.model.rooms;

import java.util.List;

import cz.pojd.homeautomation.model.refresh.Refreshable;

/**
 * DAO for Rooms
 * 
 * @author Lubos Housa
 * @since Jul 27, 2014 6:21:24 PM
 */
public interface RoomsDAO extends Refreshable {

    /**
     * Get all rooms
     * 
     * @return list of all rooms
     */
    List<RoomDetail> query();

    /**
     * Save the room
     * 
     * @param room
     *            room to save
     * @return true if the save was successful
     */
    void save(RoomDetail roomDetail);

    /**
     * Get the detail of the in passed room
     * 
     * @param roomName
     * @return
     */
    RoomDetail get(String roomName);
}