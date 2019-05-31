package com.hlyf.service.imp;

import com.hlyf.dao.cluster.sCanDao;
import com.hlyf.domin.tThirdUsers;
import com.hlyf.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-11-16.
 */
@Service
public class userServiceImp implements userService {

    private static final Logger logger = LoggerFactory.getLogger(userServiceImp.class);

    @Autowired
    private sCanDao SCanDao;

    @Override
    public List<tThirdUsers> get_tThirdUsersS(String appId) {
        return SCanDao.get_tThirdUsers(appId);
    }
}
