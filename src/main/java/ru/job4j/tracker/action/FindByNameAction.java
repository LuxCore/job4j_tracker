package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class FindByNameAction implements UserAction {
	public static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public FindByNameAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Показать заявки по имени";
	}

	@Override
	public boolean execute(Input input, Tracker tracker) {
		out.println(LINE_SEPARATOR + "=== Вывод заявок по имени ===");
		String taskName = input.askString("Введите имя заявки: ");
		Item[] items = tracker.findByName(taskName);
		if (items.length > 0) {
			StringBuilder sb = new StringBuilder("Заявки по имени: " + LINE_SEPARATOR);
			for (Item item : items) {
				sb.append(item).append(LINE_SEPARATOR);
			}
			out.println(sb.append(LINE_SEPARATOR));
		} else {
			out.println("В хранилище нет заявок с именем '" + taskName + "'" + LINE_SEPARATOR);
		}
		return true;
	}
}
