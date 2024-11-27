package ru.job4j.tracker.io;

public class MockInput implements Input {
	private final String[] answers;
	private byte pointer;

	public MockInput(String[] answers) {
		this.answers = answers;
	}

	@Override
	public String askString(String prompt) {
		return answers[pointer++];
	}

	@Override
	public int askInt(String prompt) {
		return Integer.parseInt(askString(prompt));
	}

	@Override
	public int askInt() {
		return Integer.parseInt(answers[pointer++]);
	}
}
