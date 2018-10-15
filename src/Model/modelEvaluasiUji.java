/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author arief budiman
 */
public class modelEvaluasiUji {
     public ArrayList<String> tahunAjaran;
    //public ControllerEvaluation controllerEvaluation;
    public koneksi dbkonek;
    
    public String query;
    public ArrayList<String> tableContent;
    public Connection connect;
    public Statement stm;
    public ResultSet rs;
    public PreparedStatement pstmt;

    
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

    public ArrayList<String> getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(ArrayList<String> tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public ArrayList<String> getTableContent() {
        return tableContent;
    }

    public void setTableContent(ArrayList<String> tableContent) {
        this.tableContent = tableContent;
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

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String Keterangan) {
        this.Keterangan = Keterangan;
    }

    
       public void selectTahunAjaranDataUji() {
        try {
            //controllerEvaluation = new ControllerEvaluation();
            tahunAjaran = new ArrayList<>();
            dbkonek = new koneksi();
            connect = dbkonek.konek1();
            stm = connect.createStatement();

            query = "SELECT Tahun_Ajaran FROM Uji_Siswa GROUP BY Tahun_Ajaran";
            rs = stm.executeQuery(query);
            while (rs.next()) {
                tahunAjaran.add(rs.getString("Tahun_Ajaran"));
                //setTahunAjaran(tahunAjaran);
                /*Remark!!*/
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
       
        public void showDataSetUji(String tempArrayList) throws SQLException {

        /*Tanpa setter method*/
        //controllerEvaluation = new ControllerEvaluation();
        dbkonek = new koneksi();
        connect = dbkonek.konek1();

        //stm = connect.createStatement();
        query = "SELECT * FROM Uji_Siswa WHERE Tahun_Ajaran = ?";
        pstmt = connect.prepareStatement(query);
        pstmt.setString(1, tempArrayList.toString());
        rs = pstmt.executeQuery();
        while (rs.next()) {
            setNIS(rs.getString("NIS"));
            setNAMA(rs.getString("NAMA"));
            setJenisKelamin(rs.getString("JenisKelamin"));
            setJurusan(rs.getString("Jurusan"));
            setMean_SMS1(rs.getString("Mean_SMS1"));
            setMean_SMS2(rs.getString("Mean_SMS2"));
            setMean_SMS3(rs.getString("Mean_SMS3"));
            setMean_SMS4(rs.getString("Mean_SMS4"));
            setMean_SMS5(rs.getString("Mean_SMS5"));
            setMean_SMS6(rs.getString("Mean_SMS6"));
            setMTK1(rs.getString("MTK1"));
            setBINDO1(rs.getString("BINDO1"));
            setBING1(rs.getString("BING1"));
            setPeminatan1(rs.getString("Peminatan1"));
            setMTK2(rs.getString("MTK2"));
            setBINDO2(rs.getString("BINDO2"));
            setBING2(rs.getString("BING2"));
            setPeminatan2(rs.getString("Peminatan2"));
            setKeterangan(rs.getString("Keterangan"));
            break;
        }
    }
}
