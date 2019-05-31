package com.hlyf.service.imp;

import com.hlyf.dao.cluster.UserDaoBD;
import com.hlyf.domin.test1;
import com.hlyf.service.testService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-11-01.
 */
@Service
public class testServiceImp implements testService {
    private static final Logger LOGGER = LoggerFactory.getLogger(testServiceImp.class);

    @Autowired
    private UserDaoBD userDaoBD;

    @Override
    public List<test1> getTestsS() {
        return userDaoBD.getTests();
    }
}
