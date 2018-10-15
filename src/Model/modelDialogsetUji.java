/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.koneksi;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author arief budiman
 */
public class modelDialogsetUji {
    public Statement sta;
    public Connection connection;
    public koneksi dbConnection = new koneksi();
    public ArrayList<Object[]> dataSet = new ArrayList<Object[]>();
    public String tahunAjaran;
    public int jumlahData;

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public int getJumlahData() {
        return jumlahData;
    }

    public void setJumlahData(int jumlahData) {
        this.jumlahData = jumlahData;
    }

    public ArrayList<Object[]> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<Object[]> dataSet) {
        this.dataSet = dataSet;
    }
    
    public void pilihdataset() {
        try {
            koneksi dbConnection = new koneksi();
            Connection connect = dbConnection.konek1();
            String query = "SELECT tahun_ajaran, COUNT(nis) as jumlah_data FROM Uji_Siswa GROUP BY tahun_ajaran";
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                tahunAjaran = rs.getString("tahun_ajaran");
                jumlahData = rs.getInt("jumlah_data");
                Object[] tempData = {tahunAjaran, jumlahData};
                dataSet.add(tempData);
            }

        } catch (Exception e) {
            System.out.println("Uji.modelDialogsetUji.pilihdataset()");
            e.printStackTrace();
        }
    }
    
    public void hapusTahunAjaran(String tahunAjaran) {
        try {
            koneksi dbConnection = new koneksi();
            Connection connect = dbConnection.konek1();
            Statement sta = connect.createStatement();
            String query = "DELETE FROM Uji_Siswa WHERE tahun_ajaran = '"+tahunAjaran+"'";
            sta.executeUpdate(query);
            sta.close();
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pilihdataset();
    }

}
