package com.martin.model.exceptions;

@SuppressWarnings("serial")
public class MoneyException extends Exception {

	public MoneyException() {
		super("L'action que vous essayer de réaliser n'a pas aboutie : "
				+ "L'argent total serai passé sous la barre "
				+ "des 0 €, et le jeu serai...bloqué.");
	}

	public MoneyException(String string) {
		super(string);
	}

}
