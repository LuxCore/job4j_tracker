package ru.job4j.tracker.io;

public class StubOutput implements Output {
	private final StringBuilder out = new StringBuilder();

	@Override
	public void println(Object object) {
		print(object);
		out.append(System.lineSeparator());
	}

	@Override
	public void println() {
		out.append(System.lineSeparator());
	}

	@Override
	public void print(Object object) {
		if (object != null) {
			out.append(object);
		} else {
			out.append("null");
		}
	}

	@Override
	public String toString() {
		return out.toString();
	}
}
