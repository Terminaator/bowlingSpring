function gameHTML(id) {
    var content = "<div id='" + id + "' class='boxom'>";
    content += "<div class='text-right'>";
    content += "<input id='game_player_name_" + id + "' type='text'/>";
    content += "<button onclick='addPlayer(" + id + ")' class='btn btn-outline-dark'>add player</button>";
    content += "</div>";
    content += "</div>";
    return content;
}

function playerHTML(game, player, result, frameScore, total) {
    var content = "";
    content += table(game, player, result, frameScore, total);
    content += "<div class='text-right'>";
    content += "<input id='game_player_name_roll_" + game + "_" + player + "' type='text'/>";
    content += "<button class='btn btn-dark' onclick='addRoll(" + game + "," + player + ")'>roll</button>";
    content += "</div>";
    return content;
}

function table(game, player, result, frameScore, total) {
    var content = "<div id='game_" + game + "_player_table_" + player + "'>";
    content += "<div class='text-center'>";
    content += "<label>" + "player: " + player + "</label>";
    content += "<label>" + "total: " + total + "</label>";
    content += "</div>";
    content += "<table class='table table-bordered'>";
    content += "<tr>";
    for (index = 0; index < result.length; index++) {
        content += '<td>' + result[index] + '</td>';
    }
    content += "</tr>";
    content += "<tr>";

    for (var index = 0; index < frameScore.length; index++) {
        content += '<td>' + frameScore[index] + '</td>';
    }
    content += "</tr>";

    content += "</table>";
    content += "</div>";
    return content;
}

function createGame() {
    $.post("/game/create")
        .done(function (data) {
            $('#games').append(gameHTML(data.id));
        });
}

function addPlayer(game) {
    $.ajax({
        url: "/game/" + game + "/add",
        type: "POST",
        data: JSON.stringify({name: document.getElementById("game_player_name_" + game).value}),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(function (data) {
        $('#' + game).append(playerHTML(game, data.table.player.id, data.table.result, data.table.frameScore, data.table.total));

    });
}

function addRoll(game, player) {
    $.ajax({
        url: "/game/" + game + "/roll",
        type: "POST",
        data: JSON.stringify({
            playerId: player,
            roll: document.getElementById("game_player_name_roll_" + game + "_" + player).value
        }),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(function (data) {
        $('#game_' + game + "_player_table_" + player).replaceWith(table(game, player, data.table.result, data.table.frameScore, data.table.total));
    });
}


window.addEventListener("load", function () {
    $.getJSON("/game/all")
        .done(function (data) {
            console.log(data);
            for (var index = 0; index < data.length; index++) {
                $('#games').append(gameHTML(data[index].id));
                for (var x = 0; x < data[index].tables.length; x++) {
                    $('#' + data[index].id).append(
                        playerHTML(data[index].id, data[index].tables[x].player.id, data[index].tables[x].result, data[index].tables[x].frameScore, data[index].tables[x].total)
                    );

                }
            }
        });
});