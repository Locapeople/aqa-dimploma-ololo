package ru.netology.web.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DataHelper {

  public static class DBHelper {
    static final String url = System.getProperty("db.url");
    static final String user = System.getProperty("db.user");
    static final String password = System.getProperty("db.password");

    @SneakyThrows
    private static Connection connect() {
      return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    private static String execQuery(String query) {
      return new QueryRunner().query(connect(), query, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void clear() {
      var deleteOrderQuery = "DELETE FROM order_entity;";
      var deleteDirectPurchaseQuery = "DELETE FROM payment_entity;";
      var deleteCreditQuery = "DELETE FROM credit_request_entity;";
      var conn = connect();
      var runner = new QueryRunner();
      runner.update(conn, deleteOrderQuery);
      runner.update(conn, deleteDirectPurchaseQuery);
      runner.update(conn, deleteCreditQuery);
    }

    @SneakyThrows
    public static Long count() {
      return new QueryRunner().query(
              connect(),
              "SELECT COUNT(*) FROM order_entity;",
              new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getDirectPurchaseStatus() {
      return execQuery("SELECT status FROM payment_entity;");
    }

    @SneakyThrows
    public static String getCreditStatus() {
      return execQuery("SELECT status FROM credit_request_entity;");
    }
  }

  public static class DateHelper {

    public static String getDueMonthWithOffset(String offset) {
      LocalDate today = LocalDate.now();
      int intOffset = Integer.parseInt(offset);
      LocalDate newDate = intOffset > 0 ? today.plusMonths(intOffset) : today.minusMonths(intOffset);
      return newDate.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getDueYearWithOffset(String offset) {
      LocalDate today = LocalDate.now();
      int intOffset = Integer.parseInt(offset);
      LocalDate newDate = intOffset > 0 ? today.plusYears(intOffset) : today.minusYears(intOffset);
      return newDate.format(DateTimeFormatter.ofPattern("uu"));
    }
  }
}
