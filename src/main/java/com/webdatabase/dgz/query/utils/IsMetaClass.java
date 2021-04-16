package com.webdatabase.dgz.query.utils;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({TYPE})
public @interface IsMetaClass {
	public String label() default "";
	public int orderNo() default 100;
}
