package com.hlyf.dao.cluster;

import com.hlyf.domin.mingsheng.t_MingShengTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by Administrator on 2018-11-27.
 * 民生业务对接
 */
@Mapper
public interface mingSheng {

    Integer insert_t_MinShengPublicSale_z(List list);

    Integer insert_t_MingShengPublicSaleDetail_z(List list);

    void minshengCopy(@Param("cBarcode") String cBarcode);//这里的参数没用

    t_MingShengTime get_t_MingShengTime(@Param("cBarcode") String cBarcode);//无用
}
