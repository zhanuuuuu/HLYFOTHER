package com.hlyf.service.imp;

import com.hlyf.dao.cluster.mingSheng;
import com.hlyf.dao.cluster.sCanDao;
import com.hlyf.domin.*;
import com.hlyf.domin.JDcom.*;
import com.hlyf.domin.mingsheng.t_MinShengPublicSale_z;
import com.hlyf.domin.mingsheng.t_MingShengPublicSaleDetail_z;
import com.hlyf.minsheng.RopUtils;
import com.hlyf.result.ResultOk;
import com.hlyf.result.resultMsg;
import com.hlyf.service.sCanService;
import com.hlyf.tool.DB;
import com.hlyf.tool.GetConnection;
import com.hlyf.tool.String_Tool;
import com.hlyf.tool.listBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.bouncycastle.crypto.tls.MACAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hlyf.tool.HttpTookitt.sendPost;
import static com.hlyf.tool.String_Tool.getRandom;

/**
 * Created by Administrator on 2018-11-13.
 */
@Service
//@Transactional
public class sCanServiceImp implements sCanService {
    private static final Logger logger = LoggerFactory.getLogger(sCanServiceImp.class);

    @Autowired
    private sCanDao SCanDao;

    @Autowired
    private mingSheng mingSheng;

    @Override
    public List<Store> get_cStroeS(String cStroreNo) {
        return SCanDao.get_cStroe(cStroreNo);
    }

    @Override
    public List<vip> get_vipS(String mobileNo) {
        return SCanDao.get_vip(mobileNo);
    }

    @Override
    public List<t_vipInfo> get_vipInfoS(String mobileNo) {
        return SCanDao.get_vipInfo(mobileNo);
    }

    @Override
    public List<cStoreGoods> get_cStoreGoodsS(String cStoreNo, List<String> barcodeList) {
        return SCanDao.get_cStoreGoods(cStoreNo, barcodeList);
    }

    @Override
    public List<cStoreGoods> get_cStoreGoodsDanDianS(String cStoreNo, List<String> barcodeList) {
        return SCanDao.get_cStoreGoodsDanDian(cStoreNo, barcodeList);
    }

    @Override
    public List<tPloyOfSale> get_tPloyOfSaleS(String cStoreNo, String cBarcode, int num) {
        return SCanDao.get_tPloyOfSale(cStoreNo, cBarcode, num);
    }

    @Override
    public List<cGoods> get_cGoodsS(String cStoreNo, String pageNum, String number) {
        return SCanDao.get_cGoods(cStoreNo, pageNum, number);
    }

    @Override
    public List<tWareHouse> get_tWareHouseS(String cStoreNo) {
        return SCanDao.get_tWareHouse(cStoreNo);
    }

    @Override
    public List<tMethod_of_payment> get_tMethod_of_paymentS() {
        return SCanDao.get_tMethod_of_payment();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout=36000,
            rollbackFor={RuntimeException.class, Exception.class},
            readOnly = false)
    public String placing_order(List<tPublicSale_z> tPublicSale_zlist, List<tPublicSaleDetail_z> tPublicSaleDetail_zlist) {
        String result=new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        tPublicSale_z tPublicSaleZ=null;

        for(tPublicSale_z tPublicSaleZ1:tPublicSale_zlist){
            tPublicSaleZ=tPublicSaleZ1;
        }
        //主表单号
        String cSheeetNo=tPublicSaleZ.getcSheetNo();
        /**
         * @修改时间  2018-12-12
         * @修改内容描述   判断主表单号是否为空
         */
        if(cSheeetNo==null || cSheeetNo.trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.ORDERNO_ERROR.getValue(),ResultOk.ORDERNO_ERROR.getDesc()).toString();
        }

