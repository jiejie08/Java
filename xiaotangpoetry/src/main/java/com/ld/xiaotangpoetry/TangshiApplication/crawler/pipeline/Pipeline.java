package com.ld.xiaotangpoetry.TangshiApplication.crawler.pipeline;

import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;
import com.ld.xiaotangpoetry.TangshiApplication.crawler.parse.Prase;

/**
 * Author:li_d
 * Created:2019/4/4
 */
public interface Pipeline  {
    /**
     * 清洗器
     * 管道处理page中的数据
     * @param page
     */
    void pipeline(final Page page);
}
