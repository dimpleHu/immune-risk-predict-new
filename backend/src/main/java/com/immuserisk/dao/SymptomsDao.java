package com.immuserisk.dao;

import com.immuserisk.entity.Symptoms;

import java.util.List;

public interface SymptomsDao {
    // 查询所有症状
    List<Symptoms> findAll();

    // 根据ID查询症状
    Symptoms findById(Integer symptomId);

    // 根据名称模糊查询症状
    List<Symptoms> findByName(String symptomName);
}