package net.itneering.struts2naked.actions;

import net.itneering.struts2naked.services.ServiceBean;

import javax.inject.Inject;

/**
 * HelloAction.
 *
 * @author Rene Gielen
 */
public class HelloAction {

	@Inject ServiceBean serviceBean;

	public String execute() {
		System.out.println("HelloAction#execute called " + serviceBean.getFoo());
		return "success";
	}

}
