package ru.job4j.tracker.core;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {

	private Connection connection;

	public SqlTracker() {
		initConnection();
	}

	public SqlTracker(Connection connection) {
		this.connection = connection;
	}

	private void initConnection() {
		try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
			Properties config = new Properties();
			config.load(in);
			Class.forName(config.getProperty("db.driver"));
			connection = DriverManager.getConnection(
					config.getProperty("db.url"),
					config.getProperty("db.user"),
					config.getProperty("db.password"));
		} catch (IOException e) {
			throw new RuntimeException("Ошибка при чтении файла 'app.properties'.");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Не удалось найти драйвер базы данных.");
		} catch (SQLException e) {
			throw new RuntimeException("Не удалось подключиться к базе данных.");
		}
	}

	@Override
	public Item add(Item item) {
		try (PreparedStatement insQuery = connection.prepareStatement(
				"INSERT INTO tracker.items (name, created) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS)) {
			insQuery.setString(1, item.getName());
			insQuery.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
			insQuery.execute();
			try (ResultSet generatedKeys = insQuery.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					item.setId(generatedKeys.getInt("id"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при вставке задачи в базу данных: %s".formatted(e.getMessage()));
		}
		return item;
	}

	@Override
	public boolean replace(int id, Item item) {
		boolean result = false;
		try (PreparedStatement updQuery = connection.prepareStatement(
				"UPDATE tracker.items SET name = ?, created = ? WHERE id = ?")) {
			updQuery.setString(1, item.getName());
			updQuery.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
			updQuery.setInt(3, id);
			result = updQuery.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при обновлении задачи в базе данных: %s".formatted(e.getMessage()));
		}
		return result;
	}

	@Override
	public void delete(int id) {
		try (PreparedStatement delQuery = connection.prepareStatement(
				"DELETE FROM tracker.items WHERE id = ?"
		)) {
			delQuery.setInt(1, id);
			delQuery.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при удалении задачи в базе данных: %s".formatted(e.getMessage()));
		}
	}

	@Override
	public List<Item> findAll() {
		List<Item> items = new ArrayList<>();
		try (Statement selQuery = connection.createStatement();
		     ResultSet selectedItems = selQuery.executeQuery(
				     "SELECT id, name, created FROM tracker.items")) {
			copyQueryResultToList(selectedItems, items);
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при получении всех задач из базы данных: %s".formatted(e.getMessage()));
		}
		return items;
	}

	private static void copyQueryResultToList(ResultSet selectedItems, List<Item> items) throws SQLException {
		while (selectedItems.next()) {
			items.add(new Item(
					selectedItems.getInt("id"),
					selectedItems.getString("name"),
					selectedItems.getTimestamp("created").toLocalDateTime()
			));
		}
	}

	@Override
	public List<Item> findByName(String key) {
		List<Item> items = new ArrayList<>();
		try (PreparedStatement selQuery = connection.prepareStatement(
				"SELECT id, name, created FROM tracker.items WHERE name = ?"
		)) {
			selQuery.setString(1, key);
			try (ResultSet selectedItems = selQuery.executeQuery()) {
				copyQueryResultToList(selectedItems, items);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при получении всех задач из базы данных по имени '%s': %s"
					.formatted(key, e.getMessage()));
		}
		return items;
	}

	@Override
	public Item findById(int id) {
		Item item;
		try (PreparedStatement selQuery = connection.prepareStatement(
				"SELECT id, name, created FROM tracker.items WHERE id = ?"
		)) {
			selQuery.setInt(1, id);
			try (ResultSet selectedItems = selQuery.executeQuery()) {
				List<Item> items = new ArrayList<>(1);
				copyQueryResultToList(selectedItems, items);
				item = items.size() == 1 ? items.getFirst() : null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при получении задачи из базы данных по id = %d: %s"
					.formatted(id, e.getMessage()));
		}
		return item;
	}

	@Override
	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
