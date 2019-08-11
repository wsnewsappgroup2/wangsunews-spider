package com.crow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    @Test
    public void test(){
        System.out.println("原时间 " + new Date());
        System.out.println("修改后时间 " + new Date());
    };
}
