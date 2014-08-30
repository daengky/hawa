package cz.pojd.homeautomation.hawa.rooms;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cz.pojd.homeautomation.hawa.graphs.GraphData;
import cz.pojd.rpi.sensors.Reading;

/**
 * Simple POJO to hold information about a room
 * 
 * @author Lubos Housa
 * @since Jul 27, 2014 12:51:36 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDetail {

    private String name;
    private Boolean autoLights;
    private Floor floor;
    private String lastUpdate;
    private Reading temperature;
    private GraphData[] temperatureHistory;

    public RoomDetail() {
    }

    public RoomDetail(RoomDetail copy) {
	name = copy.name;
	temperature = copy.temperature;
	autoLights = copy.autoLights;
	floor = copy.floor;
	lastUpdate = copy.lastUpdate;
	temperatureHistory = copy.temperatureHistory;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Reading getTemperature() {
	return temperature;
    }
    
    public boolean isValidTemperature() {
	return getTemperature()!=null && getTemperature().isValid();
    }

    public void setTemperature(Reading temperature) {
	this.temperature = temperature;
    }

    public Boolean getAutoLights() {
	return autoLights;
    }

    public void setAutoLights(Boolean autoLights) {
	this.autoLights = autoLights;
    }

    public Floor getFloor() {
	return floor;
    }

    public void setFloor(Floor floor) {
	this.floor = floor;
    }

    public String getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    public GraphData[] getTemperatureHistory() {
	return temperatureHistory;
    }

    public void setTemperatureHistory(GraphData[] temperatureHistory) {
	this.temperatureHistory = temperatureHistory;
    }

    @Override
    public String toString() {
	return "RoomDetail [name=" + name + ", autoLights=" + autoLights + ", floor=" + floor + ", lastUpdate=" + lastUpdate + ", temperature="
		+ temperature + ", temperatureHistory=" + Arrays.toString(temperatureHistory) + "]";
    }
}
