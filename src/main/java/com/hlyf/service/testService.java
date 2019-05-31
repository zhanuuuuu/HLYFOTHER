package com.hlyf.service;

import com.hlyf.dao.cluster.UserDaoBD;
import com.hlyf.domin.test1;
import com.hlyf.service.imp.testServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2018-11-01.
 *
 */
public interface testService {

    public List<test1> getTestsS();



}
