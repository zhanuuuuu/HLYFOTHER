package com.hlyf.dao.cluster;

import com.hlyf.domin.test1;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018-11-01.
 */
@Mapper
public interface UserDaoBD {
    List<test1>  getTests();
}
