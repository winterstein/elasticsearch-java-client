package com.winterwell.es;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A field marker for "ElasticSearch should treat this as a keyword (not text)"
 * - since that is the most common schema error. 
 * @author daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ESKeyword {

}
