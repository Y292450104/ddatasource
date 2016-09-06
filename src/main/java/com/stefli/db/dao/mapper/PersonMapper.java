package com.stefli.db.dao.mapper;

import com.stefli.db.domain.Person;

import java.util.List;

/**
 * Created by yinaijie on 2016/9/6.
 */
public interface PersonMapper {

    void create(Person person);

    List<Person> selectAll();
}
