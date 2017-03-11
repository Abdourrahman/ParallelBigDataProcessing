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
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils;

import java.io.*;
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
            weka.classifiers.functions.LibSVM scheme = new weka.classifiers.functions.LibSVM();



            scheme.setOptions(weka.core.Utils.splitOptions("-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\""));

            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // setting class attribute

    }

    public static void main(String[] args) throws DbxException {


        Main c = new Main();

        c.loadArffFile();

/*        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            DbxRequestConfig config = new DbxRequestConfig("ParallelBigDataProcessing", "en_US");
            DbxClientV2 client = new DbxClientV2(config, prop.getProperty("ACCESS_TOKEN"));
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());
        } catch (IOException e) {
            e.printStackTrace();
        }
*/




        //launch(args);
    }
    public static Classifier getClassifierById(int id){
        Classifier c = null;
        if(id == 0){
            LibSVM sv = new LibSVM();
            sv.setSVMType(new SelectedTag(LibSVM.SVMTYPE_EPSILON_SVR,LibSVM.TAGS_SVMTYPE));
            sv.setCost(Math.pow(2, 2));
            sv.setGamma(Math.pow(2, 1));
            sv.setEps(0.00001);
            c=sv;
        }
        else if(id == 1){
            c = new LinearRegression();
        }

        return c;
    }
}
