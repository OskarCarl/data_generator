package Generator;

import Main.SettingStore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by oskar on 15/01/17.
 */
class DataOutputBufferTest {
	DataOutputBuffer testObject;

	@BeforeAll
	static void setUp() {
		SettingProvider.set(new SettingStore());
	}

	@BeforeEach
	void setUpEach() {
		this.testObject = new DataOutputBuffer(2);
	}

	@Test
	void append() {
		this.testObject.append(new SensorData(0.3f, 0.5f, "C"));
	}

	@Test
	void isFull() {
		this.testObject.append(new SensorData(0.3f, 0.5f, "C"));
		boolean result1 = this.testObject.isFull();
		this.testObject.append(new SensorData(0.2f, 0.5f, "C"));
		boolean result2 = this.testObject.isFull();
		assertFalse(result1);
		assertTrue(result2);
	}

	@Test
	void isEmpty() {
		DataOutputBuffer testObject = new DataOutputBuffer();
		boolean result1 = testObject.isEmpty();
		testObject.append(new SensorData(0.3f, 0.5f, "C"));
		boolean result2 = testObject.isEmpty();
		assertTrue(result1);
		assertFalse(result2);
	}

	@Test
	void getContents() {
		String expected = "0000 currentValue:0.3,precision:0.5,measurementUnit:C0001 currentValue:0.2,precision:0.5,measurementUnit:C";
		this.testObject.append(new SensorData(0.3f, 0.5f, "C"));
		this.testObject.append(new SensorData(0.2f, 0.5f, "C"));
		List<String> result = this.testObject.getContents();
		assertEquals(expected, result.get(0) + result.get(1));
	}

}