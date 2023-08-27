package com.adolfosc.ui.resource;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author hectoradolfo
 */
public class InsetGrafic {

    private XYSeries oSeries;
    private XYSeriesCollection oDataset;
    
    public InsetGrafic(JPanel jpGrafica) {
        oSeries = new XYSeries("Musica");

        //valores de los puntos en HZ
        int year1 = Integer.parseInt("0");
        int year2 = Integer.parseInt("0");
        int year3 = Integer.parseInt("0");
        int year4 = Integer.parseInt("0");

        oSeries.add(1, year1);
        oSeries.add(2, year2);
        oSeries.add(3, year3);
        oSeries.add(4, year4);

        oDataset = new XYSeriesCollection();
        oDataset.addSeries(oSeries);
        //(x,y)
        //eje X, eje Y , colecion datos,parametros son de visualizacion
        JFreeChart oChart = ChartFactory.createXYLineChart("", "Seg", "Hz", oDataset,
                PlotOrientation.VERTICAL, true, false, false);
        ChartPanel oPanel = new ChartPanel(oChart);

        //agregar la grafica en el JPanel PanelGrafico(nombre del panel en JFrame
        jpGrafica.setLayout(new java.awt.BorderLayout());
        jpGrafica.add(oPanel);
        jpGrafica.validate();
    }
    
    
}
