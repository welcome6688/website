package com.rmc.crawl.plugins.demo;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;

/**
 * Crawling news from hfut news
 *
 * @author hu
 */
public class NewsCrawler extends BreadthCrawler {

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     * information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     * links which match regex rules from pag
     */
    public NewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start page*/
        //种子页面：顶点小说
//        this.addSeed("http://news.hfut.edu.cn/list-1-1.html");
        this.addSeed("http://www.booktxt.net/2_2219/");

        /*fetch url like http://news.hfut.edu.cn/show-xxxxxxhtml*/
//        this.addRegex("http://news.hfut.edu.cn/show-.*html");
        this.addRegex("http://www.booktxt.net/2_2219/21.*html");
        	
        /*do not fetch jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.getUrl();
        /*if page is news page*/
//        if (page.matchUrl("http://news.hfut.edu.cn/show-.*html")) {
        if (page.matchUrl("http://www.booktxt.net/2_2219/21.*html")) {	
            /*we use jsoup to parse page*/
            Document doc = page.getDoc();

            /*extract title and content of news by css selector*/
            String title = page.select("div[class=bookname]>h1").first().text();
            String content = page.select("div#content", 0).text();
//            content = content.replace("没了", "okok");
            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);

            /*If you want to add urls to crawl,add them to nextLink*/
            /*WebCollector automatically filters links that have been fetched before*/
            /*If autoParse is true and the link you add to nextLinks does not match the regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        }
    }

    public static void main(String[] args) throws Exception {
        NewsCrawler crawler = new NewsCrawler("crawl", true);
        crawler.setThreads(50);
        crawler.setTopN(100);
        //crawler.setResumable(true);
        /*start crawl with depth of 4*/
        crawler.start(4);
    }
}