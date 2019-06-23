package com.rajava.demo.rxjavademo2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by CC on 2019/6/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.LOCAL_VARIABLE,ElementType.METHOD})//可以给类接口，
public @interface AnnotationTest {
    int id() default 0;
    String name() default "";
}

@Repeatable(Persons.class)
@interface Person{
   String roal() default "";
}

@interface Persons{
    Person[] value();
}

