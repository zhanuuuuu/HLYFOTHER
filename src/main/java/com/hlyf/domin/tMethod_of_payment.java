package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-11-23.
 * payId	payMethod
 1	支付宝支付
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class tMethod_of_payment implements Serializable {
    private Integer payId;
    private String payMethod;
}
