package com.martinheywang.products.view;

public interface Displayable<E> {

	/**
	 * Returns a fuly functional displayer for
	 * 
	 * @return
	 */
	public Displayer<E> getDisplayer();
}
