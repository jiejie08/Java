package com.ld.xiaotangpoetry.TangshiApplication.analyze.service;

import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.AuthorCount;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.WordCount;

import java.util.List;

/**
 * Author:li_d
 * Created:2019/5/16
 */
public interface AnalyzeService {
    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 词云分析
     * @return
     */
    List<WordCount> analyzeWordCloud();
}
