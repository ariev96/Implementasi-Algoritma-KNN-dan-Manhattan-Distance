/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import Connection.koneksi;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Our
 */
public class modelKlasifikasi {


    
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

    public String getMTK1() {
        return MTK1;
    }

    public void setMTK1(String MTK1) {
        this.MTK1 = MTK1;
    }

    public String getBINDO1() {
        return BINDO1;
    }

    public void setBINDO1(String BINDO1) {
        this.BINDO1 = BINDO1;
    }

    public String getBING1() {
        return BING1;
    }

    public void setBING1(String BING1) {
        this.BING1 = BING1;
    }

    public String getPeminatan1() {
        return Peminatan1;
    }

    public void setPeminatan1(String Peminatan1) {
        this.Peminatan1 = Peminatan1;
    }

    public String getMTK2() {
        return MTK2;
    }

    public void setMTK2(String MTK2) {
        this.MTK2 = MTK2;
    }

    public String getBINDO2() {
        return BINDO2;
    }

    public void setBINDO2(String BINDO2) {
        this.BINDO2 = BINDO2;
    }

    public String getBING2() {
        return BING2;
    }

    public void setBING2(String BING2) {
        this.BING2 = BING2;
    }

    public String getPeminatan2() {
        return Peminatan2;
    }

    public void setPeminatan2(String Peminatan2) {
        this.Peminatan2 = Peminatan2;
    }

    public String getMean_SMS1() {
        return Mean_SMS1;
    }

    public void setMean_SMS1(String Mean_SMS1) {
        this.Mean_SMS1 = Mean_SMS1;
    }

    public String getMean_SMS2() {
        return Mean_SMS2;
    }

    public void setMean_SMS2(String Mean_SMS2) {
        this.Mean_SMS2 = Mean_SMS2;
    }

    public String getMean_SMS3() {
        return Mean_SMS3;
    }

    public void setMean_SMS3(String Mean_SMS3) {
        this.Mean_SMS3 = Mean_SMS3;
    }

    public String getMean_SMS4() {
        return Mean_SMS4;
    }

    public void setMean_SMS4(String Mean_SMS4) {
        this.Mean_SMS4 = Mean_SMS4;
    }

    public String getMean_SMS5() {
        return Mean_SMS5;
    }

    public void setMean_SMS5(String Mean_SMS5) {
        this.Mean_SMS5 = Mean_SMS5;
    }

    public String getMean_SMS6() {
        return Mean_SMS6;
    }

    public void setMean_SMS6(String Mean_SMS6) {
        this.Mean_SMS6 = Mean_SMS6;
    }

  
    public String getNIS() {
        return NIS;
    }

    public void setNIS(String NIS) {
        this.NIS = NIS;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String JenisKelamin) {
        this.JenisKelamin = JenisKelamin;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String Jurusan) {
        this.Jurusan = Jurusan;
    }

   

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String Keterangan) {
        this.Keterangan = Keterangan;
    }

    public String getHasil() {
        return Hasil;
    }

    public void setHasil(String Hasil) {
        this.Hasil = Hasil;
    }

  

    /*Hasil Transformasi
    public int meanTO1;
    public int meanTO2;
    public int transKeterangan;*/

    
    public int getRowCount() {
        int rowCount = 0;
        try {
            koneksi dbkonek = new koneksi();
            Connection connect = dbkonek.konek1();
            Statement stm = connect.createStatement();
            String query = "SELECT COUNT(*) FROM Model_Siswa";
            ResultSet rs = stm.executeQuery(query);
            rowCount = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Model.modelKlasifikasi.getRowCount() " + e);
            
            e.printStackTrace();

        }
        return rowCount;
    }
    
    public String[][] getData(int rowCOuntModel) {
        String[][] data = new String[rowCOuntModel][10];
        try {
            koneksi dbkonek = new koneksi();
            Connection connect = dbkonek.konek1();
            Statement stm = connect.createStatement();
            String query = "SELECT * FROM Uji_Siswa";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                setMean_SMS1("Mean_SMS1");
                setMean_SMS2("Mean_SMS2");
                setMean_SMS3("Mean_SMS3");
                setMean_SMS4("Mean_SMS4");
                setMean_SMS5("Mean_SMS5");
                setMean_SMS6("Mean_SMS6");
                setMTK1("MTK1");
                setBINDO1("BINDO1");
                setBING1("BING1");
                setPeminatan1("Peminatan1");
                setMTK2("MTK2");
                setBINDO2("BINDO2");
                setBING2("BING2");
                setPeminatan2("Peminatan2");
                setKeterangan("Keterangan");
                //setPredikat("Predikat");
               
                break;
            }
        } catch (Exception e) {

        }
        return data;
    }
    
}
