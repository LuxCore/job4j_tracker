package ru.job4j.tracker;

import ru.job4j.tracker.action.CreateAction;
import ru.job4j.tracker.action.DeleteAction;
import ru.job4j.tracker.action.ExitAction;
import ru.job4j.tracker.action.FindAllAction;
import ru.job4j.tracker.action.FindByIdAction;
import ru.job4j.tracker.action.FindByNameAction;
import ru.job4j.tracker.action.ReplaceAction;
import ru.job4j.tracker.action.UserAction;
import ru.job4j.tracker.core.MemTracker;
import ru.job4j.tracker.core.Store;
import ru.job4j.tracker.io.ConsoleInput;
import ru.job4j.tracker.io.ConsoleOutput;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.io.ValidateInput;

public class StartUI {
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private final Output out;

	public StartUI(Output output) {
		this.out = output;
	}

	public void init(Input input, Store tracker, UserAction[] actions) {
		boolean isRun = true;
		while (isRun) {
			showMenu(actions);
			int menuNum = input.askInt();
			if (menuNum < 0 || 6 < menuNum) {
				out.println("Введите, пожалуйста, номер меню от 0 до " + (actions.length - 1) + "." + LINE_SEPARATOR);
				continue;
			}
			UserAction action = actions[menuNum];
			isRun = action.execute(input, tracker);
		}
	}

	public void showMenu(UserAction[] actions) {
		StringBuilder sb = new StringBuilder("Меню:" + LINE_SEPARATOR);
		for (int i = 0; i < actions.length; i++) {
			sb.append(i).append(". ").append(actions[i].name()).append('.').append(LINE_SEPARATOR);
		}
		out.print(sb.append("Введите номер меню: "));
	}

	public static void main(String[] args) {
		Output output = new ConsoleOutput();
		Input input = new ValidateInput(new ConsoleInput(), output);
		Store tracker = new MemTracker();
		UserAction[] actions = {
				new CreateAction(output),
				new FindAllAction(output),
				new ReplaceAction(output),
				new DeleteAction(output),
				new FindByIdAction(output),
				new FindByNameAction(output),
				new ExitAction(output)
		};
		new StartUI(output).init(input, tracker, actions);
	}
}
