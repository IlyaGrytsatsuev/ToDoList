package Controller;

import Model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.Cookie;


import java.util.ArrayList;

public class ToDoListServlet extends HttpServlet{

    private ToDoList list;
    private UsersManager users;

    public void init(ServletConfig config) {
        try {
            list = new ToDoList();
            users = new UsersManager();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

   /* protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            list.readFile();
            users.readIdsFile();
            String state = request.getParameter("state");
            if(state.equals("AddList")){
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(30*60);
                String name = (String)session.getAttribute("name");
                String title = request.getParameter("title");

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try{

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
            String name = (String)session.getAttribute("name");
            if(name == null)
                response.sendRedirect("LogIn");

            else {
                list.readFile();
                users.readIdsFile();

                LinkedHashMap<Integer, ArrayList<String>> l1 = list.getLists();
                LinkedHashMap<Integer, String> l2 = list.getListIds();
                LinkedHashMap<String, ArrayList<Integer>> ids = users.getUsersLists();

                ArrayList<Integer> myIds = ids.get(name);
                LinkedHashMap<String, ArrayList<String>> myLists = new LinkedHashMap<>();


                for(int i = 0; i < myIds.size(); i++){
                    String title = l2.get(myIds.get(i));
                    ArrayList<String> sub = l1.get(myIds.get(i));
                    myLists.put(title, sub);
                }


                request.setAttribute("session", session);
                request.setAttribute("lists", myLists);
                //request.setAttribute("listIds", l2);


                RequestDispatcher rd = request.getRequestDispatcher("/src/View/ToDoList.jsp");
                rd.forward(request, response);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
