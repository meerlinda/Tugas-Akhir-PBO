/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket.TugasAkhir;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.util.HashMap;
import paket.TugasAkhir.TugasAkhir;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Microsoft
 */
public class FormDataPembelian extends javax.swing.JFrame {
TugasAkhir Apotek = new TugasAkhir();
DefaultTableModel tabel = new DefaultTableModel();

    public class MainLaporan {

        public String namaLaporan, ParameterLaporan, idLaporan;

        public void cetakLaporanMaster() {
            Apotek.PBOTugasAkhir();
            String Rs = "src/paket/ReportTA/" + namaLaporan + ".jrxml";
            HashMap param = new HashMap();
            param.put(ParameterLaporan, idLaporan);

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(Rs);
                JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport,
                        param, Apotek.connect);
                JasperViewer.viewReport(JasperPrint, false);
            } catch (JRException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Creates new form FormDataPembelian
     */
    public FormDataPembelian() {
        initComponents();
        CenterForm();
        enableFalse();
        tampilDataBeli();
        tampilDataBarang();
        tanggalPembelian();
    }
    
    private void CenterForm(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }
    
    void tampilDataBeli() {
        DefaultTableModel tabel = new DefaultTableModel();

        tabel.addColumn("Nomor Trx");
        tabel.addColumn("Tanggal");
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Jumlah Beli");
        tabel.addColumn("Harga");
        tabel.addColumn("Total");
        
        JTable_Beli.setModel(tabel);

        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT * FROM tbl_pembelian";

            ResultSet ress = Apotek.state.executeQuery(querySelect);

            while (ress.next()) {
                tabel.addRow(new Object[]{
                    ress.getString("NomorTrx"),
                    ress.getString("Tanggal"), 
                    ress.getString("KodeBarang"), 
                    ress.getString("NamaBarang"), 
                    ress.getString("JumlahBeli"), 
                    ress.getString("Harga"), 
                    ress.getString("Total")
                });
                JTable_Beli.setModel(tabel);
            }
        } catch (SQLException e) {
            System.err.println(" "+e);
        }
        }
    
