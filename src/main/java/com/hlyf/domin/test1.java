package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-05-10.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
@ToString
public class test1 implements Serializable{
    private String   cGoodsNo;
    private String   cGoodsName;
    private String   cBarcode;
    private String   cUnit;
    private String   cSpec;

//    public test1(String cGoodsNo, String cGoodsName, String cBarcode, String cUnit, String cSpec) {
//        this.cGoodsNo = cGoodsNo;
//        this.cGoodsName = cGoodsName;
//        this.cBarcode = cBarcode;
//        this.cUnit = cUnit;
//        this.cSpec = cSpec;
//    }
//
//    public String getcGoodsNo() {
//        return cGoodsNo;
//    }
//
//    public void setcGoodsNo(String cGoodsNo) {
//        this.cGoodsNo = cGoodsNo;
//    }
//
//    public String getcGoodsName() {
//        return cGoodsName;
//    }
//
//    public void setcGoodsName(String cGoodsName) {
//        this.cGoodsName = cGoodsName;
//    }
//
//    public String getcBarcode() {
//        return cBarcode;
//    }
//
//    public void setcBarcode(String cBarcode) {
//        this.cBarcode = cBarcode;
//    }
//
//    public String getcUnit() {
//        return cUnit;
//    }
//
//    public void setcUnit(String cUnit) {
//        this.cUnit = cUnit;
//    }
//
//    public String getcSpec() {
//        return cSpec;
//    }
//
//    public void setcSpec(String cSpec) {
//        this.cSpec = cSpec;
//    }
//
//    @Override
//    public String toString() {
//        return "test1{" +
//                "cGoodsNo='" + cGoodsNo + '\'' +
//                ", cGoodsName='" + cGoodsName + '\'' +
//                ", cBarcode='" + cBarcode + '\'' +
//                ", cUnit='" + cUnit + '\'' +
//                ", cSpec='" + cSpec + '\'' +
//                '}';
//    }
}
