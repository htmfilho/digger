$(document).ready(
    function() {
        if ($("#list-datasources")) {
            $.ajax({
                url: "/api/datasources",
                success: function(result) {
                    console.log(result);
                    result.forEach(element => {
                        $("#list-datasources").append('<a href="/datasources/'+ element.id +'" class="list-group-item list-group-item-action"><div class="d-flex w-100 justify-content-between"><h5 class="mb-1">'+ element.name +'</h5><small>'+ element.driver +'</small></div><p class="mb-1">'+ element.description +'</p></a>');
                    });
                }
            });
        }
    }
);