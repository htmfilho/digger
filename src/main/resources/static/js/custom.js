$(function() {
    if ($("#list-datasources").length != 0) {
        $.ajax({
            url: "/api/datasources",
            success: function(result) {
                result.forEach(element => {
                    $("#list-datasources").append('<a href="/datasources/'+ element.id +'" class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">'+ element.name +'</h5><small>'+ element.driver +'</small></div><p class="mb-1">'+ element.description +'</p></a>');
                });
            }
        });
    }
});

$(function() {
    if ($("#datasource-tables").length != 0) {
        datasourceId = $("#datasource").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/documented",
            success: function(result) {
                result.forEach(element => {
                    $("#datasource-tables").append('<tr><td><a href="/datasources/'+ datasourceId +'/tables/'+ element.id +'">'+ element.name +'</a></td><td>'+ element.friendlyName +'</td><td>'+ element.type +'</td></tr>');
                });
            }
        });
    }
});

$(function() {
    if ($("#table-columns").length != 0) {
        datasourceId = $("#datasource").val();
        tableId = $("#table").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/"+ tableId +"/columns/documented",
            success: function(result) {
                result.forEach(element => {
                    $("#table-columns").append('<tr><td><a href="/datasources/'+ datasourceId +'/tables/'+ tableId +'/columns/'+ element.id +'">'+ element.name +'</a></td><td>'+ element.friendlyName +'</td><td>'+ element.type +' ('+ element.size +')</td><td>'+ element.nullable +'</td><td>'+ element.defaultValue +'</td></tr>');
                });
            }
        });
    }
});

var databaseTables = null;
var tableColumns = null;

function loadTableAttributes(tableName) {
    if(tableName === "") {
        $("#type-label").html("-");
        $("#type").val("");
    } else {
        databaseTables.forEach(table => {
            if(table.name === tableName) {
                $("#type-label").html(table.type);
                $("#type").val(table.type);
            }
        });
    }
}

function loadColumnAttributes(columnName) {
    if(columnName === "") {
        $("#type-label").html("-");
        $("#type").val("");
        $("#size").val("");
        $("#nullable-label").html("-");
        $("#nullable").val("");
        $("#default-label").html("-");
        $("#default").val("");
    } else {
        tableColumns.forEach(column => {
            if(column.name === columnName) {
                $("#type-label").html(column.type + " ("+ column.size +")");
                $("#type").val(column.type);
                $("#size").val(column.size);
                $("#nullable-label").text(column.nullable);
                $("#nullable").val(column.nullable);
                $("#default-label").html(column.defaultValue);
                $("#default").val(column.defaultValue);
            }
        });
    }
}

$(function() {
    if ($("#database-table-name").length != 0) {
        datasourceId = $("#datasource").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables",
            success: function(result) {
                databaseTables = result;
                databaseTables.forEach(table => {
                    $("#database-table-name").append('<option value="'+ table.name +'">'+ table.name +'</option>');
                });
                nameAux = $("#name-aux").val();
                $("#database-table-name").val(nameAux);
                loadTableAttributes(nameAux);
            }
        });
    }
});

$(function() {
    if ($("#table-column-name").length != 0) {
        datasourceId = $("#datasource").val();
        tableId = $("#table").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/"+ tableId + "/columns",
            success: function(result) {
                tableColumns = result;
                tableColumns.forEach(column => {
                    $("#table-column-name").append('<option value="'+ column.name +'">'+ column.name +'</option>');
                });
                nameAux = $("#name-aux").val();
                $("#table-column-name").val(nameAux);
                loadColumnAttributes(nameAux);
            }
        });
    }
});

$("#database-table-name").change(function() {
    var elem = $(this);
    loadTableAttributes(elem.val());
});

$("#table-column-name").change(function() {
    var elem = $(this);
    loadColumnAttributes(elem.val());
});

$("#cancel").click(function() {
    window.history.back();
});