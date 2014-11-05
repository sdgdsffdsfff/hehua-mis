package com.hehua.mis.item.param;

import java.lang.annotation.*;

/**
 * Created by hewenjerry on 14-9-4.
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ItemDTOI {
    String value();
}
