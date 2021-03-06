package cz.pojd.homeautomation.model.outdoor.factory;

import cz.pojd.homeautomation.model.outdoor.Outdoor;
import cz.pojd.homeautomation.model.spring.OutdoorSpecification;

/**
 * Factory for creating instances of outdoor
 *
 * @author Lubos Housa
 * @since Sep 11, 2014 12:14:32 AM
 */
public interface OutdoorFactory {

    /**
     * Create new instance of outdoor given the specification
     * 
     * @param outdoorSpecification
     *            specification of the outdoor to create
     * @return new instance of outdoor
     */
    Outdoor create(OutdoorSpecification outdoorSpecification);
}
