package controller;

/**
 * 社員情報管理コントローラー
 */
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.EmployeeBean;
import service.EmployeeService;


public class EmployeeController extends HttpServlet {
 private static final long serialVersionUID = 1L;

 //HTTPServletを継承する、パラメータ情報をFORMタグに含めてサーブレットへ投げる
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    //例外を発生させる
      throws ServletException, IOException {

 try {
  // 問① index.htmlから送信されたIDとPassWordの値を取得できるように修正しましょう。＝リクエストパラメータ
 String id = request.getParameter("id");
 String password = request.getParameter("password");
 /*
 * IDとPassWordと元に、社員情報を検索する関数の呼び出し、結果をJSPに渡す処理
 * ※ EmployeeBeanとEmployeeServiceをimportするのを忘れないでください。
 */

 // 問② EmployeeServiceクラスをインスタンス化する。＝クラス名　インスタンス名　=　new クラス名();
  EmployeeService emps= new EmployeeService();

  // 問③ EmployeeBeanに、EmployeeServiceよりsearch関数を呼び出し、返り値を格納する。
 EmployeeBean returndata=  emps.search(id, password);

 // 問④ nullの部分に適切な引数をセットする。
 //EmployeeBeanオブジェクトを登録している
 //requestの中にEmployeeBeanという名前をつけた(null)という文字列を格納している
 //request.setattribute("keyとして呼び出す","指定した値が画角に出力");  or  setAttribute("属性の名前","新しい属性");
 request.setAttribute("EmployeeBean" , returndata);

 //例外が発生したら実行する
 //catchブロックでprintStacktraceメソッドを使ってメッセージを出力する
 } catch (Exception e) {
 e.printStackTrace();

 //webアプリケーションに何らかのイベントが発生した際にそれをプログラムに通知する機能のこと
 //this.getServletContextメソッドを使用して、ServletContextオブジェクトを取得する
 } finally {
 ServletContext context = this.getServletContext();

 //リクエストスコープに格納した値をwebに表示する
 //getRequestDispatcherの引数に(/index.jsp)を指定して、サーブレット処理をフォワードする(呼び出す)
 RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
 dispatcher.forward(request, response);
 }
 }
}

//set=格納、get=取り出し