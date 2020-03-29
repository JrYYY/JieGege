package com.jryyy.forum.dao;

import com.jryyy.forum.model.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author OU
 */
@Mapper
public interface ThesaurusMapper {

    /**
     * 随机获取n个单词
     *
     * @param number 数量
     * @return {@link Word}
     * @throws Exception
     */
    @Select("select id,English,Chinese " +
            "from thesaurus as r1 join " +
            "(select round(rand() * (select max(id) from thesaurus)) as id2) as r2 " +
            "where r1.id >= r2.id2 order by r1.id limit #{number}")
    Word randomlyFindWord(Integer number) throws Exception;

    /**
     * 查找所有单词
     *
     * @return {@link Word}
     * @throws Exception
     */
    @Select("select id,English,Chinese from thesaurus")
    List<Word> findAllWords() throws Exception;


}
