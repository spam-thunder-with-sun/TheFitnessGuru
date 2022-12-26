function getRecipes(){
    console.log("get recipes");

    let base_url = "https://api.api-ninjas.com/v1/recipe?query=";

    if ($('#name').val() === null || $('#name').val() === undefined || $('#name').val() === "")
    {
        alert("Insert a recipe name");
    }
    else
    {
        let ex_name = $('#name').val();
        base_url += ex_name;
        $.ajax({
            url: base_url,
            type: 'GET',
            dataType: 'json', // added data type
            headers: {"x-api-key": "zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd"},
            success: function(res) {
                console.log(res);
                chargeRecipes(res);
            },
            error: function(res) {
                console.log("ERROR", res);
                alert(res);
            },
        });
    }
}

function chargeRecipes(res){
    console.log("charge recipes");

    var div_list = $('#recipe_list');

    div_list.empty();

    for(var k in res) {
        var li = $('<li/>')
            .addClass('ui-menu-item')
            .attr('role', 'menuitem')
            .appendTo(div_list);

        var input = $('<input/>')
            .addClass('ui-all')
            .attr('onclick','selectRecipe(this)')
            .attr('type', 'checkbox')
            .attr('value', res[k].title)
            .appendTo(li);

        var a_title = $('<a/>')
            .addClass('ui-all')
            .attr("name", "title")
            .text(res[k].title)
            .appendTo(li);

        var a_ingredients = $('<a/>')
            .addClass('ui-all')
            .attr("hidden", "true")
            .attr("name", "ingredients")
            .text(res[k].ingredients)
            .appendTo(li);

        var a_servings = $('<a/>')
            .addClass('ui-all')
            .attr("hidden", "true")
            .attr("name", "servings")
            .text(res[k].servings)
            .appendTo(li);

        var a_instructions = $('<a/>')
            .addClass('ui-all')
            .attr("hidden", "true")
            .attr("name", "instructions")
            .text(res[k].instructions)
            .appendTo(li);
    }
}

function selectRecipe(element){
    console.log("select recipe");

    console.log(element);
    var day_selected = $('#day_input').val();
    var destination_div_id = "day_" + day_selected;
    var destination_div = $('#' + destination_div_id);

    var li = $('<li/>')
        .addClass('ui-menu-item')
        .attr('role', 'menuitem')
        .appendTo(destination_div);

    var ckbox = $('<input/>')
        .addClass('ui-all')
        .attr('onclick','deselectRecipe(this)')
        .attr('type', 'checkbox')
        .attr('checked', 'true')
        .appendTo(li);

    var a_title = $('<a/>')
        .addClass('ui-all')
        .attr("name", "title")
        .text(element.value)
        .appendTo(li);

    var a_ingredients = $('<a/>')
        .addClass('ui-all')
        .attr("name", "ingredients")
        .attr("hidden", "true")
        .text($(element).closest('li').children('a[name="ingredients"]').html())
        .appendTo(li);

    var a_servings = $('<a/>')
        .addClass('ui-all')
        .attr("name", "servings")
        .attr("hidden", "true")
        .text($(element).closest('li').children('a[name="servings"]').html())
        .appendTo(li);

    var a_instructions = $('<a/>')
        .addClass('ui-all')
        .attr("name", "instructions")
        .attr("hidden", "true")
        .text($(element).closest('li').children('a[name="instructions"]').html())
        .appendTo(li);
}

function deselectRecipe(element){
    console.log("deselect recipe");

    let parent_element = element.closest('li');

    console.log("removing:");
    console.log(parent_element);

    parent_element.remove();
}

function send_json(){
    console.log("Send JSON");

    let name = $('#name_hide').text();
    let allergies = $('#allergies_hide').text();
    let intolerances = $('#intolerances_hide').text();
    let goals = $('#goals_hide').text();
    let bmr = $('#bmr_hide').text();
    let lifestyle = $('#lifestyle_hide').text();


    let json = new Object();

    var days = [];

    for (let k = 1; k <= 7; k++){
        let div_id = "day_" + k;
        let div_element = $('#' + div_id);

        let day_object = new Object();
        day_object.name = div_id;

        var recipes = [];

        $('#' + div_id).children('li').each(function () {
            let re_title = $(this).children('a[name="title"]').html();
            let re_ingredients = $(this).children('a[name="ingredients"]').html();
            let re_servings = $(this).children('a[name="servings"]').html();
            let re_instructions = $(this).children('a[name="instructions"]').html();

            recipes.push({
                "title" : re_title,
                "ingredients" : re_ingredients,
                "servings" : re_servings,
                "instructions" : re_instructions
            });
        });

        day_object.recipes = recipes;
        days.push(day_object);
    }

    json.name = name;
    json.allergies = allergies;
    json.intolerances = intolerances;
    json.goals = goals;
    json.bmr = bmr;
    json.lifestyle = lifestyle;
    json.days = days;

    let servlet = "JSONReceiverServlet";

    let big_url = location.protocol + "//" + location.host + location.pathname;
    let base_url = (big_url).substring(0, big_url.lastIndexOf('/') +1);
    let complete_url = base_url + servlet;

    $.ajax({
        type: "POST",
        url: complete_url,
        data: {"json": JSON.stringify(json)},
        success: function(res) {
            console.log(res);
        },
        error: function(res) {
            console.log("ERROR", res);
            alert(res);
        },
    });
}
