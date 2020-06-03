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
    if ($("#list-tables").length != 0) {
        let pathname = window.location.pathname;
        $.ajax({
            url: "/api".concat(pathname, "/tables/documented"),
            success: function(result) {
                result.forEach(element => {
                    $("#list-tables").append('<div class="card"><div class="card-body"><h5 class="card-title"><a href="'+ pathname +'/tables/'+ element.id +'">'+ element.friendlyName +'</a></h5><h6 class="card-subtitle mb-2 text-muted">'+ element.name +' ('+ element.type +')</h6><p class="card-text">'+ renderAsciidoctor(element.documentation) +'</p></div></div><p></p>');
                });
            }
        });
    }
});

$(function() {
    if ($("#list-columns").length != 0) {
        let pathname = window.location.pathname;
        $.ajax({
            url: "/api".concat(pathname, "/columns/documented"),
            success: function(result) {
                result.forEach(element => {
                    $("#list-columns").append('<div class="card"><div class="card-body"><h5 class="card-title"><a href="'+ pathname +'/columns/'+ element.id +'">'+ element.friendlyName +'</a></h5><h6 class="card-subtitle mb-2 text-muted">'+ element.name +' ('+ element.type +') '+ (element.nullable ? 'NULL': 'NOT NULL') +'</h6><p class="card-text">'+ renderAsciidoctor(element.documentation) +'</p>'+ (element.foreignKey ? '<a href="'+ pathname.substring(0, pathname.lastIndexOf("/") + 1) + element.foreignKey.table.id +'/columns/'+ element.foreignKey.id +'"><span class="badge badge-info"><img src="/images/link.svg" alt="Foreign Key" data-toggle="tooltip" data-placement="right" title="Foreign Key"></span></a>' : '') +'</div></div><p></p>');
                });
            }
        });
    }
});

$(function() {
    if ($("#datasource-ignored-tables").length != 0) {
        let pathname = window.location.pathname;
        $.ajax({
            url: "/api".concat(pathname, "/tables/ignored"),
            success: function(result) {
                result.forEach(element => {
                    $("#datasource-ignored-tables").append('<tr id="delete_'+ element.id +'"><td>'+ element.name +'</td><td><button type="button" class="btn btn-warning float-right" onclick="deleteIgnoredTable('+ element.id +')"><i class="far fa-trash-alt"></i></button></td></tr>');
                });
            }
        });
    }
});

$(function() {
    if ($("#column-references").length != 0) {
        let pathname = window.location.pathname;
        $.ajax({
            url: "/api".concat(pathname, "/foreignkeys"),
            success: function(result) {
                result.forEach(element => {
                    $("#column-references").append('<tr><td><a href="'+ pathname.match(/\Wdatasources\W[0-9]+/) +'/tables/'+ element.table.id +'">'+ element.table.name +'</a></td><td><a href="'+ pathname.match(/\Wdatasources\W[0-9]+/) +'/tables/'+ element.table.id +'/columns/'+ element.id +'">'+ element.name +'</a></td><td>'+ element.friendlyName +'</td></tr>');
                });
            }
        });
    }
});

$(function() {
    if ($("#database_ignorable_tables").length != 0) {
        let pathname = window.location.pathname;
        $.ajax({
            url: "/api".concat(pathname.match(/\Wdatasources\W[0-9]+/), "/tables/ignorable"),
            success: function(result) {
                result.forEach(table => {
                    $("#database_ignorable_tables").append('<tr><td><input type="checkbox" id="ignored-'+ table.name +'" name="ignored" value="'+ table.name +'">&nbsp;<label for="ignored-'+ table.name +'">'+ table.name +'</label>');
                });
            }
        });
    }
});

$(function() {
    if ($("#users").length != 0) {
        $.ajax({
            url: "/api/admin/users",
            success: function(result) {
                result.forEach(element => {
                    $("#users").append('<tr><td><a href="/admin/users/'+ element.id +'">'+ element.username +'</a></td><td>'+ element.mainRole +'</td><td><input type="checkbox" id="enabled-'+ element.id +'" name="enabled" value="'+ element.username +'" '+ (element.enabled ? 'checked': '') +' onclick="enableUser(\''+ element.username +'\', this);"></td></tr>');
                });
            }
        });
    }
});

let databaseTables = null;
let tableColumns = null;

function loadTableAttributes(table) {
    let tableName = table.val();
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

function loadColumnAttributes(column) {
    let columnName = column.val();
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
        let datasourceId = $("#datasource").val();
        let tableId = $("#id").val();
        $.ajax({
            url: "/api/datasources/".concat(datasourceId, "/tables?except=", tableId),
            success: function(result) {
                databaseTables = result;
                databaseTables.forEach(table => {
                    addOption("#database-table-name", table.name, table.name);
                });
                let nameAux = $("#name-aux").val();
                $("#database-table-name").val(nameAux);
                loadTableAttributes($("#name-aux"));
            }
        });
    }
});

