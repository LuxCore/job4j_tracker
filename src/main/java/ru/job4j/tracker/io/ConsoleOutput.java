package ru.job4j.tracker.io;

public class ConsoleOutput implements Output {

	@Override
	public void println(Object object) {
		System.out.println(object);
	}

	@Override
	public void println() {
		System.out.println(System.lineSeparator());
	}

	@Override
	public void print(Object object) {
		System.out.print(object);
	}
}
