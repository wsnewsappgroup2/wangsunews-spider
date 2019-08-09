package com.crow.webmagic.pageprocessor;

import com.crow.utils.ProxyGeneratedUtil;
import com.crow.utils.URLGeneratedUtil;
import com.crow.utils.UserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 * @ClassName:CrowProxyProvider
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 16:30 2019/8/8
 * @Version: V1.0
 */

public class ChinaPeapleProcessor extends BaseProcessor {
    public static final String URL_LIST = "http://sousuo.gov.cn/column/30611/\\d+.htm";
    private static final String ORDER_NUM = "ZF201710169692T66jkr";//讯代理订单号
    private static final String SECRET = "3b23ace31a2447baa44d624e9c5fd0f5";//讯代理密码

    //抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
    private Site site = Site.me()
            .addHeader("Proxy-Authorization", ProxyGeneratedUtil.authHeader(ORDER_NUM, SECRET, (int) (new Date().getTime()/1000)))//设置代理
            .setDisableCookieManagement(true)
            .setCharset("UTF-8")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setSleepTime(new Random().nextInt(20)*1000)
            .setUserAgent(UserAgentUtil.getRandomUserAgent());

    @Override
    public void process(Page page) {

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            crawlList(page);
        }
        //主题帖第一页
        if (!(page.getUrl().regex(URL_LIST).match())) {
            crawlPost(page);
        }


    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 抓取列表页信息
     * @param page 当前页面对象
     */
    private void crawlList(Page page) {//抓取论坛列表页
//       System.out.print(page.getHtml());
        List<String> listUrls1 = page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[2]/ul//li//h4//a/@href").all();
        listUrls1.forEach(e -> URLGeneratedUtil.generatePostURL(e));
        page.addTargetRequests(listUrls1);//把所有帖子页的URL加入抓取队列
        listUrls1.forEach(e -> page.addTargetRequests(URLGeneratedUtil.generatePostURLs(e)));
        List<String> listUrls2 = page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[3]/ul/li[11]/a/@href").all();
        listUrls2.forEach(e -> URLGeneratedUtil.generatePostURL(e));
        page.addTargetRequests(listUrls2);//把所有帖子页的URL加入抓取队列

    }

    /**
     * 抓取新闻页内信息
     * @param page 当前页面对象
     */
    private void crawlPost(Page page) {
//        System.out.print(page.getHtml());
//        page.addTargetRequests(page.getHtml().links().regex(URL_USER).all());//把所有用户主页的URL加入抓取队列
        //新闻标题
        String title = page.getHtml().xpath("//*[@class=\"article oneColumn pub_border\"]/h1/text()").toString();
        //新闻来源
        String comFrom = page.getHtml().xpath("//span[@class=\"font\"]/text()").toString();
        if(comFrom!=null)
        {
            int length = comFrom.length();
            comFrom=comFrom.substring(3,length);
        }
        //新闻内容
        String content = page.getHtml().xpath("//*[@id=\"UCAP-CONTENT\"]/p/text()").all().toString();
        //新闻主图()
        String mainImage = page.getHtml().xpath("/html/body/div/div[1]/div[1]/a/img[2]/@src").toString();
        //新闻时间
//        String newsDate =  page.getHtml().xpath("/html/head/meta[@property=\"article:published_time\"]/@content").toString();
        String newsDate =  page.getHtml().xpath("//div[@lass=\"pages-date\"]/text()").toString();
        //评论数
        String sourceCommentNum =  page.getHtml().xpath("//*[@id=\"bottom_sina_comment\"]/div[1]/div[1]/span[1]/em[1]/a/text()").toString();
        //主题词
        String topicWord =null;
        if(title.length()>4){
             topicWord = title.substring(0,3);
        }


        setPost(title, content, comFrom, mainImage,newsDate,sourceCommentNum,
                topicWord,page,"yyyy-MM-dd HH:mm:ss","politics");

    }



}
