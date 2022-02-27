package jUnit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class Test {

	@org.junit.jupiter.api.Test
	void test() {
		assertEquals(1, 1);
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("³o­Ó·|¿ù")
	void test2() {
		assertEquals(1, 2);
	}

}
