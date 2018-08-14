package com.winterwell.es;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A field marker for "ElasticSearch should treat this as a keyword (not text)"
 * - since that is the most common schema error. 
 * 
 * Usage: This will NOT do anything by itself!
 * You'll need your own reflection code to spot annotations and make ES mappings.
 * TODO refactor AppUtils mapping-maker into a generic class here.
 * 
 * @author daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ESKeyword {

}
