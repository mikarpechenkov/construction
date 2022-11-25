module com.mkenit.timemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.mkenit.timemanager.templates to javafx.fxml;
    exports com.mkenit.timemanager;
}