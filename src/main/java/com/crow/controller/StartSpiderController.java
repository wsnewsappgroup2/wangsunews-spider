package com.crow.controller;

import com.crow.webmagic.downloader.CrowProxyProvider;
import com.crow.webmagic.pageprocessor.HupuNBAPageProcessor;
import com.crow.webmagic.pageprocessor.SinaSpiderProcessor;
import com.crow.webmagic.pipeline.HupuSpiderPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.proxy.Proxy;


/**
 * Created by CrowHawk on 17/10/8.
 */
@RestController
public class StartSpiderController {
    private static Logger logger = LoggerFactory.getLogger(StartSpiderController.class);
    @Autowired
    HupuSpiderPipeline hupuSpiderPipeline;
    /*
    @Autowired
    ProxyIpMapper proxyIpMapper;
    */
    @GetMapping("/spider")
    public String index() {
        System.setProperty("selenuim_config", "config.ini");
       // System.getProperties().setProperty("webdriver.chrome.driver","/Users/yihua/Downloads/chromedriver.exe");

        /*
        List<ProxyIp> proxyList = proxyIpMapper.findAllProxies();
        proxyList = proxyList.subList(0,10);
        List<Proxy> proxies = new ArrayList<>(proxyList.size());
        for(ProxyIp proxyIp : proxyList) {
            proxies.add(new Proxy(proxyIp.getIp(), proxyIp.getPort()));
        }
        */
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置动态转发代理，使用定制的ProxyProvider
        httpClientDownloader.setProxyProvider(CrowProxyProvider.from(new Proxy("forward.xdaili.cn", 80)));
        Spider hupuSpider ;
        hupuSpider = Spider.create(new HupuNBAPageProcessor());
                hupuSpider.addUrl("https://voice.hupu.com/nba/1");
                hupuSpider.addPipeline(hupuSpiderPipeline);
                hupuSpider.thread(1);
                hupuSpider.start();
                try {
                    Thread.sleep(100000);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                logger.error("hupuSpider stop！");
                hupuSpider.stop();
//        Spider sinaEntSpider;
//        sinaEntSpider = Spider.create(new SinaSpiderProcessor());
////        sinaEntSpider.addUrl("http://ent.sina.com.cn/weibo/");
//        sinaEntSpider.addUrl("https://ent.sina.com.cn/s/m/2019-08-06/doc-ihytcitm7234678.shtml");
//        sinaEntSpider.addPipeline(hupuSpiderPipeline);
//        sinaEntSpider.setDownloader(new SeleniumDownloader("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe"));
//        sinaEntSpider.thread(1);
//        sinaEntSpider.start();
//        try {
//            Thread.sleep(10000);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        logger.error("SinaEnt Spider stop！");
//        sinaEntSpider.stop();
//
        return "爬虫开启";
    }

}

