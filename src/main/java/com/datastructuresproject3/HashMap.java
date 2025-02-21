package com.datastructuresproject3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;

public class HashMap {
	private HashEntry[] table;
	private int tableSize;
	private int currentSize;
	private ObservableList<HashEntry> data1;

	public HashMap(int tableSize) {
		this.table = new HashEntry[tableSize];
		this.tableSize = tableSize;
		currentSize = 0;

		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	public HashEntry[] getTable() {
		return table;
	}

	public void setTable(HashEntry[] table) {
		this.table = table;
	}

	public int getTableSize() {
		return tableSize;
	}

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public ObservableList<HashEntry> getData1() {
		return data1;
	}

	public void setData1(ObservableList<HashEntry> data1) {
		this.data1 = data1;
	}

	public int hOfK(String key) {
		long sum = 0;
		for (int i = 0; i < key.length(); i++) {
			int ki = key.charAt(i);
			sum += ki * Math.pow(32, i);
		}
		return (int) (sum % tableSize);
	}

	public int fOfK(String key) {
		for (int i = 0; i < tableSize; i++) {
			int index = (hOfK(key) + (i * i)) % tableSize;
			if (table[index] == null || table[index].getStatus() == 'E') {
				return index;
			}
		}
		return -1;
	}

	public boolean insert(MartyrDate martDate) {
		// Check for resize if load factor exceeds threshold
		if ((double) currentSize / tableSize >= 0.5) {
			resizeTable();
		}

		// Calculate hash index
		int hashIndex = hOfK(martDate.getDate());
		int probeDistance = 0;
		// Start index at initial hash
		int index = hashIndex;

		// Find an empty slot using quadratic probing
		while (table[index] != null && table[index].getStatus() == 'F') {
			index = (hashIndex + (probeDistance * probeDistance)) % tableSize;
			probeDistance++;
		}

		// Insert the MartyrDate object into the hash table
		table[index] = new HashEntry(martDate, 'F');
		table[index].setIndex(index);
		// Set Martyr tree reference
		table[index].setHead(martDate.getMartyrTree());
		currentSize++;

		return true; // Signal successful insertion
	}

	private void resizeTable() {
		int newSize = findNextPrime(2 * tableSize);

		HashEntry[] oldTable = table; // Preserve the old table
		table = new HashEntry[newSize]; // Initialize new table with new size
		tableSize = newSize;
		currentSize = 0; // Reset the current size, will be updated during reinsertions

		// Reinsert all existing elements into the new table
		for (HashEntry entry : oldTable) {
			if (entry != null && entry.getStatus() != 'E') {
				insert(entry.getKey());
			}
		}
	}

	private int findNextPrime(int n) {
		while (!isPrime(n)) {
			n++;
		}
		return n;
	}

	private boolean isPrime(int n) {
		if (n <= 1)
			return false;
		if (n <= 3)
			return true;

		if (n % 2 == 0 || n % 3 == 0)
			return false;

		for (int i = 5; i * i <= n; i += 6) {
			if (n % i == 0 || n % (i + 2) == 0)
				return false;
		}

		return true;
	}

	public boolean remove(String date) {
		if (date == null) {
			return false;
		}

		int hashIndex = hOfK(date);

		// Check the initial hash index first
		if (table[hashIndex] != null && table[hashIndex].getKey() != null
				&& table[hashIndex].getKey().getDate().equals(date) && table[hashIndex].getStatus() != 'D') {
			table[hashIndex].setStatus('D');
			table[hashIndex].setHead(null);
			currentSize--;

			HashEntry hashEntery = new HashEntry(hashIndex, table[hashIndex].getKey().getDate(),
					(char) table[hashIndex].getStatus());

			if (data1.contains(hashEntery))
				data1.remove(hashEntery);
			return true; // Element found at initial hash index
		}

		int index = hashIndex;
		int probeDistance = 0;

		// Quadratic probing to find the element
		while (probeDistance < tableSize) {
			if (table[index] != null && table[index].getKey() != null && table[index].getKey().getDate() != null
					&& table[index].getKey().getDate().equals(date) && table[index].getStatus() == 'F') {

				// Element found, mark as deleted
				table[index].setStatus('D');
				table[index].setHead(null);
				currentSize--;
				HashEntry hashEntery = new HashEntry(index, table[index].getKey().getDate(),
						(char) table[index].getStatus());

				if (data1.contains(hashEntery))
					data1.remove(hashEntery);

				return true;
			}
			// Move to the next index using quadratic probing
			index = (hashIndex + (probeDistance * probeDistance)) % tableSize;
			probeDistance++;
		}

		// Element not found
		return false;
	}

	public HashEntry findHashEntry(String key) {
		if (key == null) {
			return null; // Handle null key edge case to prevent null pointer exceptions.
		}

		int hashIndex = hOfK(key);

		// Check the initial hash index first
		if (table[hashIndex] != null && table[hashIndex].getKey().getDate().equals(key)
				&& table[hashIndex].getStatus() != 'D') {
			return table[hashIndex]; // Element found at initial hash index
		}

		// If not found at initial index, use quadratic probing
		int index = hashIndex;
		int probeDistance = 1;

		while (probeDistance < tableSize) {
			if (table[index] != null && table[index].getKey() != null && table[index].getKey().getDate().equals(key)
					&& table[index].getStatus() == 'F') {
				// Element found, return the HashEntry object
				return table[index];
			}
			// Move to the next index using quadratic probing
			index = (hashIndex + (probeDistance * probeDistance)) % tableSize;
			probeDistance++;
		}

		// Element not found, return null
		return null;
	}

	public boolean contains(String date) {
		if (date == null) {
			return false; // Handle null key edge case to prevent null pointer exceptions.
		}

		int hashIndex = hOfK(date);

		// Check the initial hash index first
		if (table[hashIndex] != null && table[hashIndex].getKey() != null
				&& table[hashIndex].getKey().getDate().equals(date) && table[hashIndex].getStatus() != 'D') {
			return true; // Element found at initial hash index
		}

		// If not found at initial index, use quadratic probing
		int index = hashIndex;
		int probeDistance = 1;

		while (probeDistance < tableSize) {
			if (table[index] != null && table[index].getKey() != null && table[index].getKey().getDate().equals(date)
					&& table[index].getStatus() == 'F') {
				// Element found, return the HashEntry object
				return true;
			}
			// Move to the next index using quadratic probing
			index = (hashIndex + (probeDistance * probeDistance)) % tableSize;
			probeDistance++;
		}

		// Element not found, return null
		return false;
	}

	public int find(String date) {
		if (date == null) {
			return -1; // Handle null key edge case to prevent null pointer exceptions.
		}

		int hashIndex = hOfK(date);

		// Check the initial hash index first
		if (table[hashIndex] != null && table[hashIndex].getKey() != null
				&& table[hashIndex].getKey().getDate().equals(date) && table[hashIndex].getStatus() != 'D') {
			return hashIndex; // Element found at initial hash index
		}

		// If not found at initial index, use quadratic probing
		int index = hashIndex;
		int probeDistance = 1;

		while (probeDistance < tableSize) {
			if (table[index] != null && table[index].getKey() != null && table[index].getKey().getDate().equals(date)
					&& table[index].getStatus() == 'F') {
				// Element found, return the HashEntry object
				return index;
			}
			// Move to the next index using quadratic probing
			index = (hashIndex + (probeDistance * probeDistance)) % tableSize;
			probeDistance++;
		}

		// Element not found, return null
		return -1;
	}

	public void printHash() {
		for (int i = 0; i < table.length; i++) {
			System.out.println("Index " + i + ": " + table[i]);
		}
	}

	public void printHashOnTable(BorderPane bdPane, Button bt) {
		TableView<HashEntry> tableView = new TableView<>();
		tableView.setMaxWidth(500);
		tableView.setPrefHeight(750);
		tableView.setStyle("* { -fx-font-family: \"Arial\"; -fx-font-size: 16px; }");

		TableColumn<HashEntry, Integer> indexColumn = new TableColumn<>("Index");
		indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
		indexColumn.setPrefWidth(100);

		TableColumn<HashEntry, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setPrefWidth(280);

		TableColumn<HashEntry, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setPrefWidth(100);
		statusColumn.setCellValueFactory(data -> {
			char status = (char) data.getValue().getStatus();
			String statusText;
			if (status == 'F') {
				statusText = "F";
			} else if (status == 'D') {
				statusText = "D";
			} else if (status == 'E') {
				statusText = "E";
			} else {
				statusText = "Unknown";
			}
			return new SimpleStringProperty(statusText);
		});

		tableView.getColumns().add(indexColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(statusColumn);

		ObservableList<HashEntry> data = FXCollections.observableArrayList();

		for (int i = 0; i < tableSize; i++) {
			if (table[i] != null) {
				// Get the date from the MartyrDate key
//				String date = table[i].getKey().getDate();
				data.add(table[i]);
			} else {
				data.add(new HashEntry(i, "null", 'E'));
			}
		}

		tableView.setItems(data);

		VBox vbox = new VBox(20);
		Label lbl = new Label("Complete Hash Table");
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lbl.setStyle(
				"-fx-font-size: 24px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");

		vbox.getChildren().addAll(lbl, tableView, bt);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setAlignment(Pos.CENTER);

		if (bdPane.getCenter() != vbox)
			bdPane.setCenter(vbox);
	}

	public void printHashOnTableWithStatusF(BorderPane bdPane, Button bt) {
		TableView<HashEntry> tableView = new TableView<>();
		tableView.setMaxWidth(500);
		tableView.setPrefHeight(750);
		tableView.setStyle("* { -fx-font-family: \"Arial\"; -fx-font-size: 16px; }");

		TableColumn<HashEntry, Integer> indexColumn = new TableColumn<>("Index");
		indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
		indexColumn.setPrefWidth(100);

		TableColumn<HashEntry, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setPrefWidth(280);

		TableColumn<HashEntry, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setPrefWidth(100);
		statusColumn.setCellValueFactory(data -> {
			char status = (char) data.getValue().getStatus();
			String statusText;
			if (status == 'F') {
				statusText = "F";
			} else if (status == 'D') {
				statusText = "D";
			} else if (status == 'E') {
				statusText = "E";
			} else {
				statusText = "Unknown";
			}
			return new SimpleStringProperty(statusText);
		});

		tableView.getColumns().add(indexColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(statusColumn);

		data1 = FXCollections.observableArrayList();

		for (int i = 0; i < tableSize; i++) {
			if (table[i] != null && table[i].getStatus() == 'F') {
				data1.add(table[i]);
			}
		}

		tableView.setItems(data1);

		VBox vbox = new VBox(20);
		Label lbl = new Label("Full Hash Table");
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lbl.setStyle(
				"-fx-font-size: 24px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");

		vbox.getChildren().addAll(lbl, tableView, bt);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setAlignment(Pos.CENTER);

		if (bdPane.getCenter() != vbox)
			bdPane.setCenter(vbox);
	}

	public boolean martyrExist(Martyr martyr) {
		if (martyr == null || martyr.getDate() == null) {
			return false; // Handle null martyr or date
		}

		String key = martyr.getDate(); // Use date as the key

		int hashIndex = hOfK(key);

		// Check the initial hash index first
		if (table[hashIndex] != null && table[hashIndex].getStatus() != 'D'
				&& table[hashIndex].getKey().getDate().equals(key) && table[hashIndex].getHead() != null) {
			// Check AVL tree at initial hash index if entry is not deleted
			return table[hashIndex].getHead().checkMartyr(martyr) != false;
		}

		int index = hashIndex;
		int probeDistance = 0;

		// Iterate through the table using quadratic probing
		while (probeDistance < tableSize) {
			if (table[index] != null && table[index].getStatus() != 'D') { // Skip deleted entries
				// Check if the martyr's date matches the key and if the AVL tree within the
				// HashEntry exists
				if (table[index].getKey().getDate().equals(key) && table[index].getHead() != null) {
					// Search the AVL tree associated with the key (date) for the martyr
					return table[index].getHead().checkMartyr(martyr) != false;
				}
			}
			// Move to the next index using quadratic probing
			index = (hashIndex + (probeDistance * probeDistance)) % tableSize;
			probeDistance++;
		}

		// Martyr not found
		return false;
	}

	public LinkedListStack sortDatesInStack(LinkedListStack stack) {
		stack = new LinkedListStack();
		for (int i = 0; i < tableSize; i++) {
			if (table[i] != null && table[i].getStatus() == 'F') {
				stack.push(table[i]);
			}
		}
		return stack;
	}

	public void martyrStatistics(HashEntry current, TextArea txtArea) {
		txtArea.clear();
		int totalMartyrs = current.getHead().getTotalMartyr();
		double averageAge = current.getHead().getTotalAge() / totalMartyrs;
		String formattedAverageAge = String.format("%.2f", averageAge);

		String formattedAverageAgeCheck;

		if (!formattedAverageAge.equals("0.00")) {
			formattedAverageAgeCheck = formattedAverageAge;
		} else
			formattedAverageAgeCheck = "The age is unknown!";

		txtArea.appendText("Total Martyrs: " + current.getHead().getTotalMartyr() + "\n" + "Average age of martyrs: "
				+ formattedAverageAgeCheck + "\n" + "Total Males: " + current.getHead().getTotalMales() + "\n"
				+ "Total Females: " + current.getHead().getTotalFemales() + "\n");
		txtArea.appendText("District that has the maximum martyrs: " + current.getHead().getDistrictWithMaxMartyrs()
				+ "\nLocation that has the maximum martyrs: " + current.getHead().getLocationWithMaxMartyrs());

	}

	public void updateDate(BorderPane bdPane, Button bt) {
		TableView<HashEntry> tableView = new TableView<>();
		tableView.setMaxWidth(500);
		tableView.setPrefHeight(500);
		tableView.setStyle("* { -fx-font-family: \"Arial\"; -fx-font-size: 16px; }");

		TableColumn<HashEntry, Integer> indexColumn = new TableColumn<>("Index");
		indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
		indexColumn.setPrefWidth(100);

		TableColumn<HashEntry, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setPrefWidth(280);

		TableColumn<HashEntry, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setPrefWidth(100);
		statusColumn.setCellValueFactory(data -> {
			char status = (char) data.getValue().getStatus();
			String statusText;
			if (status == 'F') {
				statusText = "F";
			} else if (status == 'D') {
				statusText = "D";
			} else if (status == 'E') {
				statusText = "E";
			} else {
				statusText = "Unknown";
			}
			return new SimpleStringProperty(statusText);
		});

		tableView.getColumns().addAll(indexColumn, dateColumn, statusColumn);

		data1 = FXCollections.observableArrayList();

		for (int i = 0; i < tableSize; i++) {
			if (table[i] != null && table[i].getStatus() == 'F') {
//				HashEntry entry = new HashEntry(i, table[i].getKey().getDate(), (char) table[i].getStatus());
//				entry.setHead(table[i].getHead());
//				data1.add(entry);
//				table[i].setIndex(i);
				data1.add(table[i]);
			}
		}

		// Wrap the ObservableList in a FilteredList
		FilteredList<HashEntry> filteredData = new FilteredList<>(data1, entry -> entry.getStatus() == 'F');

		tableView.setItems(filteredData);

		// Create a TextField for filtering
		Label label = new Label("Filter");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		TextField filterField = new TextField();
		filterField.setPromptText("Enter date to filter");
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(label, filterField);

		// Create TextField and Button for updating date
		Label dateLabel = new Label("Date");
		dateLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		DatePicker datePicker = new DatePicker();
		datePicker.setPromptText("Select date");
		Button updateButton = new Button("Update");
		Button removeButton = new Button("Remove");

		HBox updateBox = new HBox(10);
		updateBox.setAlignment(Pos.CENTER);
		updateBox.getChildren().addAll(dateLabel, datePicker, updateButton, removeButton);

		VBox vbox = new VBox(30);
		Label lbl = new Label("Full Hash Table");
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lbl.setStyle(
				"-fx-font-size: 24px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");

		vbox.getChildren().addAll(lbl, hbox, tableView, updateBox, bt);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setAlignment(Pos.CENTER);

		if (bdPane.getCenter() != vbox)
			bdPane.setCenter(vbox);

		// Listener for table row selection
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Convert the date string to LocalDate and set it in DatePicker
				String dateString = newSelection.getDate();
				LocalDate localDate = null;
				if (dateString != null && !dateString.isEmpty()) {
					localDate = parseDateString(dateString);
				}
				datePicker.setValue(localDate);
			}
		});

		// Update button action to change the date
		updateButton.setOnAction(e -> {
			HashEntry selectedEntry = tableView.getSelectionModel().getSelectedItem();
			if (selectedEntry != null) {
				LocalDate newDate = datePicker.getValue();
				if (newDate == null) {
					showAlert(Alert.AlertType.ERROR, "Date is empty!", "Date is empty!", "Please select a date");
					return;
				}

				LocalDate todayDate = LocalDate.now();

				if (newDate.isAfter(todayDate)) {
					showAlert(Alert.AlertType.ERROR, "Invalid Date", "Date is in the future!",
							"Please select a valid date.");
					return;
				}

				String date = null;
				if (newDate != null) {
					if (newDate.getMonthValue() < 10 && newDate.getDayOfMonth() >= 10) {
						date = newDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy"));
					} else if (newDate.getDayOfMonth() < 10 && newDate.getMonthValue() < 10) {
						date = newDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
					} else if (newDate.getDayOfMonth() < 10 && newDate.getMonthValue() >= 10) {
						date = newDate.format(DateTimeFormatter.ofPattern("MM/d/yyyy"));
					} else if (newDate.getDayOfMonth() >= 10 && newDate.getMonthValue() >= 10) {
						date = newDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
					} else
						date = "";
				}

				if (!date.equals(selectedEntry.getKey().getDate())) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Confirm Date Update");
					alert.setContentText("Are you sure you want to update the date to (MM/DD/YYYY): " + date + "?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {

						selectedEntry.getHead().updateMartyrDate(selectedEntry.getHead().getRoot(), date);

						MartyrAvlTree martyrTree = selectedEntry.getHead();
						HashEntry hashEntry = findHashEntry(date);

						// Date has changed, remove from hash table and reinsert
						if (remove(selectedEntry.getKey().getDate())) {
							data1.remove(selectedEntry);

							// If the new Date already exist in the hash table, we will just change the date
							// for the martyrs and connect the tree to the existing tree in that node
							if (hashEntry != null) {
								hashEntry.getHead().insertMartyrsToNewTree(martyrTree.getRoot());
								tableView.refresh();

							} else // If the new Date doesn't exist in the hashTable, we should insert it to the
									// hash then change the martyrs dates to suit the new date
							{
								MartyrDate newMartDate = new MartyrDate(date);
								if (insert(newMartDate)) {
									// Find the new entry from the hash table
									HashEntry newEntry = findHashEntry(newMartDate.getDate());
									if (newEntry != null) {
										// Calculate the correct insertion index
										int insertIndex = calculateIndexForEntry(newEntry);
										newEntry.getHead().insertMartyrsToNewTree(martyrTree.getRoot());
										// Insert the new entry at the calculated position
										data1.add(insertIndex, newEntry);
										tableView.refresh();
									}
								}
							}
						}
					}
				}
			}
		});

