package com.crow.service;

import com.crow.enums.AlgorithmType;
import com.crow.enums.LabelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName:CrowProxyProvider
 * @Author: wuy2
 * @Description: TODO
 * @Date: Created in 16:30 2019/8/8
 * @Version: V1.0D
 */
@Service
public class RecommendService {
    private static Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Value("${pythonroute.python-package-root}")
    private String pythonRoute;
//
//    public List<Integer> defaultRecommend(Integer openid) {
//
//    }

    public List<Integer> getRecommend(Integer userid, AlgorithmType algorithm, LabelType labelType,String operation)  {
        List<Integer> reconmmendLists = new ArrayList<Integer>();
        String useridstr = null;
        try {
            useridstr = userid.toString();
        }catch (Exception e){
            logger.error("userid 转换出错！");
        }
        Process process;
        String line =null;
        BufferedReader in;
        String[] arguments = new String[] {"python",pythonRoute+(algorithm.getAlgorithmType()),useridstr,labelType.getLabel(),operation};   //这里构建要在cmd中输入的参数，第二个参数表示.py文件的路径，第二个之后的参数都表示要传给.py文件的参数，可以根据.py文件的需求写
        String[] arguments1 = new String[] {"python",pythonRoute+(algorithm.getAlgorithmType()),useridstr,labelType.getLabel(),operation};   //这里构建要在cmd中输入的参数，第二个参数表示.py文件的路径，第二个之后的参数都表示要传给.py文件的参数，可以根据.py文件的需求写
        try {
            process = Runtime.getRuntime().exec(arguments);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            Process process = Runtime.getRuntime().exec(arguments);//这个方法相当于在cmd中输入 python D:\\ccc\\1.py D:/ccc/
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                line = line.substring(1,line.length()-1);
                System.out.println(line);
                if(line!=null) {
                    reconmmendLists = Arrays.asList(line.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                }
                in.close();
                int re = process.waitFor();//因为是process这个进程调用.py文件， 所以要将当前进程阻塞等待.py文件执行完毕， 若果.py文件成功运行完毕，此方法将返回0，代表执行成功
                System.out.println(re); //执行成功的话这里会打印一个0，否则会打印1，2或者其他数字

            }
        }
        catch (Exception e)
        {
            logger.error("pyhon执行异常！");
            e.printStackTrace();
        }

        return reconmmendLists;
    }

    public static void main(String[] args) {
        String ids= "746, 747, 748, 752, 500, 503, 745, 489, 449, 754";
        List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        System.out.println(Arrays.toString(listIds .toArray()));
    }
}

