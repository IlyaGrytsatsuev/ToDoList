
var cur_page ;
var pages_num ;


$("#NewButton").click(function() {
    $("#ListTitle").show();
  });
  
  $("#ListAddButton").click(function() {
    AddNewList();
  });


function AddNewList(){
    var xhr = new XMLHttpRequest();
    var Title = document.getElementById("TitleInput").value;
    if(Title != null && Title != ""){
        var data =  "state=AddList" + "&" + "title=" + Title;

        xhr.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                location.reload();
            }

        }

        xhr.open("POST", "/ToDoList/ToDoList", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);

    }

}

function DeleteList(id){

    var xhr = new XMLHttpRequest();
     
    var data =  "state=DeleteList" + "&" + "id=" + id;

        xhr.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                location.reload();
            }

        }

        xhr.open("POST", "/ToDoList/ToDoList", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);

}

function showList(id){
    var sub = document.getElementById(id + "SubLists");
    var button = document.getElementById(id + "OpenButton");
    if($(sub).is(":hidden")){
        $(sub).show();
        button.value = "Close";
   }
    else{
        $(sub).hide();
        button.value = "Open";
    }
}

function showAddSubList(id){
    $("#" + id +"SubText").show();
}

function addSubList(id){

    var xhr = new XMLHttpRequest();
    var title = document.getElementById(id + "SubTextInput").value;

    if(title != "" && title != null){

         var data =  "state=addSubList" + "&" + "id=" + id + "&" + "title=" + title;

        xhr.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                location.reload();
            }

        }

        xhr.open("POST", "/ToDoList/ToDoList", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
    }

}

function DeleteSubList(i, j){
    var xhr = new XMLHttpRequest();
    var data =  "state=deleteSubList" + "&" + "titleId=" + i + "&" + "subId=" + j;

    xhr.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            location.reload();
        }
    }

    xhr.open("POST", "/ToDoList/ToDoList", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);

}


function ShowFirst(num){
    
    pages_num = num;
    cur_page = 1;

    for( var i = 2; i < num+1; i++ ){
        var p = document.getElementById(i);
        p.style.display = 'none';
    }

}

function Next(){
    var page = document.getElementById(cur_page);
    page.style.display = 'none';
    cur_page += 1;

    page = document.getElementById(cur_page);
    page.style.display = 'block';

    ShowNavButtons();
}

function Prev(){
    var page = document.getElementById(cur_page);
    page.style.display = 'none';
    cur_page -= 1;

    page = document.getElementById(cur_page);
    page.style.display = 'block';

    ShowNavButtons();
    
}

function ShowNavButtons(){
    var Pbutton = document.getElementById("PButton");
    var Nbutton = document.getElementById("NButton");


        if(cur_page == 1 ){
            Pbutton.style.display = 'none';
            Nbutton.style.display = 'block';
        }
    
        if(cur_page > 1 && cur_page < pages_num){
            Pbutton.style.display = 'block';
            Nbutton.style.display = 'block';
        }

        if(pages_num == cur_page){
            Nbutton.style.display = 'none';
            Pbutton.style.display = 'block';
        }
    
    
}