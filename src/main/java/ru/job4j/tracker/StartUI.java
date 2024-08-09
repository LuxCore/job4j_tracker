package ru.job4j.tracker;

import ru.job4j.tracker.core.Item;
import ru.job4j.tracker.core.Tracker;

import java.util.Scanner;

public class StartUI {
	public void init(Scanner scanner, Tracker tracker) {
		boolean isRun = true;
		String ln = System.lineSeparator();
		while (isRun) {
			showMenu();
			int select = Integer.parseInt(scanner.nextLine());
			if (select == 0) {
				System.out.println(ln + "=== Создание новой заявки ===");
				System.out.print("Введите название заявки: ");
				String itemName = scanner.nextLine();
				Item item = new Item(itemName);
				tracker.add(item);
				System.out.println("Заявка успешно добавлена: " + item + ln);
			} else if (select == 1) {
				System.out.println(ln + "=== Вывод всех заявок ===");
				Item[] items = tracker.findAll();
				if (items.length == 0) {
					System.out.println("В хранилище нет заявок. Создайте хотя бы одну заявку." + ln);
					continue;
				}
				for (Item item : items) {
					System.out.println(item);
				}
				System.out.println();
			} else if (select == 2) {
				System.out.println(ln + "=== Замена заявки ===");
				System.out.print("Введите id заявки, которую необходимо заменить: ");
				int id = Integer.parseInt(scanner.nextLine());
				Item oldItem = tracker.findById(id);
				System.out.print("Введите название новой заявки: ");
				Item newItem = new Item(scanner.nextLine());
				boolean isItemReplaced = tracker.replace(id, newItem);
				if (isItemReplaced) {
					String output = "Заявка заменена успешно:" + ln
							+ "\tСтарая заявка: " + oldItem + ln
							+ "\tНовая заявка: " + newItem + ln;
					System.out.println(output);
				} else {
					System.out.println("Ошибка: Заявка с идентификатором id = " + id + " не найдена." + ln);
				}
			} else if (select == 3) {
				System.out.println(ln + "=== Удаление заявки ===");
				System.out.print("Введите id заявки для удаления: ");
				int id = Integer.parseInt(scanner.nextLine());
				Item oldItem = tracker.findById(id);
				boolean isItemDeleted = tracker.delete(id);
				if (isItemDeleted) {
					String output = "Заявка удалена успешно:" + ln
							+ "\tСтарая заявка: " + oldItem + ln;
					System.out.println(output);
				} else {
					System.out.println("Ошибка: Заявка с идентификатором id = " + id + " не найдена." + ln);
				}
			} else if (select == 4) {
				System.out.println(ln + "=== Вывод заявки по id ===");
				System.out.print("Введите id нужной заявки: ");
				int id = Integer.parseInt(scanner.nextLine());
				Item item = tracker.findById(id);
				if (item != null) {
					System.out.println(item + ln);
				} else {
					System.out.println("Ошибка: Заявка с идентификатором id = " + id + " не найдена." + ln);
				}
			} else if (select == 5) {
				System.out.println(ln + "=== Вывод заявок по имени ===");
				System.out.print("Введите имя заявки: ");
				String taskName = scanner.nextLine();
				Item[] items = tracker.findByName(taskName);
				if (items.length > 0) {
					StringBuilder sb = new StringBuilder("Заявки по имени: " + ln);
					for (Item item : items) {
						sb.append(item).append(ln);
					}
					System.out.println(sb.append(ln));
				} else {
					System.out.println("В хранилище нет заявок с именем " + taskName + ln);
				}
			} else if (select == 6) {
				isRun = false;
			} else {
				System.out.println("Введите, пожалуйста, номер меню от 0 до 6." + ln);
			}
		}
	}

	private void showMenu() {
		String[] menu = {
				"Добавить новую заявку",
				"Показать все заявки",
				"Изменить заявку",
				"Удалить заявку",
				"Показать заявку по id",
				"Показать заявки по имени",
				"Завершить программу"
		};
		StringBuilder sb = new StringBuilder("Меню:\n");
		for (int i = 0; i < menu.length; i++) {
			sb.append(i).append(". ").append(menu[i]).append('.').append("\n");
		}
		sb.append("Введите номер меню: ");
		System.out.print(sb);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Tracker tracker = new Tracker();
		new StartUI().init(scanner, tracker);
	}
}
