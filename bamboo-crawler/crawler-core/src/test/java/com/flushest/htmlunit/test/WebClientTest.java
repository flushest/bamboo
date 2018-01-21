package com.flushest.htmlunit.test;

import com.flushest.bamboo.framework.util.ClassUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/11/13 0013.
 */
public class WebClientTest {
    @Test
    public void test() throws IOException {
        String filePath = "META-INF/bamboo/com.flushest.bamboo.crawler.core.storage.Storage";
        System.out.println(ClassUtil.scanFile(filePath).length);
    }
    @Test
    public void windowTest() throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        WebWindow webWindow = webClient.openWindow(new URL("https://www.miaobige.com/"),"baiDu");
        Page page = webWindow.getEnclosedPage();
        if(page.isHtmlPage()) {
            HtmlPage htmlPage = (HtmlPage) page;
            HtmlInput input = (HtmlInput) htmlPage.getElementById("key");
            input.setValueAttribute("我修的可能是假仙");
            HtmlForm form = htmlPage.getFormByName("search");
            HtmlButton button = form.getFirstByXPath("./button");
            Page ser = button.click();
            if(ser.isHtmlPage()) {
                HtmlPage serHtmlPage = (HtmlPage) ser;
                String text = serHtmlPage.asText();
                System.out.println(ser.getUrl().toString());
            }
        }
    }

    @Test
    public void xPathTest() throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        WebWindow webWindow = webClient.openWindow(new URL("https://www.miaobige.com/"),"baiDu");
        Page page = webWindow.getEnclosedPage();
        if(page.isHtmlPage()) {
            HtmlPage htmlPage = (HtmlPage) page;
            DomElement obj = htmlPage.getFirstByXPath("/html/body/div/div/div/a");
            NamedNodeMap map = obj.getAttributes();
            System.out.print(map);
        }
    }
}
