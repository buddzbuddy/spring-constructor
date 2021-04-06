package com.webdatabase.dgz.query.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target({FIELD})
public @interface MetaFieldName {
	public String label() default "";
	public String selectClassName() default "";
	public String selectClassFieldName() default "name";
}
