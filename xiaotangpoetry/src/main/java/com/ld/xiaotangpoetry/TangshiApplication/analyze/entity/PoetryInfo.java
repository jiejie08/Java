package com.ld.xiaotangpoetry.TangshiApplication.analyze.entity;

import lombok.Data;

/**
 * Author:li_d
 * Created:2019/4/6
 */
@Data
public class PoetryInfo {
    /**
     * 标题
     */
    private String title;
    /**
     * 朝代
     */
    private String dynasty;
    /**
     * 作者
     */
    private String author;
    /**
     * 正文
     */
    private String content;
}
