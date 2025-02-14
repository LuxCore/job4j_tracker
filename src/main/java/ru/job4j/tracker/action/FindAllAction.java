package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Store;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

import java.util.List;

public class FindAllAction implements UserAction {
	public static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public FindAllAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Показать все заявки";
	}

	@Override
	public boolean execute(Input input, Store tracker) {
		out.println(LINE_SEPARATOR + "=== Вывод всех заявок ===");
		List<Item> items = tracker.findAll();
		if (items.isEmpty()) {
			out.println("В хранилище нет заявок. Создайте хотя бы одну заявку." + LINE_SEPARATOR);
			return true;
		}
		for (Item item : items) {
			out.println(item);
		}
		out.println();
		return true;
	}
}
