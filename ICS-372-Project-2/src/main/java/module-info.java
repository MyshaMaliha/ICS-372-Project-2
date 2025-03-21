module org.example.ics372project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires  json.simple;  // Use "static" to allow non-modular JARs
    requires java.desktop; // Add this line to allow access to org.w3c.dom

    opens org.example.ics372project2 to javafx.fxml;
    exports org.example.ics372project2;
}