$(function() {
    if ($("#table-column-name").length != 0) {
        let datasourceId = $("#datasource").val();
        let tableId = $("#table").val();
        let columnId = $("#id").val();
        $.ajax({
            url: "/api/datasources/".concat(datasourceId, "/tables/", tableId, "/columns?except=", columnId),
            success: function(result) {
                tableColumns = result;
                tableColumns.forEach(column => {
                    addOption("#table-column-name", column.name, column.name);
                });
                let nameAux = $("#name-aux").val();
                $("#table-column-name").val(nameAux);
                loadColumnAttributes($("#name-aux"));
            }
        });
    }
});

$(function() {
    if ($("#foreign-table").length != 0) {
        let datasourceId = $("#datasource").val();
        let foreignTableId = $("#foreign-table-aux").val();
        $.ajax({
            url: "/api/datasources/".concat(datasourceId, "/tables/documented"),
            success: function(result) {
                result.forEach(table => {
                    addOption("#foreign-table", table.id, table.name);
                });
                $("#foreign-table").val(foreignTableId);
                loadForeignColumns(foreignTableId);
            }
        });
    }
});

/* Select a tab based on the query string parameter 'tab'. */
$(function() {
    let urlParams = new URLSearchParams(window.location.search);
    let tab = urlParams.get('tab');
    if(tab) {
        $('.nav-tabs a[href="#'+ tab +'"]').tab('show');
    }
});

$(function() {
    $("table").addClass("table");
});

$(function() {
    const now = new Date();
    const year = now.getFullYear();
    $("#current-year").html(year);
});

$(function() {
    if ($("#documentation-preview").length != 0) {
        $("#documentation-preview").html(renderAsciidoctor($("#documentation").val()));
    }
});

$("#cancel").click(function() {
    window.history.back();
});

// When there is a checkbox that when checked also checks all checkboxes in the page. An example can be found in the
// Ignored Table form.
$("#check-all").click(function(event) {
    if(this.checked) {
        // Iterate each checkbox
        $(':checkbox').each(function() {
            this.checked = true;
        });
    } else {
        $(':checkbox').each(function() {
            this.checked = false;
        });
    }
});

$("#database-table-name").change(function() {
    loadTableAttributes($(this));
});

$("#documentation").change(function() {
    $("#documentation-preview").html(renderAsciidoctor($(this).val()));
});

$("#driver").change(function() {
    const driver = $(this).val();

    switch(driver) {
        case "org.h2.Driver":
            $("#urlTemplate").text("Template: jdbc:h2:file:[path-to-file]");
            break;
        case "org.postgresql.Driver":
            $("#urlTemplate").text("Template: jdbc:postgresql://[server]:[port]/[database-name]");
            break;
        case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
            $("#urlTemplate").text("Template: jdbc:sqlserver://[server]:[port];databaseName=[database-name]");
            break;
        default:
            $("#urlTemplate").text("");
    }
});

$("#foreign-table").change(function() {
    let elem = $(this);
    let tableId = elem.val();
    loadForeignColumns(tableId);
});

$("#table-column-name").change(function() {
    loadColumnAttributes($(this));
});

function renderAsciidoctor(content) {
    let asciidoctor = Asciidoctor();
    return asciidoctor.convert(content);
}

function addOption(id, value, label) {
    $(id).append('<option value="'+ value +'">'+ label +'</option>');
}

function loadForeignColumns(foreignTableId) {
    let datasourceId = $("#datasource").val();

    if(isNaN(foreignTableId)) {
        return
    }

    $.ajax({
        url: "/api/datasources/".concat(datasourceId, "/tables/", foreignTableId, "/columns/documented"),
        success: function(result) {
            $("#foreignKey").empty();
            $("#foreignKey").append('<option value="">Select...</option>');
            result.forEach(column => {
                addOption("#foreignKey", column.id, column.name);
            });
            $("#foreignKey").val($("#foreign-column-aux").val());
        }
    });
}

function deleteIgnoredTable(id) {
    if (confirm("Are you sure you want to delete it?")) {
        let pathname = window.location.pathname;
        $.ajax({
            url: "/api".concat(pathname, "/tables/ignored/", id),
            type: 'DELETE',
            success: function() {
                $("#delete_"+ id).remove();
            }
        });
    }
}

function enableUser(username, field) {
    $.ajax({
        url: "/api/admin/users",
        type: "POST",
        data: {username: username}
    });
}

function buildSuccessRedirectUrl(pathname) {
    let redirectUrl = pathname;
    redirectUrl = redirectUrl.substring(0, redirectUrl.lastIndexOf("/"));
    redirectUrl = redirectUrl.substring(0, redirectUrl.lastIndexOf("/"));

    if(!redirectUrl) {
        redirectUrl = "/"
    }

    return redirectUrl;
}

function deleteElement(prefix = "/") {
    if (confirm("Are you sure you want to delete it?")) {
        let pathname = window.location.pathname;
        let apiUrl = prefix.concat("api", pathname);
        let redirectUrl = buildSuccessRedirectUrl(pathname);
        console.log(apiUrl);
        console.log(redirectUrl);
        $.ajax({
            url: apiUrl,
            type: 'DELETE',
            success: function() {
                window.location = redirectUrl;
            }
        });
    }
}