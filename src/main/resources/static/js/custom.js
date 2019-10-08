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
    if ($("#datasource-ignored-tables").length != 0) {
        datasourceId = $("#datasource").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/ignored",
            success: function(result) {
                result.forEach(element => {
                    $("#datasource-ignored-tables").append('<tr id="delete_'+ element.id +'"><td>'+ element.name +'</td><td><button type="button" class="btn btn-warning float-right" onclick="deleteIgnoredTable('+ element.id +')"><i class="far fa-trash-alt"></i></button></td></tr>');
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
                    $("#table-columns").append('<tr><td><a href="/datasources/'+ datasourceId +'/tables/'+ tableId +'/columns/'+ element.id +'">'+ element.name +'</a></td><td>'+ element.friendlyName +'</td><td>'+ element.type +' ('+ element.size +')</td><td>'+ element.nullable +'</td></tr>');
                });
            }
        });
    }
});

$(function() {
    if ($("#column-references").length != 0) {
        datasourceId = $("#datasource").val();
        tableId = $("#table").val();
        columnId = $("#column").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/"+ tableId +"/columns/"+ columnId +"/foreignkeys",
            success: function(result) {
                result.forEach(element => {
                    $("#column-references").append('<tr><td><a href="/datasources/'+ datasourceId +'/tables/'+ element.table.id +'">'+ element.table.name +'</a></td><td><a href="/datasources/'+ datasourceId +'/tables/'+ tableId +'/columns/'+ element.id +'">'+ element.name +'</a></td><td>'+ element.friendlyName +'</td></tr>');
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

function loadForeignColumns(foreignTableId) {
    datasourceId = $("#datasource").val();
    $.ajax({
        url: "/api/datasources/"+ datasourceId +"/tables/"+ foreignTableId +"/columns/documented",
        success: function(result) {
            $("#foreignKey").empty();
            $("#foreignKey").append('<option value="">Select...</option>');
            result.forEach(column => {
                $("#foreignKey").append('<option value="'+ column.id +'">'+ column.name +'</option>');
            });
            $("#foreignKey").val($("#foreign-column-aux").val());
        }
    });
}

$(function() {
    if ($("#database-table-name").length != 0) {
        datasourceId = $("#datasource").val();
        tableId = $("#id").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables?except=" + tableId,
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
    if ($("#database-ignored-table-name").length != 0) {
        datasourceId = $("#datasource").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/ignorable",
            success: function(result) {
                result.forEach(table => {
                    $("#database-ignored-table-name").append('<option value="'+ table.name +'">'+ table.name +'</option>');
                });
            }
        });
    }
});

$(function() {
    if ($("#table-column-name").length != 0) {
        datasourceId = $("#datasource").val();
        tableId = $("#table").val();
        columnId = $("#id").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/"+ tableId + "/columns?except=" + columnId,
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

$(function() {
    if ($("#foreign-table").length != 0) {
        datasourceId = $("#datasource").val();
        foreignTableId = $("#foreign-table-aux").val();
        $.ajax({
            url: "/api/datasources/"+ datasourceId +"/tables/documented",
            success: function(result) {
                result.forEach(table => {
                    $("#foreign-table").append('<option value="'+ table.id +'">'+ table.name +'</option>');
                });
                $("#foreign-table").val(foreignTableId);
                loadForeignColumns(foreignTableId);
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

$("#foreign-table").change(function() {
    var elem = $(this);
    tableId = elem.val();
    loadForeignColumns(tableId);
});

$("#cancel").click(function() {
    window.history.back();
});

function deleteIgnoredTable(id) {
    if (confirm("Are you sure you want to delete it?")) {
        datasourceId = $("#datasource").val();
        $.ajax({
            url: '/api/datasources/'+ datasourceId +'/tables/ignored/'+ id,
            type: 'DELETE',
            success: function(result) {
                $("#delete_"+ id).remove();
            }
        });
    }
}