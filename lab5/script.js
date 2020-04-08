let locations = {
    "Sibiu": ["Sibiu", "Medias", "Agnita"],
    "County 1": ["1 - City 1", "1 - City 2", "1 - City 3"],
    "County 2": ["2 - City 1", "2 - City 2", "2 - City 3"]
};
window.onload = function () {
    for (let location in locations) {
        document.getElementById("counties").append(createOption(location))
    }
    changeCities();

    const counties = document.getElementById("counties");
    counties.onchange = () => changeCities();
};

function changeCities() {
    const counties = document.getElementById("counties");
    const cities = document.getElementById("cities");
    const selectedCounty = counties.options[counties.selectedIndex].text;

    cities.textContent = "";
    for (let city of locations[selectedCounty]) {
        cities.append(createOption(city))
    }
}

function createOption(optionName) {
    const option = document.createElement("option");
    const text = document.createTextNode(optionName);
    option.append(text);
    return option;
}