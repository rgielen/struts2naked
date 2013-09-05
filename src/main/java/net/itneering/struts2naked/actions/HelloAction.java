package net.itneering.struts2naked.actions;

/**
 * HelloAction.
 *
 * @author Rene Gielen
 */
public class HelloAction {

	public String execute() {
		System.out.println("HelloAction#execute called");
		return "success";
	}

}
