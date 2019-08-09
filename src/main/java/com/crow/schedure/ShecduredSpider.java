package com.crow.schedure;

import com.crow.dao.NewsListMapper;
import com.crow.utils.FreshUtil;
import com.crow.webmagic.downloader.CrowProxyProvider;
import com.crow.webmagic.pageprocessor.AbstractProcessor;
import com.crow.webmagic.pageprocessor.ChinaPeapleProcessor;
import com.crow.webmagic.pageprocessor.HupuNBAPageProcessor;
import com.crow.webmagic.pageprocessor.SinaSpiderProcessor;
import com.crow.webmagic.pipeline.HupuSpiderPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.proxy.Proxy;

/**
 * @ClassName:ShecduredSpider
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 19:40 2019/8/8
 * @Version: V1.0
 */
public class ShecduredSpider {
    private static Logger logger = LoggerFactory.getLogger(ShecduredSpider.class);
    @Autowired
    HupuSpiderPipeline hupuSpiderPipeline;
    @Autowired
    NewsListMapper newsListMapper;
    @Scheduled(cron = "0 0 0-23/1 * * *") // 0-24点每整点执行一次
    public void shecduredRunSpider(){
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置动态转发代理，使用定制的ProxyProvider
        httpClientDownloader.setProxyProvider(CrowProxyProvider.from(new Proxy("forward.xdaili.cn", 80)));
        startChinaSpider();
        startHupuSpider();
        try{
            startSinaSpider();
        }
        catch (Exception e){
            logger.error("新浪chrome模拟浏览失败");
        }
        waitSpider(10000);
        FreshUtil.freshline=newsListMapper.selectMaxId();

    }
    private void startSinaSpider(){
        System.setProperty("selenuim_config", "config.ini");
        Spider SinaSpider ;
        SinaSpider = unionConfigSpider(new SinaSpiderProcessor(),
                "http://ent.sina.com.cn/weibo/",
                new HupuSpiderPipeline(),
                1);
        SinaSpider.setDownloader(new SeleniumDownloader("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe"));
        SinaSpider.start();
        logger.info("Spider start！");
        waitSpider(10000);
        SinaSpider.stop();
        logger.info("Spider stop！");
    }

    private void startChinaSpider() {
        Spider chinaSpider ;
        chinaSpider = unionConfigSpider(new ChinaPeapleProcessor(),
                "http://sousuo.gov.cn/column/30611/0.htm",
                new HupuSpiderPipeline(),
                1);
        chinaSpider.start();
        logger.info("Spider start！");
        waitSpider(10000);
        chinaSpider.stop();
        logger.info("Spider stop！");
    }
    private void startHupuSpider() {
        Spider hupuSpider ;
        hupuSpider = unionConfigSpider(new HupuNBAPageProcessor(),
                "https://voice.hupu.com/nba/1",
                new HupuSpiderPipeline(),
                1);
        hupuSpider.start();
        waitSpider(10000);
        logger.error("hupuSpider stop！");
        hupuSpider.stop();

    }
//    "https://voice.hupu.com/nba/1"
    private Spider unionConfigSpider(AbstractProcessor processor,String Url,HupuSpiderPipeline pipline,Integer threadnum){
        Spider spider;
        spider = Spider.create(processor);
        spider.addUrl(Url);
        spider.addPipeline(pipline);
        spider.thread(threadnum);
        return spider;
    }
    private void waitSpider(int time){
        try {
            Thread.sleep(time);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



}
