/* 
 * Copyright 2014 LianXi. All rights reserved.
 * LianXi PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @Column.java - 2014-10-16 - OCEAN
 */

package com.goldmantis.wb.viewdemo.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author OCEAN
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GsonExclude {
    
    public boolean needExcude() default false;
}

