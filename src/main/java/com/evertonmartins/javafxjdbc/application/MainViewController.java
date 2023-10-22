package com.evertonmartins.javafxjdbc.application;

import com.evertonmartins.javafxjdbc.model.services.DepartmentService;
import com.evertonmartins.javafxjdbc.model.services.SellerService;
import com.evertonmartins.javafxjdbc.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction(){
        loadView("/SellerList.fxml", (SellerListController controller) -> {
            controller.setSellerService(new SellerService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemDepartmentAction(){
        loadView("/DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemAboutAction(){
        loadView("/About.fxml", x -> {});
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initilizingAction){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());

            T controller = loader.getController();
            initilizingAction.accept(controller);

        }catch (IOException e){
            Alerts.showAlert("IOException", "Error loafing view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
