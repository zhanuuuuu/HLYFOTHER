package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *   id     BIGINT  IDENTITY(1,1) PRIMARY KEY,
     appId     VARCHAR(200),
     userName  VARCHAR(200),
     userIp    VARCHAR(200),
     visitTime  DATETIME DEFAULT (GETDATE()),
     visitData  VARCHAR(8000)
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class tThirdUsers_Log implements Serializable {

    private String appId;
    private String userName;
    private String userIp;
    private String visitData;

}
