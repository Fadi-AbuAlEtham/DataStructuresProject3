package com.datastructuresproject3;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Proj3Driver extends Application {
	private LocationLinkedList districtList;
	private HashMap datesHash;
	private FileChooser fChooser;
	private MenuBar menuBar;
	private Menu menu1, menu2, datesMenu, martMenu;
	private MenuItem openItem, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11,
			dateInsert, dateUpdate, dateDelete, datePrint, datesNavigate, disWrFile, MartTreeNavigate, martInsert,
			martUpdate, martTreeHeightAndSize, martSearch;
	private Image image, image2;
	private ImageView imageView, imageView2;
	private BorderPane bdPane;
	private VBox vBoxMain, vBoxInstDis, vBoxChoUpdDis, vBoxUpdDis, vBoxNavDis, vBoxInstLoc, vBoxUpdLoc, vBoxInstMart,
			vBoxNavMartTree, vBoxSeaLoc, vBoxSeaMart, vBoxUpdMart, vBoxDelMart, vBoxdatesPrint;
	private HBox hBoxInstDis, hBoxChoUpdDis, hBoxUpdDis, hBoxUpdDis2, hBoxNavDis, hBoxNavDis1, hBoxInstDisLoc,
			hBoxInstLoc, hBoxInstMart, hBox1, hBoxNavLoc, hBoxSeaDisLoc, hBoxSeaLoc, hBoxDatesNav, hBoxNavLoc2,
			hBoxdatesPrint;
	private GridPane gdPane, gdPane1, gdPaneMartSea, gdPaneMartUpd, gdPaneMartDel;
	private Label lblMainInst, lblMainInfo, lblDateInst, lblDateInstInfo, lblDisUpdChoInfo, lblDisUpdOld, lblDisUpdNew,
			lblDisUpdInfo, lblDateNavInfo, lblLocDisInst, lblLocInst, lblLocInstInfo, lblLocDisUpd, lblOldLocUpd,
			lblNewLocUpd, lblLocUpdInfo, lblMartInstInfo, lblMartName, lblMartDate, lblMartAge, lblMartLoc, lblMartDis,
			lblMartGender, lblLocDisNavInfo, lblMartTreeNavInfo, lblLocDisSea, lblLocSea, lblLocSeaInfo, lblMartDisSea,
			lblMartLocSea, lblMartLocDateSea, lblMartSea, lblMartSeaInfo, lblMartDisUpd, lblMartLocUpd, lblOldMartUpd,
			lblMartDateUpd, lblMartUpdInfo, lblMartDisDel, lblMartLocDel, lblMartDel, lblMartDateDel, lblMartDelInfo;
	private TextField txtFieldDateInst, txtDisUpdOld, txtDisUpdNew, txtLocDisInst, txtLocInst, txtLocDisUpd,
			txtOldLocUpd, txtNewLocUpd, txtMartName, txtMartDate, txtMartAge, txtMartLoc, txtMartDis, txtLocDisSea,
			txtLocSea, txtMartDisSea, txtMartLocSea, txtMartSea, txtMartDisUpd, txtMartLocUpd, txtOldMartUpd,
			txtMartDateUpd, txtMartDisDel, txtMartLocDel, txtMartDel, txtMartDateDel;
	private TextArea txtAreaMain, txtAreaDateNavigation, txtAreaLocSearch, txtAreaMartSearch, txtAreaMartDelete,
			txtAreaMartTreeNav, txtAreaNavDates;
	private Button btDateInst, btDateInstMain, btDisChoUpd, btDisChoRen, btDisUpd, btDateUpdMain, btDateNavNext,
			btDateNavPrev, btDateNavLoad, btDateNavMain, btLocInst, btLocInstMain, btLocUpd, btLocUpdMain, btMartInst,
			btMartClear, btMartInstMain, btNavMartTreeNext, btNavMartTreePrev, btLocNavLoad, btLocNavMain, btLocSeaMain,
			btMartSea, btMartSeaMain, btMartUpd, btMartUpdMain, btMartHeightAndSize, btMartDelMain, btMartCheckUpd,
			btDisChoMain, btDatesNavPrev, btDatesNavNext, btMartSortMain, btdatesPrintMain, btdatesFullPrint,
			btdatesCompPrint, btdatesPrintReturn, btdatesPrintReturn2;
	private RadioButton rbMale, rbFemale, rbUnknown;
	private ToggleGroup group;
	private ComboBox<String> disCmBox, locCmBox, martDateCmBox;
	private ArrayList<String> disNames, locNames, datesNames;
	private Scene sceneMain;
	private DatePicker datePicker;
	private HashEntry currentDate, currentDate2;
	private LinkedListStack forwardStack;
	private TableView<Martyr> tableViewMartyrSearch, tableViewDatesNavigation;
	private File f;

	@Override
	public void start(Stage primaryStage) {
		datesHash = new HashMap(11);
		districtList = new LocationLinkedList();
		// Panes
		bdPane = new BorderPane();
		vBoxMain = new VBox(20);
		vBoxMain.setAlignment(Pos.CENTER);
		vBoxMain.setPadding(new Insets(10, 10, 10, 10));
		vBoxInstDis = new VBox(20);
		vBoxInstDis.setAlignment(Pos.CENTER);
		vBoxInstDis.setPadding(new Insets(10, 10, 10, 10));
		hBoxInstDis = new HBox(20);
		hBoxInstDis.setAlignment(Pos.CENTER);
		hBoxInstDis.setPadding(new Insets(10, 10, 10, 10));
		vBoxChoUpdDis = new VBox(20);
		vBoxChoUpdDis.setAlignment(Pos.CENTER);
		vBoxChoUpdDis.setPadding(new Insets(10, 10, 10, 10));
		hBoxChoUpdDis = new HBox(20);
		hBoxChoUpdDis.setAlignment(Pos.CENTER);
		hBoxChoUpdDis.setPadding(new Insets(10, 10, 10, 10));
		vBoxUpdDis = new VBox(20);
		vBoxUpdDis.setAlignment(Pos.CENTER);
		vBoxUpdDis.setPadding(new Insets(10, 10, 10, 10));
		hBoxUpdDis = new HBox(20);
		hBoxUpdDis.setAlignment(Pos.CENTER);
		hBoxUpdDis.setPadding(new Insets(10, 10, 10, 10));
		hBoxUpdDis2 = new HBox(20);
		hBoxUpdDis2.setAlignment(Pos.CENTER);
		hBoxUpdDis2.setPadding(new Insets(10, 10, 10, 10));
		vBoxNavDis = new VBox(20);
		vBoxNavDis.setAlignment(Pos.CENTER);
		vBoxNavDis.setPadding(new Insets(10, 10, 10, 10));
		hBoxNavDis = new HBox(20);
		hBoxNavDis.setAlignment(Pos.CENTER);
		hBoxNavDis.setPadding(new Insets(10, 10, 10, 10));
		hBoxNavDis1 = new HBox(30);
		hBoxNavDis1.setAlignment(Pos.CENTER);
		hBoxNavDis1.setPadding(new Insets(10, 10, 10, 10));
		vBoxInstLoc = new VBox(20);
		vBoxInstLoc.setAlignment(Pos.CENTER);
		vBoxInstLoc.setPadding(new Insets(10, 10, 10, 10));
		hBoxInstLoc = new HBox(20);
		hBoxInstLoc.setAlignment(Pos.CENTER);
		hBoxInstLoc.setPadding(new Insets(10, 10, 10, 10));
		hBoxInstDisLoc = new HBox(20);
		hBoxInstDisLoc.setAlignment(Pos.CENTER);
		hBoxInstDisLoc.setPadding(new Insets(10, 10, 10, 10));
		hBoxDatesNav = new HBox(20);
		hBoxDatesNav.setAlignment(Pos.CENTER);
		hBoxDatesNav.setPadding(new Insets(10, 10, 10, 10));
		vBoxUpdLoc = new VBox(20);
		vBoxUpdLoc.setAlignment(Pos.CENTER);
		vBoxUpdLoc.setPadding(new Insets(10, 10, 10, 10));
		gdPane = new GridPane();
		gdPane.setAlignment(Pos.CENTER);
		gdPane.setPadding(new Insets(10, 10, 10, 10));
		gdPane.setVgap(10);
		gdPane.setHgap(10);
		gdPane1 = new GridPane();
		gdPane1.setPadding(new Insets(20, 20, 20, 20));
		gdPane1.setAlignment(Pos.CENTER);
		gdPane1.setHgap(10);
		gdPane1.setVgap(10);
		gdPaneMartSea = new GridPane();
		gdPaneMartSea.setPadding(new Insets(20, 20, 20, 20));
		gdPaneMartSea.setAlignment(Pos.CENTER);
		gdPaneMartSea.setHgap(10);
		gdPaneMartSea.setVgap(10);
		vBoxInstMart = new VBox(20);
		vBoxInstMart.setAlignment(Pos.CENTER);
		vBoxInstMart.setPadding(new Insets(10, 10, 10, 10));
		hBoxInstMart = new HBox(20);
		hBoxInstMart.setAlignment(Pos.CENTER);
		hBoxInstMart.setPadding(new Insets(10, 10, 10, 10));
		hBox1 = new HBox(30);
		hBox1.setAlignment(Pos.CENTER);
		vBoxNavMartTree = new VBox(30);
		vBoxNavMartTree.setAlignment(Pos.CENTER);
		vBoxNavMartTree.setPadding(new Insets(10, 10, 10, 10));
		hBoxNavLoc = new HBox(20);
		hBoxNavLoc.setAlignment(Pos.CENTER);
		hBoxNavLoc.setPadding(new Insets(10, 10, 10, 10));
		hBoxNavLoc2 = new HBox(20);
		hBoxNavLoc2.setAlignment(Pos.CENTER);
		hBoxNavLoc2.setPadding(new Insets(10, 10, 10, 10));
		hBoxSeaDisLoc = new HBox(20);
		hBoxSeaDisLoc.setAlignment(Pos.CENTER);
		hBoxSeaDisLoc.setPadding(new Insets(10, 10, 10, 10));
		hBoxSeaLoc = new HBox(20);
		hBoxSeaLoc.setAlignment(Pos.CENTER);
		hBoxSeaLoc.setPadding(new Insets(10, 10, 10, 10));
		vBoxSeaLoc = new VBox(20);
		vBoxSeaLoc.setAlignment(Pos.CENTER);
		vBoxSeaLoc.setPadding(new Insets(10, 10, 10, 10));
		vBoxSeaMart = new VBox(20);
		vBoxSeaMart.setAlignment(Pos.CENTER);
		vBoxSeaMart.setPadding(new Insets(10, 10, 10, 10));
		gdPaneMartUpd = new GridPane();
		gdPaneMartUpd.setPadding(new Insets(20, 20, 20, 20));
		gdPaneMartUpd.setAlignment(Pos.CENTER);
		gdPaneMartUpd.setHgap(10);
		gdPaneMartUpd.setVgap(10);
		vBoxUpdMart = new VBox(20);
		vBoxUpdMart.setAlignment(Pos.CENTER);
		vBoxUpdMart.setPadding(new Insets(10, 10, 10, 10));
		gdPaneMartDel = new GridPane();
		gdPaneMartDel.setPadding(new Insets(20, 20, 20, 20));
		gdPaneMartDel.setAlignment(Pos.CENTER);
		gdPaneMartDel.setHgap(10);
		gdPaneMartDel.setVgap(10);
		vBoxDelMart = new VBox(20);
		vBoxDelMart.setAlignment(Pos.CENTER);
		vBoxDelMart.setPadding(new Insets(10, 10, 10, 10));
		hBoxdatesPrint = new HBox(40);
		hBoxdatesPrint.setAlignment(Pos.CENTER);
		hBoxdatesPrint.setPadding(new Insets(10, 10, 10, 10));
		vBoxdatesPrint = new VBox(100);
		vBoxdatesPrint.setAlignment(Pos.CENTER);
		vBoxdatesPrint.setPadding(new Insets(10, 10, 10, 10));

		// Image
		image = new Image(getClass().getResource("pngfind.com-manila-folder-png-1353429.png").toExternalForm());
		imageView = new ImageView(image);
		imageView.setFitWidth(16);
		imageView.setFitHeight(16);

		image2 = new Image(getClass().getResource("Save-Button-PNG-Free-Image.png").toExternalForm());
		imageView2 = new ImageView(image2);
		imageView2.setFitWidth(16);
		imageView2.setFitHeight(16);

		// Menu
		menuBar = new MenuBar();
		menu1 = new Menu("File");
		menu2 = new Menu("Color");
		datesMenu = new Menu("Date");
		martMenu = new Menu("Martyr");

		// MenuItem
		openItem = new MenuItem("Open");
		openItem.setGraphic(imageView);
		item1 = new MenuItem("Light Green");
		item1.setOnAction(e -> bdPane.setStyle("-fx-background-color: lightgreen"));
		item2 = new MenuItem("Baby Blue");
		item2.setOnAction(e -> bdPane.setStyle("-fx-background-color: lightblue"));
		item3 = new MenuItem("Biege");
		item3.setOnAction(e -> bdPane.setStyle("-fx-background-color: Cornsilk"));
		item4 = new MenuItem("Plum");
		item4.setOnAction(e -> bdPane.setStyle("-fx-background-color: PLUM"));
		item5 = new MenuItem("Salmon");
		item5.setOnAction(e -> bdPane.setStyle("-fx-background-color: salmon"));
		item6 = new MenuItem("Pink");
		item6.setOnAction(e -> bdPane.setStyle("-fx-background-color: lightpink"));
		item7 = new MenuItem("White");
		item7.setOnAction(e -> bdPane.setStyle("-fx-background-color: white"));
		item8 = new MenuItem("Aquamarine");
		item8.setOnAction(e -> bdPane.setStyle("-fx-background-color: AQUAMARINE"));
		item9 = new MenuItem("Deep Sky Blue");
		item9.setOnAction(e -> bdPane.setStyle("-fx-background-color: DEEPSKYBLUE"));
		item10 = new MenuItem("Medium Sea Green");
		item10.setOnAction(e -> bdPane.setStyle("-fx-background-color: MEDIUMSEAGREEN"));
		item11 = new MenuItem("Original color");
		item11.setOnAction(e -> bdPane.setStyle("-fx-background-color: whitesmoke"));
		dateInsert = new MenuItem("Insert a new date");
		dateUpdate = new MenuItem("Update/Delete a date");
		dateDelete = new MenuItem("Delete a date");
		datePrint = new MenuItem("Print dates");
		datesNavigate = new MenuItem("Navigate dates");
		disWrFile = new MenuItem("Save As");
		disWrFile.setGraphic(imageView2);
		MartTreeNavigate = new MenuItem("Print tree");
		martInsert = new MenuItem("Insert a new martyr");
		martUpdate = new MenuItem("Update/Delete a martyr");
		martTreeHeightAndSize = new MenuItem("Tree Height and Size");
		martSearch = new MenuItem("Print martyrs Sorted by Age");
		menu1.getItems().addAll(openItem, disWrFile);
		datesMenu.getItems().addAll(dateInsert, dateUpdate, datePrint, datesNavigate);
		martMenu.getItems().addAll(martInsert, martUpdate, martTreeHeightAndSize, MartTreeNavigate, martSearch);
		menu2.getItems().addAll(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11);
		menuBar.getMenus().addAll(menu1, datesMenu, martMenu, menu2);

		// Labels
		lblMainInst = new Label("     Martyr's Data Program\n\nPlease open a file to continue");
		lblMainInst.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
		lblMainInfo = new Label("Choose Operation from the menus");
		lblMainInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lblMainInfo.setVisible(false);
		lblDateInst = new Label("District Name: ");
		lblDateInst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblDateInstInfo = new Label();
		lblDateInstInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblDateInstInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lblDisUpdOld = new Label("Old District Name: ");
		lblDateInst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblDisUpdNew = new Label("New District Name: ");
		lblDateInst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblDisUpdInfo = new Label();
		lblDisUpdInfo.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblDisUpdInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lblDateNavInfo = new Label();
		lblDateNavInfo.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblDateNavInfo.setStyle(
				"-fx-font-size: 24px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lblLocDisInst = new Label("District Name: ");
		lblLocDisInst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblLocInstInfo = new Label();
		lblLocInstInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblLocInstInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lblLocInst = new Label("Location Name: ");
		lblLocInst.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblLocDisUpd = new Label("District Name: ");
		lblLocDisUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblOldLocUpd = new Label("Old Location Name: ");
		lblOldLocUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblNewLocUpd = new Label("New Location Name: ");
		lblNewLocUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblLocUpdInfo = new Label();
		lblLocUpdInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblLocUpdInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lblMartInstInfo = new Label();
		lblMartInstInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblMartInstInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
		lblMartName = new Label("Name: ");
		lblMartName.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartDate = new Label("Date: ");
		lblMartDate.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartAge = new Label("Age: ");
		lblMartAge.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartLoc = new Label("Location: ");
		lblMartLoc.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartDis = new Label("District: ");
		lblMartDis.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartGender = new Label("Gender: ");
		lblMartGender.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblLocDisNavInfo = new Label();
		lblLocDisNavInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblLocDisNavInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: green; -fx-border-width: 1;");
		lblMartTreeNavInfo = new Label();
		lblMartTreeNavInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblMartTreeNavInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-background-color: transparent;");
		lblLocDisSea = new Label("District: ");
		lblLocDisSea.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblLocSea = new Label("Location: ");
		lblLocSea.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblLocSeaInfo = new Label();
		lblLocSeaInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblLocSeaInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: green; -fx-border-width: 1;");
		lblMartDisSea = new Label("District: ");
		lblMartDisSea.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartLocSea = new Label("Location: ");
		lblMartLocSea.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartSea = new Label("Martyr Name: ");
		lblMartSea.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartLocDateSea = new Label("Date of Death: ");
		lblMartLocDateSea.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartSeaInfo = new Label();
		lblMartSeaInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblMartSeaInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: green; -fx-border-width: 1;");
		lblMartDisUpd = new Label("District: ");
		lblMartDisUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartLocUpd = new Label("Location: ");
		lblMartLocUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblOldMartUpd = new Label("Old Martyr Name: ");
		lblOldMartUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartDateUpd = new Label("Date of Death: ");
		lblMartDateUpd.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartUpdInfo = new Label();
		lblMartUpdInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblMartUpdInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: green; -fx-border-width: 1;");
		lblMartDisDel = new Label("District: ");
		lblMartDisDel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartLocDel = new Label("Location: ");
		lblMartLocDel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartDel = new Label("Martyr Name: ");
		lblMartDel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartDateDel = new Label("Date of Death: ");
		lblMartDateDel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		lblMartDelInfo = new Label();
		lblMartDelInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblMartDelInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 1;");
		lblDisUpdChoInfo = new Label("Choose whether you want to update or rename.");
		lblDisUpdChoInfo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		lblDisUpdChoInfo.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 1;");

		// TextFields
		txtFieldDateInst = new TextField();
		txtFieldDateInst.setPromptText("e.g.: Al-Quds");
		txtDisUpdOld = new TextField();
		txtDisUpdOld.setPromptText("Old District Name");
		txtDisUpdNew = new TextField();
		txtDisUpdNew.setPromptText("New District Name");
		txtLocDisInst = new TextField();
		txtLocDisInst.setPromptText("e.g.: Al-Quds");
		txtLocInst = new TextField();
		txtLocInst.setPromptText("e.g.: a-Ram");
		txtLocDisUpd = new TextField();
		txtLocDisUpd.setPromptText("e.g.: Al-Quds");
		txtOldLocUpd = new TextField();
		txtOldLocUpd.setPromptText("Old Location Name");
		txtNewLocUpd = new TextField();
		txtNewLocUpd.setPromptText("New Location Name");
		txtMartName = new TextField();
		txtMartName.setPromptText("e.g.: Elias");
		txtMartDate = new TextField();
		txtMartDate.setPromptText("(MM/DD/YYYY)");
		txtMartAge = new TextField();
		txtMartAge.setPromptText("e.g.: 25");
		txtMartLoc = new TextField();
		txtMartLoc.setPromptText("e.g.: a-Ram");
		txtMartDis = new TextField();
		txtMartDis.setPromptText("e.g.: Al-Quds");
		txtLocDisSea = new TextField();
		txtLocDisSea.setPromptText("e.g.: Al-Quds");
		txtLocSea = new TextField();
		txtLocSea.setPromptText("e.g.: a-Ram");
		txtMartDisSea = new TextField();
		txtMartDisSea.setPromptText("e.g.: Al-Quds");
		txtMartLocSea = new TextField();
		txtMartLocSea.setPromptText("e.g.: a-Ram");
		txtMartSea = new TextField();
		txtMartSea.setPromptText("e.g.: Elias");
		txtMartDisUpd = new TextField();
		txtMartDisUpd.setPromptText("e.g.: Al-Quds");
		txtMartLocUpd = new TextField();
		txtMartLocUpd.setPromptText("e.g.: a-Ram");
		txtOldMartUpd = new TextField();
		txtOldMartUpd.setPromptText("e.g.: Elias");
		txtMartDateUpd = new TextField();
		txtMartDateUpd.setPromptText("e.g.: (MM/DD/YYYY)");
		txtMartDisDel = new TextField();
		txtMartDisDel.setPromptText("e.g.: Al-Quds");
		txtMartLocDel = new TextField();
		txtMartLocDel.setPromptText("e.g.: a-Ram");
		txtMartDel = new TextField();
		txtMartDel.setPromptText("e.g.: Elias");
		txtMartDateDel = new TextField();
		txtMartDateDel.setPromptText("(MM/DD/YYYY)");

		// Buttons
		btDateInst = new Button("Insert");
		btDateInstMain = new Button("Return To Main");
		btDisUpd = new Button("Update");
		btDateUpdMain = new Button("Return To Main");
		btDateNavNext = new Button("Next >");
		btDateNavPrev = new Button("< Previous");
		btDateNavLoad = new Button("Load");
		btLocNavLoad = new Button("Load");
		btDatesNavPrev = new Button("< Previous");
		btDatesNavNext = new Button("Next >");
		btDateNavLoad.setPadding(new Insets(10, 10, 10, 10));
		btDateNavMain = new Button("Return To Main");
		btLocInst = new Button("Insert");
		btLocInstMain = new Button("Return To Main");
		btLocUpd = new Button("Update");
		btLocUpdMain = new Button("Return To Main");
		btMartInst = new Button("Insert");
		btMartInstMain = new Button("Return To Main");
		btMartClear = new Button("Clear");
		btNavMartTreeNext = new Button("Next Location >");
		btNavMartTreePrev = new Button("< Previous Location");
		btLocNavMain = new Button("Return To Main");
		btLocSeaMain = new Button("Return To Main");
		btMartSea = new Button("Search");
		btMartSeaMain = new Button("Return To Main");
		btMartUpd = new Button("Update");
		btMartCheckUpd = new Button("Check");
		btMartUpdMain = new Button("Return To Main");
		btMartHeightAndSize = new Button("Check");
		btMartDelMain = new Button("Return To Main");
		btDisChoUpd = new Button("Update");
		btDisChoRen = new Button("Rename");
		btDisChoMain = new Button("Return To Main");
		btMartSortMain = new Button("Return To Main");
		btdatesPrintMain = new Button("Return To Main");
		btdatesFullPrint = new Button("Full Hash");
		btdatesCompPrint = new Button("Complete Hash");
		btdatesPrintReturn = new Button("Return");
		btdatesPrintReturn2 = new Button("Return");

		// TextArea
		txtAreaMain = new TextArea();
		txtAreaMain.setPrefHeight(350);
		txtAreaMain.setMaxWidth(700);
		txtAreaMain.setVisible(false);
		txtAreaMain.setEditable(false);
		txtAreaMain.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 17));
		txtAreaDateNavigation = new TextArea();
		txtAreaDateNavigation.setPrefHeight(350);
		txtAreaDateNavigation.setMaxWidth(700);
		txtAreaDateNavigation.setEditable(false);
		txtAreaDateNavigation.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		txtAreaLocSearch = new TextArea();
		txtAreaLocSearch.setPrefHeight(350);
		txtAreaLocSearch.setMaxWidth(700);
		txtAreaLocSearch.setEditable(false);
		txtAreaLocSearch.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		txtAreaMartSearch = new TextArea();
		txtAreaMartSearch.setPrefHeight(350);
		txtAreaMartSearch.setMaxWidth(700);
		txtAreaMartSearch.setEditable(false);
		txtAreaMartSearch.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		txtAreaMartDelete = new TextArea();
		txtAreaMartDelete.setPrefHeight(350);
		txtAreaMartDelete.setMaxWidth(700);
		txtAreaMartDelete.setEditable(false);
		txtAreaMartDelete.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		txtAreaMartTreeNav = new TextArea();
		txtAreaMartTreeNav.setPrefHeight(350);
		txtAreaMartTreeNav.setMaxWidth(700);
		txtAreaMartTreeNav.setEditable(false);
		txtAreaMartTreeNav.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
//		txtAreaDateNav = new TextArea();
//		txtAreaDateNav.setPrefHeight(500);
//		txtAreaDateNav.setMaxWidth(1000);
//		txtAreaDateNav.setEditable(false);
//		txtAreaDateNav.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		txtAreaNavDates = new TextArea();
		txtAreaNavDates.setPrefHeight(300);
		txtAreaNavDates.setMaxWidth(650);
		txtAreaNavDates.setEditable(false);
		txtAreaNavDates.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		// FileChooser
		fChooser = new FileChooser();

		// RadioButtons
		group = new ToggleGroup();
		rbMale = new RadioButton("Male");
		rbFemale = new RadioButton("Female");
		rbMale.setToggleGroup(group);
		rbFemale.setToggleGroup(group);
		rbUnknown = new RadioButton("Unknown");
		rbUnknown.setToggleGroup(group);

		// Combo Box
		disCmBox = new ComboBox<String>();
		disCmBox.getItems().add("-");

		locCmBox = new ComboBox<String>();
		locCmBox.getItems().add("-");

		martDateCmBox = new ComboBox<String>();
		martDateCmBox.getItems().add("-");

		// ArrayLists
		disNames = new ArrayList<>();
		locNames = new ArrayList<>();
		datesNames = new ArrayList<>();

		// Date Picker
		datePicker = new DatePicker();
		datePicker.setEditable(false);

		// TableView
		tableViewMartyrSearch = new TableView<>();
		tableViewMartyrSearch.setMaxWidth(910);
		tableViewMartyrSearch.setPrefHeight(350);

		tableViewDatesNavigation = new TableView<>();
		tableViewDatesNavigation.setMaxWidth(900);
		tableViewDatesNavigation.setPrefHeight(350);

		// Event handler for the MenuItems
		openItem.setOnAction(e -> readFile(primaryStage));
		dateInsert.setOnAction(e -> insertDateScene(primaryStage));
		dateUpdate.setOnAction(e -> updateDateScene(primaryStage));
		dateDelete.setOnAction(e -> deleteDateScene(primaryStage));
		datePrint.setOnAction(e -> printDateScene(primaryStage));
		datesNavigate.setOnAction(e -> navigateDateScene(primaryStage));
		disWrFile.setOnAction(e -> writeFileDatesScene(primaryStage));
		MartTreeNavigate.setOnAction(e -> navigateMartTreeScene(primaryStage));
		martInsert.setOnAction(e -> insertMartScene(primaryStage));
		martUpdate.setOnAction(e -> updateMartScene(primaryStage));
		martTreeHeightAndSize.setOnAction(e -> MartTreeHeightAndSizeScene(primaryStage));
		martSearch.setOnAction(e -> printMartSortedScene(primaryStage));

		// Main scene
		vBoxMain.getChildren().addAll(lblMainInst, txtAreaMain, lblMainInfo);
		bdPane.setTop(menuBar);
		bdPane.setCenter(vBoxMain);
		// Adding the panes on the scenes
		sceneMain = new Scene(bdPane, 1420, 800);
		primaryStage.setTitle("Main Screen Window");
		primaryStage.setScene(sceneMain);
		primaryStage.show();

		// InsertDateScene and DeleteDateScene and NumberOfMartyrsByDateScene
		hBoxInstDis.getChildren().addAll(lblDateInst, datePicker);
		vBoxInstDis.getChildren().addAll(hBoxInstDis, lblDateInstInfo, btDateInst, btDateInstMain);

		// ChooseUpdDateScene
		hBoxChoUpdDis.getChildren().addAll(btDisChoUpd, btDisChoRen);
		vBoxChoUpdDis.getChildren().addAll(lblDisUpdChoInfo, hBoxChoUpdDis, btDisChoMain);

		// UpdateDisScene
		hBoxUpdDis.getChildren().addAll(lblDisUpdOld, disCmBox);
		hBoxUpdDis2.getChildren().addAll(lblDisUpdNew, txtDisUpdNew);
		vBoxUpdDis.getChildren().addAll(hBoxUpdDis, hBoxUpdDis2, lblDisUpdInfo, btDisUpd, btDateUpdMain);

		// Dates Navigation Scene
		hBoxNavDis1.getChildren().addAll(lblDateNavInfo, btDateNavLoad);
		hBoxNavDis.getChildren().addAll(btDateNavPrev, btDateNavNext);
		vBoxNavDis.getChildren().addAll(hBoxNavDis1, txtAreaNavDates, hBoxNavDis, btDateNavMain);

		// LocationInstScene
		hBoxInstDisLoc.getChildren().addAll(lblLocDisInst);
		hBoxInstLoc.getChildren().addAll(lblLocInst, txtLocInst);
		vBoxInstLoc.getChildren().addAll(hBoxInstDisLoc, hBoxInstLoc, lblLocInstInfo, btLocInst, btLocInstMain);

		// UpdateLocScene
		gdPane.add(lblLocDisUpd, 0, 0);
		gdPane.add(disCmBox, 1, 0);
		gdPane.add(lblOldLocUpd, 0, 1);
		gdPane.add(locCmBox, 1, 1);
		gdPane.add(lblNewLocUpd, 0, 2);
		gdPane.add(txtNewLocUpd, 1, 2);
		vBoxUpdLoc.getChildren().addAll(gdPane, lblLocUpdInfo, btLocUpd, btLocUpdMain);

		// NavigateLocScene
		hBoxNavLoc.getChildren().addAll(btNavMartTreePrev, btNavMartTreeNext);
		vBoxNavMartTree.getChildren().addAll(lblMartTreeNavInfo, hBoxNavLoc, txtAreaMartTreeNav, btLocNavMain);

		// NavigationDatesScene
		hBoxSeaDisLoc.getChildren().addAll(lblLocDisSea, disCmBox);
		hBoxSeaLoc.getChildren().addAll(lblLocSea, locCmBox);
		hBoxDatesNav.getChildren().addAll(btDatesNavPrev, btDatesNavNext);
		vBoxSeaLoc.getChildren().addAll(hBoxSeaDisLoc, hBoxSeaLoc, lblLocSeaInfo, hBoxDatesNav, txtAreaLocSearch,
				tableViewDatesNavigation, btLocSeaMain);

		// Print dates Scene
		hBoxdatesPrint.getChildren().addAll(btdatesCompPrint, btdatesFullPrint);
		vBoxdatesPrint.getChildren().addAll(hBoxdatesPrint, btdatesPrintMain);

		// InsertMartScene
		hBoxInstMart.getChildren().addAll(btMartInst, btMartClear);
		hBox1.getChildren().addAll(rbMale, rbFemale, rbUnknown);
		gdPane1.add(lblMartName, 0, 0);
		gdPane1.add(txtMartName, 1, 0);
		gdPane1.add(lblMartDate, 0, 1);
		gdPane1.add(datePicker, 1, 1);
		gdPane1.add(lblMartAge, 0, 2);
		gdPane1.add(txtMartAge, 1, 2);
		gdPane1.add(lblMartDis, 0, 3);
		gdPane1.add(disCmBox, 1, 3);
		gdPane1.add(lblMartLoc, 0, 4);
		gdPane1.add(locCmBox, 1, 4);
		gdPane1.add(lblMartGender, 0, 5);
		gdPane1.add(hBox1, 1, 5);
		vBoxInstMart.getChildren().addAll(gdPane1, lblMartInstInfo, hBoxInstMart, btMartInstMain);

		// UpdateMartScene
		gdPaneMartUpd.add(lblMartDisUpd, 0, 0);
		gdPaneMartUpd.add(disCmBox, 1, 0);
		gdPaneMartUpd.add(lblMartLocUpd, 0, 1);
		gdPaneMartUpd.add(locCmBox, 1, 1);
		gdPaneMartUpd.add(lblMartDateUpd, 0, 2);
		gdPaneMartUpd.add(datePicker, 1, 2);
		gdPaneMartUpd.add(lblOldMartUpd, 0, 3);
		gdPaneMartUpd.add(txtOldMartUpd, 1, 3);
		gdPaneMartUpd.add(btMartUpd, 3, 3);
		gdPaneMartUpd.add(btMartCheckUpd, 2, 3);
		vBoxUpdMart.getChildren().addAll(gdPaneMartUpd, lblMartUpdInfo, btMartUpdMain);

		// DeleteMartScene
		gdPaneMartDel.add(lblMartDateDel, 0, 0);
		gdPaneMartDel.add(datePicker, 1, 0);
		gdPaneMartDel.add(btMartHeightAndSize, 1, 2);
		vBoxDelMart.getChildren().addAll(gdPaneMartDel, lblMartDelInfo, txtAreaMartDelete, btMartDelMain);

		// SearchMartScene
//		gdPaneMartSea.add(lblMartDisSea, 0, 0);
//		gdPaneMartSea.add(disCmBox, 1, 0);
//		gdPaneMartSea.add(lblMartLocSea, 0, 1);
//		gdPaneMartSea.add(txtMartLocSea, 1, 1);
//		gdPaneMartSea.add(lblMartLocDateSea, 0, 2);
//		gdPaneMartSea.add(datePicker, 1, 2);
		gdPaneMartSea.add(lblMartSea, 0, 0);
		gdPaneMartSea.add(txtMartSea, 1, 0);
		gdPaneMartSea.add(btMartSea, 2, 0);
		vBoxSeaMart.getChildren().addAll(gdPaneMartSea, lblMartSeaInfo, tableViewMartyrSearch, btMartSeaMain);
	}

	// Method to read from a file
	private void readFile(Stage stage) {
		f = fChooser.showOpenDialog(stage);
		txtAreaMain.setText("");
		// Checks if the file was null
		if (f != null) {
			// Informing the user of the file name that was chosen and any error while
			// reading
			lblMainInst.setText("You opened this file: " + f.getName() + "\nReading from file warnings:");
			txtAreaMain.setVisible(true);
			lblMainInfo.setVisible(true);

			// try-catch block to handle any possible exception that might occurs
			try (Scanner read = new Scanner(f)) {
				// Skip the first line
				read.nextLine();
				// Keep reading from the file while it has content
				while (read.hasNextLine()) {
					String s = read.nextLine();
					// Split the line by ","
					String[] line = s.split(",");
					try {
						// If the format of the line was wrong
						if (line.length != 6)
							throw new IndexOutOfBoundsException("Incorrect inputs check the format!\n");
						// The first entry represents the name
						String name = line[0];
						if (containsNumeric(name)) {
							throw new InputMismatchException(name + ": name must only be String not a number");
						}

						// The second entry represents the event
						String date = line[1];
						byte age;

						// The third entry represents the age
						if (line[2].isEmpty()) {
							age = -1;
							txtAreaMain.appendText(name + " doesn't have an age.\n");
						} else {
							// Checks if the age was a digit or not
							if (isNumeric(line[2]))
								age = Byte.parseByte(line[2]);
							else {
								age = -1;
								txtAreaMain.appendText(name + " has an invalid age.\n");
							}
						}
						// The forth entry represents the location
						String location = line[3];
						if (containsNumeric(location)) {
							throw new InputMismatchException(name + ": location must only be String not a number");
						}
						if (!locCmBox.getItems().contains(location)) {
							locCmBox.getItems().add(location);
							locNames.add(location);
						}

						// The fifth entry represents the district
						String district = line[4];
						if (containsNumeric(district)) {
							throw new InputMismatchException(name + ": district must only be String not a number");
						}
						if (!disCmBox.getItems().contains(district)) {
							disCmBox.getItems().add(district);
							disNames.add(district);
						}

						if (!CorrectDate(date, lblLocInstInfo)) {
							txtAreaMain.appendText(name + " has invalid date of death\n");
						}
						char gender = '?';

						// The sixth entry represents the gender
						if (line[5].equals("NA") || line[5].isEmpty())
							gender = 'N';
						else
							gender = line[5].charAt(0);

						if (gender != 'm' && gender != 'M' && gender != 'F' && gender != 'f' && gender != 'N')
							throw new InputMismatchException(name + ": Gender must only be M/m/F/f\n");

						// Insert Date
						MartyrDate martDate = new MartyrDate(date);
						HashEntry dateHashEntry = datesHash.findHashEntry(date);
						if (dateHashEntry == null) {
							dateHashEntry = new HashEntry(martDate);
							datesHash.insert(martDate);
							datesNames.add(date);
							martDateCmBox.getItems().add(date);
							dateHashEntry.setHead(martDate.getMartyrTree());
						}

						MartyrAvlTree martyrTree = dateHashEntry.getHead();

						// Insert Martyr
						Martyr martyr = new Martyr(name, date, age, location, district, gender);
						MartyrNode martNode = martyrTree.find(martyr);
						if (martNode == null) {
							martNode = new MartyrNode(martyr);
							martyrTree.insert(martyr);
						}

						// Insert district
						LocationNode districtNode = districtList.findNode(district);
						if (districtNode == null) {
							districtNode = new LocationNode(district);
							LocationLinkedList locationList = new LocationLinkedList();
							districtNode.setHead(locationList);
							districtList.insertLocationSorted(districtNode);
						}
						// Get the reference to the locationList of the district
						LocationLinkedList locationList = (LocationLinkedList) districtNode.getHead();

						// Insert location
						LocationNode locationNode = locationList.findNode(location);
						if (locationNode == null) {
							locationNode = new LocationNode(location);
							locationList.insertLocationSorted(locationNode);
						}

					}
					// Catch blocks to handle exceptions
					catch (InputMismatchException e1) {
						e1.printStackTrace();
						txtAreaMain.appendText(e1.getMessage());
					} catch (IndexOutOfBoundsException e1) {
						e1.printStackTrace();
						txtAreaMain.appendText(e1.getMessage());
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
						txtAreaMain.appendText(e1.getMessage());
					} catch (Exception e1) {
						e1.printStackTrace();
						txtAreaMain.appendText(e1.getMessage());
					}
				}
			}
			// Catch blocks to handle exceptions and organize nodes
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
				lblMainInst.setText(e1.getMessage());
				txtAreaMain.setVisible(false);
				lblMainInfo.setVisible(false);
			} catch (NoSuchElementException e1) {
				e1.printStackTrace();
				lblMainInst.setText(e1.getMessage());
				txtAreaMain.setVisible(false);
				lblMainInfo.setVisible(false);
			} catch (Exception e1) {
				e1.printStackTrace();
				lblMainInst.setText(e1.getMessage());
				txtAreaMain.setVisible(false);
				lblMainInfo.setVisible(false);
			}
			sortComboBoxAlphabetically(disCmBox);
			sortComboBoxAlphabetically(locCmBox);
			sortComboBoxAlphabetically(martDateCmBox);

		}

		datesHash.printHash();
