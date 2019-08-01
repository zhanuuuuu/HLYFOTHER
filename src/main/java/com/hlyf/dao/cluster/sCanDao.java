package com.hlyf.dao.cluster;

import com.hlyf.domin.*;
import com.hlyf.domin.JDcom.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-13.
 */
@Mapper
public interface sCanDao {

    List<Store> get_cStroe(@Param("cStroreNo") String cStroreNo);
    List<vip>   get_vip(@Param("mobileNo") String mobileNo);
    List<t_vipInfo> get_vipInfo(@Param("mobileNo") String mobileNo);

    //获取商品基本信息
    List<cStoreGoods>  get_cStoreGoods(@Param("cStoreNo") String cStoreNo,@Param("barcodeList")List<String> barcodeList);

    //获取商品基本信息 单店
    List<cStoreGoods>  get_cStoreGoodsDanDian(@Param("cStoreNo") String cStoreNo,@Param("barcodeList")List<String> barcodeList);

    List<t_goodsKuCurQty_wei>  get_t_goodsKuCurQty_wei(@Param("cStoreNo") String cStoreNo,@Param("cGoodsNoList")List<String> cGoodsNoList);
    List<tPloyOfSale>  get_tPloyOfSale(@Param("cStoreNo") String cStoreNo,@Param("cBarcode") String cBarcode,@Param("num") int num);
    List<tThirdUsers>  get_tThirdUsers(@Param("appId") String appId);

    List<cGoods>  get_cGoods(@Param("cStoreNo") String cStoreNo,@Param("pageNum") String pageNum,@Param("number") String number);

    Integer insert_tThirdUsers_Log(@Param("appId") String appId,@Param("userName") String userName,
                                   @Param("userIp") String userIp,@Param("visitData") String visitData);

    List<tWareHouse> get_tWareHouse(@Param("cStoreNo") String cStoreNo);

    List<tMethod_of_payment> get_tMethod_of_payment();


    Integer insert_tPublicSale_z(List list);

    Integer insert_tPublicSaleDetail_z(List list);

    //京东插入销售主表数据
    Integer insert_tPublicSale_JingDong_z(List list);

    //京东插入销售附表表数据
    Integer insert_tPublicSaleDetail_JingDong_z(Map map);

    List<tPublicSale_z> get_tPublicSale_z(@Param("cSheetNo") String cSheetNo,@Param("appId") String appId);

    List<tPublicSale_JingDong_z>  get_tPublicSale_JingDong_z(@Param("cSheetNo") String cSheetNo, @Param("appId") String appId, @Param("appSheetNo") String appSheetNo);

    List<tPublicSaleDetail_JingDong_z> get_tPublicSaleDetail_JingDong_z(@Param("cSheetNo") String cSheetNo);

    /**
     * 更改订单状态
     * @param map
     * @return
     */
    String updateOrderTypeToJd(Map map);

    /**
     * @描述 计算整单优惠的过程  留给丰总写的
     * @param map
     * @return
     */
    String calAmountOrder(Map map);

    Integer insert_returntPublicSale_z(List list);

    List<returntPublicSale_z> get_returntPublicSale_z(@Param("cSheetNo") String cSheetNo,@Param("appId") String appId);

    List<tPublicSaleDetail_z> get_tPublicSaleDetail_z(@Param("cSheetNo") String cSheetNo);

    //每天凌晨00点  恢复
    Integer update_leftNumber();

    Integer update_leftNumber_reduce(@Param("appId") String appId);

    tThirdUsersAll  get_tThirdUsersAll(@Param("appId") String appId);

    //查询期间会员特价商品
    List<t_VipcGoodsPrice> gett_VipcGoodsPrice(@Param("cStoreNo") String cStoreNo);

    //得到促销的
    List<t_PloyOfSale> gett_t_PloyOfSale(@Param("cStoreNo") String cStoreNo);

    //得到当前自定义组合促销
    List<t_PloyGroupOfSale> selectByNowData(@Param("cStoreNo") String cStoreNo);

    //品类打折促销
    List<t_PloyOfSale_GoodsType> gett_PloyOfSale_GoodsType(@Param("cStoreNo") String cStoreNo);


    List<posConfig> selectAll(@Param("tableName")String tableName,@Param("cID")String cID);

    Integer update_Vip(@Param("appId")String appId,@Param("machineId")String machineId,
                       @Param("vipNo")String vipNo,@Param("addScore")Double addScore);

    posstation select_posstation(@Param("appId")String appId,
                                       @Param("machineId")String machineId,
                                       @Param("cStoreNo") String cStoreNo);

    List<preferentialGoods> get_preferentialGoods(@Param("cStoreNo")String cStoreNo,@Param("machineId")String machineId,
                                                  @Param("cSheetNo")String cSheetNo,@Param("vipNo")String vipNo,
                                                  @Param("fVipScoreRatio")String fVipScoreRatio,@Param("bDiscount")String bDiscount,
                                                  @Param("callName")String callName);
    List<preferentialGoods>  get_preferentialGoodsTwo(Map map);
    //保存单号的
//    @cStoreNo VARCHAR(32),
//    @cPosID VARCHAR(32),
//    @Zdriqi VARCHAR(32),
//    @SerNo VARCHAR(32),
//    @iSeed_Max VARCHAR(32),
//    @callName VARCHAR(80)
    Integer p_saveSheetNo_Z_call(@Param("cStoreNo")String cStoreNo,@Param("cPosID")String cPosID,
                            @Param("Zdriqi")String Zdriqi,@Param("SerNo")String SerNo,
                            @Param("iSeed_Max")String iSeed_Max,@Param("callName")String callName);
    //获取单号的公共的类
//    CREATE procedure [dbo].[p_getPos_SerialNoSheetNo_Z]
//    @cStoreNo VARCHAR(32),
//    @cPosID VARCHAR(32),
//    @Zdriqi VARCHAR(32),
//    @callName VARCHAR(80)
    commSheetNo getCommSheetNo(@Param("cStoreNo")String cStoreNo,@Param("cPosID")String cPosID,
                               @Param("Zdriqi")String Zdriqi,@Param("callName")String callName);

    List<pos_jiesuan> get_pos_jiesuan();

    //插入结算表
    Integer insert_pos_jiesuan(Map map);

    //结算单详情表
    List<POS_SaleSheetDetail> get_POS_SaleSheetDetail();

    //插入结算单详情表
    Integer insert_POS_SaleSheetDetail(Map map);

    //根据单号删除主表记录
    Integer delete_pos_jiesuan(Map map);

    //根据单号珊瑚附表记录
    Integer delete_POS_SaleSheetDetail(Map map);

    //查询函数的方法
    VipAddScore getVipScoreAdd(@Param("cSheetNo")String cSheetNo,
                               @Param("fVipScoreRatio")String fVipScoreRatio,
                               @Param("callName")String callName);

}
