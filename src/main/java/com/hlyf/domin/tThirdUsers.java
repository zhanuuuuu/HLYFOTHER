package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-11-16.
 * appId     VARCHAR(200) PRIMARY KEY,
 appSercet VARCHAR(300),
 userName  VARCHAR(200)
 * @Descreption  第三方数据信息表
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class tThirdUsers implements Serializable {
    private String appId;
    private String appSercet;
    private String userName;
}
