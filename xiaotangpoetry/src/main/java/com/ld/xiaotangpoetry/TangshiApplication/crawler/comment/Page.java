package com.ld.xiaotangpoetry.TangshiApplication.crawler.comment;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 将url,detail,htmlpage,subpage抽象成自己的Page，放于公共模块
 * Author:li_d
 * Created:2019/4/20
 */
@Data
public class Page {

    /**
     * 数据网站的根地址
     * 比如：https://so.gushiwen.org
     */
    private final String base;

    /**
     * 具体网页的路径
     * /shiwenv_45c396367f59.aspx
     */
    private final String path;

    /**
     * 网页DOM对象，文档对象模型
     */
    private HtmlPage htmlPage;

    /**
     * 标识网页是否是详情页
     */
    private final boolean detail;

    /**
     * 子页面对象集合
     * 如果不是详情页，意味着有子页面，因此再写一个属性-集合
     * @return
     */
    private Set<Page> subPage = new HashSet<>();

    /**
     * 数据对象
     * @return
     */
    private DataSet dataSet = new DataSet();

//    public Page() {
//        this.path = null;
//        this.base = null;
//        this.detail = true;
//    }


    public String getUrl(){
        return this.base + this.path;
    }

}
