package coffeeclub;

import java.awt.*;
import java.io.*;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class DrawChart {

    public static void main(String[] args) throws IOException {

        //line chart with two category data
        ResourceLineChart lineChart = new ResourceLineChart(System.getProperty("user.dir") + "/performance");
        if (!lineChart.loadDataset("label1")) {
            System.out.println("Draw chart failed!");
            return;
        }

        lineChart.changeResourceFilePath(System.getProperty("user.dir") + "/performance2");
        if (!lineChart.loadDataset("label2")) {
            System.out.println("Draw chart failed!");
            return;
        }

        JFreeChart cpuLineChart = ChartFactory.createLineChart(
                "CPU",
                "Time",
                "CPU%",
                lineChart.getCPUDataSet(),
                PlotOrientation.VERTICAL,
                true, true, false);

        //set line color
        CategoryPlot plot = cpuLineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        File cpu = new File("cpuLine.jpeg");

        JFreeChart memoryLineChart = ChartFactory.createLineChart(
                "Memory",
                "Time",
                "Memory(KB)",
                lineChart.getMemoryDataSet(), PlotOrientation.VERTICAL,
                true, true, false);

        //set line color
        CategoryPlot plotMemory = memoryLineChart.getCategoryPlot();
        LineAndShapeRenderer rendererMemory = (LineAndShapeRenderer) plotMemory.getRenderer();
        rendererMemory.setSeriesPaint(0, Color.blue);
        File memory = new File("memoryLine.jpeg");

        //save files as pictures
        int width = 1000;
        int height = 600;
        ChartUtilities.saveChartAsJPEG(cpu, cpuLineChart, width, height);
        ChartUtilities.saveChartAsJPEG(memory, memoryLineChart, width, height);

        //Bar chart with only one category data
        ResourceBarChart barChart = new ResourceBarChart(System.getProperty("user.dir") + "/performance");
        if (!barChart.loadDataset("label1")) {
            System.out.println("Draw chart failed!");
            return;
        }

        JFreeChart memoryBarChart = ChartFactory.createBarChart(
                "Memory",
                "Time",
                "Memory(KB)",
                barChart.getMemoryDataSet(), PlotOrientation.VERTICAL,
                true, true, false);

        //set bar color
        memoryBarChart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(64, 140, 255));
        memoryBarChart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        BarRenderer renderer1 = (BarRenderer) memoryBarChart.getCategoryPlot().getRenderer();
        renderer1.setDrawBarOutline(false);
        renderer1.setShadowVisible(false);

        File memoryBar = new File("memoryBar.jpeg");
        ChartUtilities.saveChartAsJPEG(memoryBar, memoryBarChart, width, height);
    }
}
