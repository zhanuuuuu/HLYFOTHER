package com.hlyf.domin;

/**
 * Created by Administrator on 2018-11-29.
 * SELECT top 10 cGoodsNo,EndQty,Endmoney,cStoreNo,cStoreName from t_goodsKuCurQty_wei
 */
public class t_goodsKuCurQty_wei {

    private String cGoodsNo;
    private Double EndQty;
    private String cStoreNo;

    public String getcGoodsNo() {
        return cGoodsNo;
    }

    public void setcGoodsNo(String cGoodsNo) {
        this.cGoodsNo = cGoodsNo;
    }

    public Double getEndQty() {
        return EndQty;
    }

    public void setEndQty(Double endQty) {
        EndQty = endQty;
    }

    public String getcStoreNo() {
        return cStoreNo;
    }

    public void setcStoreNo(String cStoreNo) {
        this.cStoreNo = cStoreNo;
    }

    @Override
    public String toString() {
        return "t_goodsKuCurQty_wei{" +
                "cGoodsNo='" + cGoodsNo + '\'' +
                ", EndQty=" + EndQty +
                ", cStoreNo='" + cStoreNo + '\'' +
                '}';
    }
}
