package com.evertonmartins.javafxjdbc.application;

import com.evertonmartins.javafxjdbc.listeners.DataChangeListener;
import com.evertonmartins.javafxjdbc.model.entities.Department;
import com.evertonmartins.javafxjdbc.model.services.DepartmentService;
import com.evertonmartins.javafxjdbc.util.Alerts;
import com.evertonmartins.javafxjdbc.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable, DataChangeListener {

    @FXML
    private DepartmentService departmentService;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    @FXML
    private ObservableList<Department> obsList;
    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj, "/DepartmentForm.fxml", parentStage);
    }

    public void setDepartmentService(DepartmentService service){
        this.departmentService = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(departmentService == null){
            throw new IllegalStateException("Service was null");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
    }

    private void createDialogForm(Department obj, String absolutName, Stage parentStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();

            DepartmentFormController controller = loader.getController();
            controller.setDepartment(obj);
            controller.setDepartmentService(new DepartmentService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data:");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }catch (IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
