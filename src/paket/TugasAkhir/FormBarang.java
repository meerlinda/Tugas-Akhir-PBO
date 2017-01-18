/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket.TugasAkhir;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public final class FormBarang extends javax.swing.JFrame {
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
     * Creates new form FormBarang
     */
    public FormBarang() {
        initComponents();
        enableFalse();
        tampilDataBarang();
        CenterForm();
    }
    
    private void CenterForm(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }
    
    void tampilDataBarang() {
        DefaultTableModel tabel = new DefaultTableModel();

        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Jenis Barang");
        tabel.addColumn("Stok Barang");
        tabel.addColumn("Harga Satuan");
        
        JTable_DataBarang.setModel(tabel);

        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT * FROM tbl_barang";

            ResultSet ress = Apotek.state.executeQuery(querySelect);

            while (ress.next()) {
                tabel.addRow(new Object[]{
                    ress.getString("KodeBarang"), 
                    ress.getString("NamaBarang"), 
                    ress.getString("JenisBarang"), 
                    ress.getString("StokBarang"), 
                    ress.getString("HargaSatuan")
                });
                JTable_DataBarang.setModel(tabel);
            }
        } catch (SQLException e) {
            System.err.println(" "+e);
        }
        }
    
    private void Bersih() {
        JT_KodeBarang.setText("");
        JT_NamaBarang.setText("");
        JT_JenisBarang.setText("");
        JT_StokBarang.setText("");
        JT_HargaSatuan.setText("");
        JT_CariBarang.setText("");
    }
      
    void enableFalse(){
        Btn_Simpan.setEnabled(false);
        Btn_Ubah.setEnabled(false);
        Btn_Hapus.setEnabled(false);
        Btn_Batal.setEnabled(false);
        JT_KodeBarang.setEnabled(false);
        JT_NamaBarang.setEnabled(false);
        JT_JenisBarang.setEnabled(false);
        JT_StokBarang.setEnabled(false);
        JT_HargaSatuan.setEnabled(false);
        JT_CariBarang.setEnabled(false);
    }
    
    void enableTrueTambah(){
        Btn_Tambah.setEnabled(false);
        Btn_Simpan.setEnabled(true);
        Btn_Ubah.setEnabled(false);
        Btn_Hapus.setEnabled(false);
        Btn_Batal.setEnabled(true);
        JT_KodeBarang.setEnabled(false);
        JT_NamaBarang.setEnabled(true);
        JT_JenisBarang.setEnabled(true);
        JT_StokBarang.setEnabled(true);
        JT_HargaSatuan.setEnabled(true);
        JT_CariBarang.setEnabled(true);
    }
    
    void enableTrueUbahHapus(){
        Btn_Tambah.setEnabled(false);
        Btn_Simpan.setEnabled(false);
        Btn_Ubah.setEnabled(true);
        Btn_Hapus.setEnabled(true);
        Btn_Batal.setEnabled(true);
        JT_KodeBarang.setEnabled(false);
        JT_NamaBarang.setEnabled(true);
        JT_JenisBarang.setEnabled(true);
        JT_StokBarang.setEnabled(true);
        JT_HargaSatuan.setEnabled(true);
        JT_CariBarang.setEnabled(false);
    }
    
    void enableUbahHapusBatal(){
        Btn_Tambah.setEnabled(true);
        Btn_Simpan.setEnabled(false);
        Btn_Ubah.setEnabled(false);
        Btn_Hapus.setEnabled(false);
        Btn_Batal.setEnabled(false);
        JT_KodeBarang.setEnabled(false);
        JT_NamaBarang.setEnabled(false);
        JT_JenisBarang.setEnabled(false);
        JT_StokBarang.setEnabled(false);
        JT_HargaSatuan.setEnabled(false);
        JT_CariBarang.setEnabled(false);
    }
    
    public void cariBarang() {
        tabel.getDataVector().removeAllElements();
        try {
            Apotek.PBOTugasAkhir();
            String querySelect = "SELECT * FROM tbl_barang where NamaBarang like '%"+JT_CariBarang.getText()+"%'";
            
            ResultSet ress = Apotek.state.executeQuery(querySelect);
            
            while (ress.next()) {
                tabel.addRow(new Object[]{
                    ress.getString("KodeBarang"), 
                    ress.getString("NamaBarang"),
                    ress.getString("JenisBarang"),
                    ress.getString("StokBarang"),
                    ress.getString("HargaSatuan")
                });
                JTable_DataBarang.setModel(tabel);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "eror tampil : " + e);
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_DataBarang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        JT_HargaSatuan = new javax.swing.JTextField();
        JT_StokBarang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        JT_JenisBarang = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        JT_CariBarang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Btn_Tambah = new javax.swing.JButton();
        Btn_Simpan = new javax.swing.JButton();
        Btn_Ubah = new javax.swing.JButton();
        Btn_Hapus = new javax.swing.JButton();
        Btn_Batal = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        JT_NamaBarang = new javax.swing.JTextField();
        JT_KodeBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Btn_Cetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Barang");
        setBackground(new java.awt.Color(255, 153, 153));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(255, 153, 153));
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe Marker", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Data Barang Apotek \"99\"");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        JTable_DataBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JTable_DataBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jenis Barang", "Stok Barang", "Harga/pcs"
            }
        ));
        JTable_DataBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_DataBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable_DataBarang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 500, 110));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Harga/pcs  :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 90, -1));

        jLabel5.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Jenis Barang  :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, -1));

        JT_HargaSatuan.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_HargaSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 130, -1));

        JT_StokBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_StokBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 130, -1));

        jLabel8.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Stok Barang  :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 90, -1));

        JT_JenisBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel1.add(JT_JenisBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 130, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 240, 100));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JT_CariBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        JT_CariBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JT_CariBarangKeyReleased(evt);
            }
        });
        jPanel2.add(JT_CariBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 390, -1));

        jLabel10.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Cari Barang  :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 20, -1, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 500, 60));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Btn_Tambah.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Tambah.setText("Tambah");
        Btn_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_TambahActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_Tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        Btn_Simpan.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Simpan.setText("Simpan");
        Btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_Simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 80, -1));

        Btn_Ubah.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Ubah.setText("Ubah");
        Btn_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_UbahActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_Ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, -1));

        Btn_Hapus.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Hapus.setText("Hapus");
        Btn_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_HapusActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_Hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 80, -1));

        Btn_Batal.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Batal.setText("Batal");
        Btn_Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_BatalActionPerformed(evt);
            }
        });
        jPanel3.add(Btn_Batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 80, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 500, 50));
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 10, 400));
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 520, 20));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setForeground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nama Barang  :");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 90, -1));

        JT_NamaBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel6.add(JT_NamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 130, -1));

        JT_KodeBarang.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jPanel6.add(JT_KodeBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 130, -1));

        jLabel3.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Kode Barang  :");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 250, 100));

        Btn_Cetak.setFont(new java.awt.Font("Segoe Marker", 0, 14)); // NOI18N
        Btn_Cetak.setText("Cetak");
        Btn_Cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_CetakActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_Cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 500, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_UbahActionPerformed
        // TODO add your handling code here:        
        try {
            String KodeBarang = JT_KodeBarang.getText().trim();
            String NamaBarang = JT_NamaBarang.getText().trim();
            String JenisBarang = JT_JenisBarang.getText().trim();
            int StokBarang = Integer.parseInt(JT_StokBarang.getText().trim());
            int HargaSatuan = Integer.parseInt(JT_HargaSatuan.getText().trim());

            String queryUpdate = "update tbl_barang set "
                    + "NamaBarang = ('" + NamaBarang + "'), "
                    + "JenisBarang = ('" + JenisBarang + "'), "
                    + "StokBarang = ('" + StokBarang + "'), "
                    + "HargaSatuan = ('" + HargaSatuan + "')"
                    + "where KodeBarang = ('" + KodeBarang + "')";
            Apotek.PBOTugasAkhir();
            Apotek.state.executeUpdate(queryUpdate);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah !!");
            tampilDataBarang();
            Bersih();
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_UbahActionPerformed

    private void Btn_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_HapusActionPerformed
        // TODO add your handling code here:
        try {
            String KodeBarang = JT_KodeBarang.getText().trim();
                        
            String queryDelete = "delete from tbl_barang where KodeBarang=('" + KodeBarang + "')";
            Apotek.PBOTugasAkhir();
            Apotek.state.executeUpdate(queryDelete);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus !!");
            tampilDataBarang();
            Bersih();
        } catch (SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_HapusActionPerformed

    private void Btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SimpanActionPerformed
        // TODO add your handling code here:
        String NamaBarang = JT_NamaBarang.getText().trim();
        String JenisBarang = JT_JenisBarang.getText().trim();
        int StokBarang = Integer.parseInt(JT_StokBarang.getText().trim());
        int HargaSatuan = Integer.parseInt(JT_HargaSatuan.getText().trim());

        Apotek.PBOTugasAkhir();

        try {
            String queryInsert = "INSERT INTO tbl_barang"
                    + "(NamaBarang, JenisBarang, StokBarang,"
                    + "HargaSatuan) VALUES "
                    + "('" + NamaBarang + "',"
                    + "'" + JenisBarang + "',"
                    + "'" + StokBarang + "',"
                    + "'" + HargaSatuan + "')";
            Apotek.state.executeUpdate(queryInsert);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan !!");
            Apotek.connect.close();
            tampilDataBarang();
            Bersih();
        } catch (SQLException e) {
            System.err.println("" + e);
            Bersih();
        }
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_SimpanActionPerformed

    private void Btn_BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_BatalActionPerformed
        // TODO add your handling code here:
        Bersih();
        enableUbahHapusBatal();
    }//GEN-LAST:event_Btn_BatalActionPerformed

    private void Btn_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_TambahActionPerformed
        // TODO add your handling code here:
        enableTrueTambah();
    }//GEN-LAST:event_Btn_TambahActionPerformed

    private void JTable_DataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_DataBarangMouseClicked
        // TODO add your handling code here:
        enableTrueUbahHapus();
        int baris = JTable_DataBarang.getSelectedRow();
        JT_KodeBarang.setText(JTable_DataBarang.getValueAt(baris, 0).toString());
        JT_NamaBarang.setText(JTable_DataBarang.getValueAt(baris, 1).toString());
        JT_JenisBarang.setText(JTable_DataBarang.getValueAt(baris, 2).toString());
        JT_StokBarang.setText(JTable_DataBarang.getValueAt(baris, 3).toString());
        JT_HargaSatuan.setText(JTable_DataBarang.getValueAt(baris, 4).toString());
        JT_CariBarang.setText("");
    }//GEN-LAST:event_JTable_DataBarangMouseClicked

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowStateChanged

    private void JT_CariBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_CariBarangKeyReleased
        // TODO add your handling code here:
        cariBarang();
    }//GEN-LAST:event_JT_CariBarangKeyReleased

    private void Btn_CetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_CetakActionPerformed
        // TODO add your handling code here:
        MainLaporan laporan = new MainLaporan();
        laporan.namaLaporan = "reportBarang";
        laporan.cetakLaporanMaster();
    }//GEN-LAST:event_Btn_CetakActionPerformed

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
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Batal;
    private javax.swing.JButton Btn_Cetak;
    private javax.swing.JButton Btn_Hapus;
    private javax.swing.JButton Btn_Simpan;
    private javax.swing.JButton Btn_Tambah;
    private javax.swing.JButton Btn_Ubah;
    private javax.swing.JTextField JT_CariBarang;
    private javax.swing.JTextField JT_HargaSatuan;
    private javax.swing.JTextField JT_JenisBarang;
    private javax.swing.JTextField JT_KodeBarang;
    private javax.swing.JTextField JT_NamaBarang;
    private javax.swing.JTextField JT_StokBarang;
    private javax.swing.JTable JTable_DataBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
