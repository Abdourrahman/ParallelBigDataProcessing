package sample;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.users.FullAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import libsvm.svm_model;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;

public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public  void loadArffFile() throws DbxException{
        BufferedReader reader = null;

        try {

            URL url = this.getClass().getResource( "/datasetes/credit.arff" );

            ConverterUtils.DataSource source = new ConverterUtils.DataSource(url.getPath());

            Instances data = source.getDataSet();

            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

            // create Model
            LibSVM libsvmModel = new LibSVM();
            libsvmModel.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR, LibSVM.TAGS_KERNELTYPE));

            // train classifier
            libsvmModel.buildClassifier(data);

            svm_model model = getModel(libsvmModel);

            // get the indices of the support vectors in the training data
            // Note: this indices start count at 1 insteadof 0
            int[] indices = model.sv_indices;

            for (int i : indices) {
                Instance supportVector = data.instance(i - 1);
                System.out.println(i + ": " + supportVector);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws DbxException {

        Main c = new Main();
        c.loadArffFile();
        // this is what I want
        //launch(args);
    }
    public static svm_model getModel(LibSVM svm) throws IllegalAccessException, NoSuchFieldException {
        Field modelField = svm.getClass().getDeclaredField("m_Model");
        modelField.setAccessible(true);
        return (svm_model) modelField.get(svm);
    }

}
