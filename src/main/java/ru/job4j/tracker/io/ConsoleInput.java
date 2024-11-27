package ru.job4j.tracker.io;

import java.util.Scanner;

public class ConsoleInput implements Input {
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String askString(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	@Override
	public int askInt(String prompt) {
		return Integer.parseInt(askString(prompt));
	}

	@Override
	public int askInt() {
		return Integer.parseInt(scanner.nextLine());
	}
}
