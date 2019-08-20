package com.ld.xiaotangpoetry.TangshiApplication.crawler.pipeline;

import com.ld.xiaotangpoetry.TangshiApplication.crawler.comment.Page;

import java.util.Map;

/**
 * 输出，实现管道方法
 * Author:li_d
 * Created:2019/4/26
 */
public class ConsolePipeline implements Pipeline{


    @Override
    public void pipeline(final Page page) {
        Map<String,Object> data = page.getDataSet().getData();
        //存储最终的数据
        System.out.println(data);
    }


}
