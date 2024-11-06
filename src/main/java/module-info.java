/**
 * JavaFX is built and distributed as a set of named modules, each in its own modular jar file, and the JavaFX runtime
 * expects its classes to be loaded from a set of named javafx.* modules, and does not support loading those modules
 * from the classpath.
 */
module club.youtee.filerename {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    //requires spring.beans;
    //requires spring.boot;
    //requires spring.boot.autoconfigure;
    //requires spring.context;
    //requires spring.core;
    requires static lombok;
    requires org.apache.commons.io;
    requires org.yaml.snakeyaml;
    requires info.movito.themoviedbapi;

    opens club.youtee.filerename to javafx.fxml;
    opens club.youtee.filerename.controller to javafx.fxml;
    opens javafx.scene.control.ext.cell to javafx.fxml;
    //opens club.youtee.filerename to javafx.fxml, spring.context, spring.beans, spring.core;
    //opens club.youtee.filerename.config to spring.context, spring.beans, spring.core;
    //opens club.youtee.filerename.controller to javafx.fxml, spring.context, spring.beans, spring.core;

    exports club.youtee.filerename;
    exports club.youtee.filerename.config;
    exports club.youtee.filerename.controller;
    exports club.youtee.filerename.domain;
    exports club.youtee.filerename.domain.entity;

    //exports club.youtee.filerename.service.impl to spring.beans;
}