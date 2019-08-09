package com.crow;

import com.auth0.jwt.JWT;
import com.crow.controller.StartSpiderController;
import com.crow.utils.JwtUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HupuspiderApplicationTests {
	//@Autowired
	StartSpiderController startSpiderController;

	@Test
	public void testSignToken()throws Exception{
		String openId="o5pmm5O84n2_9_VpvaEb2WBe4iAE";
		String token=JwtUtil.sign(openId);
		return;
	}

	// @Test
/*	public void testFunc(){
		String result = startSpiderController.index();
		Assert.assertSame("爬虫开启", result);
	}*/
}
