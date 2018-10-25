
var responseForTable;

var areareach;
var canvasVue=new Vue({
    el:"#graph",

    methods:{
        setPoint:function (event) {

            var rect=canvas.getBoundingClientRect();
            var a=(rect.width-canvas.width)/2+1;
            var x=event.clientX-rect.left-a;
            var y=event.clientY-rect.top-a;
            if(rValueVue.$el.selectedOptions[0].value<=0){
                alert("Set a normal radius!!!!!!!")
            }else{
                var real_x=(x-300)/valueChange;
                var real_y=-(y-300)/valueChange;
                var  xValue=real_x;
                var yValue=real_y;
               alert(xValue);
               alert(yValue);


                var  xCoord = xValue * valueChange + 300;
                var yCoord = -yValue * valueChange + 300;

              //  axios.post('/springJPA-postgreSQL-0.0.1-SNAPSHOT/points', {x:xValue,y:yValue,r:rValueVue.$el.selectedOptions[0].value} )
                axios.post('/points', {x:xValue,y:yValue,r:rValueVue.$el.selectedOptions[0].value} )

                    .then(function (response) {
                        console.log(response);

                    console.log(response.data.areareach);
                    areareach=response.data.areareach;
                    console.log("areareach "+areareach);
                        drawPoint(context, xCoord, yCoord,areareach);
                    })
                    .catch(function (error) {

                        console.log(error.message);
                    });



            }

        }
    }
})

var canvas=canvasVue.$el;
canvas.width=canvas.width;
var context=canvas.getContext("2d");


//axios.get('/springJPA-postgreSQL-0.0.1-SNAPSHOT/points')
axios.get('/points')

    .then(function (response) {
        console.log(response);
        responseForTable=response;
        fillTable();
    })
    .catch(function (error) {
        console.log(error.message);
    });

new Vue({
    el:'#xLabel'  ,
    data: {
        message: 'Выберите значение Х:'
    }
})

new Vue({
    el:'#yLabel'  ,
    data: {
        message: 'Выберите значение Y:'
    }
})
/*
new Vue({
    el:'#rLabel'  ,
    data: {
        message: 'Выберите значение R:'
    }
})*/

new Vue({
    el:'#submitButton',
    methods: {
        send: function() {
            var x =xValueVue.$el.selectedOptions[0].value;
            var y=yValueVue.$el.value;
            var r=rValueVue.$el.selectedOptions[0].value;

           // axios.post('/springJPA-postgreSQL-0.0.1-SNAPSHOT/points', {x:x,y:y,r:r} )
            axios.post('/points', {x:x,y:y,r:r} )

                .then(function (response) {
                    console.log(response);



                    var  xCoord = x * valueChange + 300;
                    var yCoord = -y * valueChange + 300;
                    var areareach=false;

                    /*   if((x>=-rValue/2 && x<=0 && yValue>=-rValue && yValue<=0) ||
                           (xValue>=0 && xValue<=rValue && yValue<=0 && yValue>=-rValue/2 && yValue>=(xValue-rValue)/2) ||
                           (xValue>=-rValue/2 && xValue<=0 && yValue>=0 && yValue<=rValue/2 && xValue*xValue+yValue*yValue<=rValue*rValue)){
                           areareach=true;
                       }else{
                           areareach=false;
                       }*/
                    areareach=response.data.areareach;

                    alert(areareach);
                    drawPoint(context, xCoord, yCoord,areareach);

                })
                .catch(function (error) {

                    console.log(error.message);
                });
        },
    }

})


