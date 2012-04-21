/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 * Mark methods to signal that a transaction is required.
 * 
 * @author Jörg Groß
 */
@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireTx {
}
