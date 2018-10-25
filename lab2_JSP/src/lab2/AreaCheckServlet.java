package lab2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class AreaCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        double x=Double.parseDouble(request.getParameter("xValue"));
        int y=Integer.parseInt(request.getParameter("yValue"));
        int r=Integer.parseInt(request.getParameter("rValue"));
        String areaReach;
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();


if(check(x,y,r)){

         areaReach="Point is in area";
        }
        else {
            areaReach="Point isn't in area";
        }
     ResultBean resultBean=(ResultBean) request.getSession().getAttribute("resultBean");
    resultBean.addResult(x,y,r,areaReach);
    request.getRequestDispatcher("answer.jsp").include(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-Type","application/json; charset=UTF-8");
        PrintWriter pw=response.getWriter();
        double x=Double.parseDouble(request.getParameter("xValue"));
        double y=Double.parseDouble(request.getParameter("yValue"));
        int r=Integer.parseInt(request.getParameter("rValue"));
        pw.write(String.valueOf(check(x,y,r)));

    }

    public boolean check(double x,double y, int r){

        if((0<=x && x<=r && -r<=y && y<=0) || (0<=x && x<=r/2 && 0<=y && y<=r/2 && y<=-x+r/2)
                || (-r<=x && x<=0 && 0<=y && y<=r && x*x+y*y<=r*r) ){
            return true;
        }else {
            return false;
        }
    }
}
