module com.example.jdom_java_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires org.jdom2;
    requires java.desktop;

    opens com.example.jdom_java_project to javafx.fxml;
    exports com.example.jdom_java_project;
}