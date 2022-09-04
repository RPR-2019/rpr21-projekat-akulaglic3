package ba.unsa.etf.rpr.Jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class Report extends JFrame {
    private int buyerID;

    public Report(int buyerID) throws HeadlessException {
        this.buyerID = buyerID;
    }

    public void showReport(Connection conn) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/eHealthReport.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("reportsDirPath", reportsDir);
        parameters.put("buyer", buyerID);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }
}
