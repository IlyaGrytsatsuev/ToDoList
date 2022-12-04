package Controller;

import Model.ToDoList;
import Model.UsersManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.Cookie;

public class SharedListsServlet extends HttpServlet{

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            list.readAllFiles();
            String state = request.getParameter("state");


            if(state.equals("addSubList")){
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(30*60);
                String name = (String)session.getAttribute("name");
                int id = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                list.addSub(name, id, title);
                list.writeFile();
            }

            if(state.equals("deleteSubList")){
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(30*60);
                String name = (String)session.getAttribute("name");
                int titleId = Integer.parseInt(request.getParameter("titleId"));
                int subId = Integer.parseInt(request.getParameter("subId"));
                list.delete(name, titleId, subId);
                list.writeFile();
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try{

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
            String name = (String)session.getAttribute("name");
            if(name == null)
                response.sendRedirect("LogIn");

            else {
                list.readAllFiles();
                users.readAll();

                LinkedHashMap<String, ArrayList<Integer>> usersLists = users.getUsersLists();
                Set<String> keySet = usersLists.keySet();
                List<String> ListUsers = new ArrayList<String>(keySet);

                LinkedHashMap<String, ArrayList<Integer>> sharedLists = users.getSharedLists();
                LinkedHashMap<Integer, ArrayList<String>> allSubLists = list.getLists();
                LinkedHashMap<Integer, String> allTitles = list.getListIds();
                ArrayList<Integer> mySharedIds = sharedLists.get(name);
                LinkedHashMap<String, ArrayList<String>> myLists = new LinkedHashMap<>();
                ArrayList<String> mySharedListsOwners = new ArrayList<>();

                for(int i = 0; i < mySharedIds.size(); i++){
                    String title = allTitles.get(mySharedIds.get(i));
                    ArrayList<String> sub = allSubLists.get(mySharedIds.get(i));
                    myLists.put(title, sub);
                }

                for(int i = 0; i < mySharedIds.size(); i++){
                    for(int j = 0; j < ListUsers.size(); j++) {
                        ArrayList<Integer> tmp = usersLists.get(ListUsers.get(j));
                        if(tmp.contains(mySharedIds.get(i))){
                            mySharedListsOwners.add(ListUsers.get(j));
                            break;
                        }
                    }
                }


                request.setAttribute("session", session);
                request.setAttribute("lists", myLists);
                request.setAttribute("listsOwners", mySharedListsOwners);


                RequestDispatcher rd = request.getRequestDispatcher("/src/View/Shared.jsp");
                rd.forward(request, response);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
