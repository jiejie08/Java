package com.ld.xiaotangpoetry.TangshiApplication.crawler.parse;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * 链接解析
 * 网页遍历完之后，把子页面加载到当前页面的子页面中
 * Author:li_d
 * Created:2019/3/17
 */
public class DocumentPrase implements Prase {
    @Override
    public void prase(final Page page) {
        if (page.isDetail()){
            return;
        }
        //final AtomicInteger count = new AtomicInteger(0);
        //取出当前页面page的htmlPage
        HtmlPage htmlPage = page.getHtmlPage();
        //获取超链接,取得body,分析文档模型，发现超链接都在一个div中，这个div的特点是所有的class都在typecont中
        htmlPage.getBody().getElementsByAttribute("div","class","typecont")
                .forEach(div -> {//取出div，进行遍历，通过链式调用
                    DomNodeList<HtmlElement> aNodeList = div.getElementsByTagName("a");
                    aNodeList.forEach(aNode->{//取出每个a标签的节点属性-超链接，进行遍历，
                        //System.out.println(aNode.asXml());
                        String path = aNode.getAttribute("href");
                        Page subPage = new Page(
                                page.getBase(),
                                path,
                                true
                        );
                        //获取到每个文档的子页面
                        page.getSubPage().add(subPage);
                        //count.getAndIncrement();
                        //System.out.println(path);
                    });
                });
        //System.out.println("总共"+count.get()+"地址");
    }
}