module com.datastructuresproject3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.datastructuresproject3 to javafx.fxml;
    exports com.datastructuresproject3;
}