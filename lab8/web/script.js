let myboard = []
let attackboard = []
let letters = "ABCDEFGHIJ"
let boat1
let boat2
const URL = "/battleships"
let timer = null

function createBoards(matrix, tableId)
{
    let board = $(tableId)

    let header = $('<tr>')
    header.append($('<td>'))
    for (let letter of letters)
    {
        header.append($('<td>').text(letter))
    }
    board.append(header)

    for (let i = 0; i < 10; i++)
    {
        let row = $('<tr>')
        row.append($('<td>').text(i + 1))
        matrix.push([])
        for (let j = 0; j < 10; j++)
        {
            let td = $('<td>');
            matrix[i].push(td)
            td.click(() => squareClicked(tableId, j, i))
            td.mouseover(() => squareHovered(tableId, j, i))
            td.mouseout(() => squareOut(tableId, j, i))
            td.css('cursor', 'pointer')
            row.append(td)
        }
        board.append(row)
    }
}

/*function fetchBoats()
{
    boat1 = [boats.boat1x, boats.boat1y]
    boat2 = [boats.boat2x, boats.boat2y]
    drawShip1(boat1[0], boat1[1], 'navy')
    drawShip2(boat2[0], boat2[1], 'navy')
}*/

function color(matrix, x, y, color)
{
    matrix[y][x].css('background-color', color);
}

function drawBattlefield()
{
    console.log("Drawing battlefield")
    $.getJSON(URL + "/battlefield",
        (data) =>
        {
            for (let square of data)
            {
                if (square.hit2)
                {
                    let matrix
                    if (player === 1) matrix = myboard
                    else if (player === 2) matrix = attackboard
                    let drawColor = 'grey';
                    if (square.ship1) drawColor = 'red'
                    color(matrix, square.x, square.y, drawColor);
                }
                if (square.hit1)
                {
                    let matrix
                    if (player === 2) matrix = myboard
                    else if (player === 1) matrix = attackboard
                    let drawColor = 'grey';
                    if (square.ship2) drawColor = 'red'
                    color(matrix, square.x, square.y, drawColor);
                }
                if (!square.hit2 && square.ship1 && player === 1 || !square.hit1 && square.ship2 && player === 2)
                {
                    color(myboard, square.x, square.y, 'navy');
                }
            }
            let playerWin = checkWin(data);
            if (playerWin !== 0)
            {
                announce(`Player ${playerWin} won!`)
            }
        }
    )
}

function phase1()
{
    console.log("Phase 1")
    phase = 1
    announce(`Player ${player} - Make your move!`)
    drawBattlefield()
}

function checkWin(data)
{
    if (checkWinPlayer(1, data)) return 1
    if (checkWinPlayer(2, data)) return 2
    return 0
}

function gameStarted(player, data)
{
    if (player === 2)
    {
        for (let square of data)
        {
            if (square.ship1)
            {
                return true
            }
        }
        return false;
    }

    for (let square of data)
    {
        if (square.ship2)
        {
            return true
        }
    }
    return false;
}

function checkWinPlayer(player, data)
{
    if (!gameStarted(player, data)) return false
    if (player === 1)
    {
        for (let square of data)
        {
            if (square.ship2 && !square.hit1)
            {
                return false
            }
        }
        return true;
    }

    for (let square of data)
    {
        if (square.ship1 && !square.hit2)
        {
            return false
        }
    }
    return true;
}

function phase0()
{
    phase = 0
    announce(`Player ${player} - Wait for the other player!`)
    if (timer != null)
    {
        clearInterval(timer)
    }
    timer = setInterval(() =>
    {
        $.get(URL + "/turn", {player: player}, (phase) =>
        {
            if (phase == 1)
            {
                console.log("Your turn came")
                clearInterval(timer)
                timer = null
                phase1()
            }
        })
    }, 1000)
    drawBattlefield()
}

function updateShips()
{
    console.log("Update ships:" + boat1 + boat2)
    let data = [player, boat1[0], boat1[1], boat2[0], boat2[1]]
    $.ajax(URL + "/ships", {type: "POST", data: data.join(";")})
}

function squareClicked(tableId, x, y)
{
    console.log("Square clicked: "+ x + " " +y)
    if (placeBoard1Condition(tableId, x))
    {
        phase = -1
        boat1 = [x, y]
        return
    }
    if (placeBoard2Condition(tableId, y))
    {
        boat2 = [x, y]
        updateShips()
        if (player === 1) phase1()
        else phase0()
    }
    if (phase === 1 && tableId === "#attackboard")
    {
        let data = [player, x, y]
        $.post(URL + "/hit", data.join(";"), () => phase0())
    }
}

function squareHovered(tableId, x, y)
{
    if (placeBoard1Condition(tableId, x))
    {
        drawShip1(x, y, 'navy')
        return
    }
    if (placeBoard2Condition(tableId, y))
    {
        drawShip2(x, y, 'navy')
        return
    }
}

function drawShip1(x, y, color)
{
    for (let i = 0; i < 4; i++)
    {
        myboard[y][x + i].css('background-color', color)
    }
}

function drawShip2(x, y, color)
{
    for (let i = 0; i < 4; i++)
    {
        myboard[y + i][x].css('background-color', color)
    }
}

function squareOut(tableId, x, y)
{
    if (placeBoard1Condition(tableId, x))
    {
        drawShip1(x, y, 'white')
        return
    }
    if (placeBoard2Condition(tableId, y))
    {
        drawShip2(x, y, 'white')
        drawShip1(boat1[0], boat1[1], 'navy')
        return
    }
}

function placeBoard1Condition(tableId, x)
{
    return phase === -2 && tableId === '#myboard' && x <= 6;
}

function placeBoard2Condition(tableId, y)
{
    return phase === -1 && tableId === '#myboard' && y <= 6;
}

function announce(message)
{
    $('#announcer').html(message)
}

function resetGame()
{
    $.post(URL + "/reset")
    if (timer != null)
    {
        clearInterval(timer)
        timer = null
    }
    window.location.href=URL
}

function showEnemyShips()
{
    $.getJSON(URL + "/ships", {player:player===1?2:1}, (ships) => console.log(ships))
    return "The ships are 4 squares long. \nShip1 goes from that point to the right and ship2 2 goes to the bottom."
}