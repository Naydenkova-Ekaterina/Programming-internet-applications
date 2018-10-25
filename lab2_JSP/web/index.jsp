<%--
  Created by IntelliJ IDEA.
  User: ekaterina
  Date: 14.09.17
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>$Lab2$</title>
    <style>
      <%@include file='style.css' %>
    </style>
    <jsp:useBean id="resultBean" class="lab2.ResultBean" scope="session" ></jsp:useBean>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  </head>
  <body>
  <header>
    <p>Ковалёва А.А., Найденкова Е.Д., группа:Р3200, вариант:950</p>
  </header>

  <main>
    <form method="post" onsubmit="return validate()" action="./ControllerServlet">
      <table>
        <!--Begin first row-->
        <tr>
          <td><p>Выберите значение X (-5<=x<=5):</p></td>
          <td id="x_col">
            <input type="text" name="xValue" id="xValue">
          </td>
          <td class="error_mes" id="x_error">Некорректное значение X.</td>
        </tr>
        <!--End first row-->

        <!--Begin second row-->
        <tr>
          <td><p>Выберите значение Y:</p></td>
          <td id="y_col">
            <input type="button" value="-4" name="yValue" onclick="getYValue(this)">
            <input type="button" value="-3" name="yValue" onclick="getYValue(this)">
            <input type="button" value="-2" name="yValue" onclick="getYValue(this)">
            <input type="button" value="-1" name="yValue" onclick="getYValue(this)">
            <input type="button" value="0" name="yValue" onclick="getYValue(this)">
            <input type="button" value="1" name="yValue" onclick="getYValue(this)">
            <input type="button" value="2" name="yValue" onclick="getYValue(this)">
            <input type="button" value="3" name="yValue" onclick="getYValue(this)">
            <input type="button" value="4" name="yValue" onclick="getYValue(this)">
            <input type="text" name="yValue" id="hiddenY" >
          </td>
          <td class="error_mes" id="y_error">Некорректное значение Y.</td>
        </tr>
        <!--End second row-->

        <!--Begin third row-->
        <tr>
          <td><p>Выберите значение R:</p></td>
          <td id="r_col">
            <input type="button" value="1" name="rValue" onclick="getRValue(this)">
            <input type="button" value="2" name="rValue" onclick="getRValue(this)">
            <input type="button" value="3" name="rValue" onclick="getRValue(this)">
            <input type="button" value="4" name="rValue" onclick="getRValue(this)">
            <input type="button" value="5" name="rValue" onclick="getRValue(this)">
            <input type="text" name="rValue" id="hiddenR" >
          </td>
          <td class="error_mes" id="r_error">Некорректное значение R.</td>
        </tr>
        <!--End third row-->
        <tr>
          <td>
            <input type="submit" name="submit">
          </td>
        </tr>
      </table>

    </form>
    
    <canvas class="brd" id="graph" onclick="setPoint(event)" width="600" height="600" ></canvas>
  </main>

<script src="validation.js"></script>
  </body>
</html>
