package com.evertonmartins.javafxjdbc.application;

import com.evertonmartins.javafxjdbc.db.DbException;
import com.evertonmartins.javafxjdbc.listeners.DataChangeListener;
import com.evertonmartins.javafxjdbc.model.entities.Department;
import com.evertonmartins.javafxjdbc.model.entities.Seller;
import com.evertonmartins.javafxjdbc.model.exceptions.ValidationException;
import com.evertonmartins.javafxjdbc.model.services.DepartmentService;
import com.evertonmartins.javafxjdbc.model.services.SellerService;
import com.evertonmartins.javafxjdbc.util.Alerts;
import com.evertonmartins.javafxjdbc.util.Constraints;
import com.evertonmartins.javafxjdbc.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private DepartmentService departmentService;

    private SellerService sellerService;

    private Seller entity;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private Label errorLabelName;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label errorLabelEmail;

    @FXML
    private Label errorLabelBirthDate;

    @FXML
    private Label errorLabelBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    private ObservableList<Department> obsList;

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setServices(SellerService sellerService, DepartmentService departmentService) {
        this.sellerService = sellerService;
        this.departmentService = departmentService;
    }

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event){
        if (entity == null){
            throw new IllegalStateException("Entitie was null!");
        }
        if (sellerService == null){
            throw new IllegalStateException("sellerService was null!");
        }
        try {
            entity = getFormData();
            sellerService.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }catch (DbException e){
            Alerts.showAlert("Error saving object!", null, e.getMessage(), Alert.AlertType.ERROR);
        }catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation error!");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty!");
        }
        obj.setName(txtName.getText());

        if (!exception.getErrors().isEmpty()){
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Entity was null!");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if (entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        }else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects(){
        if (departmentService == null){
            throw new IllegalStateException("Department service was null!");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("name")){
            errorLabelName.setText(errors.get("name"));
        }
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
