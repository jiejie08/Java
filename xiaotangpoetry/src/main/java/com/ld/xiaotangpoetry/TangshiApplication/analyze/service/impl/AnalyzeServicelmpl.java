package com.ld.xiaotangpoetry.TangshiApplication.analyze.service.impl;

import com.ld.xiaotangpoetry.TangshiApplication.analyze.dao.AnalyzeDao;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.entity.PoetryInfo;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.AuthorCount;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.model.WordCount;
import com.ld.xiaotangpoetry.TangshiApplication.analyze.service.AnalyzeService;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

/**
 * Author:li_d
 * Created:2019/5/16
 */
public class AnalyzeServicelmpl implements AnalyzeService {
    private final AnalyzeDao analyzeDao;

    public AnalyzeServicelmpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        //排序方式
        //1. DAO层SQL排序
        //2. Servicce层进行数据排序
        List<AuthorCount> authorCounts = analyzeDao.analyzeAuthorCount();
        //此处是按照count升序
        authorCounts.sort(Comparator.comparing(AuthorCount::getCount));
        return authorCounts;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {
        //1.查询出所有的数据
        //2. 取出title content
        //3. 分词 过滤 /w(标点符号) null 空 len<2
        //4. 统计 k-v, k是词 v是词频

        Map<String,Integer> map = new HashMap<>();
        List<PoetryInfo> poetryInfos = analyzeDao.queryAllPoetryInfo();
        for (PoetryInfo poetryInfo : poetryInfos){
            List<Term> terms = new ArrayList<>();
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();
            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            Iterator<Term> iterator = terms.iterator();
            while (iterator.hasNext()){
                Term term = iterator.next();
                //词性的过滤
                if (term.getNatureStr() == null || term.getNatureStr().equals("w")){
                    iterator.remove();
                    continue;
                }
                //词的过滤
                if (term.getRealName().length() < 2){
                    iterator.remove();
                    continue;
                }
                //统计 k-v, k是词 v是词频------扩展Map集合
                String realName = term.getRealName();
                int count;
                if (map.containsKey(realName)){
                    count = map.get(realName) + 1;
                }else {
                    count = 1;
                }
                map.put(realName,count);
            }
        }
        //把map转为list集合
        List<WordCount> wordCounts = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : map.entrySet()){
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }

}