//		districtList.print();
//		districtList.getFront().getHead().print();
//		districtList.getFront().getNext().getHead().print();
	}

	// Method that inserts a district to the list
	private void insertDateScene(Stage primaryStage) {
		clear();
		if (bdPane.getCenter() != vBoxInstDis)
			bdPane.setCenter(vBoxInstDis);

		if (!hBoxInstDis.getChildren().contains(datePicker)) {
			hBoxInstDis.getChildren().clear();
			hBoxInstDis.getChildren().addAll(lblDateInst, datePicker);
		}

		// Organize the nodes on the scene
		lblDateInst.setText("Date: ");
		lblDateInstInfo.setText("");
		datePicker.setValue(null);
		txtFieldDateInst.clear();
		btDateInst.setText("Insert");
		lblDateInstInfo.setVisible(false);

		// Event handler for btDisInstMain button
		btDateInstMain.setOnAction(e -> returnMain(primaryStage));

		// Event handler for btDisInst button
		btDateInst.setOnAction(e -> {
			String date;
			LocalDate selectedDate = datePicker.getValue();
			LocalDate todayDate = LocalDate.now();

			if(selectedDate != null)
				if (selectedDate.isAfter(todayDate)) {
					showAlert(Alert.AlertType.ERROR, "Invalid Date, Date is in the future!", "Please select a valid date.\nTodays date is: " + todayDate);
					return;
				}

			if (datePicker.getValue() != null) {
				if (selectedDate.getMonthValue() < 10 && selectedDate.getDayOfMonth() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy"));
				} else if (selectedDate.getDayOfMonth() < 10 && selectedDate.getMonthValue() < 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
				} else if (selectedDate.getDayOfMonth() < 10 && selectedDate.getMonthValue() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("MM/d/yyyy"));
				} else if (selectedDate.getDayOfMonth() >= 10 && selectedDate.getMonthValue() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				} else
					date = "";

				// Check if the textField is empty or not
				if (!date.isEmpty()) {
					// Insert date
					MartyrDate martDate = new MartyrDate(date);
					HashEntry dateHashEntry = datesHash.findHashEntry(martDate.getDate());
					if (dateHashEntry == null) {
						dateHashEntry = new HashEntry(martDate);
						if (datesHash.insert(martDate)) {
							showAlert(Alert.AlertType.INFORMATION, "Insertation Success!",
									"Date: " + date + " has been inserted successfully!");
						} else
							showAlert(Alert.AlertType.ERROR, "Insertation Failed!",
									"Date: " + martDate.getDate() + " has not been inserted successfully!");
						martDateCmBox.getItems().add(date);
						sortComboBoxAlphabetically(martDateCmBox);
						lblDateInstInfo.setVisible(true);
						dateHashEntry.setHead(martDate.getMartyrTree());
					} else { // Notify the user that district exist from before
						lblDateInstInfo.setVisible(true);
						lblDateInstInfo.setText("Error: " + date + " date already exists!");
						showAlert(Alert.AlertType.ERROR, "Date Already Exists",
								"Error: " + date + " date already exists!");
					}
				} else { // Notify the user that the district is empty
					lblDateInstInfo.setVisible(true);
					lblDateInstInfo.setText("Error: Enter a date first!");
					showAlert(Alert.AlertType.ERROR, "Empty date", "Error: Enter a date first!");
				}
			} else {
				lblDateInstInfo.setVisible(true);
				lblDateInstInfo.setText("Error: Enter a date first!");
				showAlert(Alert.AlertType.ERROR, "Empty date", "Error: Enter a date first!");
			}
			datesHash.printHash();
		});

		primaryStage.setTitle("Insert Date");
		primaryStage.show();
	}

	private void updateDateScene(Stage primaryStage) {
		btDateUpdMain.setOnAction(e -> returnMain(primaryStage));
		datesHash.updateDate(bdPane, btDateUpdMain);
	}

	private void deleteDateScene(Stage primaryStage) {
		// Organize the nodes on the scene
		lblDateInstInfo.setText("");
		txtFieldDateInst.clear();
		lblDateInst.setText("Date: ");
		btDateInst.setText("Delete");
		datePicker.setValue(null);
		lblDateInstInfo.setVisible(false);
		clear();

		if (bdPane.getCenter() != vBoxInstDis) {
			bdPane.setCenter(vBoxInstDis);
		}

		if (!hBoxInstDis.getChildren().contains(datePicker))
			hBoxInstDis.getChildren().add(datePicker);

		btDateInstMain.setOnAction(e -> returnMain(primaryStage));

		// Event handler for btDisInst button with confirmation
		btDateInst.setOnAction(e -> {

			String date;
			LocalDate selectedDate = datePicker.getValue();

			if (datePicker.getValue() != null) {
				if (selectedDate.getMonthValue() < 10 && selectedDate.getDayOfMonth() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy"));
				} else if (selectedDate.getDayOfMonth() < 10 && selectedDate.getMonthValue() < 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
				} else if (selectedDate.getDayOfMonth() < 10 && selectedDate.getMonthValue() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("MM/d/yyyy"));
				} else if (selectedDate.getDayOfMonth() >= 10 && selectedDate.getMonthValue() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				} else
					date = "";

				// Check if the date is empty or not
				if (!date.isEmpty()) {
					// Confirmation dialog before deleting
					Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
					confirmation.setTitle("Confirm Delete Date");
					confirmation.setHeaderText("Are you sure you want to delete date?");
					confirmation.setContentText("This will permanently delete '" + date + "'");
					Optional<ButtonType> result = confirmation.showAndWait();

					if (result.isPresent() && result.get() == ButtonType.OK) {
						lblDateInstInfo.setVisible(true);
						if (datesHash.remove(date)) {
							martDateCmBox.getItems().remove(date); // Remove date from ComboBox
							showAlert(Alert.AlertType.INFORMATION, "Delete Success!",
									"Date: " + date + " has been deleted successfully!");
							lblDateInstInfo.setText("Date: " + date + " has been deleted successfully!");
						} else {
							showAlert(Alert.AlertType.ERROR, "Delete Failed!",
									"Date: " + date + " has not been deleted because it doesn't exist!");
							lblDateInstInfo
									.setText("Date: " + date + " has not been deleted because it doesn't exist!");
						}
					}
				} else { // Notify the user that the datePicker is empty
					lblDateInstInfo.setVisible(true);
					lblDateInstInfo.setText("Error: Enter a district name first!");
					showAlert(Alert.AlertType.ERROR, "Empty District Name", "Error: Enter a district name first!");
				}
			} else {
				lblDateInstInfo.setVisible(true);
				lblDateInstInfo.setText("Error: Enter a district name first!");
				showAlert(Alert.AlertType.ERROR, "Empty District Name", "Error: Enter a district name first!");
			}

		});

		primaryStage.setTitle("Delete District");
		primaryStage.show();
	}

	private void printDateScene(Stage primaryStage) {
		if (bdPane.getCenter() != vBoxdatesPrint)
			bdPane.setCenter(vBoxdatesPrint);

		btdatesPrintReturn.setOnAction(e -> {
			if (bdPane.getCenter() != vBoxdatesPrint)
				bdPane.setCenter(vBoxdatesPrint);
		});

		btdatesPrintReturn2.setOnAction(e -> {
			if (bdPane.getCenter() != vBoxdatesPrint)
				bdPane.setCenter(vBoxdatesPrint);
		});

		btdatesPrintMain.setOnAction(e -> returnMain(primaryStage));
		btdatesFullPrint.setOnAction(e -> datesHash.printHashOnTableWithStatusF(bdPane, btdatesPrintReturn));
		btdatesCompPrint.setOnAction(e -> datesHash.printHashOnTable(bdPane, btdatesPrintReturn));
	}

	private void navigateDateScene(Stage primaryStage) {
		// Organize the nodes on the scene
		txtAreaDateNavigation.setText("");
		txtAreaDateNavigation.setVisible(true);
		lblDateNavInfo.setText("");
		lblDateNavInfo.setVisible(true); // Show the label
		clear();
		btDateNavMain.setOnAction(e -> returnMain(primaryStage));

		forwardStack = new LinkedListStack();
		LinkedListStack visitedStack = new LinkedListStack();

		if (bdPane.getCenter() != vBoxNavDis) {
			bdPane.setCenter(vBoxNavDis);
		}

		// Ensure the stack is initialized with dates
		forwardStack = datesHash.sortDatesInStack(forwardStack);

		if (forwardStack.isEmpty()) {
			lblDateNavInfo.setText("There are no dates!");
			return;
		}

		// Reverse the dates in the forwardStack and push them onto visitedStack
		while (!forwardStack.isEmpty()) {
			visitedStack.push(forwardStack.pop());
		}

		// Peek the first date for display
		if (!visitedStack.isEmpty()) {
			currentDate = (HashEntry) visitedStack.pop();
			lblDateNavInfo.setText("Date: " + currentDate.getKey().getDate());
			datesHash.martyrStatistics(currentDate, txtAreaNavDates);
		}

		// Event handler for btDisNavPrev button
		btDateNavPrev.setOnAction(e -> {
			lblDateNavInfo.setVisible(true);
			txtAreaDateNavigation.setText("");

			if (!forwardStack.isEmpty()) {
				visitedStack.push(currentDate);
				currentDate = (HashEntry) forwardStack.pop();
				lblDateNavInfo.setText("Date: " + currentDate.getKey().getDate());
				datesHash.martyrStatistics(currentDate, txtAreaNavDates);
			}
		});

		// Event handler for btDisNavNext button
		btDateNavNext.setOnAction(e -> {
			lblDateNavInfo.setVisible(true);
			txtAreaDateNavigation.setText("");

			if (!visitedStack.isEmpty()) {
				forwardStack.push(currentDate);
				currentDate = (HashEntry) visitedStack.pop();
				lblDateNavInfo.setText("Date: " + currentDate.getKey().getDate());
				datesHash.martyrStatistics(currentDate, txtAreaNavDates);
			}
		});

		// Event handler for load button
		btDateNavLoad.setOnAction(e5 -> {
			// Create a new stage to show the district statistics

			currentDate2 = datesHash.findHashEntry(currentDate.getKey().getDate());

			if (currentDate2 != null) {
				datesHash.btLoadDisNavigation(currentDate2);
			}
		});

		primaryStage.setTitle("Navigate District");
		primaryStage.show();
	}

	private void insertMartScene(Stage primaryStage) {
		// Organize the nodes on the scene
		clear();
		btMartInst.setText("Insert");
		locCmBox.setDisable(true);
		btMartInstMain.setOnAction(e -> returnMain(primaryStage));

		if (bdPane.getCenter() != vBoxInstMart)
			bdPane.setCenter(vBoxInstMart);

		gdPane1.getChildren().clear();
		gdPane1.add(lblMartName, 0, 0);
		gdPane1.add(txtMartName, 1, 0);
		gdPane1.add(lblMartDate, 0, 1);
		gdPane1.add(datePicker, 1, 1);
		gdPane1.add(lblMartAge, 0, 2);
		gdPane1.add(txtMartAge, 1, 2);
		gdPane1.add(lblMartDis, 0, 3);
		gdPane1.add(disCmBox, 1, 3);
		gdPane1.add(lblMartLoc, 0, 4);
		gdPane1.add(locCmBox, 1, 4);
		gdPane1.add(lblMartGender, 0, 5);
		gdPane1.add(hBox1, 1, 5);

		// Add event listener to district combo box
		disCmBox.setOnAction(e -> {
			String selectedDistrict = disCmBox.getValue();
			if (selectedDistrict != null && !selectedDistrict.trim().isEmpty() && !selectedDistrict.equals("-")) {
				// Populate location combo box
				datesHash.insertLocationsToComboBox(districtList, selectedDistrict, locCmBox, lblDateInst);
				locCmBox.setDisable(false);
			} else
				locCmBox.setDisable(true);
		});

		// Set action for insert button
		btMartInst.setOnAction(e -> {
			// try-catch block to handle any exception that might occurs
			try {
				String name, date, location = null, district = null;

				// If all fields were empty it will inform the user
				if (txtMartName.getText().trim().isEmpty() && txtMartAge.getText().isEmpty() && location == null
						&& district == null && txtMartDate.getText().isEmpty()) {
					lblMartInstInfo.setVisible(true);
					lblMartInstInfo.setText("Fileds are empty");
					showAlert(Alert.AlertType.ERROR, "Empty Fileds", "Error: Fileds are empty!");
					return;
				}
				// If the name was empty it will inform the user
				if (txtMartName.getText().trim().isEmpty()) {
					lblMartInstInfo.setVisible(true);
					lblMartInstInfo.setText("Name is empty");
					showAlert(Alert.AlertType.ERROR, "Empty Name", "Error: Enter a name first!");
					return;
				} else {
					name = txtMartName.getText();
				}

				LocalDate selectedDate = datePicker.getValue();
				LocalDate todayDate = LocalDate.now();

				if (selectedDate.isAfter(todayDate)) {
					showAlert(Alert.AlertType.ERROR, "Invalid Date, Date is in the future!", "Please select a valid date.");
					return;
				}
				
				if (selectedDate.getMonthValue() < 10 && selectedDate.getDayOfMonth() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("M/dd/yyyy"));
				} else if (selectedDate.getDayOfMonth() < 10 && selectedDate.getMonthValue() < 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
				} else if (selectedDate.getDayOfMonth() < 10 && selectedDate.getMonthValue() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("MM/d/yyyy"));
				} else if (selectedDate.getDayOfMonth() >= 10 && selectedDate.getMonthValue() >= 10) {
					date = selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				} else
					date = "";

//			          date = datePicker.getValue().getMonth()+"/"+datePicker.getValue().getDayOfMonth()+"/"+datePicker.getValue().getYear();

				// If the date was empty it will inform the user
				if (date.isEmpty()) {
					lblMartInstInfo.setVisible(true);
					lblMartInstInfo.setText("Date is empty");
					showAlert(Alert.AlertType.ERROR, "Empty Date", "Error: Enter a date first!");
					return;
				}

				byte age;
				// If the age was empty it will inform the user
				if (txtMartAge.getText().trim().isEmpty()) {
					age = -1;
					lblMartInstInfo.setVisible(true);
					lblMartInstInfo.setText("Warning: Age is empty!");
					showAlert(Alert.AlertType.WARNING, "Empty Age", "Age is empty!");
				} else {
					age = Byte.parseByte(txtMartAge.getText());
					if (age < 0) {
						lblMartInstInfo.setVisible(true);
						lblMartInstInfo.setText("Age must be greater or equal to zero!");
						showAlert(Alert.AlertType.ERROR, "Wrong Age", "Age must be greater or equal to zero!");
						return;
					}
				}

				String disName = disCmBox.getValue();

				// If the location was empty it will inform the user
				if (disName == null || disName.trim().isEmpty() || disName.equals("-")) {
					lblMartInstInfo.setVisible(true);
					lblMartInstInfo.setText("District is empty");
					showAlert(Alert.AlertType.ERROR, "Empty District", "Error: Enter a district name first!");
					return;
				} else {
					district = disName;
				}

				String locName = locCmBox.getValue();

				// If the location was empty it will inform the user
				if (locName == null || locName.trim().isEmpty() || locName.equals("-")) {
					lblMartInstInfo.setVisible(true);
					lblMartInstInfo.setText("Location is empty");
					showAlert(Alert.AlertType.ERROR, "Empty Location", "Error: Enter a location name first!");
					return;
				} else {
					location = locName;
				}

				char gender = '?';

				if (rbMale.isSelected())
					gender = 'M';
				else if (rbFemale.isSelected())
					gender = 'F';
				else if (rbUnknown.isSelected())
					gender = 'N';

				// If all fields were entered correctly it will add the new martyr
				if (!name.isEmpty() && (age >= 0 || age == -1) && !location.isEmpty() && !district.isEmpty()
						&& !date.isEmpty()) {
					// If no gender was selected it will throw an exception
					if (gender != 'M' && gender != 'F' && gender != 'N') {
						lblMartInstInfo.setVisible(true);
						lblMartInstInfo.setText("Choose a gender!");
						showAlert(Alert.AlertType.ERROR, "Age not Chosen", "Error: Please choose an age first!");
						return;
					}

					// Insert Date
					MartyrDate martDate = new MartyrDate(date);
					HashEntry dateHashEntry = datesHash.findHashEntry(date);
					if (dateHashEntry == null) {
						dateHashEntry = new HashEntry(martDate);
						datesHash.insert(martDate);
						dateHashEntry.setHead(martDate.getMartyrTree());
					}

					MartyrAvlTree martyrTree = dateHashEntry.getHead();

					// Insert Martyr
					Martyr martyr = new Martyr(name, date, age, location, district, gender);
					MartyrNode martNode = martyrTree.find(martyr);
					if (martNode == null) {
						martNode = new MartyrNode(martyr);
						if (martyrTree.insert(martyr))
							showAlert(Alert.AlertType.INFORMATION, "Insertation Success!",
									"Martyr: " + name + " has been inserted successfully!");
						else
							showAlert(Alert.AlertType.ERROR, "Insertation Failed!",
									"Martyr '" + name + "' does exist from before!");

					} else {
						lblMartInstInfo.setVisible(true);
						lblMartInstInfo.setText("This martyr: " + martyr.getName() + " already exists!");
						showAlert(Alert.AlertType.ERROR, "Martyr Exist",
								"This martyr: " + martyr.getName() + " already exists!!");
					}

				}
				// If any field was empty it will notify the user
				else {
					lblMartInstInfo.setVisible(true);
					if (name.isEmpty()) {
						lblMartInstInfo.setText("Name is empty");
						showAlert(Alert.AlertType.ERROR, "Empty Name", "Error: Enter a martyr name first!");
						return;
					}
					if (location.isEmpty()) {
						lblMartInstInfo.setText("Location is empty");
						showAlert(Alert.AlertType.ERROR, "Empty Location Name", "Error: Enter a location name first!");
						return;
					}
					if (district.isEmpty()) {
						lblMartInstInfo.setText("District is empty");
						showAlert(Alert.AlertType.ERROR, "Empty District Name", "Error: Enter a district name first!");
						return;
					}
					if (date.isEmpty()) {
						lblMartInstInfo.setText("Date is empty");
						showAlert(Alert.AlertType.ERROR, "Empty Date", "Error: Enter a date first!");
						return;
					}
					if (gender == '?') {
						lblMartInstInfo.setText("Gender is not chosen");
						showAlert(Alert.AlertType.ERROR, "Gender not chosen", "Error: choose a gender first!");
						return;
					}
				}
			}
			// Catch blocks to handle exceptions
			catch (InputMismatchException e1) {
				lblMartInstInfo.setVisible(true);
				lblMartInstInfo.setText(e1.getMessage());
			} catch (NumberFormatException e1) {
				lblMartInstInfo.setVisible(true);
				lblMartInstInfo.setText("Please an integer for the age not a string!");
				showAlert(Alert.AlertType.ERROR, "Wrong Fotmat",
						"Error: this field should contains only string not integer!");
			} catch (Exception e1) {
				lblMartInstInfo.setVisible(true);
				lblMartInstInfo.setText(e1.getMessage());
			}
		});

		btMartClear.setOnAction(e -> clear());

		primaryStage.setTitle("Insert Martyr");
		primaryStage.show();
	}

	// Method that updates a martyr information
	private void updateMartScene(Stage primaryStage) {
		// Organize the nodes on the scene
		clear();
		lblMartUpdInfo.setText("");
		txtMartDisUpd.clear();
		txtMartLocUpd.clear();
		txtOldMartUpd.clear();
		txtMartDateUpd.clear();
		txtAreaMartTreeNav.setText("");
		lblMartDateUpd.setVisible(true);
		txtMartDateUpd.setVisible(true);
		txtAreaMartTreeNav.setVisible(false);
		lblMartUpdInfo.setVisible(false);
		btMartUpd.setVisible(false);

		btMartUpdMain.setOnAction(e -> returnMain(primaryStage));

		if (bdPane.getCenter() != datesHash.updateDeleteMartyr(districtList, disCmBox, locCmBox)) {
			VBox vbox = datesHash.updateDeleteMartyr(districtList, disCmBox, locCmBox);
			vbox.getChildren().add(btMartUpdMain);
			bdPane.setCenter(vbox);
		}

		primaryStage.setTitle("Update Martyr");
		primaryStage.show();

	}

	// Method that deletes a martyr
	private void MartTreeHeightAndSizeScene(Stage primaryStage) {
		// Organize the nodes on the scene
		clear();
		txtMartDisDel.clear();
		txtMartLocDel.clear();
		txtMartDel.clear();
		txtMartDateDel.clear();
		lblMartDelInfo.setText("");
		lblMartDelInfo.setVisible(false);
		lblMartDateDel.setVisible(true);
		txtMartDateDel.setVisible(true);
		txtAreaMartDelete.setVisible(false);
		martDateCmBox.setValue(null);
		martDateCmBox.setPromptText("MM/DD/YYYY");

		btMartDelMain.setOnAction(e -> returnMain(primaryStage));

		if (bdPane.getCenter() != vBoxDelMart)
			bdPane.setCenter(vBoxDelMart);

		gdPaneMartDel.getChildren().clear();
		gdPaneMartDel.add(lblMartDateDel, 0, 0);
		gdPaneMartDel.add(martDateCmBox, 1, 0);
		gdPaneMartDel.add(btMartHeightAndSize, 2, 0);

		btMartHeightAndSize.setOnAction(e -> {
			String date;
			String selectedDate = martDateCmBox.getValue();
			if (selectedDate != null) {
				date = selectedDate;
			} else {
				txtAreaMartDelete.setVisible(false);
				lblMartDelInfo.setVisible(true);
				showAlert(Alert.AlertType.ERROR, "Empty Date", "Error: Enter a date first!");
				return;
			}

			if (date == null || date.isEmpty() || date.equals("-")) {
				txtAreaMartDelete.setVisible(false);
				lblMartDelInfo.setVisible(true);
				lblMartDelInfo.setText("Enter a date first!");
				showAlert(Alert.AlertType.ERROR, "Empty Date", "Error: Enter a date first!");
				return;
			}

			HashEntry node = datesHash.findHashEntry(date);
			if (node != null) {
				MartyrAvlTree martyrTree = node.getHead();
				if (martyrTree != null) {
					txtAreaMartDelete.setVisible(true);
					txtAreaMartDelete.clear();
					txtAreaMartDelete.appendText(
							"Height: " + martyrTree.getHeight() + "\n\nSize: " + martyrTree.getSize() + "\n");
				} else {
					txtAreaMartDelete.setVisible(false);
					lblMartDelInfo.setVisible(true);
					lblMartDelInfo.setText("Tree is empty!");
					showAlert(Alert.AlertType.ERROR, "Tree is empty", "Error: Tree is empty!");
					return;
				}
			} else {
				txtAreaMartDelete.setVisible(false);
				lblMartDelInfo.setVisible(true);
				lblMartDelInfo.setText("Date doesn't exist!");
				showAlert(Alert.AlertType.ERROR, "Date doesn't exist!", "Error: Date doesn't exist!");
				return;
			}

		});

		primaryStage.setTitle("Delete Martyr");
		primaryStage.show();
	}

	private void navigateMartTreeScene(Stage primaryStage) {
		clear();
		lblMartTreeNavInfo.setVisible(false);
		btLocNavLoad.setVisible(false);
		txtAreaMartTreeNav.setVisible(true);
		btLocNavMain.setOnAction(e -> returnMain(primaryStage));

		if (bdPane.getCenter() != vBoxNavMartTree) {
			bdPane.setCenter(vBoxNavMartTree);
		}

		forwardStack = new LinkedListStack();
		LinkedListStack visitedStack = new LinkedListStack();

		// Ensure the stack is initialized with dates
		forwardStack = datesHash.sortDatesInStack(forwardStack);

		if (forwardStack.isEmpty()) {
			lblMartTreeNavInfo.setText("There are no dates!");
			return;
		}

		// Reverse the dates in the forwardStack and push them onto visitedStack
		while (!forwardStack.isEmpty()) {
			visitedStack.push(forwardStack.pop());
		}

		// Peek the first date for display
		if (!visitedStack.isEmpty()) {
			currentDate = (HashEntry) visitedStack.pop();
			lblMartTreeNavInfo.setVisible(true);
			lblMartTreeNavInfo.setText("Date: " + currentDate.getKey().getDate());
			currentDate.getHead().printTreeLevelByLevelRightToLeft(currentDate.getHead().getRoot(), txtAreaMartTreeNav);
		}

		// Event handler for btDisNavPrev button
		btNavMartTreePrev.setOnAction(e -> {
			lblDateNavInfo.setVisible(true);
			txtAreaDateNavigation.setText("");

			if (!forwardStack.isEmpty()) {
				visitedStack.push(currentDate);
				currentDate = (HashEntry) forwardStack.pop();
				lblMartTreeNavInfo.setText("Date: " + currentDate.getKey().getDate());
				currentDate.getHead().printTreeLevelByLevelRightToLeft(currentDate.getHead().getRoot(),
						txtAreaMartTreeNav);
			}
		});

		// Event handler for btDisNavNext button
		btNavMartTreeNext.setOnAction(e -> {
			lblMartTreeNavInfo.setVisible(true);
			txtAreaMartTreeNav.setText("");

			if (!visitedStack.isEmpty()) {
				forwardStack.push(currentDate);
				currentDate = (HashEntry) visitedStack.pop();
				lblMartTreeNavInfo.setText("Date: " + currentDate.getKey().getDate());
				currentDate.getHead().printTreeLevelByLevelRightToLeft(currentDate.getHead().getRoot(),
						txtAreaMartTreeNav);
			}
		});

		primaryStage.setTitle("Navigate Martyr Tree");
		primaryStage.show();
	}

	private void printMartSortedScene(Stage primaryStage) {
		btMartSortMain.setOnAction(e -> returnMain(primaryStage));
		datesHash.printMartyrsSortedByAge(bdPane, btMartSortMain);
	}

	// Method that writes the district information on a file
	private void writeFileDatesScene(Stage primaryStage) {
		// Organize the nodes on the scene
		lblDateInst.setText("File Name: ");
		lblDateInstInfo.setText("");
		txtFieldDateInst.clear();
		txtFieldDateInst.setPromptText("e.g.: Al-Quds");
		btDateInst.setText("Save");
		lblDateInstInfo.setVisible(false);
		btDateInstMain.setOnAction(e -> returnMain(primaryStage));

		if (bdPane.getCenter() != vBoxInstDis)
			bdPane.setCenter(vBoxInstDis);

		if (!hBoxInstDis.getChildren().contains(txtFieldDateInst)) {
			hBoxInstDis.getChildren().clear();
			hBoxInstDis.getChildren().addAll(lblDateInst, txtFieldDateInst);
		}

		// Event handler for btDisInst button
		btDateInst.setOnAction(e -> {
			// Check if the textField is empty or not
			if (!txtFieldDateInst.getText().isEmpty()) {
				String fileName = txtFieldDateInst.getText().concat(".csv");

				// Check if the file already exists
				File file = new File(fileName);
				if (file.exists()) {
					// File exists, show confirmation dialog
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText("File already exists!");
					alert.setContentText("The file \"" + fileName + "\" already exists. Do you want to override it?");

					ButtonType yesButton = new ButtonType("Yes");
					ButtonType noButton = new ButtonType("No");

					alert.getButtonTypes().setAll(yesButton, noButton);

					alert.showAndWait().ifPresent(buttonType -> {
						if (buttonType == yesButton) {
							// User clicked Yes, override the file
							if (datesHash.writeOnFile(fileName)) {
								// File saved successfully, show confirmation
								showConfirmationAlert("File Saved",
										"The file \"" + fileName + "\" has been saved successfully!");
							}
						}
					});
				} else {
					// File does not exist, directly write to the file
					if (datesHash.writeOnFile(fileName)) {
						// File saved successfully, show confirmation
						showConfirmationAlert("File Saved",
								"The file \"" + fileName + "\" has been saved successfully!");
					}
				}
			} else { // Notify the user that the textField is empty
				lblDateInstInfo.setVisible(true);
				lblDateInstInfo.setText("Error: Enter a file name first!");
			}
		});

		primaryStage.setTitle("Write District On File");
		primaryStage.show();
	}

	// Method to show a confirmation alert
	private void showConfirmationAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// Method that checks if the date is in the correct format and valid
	private boolean CorrectDate(String date, Label lbl) {
		// If the date contains "-" and doesn't contain "/" it will return false
		if (date.contains("-") || !date.contains("/")) {
			lbl.setText("Check the date format it should be written (MM/DD/YYYY)");
			return false;
		}

		// If the date didn't contain 3 parts it will return false
		String[] d = date.split("/");
		if (d.length != 3) {
			lbl.setText("Check the date format it should be written (MM/DD/YYYY)");
			return false;
		}

		int year, month, day;
		try {
			day = Integer.parseInt(d[1]);
			month = Integer.parseInt(d[0]);
			year = Integer.parseInt(d[2]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (Exception e) {
			return false;
		}

		// If the moth was anything except the numbers form 1-12 it will return false
		if (month < 1 || month > 12) {
			lbl.setText("The date is invalid, check the date format (MM/DD/YYYY)");
			return false;
		}

		int daysInMonth;
		// February
		if (month == 2) {
			if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
				// Leap year
				daysInMonth = 29;
			} else {
				// Non-leap year
				daysInMonth = 28;
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			// April, June, September, November
			daysInMonth = 30;
		} else {
			// All other months
			daysInMonth = 31;
		}

		// If the day was between 1 and 31 it will return true
		if (day >= 1 && day <= daysInMonth) {
			return true;
		} else {
			lbl.setText("The date is invalid, check the date format (MM/DD/YYYY)");
			return false;
		}

	}

	// Method that switch the case to the main window
	private void returnMain(Stage stage) {
		stage.setScene(sceneMain);
		if (bdPane.getCenter() != vBoxMain)
			bdPane.setCenter(vBoxMain);
		stage.setTitle("Main Window");
		stage.show();
	}

	// Method that clears all the textFields and organize the nodes
	private void clear() {
		txtMartName.clear();
		txtMartAge.clear();
		txtMartDis.clear();
		txtMartLoc.clear();
		txtMartDate.clear();
		datePicker.setValue(null);
		disCmBox.setValue(null);
		locCmBox.setValue(null);
		locCmBox.setDisable(true);
		rbMale.setSelected(false);
		rbFemale.setSelected(false);
		rbUnknown.setSelected(false);
		lblMartInstInfo.setText("");
		lblMartInstInfo.setVisible(false);
	}

	// Method that checks if the string was a digit or not
	private boolean isNumeric(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// Method that checks if the string contains any numeric character
	private boolean containsNumeric(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	// Method to sort the content of a ComboBox alphabetically ignoring case
	public void sortComboBoxAlphabetically(ComboBox<String> comboBox) {
		ObservableList<String> itemsList = FXCollections.observableArrayList(comboBox.getItems());

		// Sort the items alphabetically ignoring case
		Collections.sort(itemsList, String.CASE_INSENSITIVE_ORDER);

		// Clear the ComboBox and add the sorted items back
		comboBox.getItems().setAll(itemsList);
	}

	private void showAlert(Alert.AlertType alertType, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
