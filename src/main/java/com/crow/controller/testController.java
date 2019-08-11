package com.crow.controller;

import com.crow.enums.AlgorithmType;
import com.crow.enums.LabelType;
import com.crow.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName:TestController
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 19:40 2019/8/8
 * @Version: V1.0
 */

@RestController
public class TestController {
    @Autowired
    RecommendService recommendService;
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String test() {
        logger.info("Test Controller Log Info");
        List<Integer> result;
        result= recommendService.getRecommend(1, AlgorithmType.HOT_BASED_RECOMMEND, LabelType.SPORT_COLOUMN,"1");
        logger.info("java得到的结果1"+ Arrays.toString(result .toArray()));
        result= recommendService.getRecommend(1, AlgorithmType.CONTTENT_BASED_RECONMMEND, LabelType.SPORT_COLOUMN,"1");
        logger.info("java得到的结果2"+ Arrays.toString(result .toArray()));
        result= recommendService.getRecommend(1, AlgorithmType.UCF_BASED_RECOMMEND, LabelType.SPORT_COLOUMN,"1");
        logger.info("java得到的结果3"+ Arrays.toString(result .toArray()));

//        result= recommendService.getRecommend(1, AlgorithmType.CONTTENT_BASED_RECONMMEND, LabelType.SPORT_COLOUMN,"1");
//        logger.info("java得到的结果"+ Arrays.toString(result .toArray()));

//        Process proc;
//        try {
//            logger.info("进入try");
//            proc = Runtime.getRuntime().exec("python /usr/wsnews/py/mostPopular_recommend.py a b");
//            logger.info("执行过python语句");
//            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            logger.info("打开io");
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                logger.info(line);
//            }
//            in.close();
//            proc.waitFor();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally {
//            logger.info("finally");
//        }

        return "wsnewsapp is running!";
    }
}
