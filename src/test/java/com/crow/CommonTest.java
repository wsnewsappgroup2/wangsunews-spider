package com.crow;

import com.crow.dao.UserLabelAlgorithmMapper;
import com.crow.entity.Algorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    @Autowired
    UserLabelAlgorithmMapper userLabelAlgorithmMapper;

    @Test
    public void test(){
        System.out.println("原时间 " + new Date());
        System.out.println("修改后时间 " + new Date());
    };

    @Test
    public void TT(){
        Integer d= userLabelAlgorithmMapper.selectAlgorithmUsedCount(1);
        userLabelAlgorithmMapper.updateUserLabelAlgorithm(1,1,1);
        List<Algorithm>s=userLabelAlgorithmMapper.selectCloumnedList(1);
        return;
    }
}