        /**
         * @修改时间   2018-12-12
         * @修改内容   获取门店编号  门店 仓库编号   判断是否有误
         */
        String cStoreNo=tPublicSaleZ.getcStoreNo();
        String cWhno=tPublicSaleZ.getcWHno();
        if(cStoreNo==null || cStoreNo.trim().equals("") ||
                cWhno==null || cWhno.trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.STORENO_OR_WHNO_ERROR.getValue(),ResultOk.STORENO_OR_WHNO_ERROR.getDesc()).toString();
        }

        List<tPublicSale_z> list=SCanDao.get_tPublicSale_z(tPublicSaleZ.getcSheetNo(),tPublicSaleZ.getAppId());
        if(list!=null && list.size()>0){
            return new resultMsg(false,"[]", ResultOk.ORDERNO_EXIST.getValue(),ResultOk.ORDERNO_EXIST.getDesc()).toString();
        }
        /**
         * @修改时间  2018-12-12
         * @修改内容描述  将字表单号更改主表单号  这里不去取字表的单号  直接改成主表的单号
         * @为什么写到这里    因为前面的判断没走通  就直接返回了  写到这里  提高性能
         */
        int Serialnumber=1;
        for(tPublicSaleDetail_z  zlist:tPublicSaleDetail_zlist){
            zlist.setcSheetNo(cSheeetNo);
            zlist.setcStoreNo(cStoreNo);
            zlist.setcWHno(cWhno);
            if(zlist.getbAuditing()==null){
                zlist.setbAuditing("0");
            }
            zlist.setiSeed(""+Serialnumber);
            Serialnumber++;
        }
        try{
            SCanDao.insert_tPublicSale_z(tPublicSale_zlist);
            SCanDao.insert_tPublicSaleDetail_z(tPublicSaleDetail_zlist);

            JSONObject json=new JSONObject();
            json.put("cSheeetNo",cSheeetNo);
            result=new resultMsg(true,json.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
        }catch (Exception e){
            logger.error("下订单出错   "+e.getMessage());
            throw new RuntimeException("下单出错了");
        }
        return result;
    }

    /**
     *
     * @描述  京东插入订单  废弃的方法
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout=36000,
            rollbackFor={RuntimeException.class, Exception.class},
            readOnly = false)
    @Deprecated
    public String placing_order_JD(List<tPublicSale_JingDong_z> tPublicSale_JingDong_zlist, List<tPublicSaleDetail_JingDong_z> tPublicSaleDetail_JingDong_zlist) {
        String result=new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();

        tPublicSale_JingDong_z tPublicSaleZ_JingDong=null;

        for(tPublicSale_JingDong_z tPublicSale_JingDongZ1:tPublicSale_JingDong_zlist){
            tPublicSaleZ_JingDong=tPublicSale_JingDongZ1;
        }

        //设置设备标识  如果没有值  就重置 “”
        tPublicSaleZ_JingDong.setMachineIp(tPublicSaleZ_JingDong.getMachineIp() !=null ? tPublicSaleZ_JingDong.getMachineIp():"");

        String appId=tPublicSaleZ_JingDong.getAppId();
        //生成并设置单号
        tPublicSaleZ_JingDong.setcSheetNo(
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+getRandom(10000,99999));
        //主表单号
        String cSheeetNo=tPublicSaleZ_JingDong.getcSheetNo();


        /**
         * @修改时间   2018-12-12
         * @修改内容   获取门店编号  门店 仓库编号   判断是否有误
         */
        String cStoreNo=tPublicSaleZ_JingDong.getcStoreNo();
        String cWhno=tPublicSaleZ_JingDong.getcWHno();
        if(cStoreNo==null || cStoreNo.trim().equals("") ||
                cWhno==null || cWhno.trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.STORENO_OR_WHNO_ERROR.getValue(),ResultOk.STORENO_OR_WHNO_ERROR.getDesc()).toString();
        }

        /**
         * 判断主表单号是否存在  避免单号重复
         */
