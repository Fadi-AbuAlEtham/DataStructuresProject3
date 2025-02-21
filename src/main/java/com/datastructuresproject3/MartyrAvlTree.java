package com.datastructuresproject3;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MartyrAvlTree {
	private MartyrNode root;
	private int totalMartyr;
	private double totalAge;
	private int totalMales;
	private int totalFemales;
	private String originalName;
	private String originalDate;
	private byte originalAge;
	private String originalLocation;
	private String originalDistrict;
	private char originalGender;
	private String maxDistrict;
	private String maxLocation;
	private int maxMartyrs;

	public MartyrAvlTree() {
		root = null;
	}

	public MartyrNode getRoot() {
		return root;
	}

	public void setRoot(MartyrNode root) {
		this.root = root;
	}

	public int getTotalMartyr() {
		return totalMartyr;
	}

	public void setTotalMartyr(int totalMartyr) {
		this.totalMartyr = totalMartyr;
	}

	public double getTotalAge() {
		return totalAge;
	}

	public void setTotalAge(double martyrAvg) {
		this.totalAge = martyrAvg;
	}

	public int getTotalMales() {
		return totalMales;
	}

	public void setTotalMales(int totalMales) {
		this.totalMales = totalMales;
	}

	public int getTotalFemales() {
		return totalFemales;
	}

	public void setTotalFemales(int totalFemales) {
		this.totalFemales = totalFemales;
	}

	// Return the height of a node e
	private int height(MartyrNode e) {
		if (e == null)
			return -1;
		return e.getHeight();
	}

	public int getHeight() {
		return getHeight(root);
	}

	private int getHeight(MartyrNode node) {
		if (node == null) {
			return -1; // Height of an empty tree is -1
		} else {
			return node.getHeight();
		}
	}

	public int getSize() {
		return getSize(root);
	}

	private int getSize(MartyrNode node) {
		if (node == null) {
			return 0; // Size of an empty tree is 0
		} else {
			return getSize(node.getLeft()) + getSize(node.getRight()) + 1;
		}
	}

	// Rotate binary tree node with left child(single rotate to right)
	private MartyrNode rotateWithLeftChild(MartyrNode k2) {
		MartyrNode k1 = k2.getLeft();
		k2.setLeft(k1.getRight());
		k1.setRight(k2);
		k2.setHeight(Math.max(height(k2.getLeft()), height(k2.getRight())) + 1);
		k1.setHeight(Math.max(height(k1.getLeft()), k2.getHeight()) + 1);
		return k1;
	}

	// Rotate binary tree node with right child (single rotate to left)
	private MartyrNode rotateWithRightChild(MartyrNode k1) {
		MartyrNode k2 = k1.getRight();
		k1.setRight(k2.getLeft());
		k2.setLeft(k1);
		k1.setHeight(Math.max(height(k1.getLeft()), height(k1.getRight())) + 1);
		k2.setHeight(Math.max(height(k2.getRight()), k1.getHeight()) + 1);
		return k2;
	}

	/*
	 * Double rotate binary tree node: first left child with its right child; then
	 * node k3 with new left child
	 */
	private MartyrNode DoubleWithLeftChild(MartyrNode k3) {
		k3.setLeft(rotateWithRightChild(k3.getLeft()));
		return rotateWithLeftChild(k3);
	}

	/*
	 * Double rotate binary tree node: first right child with its left child; then
	 * node k1 with new right child
	 */
	private MartyrNode DoubleWithRightChild(MartyrNode k1) {
		k1.setRight(rotateWithLeftChild(k1.getRight()));
		return rotateWithRightChild(k1);
	}

	private MartyrNode insert(Martyr element, MartyrNode node) {
		if (node == null) {
			// Increment the totalMartyr counter and gender-specific counters
			totalMartyr++;
			if (element.getAge() >= 0)
				totalAge += element.getAge();
			else
				totalAge += 0;

			if (element.getGender() == 'M') {
				totalMales++;
			} else {
				totalFemales++;
			}
			return new MartyrNode(element);
		}

		int compareResult = element.compareTo(node.getElement());
		if (compareResult < 0) {
			node.setLeft(insert(element, node.getLeft()));
			if (height(node.getLeft()) - height(node.getRight()) == 2) {
				if (element.compareTo(node.getLeft().getElement()) < 0) {
					node = rotateWithLeftChild(node);
				} else {
					node = DoubleWithLeftChild(node);
				}
			}
		} else if (compareResult > 0) {
			node.setRight(insert(element, node.getRight()));
			if (height(node.getRight()) - height(node.getLeft()) == 2) {
				if (element.compareTo(node.getRight().getElement()) > 0) {
					node = rotateWithRightChild(node);
				} else {
					node = DoubleWithRightChild(node);
				}
			}
		}
		node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
		return node;
	}

	public boolean insert(Martyr element) {
		root = insert(element, root);
		if (root != null)
			return true;
		else
			return false;
	}

	private boolean contains(Martyr element, MartyrNode node) {
		if (node == null) {
			return false;
		}
		int compareResult = element.compareTo(node.getElement());
		if (compareResult < 0) {
			return contains(element, node.getLeft());
		} else if (compareResult > 0) {
			return contains(element, node.getRight());
		} else {
			return true;
		}
	}

	public boolean contains(Martyr element) {
		return contains(element, root);
	}

	private MartyrNode find(Martyr element, MartyrNode node) {
		if (node == null) {
			return null;
		}

		int compareResult = element.compareTo(node.getElement());
		if (compareResult < 0) {
			return find(element, node.getLeft());
		} else if (compareResult > 0) {
			return find(element, node.getRight());
		} else {
			// Check all attributes if comparison based on district and name matches
			if (element.compareTo(node.getElement()) == 0 && element.getName().equals(node.getElement().getName())
					&& element.getDate().equals(node.getElement().getDate())
					&& element.getAge() == node.getElement().getAge()
					&& element.getDistrict() == node.getElement().getDistrict()
					&& element.getLocation().equals(node.getElement().getLocation())
					&& element.getGender() == node.getElement().getGender()) {
				return node;
			} else {
				// Not a complete match, continue searching (may not be necessary)
				return null; // Uncomment this line if searching further after a partial match is not needed
			}
		}
	}

	public MartyrNode find(Martyr element) {
		return find(element, root);
	}

	private MartyrNode findMin(MartyrNode node) {
		if (node == null) {
			return null;
		} else if (node.getLeft() == null) {
			return node;
		}
		return findMin(node.getLeft());
	}

	public MartyrNode findMin() {
		return findMin(root);
	}

	private MartyrNode findMax(MartyrNode node) {
		if (node == null) {
			return null;
		} else if (node.getRight() == null) {
			return node;
		}
		return findMax(node.getRight());
	}

	public MartyrNode findMax() {
		return findMax(root);
	}

	private MartyrNode remove(Martyr element, MartyrNode node) {
		if (node == null) {
			return node; // Item not found; do nothing
		}
		int compareResult = element.compareTo(node.getElement());
		if (compareResult < 0) {
			node.setLeft(remove(element, node.getLeft()));
		} else if (compareResult > 0) {
			node.setRight(remove(element, node.getRight()));
		} else if (node.getLeft() != null && node.getRight() != null) { // Two children
			node.setElement(findMin(node.getRight()).getElement());
			node.setRight(remove((Martyr) node.getElement(), node.getRight()));
		} else {
			node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
		}

		if (node == null) {
			return node;
		}

		// Update height
		node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);

		// Re balance the tree
		if (height(node.getLeft()) - height(node.getRight()) == 2) {
			if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
				node = rotateWithLeftChild(node);
			} else {
				node = DoubleWithLeftChild(node);
			}
		} else if (height(node.getRight()) - height(node.getLeft()) == 2) {
			if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
				node = rotateWithRightChild(node);
			} else {
				node = DoubleWithRightChild(node);
			}
		}
		return node;
	}

	public void remove(Martyr element) {
		root = remove(element, root);
	}

	public boolean checkMartyr(Martyr martyr) {
		if (martyr == null) {
			return false; // Handle null martyr
		}
		return checkMartyr(martyr, root);
	}

	private boolean checkMartyr(Martyr martyr, MartyrNode node) {
		if (node == null) {
			return false; // Martyr not found
		}

		int compareResult = martyr.compareTo(node.getElement());
		if (compareResult < 0) {
			return checkMartyr(martyr, node.getLeft());
		} else if (compareResult > 0) {
			return checkMartyr(martyr, node.getRight());
		} else {
			// Check if all attributes match
			return martyr.getName().equals(node.getElement().getName())
					&& martyr.getDate().equals(node.getElement().getDate())
					&& martyr.getAge() == node.getElement().getAge()
					&& martyr.getLocation().equals(node.getElement().getLocation())
					&& martyr.getDistrict().equals(node.getElement().getDistrict())
					&& martyr.getGender() == node.getElement().getGender();
		}
	}

	public void inOrderTraversal(MartyrNode root, TableView<Martyr> tableView, ObservableList<Martyr> martyrList) {
		if (root != null) {
			// Traverse left subtree
			inOrderTraversal(root.getLeft(), tableView, martyrList);

			// Add current Martyr to the list
			martyrList.add((Martyr) root.getElement());

			// Traverse right subtree
			inOrderTraversal(root.getRight(), tableView, martyrList);
		}
	}

	public void showMartyrsInTable(MartyrNode root) {
		Stage stage = new Stage();
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

		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(ageColumn);
		tableView.getColumns().add(locationColumn);
		tableView.getColumns().add(districtColumn);
		tableView.getColumns().add(genderColumn);

		ObservableList<Martyr> martyrList = FXCollections.observableArrayList();
		inOrderTraversal(root, tableView, martyrList);

		tableView.setItems(martyrList);

		VBox vBoxDate = new VBox(20);
		vBoxDate.setAlignment(Pos.CENTER);
		vBoxDate.setPadding(new Insets(10, 10, 10, 10));

		Label lbl = new Label();
		lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		lbl.setPadding(new Insets(10, 10, 30, 10));
		lbl.setStyle(
				"-fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lbl.setText(martyrList.get(0).getDate());

		vBoxDate.getChildren().addAll(lbl, tableView);

		Scene scene = new Scene(vBoxDate, 1000, 800);
		stage.setScene(scene);
		stage.setTitle("Martyrs Table");
		stage.show();
	}

	public void updateMartyr() {
		Stage stage = new Stage();
		TableView<Martyr> tableView = new TableView<>();
		tableView.setMaxWidth(750);
		tableView.setPrefHeight(600);

		TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setPrefWidth(300);

		TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateColumn.setPrefWidth(120);

		TableColumn<Martyr, Byte> ageColumn = new TableColumn<>("Age");
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
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

		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(ageColumn);
		tableView.getColumns().add(locationColumn);
		tableView.getColumns().add(districtColumn);
		tableView.getColumns().add(genderColumn);

		ObservableList<Martyr> martyrList = FXCollections.observableArrayList();
		inOrderTraversal(root, tableView, martyrList);

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

		// Create TextFields for editing selected Martyr attributes
		TextField nameEditField = new TextField();
		nameEditField.setPromptText("Name");

		TextField dateEditField = new TextField();
		dateEditField.setPromptText("Date");

		TextField ageEditField = new TextField();
		ageEditField.setPromptText("Age");

		TextField locationEditField = new TextField();
		locationEditField.setPromptText("Location");

		TextField districtEditField = new TextField();
		districtEditField.setPromptText("District");

		TextField genderEditField = new TextField();
		genderEditField.setPromptText("Gender");

		// Buttons for update and delete actions
		Button updateButton = new Button("Update");
		Button deleteButton = new Button("Delete");

		HBox editBox = new HBox(10);
		editBox.setAlignment(Pos.CENTER);
		editBox.getChildren().addAll(new Label("Name:"), nameEditField, new Label("Date:"), dateEditField,
				new Label("Age:"), ageEditField, new Label("Location:"), locationEditField, new Label("District:"),
				districtEditField, new Label("Gender:"), genderEditField, updateButton, deleteButton);

		// Add listener for table row selection
		tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Store original values before editing
				originalName = newValue.getName();
				originalDate = newValue.getDate();
				originalAge = newValue.getAge();
				originalLocation = newValue.getLocation();
				originalDistrict = newValue.getDistrict();
				originalGender = newValue.getGender();

				// Set values in edit fields
				nameEditField.setText(newValue.getName());
				dateEditField.setText(newValue.getDate());
				ageEditField.setText(Byte.toString(newValue.getAge()));
				locationEditField.setText(newValue.getLocation());
				districtEditField.setText(newValue.getDistrict());
				genderEditField.setText(Character.toString(newValue.getGender()));
			}
		});

		// Update button action
		updateButton.setOnAction(e -> {
			Martyr selectedMartyr = tableView.getSelectionModel().getSelectedItem();
			if (selectedMartyr != null) {
				if (!nameEditField.getText().equals(originalName)) {
					selectedMartyr.setName(nameEditField.getText());
				}
				if (!dateEditField.getText().equals(originalDate)) {
					selectedMartyr.setDate(dateEditField.getText());
				}
				if (!ageEditField.getText().equals(Byte.toString(originalAge))) {
					selectedMartyr.setAge(Byte.parseByte(ageEditField.getText()));
				}
				if (!locationEditField.getText().equals(originalLocation)) {
					selectedMartyr.setLocation(locationEditField.getText());
				}
				if (!districtEditField.getText().equals(originalDistrict)) {
					selectedMartyr.setDistrict(districtEditField.getText());
				}
				if (!genderEditField.getText().equals(Character.toString(originalGender))) {
					selectedMartyr.setGender(genderEditField.getText().charAt(0));
				}
				tableView.refresh();
			}
		});

		// Delete button action
		deleteButton.setOnAction(e -> {
			Martyr selectedMartyr = tableView.getSelectionModel().getSelectedItem();
			if (selectedMartyr != null) {
				martyrList.remove(selectedMartyr);
				// Implement your tree removal method here
				remove(selectedMartyr);
				tableView.refresh();
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

		vBoxDate.getChildren().addAll(lbl, filterBox, tableView, editBox);

		Scene scene = new Scene(vBoxDate, 1000, 800);
		stage.setScene(scene);
		stage.setTitle("Martyrs Table");
		stage.show();
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
			if (dateFilter != null && !dateFilter.isEmpty()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date;
				try {
					date = LocalDate.parse(dateFilter, formatter);
				} catch (Exception e) {
					return false;
				}
				LocalDate martyrDate = LocalDate.parse(martyr.getDate(), formatter);
				if (!martyrDate.equals(date)) {
					return false;
				}
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

	// Method that finds the next location of a district using level-by-level
	// traversal (right to left)
	public MartyrNode getNextMartyr(MartyrNode root, MartyrNode currentLocation) {
		if (root == null || currentLocation == null)
			return null;

		LinkedListQueue queue = new LinkedListQueue();
		queue.enQueue(root);

		boolean found = false;
		MartyrNode prev = null;

		while (!queue.isEmpty()) {
			int size = queue.size();

			for (int i = 0; i < size; i++) {
				MartyrNode node = (MartyrNode) queue.deQueue();

				if (found)
					return node;

				if (node == currentLocation)
					found = true;

				if (node.getRight() != null)
					queue.enQueue(node.getRight());
				if (node.getLeft() != null)
					queue.enQueue(node.getLeft());

				prev = node;
			}
		}

		return null;
	}

	// Method that finds the previous location of a district using level-by-level
	// traversal (right to left)
	public MartyrNode getPrevDate(MartyrNode root, MartyrNode currentDate) {
		if (root == null || currentDate == null)
			return null;

		LinkedListQueue queue = new LinkedListQueue();
		queue.enQueue(root);

		MartyrNode prev = null;

		while (!queue.isEmpty()) {
			int size = queue.size();

			for (int i = 0; i < size; i++) {
				MartyrNode node = (MartyrNode) queue.deQueue();

				if (node == currentDate)
					return prev;

				if (node.getRight() != null)
					queue.enQueue(node.getRight());
				if (node.getLeft() != null)
					queue.enQueue(node.getLeft());

				prev = node;
			}
		}

		return null;
	}

	public void printTreeLevelByLevelRightToLeft(MartyrNode root, TextArea txtArea) {
		txtArea.clear();
		if (root == null)
			return;

		LinkedListQueue queue = new LinkedListQueue();
		LinkedListStack stack = new LinkedListStack();
		queue.enQueue(root);

		while (!queue.isEmpty()) {
			int levelSize = queue.size();

			for (int i = 0; i < levelSize; i++) {
				MartyrNode node = (MartyrNode) queue.deQueue();

				// Push the node onto the stack instead of printing it
				stack.push(node);

				// Enqueue the right child first if it exists
				if (node.getRight() != null)
					queue.enQueue(node.getRight());
				// Enqueue the left child
				if (node.getLeft() != null)
					queue.enQueue(node.getLeft());
			}
		}

		// Print the nodes from the stack
		while (!stack.isEmpty()) {
			Martyr martyr = ((MartyrNode) stack.pop()).getElement();
			txtArea.appendText("Name: " + martyr.getName() + "\n");
		}
	}

	// In-order traversal method for AVL tree
	public void inOrderTraversal(MartyrNode node, PrintWriter writer) {
		if (node != null) {
			inOrderTraversal(node.getLeft(), writer);
			writer.println(node.getElement().getName() + "," + node.getElement().getDate() + ","
					+ node.getElement().getAge() + "," + node.getElement().getLocation() + ","
					+ node.getElement().getDistrict() + "," + node.getElement().getGender());
			inOrderTraversal(node.getRight(), writer);
		}
	}

	// traverse and update martyr attributes
	public void updateMartyrDate(MartyrNode node, String date) {
		if (node != null) {
			// Traverse the left subtree
			updateMartyrDate(node.getLeft(), date);

			// Update the current node's martyr attributes
			Martyr martyr = node.getElement();
			martyr.setDate(date);

			// Traverse the right subtree
			updateMartyrDate(node.getRight(), date);
		}
	}

	// Method to get the district with the maximum number of martyrs
	public String getDistrictWithMaxMartyrs() {
		maxDistrict = null;
		maxMartyrs = 0;

		traverseAndCount(root);

		return maxDistrict;
	}

	// traverse the tree and count martyrs in each district
	private void traverseAndCount(MartyrNode node) {
		if (node != null) {
			traverseAndCount(node.getLeft());

			// Get the district of the current node
			String district = node.getElement().getDistrict();

			// Update maxDistrict and maxMartyrs if needed
			int districtMartyrs = countMartyrsInDistrict(root, district);
			if (districtMartyrs > maxMartyrs) {
				maxMartyrs = districtMartyrs;
				maxDistrict = district;
			}

			traverseAndCount(node.getRight());
		}
	}

	// Helper method to count martyrs in a specific district
	private int countMartyrsInDistrict(MartyrNode node, String district) {
		if (node == null)
			return 0;

		int count = 0;
		if (node.getElement().getDistrict().equals(district)) {
			count++;
		}
		count += countMartyrsInDistrict(node.getLeft(), district);
		count += countMartyrsInDistrict(node.getRight(), district);
		return count;
	}

	// Method to get the location with the maximum number of martyrs
	public String getLocationWithMaxMartyrs() {
		maxLocation = null;
		maxMartyrs = 0;

		traverseAndCountLoc(root);

		return maxLocation;
	}

	// traverse the tree and count martyrs in each location
	private void traverseAndCountLoc(MartyrNode node) {
		if (node != null) {
			traverseAndCountLoc(node.getLeft());

			// Get the location of the current node
			String location = node.getElement().getLocation();

			// Update maxDistrict and maxMartyrs if needed
			int locationMartyrs = countMartyrsInLocation(root, location);
			if (locationMartyrs > maxMartyrs) {
				maxMartyrs = locationMartyrs;
				maxLocation = location;
			}

			traverseAndCountLoc(node.getRight());
		}
	}

	// Helper method to count martyrs in a specific location
	private int countMartyrsInLocation(MartyrNode node, String location) {
		if (node == null)
			return 0;

		int count = 0;
		if (node.getElement().getLocation().equals(location)) {
			count++;
		}
		count += countMartyrsInLocation(node.getLeft(), location);
		count += countMartyrsInLocation(node.getRight(), location);
		return count;
	}

	// In-order traversal method for AVL tree
	public void inOrderTraversal(MartyrNode node, Heap heap) {
		if (node != null) {
			inOrderTraversal(node.getLeft(), heap);
			heap.insert(node);
			inOrderTraversal(node.getRight(), heap);
		}
	}

	// In-order traversal method for AVL tree
	public void insertMartyrsToNewTree(MartyrNode node) {
		if (node != null) {
			insertMartyrsToNewTree(node.getLeft());
			insert(node.getElement());
			insertMartyrsToNewTree(node.getRight());
		}
	}

	@Override
	public String toString() {
		return "MartyrAvlTree [root=" + root + "]";
	}

}
