module club.youtee.filerename {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires static lombok;

    opens club.youtee.filerename to javafx.fxml, spring.context, spring.beans, spring.core;
    opens club.youtee.filerename.config to spring.context, spring.beans, spring.core;
    opens club.youtee.filerename.controller to javafx.fxml, spring.context, spring.beans, spring.core;
    exports club.youtee.filerename;
    exports club.youtee.filerename.controller;
    exports club.youtee.filerename.domain;
    exports club.youtee.filerename.service.impl to spring.beans;
}