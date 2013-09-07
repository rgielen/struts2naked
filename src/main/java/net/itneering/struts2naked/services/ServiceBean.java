package net.itneering.struts2naked.services;

import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * ServiceBean.
 *
 * @author Rene Gielen
 */
@Named
public class ServiceBean {

	@Transactional
	public String getFoo() {
		return "foo";
	}
}
