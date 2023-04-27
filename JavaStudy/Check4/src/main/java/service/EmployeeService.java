package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.EmployeeBean;


/**
 * ・社員情報検索サービス
 */

public class EmployeeService {

  // 問① 接続情報を記述してください
 /** ドライバーのクラス名 */
 private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
 /** ・JDBC接続先情報 */
 private static final String JDBC_CONNECTION =  "jdbc:postgresql://localhost:5432/Employee" ;
 /** ・ユーザー名 */
 private static final String USER = "postgres";
 /** ・パスワード */
 private static final String PASS = "postgres";
 /** ・タイムフォーマット */
 private static final String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

  // 問② 入力された値で、UPDATEする文
 /** ・SQL UPDATE文 */
 private static final String SQL_UPDATE = "UPDATE employee_table SET login_time = ? WHERE id = ?";

  // 問③ 入力されたIDとPassWordをキーにして、検索するSELECT文
 /** ・SQL SELECT文 */
 private static final String SQL_SELECT = "select * from employee_table where id = ? AND password = ?";
 EmployeeBean employeeDate = null;

  // 送信されたIDとPassWordを元に社員情報を検索するためのメソッド
public EmployeeBean search(String id, String password) {
 Connection connection = null;
 Statement statement = null;
 ResultSet resultSet = null;
 PreparedStatement preparedStatement = null;

 try {
  // データベースに接続
//JDBCドライバをロードする
 Class.forName(POSTGRES_DRIVER);
 //DBへのコネクションを作成する、(ホスト名、ユーザー名、パスワード)
 connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
 //ステートメントの作成、connectionクラスのcreateStatement()メソッドで取得する
 statement = connection.createStatement();

  // 処理が流れた時間をフォーマットに合わせて生成
 //calendarクラスのオブジェクト生成
 Calendar cal = Calendar.getInstance();
 //SimpleDateformatクラスでフォーマットパターンを設定する(日付のフォーマット)
 SimpleDateFormat sdFormat = new SimpleDateFormat(TIME_FORMAT);
  // PreparedStatementで使用するため、String型に変換
 //現在時刻
 String login_time = sdFormat.format(cal.getTime());
 /*
 * 任意のユーザーのログインタイムを更新できるように、プリペアドステートメントを記述。
 */
  // preparedStatementに実行したいSQLを格納
 //パラメータ付きSQL,UPDATE文をDBに送る為のオブジェクト生成をする
 preparedStatement = connection.prepareStatement(SQL_UPDATE);
  // 問④ preparedStatementを使って、一番目のindexに今の時間をセットしてください。2番目のindexにIDをセットしてください。
 preparedStatement.setString(1, login_time);
 preparedStatement.setString(2, id);
  // 問⑤ UPDATEを実行する文を記入
 //前処理済のSQL文(検索系SQL以外のSQL文)を実行し、更新係数を返却する
 preparedStatement.executeUpdate();
 /*
 * UPDATEが成功したものを即座に表示
 * 任意のユーザーを検索できるように、プリペアドステートメントを記述。
 */
 //reparedStatementに実行したいSQLを格納
 preparedStatement = connection.prepareStatement(SQL_SELECT);
  //問⑥ 一番目のindexにIDをセットしてください。2番目のindexにPASSWORDをセット。
 preparedStatement.setString(1, id);
 preparedStatement.setString(2, password);
  // SQLを実行。実行した結果をresultSetに格納。
 //preparedStatementインターフェイスのexeQuerty()メソッドは引数で指定されたSQLをデータベースで実行するメソッドのこと
 //→何のエラーもなければSQLの実行結果を格納したResultSetインターフェイス型のオブジェクトを返す
 resultSet = preparedStatement.executeQuery();
 //resultSetから1レコードずつデータを取りだす
 while (resultSet.next()) {
  // 問⑦ tmpName,tmpComment,tmpLoginTimeに適当な値を入れてください。
//getString("","")内の引数にプロパティファイルのキーを引き渡す→キーに紐づいた値が取得できる
 String tmpName = resultSet.getString("name");
 String tmpComment = resultSet.getString("comment");
 String tmpLoginTime = resultSet.getString("login_time");

  // 問⑧ EmployeeBeanに取得したデータを入れてください。
 employeeDate= new EmployeeBean();
 employeeDate.setName(tmpName);
 employeeDate.setComment(tmpComment);
 employeeDate.setLogin_Time(tmpLoginTime);
 }

  // forName()で例外発生
 } catch (ClassNotFoundException e) {
 e.printStackTrace();

  // getConnection()、createStatement()、executeQuery()で例外発生
 } catch (SQLException e) {
 e.printStackTrace();

 } finally {
 try {

 if (resultSet != null) {
 resultSet.close();
 }
 if (statement != null) {
 statement.close();
 }
 if (connection != null) {
 //データベースを切断する
  connection.close();
 }
//例外クラス、eがインスタンス名
 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 //戻り値、呼び出し元へ返す
 return employeeDate;
 }
}