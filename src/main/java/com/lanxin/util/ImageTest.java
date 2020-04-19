package com.lanxin.util;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;

public class ImageTest {
    public static void main(String[] args) throws Exception {
        String url = "http://image.baidu.com/";//双引号里内容为你要爬去的网站url 此处略过
        Document document = Jsoup.connect(url).post();//获取url网站html内容
        /*//获取html里所有class值为"block"的属性的内容   getElementsByTag("") 用来获取标签 如img src 等
        Elements elements = document.getElementsByClass("block");
        for (int i = 0; i < elements.size(); i++) {//循环输出
            Element yuansu= elements.get(i);//用来获取单条class为"block"的内容
            Elements yuansu_zi = yuansu.getElementsByTag("img");//获取class为"block"的子元素为img的内容
            String imgurl = yuansu_zi.attr("src");//获取img中src属性值
            String imgname = yuansu.attr("title");//获取class为"block"中src属性值
            //调用方法
            paQu(imgurl, "/home/personal/down/", imgname);//第一个参数为要下载的图片路径 第二个为存放的路径 第三个为图片的名字
        }*/
        //获取html里所有class值为"block"的属性的内容   getElementsByTag("") 用来获取标签 如img src 等
        Elements elements = document.getElementsByClass("block");
        for (int i = 0; i < elements.size(); i++) {//循环输出
            Element yuansu= elements.get(i);//用来获取单条class为"block"的内容
            Elements yuansu_zi = yuansu.getElementsByTag("img");//获取class为"block"的子元素为img的内容
            String imgurl = yuansu_zi.attr("src");//获取img中src属性值
            String imgname = yuansu.attr("title");//获取class为"block"中src属性值
            //调用方法
            paQu(imgurl, "/home/personal/down/", imgname);//第一个参数为要下载的图片路径 第二个为存放的路径 第三个为图片的名字
        }
    }
    public static void paQu(String imgurl,String dizhi,String imgname) throws Exception{
        URL url = new URL(imgurl);//创建url类
        String path =dizhi+imgname+".jpg"; //图片的名字  后缀名必须弄好
        File file = new File(path);
        FileUtils.copyURLToFile(url, file);//下载为url的图片地址  同时将下载好的图片存入到file的地址中
        System.out.println("读取成功");
    }

}
