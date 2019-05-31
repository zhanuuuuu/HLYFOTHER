package com.hlyf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018-11-15.
 */
@Configuration
public class redisCon {

    @Value("${cluster.isredis}")
    private Boolean isredis;

    @Value("${cluster.encrypt}")
    private Boolean encrypt;

    @Value("${cluster.isredistime}")
    private Integer isredistime;

    public Boolean getIsredis() {
        return isredis;
    }

    public void setIsredis(Boolean isredis) {
        this.isredis = isredis;
    }

    public Integer getIsredistime() {
        return isredistime;
    }

    public void setIsredistime(Integer isredistime) {
        this.isredistime = isredistime;
    }

    public Boolean getRedis() {
        return isredis;
    }

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }

    public void setRedis(Boolean redis) {
        isredis = redis;
    }
}
