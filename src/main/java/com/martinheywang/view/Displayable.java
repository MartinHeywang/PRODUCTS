package com.martinheywang.view;

public interface Displayable {
	public Displayer<?> getDisplayer();

	default Object getSubject() {
		return this;
	}
}
