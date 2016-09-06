package com.stefli.db.dao.impl;

import java.util.List;

import com.stefli.db.dao.mapper.PersonMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stefli.db.dao.IPersonDao;
import com.stefli.db.domain.Person;

@Repository("personDao")
public class PersonDao implements IPersonDao {

    //	@Autowired
//	SqlSessionFactory sqlSessionFactory;
    @Autowired
    private PersonMapper mapper;

    public void createPerson(String name) {

        Person person = new Person();
        person.setName(name);
        mapper.create(person);
//		sqlSessionFactory.openSession().insert("Person.create", person);
    }

    public List<Person> findAll() {
//		List<Person> list = null;
//		list = sqlSessionFactory.openSession().selectList("Person.selectAll");
//		return list;
        return mapper.selectAll();
    }

}