		removeButton.setOnAction(e -> {
			HashEntry selectedEntry = tableView.getSelectionModel().getSelectedItem();
			if (selectedEntry != null) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Confirm Removal");
				alert.setContentText("Are you sure you want to remove this entry?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					// Remove selected date from hash table
					if (remove(selectedEntry.getKey().getDate())) {
						data1.remove(selectedEntry);
						tableView.getItems().remove(selectedEntry); // Update TableView
					}
				}
			}
		});

		// Add a listener to the filterField to filter the table based on input
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(entry -> {
				// If filter text is empty, display all items with status 'F'
				if (newValue == null || newValue.isEmpty()) {
					return entry.getStatus() == 'F';
				}
				// Compare date of each entry with filter text
				String lowerCaseFilter = newValue.toLowerCase();
				return entry.getKey().getDate().toLowerCase().contains(lowerCaseFilter) && entry.getStatus() == 'F';
			});
		});
	}

	public void btLoadDisNavigation(HashEntry current) {
		current.getHead().showMartyrsInTable(current.getHead().getRoot());
	}

	// Method to insert the locations for the chosen district to the ComboBox
	public void insertLocationsToComboBox(LocationLinkedList districtList, String district, ComboBox<String> comboBox,
			Label lbl) {
		LocationNode currentDistrict = districtList.findNode(district);

		if (currentDistrict == null) {
			lbl.setVisible(true);
			lbl.setText("This district doesn't exist!");
			return;
		}

		// Clear the ComboBox before adding locations
		comboBox.getItems().clear();

		// Start with the root of the location list of the current district
		LocationNode currentLocation = currentDistrict.getHead().getFront();

		while (currentLocation != null) {
			comboBox.getItems().add(currentLocation.getElement());

			currentLocation = currentLocation.getNext();
		}
	}

	private int calculateIndexForEntry(HashEntry newEntry) {
		int newIndex = newEntry.getIndex();
		for (int i = 0; i < data1.size(); i++) {
			HashEntry entry = data1.get(i);
			int entryIndex = entry.getIndex();
			if (newIndex < entryIndex) {
				return i;
			}
		}
		return data1.size(); // If not found, add at the end
	}

	public VBox updateDeleteMartyr(LocationLinkedList districtList, ComboBox<String> disCmBox,
			ComboBox<String> locCmBox) {
		disCmBox.setVisible(true);
		locCmBox.setVisible(true);
		disCmBox.setDisable(false);
		locCmBox.setDisable(false);

		TableView<Martyr> tableView = new TableView<>();
		tableView.setMaxWidth(750);
		tableView.setPrefHeight(600);

		TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setPrefWidth(300);

		TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setPrefWidth(120);

		TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
		ageColumn.setCellValueFactory(cellData -> {
			Byte age = cellData.getValue().getAge();
			if (age == -1) {
				return new SimpleStringProperty(""); // Display empty cell
			} else {
				return new SimpleStringProperty(Byte.toString(age));
			}
		});
		ageColumn.setPrefWidth(80);

		TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		locationColumn.setPrefWidth(120);

		TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
		districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
		districtColumn.setPrefWidth(100);

		TableColumn<Martyr, Character> genderColumn = new TableColumn<>("Gender");
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		genderColumn.setPrefWidth(50);

		tableView.getColumns().addAll(nameColumn, dateColumn, ageColumn, locationColumn, districtColumn, genderColumn);

		ObservableList<Martyr> martyrList = FXCollections.observableArrayList();

		// Traverse through each HashEntry in the hash map
		for (HashEntry entry : table) {
			if (entry != null && entry.getHead() != null) {
				MartyrAvlTree tree = entry.getHead();
				tree.inOrderTraversal(tree.getRoot(), tableView, martyrList);
			}
		}

		FilteredList<Martyr> filteredData = new FilteredList<>(martyrList, p -> true);
		tableView.setItems(filteredData);

		// Create filter controls
		TextField nameFilterField = new TextField();
		nameFilterField.setPromptText("Filter by Name");

		TextField districtFilterField = new TextField();
		districtFilterField.setPromptText("Filter by District");

		TextField dateFilterField = new TextField();
		dateFilterField.setPromptText("Filter by Date");

		TextField locationFilterField = new TextField();
		locationFilterField.setPromptText("Filter by Location");

		HBox filterBox = new HBox(10);
		filterBox.setAlignment(Pos.CENTER);
		filterBox.getChildren().addAll(new Label("Name:"), nameFilterField, new Label("District:"), districtFilterField,
				new Label("Date:"), dateFilterField, new Label("Location:"), locationFilterField);

		// Add listeners for filters
		nameFilterField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters(filteredData,
				nameFilterField, districtFilterField, dateFilterField, locationFilterField));
		districtFilterField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters(filteredData,
				nameFilterField, districtFilterField, dateFilterField, locationFilterField));
		dateFilterField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters(filteredData,
				nameFilterField, districtFilterField, dateFilterField, locationFilterField));
		locationFilterField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters(filteredData,
				nameFilterField, districtFilterField, dateFilterField, locationFilterField));

		// ComboBoxes for district and location
		ComboBox<String> districtComboBox = disCmBox;
		districtComboBox.setPromptText("Select District");

		ComboBox<String> locationComboBox = locCmBox;
		locationComboBox.setPromptText("Select Location");
		locationComboBox.setDisable(true);

		// Add event listener to district combo box
		districtComboBox.setOnAction(e -> {
			String selectedDistrict = disCmBox.getValue();
			if (selectedDistrict != null && !selectedDistrict.trim().isEmpty() && !selectedDistrict.equals("-")) {
				// Populate location combo box
				insertLocationsToComboBox(districtList, selectedDistrict, locationComboBox, new Label());
				locationComboBox.setDisable(false);
			} else
				locationComboBox.setDisable(true);
		});

		// RadioButtons for gender
		ToggleGroup genderToggleGroup = new ToggleGroup();
		RadioButton maleRadioButton = new RadioButton("Male");
		maleRadioButton.setToggleGroup(genderToggleGroup);
		RadioButton femaleRadioButton = new RadioButton("Female");
		femaleRadioButton.setToggleGroup(genderToggleGroup);

		HBox genderBox = new HBox(10, maleRadioButton, femaleRadioButton);
		genderBox.setAlignment(Pos.CENTER);

		// TextFields for editing selected Martyr attributes
		TextField nameEditField = new TextField();
		nameEditField.setPromptText("Name");

		DatePicker dateEditField = new DatePicker();
		dateEditField.setPromptText("Date");

		TextField ageEditField = new TextField();
		ageEditField.setPromptText("Age");

		HBox editBox = new HBox(10);
		editBox.setAlignment(Pos.CENTER);
		editBox.getChildren().addAll(new Label("Name:"), nameEditField, new Label("Date:"), dateEditField,
				new Label("Age:"), ageEditField, new Label("Location:"), locationComboBox, new Label("District:"),
				districtComboBox, new Label("Gender:"), genderBox);

		// Add buttons for update and delete
		Button updateButton = new Button("Update");
		Button deleteButton = new Button("Delete");
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(updateButton, deleteButton);

		// Add selection listener to table
		tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				nameEditField.setText(newValue.getName());

				// Convert the date string to LocalDate and set it in DatePicker
				String dateString = newValue.getDate();
				LocalDate localDate = null;
				if (dateString != null && !dateString.isEmpty()) {
					localDate = parseDateString(dateString);
				}
				dateEditField.setValue(localDate);

				ageEditField.setText(newValue.getAge() == -1 ? "" : Byte.toString(newValue.getAge()));
				districtComboBox.setValue(newValue.getDistrict());
				insertLocationsToComboBox(districtList, newValue.getDistrict(), locationComboBox, new Label());
				locationComboBox.setValue(newValue.getLocation());
				if (newValue.getGender() == 'M') {
					maleRadioButton.setSelected(true);
				} else {
					femaleRadioButton.setSelected(true);
				}
			}
		});

		// Update button action
		updateButton.setOnAction(e -> {
			Martyr selectedMartyr = tableView.getSelectionModel().getSelectedItem();
			if (selectedMartyr != null) {
				if (confirmAction("Update", "Are you sure you want to update this martyr?")) {

					// Store the original date before updating
					String originalDate = selectedMartyr.getDate();

					MartyrAvlTree originalTree = findTreeByDate(originalDate);

					// Check if the date has changed
					LocalDate newDate = dateEditField.getValue();
					if (newDate == null) {
						showAlert(Alert.AlertType.ERROR, "Date is empty!", "Date is empty!", "Please select a date");
						return;
					}

					// Check if the date is a future date
					LocalDate todayDate = LocalDate.now();
					if (newDate.isAfter(todayDate)) {
						showAlert(Alert.AlertType.ERROR, "Invalid Date", "Date is in the future!",
								"Please select a valid date.");
						return;
					}

					String date = null;
					if (newDate != null) {
						if (newDate.getMonthValue() < 10 && newDate.getDayOfMonth() >= 10) {
							date = newDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy"));
						} else if (newDate.getDayOfMonth() < 10 && newDate.getMonthValue() < 10) {
							date = newDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
						} else if (newDate.getDayOfMonth() < 10 && newDate.getMonthValue() >= 10) {
							date = newDate.format(DateTimeFormatter.ofPattern("MM/d/yyyy"));
						} else if (newDate.getDayOfMonth() >= 10 && newDate.getMonthValue() >= 10) {
							date = newDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
						} else
							date = "";
					}

					if (!originalDate.equals(date)) {

						// Update name
						if (!nameEditField.getText().isEmpty()) {
							if (originalTree != null) {
								selectedMartyr.setName(nameEditField.getText());
							}
						} else {
							showAlert(Alert.AlertType.ERROR, "Name is empty!", "Name is empty!", "Please enter a name");
							return;
						}

						// Update Age
						if (ageEditField.getText().isEmpty()) {
							selectedMartyr.setAge((byte) -1); // Set age to -1 if the field is empty
						} else {
							try {
								byte age = Byte.parseByte(ageEditField.getText());
								if (age >= 0) {
									selectedMartyr.setAge(age);
								} else {
									showAlert(Alert.AlertType.ERROR, "Invalid Age", "Invalid Age",
											"Age must be greater or equal than zero.");
									return; // Prevent further processing
								}
							} catch (NumberFormatException ex) {
								showAlert(Alert.AlertType.ERROR, "Invalid Age", "Invalid Age", "Age must be a number.");
								return; // Prevent further processing
							}
						}

						// Update Location
						if (locationComboBox.getValue() != null && !locationComboBox.getValue().isEmpty()
								&& !locationComboBox.getValue().equals("-")) {
							selectedMartyr.setLocation(locationComboBox.getValue());
						} else {
							showAlert(Alert.AlertType.ERROR, "Location is empty!", "Location is empty!",
									"Please enter a location");
							return;
						}

						if (!districtComboBox.getValue().isEmpty()) {
							if (originalTree != null) {
								selectedMartyr.setDistrict(districtComboBox.getValue());
							}
						} else {
							showAlert(Alert.AlertType.ERROR, "District is empty!", "District is empty!",
									"Please enter a district");
							return;
						}

						// Update Gender
						RadioButton selectedRadioButton = (RadioButton) genderToggleGroup.getSelectedToggle();
						if (selectedRadioButton != null) {
							char gender = selectedRadioButton.getText().charAt(0);
							selectedMartyr.setGender(gender);
						}

						// Remove the martyr from the tree with the old date
						if (originalTree != null) {
							originalTree.remove(selectedMartyr);
						} else {
							showAlert(Alert.AlertType.ERROR, "Martyr Tree is empty!", "Martyr Tree is empty!",
									"Please check the tree");
							return;
						}

						// Insert Date
						MartyrDate newMartyrDate = new MartyrDate(date);
						HashEntry newDateHashEntry = findHashEntry(date);
						if (newDateHashEntry == null) {
							newDateHashEntry = new HashEntry(newMartyrDate);
							insert(newMartyrDate);
							newDateHashEntry.setHead(newMartyrDate.getMartyrTree());
						}

						MartyrAvlTree newMartyrTree = newDateHashEntry.getHead();
						MartyrNode martNode = newMartyrTree.find(selectedMartyr);
						if (martNode == null) {
							selectedMartyr.setDate(date);
							martNode = new MartyrNode(selectedMartyr);
							newMartyrTree.insert(selectedMartyr);
						}

						tableView.refresh();
					}
					// If the date hasn't changed just check for the other attributes
					else {
						// Update district and name
						if (districtComboBox.getValue() != null && !districtComboBox.getValue().equals("-")
								&& !districtComboBox.getValue().isEmpty() && !nameEditField.getText().isEmpty()) {
							if (originalTree != null) {
								originalTree.remove(selectedMartyr);
								selectedMartyr.setDistrict(districtComboBox.getValue());
								selectedMartyr.setName(nameEditField.getText());
								originalTree.insert(selectedMartyr);
							}
						} else {
							if (nameEditField.getText().isEmpty()) {
								showAlert(Alert.AlertType.ERROR, "Name is empty!", "Name is empty!",
										"Please enter a name");
								return;
							} else if (districtComboBox.getValue() == null || districtComboBox.getValue().isEmpty()
									|| districtComboBox.getValue().equals("-")) {
								showAlert(Alert.AlertType.ERROR, "District is empty!", "District is empty!",
										"Please enter a district");
								return;
							}
						}

						// Update Age
						if (ageEditField.getText().isEmpty()) {
							selectedMartyr.setAge((byte) -1); // Set age to -1 if the field is empty
						} else {
							try {
								byte age = Byte.parseByte(ageEditField.getText());
								if (age >= 0) {
									selectedMartyr.setAge(age);
								} else {
									showAlert(Alert.AlertType.ERROR, "Invalid Age", "Invalid Age",
											"Age must be greater or equal than zero.");
									return; // Prevent further processing
								}
							} catch (NumberFormatException ex) {
								showAlert(Alert.AlertType.ERROR, "Invalid Age", "Invalid Age", "Age must be a number.");
								return; // Prevent further processing
							}
						}

						// Update Location
						if (locationComboBox.getValue() != null && !locationComboBox.getValue().isEmpty()
								&& !locationComboBox.getValue().equals("-")) {
							selectedMartyr.setLocation(locationComboBox.getValue());
						} else {
							showAlert(Alert.AlertType.ERROR, "Location is empty!", "Location is empty!",
									"Please enter a location");
							return;
						}

						// Update Gender
						RadioButton selectedRadioButton = (RadioButton) genderToggleGroup.getSelectedToggle();
						if (selectedRadioButton != null) {
							char gender = selectedRadioButton.getText().charAt(0);
							selectedMartyr.setGender(gender);
						}
					}

					tableView.refresh();
				}
			}
		});

		// Delete button action
		deleteButton.setOnAction(e -> {
			Martyr selectedMartyr = tableView.getSelectionModel().getSelectedItem();
			if (selectedMartyr != null) {
				if (confirmAction("Delete", "Are you sure you want to delete this martyr?")) {
					martyrList.remove(selectedMartyr);
				}
			}
		});

		VBox vBoxDate = new VBox(20);
		vBoxDate.setAlignment(Pos.CENTER);
		vBoxDate.setPadding(new Insets(10, 10, 10, 10));

		Label lbl = new Label("Martyrs List");
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		lbl.setPadding(new Insets(10, 10, 30, 10));
		lbl.setStyle(
				"-fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");

		vBoxDate.getChildren().addAll(lbl, filterBox, tableView, editBox, buttonBox);

		return vBoxDate;
	}

	private void updateFilters(FilteredList<Martyr> filteredData, TextField nameFilterField,
			TextField districtFilterField, TextField dateFilterField, TextField locationFilterField) {
		filteredData.setPredicate(martyr -> {
			if (martyr == null) {
				return false;
			}

			// Filter by name
			String nameFilter = nameFilterField.getText();
			if (nameFilter != null && !nameFilter.isEmpty()
					&& !martyr.getName().toLowerCase().contains(nameFilter.toLowerCase())) {
				return false;
			}

			// Filter by district
			String districtFilter = districtFilterField.getText();
			if (districtFilter != null && !districtFilter.isEmpty()
					&& !martyr.getDistrict().toLowerCase().contains(districtFilter.toLowerCase())) {
				return false;
			}

			// Filter by date
			String dateFilter = dateFilterField.getText();
			if (dateFilter != null && !dateFilter.isEmpty()
					&& !martyr.getDate().toLowerCase().contains(dateFilter.toLowerCase())) {
				return false;
			}

			// Filter by location
			String locationFilter = locationFilterField.getText();
			if (locationFilter != null && !locationFilter.isEmpty()
					&& !martyr.getLocation().toLowerCase().contains(locationFilter.toLowerCase())) {
				return false;
			}

			return true;
		});
	}

	// Helper method to show a confirmation dialog
	private boolean confirmAction(String action, String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(action + " Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(message);
		ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == buttonTypeYes;
	}

	// Helper method to show an alert
	private void showAlert(Alert.AlertType alertType, String title, String message, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(message);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private MartyrAvlTree findTreeByDate(String date) {
		// Traverse through each HashEntry in the hash map
		for (HashEntry entry : table) {
			if (entry != null && entry.getStatus() == 'F' && entry.getHead() != null) {
				MartyrAvlTree tree = entry.getHead();
				MartyrDate martyrDate = entry.getKey();
				if (martyrDate != null && martyrDate.getDate().equals(date)) {
					return tree;
				}
			}
		}
		return null; // Tree not found for the given date
	}

	// Method to write martyrs to a file
	public boolean writeOnFile(String fileName) {
		try (PrintWriter writer = new PrintWriter(new File(fileName))) {
			writer.println("Name,Date,Age,Location,District,Gender");

			// Iterate through the hash table
			for (HashEntry entry : table) {
				if (entry != null) {
					// Traverse the AVL tree inside the hash entry and write to the file
					entry.getHead().inOrderTraversal(entry.getHead().getRoot(), writer);
				}
			}

			// File writing successful
			return true;
		} catch (IOException e) {
			// File writing failed
			System.out.println("Error writing to file: " + e.getMessage());
			return false;
		}
	}

	public void printMartyrsSortedByAge(BorderPane bdPane, Button bt) {
		// Create a TableView to display martyrs
		TableView<Martyr> tableView = new TableView<>();
		tableView.setMaxWidth(750);
		tableView.setPrefHeight(600);

		TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setPrefWidth(300);

		TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setPrefWidth(120);

		TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
		ageColumn.setCellValueFactory(cellData -> {
			Byte age = cellData.getValue().getAge();
			if (age == -1) {
				return new SimpleStringProperty(""); // Display empty cell
			} else {
				return new SimpleStringProperty(Byte.toString(age));
			}
		});
		ageColumn.setPrefWidth(80);

		TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		locationColumn.setPrefWidth(120);

		TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
		districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
		districtColumn.setPrefWidth(100);

		TableColumn<Martyr, Character> genderColumn = new TableColumn<>("Gender");
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		genderColumn.setPrefWidth(50);

		tableView.getColumns().addAll(nameColumn, dateColumn, ageColumn, locationColumn, districtColumn, genderColumn);

		// Determine the total number of martyrs to be processed
		int totalMartyrs = 0;
		for (int i = 0; i < tableSize; i++) {
			if (table[i] != null && table[i].getStatus() == 'F') {
				totalMartyrs += table[i].getHead().getSize();
			}
		}

		// Create a max-heap to sort martyrs by age
		Heap maxHeap = new Heap(totalMartyrs);

		// Insert martyrs into the max-heap
		for (int i = 0; i < tableSize; i++) {
			if (table[i] != null && table[i].getStatus() == 'F') {
				table[i].getHead().inOrderTraversal(table[i].getHead().getRoot(), maxHeap);
			}
		}

		// Perform heap-sort to sort martyrs by age
		maxHeap.heapSort();

		// Clear existing data in the TableView
		tableView.getItems().clear();

		// Add sorted martyrs to the TableView
		for (int i = 1; i <= maxHeap.getHeapSize(); i++) {
			Martyr martyr = maxHeap.getHeap()[i].getElement();
			tableView.getItems().add(martyr);
		}

		Label lbl = new Label("Martyrs Sorted By Age");
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		lbl.setPadding(new Insets(10, 10, 20, 10));
		lbl.setStyle(
				"-fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(lbl, tableView, bt);

		if (bdPane.getCenter() != vbox)
			bdPane.setCenter(vbox);
	}

	// Method to parse date string with all possible formats
	private LocalDate parseDateString(String dateString) {
		DateTimeFormatter[] formatters = { DateTimeFormatter.ofPattern("M/dd/yyyy"),
				DateTimeFormatter.ofPattern("M/d/yyyy"), DateTimeFormatter.ofPattern("MM/d/yyyy"),
				DateTimeFormatter.ofPattern("MM/dd/yyyy") };

		for (DateTimeFormatter formatter : formatters) {
			try {
				return LocalDate.parse(dateString, formatter);
			} catch (DateTimeParseException e) {
				// Continue to the next format
			}
		}

		// If all formats fail, return null or handle it appropriately
		return null;
	}

	@Override
	public String toString() {
		return "HashMap [table=" + Arrays.toString(table) + ", tableSize=" + tableSize + ", currentSize=" + currentSize
				+ ", data1=" + data1 + "]";
	}

	// This method implements the FNV-1a hash function for strings.
	public long fnvHash(String key) {

		// Define the FNV prime number used for multiplication.
		// This prime number (2^24) helps distribute hash values effectively.
		final long FNV_PRIME = 0x01000000; // 16777216 (decimal value)

		// Define the FNV initial value used as a seed for the hash calculation.
		// This value (0x811C9DC5) contributes to good avalanche effect and diffusion.
		final long FNV_32_INIT = 0x811C9DC5; // 3462688317 (decimal value)

		// Initialize the hash value with FNV_32_INIT.
		long hash = FNV_32_INIT;

		// Convert the string to a byte array for iterating over each byte.
		for (byte b : key.getBytes()) {

			// Multiply the current hash by the prime number using efficient bit shifting.
			hash *= FNV_PRIME;

			// Perform a bitwise XOR operation between the current hash and the current
			// byte.
			// This helps mix the information from each byte into the hash value.
			hash ^= b;
		}

		// Return the final hash value.
		return (int) (hash % tableSize);
	}

}
