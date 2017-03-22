package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.classifiers.functions.LibSVM;
import weka.core.Instance;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(this.getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    private  void loadArffFile() throws Exception {

        URL url = this.getClass().getClassLoader().getResource("json/credits.json");
        DataController dataControl = new DataController();
        dataControl.setArffSrcFileName("credit")
                   //set options
                   .setKernalType(LibSVM.KERNELTYPE_LINEAR)
                   .train();

        int[] indices = dataControl.SVM_Model.sv_indices;
        ObjectMapper mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ClassLoader classLoader = getClass().getClassLoader();

        ArrayNode outerArray = mapper.createArrayNode(); //your outer array
        mapper.writeValue(new File(classLoader.getResource("json/credits.json").getFile()), outerArray);
        for (int i : indices) {
            Instance supportVector = dataControl.dataSet.instance(i - 1);
            Credit example = new Credit();

            example.index = i;
            example.checking_status = supportVector.stringValue(0);
            example.duration = (int) supportVector.value(1);
            example.credit_history = supportVector.stringValue(2);
            example.purpose = supportVector.stringValue(3);
            example.credit_amount = (int) supportVector.value(4);
            example.savings_status = supportVector.stringValue(5);
            example.employment = supportVector.stringValue(6);
            example.installment_commitment = (int) supportVector.value(7);
            example.personal_status = supportVector.stringValue(8);
            example.other_parties = supportVector.stringValue(9);
            example.residence_since = (int) supportVector.value(10);
            example.property_magnitude = supportVector.stringValue(11);
            example.age = (int) supportVector.value(12);
            example.other_payment_plans = supportVector.stringValue(13);
            example.housing = supportVector.stringValue(14);
            example.existing_credits = (int) supportVector.value(15);
            example.job = supportVector.stringValue(16);
            example.num_dependents = (int) supportVector.value(17);
            example.own_telephone = supportVector.stringValue(18);
            example.foreign_worker = supportVector.stringValue(19);
            example.classe = supportVector.stringValue(20);
            outerArray.addPOJO(example);

        }
        mapper.writeValue(new File(classLoader.getResource("json/credits.json").getFile()), outerArray);
    }

    public static void main(String[] args) throws Exception {

       /* Main c = new Main();
        c.loadArffFile();
        */
        launch(args);
    }
}
