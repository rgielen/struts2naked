package net.itneering.struts2naked.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.inject.Inject;

/**
 * TxInterceptor.
 *
 * @author Rene Gielen
 */
public class TxInterceptor implements Interceptor {

	@Inject
	PlatformTransactionManager transactionManager;

	public String intercept( ActionInvocation invocation ) throws Exception {
		TransactionStatus status = createTransactionContext();

		try {
			// Invoke Action
			String result = invocation.invoke();

			conditionallyMarkForRollback(status, result);

			endTransaction(status);
			return result;

		} catch ( Exception e ) {
			return rollbackAndThrow(status, e);
		}
	}

	private void conditionallyMarkForRollback( TransactionStatus status, String result ) {
		if (Action.INPUT.equals(result)|| Action.ERROR.equals(result)) {
			// Special treatment of INPUT/ERROR result, forcing rollback
			status.setRollbackOnly();
		}
	}

	private void endTransaction( TransactionStatus status ) {
		if (!status.isCompleted()) {
			// Life's good, lets's commit
			transactionManager.commit(status);
		}
	}

	private TransactionStatus createTransactionContext() {
		TransactionStatus status;DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("TransactionalInterceptor@" + def.hashCode());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		// Create Transaction Context
		status = transactionManager.getTransaction(def);
		return status;
	}

	private String rollbackAndThrow( TransactionStatus status, Exception e ) throws Exception {
		// Bad things happened - an Exception popping up here is should force a rollback
		if (!status.isCompleted()) {

			transactionManager.rollback(status);

		}
		// Finally, we'll be nicely rethrowing the exception
		throw e;
	}
	public void destroy() {
	}

	public void init() {
	}

}
