package com.hlyf.controller;

import com.hlyf.dao.cluster.QxDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2019-09-09.
 * <pre>
 *     该类是单独给千禧加的  不要问为什么这样写
 *
 *     目的:  这个是给千禧刷脸支付用的,主要是给他们上传单子（订单信息）使用的
 *
 *     数据传输功能
 * </pre>
 */
@RestController
public class QxConntroller {
    private static final Logger logger = LoggerFactory.getLogger(QxConntroller.class);





    //千禧数据传输
    @RequestMapping(value = "/Qx/dataTransmission", method = RequestMethod.POST)
    @ResponseBody
    public  String dataTransmission(@RequestParam(value = "data",required = true) String data,
                                   @RequestParam(value = "vipNo",required = false,defaultValue = "") String vipNo,
                                   @RequestParam(value = "fVipRate",required = false,defaultValue = "100") String fVipRate,
                                   @RequestParam(value = "bDiscount",required = false,defaultValue = "0") String bDiscount,
                                   @RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                                   @RequestParam(value = "cMachineID",required = true) String cMachineID,
                                   @RequestParam(value = "cMachineKey",required = true) String cMachineKey,
                                   HttpServletRequest request) {
        return "";
    }

    @RequestMapping(value = "/Qx/getTransmissionData", method = RequestMethod.POST)
    @ResponseBody
    public  String getTransmissionData(@RequestParam(value = "data",required = true) String data,
                                    @RequestParam(value = "vipNo",required = false,defaultValue = "") String vipNo,
                                    @RequestParam(value = "fVipRate",required = false,defaultValue = "100") String fVipRate,
                                    @RequestParam(value = "bDiscount",required = false,defaultValue = "0") String bDiscount,
                                    @RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                                    @RequestParam(value = "cMachineID",required = true) String cMachineID,
                                    @RequestParam(value = "cMachineKey",required = true) String cMachineKey,
                                    HttpServletRequest request) {
        return "";
    }

}
