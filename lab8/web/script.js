let myboard = []
let attackboard = []
let letters = "ABCDEFGHIJ"
let boat1
let boat2

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

function phase1()
{
    phase = 1
    announce("Make your move!")
}

function phase0()
{
    phase = 0
    announce("Wait for the other player to make his move!")
//    TODO create timer and make post request
}

function squareClicked(tableId, x, y)
{
    if (placeBoard1Condition(tableId, x))
    {
        phase = -1
        boat1 = [x, y]
        return
    }
    if (placeBoard2Condition(tableId, y))
    {
        boat2 = [x, y]
        if (player === 1) phase1()
        else phase0()
        return
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
