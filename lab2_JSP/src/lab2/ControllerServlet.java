package lab2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out=response.getWriter();
        String x=request.getParameter("xValue");
        String y=request.getParameter("yValue");
        String r=request.getParameter("rValue");
        response.setContentType("text/html");
        response.setHeader("Content-Type","text/html; charset=UTF-8");
        out.println("<p>in if</p>");
        out.println("<p>X: "+x+"</p>");
        out.println("<p>Y: "+y+"</p>");
        out.println("<p>R: "+r+"</p>");
       if(x==null || y==null || r==null){
            out.println("<p>Зашли в иф</p>");
            request.getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);

        }else{
            out.println("<p>Зашли в else</p>");
            request.getServletContext().getRequestDispatcher("/AreaCheckServlet").forward(request,response);
out.write("sdsd");
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //PrintWriter out=response.getWriter();
      //  out.println("Hello");
        doPost(request,response);
      //  request.getServletContext().getRequestDispatcher("/AreaCheckServlet").forward(request,response);
    }
}
