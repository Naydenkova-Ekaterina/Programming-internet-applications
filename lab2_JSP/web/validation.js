//canvasDraw();

var xValue;
function check() {

}
function validate() {
    var firstCheck;
    var secondCheck;
    var thirdCheck;
    //Проверка х

     xValue=document.getElementById("xValue").value;
    document.getElementById("x_col").removeAttribute("class");
    document.getElementById("x_error").style.display="none";
    if((isNaN(xValue) || xValue<-5 || xValue>5 || xValue=="" ) || isNaN(parseFloat(xValue)) && isFinite(xValue) ){

        document.getElementById("x_col").setAttribute("class","error");
        document.getElementById("x_error").style.display="block";
        firstCheck= false;
    }

    //Проверка y
    var y=yValue;
    document.getElementById("y_col").removeAttribute("class");
    document.getElementById("y_error").style.display="none";
    if(y==null){
        document.getElementById("y_col").setAttribute("class","error");
        document.getElementById("y_error").style.display="block";
        secondCheck= false;
    }

    //Проверка r
    var r=rValue;
    document.getElementById("r_col").removeAttribute("class");
    document.getElementById("r_error").style.display="none";
    if(r==null){
        document.getElementById("r_col").setAttribute("class","error");
        document.getElementById("r_error").style.display="block";
        thirdCheck= false;
    }

    if(firstCheck==false || secondCheck==false || thirdCheck==false) return false;

}

function showError(container,errorMessage) {
    container.className="error";
    var message=document.createElement("span");
    message.className="error-message";
    message.innerHTML=errorMessage;
    container.appendChild(message)
}
var yValue;
function getYValue(o) {

    var but= document.getElementsByName("yValue");
    for (var i=0;i<but.length;i++){
        but[i].style.backgroundColor="cadetblue";
    }

    if(yValue!=o.value){
        yValue=o.value;
        document.getElementById("hiddenY").value=yValue;
        o.style.background="rgb(77,77,77)";}
}
var rValue;
function getRValue(o) {
    xValue=document.getElementById("xValue").value;
    var but= document.getElementsByName("rValue");
    for (var i=0;i<but.length;i++){
        but[i].style.backgroundColor="cadetblue";
    }

    if(rValue!=o.value){
        rValue=o.value;
        document.getElementById("hiddenR").value=rValue;
        o.style.background="rgb(77,77,77)";}
    context.clearRect(0,0, canvas.width,canvas.height);
    drawCoordinateLines(context);
    drawGraph(context);
}

var valueChange=50;

function canvasDraw() {
var canvas=document.getElementById("graph");
    canvas.width=canvas.width;
    var context=canvas.getContext("2d");
    context.clearRect(0,0,canvas.width,canvas.height);
    if(rValue>0){
        drawGraph(context);
    }
    drawCoordinateLines(context);

}


function ajaxFunction(xValue,yValue) {
    alert(xValue);
 $.ajax({
     url:"/Lab2_war_exploded/ControllerServlet",
    type:"GET",
    data: {
        xValue:xValue,
        yValue:yValue,
        rValue:rValue
    },
    success:function answerFunc(data){

        alert("data"+data);
        var new_data= JSON.parse(data);
        alert(new_data);
        var  xCoord = xValue * valueChange + 300;
        var yCoord = -yValue * valueChange + 300;
        drawPoint(context, xCoord, yCoord,data);
    }
    }
);

}

var canvas=document.getElementById("graph");
canvas.width=canvas.width;
var context=canvas.getContext("2d");

function setPoint(event) {
    var rect=canvas.getBoundingClientRect();
    var a=(rect.width-canvas.width)/2+1;
    var x=event.clientX-rect.left-a;
    var y=event.clientY-rect.top-a;
    if(rValue==null){
        alert("Установите радиус!!!!!!!")
    }else{
       var real_x=(x-300)/valueChange;
       alert(real_x);
        var real_y=-(y-300)/valueChange;
        xValue=real_x;
        yValue=real_y;
        ajaxFunction(xValue,yValue);
    }
}

function drawPoint(context, x, y, inArea){

    context.beginPath();
    if(inArea){
        context.fillStyle = "Green";
    } else {
        alert("Точка не попала");
        context.fillStyle = "transparent";
    }
    context.arc(x, y, 3, 0*Math.PI, 2*Math.PI);
    context.fill();
}

function drawCoordinateLines(context) {
    context.beginPath();
    context.moveTo(300,600);
    context.lineTo(300,0);
    context.lineTo(305,5);
    context.moveTo(300,0);
    context.lineTo(295,5);
    context.moveTo(0,300);
    context.lineTo(600,300);
    context.lineTo(595,305);
    context.moveTo(600,300);
    context.lineTo(595,295);

    context.strokeStyle="black";
    context.stroke();
    context.fillStyle="black";
    context.closePath();
}

function drawGraph(context){

    //треугольник
    context.beginPath();
    context.moveTo((299 + rValue/2 *valueChange), 299);
    context.lineTo(301, (301 - rValue/2 *valueChange));
    context.lineTo(301, 301);
    context.lineTo((301 +rValue/2 *valueChange), 301);

    //круг
    context.arc(299,299,rValue*valueChange,Math.PI,1.5*Math.PI);
    context.lineTo(299,299);
    context.moveTo((299 - rValue *valueChange), 301);
    context.lineTo(299,(301 + rValue*valueChange));

    //квадрат
    context.rect(301,301, rValue*valueChange, rValue*valueChange );
    context.closePath();
    context.fillStyle="red";
    context.fill();
}
canvasDraw();