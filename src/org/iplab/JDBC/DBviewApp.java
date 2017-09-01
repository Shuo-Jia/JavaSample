package org.iplab.JDBC;

import javax.sql.RowSet;
import javax.sql.rowset.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by js982 on 2017/8/31.
 * 由于缓存和数据库在更新时出现冲突，所以产生了异常，是本程序的BUG，暂时未解决！
 */
public class DBviewApp {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new ViewDBFrame();
                frame.setVisible(true);
                frame.setTitle("viewDB");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
class ViewDBFrame extends JFrame{
    private JButton prebutton;
    private JButton nextbutton;
    private JButton delbutton;
    private JButton savebutton;
    private DataPanel dataPanel;
    private Component scrollPane;
    private JComboBox<String> tableNames;
    private Properties props;
    private CachedRowSet crs;

    public ViewDBFrame() {
        tableNames = new JComboBox<String>();
        tableNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTable((String) tableNames.getSelectedItem());
            }
        });
        add(tableNames, BorderLayout.NORTH);
        try {
            readDatabaseProperties();
            try (Connection conn = getConnection()) {
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet mrs = meta.getTables(null, null, null, new String[]{"TABLE"});
                while (mrs.next()){
                    tableNames.addItem(mrs.getString(3));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        prebutton = new JButton("Previous");
        prebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousRow();
            }
        });
        buttonPanel.add(prebutton);

        nextbutton = new JButton("Next");
        nextbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextRow();
            }
        });
        buttonPanel.add(nextbutton);

        delbutton = new JButton("Delete");
        delbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow();
            }
        });
        buttonPanel.add(delbutton);

        savebutton = new JButton("Save");
        savebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChange();
            }
        });
        buttonPanel.add(savebutton);

        pack();
    }

    private void showTable(String tableName) {
        try{
            try(Connection conn = getConnection()){
                Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE);
                ResultSet result = stat.executeQuery("SELECT * FROM " + tableName);

                RowSetFactory factory = RowSetProvider.newFactory();
                crs = factory.createCachedRowSet();
                crs.setTableName(tableName);
                crs.populate(result);
            }

            if(scrollPane != null)
                remove(scrollPane);
            dataPanel = new DataPanel(crs);
            scrollPane = new JScrollPane(dataPanel);
            add(scrollPane, BorderLayout.CENTER);
            validate();
            showNextRow();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void showPreviousRow() {
        try{
            if(crs == null || crs.isFirst())
                return;
            crs.previous();
            dataPanel.showRow(crs);
        } catch (SQLException e) {
            for(Throwable t : e)
                JOptionPane.showMessageDialog(this, e);
        }
    }

    private void showNextRow() {
        try{
            if(crs == null || crs.isLast())
                return;
            crs.next();
            dataPanel.showRow(crs);
            //showNextRow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void deleteRow() {
        try {
            try (Connection conn = getConnection()) {
                crs.deleteRow();
                crs.acceptChanges(conn);
                if (crs.isAfterLast())
                    if (!crs.last())
                        crs = null;
                dataPanel.showRow(crs);
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void saveChange() {
        try {
            try (Connection conn = getConnection()) {
                dataPanel.setRow(crs);
                crs.acceptChanges(conn);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void readDatabaseProperties() throws IOException{
        props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("G:\\WORK_FILE\\JAVA\\JavaSample\\src\\org\\iplab\\JDBC\\database.properties"))){
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if(drivers != null)
            System.setProperty("jdbc.drivers",drivers);
    }

    public Connection getConnection() throws SQLException{
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }
}

class DataPanel extends JPanel{
    private java.util.List<JTextField> fields;
    private CachedRowSet row;

    public DataPanel(RowSet rs) throws SQLException{
        fields = new ArrayList<>();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++){
            gbc.gridy = i - 1;
            String columnName = rsmd.getColumnLabel(i);
            gbc.gridx = 0;
            gbc.anchor =GridBagConstraints.EAST;
            add(new JLabel(columnName), gbc);

            int columnWidth = rsmd.getColumnDisplaySize(i);
            JTextField tb = new JTextField(columnWidth);
            if(!rsmd.getColumnClassName(i).equals("java.lang.String"))
                tb.setEditable(false);

            fields.add(tb);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(tb, gbc);
        }
    }

    public void showRow(ResultSet rs) throws SQLException {
        for(int i = 1; i <= fields.size(); i++){
            String field = rs == null ? "":rs.getString(i);
            JTextField tb = fields.get(i - 1);
            tb.setText(field);
        }
    }

    public void setRow(ResultSet rs) throws SQLException {
        for(int i = 1; i <= fields.size(); i++){
            String field = rs.getString(i);
            JTextField tb = fields.get(i - 1);
            if(!field.equals(tb.getText()))
                rs.updateString(i, tb.getText());
        }
        rs.updateRow();
    }
}
