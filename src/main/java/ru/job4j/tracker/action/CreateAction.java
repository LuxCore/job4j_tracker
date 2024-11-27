package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class CreateAction implements UserAction {
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public CreateAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Добавить новую заявку";
	}

	@Override
	public boolean execute(Input input, Tracker tracker) {
		out.println(LINE_SEPARATOR + "=== Создание новой заявки ===");
		String itemName = input.askString("Введите название заявки: ");
		Item item = new Item(itemName);
		tracker.add(item);
		out.println("Заявка успешно добавлена: " + item + LINE_SEPARATOR);
		return true;
	}
}
