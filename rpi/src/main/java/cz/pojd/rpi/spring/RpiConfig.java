package cz.pojd.rpi.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.i2c.I2CBus;

import cz.pojd.rpi.controls.CameraControl;
import cz.pojd.rpi.controls.MjpegStreamerCameraControl;
import cz.pojd.rpi.sensors.gpio.Gpio;
import cz.pojd.rpi.sensors.gpio.GpioImpl;
import cz.pojd.rpi.sensors.gpio.MockGpio;
import cz.pojd.rpi.system.RuntimeExecutor;
import cz.pojd.rpi.system.RuntimeExecutorImpl;

/**
 * Main configuration for spring in rpi project
 * 
 * @author Lubos Housa
 * @since Jul 23, 2014 2:34:22 AM
 */
@Configuration
@ComponentScan("cz.pojd.rpi")
public class RpiConfig {
    private static final Log LOG = LogFactory.getLog(RpiConfig.class);

    /**
     * Detects whether this is a new RasPI or not (drives further logic in this project)
     * 
     * @return true if so, false otherwise
     */
    @Bean
    public boolean newRasPI() {
	return true;
    }

    @Bean
    public List<String> fileSystems() {
	List<String> fileSystems = new ArrayList<>();
	fileSystems.add("/");
	fileSystems.add("/boot");
	fileSystems.add("/var/log");
	fileSystems.add("/tmp");
	return fileSystems;
    }

    @Bean
    public Runtime runtime() {
	return Runtime.getRuntime();
    }

    @Bean
    public RuntimeExecutor runtimeExecutor() {
	return new RuntimeExecutorImpl();
    }

    @Bean
    public CameraControl cameraControl() {
	return new MjpegStreamerCameraControl("/etc/init.d/mjpg-streamer start", "/etc/init.d/mjpg-streamer stop", 2);
    }

    public GpioProvider getMCP23017Provider(int address) {
	try {
	    return new MCP23017GpioProvider(newRasPI() ? I2CBus.BUS_1 : I2CBus.BUS_0, address);
	} catch (IOException | UnsatisfiedLinkError e) {
	    LOG.error("Unable to locate MCP23017 at address " + String.format("0x%x", address) + ", using default provider instead.", e);
	    return gpio().getDefaultProvider();
	}
    }

    @Bean
    public Gpio gpio() {
	try {
	    return new GpioImpl(GpioFactory.getInstance());
	} catch (UnsatisfiedLinkError e) {
	    LOG.error("Unable to create the GPIO controller. Using a mock one instead.", e);
	    return new MockGpio();
	}
    }
}
