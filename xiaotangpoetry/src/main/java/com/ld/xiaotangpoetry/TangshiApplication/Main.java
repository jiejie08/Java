package com.ld.xiaotangpoetry.TangshiApplication;

import com.alibaba.druid.pool.DruidDataSource;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.AnalyzeDao;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.impl.AnalyzeDaoImpl;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.entity.PoetryInfo;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.AuthorCount;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.service.AnalyzeService;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.service.impl.AnalyzeServicelmpl;
import com.ld.xiaotangpoetry.TangshiApplication.config.ConfigProperties;
import com.ld.xiaotangpoetry.TangshiApplication.config.ObjectFactory;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.Crawler;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.parse.DataPagePrase;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.parse.DocumentPrase;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.pipeline.DatabasePipeline;
import com.ld.xiaotangpoetry.TangshiApplication.web.WebController;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.time.LocalDateTime;
import java.util.List;


import static spark.route.HttpMethod.get;

/**
 * Author:li_d
 * Created:2019/4/23
 */
@Data
public class Main {
    //添加日志来明确爬虫是否启动
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        WebController webController = ObjectFactory.getInstance().getObject(WebController.class);
        //运行了Web服务，提供接口
        LOGGER.info("Web Server launch ...");
        webController.launch();

        //启动爬虫通过命令行参数
        if (args.length == 1 && args[0].equals("run-crawler")){
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            LOGGER.info("Crawler started ...");
            crawler.start();
        }
    }
}
