package com.immuserisk.service.impl;

import com.immuserisk.dao.SymptomsDao;
import com.immuserisk.entity.Symptoms;
import com.immuserisk.service.SymptomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SymptomsServiceImpl implements SymptomsService {

    @Autowired
    private SymptomsDao symptomsDao;

    @Override
    public List<Symptoms> findAll() {
        return symptomsDao.findAll();
    }

    @Override
    public Symptoms findById(Integer symptomId) {
        return symptomsDao.findById(symptomId);
    }

    @Override
    public List<Symptoms> findByName(String symptomName) {
        return symptomsDao.findByName(symptomName);
    }
}