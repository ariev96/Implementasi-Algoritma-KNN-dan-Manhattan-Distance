/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Connection.koneksi;
import Model.modelEvaluasiModel;
import Model.modelEvaluasiUji;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
/**
 *
 * @author Our
 */
public class controlEvaluasi {
    modelEvaluasiModel meModel;
    modelEvaluasiUji meUji;
    DefaultTableModel tableModel1;
    DefaultTableModel tableModel2;
    DefaultTableModel tableModelDataSet1;
    DefaultTableModel tableModelDataSet2;
    String query;
    String Hasil;
    koneksi dbkonek;
    Connection connect;
    Statement stm;
    ResultSet rs;
    PreparedStatement pstmt;
    private DecimalFormat df = new DecimalFormat("#.##");
    
    //menampilkan tahun ajaran
     public void showTahunAjaranModel(JTable tblTahunModel) {
        meModel = new modelEvaluasiModel();
       // meUji = new modelEvaluasiUji();
        try {
            tableModel1 = (DefaultTableModel) tblTahunModel.getModel();
             meModel.selectTahunAjaran();
            for (int i = 0; i < meModel.getTahunAjaran().size(); i++) {
                tableModel1.addRow(new Object[]{
                    meModel.getTahunAjaran().get(i), false
                });
               /* tableModel2.addRow(new Object[]{
                    meUji.getTahunAjaran().get(i), false
                });*/
            }
            tblTahunModel.setModel(tableModel1);
         
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     
         //menampilkan tahun ajaran
     public void showTahunAjaranUji(JTable tblTahunUji) {
      
        meUji = new modelEvaluasiUji();
        try {
        
            tableModel2 = (DefaultTableModel) tblTahunUji.getModel();
       
            meUji.selectTahunAjaranDataUji();
            for (int i = 0; i < meUji.getTahunAjaran().size(); i++) {
                tableModel2.addRow(new Object[]{
                    meUji.getTahunAjaran().get(i), false
                });
             
            }
          
            tblTahunUji.setModel(tableModel2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     
     
    //menampilkan dataset model
    public void showDataSetModel(JTable tblDatasetModel, JTable tblTahunModel, JLabel lbltotaldataModel) throws SQLException {
        int row;
        int transKeterangan;
        tableModelDataSet1 = (DefaultTableModel) tblDatasetModel.getModel();
        row = tableModelDataSet1.getRowCount();
        for (int i = 0; i < row; i++) {
            tableModelDataSet1.removeRow(0);
        }
        row = tblTahunModel.getRowCount();
        boolean checkList;
        for (int i = 0; i < row; i++) {
            checkList = Boolean.valueOf("" + tblTahunModel.getValueAt(i, 1));
            if (checkList == true) {
                dbkonek = new koneksi();
                connect = dbkonek.konek1();
                query = "SELECT * FROM Model_Siswa WHERE Tahun_Ajaran = ?";
                pstmt = connect.prepareStatement(query);
                pstmt.setString(1, tblTahunModel.getValueAt(i, 0).toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String NIS = rs.getString("NIS");
                    String NAMA = rs.getString("NAMA");
                    //String JenisKelamin = rs.getString("JenisKelamin");
                   // String Jurusan = rs.getString("Jurusan");

                    String Mean_SMS1 = rs.getString("Mean_SMS1");
                    String Mean_SMS2 = rs.getString("Mean_SMS2");
                    String Mean_SMS3 = rs.getString("Mean_SMS3");
                    String Mean_SMS4 = rs.getString("Mean_SMS4");
                    String Mean_SMS5 = rs.getString("Mean_SMS5");
                    String Mean_SMS6 = rs.getString("Mean_SMS6");
                   // double Mean1 = (Double.parseDouble(MTK1) + Double.parseDouble(BINDO1) + Double.parseDouble(BING1) + Double.parseDouble(Peminatan1)) / 4;
                    String MTK1 = rs.getString("MTK1");
                    String BINDO1 = rs.getString("BINDO1");
                    String BING1 = rs.getString("BING1");
                    String Peminatan1 = rs.getString("Peminatan1");
                    double Mean1 = (Double.parseDouble(MTK1) + Double.parseDouble(BINDO1) + Double.parseDouble(BING1) + Double.parseDouble(Peminatan1)) / 4;
                    String MTK2 = rs.getString("MTK2");
                    String BINDO2 = rs.getString("BINDO2");
                    String BING2 = rs.getString("BING2");
                    String Peminatan2 = rs.getString("Peminatan2");
                    double Mean2 = (Double.parseDouble(MTK2) + Double.parseDouble(BINDO2) + Double.parseDouble(BING2) + Double.parseDouble(Peminatan2)) / 4;
                    
//                    double Mean3 = (Mean2 + Double.parseDouble(Nilai_UMBN) + Double.parseDouble(Nilai_UAMBN)) / 3;
                    String Hasil = rs.getString("Hasil");
                    
                    String Keterangan = rs.getString("Keterangan");
                    if (Keterangan.equals("LULUS")) {
                        transKeterangan = 1;
                    } else {
                        transKeterangan = 0;
                    }

                    Object tableContent[] = {NIS, NAMA, Mean_SMS1, Mean_SMS2, Mean_SMS3, Mean_SMS4, Mean_SMS5, Mean_SMS6, MTK1, BINDO1, BING1, Peminatan1, Mean1, MTK2, BINDO2, BING2, Peminatan2, Mean2, Keterangan, transKeterangan, Hasil};
                    tableModelDataSet1.addRow(tableContent);
                }
            }
            tblDatasetModel.setModel(tableModelDataSet1);
            lbltotaldataModel.setText(tblDatasetModel.getRowCount() + " Data");
        }
    }
    
    //menampilkan dataset uji
    public void showDataSetTest(JTable tblDatasetUji, JTable tblTahunUji, JLabel lbltotaldatauji) throws SQLException {
        int row;
        int transKeterangan;
        //modelEvaluation = new ModelEvaluation();
        tableModelDataSet2 = (DefaultTableModel) tblDatasetUji.getModel();
        //tempArray = new ArrayList<String>();
        row = tableModelDataSet2.getRowCount();
        for (int i = 0; i < row; i++) {
            tableModelDataSet2.removeRow(0);
        }
        row = tblTahunUji.getRowCount();
        boolean checkList;
        for (int i = 0; i < row; i++) {
            checkList = Boolean.valueOf("" + tblTahunUji.getValueAt(i, 1));
         
           if (checkList == true) {
                dbkonek = new koneksi();
                connect = dbkonek.konek1();
                query = "SELECT * FROM Uji_Siswa WHERE Tahun_Ajaran = ?";
                pstmt = connect.prepareStatement(query);
                pstmt.setString(1, tblTahunUji.getValueAt(i, 0).toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String NIS = rs.getString("NIS");
                    String NAMA = rs.getString("NAMA");
                  //  String JenisKelamin = rs.getString("JenisKelamin");
                    String Mean_SMS1 = rs.getString("Mean_SMS1");
                    String Mean_SMS2 = rs.getString("Mean_SMS2");
                    String Mean_SMS3 = rs.getString("Mean_SMS3");
                    String Mean_SMS4 = rs.getString("Mean_SMS4");
                    String Mean_SMS5 = rs.getString("Mean_SMS5");
                    String Mean_SMS6 = rs.getString("Mean_SMS6");
                    String MTK1 = rs.getString("MTK1");
                    String BINDO1 = rs.getString("BINDO1");
                    String BING1 = rs.getString("BING1");
                    String Peminatan1 = rs.getString("Peminatan1");
                    double Mean1 = (Double.parseDouble(MTK1) + Double.parseDouble(BINDO1) + Double.parseDouble(BING1) + Double.parseDouble(Peminatan1)) / 4;
                    String MTK2 = rs.getString("MTK2");
                    String BINDO2 = rs.getString("BINDO2");
                    String BING2 = rs.getString("BING2");
                    String Peminatan2 = rs.getString("Peminatan2");
                    double Mean2 = (Double.parseDouble(MTK2) + Double.parseDouble(BINDO2) + Double.parseDouble(BING2) + Double.parseDouble(Peminatan2)) / 4;
                    
                                      
                   // String Nilai_Raport = rs.getString("Nilai_Raport");
                   // double Mean3 = (Double.parseDouble(MTK1) + Double.parseDouble(BINDO1) + Double.parseDouble(BING1)+ Double.parseDouble(Peminatan1)) / 4;
                    String Keterangan = rs.getString("Keterangan");
                    if (Keterangan.equals("LULUS")) {
                        transKeterangan = 1;
                    } else {
                        transKeterangan = 0;
                    }
                    //Hasil = rs.getString("hasil");
                     Object tableContent[] = {NIS, NAMA, Mean_SMS1, Mean_SMS2, Mean_SMS3, Mean_SMS4, Mean_SMS5, Mean_SMS6, MTK1, BINDO1, BING1, Peminatan1, Mean1, MTK2, BINDO2, BING2, Peminatan2, Mean2, Keterangan, transKeterangan};
                  
                    tableModelDataSet2.addRow(tableContent);
                }
            }
            tblDatasetUji.setModel(tableModelDataSet2);
            lbltotaldatauji.setText(tblDatasetUji.getRowCount() + " Data");
        }
    }
    
    /*Method untuk mengambil nilai dataset model*/
    private String[][] getModelValue(int rowCountModel, JTable tblDatasetModel) {
        String[][] returnValue = new String[rowCountModel][10];
        //System.out.println(tableModelDataSet1.getValueAt(0, 2));
        // System.out.println("Ini Data Set Model");
        for (int i = 0; i < rowCountModel; i++) {
            returnValue[i][0] = tblDatasetModel.getValueAt(i, 2).toString();//mean_SMS1
            returnValue[i][1] = tblDatasetModel.getValueAt(i, 3).toString();//mean_SMS2
            returnValue[i][2] = tblDatasetModel.getValueAt(i, 4).toString();//menaSMS3
            returnValue[i][3] = tblDatasetModel.getValueAt(i, 5).toString();//mean4
            returnValue[i][4] = tblDatasetModel.getValueAt(i, 6).toString();//SMS5
            returnValue[i][5] = tblDatasetModel.getValueAt(i, 7).toString();//SMS6
           
            returnValue[i][6] = tblDatasetModel.getValueAt(i, 12).toString();//Mean1 
            returnValue[i][7] = tblDatasetModel.getValueAt(i, 17).toString();//Mean2 
            returnValue[i][8] = tblDatasetModel.getValueAt(i, 19).toString();//transket 
            returnValue[i][9] = tblDatasetModel.getValueAt(i, 20).toString();//hasil 
        }
        /// System.out.println("======================================");
        return returnValue;
    }
    
    /*Method untuk mengambil nilai dataset uji*/
    private double[][] getTestvalue(int rowCountTest, JTable tblDatasetUji) {
        double[][] returnValue = new double[rowCountTest][9];
        //System.out.println("Ini Data Set Testing");
        for (int i = 0; i < rowCountTest; i++) {
            returnValue[i][0] = Double.parseDouble(tblDatasetUji.getValueAt(i, 2).toString());//meanSMS1
            returnValue[i][1] = Double.parseDouble(tblDatasetUji.getValueAt(i, 3).toString());//meanSMS2
            returnValue[i][2] = Double.parseDouble(tblDatasetUji.getValueAt(i, 4).toString());//meanSMS3
            returnValue[i][3] = Double.parseDouble(tblDatasetUji.getValueAt(i, 5).toString());//meanSMS4
            returnValue[i][4] = Double.parseDouble(tblDatasetUji.getValueAt(i, 6).toString());//SMS5
            returnValue[i][5] = Double.parseDouble(tblDatasetUji.getValueAt(i, 7).toString());//SMS6
            returnValue[i][6] = Double.parseDouble(tblDatasetUji.getValueAt(i, 12).toString());//MEAN1
            returnValue[i][7] = Double.parseDouble(tblDatasetUji.getValueAt(i, 17).toString());//MEAN2
            returnValue[i][8] = Double.parseDouble(tblDatasetUji.getValueAt(i, 19).toString());//transket
            
          
        }
        //  System.out.println("======================================");
        return returnValue;
    }
    
    /*Method untuk melakukan proses mining+klasifikasi+pengujian+evaluasi*/
    public void proccessMining(JTable tblDatasetModel, JTable tblDatasetUji, JTextField txtKNN,
            JLabel lblerror, JTabbedPane jTabbedPane1, JTable tblResult, JTable tblCM,
            JTable tblTahunUji, JLabel lblAkurasi, JLabel lblNilaiK, JLabel lblPrecision, JLabel lblRecall, JPanel panelChart, JPanel panelChart1,
            JPanel panelChart2, JRadioButton singleTesting, JRadioButton multiTesting, JTextArea txtArea) throws SQLException {
        txtArea.setText("");
        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        meModel = new modelEvaluasiModel();
        int rowCountModel = tblDatasetModel.getRowCount();
        int rowCountTest = tblDatasetUji.getRowCount();
        int[] tempK;
        double[][] tempEval;
        double[][] evalValue;
        boolean valid = false;

        /*Validasi Dataset Model dan Dataset Uji*/
        if (rowCountModel == 0) {
            JOptionPane.showMessageDialog(null, "Pilih dataset model terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
            txtKNN.requestFocus();
        } else if (rowCountTest == 0) {
            JOptionPane.showMessageDialog(null, "Pilih dataset uji terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
            txtKNN.requestFocus();
        } else {
            valid = true;
        }
        /*Validasi Dataset Model dan Dataset Uji*/

        if (valid == true) {
            if (multiTesting.isSelected()) {
                String iterasi = JOptionPane.showInputDialog("Input Jumlah Iterasi Pengujian :");
                boolean validMulti = false;

                if (iterasi != null) {

                    /*Validasi Jumlah Iterasi*/
                    if (Pattern.matches("[0-9]+", iterasi) == false && iterasi.length() > 0) {
                        JOptionPane.showMessageDialog(null, "Nilai iterasi tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    } else if (iterasi.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nilai iterasi tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    } else if (iterasi.length() == 9) {
                        JOptionPane.showMessageDialog(null, "Nilai iterasi terlalu panjang!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    } else {
                        validMulti = true;
                        System.out.println("valiMulti = " + validMulti + " Kok");
                    }
                    /*Validasi Jumlah Iterasi*/
                }

                if (validMulti == true) {
                    tempK = new int[Integer.parseInt(iterasi)];
                    evalValue = new double[3][tempK.length];
                    for (int i = 0; i < Integer.parseInt(iterasi); i++) {
                        validMulti = false;
                        String k = JOptionPane.showInputDialog("Input Nilai Nearest Neighbor (k) ke " + (i + 1) + " :");
                        if (k != null) {
                            /*Validasi Nilai K Tiap Iterasi*/
                            if (Pattern.matches("[0-9]+", k) == false && k.length() > 0) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                            } else if (k.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                            } else if (Integer.parseInt(k) % 2 != 1) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) tidak boleh genap!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                            } else if (k.length() == 9) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) terlalu panjang!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                            } else {
                                validMulti = true;
                            }
                            /*Validasi Nilai K Tiap Iterasi*/
                        }

                        if (validMulti == true) {
                            tempK[i] = Integer.parseInt(k);
                            System.out.println(tempK[i]);
                        } else {
                            break;
                        }
                    }

                    if (validMulti == true) {
                        long startTime = 0;
                        long endTime = System.currentTimeMillis();
                        for (int i = 0; i < tempK.length; i++) {
                            int kValue = tempK[i];
                            String[][] modelValue = getModelValue(rowCountModel, tblDatasetModel);
                            double[][] testValue = getTestvalue(rowCountTest, tblDatasetUji);
                            String[] knnValue = getKNNValue(rowCountModel, rowCountTest, modelValue, testValue, kValue);
                            tempEval = evaluationModel(tblResult, tblCM, lblAkurasi, lblNilaiK, lblPrecision, lblRecall, txtKNN,tblTahunUji, tblDatasetUji, knnValue, i, tempK, panelChart);
                            //Menampung nilai Accuracy
                            evalValue[0][i] = tempEval[0][i];
                            //Menampung nilai Recall
                            evalValue[1][i] = tempEval[1][i];
                            //Menampung nilai Precision
                            evalValue[2][i] = tempEval[2][i];
                            jTabbedPane1.setSelectedIndex(1);
                            //txtArea.setSize(1500, 500);
                            //txtArea.setPreferredSize(new Dimension(500,500));
                            txtArea.append("Tingkat Keberhasilan Sistem dengan Nilai Number of Nearest Neighbor (K) = " + tempK[i] + "\n");
                            txtArea.append("Akurasi\t\t: " + evalValue[0][i] * 100 + " %\n");
                            txtArea.append("Recall\t\t: " + evalValue[1][i] * 100 + " %\n");
                            txtArea.append("Precision\t: " + evalValue[2][i] * 100 + " %\n");
                            txtArea.append("==============================================================================================================================\n");
                            //endTime = System.currentTimeMillis();
                            
                        }
                        double timeTotal = (endTime - startTime) / 1000;
                        txtArea.append("Total Waktu : " + timeTotal + "Detik");
                        showChart(tempK, evalValue, panelChart, panelChart1, panelChart2);
                    }
                }
            } else if (singleTesting.isSelected()) {
                boolean validSingle = false;
                String k = txtKNN.getText();
                int nilaiK = 0;
                evalValue = new double[3][1];

                /*Validasi Nilai Number of Nearest Neighbor*/
                if (Pattern.matches("[0-9]+", k) == false && k.length() > 0) {
                    lblerror.setText("Number of Nearest Neighbor tidak valid");
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    txtKNN.requestFocus();
                } else if (k.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    lblerror.setText("Number of Nearest Neighbor tidak boleh kosong");
                    txtKNN.requestFocus();
                } else if (Integer.parseInt(k)%2 != 1) {
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh genap!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    lblerror.setText("Number of Nearest Neighbor tidak boleh genap");
                    txtKNN.requestFocus();
                } else if (rowCountModel == 0 && Integer.parseInt(k) >= rowCountModel) {
                    JOptionPane.showMessageDialog(null, "Pilih dataset model terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    txtKNN.requestFocus();
                } else if (rowCountTest == 0 && Integer.parseInt(k) >= rowCountTest) {
                    JOptionPane.showMessageDialog(null, "Pilih dataset uji terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    txtKNN.requestFocus();
                } else if (Integer.parseInt(k) >= rowCountModel) {
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh lebih dari " + rowCountModel + " !", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/fail.png")));
                    txtKNN.requestFocus();
                } else {
                    validSingle = true;
                    nilaiK = Integer.parseInt(k);
                }
                /*Validasi Nilai Number of Nearest Neighbor*/

                if (validSingle == true) {
                    int confirm;
                    int i = 0;
                    long startTime = System.currentTimeMillis();
                    long endTime;
                    double timeTotal = 0;
                    confirm = JOptionPane.showOptionDialog(null, "Yakin ingin memproses data?", "Proses Klasifikasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == JOptionPane.OK_OPTION) {

                        int kValue = Integer.parseInt(txtKNN.getText());
                        String[][] modelValue = getModelValue(rowCountModel, tblDatasetModel);
                        double[][] testValue = getTestvalue(rowCountTest, tblDatasetUji);
                        String[] knnValue = getKNNValue(rowCountModel, rowCountTest, modelValue, testValue, kValue);
                        tempEval = evaluationModel(tblResult, tblCM, lblAkurasi, lblNilaiK, lblPrecision, lblRecall, txtKNN, tblTahunUji, tblDatasetUji, knnValue, nilaiK, panelChart);
                        evalValue[0][i] = tempEval[0][0];
                        evalValue[1][i] = tempEval[0][0];
                        evalValue[2][i] = tempEval[1][0];
                        jTabbedPane1.setSelectedIndex(1);
                        endTime = System.currentTimeMillis();
                        timeTotal = (endTime - startTime);
                        System.out.println("Total Waktu : " + timeTotal / 1000);
                    }
                    System.out.println("Controller.ControlEvaluasi.proccessMining()process");
                    showChart(nilaiK, evalValue, panelChart, panelChart1, panelChart2);
                    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                    JOptionPane.showMessageDialog(null, "Waktu Pengujian : " + timeTotal /1000 + "Detik", "Pengujian Selesai", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

    }
    
    /*Proses Utama Algoritma K-Nearest Neighbor*/
    private String[] getKNNValue(int rowCountModel, int rowCountTest, String[][] modelValue, double[][] testValue, int kValue) {
        double[][] Distance = new double[rowCountTest][rowCountModel];
        String[][] initClass = new String[rowCountTest][rowCountModel];
        double[] sortDistance = new double[rowCountModel];
        String[] sortHasil = new String[rowCountModel];
        String[] nearestNeighbor = new String[kValue];
        String[] majorClass = new String[rowCountTest];
        System.out.println("rowCountModel " + rowCountModel);
        int onRow = 0;
        for (int i = 0; i < rowCountTest; i++) {
            for (int j = 0; j < rowCountModel; j++) {
                double Mean_SMS1 = Math.abs(Double.parseDouble(modelValue[j][0]) - testValue[i][0]);
                double Mean_SMS2 = Math.abs(Double.parseDouble(modelValue[j][1]) - testValue[i][1]);
                double Mean_SMS3 = Math.abs(Double.parseDouble(modelValue[j][2]) - testValue[i][2]);
                double Mean_SMS4 = Math.abs(Double.parseDouble(modelValue[j][3]) - testValue[i][3]);
                double Mean_SMS5 = Math.abs(Double.parseDouble(modelValue[j][4]) - testValue[i][4]);
                double Mean_SMS6 = Math.abs(Double.parseDouble(modelValue[j][5]) - testValue[i][5]);
                double Mean1 = Math.abs(Double.parseDouble(modelValue[j][6]) - testValue[i][6]);
                double Mean2 = Math.abs(Double.parseDouble(modelValue[j][7]) - testValue[i][7]);
                
                double Keterangan = Math.abs(Double.parseDouble(modelValue[j][8]) - testValue[i][8]);
                double manhattanDistance = Mean_SMS1 + Mean_SMS2 + Mean_SMS3 + Mean_SMS4 + Mean_SMS5 + Mean_SMS6 + Mean1 + Mean2 + Keterangan;//=================BELUM PAHAM====================
                Distance[i][j] = manhattanDistance;
                initClass[i][j] = modelValue[j][9];
//                System.out.println("Jarak antara data ke-" + i + " dan ke-" + j + " adalah " + Distance[i][j]);
            }
//            System.out.println("===================================================================================================================================================================");
            selectionSorting(rowCountModel, Distance, initClass, onRow);
//            System.out.println("Sorting...");
            for (int k = 0; k < rowCountModel; k++) {
                sortDistance[k] = Distance[onRow][k];
                sortHasil[k] = initClass[onRow][k];
//                System.out.println("jarak setelah di sorting " + sortDistance[k]);
//                System.out.println("jurusan setelah di sorting " + sortJurusan[k]);
                //System.out.println(sortValue[k] + "'\t\t'" + sortJurusan[k]);
            }
            onRow++;
            nearestNeighbor = getNearestNeighbor(kValue, sortHasil);
            majorClass[i] = votingMajorClass(nearestNeighbor);
//            System.out.println("Kesimpulannya, Data Ke'" + i + "' kelasnya adalah : " + majorClass[i]);
        }
        return majorClass;
    }
    
    /*Sub-Proses dari KNN, mensorting secara ASC hasil perhitungan Manhattan distance*/
    private void selectionSorting(int rowCountModel, double[][] Distance, String[][] initClass, int onRow) {
        for (int i = 0; i < rowCountModel; i++) {
            double tempMin = Distance[onRow][i];
            String tempHasil = initClass[onRow][i];
            for (int j = i; j < rowCountModel; j++) {
                if (Distance[onRow][j] <= Distance[onRow][i]) {
                    /*Sorting Nilai Ecludian Distance*/
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
    
    /*Sub-Proses dari KNN, mengambil k nilai(tetangga terdekat)*/
    private String[] getNearestNeighbor(int kValue, String[] sortHasil) {
        String[] returnValue = new String[kValue];
        for (int i = 0; i < kValue; i++) {
            returnValue[i] = sortHasil[i];
        }
//        System.out.println("Jumlah '" + kValue + "' Tetangga Terdekat adalah:\n");
        int b = 0;
        while (b < kValue) {
//            System.out.println(returnValue[b]);
            b++;
        }
        return returnValue;
    }
    
    /*Sub-Proses dari KNN, memperoleh/memvoting kelas terbanyak*/
    private String votingMajorClass(String[] nearestNeighbor) {
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

        return returnValue;
    }
    
    /*Method untuk memperoleh kelas/target dari dataset uji untuk dibandingkan dengan hasil klasifikasi*/
    private String[] getHasilTest(JTable tblTahunUji, JTable tblDatasetUji) throws SQLException {
        String[] returnValue = new String[tblDatasetUji.getRowCount()];
        int row;
        row = tblTahunUji.getRowCount();
        boolean checkList;
        int j = 0;
        for (int i = 0; i < row; i++) {
            checkList = Boolean.valueOf("" + tblTahunUji.getValueAt(i, 1));
            if (checkList == true) {
                dbkonek = new koneksi();
                connect = dbkonek.konek1();
                query = "SELECT * FROM Uji_Siswa WHERE Tahun_Ajaran = ?";
                pstmt = connect.prepareStatement(query);
                pstmt.setString(1, tblTahunUji.getValueAt(i, 0).toString());
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    returnValue[j] = rs.getString("Keterangan");
                    j++;
                }
            }
        }
        return returnValue;
    }
    
    /*Method untuk mendapatkan nilai hasil evaluasi. Recall, Precision, dan Accuracy*/
    private double[][] evaluationModel(JTable tblResult, JTable tblCM, JLabel lblAkurasi, JLabel lblNilaiK , JLabel lblPrecision, JLabel lblRecall, JTextField txtKNN, JTable tblTahunUji, JTable tblDatasetUji, String[] knnValue, int i, int[] tempK, JPanel panelChart) throws SQLException {
        int actLULUS = 0;
        int actTIDAK = 0;
        int trueLULUS = 0;
        int falseLULUS = 0;
        int trueTIDAK = 0;
        int falseTIDAK = 0;

        String k = txtKNN.getText();
        
        double totPrediksiLULUS;
        double recLULUS;
        double recTIDAK;
        double preLULUS;
        double totPrediksiTIDAK;
        double accuracy;

        DefaultTableModel tableModelRes = new DefaultTableModel();
        tableModelRes = (DefaultTableModel) tblResult.getModel();
        String[] tempHasil = getHasilTest(tblTahunUji, tblDatasetUji);

        for (int j = 0; j < tblDatasetUji.getRowCount(); j++) {
            String nis = tblDatasetUji.getValueAt(j, 0).toString();
            String hasil = tempHasil[j];
            String classified = knnValue[j];
            Object[] tableContent = {nis, hasil, classified};
            tableModelRes.addRow(tableContent);
            tblResult.setModel(tableModelRes);
        }

        /*Hitung Jumlah Data Actual Lulus dan Tidak*/
        for (int j = 0; j < tempHasil.length; j++) {
            if (tempHasil[j].equals("LULUS")) {
                actLULUS = actLULUS + 1;
            } else if (tempHasil[j].equals("TIDAK")) {
                actTIDAK = actTIDAK + 1;
            }
        }

        /*Hitung Jumlah Data Classified Lulus dan tidak*/
        for (int j = 0; j < knnValue.length; j++) {
            if (tblResult.getValueAt(j, 1).equals("LULUS")) {
                if (tblResult.getValueAt(j, 1).equals(tblResult.getValueAt(j, 2))) {
                    trueLULUS = trueLULUS + 1;
                } else {
                    falseTIDAK = falseTIDAK + 1;
                }
            } else if (tblResult.getValueAt(j, 1).equals("TIDAK")) {
                if (tblResult.getValueAt(j, 1).equals(tblResult.getValueAt(j, 2))) {
                    trueTIDAK = trueTIDAK + 1;
                } else {
                    falseLULUS = falseLULUS + 1;
                }
            }
        }


        /*Hitung Nilai Recall, Precision, dan Accuracy*/
        totPrediksiLULUS = (double) trueLULUS +falseLULUS;
        preLULUS = (double) trueLULUS / (trueLULUS + falseLULUS);//trueLULUS / (trueLULUS + falseLULUS);
        totPrediksiTIDAK = (double) trueTIDAK + falseTIDAK;//trueTIDAK / (trueTIDAK + falseTIDAK);
        recLULUS = (double) trueLULUS / (trueLULUS + falseTIDAK);
        recTIDAK = (double) trueTIDAK / (trueTIDAK + falseLULUS);
        accuracy = (double) (trueLULUS + trueTIDAK) / (trueLULUS + trueTIDAK + falseLULUS + falseTIDAK);

        /*Tampung Nilai Recall, Precision, dan Accuracy*/
        double[][] tempEval = new double[3][tempK.length];
        tempEval[0][i] = accuracy;
        tempEval[1][i] = recLULUS;
        tempEval[2][i] = preLULUS;


        /*Set Nilai TF, TN, FP, FN ke Tabel Confusion Matrix*/
        tblCM.setValueAt("Actual LULUS", 0, 0);
        tblCM.setValueAt("Actual TIDAK", 1, 0);
        tblCM.setValueAt("Class Precision", 2, 0);
        tblCM.setValueAt(trueLULUS, 0, 1);
        tblCM.setValueAt(falseTIDAK, 0, 2);
        tblCM.setValueAt(falseLULUS, 1, 1);
        tblCM.setValueAt(trueTIDAK, 1, 2);

        /*Set Nilai Recall, Precision, dan Accuracy ke Tabel Confusion Matrix*/
        if (Double.isNaN(totPrediksiLULUS)) {
            tblCM.setValueAt("NaN", 2, 1);
        } else {
            tblCM.setValueAt(df.format(totPrediksiLULUS) , 2, 1);
        }
        if (Double.isNaN(totPrediksiTIDAK)) {
            tblCM.setValueAt("NaN" , 2, 2);
        } else {
            tblCM.setValueAt(df.format(totPrediksiTIDAK), 2, 2);
        }
    
        if (Double.isNaN(accuracy)) {
            lblAkurasi.setText("Akurasi : " + "NaN" + " %");
        } else {
            lblAkurasi.setText("Akurasi    :  " + df.format(accuracy * 100) + " %");
            lblNilaiK.setText("Tingkat Keberhasilan Sistem dengan Nilai (K) = "+k);
          }
        if (Double.isNaN(preLULUS)){
            lblNilaiK.setText("Precision : " + "NaN" + "%");
        } else {
            lblPrecision.setText("Presicion :  " + df.format(preLULUS * 100) + "%");
        }
         if (Double.isNaN(recLULUS)){
             lblRecall.setText("Recall      :  " + "NaN" + "%");
        } else {
            lblRecall.setText("Recall      :  " + df.format(recLULUS * 100)+ "%");
        }
        

        tableModelRes.setRowCount(0);

        return tempEval;
    }
    
    private double[][] evaluationModel(JTable tblResult, JTable tblCM, JLabel lblAkurasi, JLabel lblNilaiK, JLabel lblPrecision, JLabel lblRecall, JTextField txtKNN, JTable tblTahunUji, JTable tableDatasetUji, String[] knnValue, int nilaiK, JPanel panelChart) throws SQLException {
        int actLULUS = 0;
        int actTIDAK = 0;
        int trueLULUS = 0;
        int falseLULUS = 0;
        int trueTIDAK = 0;
        int falseTIDAK = 0;
        int classLULUS = 0;
        int classTIDAK = 0;

        String k = txtKNN.getText();
        
        double totPrediksiLULUS;
        double recLULUS;
        double recTIDAK;
        double preLULUS;
        double totPrediksiTIDAK;
        double accuracy;

        DefaultTableModel tableModelConf = (DefaultTableModel) tblCM.getModel();
        DefaultTableModel tableModelRes = (DefaultTableModel) tblResult.getModel();
        String[] tempHasil = getHasilTest(tblTahunUji, tableDatasetUji);

        if (tableModelRes.getRowCount() != 0) {
            tableModelRes.setRowCount(0);
        }

        for (int j = 0; j < tableDatasetUji.getRowCount(); j++) {
            String nis = tableDatasetUji.getValueAt(j, 0).toString();
            String Hasil = tempHasil[j];
            String classified = knnValue[j];
            Object[] tableContent = {nis, Hasil, classified};
            tableModelRes.addRow(tableContent);
            tblResult.setModel(tableModelRes);
        }

        /*Hitung Jumlah Data Actual*/
        for (int j = 0; j < tempHasil.length; j++) {
            if (tempHasil[j].equals("LULUS")) {
                actLULUS = actLULUS + 1;
            } else if (tempHasil[j].equals("TIDAK")) {
                actTIDAK = actTIDAK + 1;
            }
        }

        /*Hitung Jumlah Data Classified*/
        for (int j = 0; j < knnValue.length; j++) {
            if (tblResult.getValueAt(j, 1).equals("LULUS")) {
                if (tblResult.getValueAt(j, 1).equals(tblResult.getValueAt(j, 2))) {
                    trueLULUS = trueLULUS + 1;
                } else {
                    falseTIDAK = falseTIDAK + 1;
                }
            } else if (tblResult.getValueAt(j, 1).equals("TIDAK")) {
                if (tblResult.getValueAt(j, 1).equals(tblResult.getValueAt(j, 2))) {
                    trueTIDAK = trueTIDAK + 1;
                } else {
                    falseLULUS = falseLULUS + 1;
                }
            }
        }


        /*Hitung Nilai Recall, Precision, dan Accuracy*/
        totPrediksiLULUS = (double) trueLULUS +falseLULUS;
        preLULUS = (double) trueLULUS / (trueLULUS + falseLULUS);//trueLULUS / (trueLULUS + falseLULUS);
        totPrediksiTIDAK = (double) trueTIDAK + falseTIDAK;
        recLULUS = (double) trueLULUS / (trueLULUS + falseTIDAK);
        recTIDAK = (double) trueTIDAK / (trueTIDAK + falseLULUS);
        accuracy = (double) (trueLULUS + trueTIDAK) / (trueLULUS + trueTIDAK + falseLULUS + falseTIDAK);

        /*Tampung Nilai Recall, Precision, dan Accuracy*/
        double[][] tempEval = new double[3][1];
        tempEval[0][0] = accuracy;
        tempEval[1][0] = recLULUS;
        tempEval[2][0] = preLULUS;


        /*Set Nilai TF, TN, FP, FN ke Tabel Confusion Matrix*/
        tableModelConf.setValueAt("Actual LULUS", 0, 0);
        tableModelConf.setValueAt("Actual TIDAK", 1, 0);
        tableModelConf.setValueAt("Total", 2, 0);
        tableModelConf.setValueAt(trueLULUS, 0, 1);
        tableModelConf.setValueAt(falseTIDAK, 0, 2);
        tableModelConf.setValueAt(falseLULUS, 1, 1);
        tableModelConf.setValueAt(trueTIDAK, 1, 2);

        /*Set Nilai Recall, Precision, dan Accuracy ke Tabel Confusion Matrix*/
        if (Double.isNaN(totPrediksiLULUS)) {
            tableModelConf.setValueAt("NaN" + " %", 2, 1);
        } else {
            tableModelConf.setValueAt(df.format(totPrediksiLULUS) , 2, 1);
        }
        if (Double.isNaN(totPrediksiTIDAK)) {
            tableModelConf.setValueAt("NaN", 2, 2);
        } else {
            tableModelConf.setValueAt(df.format(totPrediksiTIDAK), 2, 2);
        }
       /* if (Double.isNaN(recLULUS)) {
            tableModelConf.setValueAt("NaN" + " %", 0, 3);
        } else {
            tableModelConf.setValueAt(df.format(recLULUS * 100) + " %", 0, 3);
        }*/
       /* if (Double.isNaN(recTIDAK)) {
            tableModelConf.setValueAt("NaN" + " %", 1, 3);
        } else {
            tableModelConf.setValueAt(df.format(recTIDAK * 100) + " %", 1, 3);
        }*/
        if (Double.isNaN(accuracy)) {
            lblAkurasi.setText("Akurasi : " + "NaN" + " %");
        } else {
            lblAkurasi.setText("Akurasi    :  " + df.format(accuracy * 100) + " %");
            lblNilaiK.setText("Tingkat Keberhasilan Sistem dengan Nilai (K) = "+k);
          }
        if (Double.isNaN(preLULUS)){
            lblPrecision.setText("Precision : " + "NaN" + "%");
        } else {
            lblPrecision.setText("Presicion :  " + df.format(preLULUS * 100) + "%");
        }
         if (Double.isNaN(recLULUS)){
             lblRecall.setText("Recall      :  " + "NaN" + "%");
        } else {
            lblRecall.setText("Recall      :  " + df.format(recLULUS * 100)+ "%");
        }
        return tempEval;
    }
    
    private void showChart(int[] tempK, double[][] evalValue, JPanel panelChart, JPanel panelChart1, JPanel panelChart2) {
        final XYSeries accuracy = new XYSeries("Accuracy");
        final XYSeries recall = new XYSeries("Recall");
        final XYSeries precision = new XYSeries("Precision");
        final XYSeriesCollection accColect = new XYSeriesCollection();

        System.out.println("tempk panjangnya " + tempK.length);
        for (int i = 0; i < tempK.length; i++) {
            accuracy.add(tempK[i], evalValue[0][i]);
            recall.add(tempK[i], evalValue[1][i]);
            precision.add(tempK[i], evalValue[2][i]);
            System.out.println("Akurasi K ke-" + tempK[i] + "= " + evalValue[0][i]);
        }
        accColect.addSeries(accuracy);
        accColect.addSeries(recall);
        accColect.addSeries(precision);
        JFreeChart xyLineChart = ChartFactory.createXYLineChart("Grafik Hasil Pengujian Multi Testing", "Number of Nearest Neighbor", "Persentase", accColect, PlotOrientation.VERTICAL, true, true, false);
        final XYPlot xyPlot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer xyRender = new XYLineAndShapeRenderer();
        xyRender.setSeriesPaint(0, Color.RED);
        xyRender.setSeriesPaint(1, Color.GREEN);
        xyRender.setSeriesPaint(2, Color.BLUE);
        xyRender.setSeriesStroke(0, new BasicStroke(4.0f));
        xyRender.setSeriesStroke(1, new BasicStroke(3.0f));
        xyRender.setSeriesStroke(2, new BasicStroke(2.0f));
        xyPlot.setRenderer(xyRender);
        ChartPanel cp = new ChartPanel(xyLineChart);
        panelChart.removeAll();
        panelChart.add(cp);
        panelChart.validate();

    }
    
    private void showChart(int nilaiK, double[][] evalValue, JPanel panelChart, JPanel panelChart1, JPanel panelChart2) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(evalValue[0][0], "Kriteria", "Accuracy");
        dataset.addValue(evalValue[1][0], "Kriteria", "Recall");
        dataset.addValue(evalValue[2][0], "Kriteria", "Precision");

        JFreeChart lineChart = ChartFactory.createLineChart("Grafik Hasil Pengujian Single Testing", "Kriteria", "Persentase", dataset, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot linePlot = lineChart.getCategoryPlot();
        LineAndShapeRenderer lineRender = new LineAndShapeRenderer();
        lineRender.setBaseShapesVisible(true);
        lineRender.setDrawOutlines(true);
        lineRender.setUseFillPaint(true);
        lineRender.setBaseFillPaint(Color.RED);
        lineRender.setSeriesPaint(0, Color.BLACK);
        lineRender.setSeriesStroke(0, new BasicStroke(1.0f));
        lineRender.setSeriesOutlineStroke(0, new BasicStroke(5.0f));
        lineRender.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        linePlot.setRenderer(lineRender);

        ChartPanel cp = new ChartPanel(lineChart);
        panelChart.removeAll();
        panelChart.add(cp);
        panelChart.validate();

    }
    
     public void validasiNumberofNearest(KeyEvent evt, JTextField txtKNN, JLabel lblerror, JTable tblDatasetModel) {
        String numberValidate = txtKNN.getText();
        int modelRow = tblDatasetModel.getRowCount();
        if (Pattern.matches("[0-9]+", numberValidate) == false && numberValidate.length() > 0) {
            evt.consume();
            lblerror.setText("Number of Nearest Neighbor tidak valid");
        } else if (numberValidate.length() == 9) {
            evt.consume();
            lblerror.setText("Number of Nearest Neighbor terlalu panjang");
        } else {
            lblerror.setText("");
        }
    }
}