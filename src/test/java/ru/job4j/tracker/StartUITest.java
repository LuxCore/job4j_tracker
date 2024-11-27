package ru.job4j.tracker;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.action.CreateAction;
import ru.job4j.tracker.action.DeleteAction;
import ru.job4j.tracker.action.ExitAction;
import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.action.ReplaceAction;
import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.action.UserAction;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.MockInput;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.io.StubOutput;

import static org.assertj.core.api.Assertions.assertThat;

class StartUITest {
	@Test
	public void whenCreateItem() {
		Input input = new MockInput(
				new String[]{"0", "Item name", "1"}
		);
		Output output = new StubOutput();
		Tracker tracker = new Tracker();
		UserAction[] actions = {
				new CreateAction(output),
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
		assertThat(tracker.findAll()[0].getName()).isEqualTo("Item name");
	}

	@Test
	void whenReplaceItem() {
		Tracker tracker = new Tracker();
		tracker.add(new Item("Replaced item"));
		String replacedName = "New item name";
		Input input = new MockInput(
				new String[] {"0", "1", replacedName, "1"}
		);
		Output output = new StubOutput();
		UserAction[] actions = {
				new ReplaceAction(output),
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
		assertThat(tracker.findById(2).getName()).isEqualTo(replacedName);
	}

	@Test
	void whenDeleteItem() {
		Tracker tracker = new Tracker();
		Item item = tracker.add(new Item("Deleted item"));
		Input input = new MockInput(
				new String[] {"0", "1", "1"}
		);
		Output output = new StubOutput();
		UserAction[] actions = {
				new DeleteAction(output),
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
		assertThat(tracker.findById(item.getId())).isNull();
	}

	@Test
	void whenExit() {
		Output output = new StubOutput();
		Input input = new MockInput(
				new String[] {"0"}
		);
		Tracker tracker = new Tracker();
		UserAction[] actions = {
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
		assertThat(output.toString()).isEqualTo(
				"Меню:" + System.lineSeparator()
						+ "0. Завершить программу." + System.lineSeparator()
						+ "Введите номер меню: " + System.lineSeparator()
						+ "=== Завершение программы ===" + System.lineSeparator()
		);
	}

	@Test
	void whenReplaceItemTestOutputIsSuccessfully() {
		Output output = new StubOutput();
		Tracker tracker = new Tracker();
		Item taskOne = tracker.add(new Item("test1"));
		String replaceName = "New Test Name";
		Input input = new MockInput(
				new String[] {"0", String.valueOf(taskOne.getId()), replaceName, "1"}
		);
		UserAction[] actions = new UserAction[]{
				new ReplaceAction(output),
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
		Item[] foundTasks = tracker.findByName(replaceName);
		String ln = System.lineSeparator();
		assertThat(output.toString()).isEqualTo(
				"Меню:" + ln
						+ "0. Изменить заявку." + ln
						+ "1. Завершить программу." + ln
						+ "Введите номер меню: " + ln
						+ "=== Замена заявки ===" + ln
						+ "Заявка заменена успешно:" + ln
						+ "\tСтарая заявка: " + taskOne + ln
						+ "\tНовая заявка: " + foundTasks[0] + ln + ln
						+ "Меню:" + ln
						+ "0. Изменить заявку." + ln
						+ "1. Завершить программу." + ln
						+ "Введите номер меню: " + ln
						+ "=== Завершение программы ===" + ln
		);
	}

	@Test
	void whenInvalidExit() {
		Output output = new StubOutput();
		Input input = new MockInput(
				new String[] {"999", "0"}
		);
		Tracker tracker = new Tracker();
		UserAction[] actions = new UserAction[]{
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
		String ln = System.lineSeparator();
		assertThat(output.toString()).isEqualTo(
				"Меню:" + ln
						+ "0. Завершить программу." + ln
						+ "Введите номер меню: "
						+ "Введите, пожалуйста, номер меню от 0 до 0." + ln + ln
						+ "Меню:" + ln
						+ "0. Завершить программу." + ln
						+ "Введите номер меню: " + ln
						+ "=== Завершение программы ===" + ln
		);
	}
}