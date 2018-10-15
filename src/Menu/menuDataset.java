/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Controller.controlDatasetModel;
import Controller.controlDatasetUji;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author arief budiman
 */
public class menuDataset extends javax.swing.JPanel {

    private JFileChooser pilihfile;
    private FileNameExtensionFilter filterEkstensi;
    private DefaultTableModel tableModel;
    private File NamaFile;
    private FileInputStream inputStream;
    private Object returnCellValue;
    private HSSFWorkbook workBook;
    private HSSFSheet sheet;
    private HSSFRow row;
    private HSSFCell cell;
    private XSSFWorkbook workBook1;
    private XSSFSheet sheet1;
    private XSSFRow row1;
    private XSSFCell cell1;

    private int rowValue;
    private int columnValue;
    private int type;
    public int countOutlier;

    public String[] NamaKolom;
    public String[][] Data;
    public String DataCell;
    public String query;
    public String Tahun;
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
    public String TahunAjaran;
    
    controlDatasetModel CDModel;
    controlDatasetUji CDUji;
    
    public menuDataset() {
        initComponents();
        inisial();
        inisial1();
        
       
       // new JScrollPane(tbData, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     //   tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    
     private void TampilFile(ActionEvent evt) {
        CDModel = new Controller.controlDatasetModel();
        try {
            if (txtDirektori.getText().endsWith(".xlsx")) {
                previewXLSX();
                btnSimpan.setEnabled(true);
            } else if (txtDirektori.getText().endsWith(".xls")) {
                previewXLSX();
                btnSimpan.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "File dataset harus file spreadsheet dengan ekstensi *xls atau *.xlsx!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/com/smanempat/image/fail.png"));
                btnCariFileActionPerformed(evt);
                inisial();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memilih file.\n\n"
                    + "Pesan Error :\n" + e.fillInStackTrace() + "\n\n"
                    + "Pastikan format file dataset sudah benar.", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/image/fail.png"));
        }
    }
     
