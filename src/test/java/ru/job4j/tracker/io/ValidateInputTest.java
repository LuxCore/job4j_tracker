package ru.job4j.tracker.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateInputTest {

	@Test
	void whenInvalidInput() {
		Output output = new StubOutput();
		Input in = new MockInput(
				new String[] {"one", "1"}
		);
		ValidateInput input = new ValidateInput(in, output);
		int selected = input.askInt();
		assertThat(selected).isEqualTo(1);
	}

	@Test
	void whenValidInputWithOneInputParam() {
		Output output = new StubOutput();
		Input in = new MockInput(
				new String[] {"2"}
		);
		ValidateInput input = new ValidateInput(in, output);
		int selected = input.askInt();
		assertThat(selected).isEqualTo(2);
	}

	@Test
	void whenValidInputWithFewInputParams() {
		Output output = new StubOutput();
		Input in = new MockInput(
				new String[] {"2", "3", "4"}
		);
		ValidateInput input = new ValidateInput(in, output);
		int selected = input.askInt();
		assertThat(selected).isEqualTo(2);
		selected = input.askInt();
		assertThat(selected).isEqualTo(3);
		selected = input.askInt();
		assertThat(selected).isEqualTo(4);
	}

	@Test
	void whenValidInputWithNegativeInputParam() {
		Output output = new StubOutput();
		Input in = new MockInput(
				new String[] {"-2"}
		);
		ValidateInput input = new ValidateInput(in, output);
		int selected = input.askInt();
		assertThat(selected).isEqualTo(-2);
	}
}