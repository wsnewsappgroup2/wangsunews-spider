package com.crow;

import com.crow.controller.StartSpiderController;
import com.crow.dao.NewsListMapper;
import com.crow.entity.CommentDetail;
import com.crow.entity.NewsList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HupuspiderApplicationTests {
	@Autowired
	StartSpiderController startSpiderController;

	@Test
	public void testFunc(){
		String result = startSpiderController.index();
		Assert.assertSame("爬虫开启", result);
	}

}
