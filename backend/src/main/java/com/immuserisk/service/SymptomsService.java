package com.immuserisk.service;

import com.immuserisk.entity.Symptoms;

import java.util.List;

public interface SymptomsService {
    // 查询所有症状
    List<Symptoms> findAll();

    // 根据ID查询症状
    Symptoms findById(Integer symptomsId);

    // 根据名称模糊查询症状
    List<Symptoms> findByName(String symptomsName);
}