package com.crow.webmagic.pageprocessor;

import com.crow.utils.ProxyGeneratedUtil;
import com.crow.utils.URLGeneratedUtil;
import com.crow.utils.UserAgentUtil;
import org.ansj.splitWord.analysis.ToAnalysis;
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
public class HupuNBAPageProcessor extends BaseProcessor {//修改改类，定制自己的抽取逻辑

    public static final String URL_LIST = "https://voice\\.hupu\\.com/nba/\\d+";
//    public static final String URL_POST = "https://voice\\.hupu\\.com/nba\\d+\\-\\d+.html";
    public static final String URL_POST_1 = "https://voice\\.hupu\\.com/nba/\\d+\\.html";
//    public static final String URL_USER = "https://my\\.hupu\\.com/\\d+";
    public static final int URL_LENGTH = "https://voice.hupu.com/nba/".length();
    private static final String ORDER_NUM = "ZF201710169692T66jkr";//讯代理订单号
    private static final String SECRET = "3b23ace31a2447baa44d624e9c5fd0f5";//讯代理密码

    //抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
    private Site site = Site.me()
            .addHeader("Proxy-Authorization",ProxyGeneratedUtil.authHeader(ORDER_NUM, SECRET, (int) (new Date().getTime()/1000)))//设置代理
            .setDisableCookieManagement(true)
            .setCharset("UTF-8")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setSleepTime(new Random().nextInt(20)*100)
            .setUserAgent(UserAgentUtil.getRandomUserAgent());

    @Override
    public void process(Page page) {

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            crawlList(page);
        }
        //主题帖第一页
        if (page.getUrl().regex(URL_POST_1).match()) {
            //page.addTargetRequests(URLGeneratedUtil.generatePostURLs(URL_POST_1));
            crawlPost(page);
        }

    }

    @Override
    public Site getSite() {
        //Set<Integer> acceptStatCode = new HashSet<>();
        //acceptStatCode.add(200);
        //site = site.setAcceptStatCode(acceptStatCode).addHeader("Accept-Encoding", "/");

        return site;
    }

    /**
     * 抓取列表页信息
     * @param page 当前页面对象
     */
    private void crawlList(Page page) {//抓取论坛列表页
        List<String> listUrls = page.getHtml().links().regex("https://voice\\.hupu\\.com/nba/\\d+\\.html").all();
        listUrls.forEach(e -> URLGeneratedUtil.generatePostURL(e));
        page.addTargetRequests(listUrls);//把所有帖子页的URL加入抓取队列
        listUrls.forEach(e -> page.addTargetRequests(URLGeneratedUtil.generatePostURLs(e)));
        page.addTargetRequests(page.getHtml().xpath("/html/body/div[3]/div[1]/div[3]/a[@class='page-btn-prev']/@href").all());

    }

    /**
     * 抓取新闻页内信息
     * @param page 当前页面对象
     */
    private void crawlPost(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex(URL_USER).all());//把所有用户主页的URL加入抓取队列
        //新闻标题
        String title = page.getHtml().xpath("//div[@class='artical-title']//h1/text()").toString();
        //新闻来源
        String comFrom = page.getHtml().xpath("//span[@class='comeFrom']//a/text()").toString();
        //新闻内容
        String content = page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p/text()").all().toString();
        //新闻主图
        String mainImage = page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[1]/img/@src").toString();
        //新闻时间
        String newsDate = page.getHtml().xpath("//*[@id=\"pubtime_baidu\"]/text()").toString();

        setPost(title, content, comFrom, mainImage, newsDate, page, "yyyy-MM-dd HH:mm:ss","sport");
    }


    /**
     * 将主题帖标题的分词信息传给pipeline进行后续处理
     * @param title 主题帖标题
     * @param page 当前页面对象
     */
    private void setTitleWord(String title, Page page) {//存放抓取的帖子标题的分词
        title = title.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "");
        String[] strings = ToAnalysis.parse(title).toStringWithOutNature().split(",");//分词的结果是用","分隔的
        //for(String word: strings) {
          //  TitleWord titleWord = new TitleWord();
            //titleWord.setWord(word);
            page.putField("titleWordInfo", strings);
        //}
    }


}
