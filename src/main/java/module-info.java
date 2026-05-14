module com.numbermatch.numbermatchalgoritmos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.numbermatch.numbermatchalgoritmos to javafx.fxml;
    exports com.numbermatch.numbermatchalgoritmos;
}