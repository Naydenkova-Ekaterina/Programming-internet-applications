function getTable(url){
    $.ajax({
        url: url,
        success: function(data) {
            var div =document.getElementById('divTable');
            if(div){
                var table = document.createElement('table');
                if(data.length>0) {
                    var tr = document.createElement('tr');
                    for (var property in data[0]) {
                        var th = document.createElement('th');
                        th.innerHTML = property;
                        tr.appendChild(th);
                    }
                    table.appendChild(tr);

                    for (var i = 0; i < data.length; i++) {
                        var tr = document.createElement('tr');
                        for (var property in data[i]) {
                            var td = document.createElement('td');
                            td.innerHTML = data[i][property];
                            tr.appendChild(td);
                        }
                        table.appendChild(tr);
                    }
                    div.appendChild(table);
                }
            }
        }
    });
}