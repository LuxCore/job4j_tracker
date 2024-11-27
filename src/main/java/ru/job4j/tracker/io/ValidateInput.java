package ru.job4j.tracker.io;

public class ValidateInput implements Input {
	private final Input in;
	private final Output out;

	public ValidateInput(Input in, Output out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public String askString(String prompt) {
		return "";
	}

	@Override
	public int askInt(String prompt) {
		boolean invalid = true;
		int result = -1;
		do {
			try {
				result = this.in.askInt(prompt);
				invalid = false;
			} catch (NumberFormatException e) {
				out.println("Введите, пожалуйста, корректные данные");
			}
		} while (invalid);
		return result;
	}

	@Override
	public int askInt() {
		return askInt("");
	}
}
