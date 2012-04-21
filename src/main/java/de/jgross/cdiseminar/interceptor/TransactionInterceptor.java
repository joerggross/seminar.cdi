/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.interceptor;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * Simple basic transaction interceptor.
 * 
 * @author Jörg Groß
 */
@RequireTx @Interceptor
public class TransactionInterceptor implements Serializable {

    @Resource
    private UserTransaction utx;

    /**
     * Handles the transaction.
     * @param ic
     * @return
     * @throws Throwable 
     */
    @AroundInvoke
    public Object handleTransaction(InvocationContext ic) throws Throwable {
        boolean txStarted = false;
        if (utx.getStatus() != Status.STATUS_ACTIVE) {
            System.out.println("TransactionInterceptor: starte Tx");
            utx.begin();
            txStarted = true;
        }
        Object result = null;
        try {
            result = ic.proceed();

            if (txStarted) {
                utx.commit();
            }
        } catch (Throwable t) {
            if (txStarted) {
                utx.rollback();
            }
            throw t;
        }
        return result;
    }
}
