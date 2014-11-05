/**
 * 
 */
package com.hehua.mis.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.Assert;

/**
 * @author zhihua
 *
 */
public class DebugThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    private static final Log logger = LogFactory
            .getLog(DebugThreadLocalSecurityContextHolderStrategy.class);

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();

    //~ Methods ========================================================================================================

    @Override
    public void clearContext() {
        if (logger.isDebugEnabled()) {
            logger.debug("clearContext " + contextHolder.get(), new Exception());
        }

        contextHolder.remove();
    }

    @Override
    public SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    @Override
    public void setContext(SecurityContext context) {

        if (logger.isDebugEnabled()) {
            logger.debug("setContext " + context, new Exception());
        }

        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(context);
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }
}
