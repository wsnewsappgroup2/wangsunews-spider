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
public class SinaSpiderProcessor extends BaseProcessor {
    public static final String URL_LIST = "http://ent.sina.com.cn/weibo/";
    private static final String ORDER_NUM = "ZF201710169692T66jkr";//讯代理订单号
    private static final String SECRET = "3b23ace31a2447baa44d624e9c5fd0f5";//讯代理密码

    //抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
    private Site site = Site.me()
            .addHeader("Proxy-Authorization", ProxyGeneratedUtil.authHeader(ORDER_NUM, SECRET, (int) (new Date().getTime()/1000)))//设置代理
            .setDisableCookieManagement(true)
            .setCharset("UTF-8")
            .setTimeOut(3000)
            .setRetryTimes(3)
            .setSleepTime(new Random().nextInt(20)*10)
            .setUserAgent(UserAgentUtil.getRandomUserAgent());

    @Override
    public void process(Page page) {

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            crawlList(page);
        }
        //主题帖第一页
        if (!(page.getUrl().regex(URL_LIST).match())) {
            //page.addTargetRequests(URLGeneratedUtil.generatePostURLs(URL_POST_1));
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
//        System.out.print(page.getHtml());
        List<String> listUrls1 = page.getHtml().links().regex("http://slide.ent.sina.com.cn/y/w/slide_4_704_\\d+\\.html").all();
        List<String> listUrls2 = page.getHtml().links().regex("http://slide.ent.sina.com.cn/star/w/slide_4_704_\\d+\\.html").all();
        listUrls1.forEach(e -> URLGeneratedUtil.generatePostURL(e));
        listUrls2.forEach(e -> URLGeneratedUtil.generatePostURL(e));

        page.addTargetRequests(listUrls1);//把所有帖子页的URL加入抓取队列
        page.addTargetRequests(listUrls2);//把所有帖子页的URL加入抓取队列

        listUrls1.forEach(e -> page.addTargetRequests(URLGeneratedUtil.generatePostURLs(e)));
        listUrls2.forEach(e -> page.addTargetRequests(URLGeneratedUtil.generatePostURLs(e)));

    }

    /**
     * 抓取新闻页内信息
     * @param page 当前页面对象
     */
    private void crawlPost(Page page) {
        System.out.print(page.getHtml());
//        page.addTargetRequests(page.getHtml().links().regex(URL_USER).all());//把所有用户主页的URL加入抓取队列
        //新闻标题
        String title = page.getHtml().xpath("/html/head/title/text()").toString();
        //新闻来源
        String comFrom = "新浪娱乐";
//        String comFrom = page.getHtml().xpath("/html/head/meta[@name=\"mediaid\"]/@content").toString();
        //新闻内容
        String content = page.getHtml().xpath("/html/head/meta[@name=\"description\"]/@content").toString();
        //新闻主图()
        String mainImage = page.getHtml().xpath("//div[@class=\"swi-hd\"]//img/@data-src").toString();
        //新闻时间
//        String newsDate =  page.getHtml().xpath("/html/head/meta[@property=\"article:published_time\"]/@content").toString();
        String newsDate =  page.getHtml().xpath("//div[@class=\"swi-hd\"]/h3/p/text()").toString();
        //评论数
        String sourceCommentNum =  page.getHtml().xpath("//*[@id=\"bottom_sina_comment\"]/div[1]/div[1]/span[1]/em[1]/a/text()").toString();
        //主题词
        String topicWord =  page.getHtml().xpath("/html/head/meta[@name=\"keywords\"]/@content").toString();

        setPost(title, content, comFrom, mainImage,newsDate,sourceCommentNum,topicWord,page,"yyyy年MM月dd日 HH:mm:ss","ent");

    }



}
