/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.modelKlasifikasi;
import Connection.koneksi;
import com.toedter.calendar.JYearChooser;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Our
 */
public class controlKlasifikasi {
    
    public void chooseFile(ActionEvent evt, JTextField txtDirektori, JTextField txtKNN, JLabel lblData, JButton btnKlasifikasi, JButton btnReset,JTable tblData) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Excel File", "xls", "xlsx");
            fileChooser.setFileFilter(fileNameExtensionFilter);

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                txtDirektori.setText(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println("Good, File Chooser runing well!");
                if (txtDirektori.getText().endsWith(".xls") || txtDirektori.getText().endsWith(".xlsx")) {
                    showOnTable(evt, txtDirektori, tblData);
                    lblData.setText(tblData.getRowCount() + " Data");
                    txtKNN.setEnabled(true);
                    txtKNN.requestFocus();
                    btnKlasifikasi.setEnabled(true);
                    btnReset.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "File dataset harus file spreadsheet dengan ekstensi *xls atau *.xlsx!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/com/smanempat/image/fail.png"));
                    txtDirektori.setText("");
                    chooseFile(evt, txtDirektori, txtKNN, lblData, btnKlasifikasi, btnReset, tblData);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showOnTable(ActionEvent evt, JTextField txtDirektori, JTable tablePreview) {
        try {
            if (txtDirektori.getText().endsWith(".xls")) {
                System.out.println("This File .XLS");
                showXLS(txtDirektori, tablePreview);
            } else if (txtDirektori.getText().endsWith(".xlsx")) {
                System.out.println("This File .XLSX");
                showXLSX(txtDirektori, tablePreview);
            } else {
                System.out.println("You must be choosing one of Excel file.");
            }
        } catch (Exception e) {
        }
    }
    
    private void showXLS(JTextField txtDirektori, JTable tblData) throws FileNotFoundException, IOException {
        DefaultTableModel tableModel = new DefaultTableModel();
        File fileName = new File(txtDirektori.getText());
        FileInputStream inputStream = new FileInputStream(fileName);
        HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workBook.getSheetAt(0);

        int rowValue = sheet.getLastRowNum() + 1;
        int colValue = sheet.getRow(0).getLastCellNum();
        String[][] data = new String[rowValue][colValue];
        String[] colName = new String[colValue];
        for (int i = 0; i < rowValue; i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < colValue; j++) {
                HSSFCell cell = row.getCell(j);
                int type = cell.getCellType();
                Object returnCellValue = null;
                if (type == 0) {
                    returnCellValue = cell.getNumericCellValue();
                } else if (type == 1) {
                    returnCellValue = cell.getStringCellValue();
                }

                data[i][j] = returnCellValue.toString();
            }
        }

        for (int i = 0; i < colValue; i++) {
            colName[i] = data[0][i];
        }

