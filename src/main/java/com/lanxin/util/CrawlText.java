package com.lanxin.util;

import com.lanxin.thread.CrawTextThread;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlText {
    /***
     * 获取文本
     *
     * @param autoDownloadFile
     *            自动下载文件
     * @param Multithreading
     *            多线程 默认false
     * @param Url
     *            网站链接
     * @throws IOException
     */
    public static void getText(boolean autoDownloadFile, boolean Multithreading, String Url) throws IOException {
        String rule = "abs:href";
        List<String> urlList = new ArrayList<String>();
        Document document = Jsoup.connect(Url)
                .timeout(4000)
                .ignoreContentType(true)
                .userAgent("Mozilla\" to \"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0)")
                .get();
        System.out.println(document.toString());
        //<a href="25260443.html">楔子 一块黑布</a>
        Elements urlNode = document.select("a[href$=.html]");
        for (Element element : urlNode) {
            urlList.add(element.attr(rule));
        }
        CrawTextThread crawTextThread = new CrawTextThread(urlList);
        crawTextThread.start();
    }

    public static void main(String[] args) {
        try {
            //http://www.biqule.org/
            CrawlText.getText(true, true, "http://www.biqule.org/book_66010/");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
