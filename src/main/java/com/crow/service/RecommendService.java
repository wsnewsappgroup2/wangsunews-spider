package com.crow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


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
//
//    public List<Integer> defaultRecommend(Integer openid) {
//
//    }

    private List<Integer> getRecommend(Integer openid, String label) throws Exception {
        List<Integer> reconmmendLists = new ArrayList<Integer>();
        String openId = null;
        try {
            openId = openid.toString();
        }catch (Exception e){
            logger.error("openid 转换出错！");
        }
        String[] arguments = new String[] {"python","/mostPopular_recommend.py",openId,label};   //这里构建要在cmd中输入的参数，第二个参数表示.py文件的路径，第二个之后的参数都表示要传给.py文件的参数，可以根据.py文件的需求写
        try {
            Process process = Runtime.getRuntime().exec(arguments);//这个方法相当于在cmd中输入 python D:\\ccc\\1.py D:/ccc/
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                line.substring(1,line.length()-1);
                String[] recommendIds=line.split(",");
                for(int i=0;i<recommendIds.length;i++){
                    if(recommendIds[i]!=null){
                        reconmmendLists.add(Integer.valueOf(recommendIds[i]));
                    }
                }
                //这里把字符串解析成数组后再转换成List<integer>类型
                logger.info("算法结果："+line);  //在java编译器中打印.py文件的执行结果
            }
            in.close();
            int re = process.waitFor();//因为是process这个进程调用.py文件， 所以要将当前进程阻塞等待.py文件执行完毕， 若果.py文件成功运行完毕，此方法将返回0，代表执行成功
            System.out.println(re); //执行成功的话这里会打印一个0，否则会打印1，2或者其他数字
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reconmmendLists;
    }

    public static void main(String[] args) {
        RecommendService recommendService = new RecommendService();
        try {
            recommendService.getRecommend(1,"sport");
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }
}

