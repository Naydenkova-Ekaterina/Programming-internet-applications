var xValue;
var rValue;
var yValue;

function getXValue(o) {

  //  var but= document.getElementsByName("formId:xValue");
    var but= document.getElementsByClassName("x");
    for (var i=0;i<but.length;i++){
        but[i].style.background="#007cae";
    }

    if(xValue!=o.value){
        xValue=o.value;
        document.getElementById("formId:hiddenX").value=xValue;
        o.style.background="rgb(77,77,77)";}
}

function clickSub() {
   // document.getElementById("formId:rValue").disabled = false;
   // document.getElementById("formId:rValue").setAttribute("disabled",false);
   /* var rect=canvas.getBoundingClientRect();
    var a=(rect.width-canvas.width)/2+1;
    var x=event.clientX-rect.left-a;
    var y=event.clientY-rect.top-a;

        var real_x=(x-300)/valueChange;
        var real_y=-(y-300)/valueChange;
        xValue=real_x;
        yValue=real_y;*/
   yValue=document.getElementById("formId:yValue").value;
   alert(yValue);
   var  xCoord = xValue * valueChange + 300;
    var yCoord = -yValue * valueChange + 300;
    var areareach=false;

    if((xValue>=-rValue/2 && xValue<=0 && yValue>=-rValue && yValue<=0) ||
        (xValue>=0 && xValue<=rValue && yValue<=0 && yValue>=-rValue/2 && yValue>=(xValue-rValue)/2) ||
        (xValue>=-rValue/2 && xValue<=0 && yValue>=0 && yValue<=rValue/2 && xValue*xValue+yValue*yValue<=rValue*rValue)){
        areareach=true;
    }else{
        areareach=false;
    }

alert(areareach);
    drawPoint(context, xCoord, yCoord,areareach);
}


function rChange() {
    rValue=document.getElementById("formId:rValue").value;
    document.getElementById("formId:rValueHid").value=rValue;
    console.log(rValue);
    if(rValue>0) {
        context.clearRect(0, 0, canvas.width, canvas.height);
        drawCoordinateLines(context);
        drawGraph(context);
    }
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

var canvas=document.getElementById("graph");
canvas.width=canvas.width;
var context=canvas.getContext("2d");

function setPoint(event) {
    var rect=canvas.getBoundingClientRect();
    var a=(rect.width-canvas.width)/2+1;
    var x=event.clientX-rect.left-a;
    var y=event.clientY-rect.top-a;
    if(rValue==null){
        alert("Set a radius!!!!!!!")
    }else{
        var real_x=(x-300)/valueChange;
        var real_y=-(y-300)/valueChange;
        xValue=real_x;
        yValue=real_y;
        document.getElementById("hiddenForm:hiddenX2").value=xValue;
        document.getElementById("hiddenForm:hiddenY").value=yValue;
        document.getElementById("hiddenForm:hiddenR").value=rValue;

        document.getElementById("hiddenForm:hiddenSubmit").click();
        //drawPoint()
        // ajaxFunction(xValue,yValue);

        var  xCoord = xValue * valueChange + 300;
        var yCoord = -yValue * valueChange + 300;
        var areareach=false;

        if((xValue>=-rValue/2 && xValue<=0 && yValue>=-rValue && yValue<=0) ||
            (xValue>=0 && xValue<=rValue && yValue<=0 && yValue>=-rValue/2 && yValue>=(xValue-rValue)/2) ||
            (xValue>=-rValue/2 && xValue<=0 && yValue>=0 && yValue<=rValue/2 && xValue*xValue+yValue*yValue<=rValue*rValue)){
            areareach=true;
        }else{
            areareach=false;
        }


        drawPoint(context, xCoord, yCoord,areareach);


    }
}

function drawPoint(context, x, y, inArea){

    context.beginPath();
    if(inArea){
        context.fillStyle = "Green";
    } else {
        alert("Point isn't in area");
        context.fillStyle = "Black";
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

    context.beginPath();
    //2-я четверть
    //круг
    context.arc(299,299,rValue/2*valueChange,Math.PI,1.5*Math.PI);
    context.lineTo(299,299);
    context.moveTo((299 - rValue/2 *valueChange), 301);
    context.lineTo(299,(301 + rValue/2*valueChange));
    //3-я четверть
    context.rect(299-rValue/2*valueChange,301, rValue/2*valueChange, rValue*valueChange );

    //4-я четверть
    //треугольник
    context.moveTo((301 + rValue *valueChange), 301);
    context.lineTo(301, (301 + rValue/2 *valueChange));
    context.lineTo(301, 301);
    context.lineTo((301 +rValue *valueChange), 301);
    context.closePath();
    context.fillStyle="red";
    context.fill();
}
canvasDraw();