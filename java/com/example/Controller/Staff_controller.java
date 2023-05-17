package com.example.Controller;

import com.example.mainclass.Staff;
import com.example.school_management.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Staff_controller implements Initializable {

    @FXML
    private TableColumn<?, ?> Gender_table;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<?> gender;

    @FXML
    private TextField id;

    @FXML
    private Button insert;

    @FXML
    private Button insert_img;

    @FXML
    private TextField mobile;

    @FXML
    private TextField name;

    @FXML
    private Button print;

    @FXML
    private ComboBox<?> subject;

    @FXML
    private Button update;

    @FXML
    private Label file_path;

    @FXML
    private TableColumn<?, ?> id_table;

    @FXML
    private ImageView img_view;

    @FXML
    private AnchorPane left_pane;

    @FXML
    private TableColumn<?, ?> mobile_table;

    @FXML
    private TableColumn<?, ?> name_table;

    @FXML
    private TableColumn<?, ?> subject_table;

    @FXML
    private TableView<Staff> table_view;

    @FXML
    private AnchorPane tchr_mainpane;

    private String[] Combo_gender={"Male","Female","Others"};
    private String[] Combo_subject={"Account officer","office assistant","Athletic director","School counselor","Secretary","Clerk","Peon","Gate man","Medical assistant","Cleaner"};


    public void Combo_box()
    {
        List<String> list=new ArrayList<>();
        for(String data:Combo_gender)
        {
            list.add(data);
        }
        ObservableList data_list= FXCollections.observableArrayList(list);
        gender.setItems(data_list);

        List<String> class_list=new ArrayList<>();
        for(String data:Combo_subject)
        {
            class_list.add(data);
        }
        ObservableList data_list_class= FXCollections.observableArrayList(class_list);
        subject.setItems(data_list_class);
    }

    @FXML
    void ImgInsert(ActionEvent event) {
        FileChooser open= new FileChooser();
        Stage stage=(Stage) left_pane.getScene().getWindow();
        File file=open.showOpenDialog(stage);
        if(file!=null)
        {
            String img_path=file.getAbsolutePath();

            img_path=img_path.replace("\\","\\\\");
            file_path.setText(img_path);

            Image image= new Image(file.toURI().toString(),110,110,false,true);
            img_view.setImage(image);

        }
        else
        {
            System.out.println("teacher pic missing");
        }

    }


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    public void clear()
    {
        id.setText("");
        name.setText("");
        subject.getSelectionModel().clearSelection();
        gender.getSelectionModel().clearSelection();
        img_view.setImage(null);
        file_path.setText("Label");
        mobile.setText("");

    }
    public ObservableList<Staff> datalist()
    {
        ObservableList<Staff> datalist = FXCollections.observableArrayList();

        String sql;
        sql ="SELECT * FROM staff_data";

        try {
            connect= database.connectDB();
            prepare = connect.prepareStatement(sql);
            result=prepare.executeQuery();


            while(result.next())
            {
                Staff teacher= new Staff(result.getInt("id"),result.getString("name"),result.getString("designation"),result.getString("gender"),result.getString("picture"),result.getString("mobile"));
                datalist.add(teacher);
            }

        }catch (Exception e) {
            System.out.println("staff database error");
        }
        finally
        {
            try
            {
                connect.close();
                result.close();
                prepare.close();
                statement.close();

            }catch (Exception e)
            {

            }
        }
        return datalist;

    }
    public void showData()
    {
        ObservableList<Staff> showlist = datalist();
        id_table.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        subject_table.setCellValueFactory(new PropertyValueFactory<>("designation"));
        Gender_table.setCellValueFactory(new PropertyValueFactory<>("gender"));
        mobile_table.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        table_view.setItems(showlist);
    }


    @FXML
    void delete(ActionEvent event) {
        String sql="DELETE from staff_data WHERE `id` ='"+id.getText()+"'";
        connect=database.connectDB();
        try
        {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("                                     Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("                     Are you sure you want to delete? ");

            Optional<ButtonType> buttontype= alert.showAndWait();
            if(buttontype.get()==ButtonType.OK)
            {
                statement=connect.createStatement();
                statement.executeUpdate(sql);
            }
            showData();
            clear();

        } catch (SQLException e) {
            System.out.println("delete error");
        }
        finally
        {
            try
            {
                connect.close();
                result.close();
                prepare.close();
                statement.close();

            }catch (Exception e)
            {

            }
        }

    }

    @FXML
    void insert(ActionEvent event) {
        String sql="INSERT INTO staff_data VALUES (?,?,?,?,?,?)";

        try {
            connect= database.connectDB();
            if(id.getText().isEmpty() | name.getText().isEmpty() | subject.getSelectionModel().isEmpty() |
                    gender.getSelectionModel().isEmpty() | img_view.getImage()==null)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("                                     Error!!!!!");
                alert.setHeaderText("            Some fields are empty.  ");
                alert.setContentText("                             Please enter all blank fields. ");
                alert.showAndWait();
            }
            else
            {

                prepare=connect.prepareStatement(sql);
                prepare.setString(1,id.getText());
                prepare.setString(2,name.getText());
                prepare.setString(3, (String) subject.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) gender.getSelectionModel().getSelectedItem());
                prepare.setString(5,file_path.getText());
                prepare.setString(6,mobile.getText());
                prepare.execute();
                System.out.println("ok12");
                showData();
                clear();
            }


        }catch (Exception e) {
            System.out.println(e);
        }
        finally
        {
            try
            {
                connect.close();
                result.close();
                prepare.close();
                statement.close();

            }catch (Exception e)
            {

            }
        }

    }

    @FXML
    void print_rep(ActionEvent event) {
        try
        {
            connect=database.connectDB();
            JasperDesign jdesign= JRXmlLoader.load("src/main/resources/com/example/school_management/report_jasper/staff.jrxml");
            JRDesignQuery jq= new JRDesignQuery();
            //  String filepath="src/main/resources/com/example/school_management/report_jasper/stud_all.jrxml";

            JasperReport jreport= JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint= JasperFillManager.fillReport(jreport,null,connect);
            JasperViewer viewer= new JasperViewer(jprint,false);
            viewer.setTitle("Report");
            viewer.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    @FXML
    void selectData(MouseEvent event) {
        Staff teacher = (Staff) table_view.getSelectionModel().getSelectedItem();
        int no=table_view.getSelectionModel().getSelectedIndex();
        if((no-1)<-1)
        {
            return;
        }
        id.setText(String.valueOf(teacher.getId()));
        name.setText(String.valueOf(teacher.getName()));
        //  crud_gender.getSelectionModel().select(Integer.parseInt(data.getCurd_gender()));
        gender.getSelectionModel().clearSelection();
        //   crud_class.getSelectionModel().select(Integer.parseInt(data.getCurd_class()));
        //crud_class.getSelectionModel().clearSelection();
        subject.getSelectionModel().clearSelection();
        String pic="file:"+ teacher.getPicture();
        Image img= new Image(pic,110,110,false,true);
        img_view.setImage(img);

        String tmp= teacher.getPicture();

        file_path.setText(tmp);
        mobile.setText(String.valueOf(teacher.getMobile()));

    }

    @FXML
    void text_field_design() {
        if(id.isFocused()){
            id.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            name.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
            subject.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }
        else if(name.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            subject.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }

        else if(subject.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            subject.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }
        else if(gender.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            subject.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            gender.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }

        else if(mobile.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            subject.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
        }

    }

    @FXML
    void update_Crud(ActionEvent event) {
        String tmp=file_path.getText();
        tmp=tmp.replace("\\","\\\\");
        String sql="UPDATE staff_data SET `name`= '"+name.getText()+ "', `designation` = '"+subject.getSelectionModel().getSelectedItem()
                +"', `gender` = '"+gender.getSelectionModel().getSelectedItem()+"', `picture` = '"+tmp+"', `mobile` = '"+mobile.getText()+"' WHERE id = '"+id.getText()+"'";
        try {
            connect= database.connectDB();
            if(id.getText().isEmpty() | name.getText().isEmpty() | subject.getSelectionModel().isEmpty() |
                    gender.getSelectionModel().isEmpty() | img_view.getImage()==null)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("                                     Error!!!!!");
                alert.setHeaderText("            Some fields are empty.  ");
                alert.setContentText("                             Please enter all blank fields. ");
                alert.showAndWait();
            }
            else
            {
                statement=connect.createStatement();
                statement.executeUpdate(sql);
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("                                      Update Successfull!!!");
                alert.setHeaderText("       ");
                alert.setContentText("                             Successfully updated the data. ");
                alert.showAndWait();
                showData();
                clear();
            }

        } catch (Exception e) {
            System.out.println("update crud error");
        }
        finally
        {
            try
            {
                connect.close();
                result.close();
                prepare.close();
                statement.close();

            }catch (Exception e)
            {

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Combo_box();
        showData();
    }
}
