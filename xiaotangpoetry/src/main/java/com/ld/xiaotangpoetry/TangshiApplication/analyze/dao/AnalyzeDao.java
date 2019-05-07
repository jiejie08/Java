package com.ld.xiaotangpoetry.TangshiApplication.analyze.dao;

import com.ld.xiaotangpoetry.TangshiApplication.analyze.entity.PoetryInfo;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.AuthorCount;

import java.util.List;

/**
 * 查询出所有数据
 * Author:li_d
 * Created:2019/4/13
 */
public interface AnalyzeDao {
    /**
     * 分析唐诗中作者的创作数量
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 查询所有的诗文，提供给业务层进行分析
     * @return
     */
    List<PoetryInfo> queryAllPoetryInfo();

    void clear();
}