//        List<tPublicSale_JingDong_z> list=SCanDao.get_tPublicSale_JingDong_z(null,tPublicSaleZ_JingDong.getAppId(),tPublicSaleZ_JingDong.getAppSheetNo());
//        if(list!=null && list.size()>0){
//            return new resultMsg(false,"[]", ResultOk.ORDERNO_EXIST.getValue(),ResultOk.ORDERNO_EXIST.getDesc()).toString();
//        }
        /**
         * @修改时间  2018-12-12
         * @修改内容描述  将字表单号更改主表单号  这里不去取字表的单号  直接改成主表的单号
         * @为什么写到这里    因为前面的判断没走通  就直接返回了  写到这里  提高性能
         */
        int Serialnumber=1;
        for(tPublicSaleDetail_JingDong_z  zlist:tPublicSaleDetail_JingDong_zlist){
            zlist.setcSheetNo(cSheeetNo);
            zlist.setcStoreNo(cStoreNo);
            zlist.setcWHno(cWhno);
            if(zlist.getbAuditing()==null){
                zlist.setbAuditing("0");
            }
            zlist.setiSeed(""+Serialnumber);
            Serialnumber++;
        }
        try{
            /**
             * 开始做真正的插入数据库
             */
            SCanDao.insert_tPublicSale_JingDong_z(tPublicSale_JingDong_zlist);

            /*
             @描述  这里一定要仔细看啊  动态传输表名
             */
            Map mapDetail=new HashMap();
            mapDetail.put("list",tPublicSaleDetail_JingDong_zlist);
            mapDetail.put("tableName","tPublicSaleDetail_JingDong_z");
            SCanDao.insert_tPublicSaleDetail_JingDong_z(mapDetail);

            /**
             * 下面是把数据插入到临时表中
             */

            Map map = new HashMap();
            map.put("cSheetNo", cSheeetNo);
            map.put("appId",appId);
            map.put("tableName","posstation101.dbo.pos_SaleSheetDetailTemp");
            map.put("iFlag", 0);

            SCanDao.calAmountOrder(map);
            Integer iFlag = (Integer)map.get("iFlag");

            // Map 调用存储过程返回修改成功的标识 下面肯定不会返回-1 的  除非过程中有设定
            if(iFlag != -1){
                result=get_Order_JD(cSheeetNo,tPublicSaleZ_JingDong.getAppId());
                return result;
            }

            //下面结束了
            JSONObject json=new JSONObject();
            json.put("cSheeetNo",cSheeetNo);
            result=new resultMsg(true,json.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
        }catch (Exception e){
            logger.error("京东下订单出错   "+e.getMessage());
            throw new RuntimeException("京东下单出错了");
        }
        return result;
    }

    /**
     *    通过该接口 获取京东那边整单优惠的商品
     * @param tPublicSale_JingDong_zlist
     * @param tPublicSaleDetail_JingDong_zlist
     * @param fVipRate
     * @param bDiscount
     * @param tableName
     * @param posstation
     * @return
     */
    public String getPreGoodsInfoS(List<tPublicSale_JingDong_z> tPublicSale_JingDong_zlist,
                                   List<tPublicSaleDetail_JingDong_z> tPublicSaleDetail_JingDong_zlist,
                                   String fVipRate,String bDiscount,String tableName,posstation posstation,String sheetNotrue) {
        String result=new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();

        tPublicSale_JingDong_z tPublicSaleZ_JingDong=null;

        for(tPublicSale_JingDong_z tPublicSale_JingDongZ1:tPublicSale_JingDong_zlist){
            tPublicSaleZ_JingDong=tPublicSale_JingDongZ1;
        }

        //设置设备标识  如果没有值  就重置 “”
        tPublicSaleZ_JingDong.setMachineIp(tPublicSaleZ_JingDong.getMachineIp() !=null ? tPublicSaleZ_JingDong.getMachineIp():"");

        String appId=tPublicSaleZ_JingDong.getAppId();

        String vipNo=tPublicSaleZ_JingDong.getcVipNo();
        //生成并设置单号  防止单号重复
        tPublicSaleZ_JingDong.setcSheetNo(
                        posstation.getcStoreNo()+posstation.getPosid()
                        +new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                        +getRandom(10000,99999));
        //主表单号
        String cSheeetNo=tPublicSaleZ_JingDong.getcSheetNo();


        /**
         * @修改时间   2018-12-12
         * @修改内容   获取门店编号  门店 仓库编号   判断是否有误
         */
        String cStoreNo=tPublicSaleZ_JingDong.getcStoreNo();
        String cWhno=tPublicSaleZ_JingDong.getcWHno();
        if(cStoreNo==null || cStoreNo.trim().equals("") ||
                cWhno==null || cWhno.trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.STORENO_OR_WHNO_ERROR.getValue(),ResultOk.STORENO_OR_WHNO_ERROR.getDesc()).toString();
        }

        int Serialnumber=1;
        for(tPublicSaleDetail_JingDong_z  zlist:tPublicSaleDetail_JingDong_zlist){
            zlist.setcSheetNo(cSheeetNo);
            zlist.setcStoreNo(cStoreNo);
            zlist.setcWHno(cWhno);
            if(zlist.getbAuditing()==null){
                zlist.setbAuditing("0");
            }
            zlist.setiSeed(""+Serialnumber);

            if(zlist.getbWeight()==null || (!zlist.getbWeight().trim().equals("0") && !zlist.getbWeight().trim().equals("1"))){
                zlist.setbWeight("0");
            }

            Serialnumber++;
        }
        try{

            SCanDao.p_saveSheetNo_Z_call(posstation.getcStoreNo().trim(),
                    posstation.getPosid().trim(),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()),  //
                    sheetNotrue,//单号
                    ""+Serialnumber,
                    posstation.getPos_Day().trim()+".dbo.p_saveSheetNo");

            /**
             * 开始做真正的插入数据库
             */
            SCanDao.insert_tPublicSale_JingDong_z(tPublicSale_JingDong_zlist);

            /*
             @描述  这里一定要仔细看啊  动态传输表名
             */
            Map mapDetail=new HashMap();
            mapDetail.put("list",tPublicSaleDetail_JingDong_zlist);
            mapDetail.put("tableName","tPublicSaleDetail_JingDong_z");
            SCanDao.insert_tPublicSaleDetail_JingDong_z(mapDetail);

            /**
             * 下面是把数据插入到临时表中
             */
            Map map = new HashMap();
            map.put("cSheetNo", cSheeetNo);
            map.put("appId",appId);
            map.put("tableName",(posstation.getPos_Day()+".dbo.pos_SaleSheetDetailTemp").trim());
            map.put("iFlag", 0);
            SCanDao.calAmountOrder(map);
            Integer iFlag = (Integer)map.get("iFlag");

            //去数据库中查询并获取到在那个数据库执行
            logger.error("单号   "+cSheeetNo);

            //插入完临时表后做计算  计算完后做查询
            Map map1=new HashMap();
            map1.put("callName",(posstation.getPos_Day()+".dbo.p_ProcessPosSheet").trim());
            map1.put("cStoreNo", cStoreNo);
            map1.put("cPosID",posstation.getPosid());
            map1.put("cSaleSheetNo", cSheeetNo);
            map1.put("cVipNo",vipNo);
            map1.put("fVipRate", fVipRate);
            map1.put("bDiscount", bDiscount);
