package com.jryyy.forum.utils.sql.test;

import com.jryyy.forum.utils.sql.generation.BaseSql;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseSql<User> {
}
