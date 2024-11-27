package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class ReplaceAction implements UserAction {
	public static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public ReplaceAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Изменить заявку";
	}

	@Override
	public boolean execute(Input input, Tracker tracker) {
		out.println(LINE_SEPARATOR + "=== Замена заявки ===");
		int id = input.askInt("Введите id заявки, которую необходимо заменить: ");
		Item oldItem = tracker.findById(id);
		Item newItem = new Item(input.askString("Введите название новой заявки: "));
		boolean isItemReplaced = tracker.replace(id, newItem);
		if (isItemReplaced) {
			String output = "Заявка заменена успешно:" + LINE_SEPARATOR
					+ "\tСтарая заявка: " + oldItem + LINE_SEPARATOR
					+ "\tНовая заявка: " + newItem + LINE_SEPARATOR;
			out.println(output);
		} else {
			out.println("Ошибка: Заявка с идентификатором id = " + id + " не найдена." + LINE_SEPARATOR);
		}
		return true;
	}
}
