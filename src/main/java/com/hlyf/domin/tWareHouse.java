package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author  zmy
 * @version
 * @descripet  描述  仓库表
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class tWareHouse implements Serializable {
    private String cWhNo;
    private String cWh;
    private String cStoreNo;
}
