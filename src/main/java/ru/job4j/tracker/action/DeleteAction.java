package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class DeleteAction implements UserAction {
	public static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public DeleteAction(Output out) {
		this.out = out;
	}

	@Override
	public String name() {
		return "Удалить заявку";
	}

	@Override
	public boolean execute(Input input, Tracker tracker) {
		out.println(LINE_SEPARATOR + "=== Удаление заявки ===");
		int id = input.askInt("Введите id заявки для удаления: ");
		Item oldItem = tracker.findById(id);
		boolean isItemDeleted = tracker.delete(id);
		if (isItemDeleted) {
			String output = "Заявка удалена успешно:" + LINE_SEPARATOR
					+ "\tСтарая заявка: " + oldItem + LINE_SEPARATOR;
			out.println(output);
		} else {
			out.println("Ошибка: Заявка с идентификатором id = " + id + " не найдена." + LINE_SEPARATOR);
		}
		return true;
	}
}
