package com.ld.xiaotangpoetry.TangshiApplication.crawler.pipeline;

import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 清洗数据存入数据库
 * Author:li_d
 * Created:2019/4/28
// */
public class DatabasePipeline implements Pipeline {
    private final Logger logger = LoggerFactory.getLogger(DatabasePipeline.class);

    private final DataSource dataSource;

    public DatabasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    //private final DataSource dataSource;

//    public DatabasePipeline(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Override
    public void pipeline(final Page page) {
        //PoetryInfo poetryInfo = (PoetryInfo) page.getDataSet().getData("poetry");
        //PoetryInfo poetryInfo = new PoetryInfo();
        //解析page中的数据，可以选择需要的数据，不需要在解析中把数据处理完，优化
        String title = (String)page.getDataSet().getData("title");
        String dynasty = (String)page.getDataSet().getData("dynasty");
        String author = (String)page.getDataSet().getData("author");
        String content = (String)page.getDataSet().getData("content");

//        poetryInfo.setTitle((String)page.getDataSet().getData("title"));
//        poetryInfo.setDynasty((String)page.getDataSet().getData("dynasty"));
//        poetryInfo.setAuthor((String)page.getDataSet().getData("author"));
//        poetryInfo.setContent((String)page.getDataSet().getData("content"));
        //修改对象


        String sql = "insert into poetry_info(title, dynasty, author, content) VALUES (?,?,?,?)";
        //try()功能：执行完自动关闭功能
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ){
            statement.setString(1,title);
            statement.setString(2,dynasty);
            statement.setString(3,author);
            statement.setString(4,content);
            //更新
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("Database insert occur exception {}.",e.getMessage());
        }
    }
}
