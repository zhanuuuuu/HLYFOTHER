package com.hlyf.service;

import com.hlyf.domin.tThirdUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018-11-16.
 */
public interface userService {
    List<tThirdUsers> get_tThirdUsersS(String appId);
}