//            List<preferentialGoods> list =get_preferentialGoodsS(cStoreNo,posstation.getPosid(),cSheeetNo,
//                    vipNo,String.valueOf(fVipRate),String.valueOf(bDiscount),(posstation.getPos_Day()+".dbo.p_ProcessPosSheet").trim());
            //强制改变事物
//            Connection conn= GetConnection.getConn(posstation.getPos_Day());
//            CallableStatement c = null;
//            try {
//                c = conn.prepareCall("{call p_ProcessPosSheet (?,?,?,?,?,?)}");
//                c.setString(1, cStoreNo);
//                c.setString(2, posstation.getPosid());
//                c.setString(3, cSheeetNo);
//                c.setString(4, vipNo);
//                c.setString(5, fVipRate);
//                c.setString(6, bDiscount);
//                c.execute();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                logger.info("获取整单优惠信息失败 Connection ：{} ",e.getMessage());
//            }finally {
//                DB.closeCallState(c);
//                DB.closeConn(conn);
//            }
            List<preferentialGoods> list =SCanDao.get_preferentialGoodsTwo(map1);
            VipAddScore vipAddScore=SCanDao.getVipScoreAdd(cSheeetNo,
                        "100",posstation.getPos_Day()+".dbo.p_CountVipScore_Online");
            //list =SCanDao.get_preferentialGoodsTwo(map1);
            //这里重置单号返回信息
            for(preferentialGoods p:list){
                //p.setcSaleSheetno_time(sheetNotrue);
                p.setCSaleSheetno_time(sheetNotrue);
                p.setVipScoreAdd(vipAddScore.getVipAddScore());
            }
            result=new resultMsg(true,listBean.getBeanJson(list), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
        }catch (Exception e){
            logger.error("京东下订单出错   "+e.getMessage());
            throw new RuntimeException("京东下单出错了");
        }
        return result;
    }

    /**
     *  把京东数据插入到我们erp结算表
     * @param list  POS_SaleSheetDetail结算详情表集合
     * @param commSheetNo 单号
     * @param posstation
     * @param saleType  结款方式
     * @return
     */
    @Override
    public String insert_jiesuan_sure(List<POS_SaleSheetDetail> list, commSheetNo commSheetNo,
                                      posstation posstation,String saleType,
                                      String vipNo,String vipScoreAdd,String cSheetNoJD) {
        String result=new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();

        int i=1;
        Double sumMoney=0.0;
        String cWHno="";

        String cSheetNo=cSheetNoJD.equals("") ? commSheetNo.getcSheetNo():cSheetNoJD;

        //TODO 这里可以写个方法  如果存在直接返回

        for(POS_SaleSheetDetail t:list){
            cWHno=t.getcWHno();

            t.setcSaleTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            t.setbHideQty("0");
            t.setiLineNo_Banci("1");
            t.setbSettle("0");
            t.setcSaleSheetno(cSheetNo);//设置单号
            t.setfVipScore("0");
            t.setbChecked("0");
            t.setfVipScore_cur(vipScoreAdd);
            //t.setbAuditing("0");
            t.setbVipRate("0");
            t.setiSeed(""+i);

            i= i+1;
            if(t.getbWeight()==null || (!t.getbWeight().trim().equals("0") && !t.getbWeight().trim().equals("1"))){
                t.setbWeight("0");
            }
            //t.setfVipRate("100");
            t.setbPost("0");
            t.setcOperatorno(posstation.getPosid());
            t.setcOperatorName(posstation.getPosid());
            t.setdSaleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            t.setdFinanceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            t.setfNormalVipScore("0");
            t.setcVipCardno("");
            t.setbVipPrice("0");
            t.setbHidePrice("0");
            t.setcBanci("早班");
            t.setcWorkerno("1");
            t.setcBanci_ID(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_1");
            //t.setfAgio("0");
            t.setcVipNo(vipNo);

            sumMoney = sumMoney +Double.parseDouble(t.getfLastSettle());
            logger.info("我是单号 {}",t.getcSaleSheetno());
        }

        String str= listBean.getBeanJson(list);
        logger.info("数据集合 POS_SaleSheetDetail"+str);

        List<pos_jiesuan> listpos_jiesuan=new ArrayList<pos_jiesuan>();

        pos_jiesuan posJiesuan=new pos_jiesuan(
                cSheetNo,  //设置单号 commSheetNo.getcSheetNo()
                saleType,
                String.valueOf(sumMoney),
                "100",
                "0",
                String.valueOf(sumMoney),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                "0",
                posstation.getPosid(),
                posstation.getPosid(),
                ""+i,
                "0",
                "0",
                "0",
                saleType,
                "0",
                cWHno,
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_1",
                "1",
                "早班");

        listpos_jiesuan.add(posJiesuan);

        str= listBean.getBeanJson(listpos_jiesuan);
        logger.info("数据集合 pos_jiesuan"+str);

        //结算表
        Map mapposDetail=new HashMap();
        mapposDetail.put("list",listpos_jiesuan);
        mapposDetail.put("tableName",posstation.getPos_Day().trim()+".dbo.pos_jiesuan");

        //结算详情表
        Map mapDetail=new HashMap();
        mapDetail.put("list",list);
        mapDetail.put("tableName",posstation.getPos_Day().trim()+".dbo.POS_SaleSheetDetail");

        //下面是自己写的事物判断  因为表名字是动态的  影响了mybatis的事务管理
        // 在这里开启mybatis的事物已经不起作用了  所以自己写事务管理
        try{

            //插入主表
            int a=SCanDao.insert_pos_jiesuan(mapposDetail);

            //删除主表
            Map del_pos_jiesuan=new HashMap();
            del_pos_jiesuan.put("sheetno",commSheetNo.getcSheetNo());
            del_pos_jiesuan.put("tableName",posstation.getPos_Day().trim()+".dbo.pos_jiesuan");

            //删除附表
            Map del_POS_SaleSheetDetail=new HashMap();
            del_POS_SaleSheetDetail.put("sheetno",commSheetNo.getcSheetNo());
            del_POS_SaleSheetDetail.put("tableName",posstation.getPos_Day().trim()+".dbo.POS_SaleSheetDetail");


            //更改保存单号
            if(a>0){
                int c=0;
                try{
                    c=SCanDao.insert_POS_SaleSheetDetail(mapDetail);
                }catch (Exception e){

                    //删除主表数据
                    SCanDao.delete_pos_jiesuan(del_pos_jiesuan);
                    logger.info("数据保存失败 {}",e.getMessage());
                    throw new RuntimeException("京东 订单回传 数据保存失败");
                }
                if(c>0){
                    try{
                        if(!cSheetNoJD.equals("")){
                            SCanDao.p_saveSheetNo_Z_call(posstation.getcStoreNo().trim(),
                                    posstation.getPosid().trim(),
                                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()),  //
                                    commSheetNo.getcSheetNo(),//单号
                                    ""+i,
                                    posstation.getPos_Day().trim()+".dbo.p_saveSheetNo");
                        }
                        //增加积分的
                        if(!"".equals(vipNo)){
                            this.update_VipS(posstation.getAppId(),posstation.getcMachineID(),vipNo,Double.parseDouble(vipScoreAdd));
                        }

                        //返回我们数据库中的单号
                        JSONObject json=new JSONObject();
                        json.put("cSheeetNo",cSheetNo);
                        result=new resultMsg(true,json.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                    }catch (Exception e){

                        //删除主表数据
                        SCanDao.delete_pos_jiesuan(del_pos_jiesuan);
                        //删除附表数据
                        SCanDao.delete_POS_SaleSheetDetail(del_POS_SaleSheetDetail);
                        logger.info("数据保存失败 {}",e.getMessage());
                        throw new RuntimeException("京东 订单回传 数据保存失败");
                    }

                }

            }

        }catch (Exception e){
            logger.info("数据保存失败 {}",e.getMessage());
            throw new RuntimeException("京东 订单回传 数据保存失败");
        }

        return result;
    }

    @Override
    public String updateOrderTypeToJdS(Map map) {
        return SCanDao.updateOrderTypeToJd(map);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout=36000,
            rollbackFor={RuntimeException.class, Exception.class},
            readOnly = false)
    public String placing_ruturnorder(List<returntPublicSale_z> returntPublicSale_z, List<tPublicSaleDetail_z> tPublicSaleDetail_zlist) {
        String result=new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        returntPublicSale_z returntPublicSalez=null;
        for(returntPublicSale_z tPublicSaleZ1:returntPublicSale_z){
            returntPublicSalez=tPublicSaleZ1;
            tPublicSaleZ1.setBeizhu("退货");
            //1 代表正常销售   2 代表退货
            tPublicSaleZ1.setReturnSale("2");
        }
        String cSheeetNo=returntPublicSalez.getcSheetNo();

        if(cSheeetNo==null || cSheeetNo.trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.ORDERNO_ERROR.getValue(),ResultOk.ORDERNO_ERROR.getDesc()).toString();
        }

        if(returntPublicSalez.getReturncSheetNo()==null || returntPublicSalez.getReturncSheetNo().trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.RETURN_ORDERNO_ERROR.getValue(),ResultOk.RETURN_ORDERNO_ERROR.getDesc()).toString();
        }


        /**
         * @修改时间   2018-12-12
         * @修改内容   获取门店编号  门店 仓库编号   判断是否有误
         */
        String cStoreNo=returntPublicSalez.getcStoreNo();
        String cWhno=returntPublicSalez.getcWHno();
        if(cStoreNo==null || cStoreNo.trim().equals("") ||
                cWhno==null || cWhno.trim().equals("")){
            return new resultMsg(false,"[]", ResultOk.STORENO_OR_WHNO_ERROR.getValue(),ResultOk.STORENO_OR_WHNO_ERROR.getDesc()).toString();
        }

        List<tPublicSale_z> list=SCanDao.get_tPublicSale_z(returntPublicSalez.getcSheetNo(),returntPublicSalez.getAppId());
        if(list!=null && list.size()>0){
            return new resultMsg(false,"[]", ResultOk.ORDERNO_EXIST.getValue(),ResultOk.ORDERNO_EXIST.getDesc()).toString();
        }
        list=SCanDao.get_tPublicSale_z(returntPublicSalez.getReturncSheetNo(),returntPublicSalez.getAppId());
        if(list==null || list.size()<1){
            return new resultMsg(false,"[]", ResultOk.ORDERNO_ORIGINAL_EXIST.getValue(),ResultOk.ORDERNO_ORIGINAL_EXIST.getDesc()).toString();
        }
        int Serialnumber=1;
        for(tPublicSaleDetail_z  zlist:tPublicSaleDetail_zlist){
            zlist.setcSheetNo(cSheeetNo);
            zlist.setcStoreNo(cStoreNo);
            zlist.setcWHno(cWhno);
            if(zlist.getbAuditing()==null){
                zlist.setbAuditing("0");
            }
            zlist.setiSeed(""+Serialnumber);
            Serialnumber++;
        }

        try{
            SCanDao.insert_returntPublicSale_z(returntPublicSale_z);
            SCanDao.insert_tPublicSaleDetail_z(tPublicSaleDetail_zlist);

            JSONObject json=new JSONObject();
            json.put("cSheeetNo",cSheeetNo);
            result=new resultMsg(true,json.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
        }catch (Exception e){
            logger.error("退单出错   "+e.getMessage());
            throw new RuntimeException("退单出错了");

        }
        return result;
    }

    /**
     * 根据单号得到订单信息
     * @param cSheetNo
     * @return
     */
    @Override
    public String get_Order(String cSheetNo,String appId) {
       try{
            List<returntPublicSale_z> list=SCanDao.get_returntPublicSale_z(cSheetNo,appId);
            List<tPublicSaleDetail_z> list1=null;
            if(list!=null && list.size()>0){
                list1=SCanDao.get_tPublicSaleDetail_z(cSheetNo);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("sale", listBean.getBeanJson(list));
                jsonObject.put("saleDatial", listBean.getBeanJson(list1));
                return new resultMsg(true,jsonObject.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
            }
       }catch (Exception e){
           e.printStackTrace();
           logger.error(e.getMessage());
           return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
       }
        return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
    }

    /**
     *
     * @param cSheetNo 通过单号 和 APPiD 查询本单据信息
     * @param appId
     * @return
     */
    @Override
    public String get_Order_JD(String cSheetNo, String appId) {
        try{
            List<tPublicSale_JingDong_z> list=SCanDao.get_tPublicSale_JingDong_z(cSheetNo,appId,null);
            List<tPublicSaleDetail_JingDong_z> list1=null;
            if(list!=null && list.size()>0){
                list1=SCanDao.get_tPublicSaleDetail_JingDong_z(cSheetNo);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("sale", listBean.getBeanJson(list));
                jsonObject.put("saleDatial", listBean.getBeanJson(list1));
                return new resultMsg(true,jsonObject.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
        return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
    }

    //更改订单状态
    @Override
    public String update_DingdanType(String cSheetNo, String appId) {
        try{
            Map map = new HashMap();
            map.put("cSheetNo", cSheetNo);
            map.put("appId",appId);
            map.put("iFlag", 0);

            SCanDao.calAmountOrder(map);
            Integer iFlag = (Integer)map.get("iFlag");
            if(iFlag > 0) {
                return new resultMsg(true, "[]", ResultOk.SUCCESS.getValue(), ResultOk.SUCCESS.getDesc()).toString();
            }
            return new resultMsg(false,"[]",ResultOk.ORDERNO_ISNOT_KONW.getValue(),ResultOk.ORDERNO_ISNOT_KONW.getDesc()).toString();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }

    }


    @Override
    public void upload_MingShengOrder_byPage(String btn, String etn, String pageNum, String pagenumber, String appid, String miyao, String supplierid, String url) {
        String num=pageNum;
        int k=1;
        while (true){

            logger.info("我是第"+k+"次循环"+" 循环的页数是"+num);
            k=k+1;
            Map<String, String> form = new HashMap<String, String>();
            form.put("appKey", appid);              //appId  "462"
            form.put("v", "1.0");
            form.put("method", "minshengec.supplier.order.get");
            form.put("format", "json");
            form.put("supplierId", supplierid);        //供应商id
            form.put("timestamp", String_Tool.DataBaseTime());
            form.put("orderStatus", "2");          //已完成的订单
            form.put("receiveType", "1");
            form.put("pageIndex", num);
            form.put("pageSize", pagenumber);
            form.put("type", "0");
            form.put("beginDate", btn);//"2018-01-01 12:22:33"
            form.put("endDate", etn);
            String sign = RopUtils.sign(form, miyao);//"438F6241DD9511E4B9E70050568E0E81"
            form.put("sign", sign);
            String result =sendPost(url, form);//"http://111.198.131.250:19600/router"
            if(result.equals("")){
                logger.error("获取民生商城的订单的数据异常");
                break;
            }
            //这个是真实的数据  这里不打印
            //logger.info("我是从民生获取的订单数据 "+result);
            org.json.JSONObject json=null;
            try{
                json=new org.json.JSONObject(result);
            }catch (Exception e){

                e.printStackTrace();
                logger.error("民生数据Json 转换错误");
                break;
            }
            //没有这个值
            if(!json.has("totalResults")){
                logger.error("获取民生的数据异常");
                break;
            }
            int number=json.getInt("totalResults");//orderInfos
            if(json.get("orderInfos").toString().equals("null")){
                break;
            }

            System.out.println(json.getInt("totalResults"));
            JSONArray jsonArray=JSONArray.fromObject(json.get("orderInfos").toString());
            List<t_MinShengPublicSale_z> t_MinShengPublicSale_z_list=JSONArray.toList(jsonArray,new t_MinShengPublicSale_z(),new JsonConfig());
            for(t_MinShengPublicSale_z t:t_MinShengPublicSale_z_list){
                if(t.getOfflineStoresId()==null){
                    t.setOfflineStoresId("");
                }
                t.setBeginTime(btn.toString());
                t.setEndTime(etn.toString());
            }
            //String str= listBean.getBeanJson(t_MinShengPublicSale_z_list);
            //System.out.println(" 我是 t_MinShengPublicSale_z_list"+str);
            List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist=new ArrayList<t_MingShengPublicSaleDetail_z>();

            for(int i=0;i<jsonArray.size();i++){
                org.json.JSONObject jsonObject=new org.json.JSONObject(jsonArray.get(i).toString());
                String setOfflineStoresId="";
                String OrderSn=jsonObject.getString("orderSn");
                if(jsonObject.has("offlineStoresId")){
                    setOfflineStoresId=jsonObject.getInt("offlineStoresId")+"";
                }
                JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("orderItems").toString());
                List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist_item=
                        JSONArray.toList(jsonArray2,new t_MingShengPublicSaleDetail_z(),new JsonConfig());
                for(t_MingShengPublicSaleDetail_z t:t_MingShengPublicSaleDetail_zlist_item){
                    t.setOfflineStoresId(setOfflineStoresId);
                    t.setOrderSn(OrderSn);
                    if(t.getWeight()==null){
                        t.setWeight(0.0);
                    }
                    if(t.getFullName()==null){
                        t.setFullName("");
                    }
                    if(t.getQuantity()==null){
                        t.setQuantity(0.0);
                    }
                }
                t_MingShengPublicSaleDetail_zlist.addAll(t_MingShengPublicSaleDetail_zlist_item);

            }
            //str= listBean.getBeanJson(t_MingShengPublicSaleDetail_zlist);
            //System.out.println("我是orderItems中的打印"+str);
            try{
                InsertSaleMngsheng( t_MinShengPublicSale_z_list, t_MingShengPublicSaleDetail_zlist);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("民生订单下拉出错了-插入数据库出错");
                throw new RuntimeException("插入数据库出错");
            }
            num=String.valueOf(Integer.parseInt(num)+1);
            //删除缓存表中的数据  先拷贝   再删除数据
            mingSheng.minshengCopy("");
        }
    }

    //减少一次
    @Override
    public Integer update_leftNumber_reduceS(String appId) {
        return SCanDao.update_leftNumber_reduce(appId);
    }

    @Override
    public tThirdUsersAll get_tThirdUsersAllS(String appId) {
        return SCanDao.get_tThirdUsersAll(appId);
    }

    @Override
    public List<t_goodsKuCurQty_wei> get_t_goodsKuCurQty_weiS(String cStoreNo, List<String> cGoodsNoList) {
        return SCanDao.get_t_goodsKuCurQty_wei(cStoreNo, cGoodsNoList);
    }

    @Override
    public List<t_VipcGoodsPrice> gett_VipcGoodsPriceS(String cStoreNo) {
        return SCanDao.gett_VipcGoodsPrice(cStoreNo);
    }

    @Override
    public List<t_PloyOfSale> gett_t_PloyOfSaleS(String cStoreNo) {
        return SCanDao.gett_t_PloyOfSale(cStoreNo);
    }

    @Override
    public List<t_PloyGroupOfSale> selectByNowDataS(String cStoreNo) {
        return SCanDao.selectByNowData(cStoreNo);
    }

    @Override
    public List<t_PloyOfSale_GoodsType> gett_PloyOfSale_GoodsTypeS(String cStoreNo) {
        return SCanDao.gett_PloyOfSale_GoodsType(cStoreNo);
    }

    @Override
    public List<posConfig> selectAllS(String tableName, String cID) {
        return SCanDao.selectAll(tableName,cID);
    }

    @Override
    public Integer update_VipS(String appId, String machineId, String vipNo, Double addScore) {
        return SCanDao.update_Vip(appId, machineId, vipNo, addScore);
    }

    @Override
    public posstation select_posstationS(String appId, String machineId, String cStoreNo) {
        return SCanDao.select_posstation(appId, machineId, cStoreNo);
    }

    @Override
    public List<preferentialGoods> get_preferentialGoodsS(String cStoreNo, String machineId, String cSheetNo, String vipNo, String fVipScoreRatio, String bDiscount, String callName) {
        return SCanDao.get_preferentialGoods(cStoreNo, machineId, cSheetNo, vipNo, fVipScoreRatio, bDiscount, callName);
    }

    @Override
    public commSheetNo getCommSheetNoS(String cStoreNo, String cPosID, String Zdriqi, String callName) {
        return SCanDao.getCommSheetNo(cStoreNo, cPosID, Zdriqi, callName);
    }


    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout=36000,
            rollbackFor={RuntimeException.class, Exception.class},
            readOnly = false)
    public void InsertSaleMngsheng(List t_MinShengPublicSale_z_list,List t_MingShengPublicSaleDetail_zlist){
        try{
            int a=mingSheng.insert_t_MingShengPublicSaleDetail_z(t_MingShengPublicSaleDetail_zlist);
            if(a>0){
                try{
                    mingSheng.insert_t_MinShengPublicSale_z(t_MinShengPublicSale_z_list);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException("插入数据库出错");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("插入数据库出错");
        }

    }
}
