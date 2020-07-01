package com.core.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Create by LiJie at 2019-05-27
 * api接口baseUrl注解，未使用该注解时默认使用ApiService init初始化传入的baseUrl
 */
@Target(ElementType.TYPE)
public @interface BaseUrl {

    String value();

}
