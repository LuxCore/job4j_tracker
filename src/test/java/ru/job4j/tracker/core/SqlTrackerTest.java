package ru.job4j.tracker.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class SqlTrackerTest {
	private static Connection connection;
	private static SqlTracker tracker;

	@BeforeAll
	static void setup() throws SQLException {
		String h2Url = "jdbc:h2:mem:%s;MODE=PostgreSQL;INIT=RUNSCRIPT FROM '%s'"
				.formatted("tracker_dkitrish_db", "src/test/resources/beforeAll.sql");
		connection = DriverManager.getConnection(h2Url);
		tracker = new SqlTracker(connection);
	}

	@AfterAll
	static void tearDown() throws SQLException {
		tracker.close();
	}

	@BeforeEach
	void initSchema() throws SQLException {
		Statement statement = connection.createStatement();
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		statement.executeUpdate(joiner.add("CREATE TABLE tracker.items (")
				.add("\tid INTEGER GENERATED ALWAYS AS IDENTITY,")
				.add("\tname TEXT,")
				.add("\tcreated TIMESTAMP,")
				.add("\tCONSTRAINT items_pk PRIMARY KEY(id));").toString());
	}

	@AfterEach
	void clearSchema() throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE IF EXISTS tracker.items;");
	}

	@Test
	void add() {
		Item expectedItem1 = new Item(1, "itemAdd1");
		expectedItem1.setCreated(LocalDateTime.of(2025, 2, 13, 20, 55));
		Item expectedItem2 = new Item(2, "itemAdd2");
		expectedItem2.setCreated(LocalDateTime.of(2025, 2, 13, 21, 23));

		Item actualItem1 = new Item("itemAdd1");
		actualItem1.setCreated(LocalDateTime.of(2025, 2, 13, 20, 55));
		Item actualItem2 = new Item("itemAdd2");
		actualItem2.setCreated(LocalDateTime.of(2025, 2, 13, 21, 23));
		tracker.add(actualItem1);
		tracker.add(actualItem2);

		assertThat(actualItem1.getId()).isEqualTo(expectedItem1.getId());
		assertThat(actualItem2.getId()).isEqualTo(expectedItem2.getId());
	}

	@Test
	void replace() {
		Item expectedItem1 = new Item(1, "itemReplace1");
		expectedItem1.setCreated(LocalDateTime.of(2025, 2, 13, 20, 55));
		Item expectedItem2 = new Item(2, "itemReplace69");
		expectedItem2.setCreated(LocalDateTime.of(2025, 2, 13, 21, 15));

		Item actualItem1 = new Item("itemReplace1");
		actualItem1.setCreated(LocalDateTime.of(2025, 2, 13, 20, 55));
		Item actualItem2 = new Item("itemReplace2");
		actualItem2.setCreated(LocalDateTime.of(2025, 2, 13, 21, 23));
		tracker.add(actualItem1);
		tracker.add(actualItem2);

		Item replaceItem = new Item("itemReplace69");
		replaceItem.setCreated(LocalDateTime.of(2025, 2, 13, 21, 29));
		tracker.replace(2, replaceItem);

		actualItem2 = tracker.findById(2);
		assertThat(actualItem2.getName()).isEqualTo("itemReplace69");
		assertThat(actualItem2.getCreated()).isEqualTo(LocalDateTime.of(2025, 2, 13, 21, 29));
	}

	@Test
	void delete() {
		tracker.add(new Item("itemDelete1"));
		tracker.add(new Item("itemDelete2"));
		tracker.add(new Item("itemDelete3"));
		tracker.add(new Item("itemDelete4"));
		assertThat(tracker.findAll()).hasSize(4);
		tracker.delete(1);
		tracker.delete(4);
		assertThat(tracker.findAll()).hasSize(2);
		assertThat(tracker.findById(1)).isNull();
		assertThat(tracker.findById(2).getName()).isEqualTo("itemDelete2");
	}

	@Test
	void findAll() {
		LocalDateTime now = LocalDateTime.now().withNano(0);

		List<Item> expectedItems = List.of(
				new Item(1, "itemFindAll1", now),
				new Item(2, "itemFindAll22", now),
				new Item(3, "itemFindAll333", now),
				new Item(4, "itemFindAll4444", now));

		Item itemFindAll1 = new Item("itemFindAll1");
		Item itemFindAll2 = new Item("itemFindAll22");
		Item itemFindAll3 = new Item("itemFindAll333");
		Item itemFindAll4 = new Item("itemFindAll4444");
		itemFindAll1.setCreated(now);
		itemFindAll2.setCreated(now);
		itemFindAll3.setCreated(now);
		itemFindAll4.setCreated(now);
		tracker.add(itemFindAll1);
		tracker.add(itemFindAll2);
		tracker.add(itemFindAll3);
		tracker.add(itemFindAll4);
		List<Item> actualItems = tracker.findAll();
		assertIterableEquals(expectedItems, actualItems);
	}

	@Test
	void findByName() {
		LocalDateTime now = LocalDateTime.now().withNano(0);

		List<Item> expectedItems = List.of(
				new Item(2, "itemFindByName69", now.withSecond(23)),
				new Item(4, "itemFindByName69", now.withMinute(45))
		);

		Item itemFindByName1 = new Item("itemFindByName1");
		Item itemFindByName2 = new Item("itemFindByName69");
		Item itemFindByName3 = new Item("itemFindByName3");
		Item itemFindByName4 = new Item("itemFindByName69");
		itemFindByName1.setCreated(now);
		itemFindByName2.setCreated(now.withSecond(23));
		itemFindByName3.setCreated(now);
		itemFindByName4.setCreated(now.withMinute(45));
		tracker.add(itemFindByName1);
		tracker.add(itemFindByName2);
		tracker.add(itemFindByName3);
		tracker.add(itemFindByName4);
		List<Item> actualItems = tracker.findByName("itemFindByName69");
		assertIterableEquals(expectedItems, actualItems);
	}

	@Test
	void findById() {
		LocalDateTime now = LocalDateTime.now().withNano(0);

		Item expectedItem = new Item(2, "itemFindById69", now);

		Item itemFindById1 = new Item("itemFindById1");
		Item itemFindById2 = new Item("itemFindById69");
		itemFindById1.setCreated(now);
		itemFindById2.setCreated(now);
		tracker.add(itemFindById1);
		tracker.add(itemFindById2);
		Item actualItem = tracker.findById(2);
		assertThat(expectedItem).isEqualTo(actualItem);
	}
}
