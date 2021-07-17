package com.example.coin.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    /*定义公有静态常量*/
    public static String ENDPOINT="oss-cn-hangzhou.aliyuncs.com";
    public static String KEYID="LTAI4G8aY3VqV55xwwtvfNyi";
    public static String KEYSECRET="NoITeRzQHAoFSm9OTx70Oj1TeK1P0G";
    public static String BUCKETNAME="software-engineering-iii";


    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        KEYID = keyId;
        KEYSECRET = keySecret;
        BUCKETNAME = bucketName;
    }
}
