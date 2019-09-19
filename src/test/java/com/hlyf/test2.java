package com.hlyf;

import com.alibaba.fastjson.JSONArray;
import com.hlyf.dao.cluster.mingSheng;
import com.hlyf.dao.cluster.sCanDao;
import com.hlyf.domin.VipAddScore;
import com.hlyf.service.sCanService;
import com.hlyf.service.testService;
import com.hlyf.service.userService;
import com.hlyf.tool.DB;
import com.hlyf.tool.GetConnection;
import com.hlyf.tool.ResultSet_To_JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2019-05-21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test2 {
    private static final Logger logger = LoggerFactory.getLogger(HlyfotherApplicationTests.class);

    private static final String appkey="warelucent";
    @Autowired
    private com.hlyf.service.testService testService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private sCanService CanService;

    @Autowired
    private userService UserService;

    @Autowired
    private sCanDao CanDao;

    @Autowired
    private com.hlyf.dao.cluster.mingSheng mingSheng;

    //保存单号
    @Test
    public void testSaveSheetNo(){
        Integer integer=CanDao.p_saveSheetNo_Z_call("1005",
                "05","2019-05-06","05201904060001",
                "32","posstation101.dbo.p_saveSheetNo");

        logger.info("我是影响行数 "+ integer);
    }
    //VipAddScore
    @Test
    public void tesVipAddScore(){
        VipAddScore vipAddScore=CanDao.getVipScoreAdd("2019043012011993595",
                "100","posstation101.dbo.p_CountVipScore_Online");
        logger.info("我是结果 "+ vipAddScore);
    }

    @Test
    public void tes123(){

        Connection conn= GetConnection.getConn("posstation005");
        CallableStatement c = null;
        try {
            c = conn.prepareCall("{call p_ProcessPosSheet (?,?,?,?,?,?)}");
            c.setString(1, "005");
            c.setString(2, "81");
            c.setString(3, "17");
            c.setString(4, "");
            c.setString(5, "100");
            c.setString(6, "0");
            c.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeCallState(c);
            DB.closeConn(conn);
        }

    }


}
