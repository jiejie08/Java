package com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.impl;

/*
从数据库拿对象
 */
import com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.AnalyzeDao;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.entity.PoetryInfo;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.AuthorCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:li_d
 * Created:2019/5/13
 */
public class AnalyzeDaoImpl implements AnalyzeDao {
    private final Logger logger = LoggerFactory.getLogger(AnalyzeDaoImpl.class);

    private final DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount> datas = new ArrayList<>();
        //try()自动关闭
        String sql = " select count(*) as count ,author from poetry_info group by author;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()
        ){
            //从数据库拿取数据
            while (rs.next()){
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(rs.getString("author"));
                authorCount.setCount(rs.getInt("count"));
                datas.add(authorCount);
            }
        }catch (SQLException e){
            logger.error("Database query occur exception {}.",e.getMessage());
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoetryInfo() {
        List<PoetryInfo> datas = new ArrayList<>();//将获取数据存储到list集合
        String sql = "select title,dynasty,author,content from poetry_info";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()){
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(rs.getString("title"));
                poetryInfo.setDynasty(rs.getString("dynasty"));
                poetryInfo.setAuthor(rs.getString("author"));
                poetryInfo.setContent(rs.getString("content"));
                datas.add(poetryInfo);
            }

        }catch (SQLException e){
            logger.error("Database query occur exception {}.",e.getMessage());
        }
        return datas;
    }

    @Override
    public void clear() {
        String sql = "delete from poetry_info";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