// register the grid component
Vue.component('demo-grid', {
    template: '#grid-template',
    props: {
        data: Array,
        columns: Array,
        filterKey: String
    },
    data: function () {
        var sortOrders = {}
        this.columns.forEach(function (key) {
            sortOrders[key] = 1
        })
        return {
            sortKey: '',
            sortOrders: sortOrders
        }
    },
    computed: {
        filteredData: function () {
            var sortKey = this.sortKey
            var filterKey = this.filterKey && this.filterKey.toLowerCase()
            var order = this.sortOrders[sortKey] || 1
            var data = this.data
            if (filterKey) {
                data = data.filter(function (row) {
                    return Object.keys(row).some(function (key) {
                        return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                    })
                })
            }
            if (sortKey) {
                data = data.slice().sort(function (a, b,c,d) {
                    a = a[sortKey]
                    b = b[sortKey]
                    c = b[sortKey]
                    d = b[sortKey]
                    return (a === b ===c ===d? 0 : a > b ? 1 : -1) * order
                })
            }
            return data
        }
    },
    filters: {
        capitalize: function (str) {
            return str.charAt(0).toUpperCase() + str.slice(1)
        }
    },
    methods: {
        sortBy: function (key) {
            this.sortKey = key
            this.sortOrders[key] = this.sortOrders[key] * -1
        }
    }
})


var demo = new Vue({
    el: '#demo',
    data: {
        searchQuery: '',
        gridColumns: ['x', 'y','r','areareach'],
        gridData: []
    }
})


function  fillTable() {

    for (var i=document.getElementsByTagName("table")[0].getElementsByTagName('tr').length-1; i>0; i--) {
        document.getElementsByTagName("table")[0].deleteRow(i);
    }

    for(var i=0;i<responseForTable.data.length;i++){
        var tr=document.createElement("tr");
        var tdX=document.createElement("td");
        var tdY=document.createElement("td");
        var tdR=document.createElement("td");
        var tdArea=document.createElement("td");
        var tr=document.createElement("tr");
        tdX.innerHTML=responseForTable.data[i].x;
        tdY.innerHTML=responseForTable.data[i].y;
        tdR.innerHTML=responseForTable.data[i].r;
        tdArea.innerHTML=responseForTable.data[i].areareach;
        tr.appendChild(tdX);
        tr.appendChild(tdY);
        tr.appendChild(tdR);
        tr.appendChild(tdArea);
        document.getElementsByTagName("table")[0].appendChild(tr);
    }
}



new Vue({
    el:'#refreshButton',
    methods: {
        refresh: function() {

          //  axios.get('/springJPA-postgreSQL-0.0.1-SNAPSHOT/points')
            axios.get('/points')

                .then(function (response) {
                    console.log(response);
                    responseForTable=response;
                    fillTable();
                })
                .catch(function (error) {
                    console.log(error.message);
                });
        },
    }

})

var yValueVue=new Vue({
    el:'#yValue',
    data:{
        message:''
    }
})

var xValueVue=new Vue({
    el:'#xValue',
    data:{
        message:''
    }
})

var rValueVue=new Vue({
    el:'#rValue',

    methods:{
        draw: function () {

            console.log(rValueVue.$el.selectedOptions[0].value);
            if(rValueVue.$el.selectedOptions[0].value>0) {
                context.clearRect(0, 0, canvas.width, canvas.height);
                drawCoordinateLines(context);
                drawGraph(context);
            }
        },
    }
})





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

var valueChange=50;

function canvasDraw() {
    var canvas=document.getElementById("graph");
    canvas.width=canvas.width;
    var context=canvas.getContext("2d");
    context.clearRect(0,0,canvas.width,canvas.height);
    if(rValueVue.$el.selectedOptions[0].value>0){
        drawGraph(context);
    }
    drawCoordinateLines(context);

}

function drawGraph(context){
    var rValue=rValueVue.$el.selectedOptions[0].value;
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

function rChange() {
   var rValue=rValueVue.$el.selectedOptions[0].value;
  //  document.getElementById("formId:rValueHid").value=rValue;
    console.log(rValue);
    if(rValue>0) {
        context.clearRect(0, 0, canvas.width, canvas.height);
        drawCoordinateLines(context);
        drawGraph(context);
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

canvasDraw();

