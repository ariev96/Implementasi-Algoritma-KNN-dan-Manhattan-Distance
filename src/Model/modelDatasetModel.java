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
/**
 *
 * @author Our
 */
public class modelDatasetModel {

   
    

    
    public String tahunajaran;    
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
    public String BING1;
    public String BINDO1;
    public String Peminatan1;
    public String MTK2;
    public String BING2;
    public String BINDO2;
    public String Peminatan2;
   

    public String Keterangan;
    public String Hasil;
    
    public int jumlahdata;
    
    //untukcek tahun
    private String query;
    
    public Connection connection;
    public koneksi dbkonek = new koneksi();
    public PreparedStatement preparedStatement;
    public Statement statement;
    public ResultSet rs;

    public String getMTK1() {
        return MTK1;
    }

    public void setMTK1(String MTK1) {
        this.MTK1 = MTK1;
    }

    public String getBING1() {
        return BING1;
    }

    public void setBING1(String BING1) {
        this.BING1 = BING1;
    }

    public String getBINDO1() {
        return BINDO1;
    }

    public void setBINDO1(String BINDO1) {
        this.BINDO1 = BINDO1;
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

    public String getBING2() {
        return BING2;
    }

    public void setBING2(String BING2) {
        this.BING2 = BING2;
    }

    public String getBINDO2() {
        return BINDO2;
    }

    public void setBINDO2(String BINDO2) {
        this.BINDO2 = BINDO2;
    }

    public String getPeminatan2() {
        return Peminatan2;
    }

    public void setPeminatan2(String Peminatan2) {
        this.Peminatan2 = Peminatan2;
    }

   

    public String getTahunajaran() {
        return tahunajaran;
    }

    public void setTahunajaran(String tahunajaran) {
        this.tahunajaran = tahunajaran;
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

    public String getHasil() {
        return Hasil;
    }

    public void setHasil(String Hasil) {
        this.Hasil = Hasil;
    }

    public int getJumlahdata() {
        return jumlahdata;
    }

    public void setJumlahdata(int jumlahdata) {
        this.jumlahdata = jumlahdata;
    }

   
    

    
    //save tahun ajaram
    public void SimpanTahunAjaran() throws SQLException{
        String tempTahun;
        boolean cekTahun = false;
        try{
            connection = dbkonek.konek1();
            statement = connection.createStatement();
            query = "select Tahun_Ajaran from Model_Siswa group by Tahun_Ajaran";
            rs = statement.executeQuery(query);
            
            while(rs.next()){
                tempTahun = rs.getString("Tahun_Ajaran");
                if (tempTahun.equals(tahunajaran)){
                    cekTahun = true;
                    break;
                }
            }
            
            rs.close();
            statement.close();
            connection.close();
        }
        catch(SQLException e){
            System.out.println("Model.modelDatasetModel.SimpanTahunAjaran() \n" +e);
        }
    }
    
    //Simpan Data nilai
    public void SimpanDataNilai(){
        try {
            connection = dbkonek.konek1();
            query = "INSERT INTO Model_Siswa(Tahun_Ajaran, NIS, NAMA, JenisKelamin, "
                    + "Jurusan, Mean_SMS1, Mean_SMS2, Mean_SMS3, Mean_SMS4, Mean_SMS5, Mean_SMS6, MTK1, BINDO1, BING1, Peminatan1,"
                    + "MTK2, BINDO2, BING2, Peminatan2, Keterangan, Hasil) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getTahunajaran());
            preparedStatement.setString(2, getNIS());
            preparedStatement.setString(3, getNAMA());
            preparedStatement.setString(4, getJenisKelamin());
            preparedStatement.setString(5, getJurusan());
            preparedStatement.setString(6, getMean_SMS1());
            preparedStatement.setString(7, getMean_SMS2());
            preparedStatement.setString(8, getMean_SMS3());
            preparedStatement.setString(9, getMean_SMS4());
            preparedStatement.setString(10, getMean_SMS5());
            preparedStatement.setString(11, getMean_SMS6());
            preparedStatement.setString(12, getMTK1());
            preparedStatement.setString(13, getBINDO1());
            preparedStatement.setString(14, getBING1());
            preparedStatement.setString(15, getPeminatan1());
            preparedStatement.setString(16, getMTK2());
            preparedStatement.setString(17, getBINDO2());
            preparedStatement.setString(18, getBING2());
            preparedStatement.setString(19, getPeminatan2());
            preparedStatement.setString(20, getKeterangan());
            preparedStatement.setString(21, getHasil());
           
            preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println("Model.modelDatasetModel.SimpanDataNilai()\n" +e);
        }
    }
    
    //TOTAL DATA
    public int PilihTotalData(){
        int totalData =0;
        try {
            connection = dbkonek.konek1();
            query = "select count(*) as total_data from Model_Siswa";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                totalData = rs.getInt("total_data");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return totalData;
    }
    //cek tahun ajaran
    public boolean cekTA() throws SQLException{
        String tempTahun;
        boolean cekTahun = false;
        try {
            connection = dbkonek.konek1();
            statement = connection.createStatement();
            query = "SELECT Tahun_Ajaran FROM Model_Siswa GROUP BY Tahun_Ajaran";
            rs = statement.executeQuery(query);
            
            while(rs.next()){
                tempTahun = rs.getString("tahun_ajaran");
                if(tempTahun.equals(tahunajaran)){
                    cekTahun = true;
                    break;
                }
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Model.modelDataset.cekTA()\n" +e);
        }
        return cekTahun;
    }
}
