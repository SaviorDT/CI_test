package jUnit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Test2 {

	@org.junit.jupiter.api.Test
	void test() {
		assertEquals(1, 1);
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("�o�ӷ|��")
	void test2() {
		assertEquals(1, 2);
	}

}
