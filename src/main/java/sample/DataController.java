package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import libsvm.svm_model;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by abdou on 3/15/17.
 */
public class DataController {



    private ObjectMapper mapper = new ObjectMapper();

    private String fileName;

    private String srcFileName;

    private ConverterUtils.DataSource sourceArff;

    private LibSVM libsvmModel = new LibSVM();

    /**
     *
     * @return the modal object of LibSVM
     */
    public LibSVM getLibsvmModel() {
        return libsvmModel;
    }

    /**
     *
     * @return name of the file that contain dataset
     */
    public String getSrcFileName() {
        return srcFileName;
    }

    /**
     *
     * @param srcFileName : name of the file that contain dataset
     */
    public DataController setArffSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
        return this;
    }

    /**
     *
     * @return object contain data with type Arff
     */
    public ConverterUtils.DataSource getSourceArff() {
        return sourceArff;
    }

    /**
     *
     * @param sourceArff : set the type of object that has type of Arrf file
     * @throws Exception
     */
    public DataController setSourceArff(ConverterUtils.DataSource sourceArff) throws Exception {
        this.sourceArff = new ConverterUtils.DataSource(this.getClass().
                getResource( "/datasetes/"+this.getSrcFileName()+".arff" ).getPath());
        return this;
    }

    /**
     *
     * @return the name of file that contain the Support vectors
     */
    public String getDatasetFileName() {
        return this.fileName;
    }

    /**
     *
     * @param fileName : set the name of file that contain the support vector
     */
    public DataController setDatasetFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     *
     * @param schema pass Name.class of the schema Class
     * @return <Type> it return the type of the schema class you pass to it
     * @throws IOException
     */
    public <Type> Type getJsonData(Class schema) throws IOException {
        return (Type) mapper.readValue(new File(this.getClass().
                getResource( "/json/"+this.getDatasetFileName()+".json" ).getPath()), schema);
    }

    /**
     *
     * @return the instance of dataset
     * @throws Exception
     */
    public Instances getDataset() throws Exception {
        return this.sourceArff.getDataSet();
    }

    /**
     * setting the class index in the dataset
     * @throws Exception
     */
    public DataController setClassIndex() throws Exception {
        if (this.getDataset().classIndex() == -1)
            this.getDataset().setClassIndex(this.getDataset().numAttributes() - 1);
        return this;
    }

    /**
     *
     * @return SVM Modal object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public svm_model getSvmModel() throws NoSuchFieldException, IllegalAccessException {
        return getModel(this.getLibsvmModel());
    }

    /**
     * training process with SVM classifier
     * @throws Exception
     */
    public DataController training() throws Exception {
        this.getLibsvmModel().buildClassifier(this.getDataset());
        return this;
    }

    /**
     *
     * @return Array of Support vectors Indices
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public int[] getSupportVectorInices() throws NoSuchFieldException, IllegalAccessException {
        return  this.getSvmModel().sv_indices;
    }

    public DataController generateSupportVectors(File file, Object result) throws Exception {

        this.mapper.writeValue(file, result);
        return this;
    }

    private static svm_model getModel(LibSVM svm) throws IllegalAccessException, NoSuchFieldException {
        Field modelField = svm.getClass().getDeclaredField("m_Model");
        modelField.setAccessible(true);
        return (svm_model) modelField.get(svm);
    }
    //Tasks
    // convert the data on arff file to json file ( I mean Support vectors )
    // first we must get the number of Attributes  code : supportVector.numAttributes()
    // second we must get the value of each Attr and put it on the json file
    // 3rd the comparison process
    // make an average for the similarities

    // create a class contains all the methods that are handling the json process and the comparison
    // name of class : DataController
        /*
        properties : ObjectMapper mapper, String FileName ( getDatasetFileName , setDatasetFileName ),url (setUrl,getUrl)
        methods : readFromeFile(),writeToFile(), setSupportVectors(svm_model model)
        */
}
