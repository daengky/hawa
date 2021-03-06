package cz.pojd.rpi.sensors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import cz.pojd.rpi.sensors.Reading.Type;
import cz.pojd.rpi.sensors.i2c.Bmp180BarometricSensor;

public class Bmp180BarometricSensorTestCase {

    @Mocked
    private I2CFactory i2cFactory;
    @Mocked
    private I2CBus i2cBus;
    @Mocked
    private I2CDevice device;

    @Test
    public void testBmp180BarometricSensorNewRaspi() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(withEqual(I2CBus.BUS_1));
		result = i2cBus;

		i2cBus.getDevice(withEqual(0x77));
		result = device;

		// calibration
		device.read(anyInt);
		result = 1;
		times = 22;
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0);
	assertTrue(sensor.isInitiated());
    }

    @Test
    public void testBmp180BarometricSensorOldRaspi() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(withEqual(I2CBus.BUS_0));
		result = i2cBus;

		i2cBus.getDevice(withEqual(0x77));
		result = device;
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(false, 0);
	assertTrue(sensor.isInitiated());
    }

    @Test
    public void testBmp180BarometricSensorI2CError() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = new UnsatisfiedLinkError("Some error talking to system libs to get I2C handle");
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0);
	assertFalse(sensor.isInitiated());
    }

    @Test
    public void testBmp180BarometricSensorI2CDeviceError() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = i2cBus;

		i2cBus.getDevice(withEqual(0x77));
		result = new IOException("Some error talking to system libs to get I2C handle");
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0);
	assertFalse(sensor.isInitiated());
    }

    @Test
    public void testReadAllInitFailedBefore() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = new UnsatisfiedLinkError("Some error talking to system libs to get I2C handle");
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0);
	assertTrue(sensor.readAll().isEmpty());
    }

    @Test
    public void testReadInitFailedBefore() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = new UnsatisfiedLinkError("Some error talking to system libs to get I2C handle");
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0);
	Reading reading = sensor.read();
	assertEquals(0, (long) reading.getDoubleValue());
	assertEquals(false, reading.isValid());
	assertEquals(Type.temperature, reading.getType());
    }

    @Test
    public void testReadAllDetectsInvalidTemperature() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = i2cBus;

		i2cBus.getDevice(anyInt);
		result = device;

		device.read(anyInt);
		result = 1;
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0) {
	    protected double readTemperature() throws IOException {
		return -89;
	    }
	};
	List<Reading> result = sensor.readAll();
	assertNotNull(result);
	assertEquals(2, result.size());

	Reading first = result.get(0);
	assertEquals(Type.temperature, first.getType());
	assertEquals(0, first.getDoubleValue(), 0.001); // would only return 0 if not valid
	assertFalse(first.isValid());
    }

    @Test
    public void testReadAllDetectsInvalidPressure() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = i2cBus;

		i2cBus.getDevice(anyInt);
		result = device;

		device.read(anyInt);
		result = 1;
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0) {
	    protected double readPressure() throws IOException {
		return 750;
	    }
	};
	List<Reading> result = sensor.readAll();
	assertNotNull(result);
	assertEquals(2, result.size());

	Reading second = result.get(1);
	assertEquals(Type.pressure, second.getType());
	assertEquals(0, second.getDoubleValue(), 0.001); // would only return 0 if not valid
	assertFalse(second.isValid());
    }

    @Test
    public void testReadAllOK() throws IOException {
	new NonStrictExpectations() {
	    {
		I2CFactory.getInstance(anyInt);
		result = i2cBus;

		i2cBus.getDevice(anyInt);
		result = device;

		device.read(anyInt);
		result = 1;
	    }
	};
	Bmp180BarometricSensor sensor = new Bmp180BarometricSensor(true, 0) {
	    // override the validation of pressure to get the raw value calculated by the algorithm
	    protected Reading translatePressure(double pressure) {
		return Reading.newBuilder().type(Type.pressure).doubleValue(pressure).units("HPa").build();
	    }
	};
	List<Reading> result = sensor.readAll();
	assertNotNull(result);
	assertEquals(2, result.size());

	Reading first = result.get(0);
	assertEquals(Type.temperature, first.getType());
	// this value was just find out from the very first invocation of the test - is used to avoid regression in the algorithm
	assertEquals("12.80°C", first.getStringValue());

	Reading second = result.get(1);
	assertEquals(Type.pressure, second.getType());
	// this value was just find out from the very first invocation of the test - is used to avoid regression in the algorithm
	assertEquals("127.51HPa", second.getStringValue());
    }
}
