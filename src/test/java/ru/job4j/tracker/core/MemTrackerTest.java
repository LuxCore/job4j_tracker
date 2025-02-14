package ru.job4j.tracker.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemTrackerTest {
	@Test
	public void whenAddNewItemThenTrackerHasSameItem() {
		Store tracker = new MemTracker();
		Item item = new Item();
		item.setName("test1");
		tracker.add(item);
		Item result = tracker.findById(item.getId());
		assertThat(result.getName()).isEqualTo(item.getName());
	}

	@Test
	public void whenTestFindById() {
		Store tracker = new MemTracker();
		Item bug = new Item("Bug");
		Item item = tracker.add(bug);
		Item result = tracker.findById(item.getId());
		assertThat(result.getName()).isEqualTo(item.getName());
	}

	@Test
	public void whenTestFindAll() {
		Store tracker = new MemTracker();
		Item first = new Item("First");
		Item second = new Item("Second");
		tracker.add(first);
		tracker.add(second);
		Item result = tracker.findAll().getFirst();
		assertThat(result.getName()).isEqualTo(first.getName());
	}

	@Test
	public void whenTestFindByNameCheckArrayLength() {
		Store tracker = new MemTracker();
		Item first = new Item("First");
		Item second = new Item("Second");
		tracker.add(first);
		tracker.add(second);
		tracker.add(new Item("First"));
		tracker.add(new Item("Second"));
		tracker.add(new Item("First"));
		List<Item> result = tracker.findByName(first.getName());
		assertThat(result.size()).isEqualTo(3);
	}

	@Test
	public void whenTestFindByNameCheckSecondItemName() {
		Store tracker = new MemTracker();
		Item first = new Item("First");
		Item second = new Item("Second");
		tracker.add(first);
		tracker.add(second);
		tracker.add(new Item("First"));
		tracker.add(new Item("Second"));
		tracker.add(new Item("First"));
		List<Item> result = tracker.findByName(second.getName());
		assertThat(result.get(1).getName()).isEqualTo(second.getName());
	}

	@Test
	public void whenReplaceItemIsSuccessful() {
		Store tracker = new MemTracker();
		Item item = new Item("Bug");
		tracker.add(item);
		int id = item.getId();
		Item updateItem = new Item("Bug with description");
		boolean result = tracker.replace(id, updateItem);
		assertThat(tracker.findById(updateItem.getId()).getName()).isEqualTo("Bug with description");
		assertThat(result).isTrue();
	}

	@Test
	public void whenReplaceItemIsNotSuccessful() {
		Store tracker = new MemTracker();
		Item item = new Item("Bug");
		tracker.add(item);
		Item updateItem = new Item("Bug with description");
		boolean result = tracker.replace(1000, updateItem);
		assertThat(tracker.findById(item.getId()).getName()).isEqualTo("Bug");
		assertThat(result).isFalse();
	}

	@Test
	public void whenDeleteItemIsSuccessful() {
		Store tracker = new MemTracker();
		Item item = new Item("Bug");
		tracker.add(item);
		int id = item.getId();
		tracker.delete(id);
		assertThat(tracker.findById(id)).isNull();
	}

	@Test
	public void whenDeleteItemIsNotSuccessful() {
		Store tracker = new MemTracker();
		Item item = new Item("Bug");
		tracker.add(item);
		tracker.delete(1000);
		assertThat(tracker.findById(item.getId()).getName()).isEqualTo("Bug");
	}
}