        tableModel = new DefaultTableModel(data, colName);
        tblData.setModel(tableModel);
        tableModel.removeRow(0);
    }
    
    private void showXLSX(JTextField txtDirektori, JTable tblData) throws FileNotFoundException, IOException {
        DefaultTableModel tableModel = new DefaultTableModel();
        File fileName = new File(txtDirektori.getText());
        FileInputStream inputStream = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rowValue = sheet.getLastRowNum() + 1;
        int colValue = sheet.getRow(0).getLastCellNum();
        String[][] data = new String[rowValue][colValue];
        String[] colName = new String[colValue];
        for (int i = 0; i < rowValue; i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < colValue; j++) {
                XSSFCell cell = row.getCell(j);
                int type = cell.getCellType();
                Object returnCellValue = null;
                if (type == 0) {
                    returnCellValue = cell.getNumericCellValue();
                } else if (type == 1) {
                    returnCellValue = cell.getStringCellValue();
                }

                data[i][j] = returnCellValue.toString();
            }
        }

        for (int i = 0; i < colValue; i++) {
            colName[i] = data[0][i];
        }

        tableModel = new DefaultTableModel(data, colName);
        tblData.setModel(tableModel);
        tableModel.removeRow(0);
    }
    
    public String[] processMining(JTextField txtKNN, JTable tblData, JLabel lblError, JTable tblDataLulus, JLabel labelSiswaLulus, JLabel labelSiswaTidakLulus, JLabel lblKeterangan, JTabbedPane jTabbedPane1) {

        String numberValidate = txtKNN.getText();
        modelKlasifikasi MK = new modelKlasifikasi();
        int rowCountModel = MK.getRowCount();
        int rowCountData = tblData.getRowCount();
        System.out.println("Jumlah Baris Data Uji : " + rowCountData);
        System.out.println("Jumlah Baris Data Model : " + rowCountModel);
        String[] knnValue = null;

        /*Validasi Nilai Number of Nearest Neighbor*/
        if (Pattern.matches("[0-9]+", numberValidate) == false && numberValidate.length() > 0) {
            lblError.setText("Number of Nearest Neighbor tidak valid");
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/fail.png"));
            txtKNN.requestFocus();
        } else if (numberValidate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/fail.png"));
            lblError.setText("Number of Nearest Neighbor tidak boleh kosong");
            txtKNN.requestFocus();
        } else if (Integer.parseInt(numberValidate) %2 != 1) {
            lblError.setText("Number of Nearest Neighbor tidak boleh genap ");
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh genap!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/fail.png"));
            txtKNN.requestFocus();
        } else if (Integer.parseInt(numberValidate) >= rowCountModel) {
            lblError.setText("Number of Nearest Neighbor tidak boleh lebih dari " + rowCountModel + "");
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh lebih dari " + rowCountModel + " !", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/fail.png"));
            txtKNN.requestFocus();
        } else {
            int confirm = 0;
            long startTime = System.currentTimeMillis();
            long endTime = 0;
            confirm = JOptionPane.showOptionDialog(null, "Yakin ingin memproses data?", "Proses Klasifikasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.OK_OPTION) {
                int kValue = Integer.parseInt(txtKNN.getText());
                String[][] modelValue = getModelValue(rowCountModel);
                double[][] dataValue = getDataValue(rowCountData, tblData);
                knnValue = getKNNValue(rowCountData, rowCountModel, modelValue, dataValue, kValue);
                endTime = System.currentTimeMillis();
                showClassificationResult(tblDataLulus, tblData, knnValue, rowCountData, labelSiswaLulus, labelSiswaTidakLulus, lblKeterangan, kValue);
                jTabbedPane1.setSelectedIndex(1);
            }
            long timeTotal = ((endTime - startTime));
            JOptionPane.showMessageDialog(null, "Waktu Proses Klasifikasi " + timeTotal / 1000 + "Detik", "Proses Selesai", JOptionPane.INFORMATION_MESSAGE);

        }
        return knnValue;
    }
    
    private String[][] getModelValue(int rowCountModel) {
        modelKlasifikasi MK = new modelKlasifikasi();
        MK.getData(rowCountModel);
        String[][] returnValue = new String[rowCountModel][10];
        String Mean_SMS1;
        String Mean_SMS2;
        String Mean_SMS3;
        String Mean_SMS4;
        String Mean_SMS5;
        String Mean_SMS6;
        
        String MTK1;
        String BINDO1;
        String BING1;
        String Peminatan1;
        String MTK2;
        String BINDO2;
        String BING2;
        String Peminatan2;
        
        String Keterangan;
        String Hasil;
        double transKeterangan = 0;
       
        try {
            koneksi dbkonek = new koneksi();
            Connection connect = dbkonek.konek1();
            Statement stm = connect.createStatement();
            String query = "SELECT * FROM Model_Siswa";
            ResultSet rs = stm.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                Mean_SMS1 = rs.getString("Mean_SMS1");
                Mean_SMS2 = rs.getString("Mean_SMS2");
                Mean_SMS3 = rs.getString("Mean_SMS3");
                Mean_SMS4 = rs.getString("Mean_SMS4");
                Mean_SMS5 = rs.getString("Mean_SMS5");
                Mean_SMS6 = rs.getString("Mean_SMS6");
                MTK1 = rs.getString("MTK1");
                BINDO1 = rs.getString("BINDO1");
                BING1 = rs.getString("BING1");
                Peminatan1 = rs.getString("Peminatan1");
                double Mean1 = (Double.parseDouble(MTK1) + Double.parseDouble(BINDO1) + Double.parseDouble(BING1) + Double.parseDouble(Peminatan1)) / 4; 
                MTK2 = rs.getString("MTK2");
                BINDO2 = rs.getString("BINDO2");
                BING2 = rs.getString("BING2");
                Peminatan2 = rs.getString("Peminatan2");
                double Mean2 = (Double.parseDouble(MTK2) + Double.parseDouble(BINDO2) + Double.parseDouble(BING2) + Double.parseDouble(Peminatan2)) / 4; 
                
                Keterangan = rs.getString("Keterangan");
                Hasil = rs.getString("Hasil");
                if (rs.getString("Keterangan").equals("LULUS")) {
                    transKeterangan = 1;
                } else if (rs.getString("Keterangan").equals("TIDAK")) {
                    transKeterangan = 0;
                }
                
                returnValue[i][0] = String.valueOf(Mean_SMS1);
                returnValue[i][1] = String.valueOf(Mean_SMS2);
                returnValue[i][2] = String.valueOf(Mean_SMS3);
                returnValue[i][3] = String.valueOf(Mean_SMS4);
                returnValue[i][4] = String.valueOf(Mean_SMS5);
                returnValue[i][5] = String.valueOf(Mean_SMS6);
                returnValue[i][6] = String.valueOf(Mean1);
                returnValue[i][7] = String.valueOf(Mean2);
                returnValue[i][8] = String.valueOf(transKeterangan);
                returnValue[i][9] = Hasil;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
     private double[][] getDataValue(int rowCountData, JTable tblData) {
        double[][] returnValue = new double[rowCountData][9];
        int transKeterangan = 0;
        for (int i = 0; i < rowCountData; i++) {
            if (tblData.getValueAt(i, 8).equals("LULUS")) {
                transKeterangan = 1;
            } else if (tblData.getValueAt(i, 8).equals("TIDAK")) {
                transKeterangan = 0;
            }//========================DARI MANA====================
            returnValue[i][0] = Double.parseDouble(tblData.getValueAt(i, 4).toString());//MEANSMS1
            returnValue[i][1] = Double.parseDouble(tblData.getValueAt(i, 5).toString());//MEANSMS2
            returnValue[i][2] = Double.parseDouble(tblData.getValueAt(i, 6).toString());//MEANSMS3
            returnValue[i][3] = Double.parseDouble(tblData.getValueAt(i, 7).toString());//MEANSMS4
            returnValue[i][4] = Double.parseDouble(tblData.getValueAt(i, 8).toString());//MEANSMS5
            returnValue[i][5] = Double.parseDouble(tblData.getValueAt(i, 9).toString());//MEANSMS6            
            returnValue[i][6] = (Double.parseDouble(tblData.getValueAt(i, 10).toString()) + Double.parseDouble(tblData.getValueAt(i, 11).toString()) + Double.parseDouble(tblData.getValueAt(i, 12).toString()) + Double.parseDouble(tblData.getValueAt(i, 13).toString())) / 4;//MEAN1           
            returnValue[i][7] = (Double.parseDouble(tblData.getValueAt(i, 14).toString()) + Double.parseDouble(tblData.getValueAt(i, 15).toString()) + Double.parseDouble(tblData.getValueAt(i, 16).toString()) + Double.parseDouble(tblData.getValueAt(i, 17).toString())) / 4;//MEAN2           
            
            returnValue[i][8] = transKeterangan;
           // returnValue[i][4] = (Double.parseDouble(tblData.getValueAt(i, 4).toString()) + Double.parseDouble(tblData.getValueAt(i, 5).toString()) + Double.parseDouble(tblData.getValueAt(i, 6).toString()) + Double.parseDouble(tblData.getValueAt(i, 7).toString())) / 4;
            //returnValue[i][2] = transKeterangan;
        }
        return returnValue;
    }
     
    private String[] getKNNValue(int rowCountData, int rowCountModel, String[][] modelValue, double[][] dataValue, int kValue) {
        DecimalFormat df = new DecimalFormat("00.0000000000");
        double[][] Distance = new double[rowCountData][rowCountModel];
        String[][] initClass = new String[rowCountData][rowCountModel];
        double[] sortDistance = new double[rowCountModel];
        String[] sortHasil = new String[rowCountModel];
        String[] nearestNeighbor = new String[kValue];
        String[] majorClass = new String[rowCountData];
        int onRow = 0;
        for (int i = 0; i < rowCountData; i++) {
            System.out.println("=======================================================================");
            System.out.println("Data Uji ke-\tData Model ke-\tManhattan Distance\t  Class Target");
            System.out.println("=======================================================================");
            for (int j = 0; j < rowCountModel; j++) {
                double Mean_SMS1 = Math.abs(Double.parseDouble(modelValue[j][0]) - dataValue[i][0]);
                double Mean_SMS2 = Math.abs(Double.parseDouble(modelValue[j][1]) - dataValue[i][1]);
                double Mean_SMS3 = Math.abs(Double.parseDouble(modelValue[j][2]) - dataValue[i][2]);
                double Mean_SMS4 = Math.abs(Double.parseDouble(modelValue[j][3]) - dataValue[i][3]);
                double Mean_SMS5 = Math.abs(Double.parseDouble(modelValue[j][4]) - dataValue[i][4]);
                double Mean_SMS6 = Math.abs(Double.parseDouble(modelValue[j][5]) - dataValue[i][5]);
                double Mean1 = Math.abs(Double.parseDouble(modelValue[j][6]) - dataValue[i][6]);
                double Mean2 = Math.abs(Double.parseDouble(modelValue[j][7]) - dataValue[i][7]);
                
                double Keterangan = Math.abs(Double.parseDouble(modelValue[j][8]) - dataValue[i][8]);
                double manhattanDistance = Mean_SMS1 + Mean_SMS2 + Mean_SMS3 + Mean_SMS4 + Mean_SMS5 + Mean_SMS6 + Mean1 + Mean2 + Keterangan;//=================BELUM PAHAM====================
                Distance[i][j] = manhattanDistance;
                initClass[i][j] = modelValue[j][9];

                System.out.print(i + "\t\t" + j + "\t\t" + df.format(Distance[i][j]) + "\t\t\t" + initClass[i][j]);
                System.out.println();
            }
            System.out.println("=======================================================================");
            System.out.println("Proses Selection Sorting...");
            selectionSorting(onRow, rowCountModel, initClass, Distance);
            System.out.println("=======================================================================");
            System.out.println("Manhattan Distance\tClass Target");
            System.out.println("=======================================================================");

            for (int k = 0; k < rowCountModel; k++) {
                sortDistance[k] = Distance[onRow][k];
                sortHasil[k] = initClass[onRow][k];
                System.out.print(df.format(sortDistance[k]) + "\t\t   ");
                System.out.println(sortHasil[k]);
            }
            System.out.println("=======================================================================");
            nearestNeighbor = getNearestNeighbor(kValue, sortHasil);
            majorClass[i] = getMajorClass(nearestNeighbor);
//            System.out.println("Data ke-'" + i + "' kelasnya, " + majorClass[i]);
            onRow++;
        }

        return majorClass;
    }
    
    private void selectionSorting(int onRow, int rowCountModel, String[][] initClass, double[][] Distance) {
        for (int i = 0; i < rowCountModel; i++) {
            double tempMin = Distance[onRow][i];
            String tempHasil = initClass[onRow][i];
            for (int j = i; j < rowCountModel; j++) {
                if (Distance[onRow][j] <= Distance[onRow][i]) {
                    /*Sorting Nilai Manhattan Distance*/
                    Distance[onRow][i] = Distance[onRow][j];
                    Distance[onRow][j] = tempMin;
                    tempMin = Distance[onRow][i];
                    /*Sorting Jurusan*/
                    initClass[onRow][i] = initClass[onRow][j];
                    initClass[onRow][j] = tempHasil;
                    tempHasil = initClass[onRow][i];
                }
            }
        }
    }
    
    private String[] getNearestNeighbor(int kValue, String[] sortHasil) {
        String[] returnvalue = new String[kValue];
        int i = 0;
        System.out.println("Get " + kValue + " Nearest Neighbor :");
        while (i < kValue) {
            returnvalue[i] = sortHasil[i];
            System.out.println(returnvalue[i]);
            i++;
        }
        return returnvalue;
    }
    
    private String getMajorClass(String[] nearestNeighbor) {
        String returnValue;
        int countLULUS = 0;
        int countTIDAK = 0;
        for (int i = 0; i < nearestNeighbor.length; i++) {
            if (nearestNeighbor[i].equals("LULUS")) {
                countLULUS = countLULUS + 1;
            } else if (nearestNeighbor[i].equals("TIDAK")) {
                countTIDAK = countTIDAK + 1;
            }
        }
        if (countLULUS >= countTIDAK) {
            returnValue = "LULUS";
        } else {
            returnValue = "TIDAK";
        }
        System.out.println("Jumlah Class Target LULUS = " + countLULUS);
        System.out.println("Jumlah Class Target TIDAK = " + countTIDAK);
        System.out.println("Hasil Akhir : " + returnValue + "\n");
        return returnValue;
    }
    
    public void showClassificationResult(JTable tblDataLulus, JTable tblData, String[] knnValue, int rowCountData, JLabel labelSiswaLulus, JLabel labelSiswaIPS, JLabel lblKeterangan, int kValue) {
        DefaultTableModel tableModelResult = new DefaultTableModel();
        tableModelResult = (DefaultTableModel) tblDataLulus.getModel();
        int jumlahSiswaLULUS = 0;
        int jumlahSiswaTIDAK = 0;

        for (int i = 0; i < rowCountData; i++) {
            if (knnValue[i].equals("LULUS")) {
                jumlahSiswaLULUS = jumlahSiswaLULUS + 1;
            } else if (knnValue[i].equalsIgnoreCase("TIDAK")) {
                jumlahSiswaTIDAK = jumlahSiswaTIDAK + 1;
            }

            String NIS = tblData.getValueAt(i, 0).toString();
            String NAMA = tblData.getValueAt(i, 1).toString();
            String JenisKelamin = tblData.getValueAt(i, 2).toString();
            String Jurusan = tblData.getValueAt(i, 3).toString();
            String Mean_SMS1 = tblData.getValueAt(i, 4).toString();
            String Mean_SMS2 = tblData.getValueAt(i, 5).toString();
            String Mean_SMS3 = tblData.getValueAt(i, 6).toString();
            String Mean_SMS4 = tblData.getValueAt(i, 7).toString();
            String Mean_SMS5 = tblData.getValueAt(i, 8).toString();
            String Mean_SMS6 = tblData.getValueAt(i, 9).toString();
            String MTK1 = tblData.getValueAt(i, 10).toString();
            String BINDO1 = tblData.getValueAt(i, 11).toString();
            String BING1 = tblData.getValueAt(i, 12).toString();
            String Peminatan1 = tblData.getValueAt(i, 13).toString();
            String MTK2 = tblData.getValueAt(i, 14).toString();
            String BINDO2 = tblData.getValueAt(i, 15).toString();
            String BING2 = tblData.getValueAt(i, 16).toString();
            String Peminatan2 = tblData.getValueAt(i, 17).toString();
           
            String Keterangan = tblData.getValueAt(i, 18).toString();
            String Hasil = knnValue[i];
            Object[] resultData = {NIS, NAMA, JenisKelamin, Jurusan, Mean_SMS1, Mean_SMS2, Mean_SMS3, Mean_SMS4, Mean_SMS5, Mean_SMS6, MTK1, BINDO1, BING1, Peminatan1, MTK2, BINDO2, BING2, Peminatan2, Keterangan, Hasil};
            tableModelResult.addRow(resultData);
        }
        tblDataLulus.setModel(tableModelResult);
        labelSiswaLulus.setText(jumlahSiswaLULUS + "");
        labelSiswaIPS.setText(jumlahSiswaTIDAK + "");
        lblKeterangan.setText("Hasil Klasifikasi Kelulusan Siswa dengan paramater K = " + kValue + " adalah sebagai berikut ");
    }
    
    public void saveResultFile(JTable tblDataLulus) throws IOException {
        JFileChooser dirChooser = new JFileChooser();
        dirChooser.setDialogTitle("Save as Excel File");
       String generateFileName = "Data Hasil Klasifikasi Kelulusan Siswa .xlsx";
        dirChooser.setSelectedFile(new File(generateFileName));
        int userSelection = dirChooser.showSaveDialog(null);
        int rowCountData = tblDataLulus.getRowCount();
        if (userSelection == dirChooser.APPROVE_OPTION) {
            File fileToSave = dirChooser.getSelectedFile();
            convertToExcel(tblDataLulus, fileToSave);
            JOptionPane.showMessageDialog(null, "Hasil klasifikasi kelulusan berhasil disimpan", "Penyimpanan Berhasil", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/com/smanempat/image/success.png"));
        }
    }
    
    public void convertToExcel(JTable tblDataLulus, File fileToSave) throws FileNotFoundException, IOException {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet();
        XSSFRow row;
        int rowCountData = tblDataLulus.getRowCount();

        Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
        data.put(1, new Object[]{"NIS", "NAMA", "JenisKelamin", "Jurusan", "Mean_SMS1", "Mean_SMS2",
            "Mean_SMS3", "Mean_SMS4", "Mean_SMS5", "Mean_SMS6", "MTK1", "BINDO1", "BING1", "Peminatan1",
            "MTK2", "BINDO2", "BING2", "Peminatan2","Keterangan", "Hasil"});
        for (int i = 0; i < rowCountData; i++) {
//            System.out.println(tableResult.getValueAt(i, 1));
            data.put((i + 2), new Object[]{tblDataLulus.getValueAt(i, 0), tblDataLulus.getValueAt(i, 1), tblDataLulus.getValueAt(i, 2),
                tblDataLulus.getValueAt(i, 3), tblDataLulus.getValueAt(i, 4), tblDataLulus.getValueAt(i, 5),
                tblDataLulus.getValueAt(i, 6), tblDataLulus.getValueAt(i, 7), tblDataLulus.getValueAt(i, 8),
                tblDataLulus.getValueAt(i, 9), tblDataLulus.getValueAt(i, 10), tblDataLulus.getValueAt(i, 11),
                tblDataLulus.getValueAt(i, 12), tblDataLulus.getValueAt(i, 13), tblDataLulus.getValueAt(i, 14),
                tblDataLulus.getValueAt(i, 15), tblDataLulus.getValueAt(i, 16), tblDataLulus.getValueAt(i, 17),
                tblDataLulus.getValueAt(i, 18), tblDataLulus.getValueAt(i, 19)});
        }

        //System.out.println("KeySet " + data.keySet());
        Set<Integer> keyID = data.keySet();
        int rowID = 0;
        for (Integer key : keyID) {
            row = sheet.createRow(rowID);
            Object[] tempData = data.get(key);
            rowID++;
            int cellID = 0;
            for (Object obj : tempData) {
                Cell cell = row.createCell(cellID);
                cell.setCellValue(obj.toString());
                cellID++;
            }
        }
        FileOutputStream out = new FileOutputStream(fileToSave);
        workBook.write(out);
        out.close();
        System.out.println(fileToSave + " Berhasil disimpan");
    }
    
    public void validasiNumberofNearest(java.awt.event.KeyEvent evt, JTextField txtKNN, JLabel lblError) {
        modelKlasifikasi MK = new modelKlasifikasi();
        String numberValidate = txtKNN.getText();
        int modelRow = MK.getRowCount();
        if (Pattern.matches("[0-9]+", numberValidate) == false && numberValidate.length() > 0) {
            evt.consume();
            lblError.setText("Number of Nearest Neighbor tidak valid");
        } else if (Integer.parseInt(numberValidate) % 2 != 1) {
            evt.consume();
            lblError.setText("Number of Nearest Neighbor tidak boleh genap");
        } else if (numberValidate.length() == 9) {
            evt.consume();
            lblError.setText("Number of Nearest Neighbor terlalu panjang");
        } else {
            lblError.setText("");
        }

    }
    
    public void showChart(JLabel jumlahSiswaLulus, JLabel jumlahSiswaTidakLulus, JLabel lblKeterangan) {
        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        barChartData.setValue(Integer.parseInt(jumlahSiswaLulus.getText()), "LULUS", "LULUS");
        barChartData.setValue(Integer.parseInt(jumlahSiswaTidakLulus.getText()), "TIDAK", "TIDAK");
        JFreeChart barchart = ChartFactory.createBarChart3D("Grafik Jumlah Siswa Yang Lulus dan Tidak  ", "Hasil", "Jumlah Siswa", barChartData, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plotBarChart = barchart.getCategoryPlot();
        ChartFrame chartFrame = new ChartFrame("Grafik Jumlah Siswa Yang Lulus dan Tidak", barchart, true);
        chartFrame.setVisible(true);
        chartFrame.setSize(700, 500);
        chartFrame.setLocationRelativeTo(null);
        plotBarChart.setRangeGridlinePaint(java.awt.Color.black);
        ChartPanel chartPanel = new ChartPanel(barchart);
    }
    
}
