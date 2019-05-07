package com.ld.xiaotangpoetry.TangshiApplication.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.AnalyzeDao;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.impl.AnalyzeDaoImpl;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.service.AnalyzeService;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.service.impl.AnalyzeServicelmpl;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.Crawler;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.parse.DataPagePrase;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.parse.DocumentPrase;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.pipeline.ConsolePipeline;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.pipeline.DatabasePipeline;
import com.ld.xiaotangpoetry.TangshiApplication.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象工厂--只关心对象
 * Author:li_d
 * Created:2019/4/15
 */
public final class ObjectFactory {
    //工厂对象用单例模式(饿汉式)实现
    private static final ObjectFactory instance = new ObjectFactory();

    private final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
    /**
     * 存放所有的对象Map集合
     */
    private final Map<Class, Object> objectHashMap = new HashMap<>();
    private ObjectFactory(){
        //1. 初始化配置对象
        initConfigProperties();
        //2. 数据源对象
        initDataSource();
        //3. 爬虫对象
        initCrawler();
        //4. Web对象
        initWebController();
        //5.对象清单打印输出
        printObjectList();
    }

    private void initWebController() {
        DataSource dataSource = getObject(DataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
        AnalyzeService analyzeService = new AnalyzeServicelmpl(analyzeDao);
        WebController webController = new WebController(analyzeService);
        objectHashMap.put(WebController.class,webController);


    }

    private void initCrawler() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);
        final Page page = new Page(configProperties.getCrawlerBase(),configProperties
                .getCrawlerPath(),configProperties.isCrawlerDetail());

        Crawler crawler = new Crawler();
        crawler.addPrase(new DocumentPrase());
        crawler.addPrase(new DataPagePrase());
        if (configProperties.isEnableConsole()){
            crawler.addPipeline(new ConsolePipeline());
        }
        crawler.addPipeline(new DatabasePipeline(dataSource));
        crawler.addPage(page);
        objectHashMap.put(Crawler.class,crawler);
    }

    private void initDataSource() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());
        AnalyzeDao clearDb = new AnalyzeDaoImpl(dataSource);
        clearDb.clear();
        objectHashMap.put(DataSource.class,dataSource);
    }

    private void initConfigProperties() {
        ConfigProperties configProperties = new ConfigProperties();
        objectHashMap.put(ConfigProperties.class,configProperties);

        logger.info("ConfigProperties info:\n{}",configProperties.toString());
    }

    public <T> T getObject(Class classz){
        if (!objectHashMap.containsKey(classz)){
            throw  new IllegalArgumentException("Class" + classz.getName() + "not found Object");
        }
        return (T) objectHashMap.get(classz);
    }

    public static ObjectFactory getInstance(){
        return instance;
    }

    private void printObjectList(){
        logger.info("======== ObjectFactory List ====");
        for (Map.Entry<Class,Object> entry : objectHashMap.entrySet()){
            logger.info(String.format("[%-5s] ==> [%s]",entry.getKey().getCanonicalName(),
                    entry.getValue().getClass().getCanonicalName()));
        }
        logger.info("=========================");
    }
}
