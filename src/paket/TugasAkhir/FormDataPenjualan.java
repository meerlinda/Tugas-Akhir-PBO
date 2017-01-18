/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket.TugasAkhir;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Date;
import java.sql.DriverManager;
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
public class FormDataPenjualan extends javax.swing.JFrame {
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
     * Creates new form FormDataPenjualan
     */
    public FormDataPenjualan() {
        initComponents();
        CenterForm();
        enableFalse();
        tampilDataJual();
        tampilDataBarang();
        tanggalPenjualan();
    }
    
    private void CenterForm(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }
    
    void tampilDataJual() {
        DefaultTableModel tabel = new DefaultTableModel();

        tabel.addColumn("Nomor Trx");
        tabel.addColumn("Tanggal");
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Jumlah Jual");
        tabel.addColumn("Harga");
        tabel.addColumn("Total");
        
        JTable_Jual.setModel(tabel);

        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT * FROM tbl_penjualan";

            ResultSet ress = Apotek.state.executeQuery(querySelect);

            while (ress.next()) {
                tabel.addRow(new Object[]{
                    ress.getString("NomorTrx"),
                    ress.getString("Tanggal"), 
                    ress.getString("KodeBarang"), 
                    ress.getString("NamaBarang"), 
                    ress.getString("JumlahJual"), 
                    ress.getString("Harga"), 
                    ress.getString("Total")
                });
                JTable_Jual.setModel(tabel);
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
            String querySelect = "SELECT * FROM tbl_barang";

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
    
    public void cariBarangJual() {
        tabel.getDataVector().removeAllElements();
        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT * FROM tbl_barang where NamaBarang like '%"+JT_CariBarangJual.getText()+"%'";
            
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
    
    private void Bersih() {
        JT_NomorPenjualan.setText("");
        JT_TanggalPenjualan.setText("");
        JT_KodeBarangJual.setText("");
        JT_NamaBarangJual.setText("");
        JT_JumlahJual.setText("");
        JT_HargaJual.setText("");
        JT_TotalJual.setText("");
        JT_CariBarangJual.setText("");
    }
      
    void enableFalse(){
        Btn_SimpanJual.setEnabled(false);
        Btn_UbahJual.setEnabled(false);
        Btn_HapusJual.setEnabled(false);
        Btn_BatalJual.setEnabled(false);
        JT_NomorPenjualan.setEnabled(false);
        JT_TanggalPenjualan.setEnabled(false);
        JT_KodeBarangJual.setEnabled(false);
        JT_NamaBarangJual.setEnabled(false);
        JT_JumlahJual.setEnabled(false);
        JT_HargaJual.setEnabled(false);
        JT_TotalJual.setEnabled(false);
        JT_CariBarangJual.setEnabled(false);
        JTable_DataBarang.setVisible(false);
    }
    
    void enableTrueTambah(){
        Btn_TambahJual.setEnabled(false);
        Btn_SimpanJual.setEnabled(true);
        Btn_UbahJual.setEnabled(false);
        Btn_HapusJual.setEnabled(false);
        Btn_BatalJual.setEnabled(true);
        JT_NomorPenjualan.setEnabled(false);
        JT_TanggalPenjualan.setEnabled(false);
        JT_KodeBarangJual.setEnabled(false);
        JT_NamaBarangJual.setEnabled(false);
        JT_JumlahJual.setEnabled(true);
        JT_HargaJual.setEnabled(false);
        JT_TotalJual.setEnabled(false);
        JT_CariBarangJual.setEnabled(true);
        JTable_DataBarang.setVisible(true);
    }
    
    void enableTrueUbahHapus(){
        Btn_TambahJual.setEnabled(false);
        Btn_SimpanJual.setEnabled(false);
        Btn_UbahJual.setEnabled(true);
        Btn_HapusJual.setEnabled(true);
        Btn_BatalJual.setEnabled(true);
        JT_NomorPenjualan.setEnabled(false);
        JT_TanggalPenjualan.setEnabled(false);
        JT_KodeBarangJual.setEnabled(false);
        JT_NamaBarangJual.setEnabled(false);
        JT_JumlahJual.setEnabled(true);
        JT_HargaJual.setEnabled(false);
        JT_TotalJual.setEnabled(false);
        JT_CariBarangJual.setEnabled(false);
        JTable_DataBarang.setVisible(false);
    }
    
    void enableUbahHapusBatal(){
        Btn_TambahJual.setEnabled(true);
        Btn_SimpanJual.setEnabled(false);
        Btn_UbahJual.setEnabled(false);
        Btn_HapusJual.setEnabled(false);
        Btn_BatalJual.setEnabled(false);
        JT_NomorPenjualan.setEnabled(false);
        JT_TanggalPenjualan.setEnabled(false);
        JT_KodeBarangJual.setEnabled(false);
        JT_NamaBarangJual.setEnabled(false);
        JT_JumlahJual.setEnabled(false);
        JT_HargaJual.setEnabled(false);
        JT_TotalJual.setEnabled(false);
        JT_CariBarangJual.setEnabled(false);
        JTable_DataBarang.setVisible(false);
    }
    
    void tanggalPenjualan(){
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        JT_TanggalPenjualan.setText(String.valueOf(sdf.format(new java.util.Date())));
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
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        JT_KodeBarangJual = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        JT_NamaBarangJual = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        JT_HargaJual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        JT_TotalJual = new javax.swing.JTextField();
        JT_JumlahJual = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        JT_NomorPenjualan = new javax.swing.JTextField();
        JT_TanggalPenjualan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_DataBarang = new javax.swing.JTable();
        JT_CariBarangJual = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Btn_TambahJual = new javax.swing.JButton();
        Btn_SimpanJual = new javax.swing.JButton();
        Btn_UbahJual = new javax.swing.JButton();
        Btn_BatalJual = new javax.swing.JButton();
        Btn_HapusJual = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTable_Jual = new javax.swing.JTable();
        Btn_Cetak = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Penjualan");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe Marker", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Data Penjualan Apotek \"99\"");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tanggal  :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 60, -1));

        jLabel4.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Kode Barang  :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, -1));

        JT_KodeBarangJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_KodeBarangJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 130, -1));

        jLabel5.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nama Barang  :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, -1));

        JT_NamaBarangJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_NamaBarangJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JT_NamaBarangJualActionPerformed(evt);
            }
        });
        JT_NamaBarangJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JT_NamaBarangJualKeyReleased(evt);
            }
        });
        jPanel1.add(JT_NamaBarangJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 130, -1));

        jLabel6.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Harga  :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 60, -1));

        JT_HargaJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_HargaJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 130, -1));

        jLabel7.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Total  :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 60, -1));

        JT_TotalJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_TotalJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 130, -1));

        JT_JumlahJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_JumlahJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JT_JumlahJualKeyPressed(evt);
            }
        });
        jPanel1.add(JT_JumlahJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 130, -1));

        jLabel8.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Jumlah Jual  :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 70, -1));

        jLabel9.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nomor Trx  :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        JT_NomorPenjualan.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_NomorPenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 130, -1));

        JT_TanggalPenjualan.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_TanggalPenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 130, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 240, 220));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane1.setViewportView(JTable_DataBarang);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 230, 130));

        JT_CariBarangJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_CariBarangJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JT_CariBarangJualActionPerformed(evt);
            }
        });
        JT_CariBarangJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JT_CariBarangJualKeyReleased(evt);
            }
        });
        jPanel2.add(JT_CariBarangJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 150, -1));

        jLabel10.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Cari Barang  :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 220));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Btn_TambahJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_TambahJual.setText("Tambah");
        Btn_TambahJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_TambahJualActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_TambahJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        Btn_SimpanJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_SimpanJual.setText("Simpan");
        Btn_SimpanJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanJualActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_SimpanJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 80, -1));

        Btn_UbahJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_UbahJual.setText("Ubah");
        Btn_UbahJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UbahJualActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_UbahJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, -1));

        Btn_BatalJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_BatalJual.setText("Batal");
        Btn_BatalJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_BatalJualActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_BatalJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 80, -1));

        Btn_HapusJual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_HapusJual.setText("Hapus");
        Btn_HapusJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_HapusJualActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_HapusJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 80, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 500, 50));

        JTable_Jual.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JTable_Jual.setModel(new javax.swing.table.DefaultTableModel(
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
        JTable_Jual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_JualMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JTable_Jual);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 500, 130));

        Btn_Cetak.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Cetak.setText("Cetak");
        Btn_Cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_CetakActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_Cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 500, -1));
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 520, 20));
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 20, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_UbahJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UbahJualActionPerformed
        // TODO add your handling code here:
        try {
            String NomorTrx = JT_NomorPenjualan.getText().trim();
            String Tanggal = JT_TanggalPenjualan.getText().trim();
            String KodeBarang = JT_KodeBarangJual.getText().trim();
            String NamaBarang = JT_NamaBarangJual.getText().trim();
            int JumlahJual = Integer.parseInt(JT_JumlahJual.getText().trim());
            int Harga = Integer.parseInt(JT_HargaJual.getText().trim());
            int Total = (JumlahJual * Harga);

            String queryUpdate = "update tbl_penjualan set "
                    + "Tanggal = ('" + Tanggal + "'), "
                    + "KodeBarang = ('" + KodeBarang + "'), "
                    + "NamaBarang = ('" + NamaBarang + "'), "
                    + "JumlahJual = ('" + JumlahJual + "'), "
                    + "Harga = ('" + Harga + "'), "
                    + "Total = ('" + Total + "')"
                    + "where NomorTrx = ('" + NomorTrx + "')";
            Apotek.PBOTugasAkhir();
            Apotek.state.executeUpdate(queryUpdate);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah !!");
            tampilDataJual();
            Bersih();
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_UbahJualActionPerformed

    private void Btn_HapusJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_HapusJualActionPerformed
        // TODO add your handling code here:
        try {
            String NomorTrx = JT_NomorPenjualan.getText().trim();
                        
            String queryDelete = "delete from tbl_penjualan where NomorTrx=('" + NomorTrx + "')";
            Apotek.PBOTugasAkhir();
            Apotek.state.executeUpdate(queryDelete);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus !!");
            tampilDataJual();
            Bersih();
        } catch (SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_HapusJualActionPerformed

    private void Btn_BatalJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_BatalJualActionPerformed
        // TODO add your handling code here:
        Bersih();
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_BatalJualActionPerformed

    private void Btn_TambahJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_TambahJualActionPerformed
        // TODO add your handling code here:
        enableTrueTambah();
    }//GEN-LAST:event_Btn_TambahJualActionPerformed

    private void Btn_SimpanJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SimpanJualActionPerformed
        // TODO add your handling code here:
        String Tanggal = JT_TanggalPenjualan.getText().trim();
        String KodeBarang = JT_KodeBarangJual.getText().trim();
        String NamaBarang = JT_NamaBarangJual.getText().trim();
        int JumlahJual = Integer.parseInt(JT_JumlahJual.getText().trim());
        int Harga = Integer.parseInt(JT_HargaJual.getText().trim());
        int Total = (JumlahJual * Harga);

        Apotek.PBOTugasAkhir();

        try {
            String queryInsertBeli = "INSERT INTO tbl_penjualan"
                    + "(Tanggal, KodeBarang, NamaBarang,"
                    + "JumlahJual, Harga, Total) VALUES "
                    + "('" + Tanggal + "', "
                    + "'" + KodeBarang + "', "
                    + "'" + NamaBarang + "', "
                    + "'" + JumlahJual + "', "
                    + "'" + Harga + "', "
                    + "'" + Total + "')";
            Apotek.state.executeUpdate(queryInsertBeli);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan !!");
            Apotek.connect.close();
            tampilDataJual();
            Bersih();
        } catch (SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_SimpanJualActionPerformed

    private void JT_NamaBarangJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JT_NamaBarangJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_NamaBarangJualActionPerformed

    private void JTable_DataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_DataBarangMouseClicked
        // TODO add your handling code here:
        enableTrueTambah();
        int baris = JTable_DataBarang.getSelectedRow();
        JT_KodeBarangJual.setText(JTable_DataBarang.getValueAt(baris, 0).toString());
        JT_NamaBarangJual.setText(JTable_DataBarang.getValueAt(baris, 1).toString());
        JT_HargaJual.setText(JTable_DataBarang.getValueAt(baris, 2).toString());
        JT_CariBarangJual.setText("");
    }//GEN-LAST:event_JTable_DataBarangMouseClicked

    private void JT_NamaBarangJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_NamaBarangJualKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_NamaBarangJualKeyReleased

    private void JTable_JualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_JualMouseClicked
        // TODO add your handling code here:
        enableTrueUbahHapus();
        int baris = JTable_Jual.getSelectedRow();
        JT_NomorPenjualan.setText(JTable_Jual.getValueAt(baris, 0).toString());
        JT_TanggalPenjualan.setText(JTable_Jual.getValueAt(baris, 1).toString());
        JT_KodeBarangJual.setText(JTable_Jual.getValueAt(baris, 2).toString());
        JT_NamaBarangJual.setText(JTable_Jual.getValueAt(baris, 3).toString());
        JT_JumlahJual.setText(JTable_Jual.getValueAt(baris, 4).toString());
        JT_HargaJual.setText(JTable_Jual.getValueAt(baris, 5).toString());
        JT_TotalJual.setText(JTable_Jual.getValueAt(baris, 6).toString());
    }//GEN-LAST:event_JTable_JualMouseClicked

    private void JT_CariBarangJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_CariBarangJualKeyReleased
        // TODO add your handling code here:
        cariBarangJual();
    }//GEN-LAST:event_JT_CariBarangJualKeyReleased

    private void JT_CariBarangJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JT_CariBarangJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JT_CariBarangJualActionPerformed

    private void Btn_CetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_CetakActionPerformed
        // TODO add your handling code here:   
        MainLaporan laporan = new MainLaporan();
        laporan.namaLaporan = "reportPenjualan";
        laporan.cetakLaporanMaster();
    }//GEN-LAST:event_Btn_CetakActionPerformed

    private void JT_JumlahJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_JumlahJualKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_JT_JumlahJualKeyPressed

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
            java.util.logging.Logger.getLogger(FormDataPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDataPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDataPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDataPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDataPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_BatalJual;
    private javax.swing.JButton Btn_Cetak;
    private javax.swing.JButton Btn_HapusJual;
    private javax.swing.JButton Btn_SimpanJual;
    private javax.swing.JButton Btn_TambahJual;
    private javax.swing.JButton Btn_UbahJual;
    private javax.swing.JTextField JT_CariBarangJual;
    private javax.swing.JTextField JT_HargaJual;
    private javax.swing.JTextField JT_JumlahJual;
    private javax.swing.JTextField JT_KodeBarangJual;
    private javax.swing.JTextField JT_NamaBarangJual;
    private javax.swing.JTextField JT_NomorPenjualan;
    private javax.swing.JTextField JT_TanggalPenjualan;
    private javax.swing.JTextField JT_TotalJual;
    private javax.swing.JTable JTable_DataBarang;
    private javax.swing.JTable JTable_Jual;
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
