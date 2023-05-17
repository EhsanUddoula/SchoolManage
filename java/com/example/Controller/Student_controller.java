package com.example.Controller;


import com.example.mainclass.Student;
import com.example.school_management.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class Student_controller implements Initializable {

    @FXML
    private AnchorPane left_pane;

    @FXML
    private AnchorPane stud_crud;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private AnchorPane class_select_pane;

    @FXML
    private Button back_button;


    @FXML
    private TableColumn<Student, String> Gender_table;

    @FXML
    private Button clear;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<?> gender;

    @FXML
    private ComboBox<?> Class;

    @FXML
    private TextField id;

    @FXML
    private TextField mobile;

    @FXML
    private Button insert;

    @FXML
    private Button insert_img;

    @FXML
    private Button print;

    @FXML
    private TextField name;

    @FXML
    private Button update;

    @FXML
    private ImageView img_view;

    @FXML
    private Label file_path;

    @FXML
    private AnchorPane stud_mainpane;

    @FXML
    private TableView<Student> table_view;

    @FXML
    private TableColumn<Student, String> class_table;

    @FXML
    private TableColumn<Student, Integer> id_table;

    @FXML
    private TableColumn<Student, String> mobile_table;

    @FXML
    private TableColumn<Student, String> name_table;

    @FXML
    private Button back_btn;

    @FXML
    private AnchorPane img_pane;



    private String[] Combo_gender={"Male","Female","Others"};
    private String[] Combo_class={"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};


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
        for(String data:Combo_class)
        {
            class_list.add(data);
        }
        ObservableList data_list_class= FXCollections.observableArrayList(class_list);
        Class.setItems(data_list_class);
    }


//    public void Combo_box()
//    {
//        List<String> class_list=new ArrayList<>();
//        for(String data:Combo_class)
//        {
//            class_list.add(data);
//        }
//        ObservableList data_list= FXCollections.observableArrayList(class_list);
//        crud_class.setItems(data_list);
//    }


    public  void text_field_design(){
        if(id.isFocused()){
            id.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            name.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
            Class.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }
        else if(name.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            Class.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }

        else if(Class.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            Class.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }
        else if(gender.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            Class.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            gender.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            mobile.setStyle("-fx-border-width:1px;-fx-background-color: transparent");
        }

        else if(mobile.isFocused()){
            id.setStyle("-fx-border-width:1px;-fx-background-color:transparent");
            name.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            Class.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
            mobile.setStyle("-fx-border-width:2px;-fx-background-color: #fff");
            gender.setStyle("-fx-border-width:1px;-fx-background-color:  transparent");
        }
    }
    ///// database
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public void clear()
    {
        id.setText("");
        name.setText("");
        Class.getSelectionModel().clearSelection();
        gender.getSelectionModel().clearSelection();
        img_view.setImage(null);
        file_path.setText("Label");
        mobile.setText("");

    }

    public void print_rep()
    {
        try
        {
            connect=database.connectDB();
            JasperDesign jdesign= JRXmlLoader.load("src/main/resources/com/example/school_management/report_jasper/stud_"+"all"+".jrxml");
            String clastitle="Class: "+ccc;
            if(ccc.equals("all"))
            {
                clastitle="Class: All";
            }
            else
            {
                JRDesignQuery jq= new JRDesignQuery();
                jq.setText("SELECT * FROM student_data WHERE `class` = '"+ccc+"'");
                jdesign.setQuery(jq);

            }
            String filepath="src/main/resources/com/example/school_management/report_jasper/stud_all.jrxml";
            Map<String,Object> para=new HashMap<String,Object>();
            para.put("class_name",clastitle);

            JasperReport jreport= JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint= JasperFillManager.fillReport(jreport,para,connect);
            JasperViewer viewer= new JasperViewer(jprint,false);
            viewer.setTitle("Report");
            viewer.show();
        }
        catch (Exception e)
        {
            System.out.println("ok");
            //System.out.println(e);
        }


    }


    public void delete()
    {
        String sql="DELETE from student_data WHERE `id` ='"+id.getText()+"'";
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
            showData(ccc);
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

    public ObservableList<Student> datalist(String cls)
    {
        ObservableList<Student> datalist = FXCollections.observableArrayList();

        String sql;
        if(cls.equals("all")) sql ="SELECT * FROM student_data";
        else sql = "SELECT * FROM student_data WHERE `class` = '"+cls+"'";

        try {
            connect= database.connectDB();
            prepare = connect.prepareStatement(sql);
            result=prepare.executeQuery();


            while(result.next())
            {
                Student student= new Student (result.getInt("id"),result.getString("name"),result.getString("Class"),result.getString("gender"),result.getString("picture"),result.getString("mobile"));

                datalist.add(student);
            }

        }catch (Exception e) {
            System.out.println("student database error");
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
    public void showData(String Class)
    {
        ObservableList<Student> showlist = datalist(Class);
        id_table.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        class_table.setCellValueFactory(new PropertyValueFactory<>("Clas"));
        Gender_table.setCellValueFactory(new PropertyValueFactory<>("gender"));
        mobile_table.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        table_view.setItems(showlist);

    }
    public void ImgInsert()
    {
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
            System.out.println("student pic missing");
        }
    }
    public void insert()
    {

        String sql="INSERT INTO student_data VALUES (?,?,?,?,?,?,?)";

        try {
            connect= database.connectDB();
            if(id.getText().isEmpty() | name.getText().isEmpty() || Class.getSelectionModel().isEmpty() |
                    gender.getSelectionModel().isEmpty() | img_view.getImage()==null)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle(" Error!!!!!");
                alert.setHeaderText("Some fields are empty.  ");
                alert.setContentText("Please enter all blank fields. ");
                alert.showAndWait();
            }
            else
            {

                prepare=connect.prepareStatement(sql);
                prepare.setString(1,id.getText());
                prepare.setString(2,name.getText());
                prepare.setString(3, (String) Class.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) gender.getSelectionModel().getSelectedItem());
                prepare.setString(5,file_path.getText());
                prepare.setString(6,mobile.getText());
                prepare.setString(7,"Unknown");
                prepare.execute();
                System.out.println("ok12");
                showData(ccc);
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
    public void selectData()
    {
        Student studnt = table_view.getSelectionModel().getSelectedItem();
        int no=table_view.getSelectionModel().getSelectedIndex();
        if((no-1)<-1)
        {
            return;
        }
        id.setText(String.valueOf(studnt.getId()));
        name.setText(String.valueOf(studnt.getName()));
        //  gender.getSelectionModel().select(Integer.parseInt(data.getCurd_gender()));
        gender.getSelectionModel().clearSelection();
        //   class.getSelectionModel().select(Integer.parseInt(data.getCurd_class()));
        //class.getSelectionModel().clearSelection();
        Class.getSelectionModel().clearSelection();
        String pic="file:"+ studnt.getPicture();
        Image img= new Image(pic,110,110,false,true);
        img_view.setImage(img);

        String tmp= studnt.getPicture();

        file_path.setText(tmp);
        mobile.setText(String.valueOf(studnt.getMobile()));
    }
    public void update_Crud()
    {
        String tmp=file_path.getText();
        tmp=tmp.replace("\\","\\\\");
        String sql="UPDATE student_data SET `name`= '"+name.getText()+ "', `class` = '"+Class.getSelectionModel().getSelectedItem()
                +"', `gender` = '"+gender.getSelectionModel().getSelectedItem()+"', `picture` = '"+tmp+"', `mobile` = '"+mobile.getText()+"' WHERE id = '"+id.getText()+"'";
        try {
            connect= database.connectDB();
            if(id.getText().isEmpty() | name.getText().isEmpty() | Class.getSelectionModel().isEmpty() |
                    gender.getSelectionModel().isEmpty() | img_view.getImage()==null)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle(" Error!!!!!");
                alert.setHeaderText("Some fields are empty.  ");
                alert.setContentText("Please enter all blank fields. ");
                alert.showAndWait();
            }
            else
            {
                statement=connect.createStatement();
                statement.executeUpdate(sql);
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(" Update Successfull!!!");
                alert.setHeaderText("  ");
                alert.setContentText(" Successfully updated the data. ");
                alert.showAndWait();
                showData(ccc);
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

    public String ccc;
    public void click_but()
    {
        class_select_pane.setVisible(false);
        anchorpane.setVisible(true);
    }
    public void button_all()
    {
        click_but();
        ccc="all";
        showData(ccc);

    }
    public void button_one()
    {
        ccc="One";
        click_but();
        showData(ccc);
    }
    public void button_two()
    {
        ccc="Two";
        click_but();
        showData(ccc);
    }
    public void button_three()
    {
        ccc="Three";
        click_but();
        showData(ccc);
    }
    public void button_four()
    {
        click_but();
        ccc="Four";
        showData(ccc);
    }
    public void button_five()
    {
        ccc="Five";
        click_but();
        showData(ccc);
    }
    public void button_six()
    {
        ccc="Six";
        click_but();
        showData(ccc);
    }
    public void button_seven()
    {
        ccc="Seven";
        click_but();
        showData(ccc);
    }
    public void button_eight()
    {
        ccc="Eight";
        click_but();
        showData(ccc);
    }
    public void button_nine()
    {
        ccc="Nine";
        click_but();
        showData(ccc);
    }
    public void button_ten()
    {
        ccc="Ten";
        click_but();
        showData(ccc);
    }

    public void back_btn_click()
    {
        class_select_pane.setVisible(true);
        anchorpane.setVisible(false);

    }

    @Override
    public void initialize(URL uurl, ResourceBundle resourse)
    {
        Combo_box();


    }

}