      private void TampilFileUji(ActionEvent evt) {
        CDUji = new Controller.controlDatasetUji();
        try {
            if (txtDirektori1.getText().endsWith(".xlsx")) {
                previewXLSXUji();
                btnSimpan1.setEnabled(true);
            } else if (txtDirektori1.getText().endsWith(".xls")) {
                previewXLSXUji();
                btnSimpan1.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "File dataset harus file spreadsheet dengan ekstensi *xls atau *.xlsx!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/com/smanempat/image/fail.png"));
                btnCariFile1ActionPerformed(evt);
                inisial1();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memilih file.\n\n"
                    + "Pesan Error :\n" + e.fillInStackTrace() + "\n\n"
                    + "Pastikan format file dataset sudah benar.", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/image/fail.png"));
        }
    }
      
      private void previewXLSXUji() throws FileNotFoundException, IOException {
        /*Method Untuk Membaca Dataset dengan ekstensi .XLS*/
        NamaFile = new File(txtDirektori1.getText());
        inputStream = new FileInputStream(NamaFile);
        workBook1 = new XSSFWorkbook(inputStream);
        sheet1 = workBook1.getSheetAt(0);

        countOutlier = 0;
        rowValue = sheet1.getLastRowNum() + 1;
        columnValue = sheet1.getRow(0).getLastCellNum();
        Data = new String[rowValue][columnValue];
        NamaKolom = new String[columnValue];
        for (int i = 0; i < rowValue; i++) {
            row1 = sheet1.getRow(i);
            for (int j = 0; j < columnValue; j++) {
                cell1 = row1.getCell(j);
                type = cell1.getCellType();
                returnCellValue = null;
                if (type == 0) {
                    returnCellValue = cell1.getNumericCellValue();
                } else if (type == 1) {
                    returnCellValue = cell1.getStringCellValue();
                }

                //dataCell = returnCellValue.toString();
                Data[i][j] = returnCellValue.toString();
            }
            /*Cleaning Anomali Data (Outlier), Dimulai dari i atau baris ke 1*/
            if (i != 0 ) {
                double tempMean_SMS1 = Double.parseDouble(Data[i][4]);
                double tempMean_SMS2 = Double.parseDouble(Data[i][5]);
                double tempMean_SMS3 = Double.parseDouble(Data[i][6]);
                double tempMean_SMS4 = Double.parseDouble(Data[i][7]);
                double tempMean_SMS5 = Double.parseDouble(Data[i][8]);
                double tempMean_SMS6 = Double.parseDouble(Data[i][9]);
                
                double tempMTK1 = Double.parseDouble(Data[i][10]);
                double tempBINDO1 = Double.parseDouble(Data[i][11]);
                double tempBING1 = Double.parseDouble(Data[i][12]);
                double tempPeminatan1 = Double.parseDouble(Data[i][13]);
                double tempMTK2 = Double.parseDouble(Data[i][14]);
                double tempBINDO2 = Double.parseDouble(Data[i][15]);
                double tempBING2 = Double.parseDouble(Data[i][16]);
                double tempPeminatan2 = Double.parseDouble(Data[i][17]);
               
                if (tempMean_SMS1 == 0.0 || tempMean_SMS2 == 0.0 || tempMean_SMS3 == 0.0 || tempMean_SMS4 == 0.0 || tempMean_SMS5 == 0.0 || tempMean_SMS6 == 0.0 || tempMTK1 == 0.0 || tempBINDO1 == 0.0 || tempBING1 == 0.0 || tempPeminatan1 == 0.0 || tempMTK2 == 0.0 || tempBINDO2 == 0.0 || tempBING2 == 0.0 || tempPeminatan2 == 0.0 ) {
                        countOutlier = countOutlier + 1;
                }
               
               
               
            }
        }
        /*Set column name*/
        for (int i = 0; i < columnValue; i++) {
            NamaKolom[i] = Data[0][i];
        }
        /*Set table model and show file on table*/
        tableModel = new DefaultTableModel(Data, NamaKolom);
        tbData1.setModel(tableModel);
        tableModel.removeRow(0);
        lblData4.setText(tableModel.getRowCount() + " Data");
        lblOutlier1.setText(countOutlier + " Data");
    }
     
     private void previewXLSX() throws FileNotFoundException, IOException {
        /*Method Untuk Membaca Dataset dengan ekstensi .XLS*/
        NamaFile = new File(txtDirektori.getText());
        inputStream = new FileInputStream(NamaFile);
        workBook1 = new XSSFWorkbook(inputStream);
        sheet1 = workBook1.getSheetAt(0);

        countOutlier = 0;
        rowValue = sheet1.getLastRowNum() + 1;
        columnValue = sheet1.getRow(0).getLastCellNum();
        Data = new String[rowValue][columnValue];
        NamaKolom = new String[columnValue];
        for (int i = 0; i < rowValue; i++) {
            row1 = sheet1.getRow(i);
            for (int j = 0; j < columnValue; j++) {
                cell1 = row1.getCell(j);
                type = cell1.getCellType();
                returnCellValue = null;
                if (type == 0) {
                    returnCellValue = cell1.getNumericCellValue();
                } else if (type == 1) {
                    returnCellValue = cell1.getStringCellValue();
                }

                //dataCell = returnCellValue.toString();
                Data[i][j] = returnCellValue.toString();
            }
            /*Cleansing Anomali Data (Outlier), Dimulai dari i atau baris ke 1*/
            if (i != 0) {
                double tempMean_SMS1 = Double.parseDouble(Data[i][4]);
                double tempMean_SMS2 = Double.parseDouble(Data[i][5]);
                double tempMean_SMS3 = Double.parseDouble(Data[i][6]);
                double tempMean_SMS4 = Double.parseDouble(Data[i][7]);
                double tempMean_SMS5 = Double.parseDouble(Data[i][8]);
                double tempMean_SMS6 = Double.parseDouble(Data[i][9]);
                double tempMTK1 = Double.parseDouble(Data[i][10]);
                double tempBINDO1 = Double.parseDouble(Data[i][11]);
                double tempBING1 = Double.parseDouble(Data[i][12]);
                double tempPeminatan1 = Double.parseDouble(Data[i][13]);
                 double tempMTK2 = Double.parseDouble(Data[i][14]);
                double tempBINDO2 = Double.parseDouble(Data[i][15]);
                double tempBING2 = Double.parseDouble(Data[i][16]);
                double tempPeminatan2 = Double.parseDouble(Data[i][17]);
                 if (tempMean_SMS1 == 0.0 || tempMean_SMS2 == 0.0 || tempMean_SMS3 == 0.0 || tempMean_SMS4 == 0.0 || tempMean_SMS5 == 0.0 || tempMean_SMS6 == 0.0 || tempMTK1 == 0.0 || tempBINDO1 == 0.0 || tempBING1 == 0.0 || tempPeminatan1 == 0.0 || tempMTK2 == 0.0 || tempBINDO2 == 0.0 || tempBING2 == 0.0 || tempPeminatan2 == 0.0) {
                    countOutlier = countOutlier + 1;
                }
            }
        }
        /*Set column name*/
        for (int i = 0; i < columnValue; i++) {
            NamaKolom[i] = Data[0][i];
        }
        /*Set table model and show file on table*/
        tableModel = new DefaultTableModel(Data, NamaKolom);
        tbData.setModel(tableModel);
        tableModel.removeRow(0);
        lblData2.setText(tableModel.getRowCount() + " Data");
        lblOutlier.setText(countOutlier + " Data");
    }
     
        private void inisial() {
        /*Method untuk inisialisasi form*/
        try {
            CDModel = new controlDatasetModel();
            int totalData = CDModel.hitungdata();
            btnSimpan.setEnabled(false);
            txtDirektori.setText("");
            txtDirektori.setEditable(false); 
            lblData1.setText(totalData + " Data");
            lblData2.setText(0 + " Data");
            lblOutlier.setText(0 + " Data");
            tbData.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "NIS", "NAMA", "JenisKelamin", "Jurusan", "Mean_SMS1", "Mean_SMS2", "Mean_SMS3", "Mean_SMS4", "Mean_SMS5", "Mean_SMS6", "MTK1", "BINDO1", "BING1", "Peminatan1" , "MTK2", "BINDO2", "BING2", "Peminatan2", "Keterangan", "Hasil"
                    }
            ));
        } catch (Exception e) {
            System.out.println("Menu.menuDataset.inisial() : " + e);
        }
        
        

    }
        
        private void inisial1() {
        /*Method untuk inisialisasi form*/
        try {
            CDUji = new controlDatasetUji();
            int totalData = CDUji.hitungdata();
            btnSimpan1.setEnabled(false);
            txtDirektori1.setText("");
            txtDirektori1.setEditable(false); 
            lblData3.setText(totalData + " Data");
            lblData4.setText(0 + " Data");
            lblOutlier1.setText(0 + " Data");
            tbData1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                       "NIS", "NAMA", "JenisKelamin", "Jurusan", "Mean_SMS1", "Mean_SMS2", "Mean_SMS3", "Mean_SMS4", "Mean_SMS5", "Mean_SMS6", "MTK1", "BINDO1", "BING1", "Peminatan1" , "MTK2", "BINDO2", "BING2", "Peminatan2", "Keterangan"
                     }
            ));
        } catch (Exception e) {
            System.out.println("Menu.datasetUji.inisial1() : " + e);
        }
        
        

    }

    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        tahun1 = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();
        tahun2 = new com.toedter.calendar.JYearChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDirektori = new javax.swing.JTextField();
        lblData1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        btnCariFile = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbData = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        lblData2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblOutlier = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDirektori1 = new javax.swing.JTextField();
        lblData3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        btnCariFile1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbData1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        lblData4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblOutlier1 = new javax.swing.JLabel();
        btnSimpan1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(59, 102, 145));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Dataset Model", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto Th", 0, 18))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1107, 110));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel1.setText("Jumlah data saat ini :");

        jLabel2.setFont(new java.awt.Font("Roboto Th", 0, 24)); // NOI18N
        jLabel2.setText("/");

        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel4.setText("Tahun ajaran :");

        txtDirektori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirektoriActionPerformed(evt);
            }
        });

        lblData1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lblData1.setText("0 data");

        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel6.setText("Pilih data :");

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton4.setText("Data");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnCariFile.setBackground(new java.awt.Color(204, 204, 204));
        btnCariFile.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCariFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/seacrh2.png"))); // NOI18N
        btnCariFile.setText("CARI");
        btnCariFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariFileActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Model", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto Cn", 0, 24))); // NOI18N

        tbData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIS", "Nama", "Jenis Kelamin", "Jurusan", "Mean_SMS1", "Mean_SMS2", "Mean_SMS3", "Mean_SMS4", "Mean_SMS5", "Mean_SMS6", "MTK1", "BINDO1", "BING1", "Peminatan1", "MTK2", "BINDO2", "BING2", "Peminatan2", "Keterangan", "Hasil"
            }
        ));
        jScrollPane3.setViewportView(tbData);

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel9.setText("Jumlah data :");

        lblData2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblData2.setText("0 data");

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel10.setText("outlier terdeteksi :");

        lblOutlier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOutlier.setText("0 data");

        btnSimpan.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bersih.png"))); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblData2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblOutlier))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblData2)
                    .addComponent(jLabel10)
                    .addComponent(lblOutlier)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tahun1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tahun2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addComponent(txtDirektori))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnCariFile))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblData1)))
                .addContainerGap(273, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCariFile, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tahun1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tahun2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addComponent(lblData1)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDirektori, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("          Dataset Model          ", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Dataset Uji", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto Th", 0, 18))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(1366, 110));

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel3.setText("Jumlah data saat ini :");

        txtDirektori1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirektori1ActionPerformed(evt);
            }
        });

        lblData3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lblData3.setText("0 data");

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel8.setText("Pilih data :");

        jButton5.setBackground(new java.awt.Color(204, 204, 204));
        jButton5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton5.setText("Data");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnCariFile1.setBackground(new java.awt.Color(204, 204, 204));
        btnCariFile1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCariFile1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/seacrh2.png"))); // NOI18N
        btnCariFile1.setText("CARI");
        btnCariFile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariFile1ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Uji", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto Cn", 0, 24))); // NOI18N

        tbData1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIS", "NAMA", "JenisKelamin", "Jurusan", "Mean_SMS1", "Mean_SMS2", "Mean_SMS3", "Mean_SMS4", "Mean_SMS5", "Mean_SMS6", "MTK1", "BINDO1", "BING1", "Peminatan1", "MTK2", "BINDO2", "BING2", "Peminatan2", "Keterangan"
            }
        ));
        jScrollPane4.setViewportView(tbData1);

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel11.setText("Jumlah data :");

        lblData4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblData4.setText("0 data");

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel12.setText("outlier terdeteksi :");

        lblOutlier1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOutlier1.setText("0 Data");

        btnSimpan1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save.png"))); // NOI18N
        btnSimpan1.setText("Simpan");
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bersih.png"))); // NOI18N
        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblData4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOutlier1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSimpan1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblData4)
                    .addComponent(jLabel12)
                    .addComponent(lblOutlier1)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addGap(28, 28, 28)
                .addComponent(txtDirektori1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCariFile1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblData3)
                .addGap(34, 34, 34)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblData3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDirektori1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCariFile1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("            Dataset Uji            ", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 463, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDirektoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirektoriActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDirektoriActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dialogDatasetModel diaipa = new dialogDatasetModel(null, true);
        diaipa.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnCariFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariFileActionPerformed
        // TODO add your handling code here:
        try{
            pilihfile = new JFileChooser();
            filterEkstensi = new FileNameExtensionFilter("Excel File", "xls", "xlsx");
            pilihfile.setFileFilter(filterEkstensi);
            if(pilihfile.showOpenDialog(this)== JFileChooser.APPROVE_OPTION){
                txtDirektori.setText(pilihfile.getSelectedFile().getAbsolutePath());
                TampilFile(evt);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal upload data","Terjadi Kesalahan Upload Data", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCariFileActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        int confirm = JOptionPane.showOptionDialog(null, "Apakah anda yakin menyimpan tahun ajaran " + tahun1.getYear()
            + "/" + tahun2.getYear() + "?", "Simpan Dataset", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.OK_OPTION) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CDModel = new controlDatasetModel();
                CDModel.isiDatabase(rowValue, columnValue, Data, tahun1, tahun2, tableModel, countOutlier, lblData1);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch(SQLException e) {
                Logger.getLogger(menuDataset.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        inisial();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtDirektori1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirektori1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirektori1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dialogDatasetUji diauji = new dialogDatasetUji(null, true);
        diauji.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnCariFile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariFile1ActionPerformed
        // TODO add your handling code here:
        try{
            pilihfile = new JFileChooser();
            filterEkstensi = new FileNameExtensionFilter("Excel File", "xls", "xlsx");
            pilihfile.setFileFilter(filterEkstensi);
            if(pilihfile.showOpenDialog(this)== JFileChooser.APPROVE_OPTION){
                txtDirektori1.setText(pilihfile.getSelectedFile().getAbsolutePath());
                TampilFileUji(evt);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal upload data","Terjadi Kesalahan Upload Data", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCariFile1ActionPerformed

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showOptionDialog(null, "Apakah anda yakin ingin menyimpan? " 
           , "Simpan Dataset", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.OK_OPTION) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CDUji = new controlDatasetUji();
                CDUji.isiDatabase(rowValue, columnValue, Data, tableModel, countOutlier, lblData3);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch(SQLException e) {
                Logger.getLogger(menuDataset.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        inisial1();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariFile;
    private javax.swing.JButton btnCariFile1;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpan1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblData2;
    private javax.swing.JLabel lblData3;
    private javax.swing.JLabel lblData4;
    private javax.swing.JLabel lblOutlier;
    private javax.swing.JLabel lblOutlier1;
    private com.toedter.calendar.JYearChooser tahun1;
    private com.toedter.calendar.JYearChooser tahun2;
    private javax.swing.JTable tbData;
    private javax.swing.JTable tbData1;
    private javax.swing.JTextField txtDirektori;
    private javax.swing.JTextField txtDirektori1;
    // End of variables declaration//GEN-END:variables
}


