package com.hlyf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018-11-20.
 */
@Configuration
public class interceptor {

    @Value("${cluster.encrypt}")
    private Boolean encrypt;

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }
}
