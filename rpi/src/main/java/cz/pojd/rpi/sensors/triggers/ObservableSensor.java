package cz.pojd.rpi.sensors.triggers;

import java.util.Observer;

import cz.pojd.rpi.sensors.Sensor;

/**
 * Special kind of sensor allowing adding observers to it and letting them get notified any time the reading of the sensor changes. Implementations of
 * this interface guarantee that they are immutable (at least from the hashCode/equals perspective) and can therefore be safely used as keys into Maps
 * 
 * @author Lubos Housa
 * @since Aug 10, 2014 10:13:47 AM
 */
public interface ObservableSensor extends Sensor {

    /**
     * Add new observer to be notified any time this sensor reading is changed. When that heppens, the Observer's update method is invoked with
     * PinState as the argument (the new value of the sensor)
     * 
     * @param o
     *            observer to observe the changes
     */
    public void addObserver(Observer o);
}
