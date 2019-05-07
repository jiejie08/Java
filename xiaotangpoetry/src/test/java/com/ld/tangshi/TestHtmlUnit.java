package com.ld.tangshi;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;


import java.io.IOException;

/**
 * htmlUnit库初步了解
 * 调研的技术，不需要写到主程序
 * Author:li_d
 * Created:2019/4/4
 */
public class TestHtmlUnit {
    public static void main(String[] args) {
        //模拟浏览器
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)){
            try {
                webClient.getOptions().setJavaScriptEnabled(false);
                HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org/shiwenv_45c396367f59.aspx");
//                HtmlElement bodyElement =  htmlPage.getBody();
//                String text = bodyElement.asXml();
//                System.out.println(text);
                //采集
//                HtmlDivision domElement = (HtmlDivision) htmlPage.getElementById("contson45c396367f59");
//                //解析
//                String divContent = domElement.asText();
//                System.out.println(divContent);

                HtmlElement body = htmlPage.getBody();
                //标题
                String titlePath = "//div[@class='cont']/h1/text()";
                DomText titleDom = (DomText) body.getByXPath(titlePath).get(0);
                String title = titleDom.asText();
                //System.out.println(titleDom.asText());

                //朝代
                String dynastyPath = "//div[@class='cont']/p/a[1]";
                HtmlAnchor dynastyDom = (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
                //System.out.println(dynastyDom.asText());
                String dynasty = dynastyDom.asText();

                //作者
                String authorPath = "//div[@class='cont']/p/a[2]";
                HtmlAnchor authorDom = (HtmlAnchor) body.getByXPath(authorPath).get(0);
                //System.out.println(authorDom.asText());
                String author = authorDom.asText();

                //正文
                String contentPath = "//div[@class='cont']/div[@class='contson']";
                HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
                //System.out.println(contentDom.asText());
                String content = contentDom.asText();

//                PoetryInfo poetryInfo = new PoetryInfo();
//                poetryInfo.setTitle(title);
//                poetryInfo.setDynasty(dynasty);
//                poetryInfo.setAuthor(author);
//                poetryInfo.setContent(content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
