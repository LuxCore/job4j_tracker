package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class FindByIdAction implements UserAction {
	public static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public FindByIdAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Показать заявку по id";
	}

	@Override
	public boolean execute(Input input, Tracker tracker) {
		out.println(LINE_SEPARATOR + "=== Вывод заявки по id ===");
		int id = input.askInt("Введите id нужной заявки: ");
		Item item = tracker.findById(id);
		if (item != null) {
			out.println(item + LINE_SEPARATOR);
		} else {
			out.println("Ошибка: Заявка с идентификатором id = " + id + " не найдена." + LINE_SEPARATOR);
		}
		return true;
	}
}
