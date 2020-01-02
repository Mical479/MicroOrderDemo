package com.test.mygateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MygatewayApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void main(String[] args) {
		String a = "/abc/static";
		System.out.println(a.contains("abc"));
	}

}
