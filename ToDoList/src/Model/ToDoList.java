package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class ToDoList {
    private final String path = "/Users/gratchuvalsky/Desktop/apache-tomcat-8.5.82/webapps/ToDoList/dataBase/Lists.txt";

    private LinkedHashMap <String, ArrayList<String>> lists;
    private LinkedHashMap <Integer, String> listIds;
    private ArrayList<Integer> id_numbers;



    public ToDoList(){
        lists = new LinkedHashMap<>();
        listIds = new LinkedHashMap<>();
        id_numbers = new ArrayList<>();

    }


    public LinkedHashMap<String, ArrayList<String>> getLists(){
        return lists;
    }

    public LinkedHashMap<Integer, String> getListIds(){
        return listIds;
    }

    public synchronized boolean add(String title, String sublist){
        ArrayList<String> tmp = new ArrayList<>();

        if(lists.size() == 0){
            if (!sublist.equals("0")) {
                tmp.add(sublist);
            }
            lists.put(title, tmp);
            id_numbers.add(0);
            listIds.put(0, title);
        }

        else {
            if(lists.containsKey(title)){
                if(!lists.containsValue(sublist)) {
                    tmp = lists.get(title);
                    if (!sublist.equals("0")) {
                        tmp.add(sublist);
                    }
                    lists.put(title, tmp);
                }
                else
                    return false;
            }
            else{
                if (!sublist.equals("0")) {
                    tmp.add(sublist);
                }
                lists.put(title, tmp);
                listIds.put(id_numbers.get(id_numbers.size() - 1) + 1, title);
                id_numbers.add(id_numbers.get(id_numbers.size() - 1) + 1);
            }

        }
        return true;
    }

    public synchronized void delete(int titleId, int sublistId){
        String key;
        if(sublistId == -1){
            key = listIds.get(titleId);
            lists.remove(key);
            listIds.remove(titleId);
            id_numbers.remove(Integer.valueOf(titleId));
        }

        else{
            key = listIds.get(titleId);
            ArrayList<String> tmp = new ArrayList<>();
            tmp = lists.get(key);
            tmp.remove(sublistId);
            lists.put(key, tmp);
        }

    }



    public synchronized void readFile() throws Exception{
        File list = new File(path);

        if(list.length() != 0) {
            lists  = new LinkedHashMap<>();
            listIds = new LinkedHashMap<>();
            id_numbers = new ArrayList<>();

            Scanner sc = new Scanner(list);

            int id = 0;
            String title;
            ArrayList<String> tmp ;

            while (sc.hasNextLine()) {
                tmp = new ArrayList<>();
                String[] arr = sc.nextLine().split(":");
                id = Integer.parseInt(arr[0]);
                title = arr[1];

                for(int i = 2; i < arr.length; i++ )
                    tmp.add(arr[i]);

                id_numbers.add(id);
                listIds.put(id, title);
                lists.put(title, tmp);
            }
            sc.close();

        }

        else{
            lists  = new LinkedHashMap<>();
            listIds = new LinkedHashMap<>();
            id_numbers = new ArrayList<>();
        }
    }

    public synchronized void writeFile() throws Exception{
        FileWriter fstream = new FileWriter(path);
        PrintWriter out = new PrintWriter(fstream);

        if(lists.size() == 0){
            out.write("");
        }
        else {

            for(int key : listIds.keySet()){
                out.write(key + ":" + listIds.get(key) + ":");
                ArrayList<String> tmp = lists.get(listIds.get(key));
                for(int i = 0; i < tmp.size(); i++){

                    if(i == tmp.size()-1)
                        out.write(tmp.get(i));

                    else
                        out.write(tmp.get(i) + ":");
                }
                out.write("\n");

            }
        }
        fstream.close();
    }



}
