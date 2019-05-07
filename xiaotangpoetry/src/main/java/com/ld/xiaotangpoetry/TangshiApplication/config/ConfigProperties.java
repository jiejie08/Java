package com.ld.xiaotangpoetry.TangshiApplication.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件config.properties，通过文件来控制修改数据库
 * Author:li_d
 * Created:2019/4/13
 */
@Data
public class ConfigProperties {
    private String crawlerBase;
    private String crawlerPath;
    private boolean crawlerDetail;

    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClass;

    //程序控制逻辑，控制打印信息，在配置文件config.properties设置为true时打印
    private boolean enableConsole;

    public ConfigProperties(){
        //从外部文件加载
        InputStream inputStream = ConfigProperties.class.getClassLoader()
                .getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            //System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.crawlerBase = String.valueOf(p.get("crawler.base"));
        this.crawlerPath = String.valueOf(p.get("crawler.path"));
        this.crawlerDetail = Boolean.parseBoolean(String.valueOf(p.get("crawler.detail")));

        this.dbUsername = String.valueOf(p.get("db.username"));
        this.dbPassword = String.valueOf(p.get("db.password"));
        this.dbUrl = String.valueOf(p.get("db.url"));
        this.dbDriverClass = String.valueOf(p.get("db.driver_class"));

        this.enableConsole = Boolean.valueOf(String.valueOf
                (p.getProperty("config.enable_console","false")));
    }

}
