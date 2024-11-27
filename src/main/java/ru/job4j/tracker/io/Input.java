package ru.job4j.tracker.io;

public interface Input {
	String askString(String prompt);

	int askInt(String prompt);

	int askInt();
}
