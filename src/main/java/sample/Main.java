package sample;

import com.dropbox.core.DbxException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    private  void loadArffFile() throws Exception {
       /* BufferedReader reader = null;

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
*/

        DataController dataControl = new DataController();
        dataControl.setDatasetFileName("credits")
                   .setClassIndex()
                   .setArffSrcFileName("credit")
                   .training()
                   .generateSupportVectors()




    }

    public static void main(String[] args) throws Exception {

        Main c = new Main();
        c.loadArffFile();
        // this is what I want
        //launch(args);
    }
}
