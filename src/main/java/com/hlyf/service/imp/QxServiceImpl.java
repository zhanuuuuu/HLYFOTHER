package com.hlyf.service.imp;

import com.hlyf.dao.cluster.QxDao;
import com.hlyf.service.QxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019-09-09.
 */
@Service
public class QxServiceImpl implements QxService {
    private static final Logger log = LoggerFactory.getLogger(QxServiceImpl.class);

    @Autowired
    private QxDao qxDaoMapper;


}
