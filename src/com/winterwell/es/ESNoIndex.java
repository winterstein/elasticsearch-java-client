
package com.winterwell.es;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A field marker for "ElasticSearch should store but NOT index this object"
 * 
 * Usage: This will NOT do anything by itself!
 * You'll need reflection code to spot annotations and make ES mappings.
 * AppUtils.initESMappings() does this.
 *
 * @see ESType#noIndex()
 * @author daniel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ESNoIndex {

}
