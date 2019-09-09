package com.hlyf.dao.cluster;

import com.hlyf.domin.Qx.SaleSheetHelp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019-09-09.
 */
@Mapper
public interface QxDao {

    int deleteByPrimaryKey(@Param("dSaleDate") Date dSaleDate, @Param("cSaleSheetno") String cSaleSheetno, @Param("iSeed") Integer iSeed, @Param("cStoreNo") String cStoreNo);

    int deleteSaleSheetHelp(@Param("cSaleSheetno") String cSaleSheetno);

    int insert(SaleSheetHelp record);

    SaleSheetHelp selectByPrimaryKey( @Param("cSaleSheetno") String cSaleSheetno);

    List<SaleSheetHelp> selectAll();

    int updateByPrimaryKey(SaleSheetHelp record);

}
