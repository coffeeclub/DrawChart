package coffeeclub;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResourceBarChart {
    private DefaultCategoryDataset CPUDataset;
    private DefaultCategoryDataset MemoryDataset;

    private String resourceFile;

    public ResourceBarChart(String resourceFilePath) {
        this.resourceFile = resourceFilePath;
        CPUDataset = new DefaultCategoryDataset();
        MemoryDataset = new DefaultCategoryDataset();
    }

    public CategoryDataset getCPUDataSet() {
        return CPUDataset;
    }

    public CategoryDataset getMemoryDataSet() {
        return MemoryDataset;
    }

    public void changeResourceFilePath(String resourceFilePath) {
        this.resourceFile = resourceFilePath;
    }

    public boolean loadDataset(String category) {

        File file = new File(resourceFile);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                String[] array = line.split(" >> ");
                Date time = new SimpleDateFormat("hh:mm:ss").parse(array[0]);
                String[] values = array[1].split(",");

                CPUDataset.addValue(Double.parseDouble(values[0]), "cpu-" + category, array[0]);
                MemoryDataset.addValue(Integer.parseInt(values[1]), "memory-" + category, array[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return false;
        } catch (IOException eio) {
            System.out.println("Read file failed");
            return false;
        }catch (ParseException pe){
            System.out.println("Parse date failed");
            return false;
        }
        return true;

    }
}
