let hobbies = Array(4).fill('');

const fadeTime = 200;

function changeHobbies()
{
    $('.gray-background').fadeIn(fadeTime);
    $('.modal').fadeIn(fadeTime);

    for (let index in hobbies)
    {
        $('#hobby' + (index + 1)).val(hobbies[index]);
    }
}

function submitHobbies()
{
    let hobbyValue = '';
    for (let index in hobbies)
    {
        let number = parseInt(index) + 1;
        let value = $('#hobby' + number).val();
        if (value)
        {
            hobbyValue += value + ';';
        }
        hobbies[index] = value;
    }

    $('#hobbies').val(hobbyValue);
    exitModal();
}

function exitModal()
{
    $('.gray-background').fadeOut();
    $('.modal').fadeOut(fadeTime);
}