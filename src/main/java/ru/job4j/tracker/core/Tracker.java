package ru.job4j.tracker.core;

import java.util.Arrays;

public class Tracker {
	private final Item[] items = new Item[100];
	private int ids = 1;
	private int size = 0;

	public Item add(Item item) {
		item.setId(ids++);
		items[size++] = item;
		return item;
	}

	private int indexOf(int id) {
		int result = -1;
		for (int index = 0; index < size; index++) {
			if (items[index].getId() == id) {
				result = index;
				break;
			}
		}
		return result;
	}

	public Item findById(int id) {
		int index = indexOf(id);
		return index != -1 ? items[index] : null;
	}

	public Item[] findAll() {
		return Arrays.copyOf(items, size);
	}

	public Item[] findByName(String key) {
		byte resultsSize = 0;
		Item[] results = new Item[size];
		for (int index = 0; index < size; index++) {
			if (items[index].getName().equals(key)) {
				results[resultsSize++] = items[index];
			}
		}
		return Arrays.copyOf(results, resultsSize);
	}

	public boolean replace(int id, Item item) {
		boolean result = false;
		int itemIndexToReplace = indexOf(id);
		if (itemIndexToReplace != -1) {
			items[indexOf(id)] = item;
			item.setId(ids++);
			result = true;
		}
		return result;
	}

	public boolean delete(int id) {
		boolean result = false;
		int itemIndexToDelete = indexOf(id);
		if (itemIndexToDelete != -1) {
			if (itemIndexToDelete < size - 1) {
				System.arraycopy(items, itemIndexToDelete + 1, items, itemIndexToDelete, size - itemIndexToDelete - 1);
			}
			items[size-- - 1] = null;
			result = true;
		}
		return result;
	}
}
