package com.crow.webmagic.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @ClassName:CrowProxyProvider
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 16:30 2019/8/8
 * @Version: V1.0
 */
public class AbstractProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
    }
    @Override
    public Site getSite() {
        Site site =new Site();
        return site;
    }
}
