/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.modelDatasetModel;
import com.sun.prism.paint.Color;
import com.toedter.calendar.JYearChooser;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Our
 */
public class controlDatasetModel {
    public modelDatasetModel modelDatasetModel = new modelDatasetModel();
    boolean yes;
    
    public String NIS;
    public String NAMA;
    public String JenisKelamin;
    public String Jurusan;
    public String Mean_SMS1;
    public String Mean_SMS2;
    public String Mean_SMS3;
    public String Mean_SMS4;
    public String Mean_SMS5;
    public String Mean_SMS6;
    public String MTK1;
    public String BINDO1;
    public String BING1;
    public String Peminatan1;
    public String MTK2;
    public String BINDO2;
    public String BING2;
    public String Peminatan2;
    
    public String Keterangan;
    public String Hasil;
    public String tahunajaran;
    
    public void isiDatabase(int rowValue, int columnValue, String[][] data, JYearChooser tahun1, JYearChooser tahun2, DefaultTableModel tableModel, int countOutlier, JLabel lblData1) throws SQLException{
        tahunajaran = Integer.toString(tahun1.getYear()) + "/" + Integer.toString(tahun2.getYear());
        modelDatasetModel.setTahunajaran(tahunajaran);
        boolean cekTahun = modelDatasetModel.cekTA();
        if (tahun1.getYear() == tahun2.getYear()) {
            JOptionPane.showMessageDialog(null, "Tahun Ajaran " + tahun1.getYear() + "/" + tahun2.getYear() + " Tahun Ajaran tidak boleh sama!", "Peringatan", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/fail.png"));
        } else if (cekTahun == true) {
            JOptionPane.showMessageDialog(null, "Tahun Ajaran " + tahun1.getYear() + "/" + tahun2.getYear() + " sudah ada, periksa Tahun Ajaran!", "Peringatan", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/fail.png"));
        } else {
            int rowData = tableModel.getRowCount();
            for (int i = 1; i < rowValue; i++) {
                yes = false;
                int j = 0;
                while (j < columnValue) {
                    NIS = data[i][j];
                    NAMA = data[i][j + 1];
                    JenisKelamin = data[i][j + 2];
                    Jurusan = data[i][j + 3];
                    Mean_SMS1 = data[i][j + 4];
                    Mean_SMS2 = data[i][j + 5];
                    Mean_SMS3 = data[i][j + 6];
                    Mean_SMS4 = data[i][j + 7];
                    Mean_SMS5 = data[i][j + 8];
                    Mean_SMS6 = data[i][j + 9];
                    
                    MTK1 = data[i][j + 10];
                    BINDO1 = data[i][j + 11];
                    BING1 = data[i][j + 12];
                    Peminatan1 = data[i][j + 13];
                    MTK2 = data[i][j + 14];
                    BINDO2 = data[i][j + 15];
                    BING2 = data[i][j + 16];
                    Peminatan2 = data[i][j + 17];
                    Keterangan = data[i][j + 18];
                    Hasil = data[i][j + 19];
                    /*Pre-Processing*/
                    bersihData(tableModel, i);
                    break;
                }
                if (yes == false) {
                    modelDatasetModel.setNIS(NIS);
                    modelDatasetModel.setNAMA(NAMA);
                    modelDatasetModel.setJenisKelamin(JenisKelamin);
                    modelDatasetModel.setJurusan(Jurusan);
                    modelDatasetModel.setMean_SMS1(Mean_SMS1);
                    modelDatasetModel.setMean_SMS2(Mean_SMS2);
                    modelDatasetModel.setMean_SMS3(Mean_SMS3);
                    modelDatasetModel.setMean_SMS4(Mean_SMS4);
                    modelDatasetModel.setMean_SMS5(Mean_SMS5);
                    modelDatasetModel.setMean_SMS6(Mean_SMS6);
                    modelDatasetModel.setMTK1(MTK1);
                    modelDatasetModel.setBINDO1(BINDO1);
                    modelDatasetModel.setBING1(BING1);
                    modelDatasetModel.setPeminatan1(Peminatan1);
                    modelDatasetModel.setMTK2(MTK2);
                    modelDatasetModel.setBINDO2(BINDO2);
                    modelDatasetModel.setBING2(BING2);
                    modelDatasetModel.setPeminatan2(Peminatan2);
                    modelDatasetModel.setKeterangan(Keterangan);
                    modelDatasetModel.setHasil(Hasil);
                    
                    modelDatasetModel.SimpanDataNilai();

                }
            }

//            modelDataSet.setJumlahData(((rowValue - 1) - countOutlier));
//            modelDataSet.saveTahunAjaran();
            lblData1.setText(hitungdata()+ " Data");
           
            ImageIcon icon = new ImageIcon("src/Image/success.png");
            JOptionPane.showMessageDialog(null, "Import Dataset Model Berhasil!\n"
                    + "Informasi :\n"
                    + "- " + ((rowValue - 1) - countOutlier) + " Data\n"
                    + "- " + countOutlier + " Outlier", "  SUKSES", JOptionPane.INFORMATION_MESSAGE, icon);

        }
    }
    
    public void bersihData(DefaultTableModel tblData, int i) {
        double tempMean_SMS1;
        double tempMean_SMS2;
        double tempMean_SMS3;
        double tempMean_SMS4;
        double tempMean_SMS5;
        double tempMean_SMS6;
        double tempMTK1;
        double tempBINDO1;
        double tempBING1;
        double tempPeminatan1;
        double tempMTK2;
        double tempBINDO2;
        double tempBING2;
        double tempPeminatan2;     
//   double tempKeterangan;
      //  double tempPredikat;
        
        tempMean_SMS1 = Double.parseDouble(Mean_SMS1);
        tempMean_SMS2 = Double.parseDouble(Mean_SMS2);
        tempMean_SMS3 = Double.parseDouble(Mean_SMS3);
        tempMean_SMS4 = Double.parseDouble(Mean_SMS4);
        tempMean_SMS5 = Double.parseDouble(Mean_SMS5);
        tempMean_SMS6 = Double.parseDouble(Mean_SMS6);
        tempMTK1 = Double.parseDouble(MTK1);
        tempBINDO1 = Double.parseDouble(BINDO1);
        tempBING1 = Double.parseDouble(BING1);
        tempPeminatan1 = Double.parseDouble(Peminatan1);
        tempMTK2 = Double.parseDouble(MTK2);
        tempBINDO2 = Double.parseDouble(BINDO2);
        tempBING2 = Double.parseDouble(BING2);
        tempPeminatan2 = Double.parseDouble(Peminatan2);
        
        if (tempMean_SMS1 == 0.0 || tempMean_SMS2 == 0.0 || tempMean_SMS3 == 0.0 || tempMean_SMS4 == 0.0 || tempMean_SMS5 == 0.0 || tempMean_SMS6 == 0.0 ||tempMTK1 == 0.0 || tempBINDO1 == 0.0 || tempBING1 == 0.0 || tempPeminatan1 == 0.0 ||tempMTK2 == 0.0 || tempBINDO2 == 0.0 || tempBING2 == 0.0 || tempPeminatan2 == 0.0) {
            //tableModel.removeRow(i);
            yes = true;
        }
        //return yes;
    }
    
    public int hitungdata(){
        int data;
        modelDatasetModel = new modelDatasetModel();
        data = modelDatasetModel.PilihTotalData();
        return data;
    }
}
