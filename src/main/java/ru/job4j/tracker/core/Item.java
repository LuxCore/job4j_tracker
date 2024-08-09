package ru.job4j.tracker.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {
	private int id;
	private String name;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");
	private final LocalDateTime created = LocalDateTime.now();

	public Item() {
	}

	public Item(String name) {
		this.name = name;
	}

	public Item(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "Item{"
				+ "id=" + id
				+ ", name='" + name + '\''
				+ ", created=" + created.format(DATE_FORMAT) + '}';
	}
}
