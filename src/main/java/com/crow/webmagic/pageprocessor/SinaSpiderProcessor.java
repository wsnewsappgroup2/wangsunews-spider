package com.crow.webmagic.pageprocessor;

import com.crow.entity.Post;
import com.crow.utils.ProxyGeneratedUtil;
import com.crow.utils.URLGeneratedUtil;
import com.crow.utils.UserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SinaSpiderProcessor implements PageProcessor {
    public static final String URL_LIST = "http://ent.sina.com.cn/weibo/";
    private static final String ORDER_NUM = "ZF201710169692T66jkr";//讯代理订单号
    private static final String SECRET = "3b23ace31a2447baa44d624e9c5fd0f5";//讯代理密码

    //抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
    private Site site = Site.me()
            .addHeader("Proxy-Authorization", ProxyGeneratedUtil.authHeader(ORDER_NUM, SECRET, (int) (new Date().getTime()/1000)))//设置代理
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
        List<String> listUrls = page.getHtml().xpath("//*[@id=\"feedCardContent\"]/div[1]/div[1]/@class)").all();
        String test = page.getHtml().xpath("//*[@id=\"feedCardContent\"]/div[1]/div[1]/h2/a/text()").toString();
        listUrls.forEach(e -> URLGeneratedUtil.generatePostURL(e));
        page.addTargetRequests(listUrls);//把所有帖子页的URL加入抓取队列
        //*[@id="feedCardContent"]/div[1]
        listUrls.forEach(e -> page.addTargetRequests(URLGeneratedUtil.generatePostURLs(e)));
    }

    /**
     * 抓取新闻页内信息
     * @param page 当前页面对象
     */
    private void crawlPost(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex(URL_USER).all());//把所有用户主页的URL加入抓取队列
        //新闻标题
        String title = page.getHtml().xpath("//*[@id=\"main-title\"]/text()").toString();
        //新闻来源
        String comFrom = page.getHtml().xpath("//*[@id=\"top_bar\"]/div/div[2]/span[2]/a/text()").toString();
        //新闻内容
        String content = page.getHtml().xpath("//*[@id=\"artibody\"]/p[1]/text()").all().toString();
        //新闻主图
        String mainImage = page.getHtml().xpath("//*[@id=\"artibody\"]/div[1]/img/@src").toString();
        //新闻时间
        String newsDate =  page.getHtml().xpath("//*[@id=\"top_bar\"]/div/div[2]/span[1]/text()").toString();
        //评论数
        String sourceCommentNum =  page.getHtml().xpath("//*[@id=\"bottom_sina_comment\"]/div[1]/div[1]/span[1]/em[1]/a/text()").toString();
        //主题词
        String topicWord =  page.getHtml().xpath("//*[@id=\"keywords\"]/a[1]/text()").toString();

        setPost(title, content, comFrom, mainImage,newsDate,sourceCommentNum,topicWord,page);

    }

    /**
     * 抓取各用户主页
     * @param page 当前页面对象
    //     */
//    private void crawlUser(Page page) {
//        //用户名
//        String name = page.getHtml().xpath("//div[@itemprop='name']/text()").toString();
//        //用户所在地
//        String address = page.getHtml().xpath("//div[@class='personalinfo']//span[@itemprop='address']/text()").toString();
//        //用户主队
//        String homeTeam = page.getHtml().xpath("//div[@class='personalinfo']//span[@itemprop='affiliation'][1]/a/text()").toString();
//        //用户性别
//        String gender = page.getHtml().xpath("//div[@class='personalinfo']//span[@itemprop='gender']/text()").toString();
//        //用户被访问量
//        int views = Integer.parseInt(page.getHtml().xpath("//div[@class='personal']//span[@class='f666'][1]/text()").toString().replaceAll("[^0-9]", ""));
//
//        setUser(name, address, homeTeam, gender, views, page);
//    }

    /**
     * 将抓取到的主题帖post信息传给pipeline进行后续处理
     * @param title 帖子标题
     * @param page 当前页面对象
     */
    private void setPost(String title, String content, String source, String mainImage,String newsDate,String sourceCommentNum,String topicWord,Page page) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setSource(source);
        post.setMainImage(mainImage);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date newsdate= new Date();
        try {
            newsdate = sdf.parse(newsDate);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        post.setSourceCommentNum(sourceCommentNum);
        post.setTopicWord(topicWord);
        post.setNewsDate(newsdate);
        page.putField("postInfo", post);
    }

}
