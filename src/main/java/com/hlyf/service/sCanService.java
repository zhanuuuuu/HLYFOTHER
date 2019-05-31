package com.hlyf.service;

import com.hlyf.domin.*;
import com.hlyf.domin.JDcom.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-13.
 */
public interface sCanService {

    /**
     *
     * @param cStroreNo  门店编号
     * @return  Store  返回门店的集合
     */
    List<Store> get_cStroeS(String cStroreNo);

    /**
     *
     * @param mobileNo  手机号
     * @return          会员卡信息
     */
    List<vip>   get_vipS(String mobileNo);

    List<t_vipInfo> get_vipInfoS(@Param("mobileNo") String mobileNo);

    List<cStoreGoods>  get_cStoreGoodsS( String cStoreNo,List<String> barcodeList);

    List<tPloyOfSale>  get_tPloyOfSaleS(String cStoreNo,String cBarcode,int num);

    /**
     *
     * @param cStoreNo
     * @param pageNum
     * @param number
     * @return
     */
    List<cGoods>  get_cGoodsS(String cStoreNo,String pageNum,String number);

    /**
     * 根据门店编号拿到仓库编号
     * @param cStoreNo
     * @return
     */
    List<tWareHouse> get_tWareHouseS(String cStoreNo);

    List<tMethod_of_payment> get_tMethod_of_paymentS();

    /**
     *
     * @param tPublicSale_zlist         销售单集合
     * @param tPublicSaleDetail_zlist  销售单详情
     * @return
     */
    String placing_order(List<tPublicSale_z> tPublicSale_zlist, List<tPublicSaleDetail_z> tPublicSaleDetail_zlist);

    @Deprecated
    String placing_order_JD(List<tPublicSale_JingDong_z> tPublicSale_JingDong_zlist, List<tPublicSaleDetail_JingDong_z> tPublicSaleDetail_JingDong_zlist);

    //获取整单优惠信息
    String getPreGoodsInfoS(List<tPublicSale_JingDong_z> tPublicSale_JingDong_zlist,
                            List<tPublicSaleDetail_JingDong_z> tPublicSaleDetail_JingDong_zlist,
                            String fVipRate,String bDiscount,String tableName,posstation posstation);

    //插入真正的结算表中

    /**
     *
     * @param list  POS_SaleSheetDetail结算详情表集合
     * @param commSheetNo 单号
     * @param posstation
     * @param saleType  结款方式
     * @return
     */
    String insert_jiesuan_sure(List<POS_SaleSheetDetail> list,
                               commSheetNo commSheetNo,posstation posstation,String saleType);

    String updateOrderTypeToJdS(Map map);
    String placing_ruturnorder(List<returntPublicSale_z> returntPublicSale_z, List<tPublicSaleDetail_z> tPublicSaleDetail_zlist);

    String get_Order(String cSheetNo,String appId);

    String get_Order_JD(String cSheetNo,String appId);

    String update_DingdanType(String cSheetNo,String appId);

    void upload_MingShengOrder_byPage(String btn,String etn,String pageNum,
                                        String pagenumber,String appid,
                                        String miyao,String supplierid,String url);

    Integer update_leftNumber_reduceS(String appId);

    tThirdUsersAll  get_tThirdUsersAllS(String appId);

    List<t_goodsKuCurQty_wei>  get_t_goodsKuCurQty_weiS( String cStoreNo,List<String> cGoodsNoList);

    //查询期间会员特价商品
    List<t_VipcGoodsPrice> gett_VipcGoodsPriceS(String cStoreNo);

    List<t_PloyOfSale> gett_t_PloyOfSaleS(String cStoreNo);

    List<t_PloyGroupOfSale> selectByNowDataS(String cStoreNo);

    List<t_PloyOfSale_GoodsType> gett_PloyOfSale_GoodsTypeS(String cStoreNo);

    //得到生鲜码
    List<posConfig> selectAllS(String tableName,String cID);

    Integer update_VipS(String appId,String machineId,
                       String vipNo,Double addScore);

    posstation select_posstationS(String appId,
                                 String machineId,
                                 String cStoreNo);

    List<preferentialGoods> get_preferentialGoodsS(String cStoreNo, String machineId,
                                                  String cSheetNo, String vipNo,
                                                  String fVipScoreRatio, String bDiscount,
                                                  String callName);

    commSheetNo getCommSheetNoS(String cStoreNo,String cPosID,
                               String Zdriqi,String callName);
}
