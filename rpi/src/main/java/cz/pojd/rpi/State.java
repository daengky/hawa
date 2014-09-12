package cz.pojd.rpi;

/**
 * Simple wrapper for a sensor/control state
 *
 * @author Lubos Housa
 * @since Sep 10, 2014 11:39:04 PM
 */
public class State {

    private boolean initiated;
    private boolean enabled;
    private boolean on;
    private boolean switchable;

    public State() {
    }

    public State(Builder builder) {
	this.initiated = builder.initiated;
	this.enabled = builder.enabled;
	this.on = builder.on;
	this.switchable = builder.switchable;
    }

    public boolean isInitiated() {
	return initiated;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public boolean isOn() {
	return on;
    }

    public boolean isSwitchable() {
	return switchable;
    }

    @Override
    public String toString() {
	return "State [initiated=" + initiated + ", enabled=" + enabled + ", on=" + on + ", switchable=" + switchable + "]";
    }

    public static Builder newBuilder() {
	return new Builder();
    }

    public static class Builder {
	private boolean initiated;
	private boolean enabled;
	private boolean on;
	private boolean switchable;

	private Builder() {
	}

	public Builder initiated(boolean initiated) {
	    this.initiated = initiated;
	    return this;
	}

	public Builder enabled(boolean enabled) {
	    this.enabled = enabled;
	    return this;
	}

	public Builder on(boolean on) {
	    this.on = on;
	    return this;
	}

	public Builder switchable(boolean switchable) {
	    this.switchable = switchable;
	    return this;
	}

	public State build() {
	    return new State(this);
	}
    }
}
