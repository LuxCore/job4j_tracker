package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Store;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class ExitAction implements UserAction {
	private final Output out;

	public ExitAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Завершить программу";
	}

	@Override
	public boolean execute(Input input, Store tracker) {
		out.println(System.lineSeparator() + "=== Завершение программы ===");
		return false;
	}
}
