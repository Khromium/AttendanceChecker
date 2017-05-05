package khrom;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * DB関連の処理
 */
public class DataBase {
//    private String DB_NAME = "gakuseki.db";


    /**
     * DBにデータを加えます
     *
     * @param number
     */
    public static synchronized void addDB(File dbName, String number) {//デッドロックすることがあるのでsynchronized
        Connection connOrigin = null;
        Statement stmt;
        Properties prop = new Properties();
        prop.put("journal_mode", "MEMORY");//必要はないが高速化のため
        prop.put("sync_mode", "OFF");//高速化のため
        String dbHead = "jdbc:sqlite:";
        PreparedStatement pstmtOrigin;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            Class.forName("org.sqlite.JDBC");
            connOrigin = DriverManager.getConnection(dbHead + dbName.getAbsolutePath(), prop);
            connOrigin.setAutoCommit(false);
            stmt = connOrigin.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tenko ( date_time INTEGER, number String)");

            pstmtOrigin = connOrigin.prepareStatement("INSERT INTO tenko VALUES (?, ?)");
            String date = String.valueOf(calendar.get(Calendar.YEAR)) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.MONTH) + 1)) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.DATE))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.MINUTE))) +
                    String.valueOf(String.format("%02d", calendar.get(Calendar.SECOND)));
            pstmtOrigin.setDouble(1, Double.parseDouble(date));
            pstmtOrigin.setString(2, number);
            pstmtOrigin.addBatch();
            pstmtOrigin.executeBatch();
            connOrigin.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connOrigin != null) {
                try {
                    //接続を閉じる
                    connOrigin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * DBから重複を取り除いた学籍番号データを取得します。
     *
     * @param from
     * @param to
     * @return
     */
    public static Set<String> readDB(File dbName, String from, String to) {
        Connection connOrigin = null;
        Set<String> gakuseki = new HashSet<>();
        Properties prop = new Properties();
        prop.put("journal_mode", "MEMORY");//必要はないが高速化のため
        prop.put("sync_mode", "OFF");//高速化のため
        String dbHead = "jdbc:sqlite:";
        PreparedStatement pstmtOrigin;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            Class.forName("org.sqlite.JDBC");
            connOrigin = DriverManager.getConnection(dbHead + dbName.getAbsolutePath(), prop);
            connOrigin.setAutoCommit(false);
            pstmtOrigin = connOrigin.prepareStatement("SELECT * FROM tenko WHERE date_time >= " + from + " AND date_time <=" + to);
            ResultSet rs = pstmtOrigin.executeQuery();
            while (rs.next()) {
                gakuseki.add(rs.getString(2));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connOrigin != null) {
                try {
                    //接続を閉じる
                    connOrigin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return gakuseki;
    }

    /**
     * データベースファイルのコピーを行います
     *
     * @param from
     * @param to
     * @return
     */
    public static void dbCpy(File dbName, File saveDB, String from, String to) {
        Connection connOrigin = null;
        Connection connTo = null;
        Statement stmt;
        Properties prop = new Properties();
        prop.put("journal_mode", "MEMORY");//必要はないが高速化のため
        prop.put("sync_mode", "OFF");//高速化のため
        String dbHead = "jdbc:sqlite:";
        PreparedStatement pstmtOrigin;
        PreparedStatement pstmtTo;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            Class.forName("org.sqlite.JDBC");
            connOrigin = DriverManager.getConnection(dbHead + dbName.getAbsolutePath(), prop);
            connOrigin.setAutoCommit(false);
            connTo = DriverManager.getConnection(dbHead + saveDB.getAbsolutePath(), prop);
            connTo.setAutoCommit(false);
            stmt = connTo.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tenko ( date_time INTEGER, number String)");

            pstmtTo = connTo.prepareStatement("INSERT INTO tenko VALUES (?, ?)");

            pstmtOrigin = connOrigin.prepareStatement("SELECT * FROM tenko WHERE date_time >= " + from + " AND date_time <=" + to);
            ResultSet rs = pstmtOrigin.executeQuery();
            int i=0;
            while (rs.next()) {
                pstmtTo.setDouble(1,rs.getDouble(1));
                pstmtTo.setString(2,rs.getString(2));
                pstmtTo.addBatch();
                i++;
                if(i==1000) pstmtTo.executeBatch();
            }
            pstmtTo.executeBatch();
            connTo.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connOrigin != null) {
                try {
                    //接続を閉じる
                    connOrigin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * DBから重複を取り除いた学籍番号データを取得します。
     *
     * @return
     */
    public static Set<String> readAllData(File dbName) {
        Connection connOrigin = null;
        Set<String> gakuseki = new HashSet<>();
        Properties prop = new Properties();
        prop.put("journal_mode", "MEMORY");//必要はないが高速化のため
        prop.put("sync_mode", "OFF");//高速化のため
        String dbHead = "jdbc:sqlite:";
        PreparedStatement pstmtOrigin;
        try {
            Class.forName("org.sqlite.JDBC");
            connOrigin = DriverManager.getConnection(dbHead + dbName.getAbsolutePath(), prop);
            connOrigin.setAutoCommit(false);
            pstmtOrigin = connOrigin.prepareStatement("SELECT * FROM tenko");
            ResultSet rs = pstmtOrigin.executeQuery();
            while (rs.next()) {
                gakuseki.add(rs.getString(2));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connOrigin != null) {
                try {
                    //接続を閉じる
                    connOrigin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return gakuseki;
    }

}
