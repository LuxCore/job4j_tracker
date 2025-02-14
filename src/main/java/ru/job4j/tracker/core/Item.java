package ru.job4j.tracker.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Item {
	private int id;
	private String name;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");
	private LocalDateTime created = LocalDateTime.now();

	public Item() {
	}

	public Item(String name) {
		this.name = name;
	}

	public Item(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Item(int id, String name, LocalDateTime created) {
		this.id = id;
		this.name = name;
		this.created = created;
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

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Item item = (Item) o;
		return id == item.id && Objects.equals(name, item.name) && Objects.equals(created, item.created);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, created);
	}

	@Override
	public String toString() {
		return "Item{id = %d, name = '%s', created = %s}"
				.formatted(id, name, created.format(DATE_FORMAT));
	}
}
