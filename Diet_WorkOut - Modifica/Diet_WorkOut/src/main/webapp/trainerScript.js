function getExercises(){
    console.log("get exercise");

    let base_url = "https://api.api-ninjas.com/v1/exercises?";

    let ex_name = $('#name').val();
    let ex_type = $('#type').val();
    let ex_muscle = $('#muscle').val();
    let ex_difficulty = $('#difficulty').val();

    let ex_Name = true;
    let ex_Type = true;
    let ex_Muscle = true;
    let ex_Difficulty = true;
    let added = false;

    if (ex_name === undefined || ex_name === "" || ex_name === null){
        ex_Name = false;
    }
    if (ex_type === undefined || ex_type === "" || ex_type === null || ex_type === "null"){
        ex_Type = false;
    }
    if (ex_muscle === undefined || ex_muscle === "" || ex_muscle === null || ex_muscle === "null"){
        ex_Muscle= false;
    }
    if (ex_difficulty === undefined || ex_difficulty === "" || ex_difficulty === null || ex_difficulty === "null"){
        ex_Difficulty = false;
    }

    if (ex_Name === true){
        base_url += ("name="+ex_name);
        added = true;
    }

    if (ex_Type === true){
        if (added === true){
            base_url += "&";
        }
        base_url += ("type="+ex_type);
        added = true;
    }

    if (ex_Muscle === true){
        if (added === true){
            base_url += "&";
        }
        base_url += ("muscle="+ex_muscle);
        added = true;
    }

    if (ex_Difficulty === true){
        if (added === true){
            base_url += "&";
        }
        base_url += ("difficulty="+ex_difficulty);
    }

    console.log("request url: " + base_url);

    $.ajax({
        url: base_url,
        type: 'GET',
        dataType: 'json', // added data type
        headers: {"x-api-key": "zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd"},
        success: function(res) {
            console.log(res);
            chargeExercises(res);
        },
        error: function(res) {
            console.log("ERROR", res);
            alert(res);
        },
    });

    added = false;
}

function chargeExercises(res){
    console.log("charge exercise");

    var div_list = $('#exercise_list');

    div_list.empty();

    for(var k in res) {
        var li = $('<li/>')
            .addClass('ui-menu-item')
            .attr('role', 'menuitem')
            .appendTo(div_list);

        var input = $('<input/>')
            .addClass('ui-all')
            .attr('onclick','selectExercise(this)')
            .attr('type', 'checkbox')
            .attr('value', res[k].name + " - " + res[k].muscle)
            .appendTo(li);

        var aaa = $('<a/>')
            .addClass('ui-all')
            .text(res[k].name + " - " + res[k].muscle)
            .appendTo(li);
    }
}

function selectExercise(element){
    console.log("select exercise");

    var day_selected = $('#day_input').val();
    var destination_div_id = "day_" + day_selected;
    var destination_div = $('#' + destination_div_id);

    var li = $('<li/>')
        .addClass('ui-menu-item')
        .attr('role', 'menuitem')
        .appendTo(destination_div);

    var ckbox = $('<input/>')
        .addClass('ui-all')
        .attr('onclick','deselectExercise(this)')
        .attr('type', 'checkbox')
        .attr('checked', 'true')
        .appendTo(li);

    var aaa = $('<a/>')
        .addClass('ui-all')
        .text(element.value)
        .appendTo(li);

    var input_set = $('<input/>')
        .addClass('ui-all')
        .attr('type', 'number')
        .attr('name', 'set')
        .attr('maxlenght', '3')
        .attr('placeholder', 'Sets')
        .appendTo(li);

    var input_rep = $('<input/>')
        .addClass('ui-all')
        .attr('type', 'number')
        .attr('name', 'rep')
        .attr('maxlenght', '3')
        .attr('placeholder', 'Reps')
        .appendTo(li);

    var input_rest = $('<input/>')
        .addClass('ui-all')
        .attr('type', 'text')
        .attr('name', 'rest')
        .attr('maxlenght', '3')
        .attr('placeholder', 'Rest')
        .appendTo(li);
}

function deselectExercise(element){
    console.log("deselect exercise");

    let parent_element = element.closest('li');

    console.log("removing:");
    console.log(parent_element);

    parent_element.remove();
}

function send_json(){
    console.log("Send JSON");

    let name = $('#name_hide').text();
    let day = $('#day_hide').text();
    let goals = $('#goals_hide').text();


    let json = new Object();

    var days = [];

    for (let k = 1; k <= day; k++){
        let div_id = "day_" + k;
        let div_element = $('#' + div_id);

        let day_object = new Object();
        day_object.name = div_id;

        var exercises = [];

        $('#' + div_id).children('li').each(function () {
            let ex_name = $(this).children('a').html();
            let ex_set = $(this).children('input[name="set"]').val();
            let ex_rep = $(this).children('input[name="rep"]').val();
            let ex_rest = $(this).children('input[name="rest"]').val();

            exercises.push({
                "name" : ex_name,
                "sets" : ex_set,
                "reps" : ex_rep,
                "rest" : ex_rest
            });
        });

        day_object.exercises = exercises;
        days.push(day_object);
    }

    json.name = name;
    json.day = day;
    json.goals = goals;
    json.days = days;

    let servlet = "JSONReceiverServlet";
    // let base_url = request.getContextPath();
    // let complete_url = base_url+servlet;

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
