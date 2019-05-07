package com.ld.xiaotangpoetry.TangshiApplication.web;

import com.google.gson.Gson;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.AuthorCount;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.WordCount;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.service.AnalyzeService;
import com.ld.xiaotangpoetry.TangshiApplication.config.ObjectFactory;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.Crawler;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

/**
 * Web API
 * 1. Sparkjava  框架完成Web API开发
 * 2. Servlet 技术实现Web API
 * 3. Java-Httpd 实现Web API(纯Java语言实现的Web服务)
 *      Socket Http协议非常清楚
 * Author:li_d
 * Created:2019/4/16
 */
public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }
    //借助spark来访问web
    // url地址 -> http://127.0.0.1:4567/
    // + -> /analyze/author_count
    private List<AuthorCount> analyzeAuthorCount(){
        return analyzeService.analyzeAuthorCount();
    }
    // -> http://127.0.0.1:4567/
    // -> /analyze/word_cloud
    private List<WordCount> analyzeWordCloud(){
        return analyzeService.analyzeWordCloud();
    }

    public void launch(){
        ResponseTransformer transformer = new JSONResponseTransformer();

        //src/main/resources/static
        //前端静态文件的目录
        Spark.staticFileLocation("/static");
        //服务端接口
        Spark.get("/analyze/author_count", ((request, response) -> analyzeAuthorCount()),transformer);

        Spark.get("/analyze/word_cloud",((request, response) -> analyzeWordCloud()),transformer);

        Spark.get("/crawler/stop", ((request, response) -> {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.stop();
            return "爬虫停止";

        }));
    }

    public static class JSONResponseTransformer implements ResponseTransformer{

        //Object -> String 把一个Java对象转换为字符串

        private  Gson gson = new Gson();
        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }

}
