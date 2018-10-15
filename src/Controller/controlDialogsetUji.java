/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Menu.dialogDatasetModel;
import Model.modelDialogsetModel;
import Model.modelDialogsetUji;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author arief budiman
 */
public class controlDialogsetUji {
    
     public void listdatabase(JTable tblDialogData) {

        DefaultTableModel tableModel = (DefaultTableModel) tblDialogData.getModel();
        modelDialogsetUji modelDialogDatasetUji = new modelDialogsetUji();
        modelDialogDatasetUji.pilihdataset();
        for (int i = 0; i < modelDialogDatasetUji.getDataSet().size(); i++) {
            tableModel.addRow(modelDialogDatasetUji.getDataSet().get(i));
        }
        tblDialogData.setModel(tableModel);

    }
     
     public int hitungdata(JTable tblDialogData) {
        int rowCount = tblDialogData.getRowCount();
        int dataCount = 0;
        for (int i = 0; i < rowCount; i++) {
            dataCount = dataCount + Integer.parseInt(tblDialogData.getValueAt(i, 1).toString());

        }
        return dataCount;

    }
     
     public void hapus(String tahunAjaran, JTable tblDialogData) {
        modelDialogsetUji mdUji = new modelDialogsetUji();
        mdUji.hapusTahunAjaran(tahunAjaran);
        
    }

}
