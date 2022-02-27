package com.example.democommon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class Demo1ApplicationTests {

	@Test
	void shouldCreate() {
		new Calc().add(1, 2);
	}

}