    void tampilDataBarang() {
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Harga");
        
        JTable_DataBarang.setModel(tabel);

        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT KodeBarang, NamaBarang, HargaSatuan FROM tbl_barang";

            ResultSet ress = Apotek.state.executeQuery(querySelect);

            while (ress.next()) {
                tabel.addRow(new Object[]{
                    ress.getString("KodeBarang"), 
                    ress.getString("NamaBarang"),
                    ress.getString("HargaSatuan")
                });
                JTable_DataBarang.setModel(tabel);
            }
        } catch (SQLException e) {
            System.err.println(" "+e);
        }
        }
    
    private void Bersih() {
        JT_NomorTransaksi.setText("");
        JT_TanggalPembelian.setText("");
        JT_KodeBarangBeli.setText("");
        JT_NamaBarangBeli.setText("");
        JT_JumlahBeli.setText("");
        JT_HargaBeli.setText("");
        JT_TotalBeli.setText("");
        JT_CariBarangBeli.setText("");
    }
      
    void enableFalse(){
        Btn_SimpanBeli.setEnabled(false);
        Btn_UbahBeli.setEnabled(false);
        Btn_HapusBeli.setEnabled(false);
        Btn_Batal.setEnabled(false);
        JT_NomorTransaksi.setEnabled(false);
        JT_TanggalPembelian.setEnabled(false);
        JT_KodeBarangBeli.setEnabled(false);
        JT_NamaBarangBeli.setEnabled(false);
        JT_JumlahBeli.setEnabled(false);
        JT_HargaBeli.setEnabled(false);
        JT_TotalBeli.setEnabled(false);
        JT_CariBarangBeli.setEnabled(false);
        JTable_DataBarang.setVisible(false);
    }
    
    void enableTrueTambah(){
        Btn_TambahBeli.setEnabled(false);
        Btn_SimpanBeli.setEnabled(true);
        Btn_UbahBeli.setEnabled(false);
        Btn_HapusBeli.setEnabled(false);
        Btn_Batal.setEnabled(true);
        JT_NomorTransaksi.setEnabled(false);
        JT_TanggalPembelian.setEnabled(false);
        JT_KodeBarangBeli.setEnabled(false);
        JT_NamaBarangBeli.setEnabled(false);
        JT_JumlahBeli.setEnabled(true);
        JT_HargaBeli.setEnabled(false);
        JT_TotalBeli.setEnabled(false);
        JT_CariBarangBeli.setEnabled(true);
        JTable_DataBarang.setVisible(true);
    }
    
    void enableTrueUbahHapus(){
        Btn_TambahBeli.setEnabled(false);
        Btn_SimpanBeli.setEnabled(false);
        Btn_UbahBeli.setEnabled(true);
        Btn_HapusBeli.setEnabled(true);
        Btn_Batal.setEnabled(true);
        JT_NomorTransaksi.setEnabled(false);
        JT_TanggalPembelian.setEnabled(false);
        JT_KodeBarangBeli.setEnabled(false);
        JT_NamaBarangBeli.setEnabled(false);
        JT_JumlahBeli.setEnabled(true);
        JT_HargaBeli.setEnabled(false);
        JT_TotalBeli.setEnabled(false);
        JT_CariBarangBeli.setEnabled(false);
        JTable_DataBarang.setVisible(false);
    }
    
    void enableUbahHapusBatal(){
        Btn_TambahBeli.setEnabled(true);
        Btn_SimpanBeli.setEnabled(false);
        Btn_UbahBeli.setEnabled(false);
        Btn_HapusBeli.setEnabled(false);
        Btn_Batal.setEnabled(false);
        JT_NomorTransaksi.setEnabled(false);
        JT_TanggalPembelian.setEnabled(false);
        JT_KodeBarangBeli.setEnabled(false);
        JT_NamaBarangBeli.setEnabled(false);
        JT_JumlahBeli.setEnabled(false);
        JT_HargaBeli.setEnabled(false);
        JT_TotalBeli.setEnabled(false);
        JT_CariBarangBeli.setEnabled(false);
        JTable_DataBarang.setVisible(false);
    }
    
    public void cariBarang() {
        tabel.getDataVector().removeAllElements();
        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT * FROM tbl_barang where NamaBarang like '%"+JT_CariBarangBeli.getText()+"%'";
            
            ResultSet ress = Apotek.state.executeQuery(querySelect);
            
            while (ress.next()) {
                tabel.addRow(new Object[]{
                    ress.getString("KodeBarang"), 
                    ress.getString("NamaBarang"),
                    ress.getString("HargaSatuan")
                });
                JTable_DataBarang.setModel(tabel);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "eror tampil : " + e);
        }
    }
    
    void tanggalPembelian(){
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        JT_TanggalPembelian.setText(String.valueOf(sdf.format(new java.util.Date())));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_Beli = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        JT_TanggalPembelian = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        JT_KodeBarangBeli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        JT_NamaBarangBeli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        JT_HargaBeli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        JT_TotalBeli = new javax.swing.JTextField();
        JT_JumlahBeli = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        JT_NomorTransaksi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        JT_CariBarangBeli = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTable_DataBarang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        Btn_TambahBeli = new javax.swing.JButton();
        Btn_SimpanBeli = new javax.swing.JButton();
        Btn_UbahBeli = new javax.swing.JButton();
        Btn_HapusBeli = new javax.swing.JButton();
        Btn_Batal = new javax.swing.JButton();
        Btn_Cetak = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Pembelian");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe Marker", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Data Pembelian Apotek \"99\"");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        JTable_Beli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JTable_Beli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "Kode Barang", "Nama Barang", "Jumlah Beli", "Harga", "Total"
            }
        ));
        JTable_Beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_BeliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable_Beli);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 500, 130));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tanggal  :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 50, -1));

        JT_TanggalPembelian.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_TanggalPembelian.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                JT_TanggalPembelianComponentShown(evt);
            }
        });
        JT_TanggalPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JT_TanggalPembelianActionPerformed(evt);
            }
        });
        jPanel1.add(JT_TanggalPembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 130, -1));

        jLabel4.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Kode Barang  :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, -1));

        JT_KodeBarangBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_KodeBarangBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 130, -1));

        jLabel5.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nama Barang  :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, -1));

        JT_NamaBarangBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_NamaBarangBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 130, -1));

        jLabel6.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Harga  :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 80, -1));

        JT_HargaBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_HargaBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 130, -1));

        jLabel7.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Total  :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 80, -1));

        JT_TotalBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_TotalBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JT_TotalBeliMouseClicked(evt);
            }
        });
        JT_TotalBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JT_TotalBeliActionPerformed(evt);
            }
        });
        jPanel1.add(JT_TotalBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 130, -1));

        JT_JumlahBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_JumlahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JT_JumlahBeliActionPerformed(evt);
            }
        });
        jPanel1.add(JT_JumlahBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 130, -1));

        jLabel8.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Jumlah Beli  :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, -1));

        JT_NomorTransaksi.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_NomorTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 130, -1));

        jLabel9.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nomor Trx  :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 240, 220));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JT_CariBarangBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_CariBarangBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JT_CariBarangBeliKeyReleased(evt);
            }
        });
        jPanel2.add(JT_CariBarangBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 150, -1));

        jLabel10.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Cari Barang  :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 20));

        JTable_DataBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JTable_DataBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Harga/pcs"
            }
        ));
        JTable_DataBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_DataBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JTable_DataBarang);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 230, 120));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 220));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Btn_TambahBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_TambahBeli.setText("Tambah");
        Btn_TambahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_TambahBeliActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_TambahBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        Btn_SimpanBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_SimpanBeli.setText("Simpan");
        Btn_SimpanBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanBeliActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_SimpanBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 80, -1));

        Btn_UbahBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_UbahBeli.setText("Ubah");
        Btn_UbahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UbahBeliActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_UbahBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, -1));

        Btn_HapusBeli.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_HapusBeli.setText("Hapus");
        Btn_HapusBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_HapusBeliActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_HapusBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 80, -1));

        Btn_Batal.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Batal.setText("Batal");
        Btn_Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_BatalActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_Batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 80, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 500, 50));

        Btn_Cetak.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Cetak.setText("Cetak");
        Btn_Cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_CetakActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_Cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 500, -1));
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 520, 20));
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 20, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_UbahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UbahBeliActionPerformed
        // TODO add your handling code here:
        try {
            String NomorTrx = JT_NomorTransaksi.getText().trim();
            String Tanggal = JT_TanggalPembelian.getText().trim();
            String KodeBarang = JT_KodeBarangBeli.getText().trim();
            String NamaBarang = JT_NamaBarangBeli.getText().trim();
            int JumlahBeli = Integer.parseInt(JT_JumlahBeli.getText().trim());
            int Harga = Integer.parseInt(JT_HargaBeli.getText().trim());
            double Total = (double) (JumlahBeli * (Harga - Harga * 0.10));

            String queryUpdate = "update tbl_pembelian set "
                    + "Tanggal = ('" + Tanggal + "'), "
                    + "KodeBarang = ('" + KodeBarang + "'), "
                    + "NamaBarang = ('" + NamaBarang + "'), "
                    + "JumlahBeli = ('" + JumlahBeli + "'), "
                    + "Harga = ('" + Harga + "'), "
                    + "Total = ('" + Total + "')"
                    + "where NomorTrx = ('" + NomorTrx + "')";
            Apotek.PBOTugasAkhir();
            Apotek.state.executeUpdate(queryUpdate);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah !!");
            tampilDataBeli();
            Bersih();
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_UbahBeliActionPerformed

    private void Btn_HapusBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_HapusBeliActionPerformed
        // TODO add your handling code here:
        try {
            String NomorTrx = JT_NomorTransaksi.getText().trim();
                        
            String queryDelete = "delete from tbl_pembelian where NomorTrx=('" + NomorTrx + "')";
            Apotek.PBOTugasAkhir();
            Apotek.state.executeUpdate(queryDelete);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus !!");
            tampilDataBeli();
            Bersih();
        } catch (SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_HapusBeliActionPerformed

    private void Btn_BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_BatalActionPerformed
        // TODO add your handling code here:
        Bersih();
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_BatalActionPerformed

    private void Btn_TambahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_TambahBeliActionPerformed
        // TODO add your handling code here:
        enableTrueTambah();
    }//GEN-LAST:event_Btn_TambahBeliActionPerformed

    private void Btn_SimpanBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SimpanBeliActionPerformed
        // TODO add your handling code here:
        String Tanggal = JT_TanggalPembelian.getText().trim();
        String KodeBarang = JT_KodeBarangBeli.getText().trim();
        String NamaBarang = JT_NamaBarangBeli.getText().trim();
        int JumlahBeli = Integer.parseInt(JT_JumlahBeli.getText().trim());
        int Harga = Integer.parseInt(JT_HargaBeli.getText().trim());
        double Total = (double) (JumlahBeli * (Harga - Harga * 0.10));

        Apotek.PBOTugasAkhir();

        try {
            String queryInsertBeli = "INSERT INTO tbl_pembelian"
                    + "(Tanggal, KodeBarang, NamaBarang,"
                    + "JumlahBeli, Harga, Total) VALUES "
                    + "('" + Tanggal + "', "
                    + "'" + KodeBarang + "', "
                    + "'" + NamaBarang + "', "
                    + "'" + JumlahBeli + "', "
                    + "'" + Harga + "', "
                    + "'" + Total + "')";
            Apotek.state.executeUpdate(queryInsertBeli);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan !!");
            Apotek.connect.close();
            tampilDataBeli();
            Bersih();
        } catch (SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_SimpanBeliActionPerformed

    private void JTable_BeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_BeliMouseClicked
        // TODO add your handling code here:
        enableTrueUbahHapus();
        int baris = JTable_Beli.getSelectedRow();
        JT_NomorTransaksi.setText(JTable_Beli.getValueAt(baris, 0).toString());
        JT_TanggalPembelian.setText(JTable_Beli.getValueAt(baris, 1).toString());
        JT_KodeBarangBeli.setText(JTable_Beli.getValueAt(baris, 2).toString());
        JT_NamaBarangBeli.setText(JTable_Beli.getValueAt(baris, 3).toString());
        JT_JumlahBeli.setText(JTable_Beli.getValueAt(baris, 4).toString());
        JT_HargaBeli.setText(JTable_Beli.getValueAt(baris, 5).toString());
        JT_TotalBeli.setText(JTable_Beli.getValueAt(baris, 6).toString());
    }//GEN-LAST:event_JTable_BeliMouseClicked

    private void JT_TotalBeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JT_TotalBeliMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_TotalBeliMouseClicked

    private void JT_TotalBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JT_TotalBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_TotalBeliActionPerformed

    private void JTable_DataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_DataBarangMouseClicked
        // TODO add your handling code here:
        enableTrueTambah();
        int baris = JTable_DataBarang.getSelectedRow();
        JT_KodeBarangBeli.setText(JTable_DataBarang.getValueAt(baris, 0).toString());
        JT_NamaBarangBeli.setText(JTable_DataBarang.getValueAt(baris, 1).toString());
        JT_HargaBeli.setText(JTable_DataBarang.getValueAt(baris, 2).toString());
        JT_CariBarangBeli.setText("");
    }//GEN-LAST:event_JTable_DataBarangMouseClicked

    private void Btn_CetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_CetakActionPerformed
        // TODO add your handling code here:
        MainLaporan laporan = new MainLaporan();
        laporan.namaLaporan = "reportPembelian";
        laporan.cetakLaporanMaster();
    }//GEN-LAST:event_Btn_CetakActionPerformed

    private void JT_CariBarangBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_CariBarangBeliKeyReleased
        // TODO add your handling code here:
        cariBarang();
    }//GEN-LAST:event_JT_CariBarangBeliKeyReleased

    private void JT_JumlahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JT_JumlahBeliActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_JT_JumlahBeliActionPerformed

    private void JT_TanggalPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JT_TanggalPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_TanggalPembelianActionPerformed

    private void JT_TanggalPembelianComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_JT_TanggalPembelianComponentShown
        
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_TanggalPembelianComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDataPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDataPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDataPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDataPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDataPembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Batal;
    private javax.swing.JButton Btn_Cetak;
    private javax.swing.JButton Btn_HapusBeli;
    private javax.swing.JButton Btn_SimpanBeli;
    private javax.swing.JButton Btn_TambahBeli;
    private javax.swing.JButton Btn_UbahBeli;
    private javax.swing.JTextField JT_CariBarangBeli;
    private javax.swing.JTextField JT_HargaBeli;
    private javax.swing.JTextField JT_JumlahBeli;
    private javax.swing.JTextField JT_KodeBarangBeli;
    private javax.swing.JTextField JT_NamaBarangBeli;
    private javax.swing.JTextField JT_NomorTransaksi;
    private javax.swing.JTextField JT_TanggalPembelian;
    private javax.swing.JTextField JT_TotalBeli;
    private javax.swing.JTable JTable_Beli;
    private javax.swing.JTable JTable_DataBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
