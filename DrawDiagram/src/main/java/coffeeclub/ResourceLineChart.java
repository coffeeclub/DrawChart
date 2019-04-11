package coffeeclub;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;

public class ResourceLineChart {
    private DefaultCategoryDataset CPUDataset;
    private DefaultCategoryDataset MemoryDataset;

    private String resourceFile;

    public ResourceLineChart(String resourceFilePath) {
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
//                Date time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(array[0]);
                String[] values = array[1].split(",");
                CPUDataset.addValue(Double.parseDouble(values[0]), "cpu-" + category, i + "");
                MemoryDataset.addValue(Integer.parseInt(values[1]), "memory-" + category, i + "");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return false;
        } catch (IOException eio) {
            System.out.println("Read file failed");
            return false;
        }
        return true;

    }
}
