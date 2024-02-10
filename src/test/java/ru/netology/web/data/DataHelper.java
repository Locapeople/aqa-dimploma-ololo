package ru.netology.web.data;

import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DataHelper {

  public static class DBHelper {
    static final String url = System.getProperty("db.url");
    static final String user = System.getProperty("db.user");
    static final String password = System.getProperty("db.password");

    private static Connection connect() throws SQLException {
      return DriverManager.getConnection(url, user, password);
    }

    private static String execQuery(String query) throws SQLException {
      return new QueryRunner().query(connect(), query, new ScalarHandler<>());
    }

    public static void clear() throws SQLException {
      val deleteOrderQuery = "DELETE FROM order_entity;";
      val deleteDirectPurchaseQuery = "DELETE FROM payment_entity;";
      val deleteCreditQuery = "DELETE FROM credit_request_entity;";
      try (val conn = connect()) {
        val runner = new QueryRunner();
        runner.update(conn, deleteOrderQuery);
        runner.update(conn, deleteDirectPurchaseQuery);
        runner.update(conn, deleteCreditQuery);
      }
    }

    public static Long count() throws SQLException {
      return new QueryRunner().query(
              connect(),
              "SELECT COUNT(*) FROM order_entity;",
              new ScalarHandler<>());
    }

    public static String getDirectPurchaseStatus() throws SQLException {
      return execQuery("SELECT status FROM payment_entity;");
    }

    public static String getCreditStatus() throws SQLException {
      return execQuery("SELECT status FROM credit_request_entity;");
    }
  }
}
