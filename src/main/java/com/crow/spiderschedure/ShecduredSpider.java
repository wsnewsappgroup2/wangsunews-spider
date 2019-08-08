package com.crow.spiderschedure;

import com.crow.controller.StartSpiderController;
import com.crow.webmagic.downloader.CrowProxyProvider;
import com.crow.webmagic.pageprocessor.AbstractProcessor;
import com.crow.webmagic.pageprocessor.ChinaPeapleProcessor;
import com.crow.webmagic.pageprocessor.HupuNBAPageProcessor;
import com.crow.webmagic.pipeline.HupuSpiderPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;


public class ShecduredSpider {
    private static Logger logger = LoggerFactory.getLogger(StartSpiderController.class);
    @Autowired
    HupuSpiderPipeline hupuSpiderPipeline;
    private void startSinaSpider()

    private void startChinaSpider()
    {
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
    private void startHupuSpider()
    {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置动态转发代理，使用定制的ProxyProvider
        httpClientDownloader.setProxyProvider(CrowProxyProvider.from(new Proxy("forward.xdaili.cn", 80)));
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
