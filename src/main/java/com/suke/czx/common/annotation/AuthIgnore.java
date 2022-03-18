package com.suke.czx.common.annotation;

import java.lang.annotation.*;

/**
 * api接口，忽略Token验证
 *
 * @author czx
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

}
