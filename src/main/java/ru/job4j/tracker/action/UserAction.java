package ru.job4j.tracker.action;

import ru.job4j.tracker.core.Tracker;
import ru.job4j.tracker.io.Input;

public interface UserAction {
	String name();

	boolean execute(Input input, Tracker tracker);
}
