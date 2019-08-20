package com.ld.xiaotangpoetry.TangshiApplication.crawler.parse;

import com.gargoylesoftware.htmlunit.html.*;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;

/**
 * 解析的实现
 * 详情页面解析，获取每一个诗文的详情页面
 * 在此获得的数据是零散的，因此新建一个类统一管理PoetryInfo
 * Author:li_d
 * Created:2019/4/25
 */
public class DataPagePrase implements Prase{

    @Override
    public void prase(final Page page) {
        if (!page.isDetail()){
            return;
        }

        HtmlPage htmlPage = page.getHtmlPage();
        HtmlElement body = htmlPage.getBody();
        //标题--把标签元素转换为字符串
        String titlePath = "//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText) body.getByXPath(titlePath).get(0);
        String title = titleDom.asText();

        //朝代
        String dynastyPath = "//div[@class='cont']/p/a[1]";
        HtmlAnchor dynastyDom = (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
        String dynasty = dynastyDom.asText();

        //作者
        String authorPath = "//div[@class='cont']/p/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor) body.getByXPath(authorPath).get(0);
        String author = authorDom.asText();

        //正文
        String contentPath = "//div[@class='cont']/div[@class='contson']";//在此不能取id,因为不唯一
        HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
        String content = contentDom.asText();

//        PoetryInfo poetryInfo = new PoetryInfo();
//        poetryInfo.setTitle(title);
//        poetryInfo.setDynasty(dynasty);
//        poetryInfo.setAuthor(author);
//        poetryInfo.setContent(content);

        page.getDataSet().putData("title",title);
        page.getDataSet().putData("dynasty",dynasty);
        page.getDataSet().putData("author",author);
        page.getDataSet().putData("content",content);
        //更多的数据
        page.getDataSet().putData("url",page.getUrl());


        //page.getDataSet().putData("poetry",poetryInfo);
    }
}
