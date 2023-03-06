function displayDriverNav() {
    const nav = document.getElementById("driver-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);
}

async function displayDriversForm() {
    const template = document.getElementById('driver-form-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let driverData = [];

    await fetch("drivers", {method:'GET'})
        .then(response => response.json())
        .then(data => {
            driverData = data;
        });

    const allYears = Object.keys(driverData);
    const data = {
        years: allYears,
        drivers: Object.keys(driverData[allYears[0]])
    };
    document.getElementById('app').innerHTML = compiledFunction(data);
    return driverData;
}

function updateDriverDropdown(driverData, year) {
    const drivers = Object.keys(driverData[year]);

    const dropdownData = {
        drivers: drivers
    }

    const eventTemplate = document.getElementById('populate-driver-dropdown').innerText;
    const eventCompiledFunction = Handlebars.compile(eventTemplate);

    document.getElementById('driver-div').innerHTML = eventCompiledFunction(dropdownData);
    console.log("Changed Data");
}

export async function displayDriverSearchPage(router) {
    displayDriverNav();
    const driverData = await displayDriversForm();
    
    document.getElementById('year-dd').addEventListener('change', (event) => {
        event.preventDefault();
        const year = event.target.value;
        updateDriverDropdown(driverData, year);
    });

    document.getElementById("drivers-form").addEventListener("submit", (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const year = data.get("year-dd");
        const driverArgs = data.get("driver-dd").split(", ");
        if (driverArgs.length < 2) {
            router.navigateTo(`/drivers/${year}`);
        } else {
            const driverSurname = driverArgs[0];
            const driverName = driverArgs[1];
            router.navigateTo(`/drivers/${year}/${driverSurname}/${driverName}`);
        } 
    });
}

export async function viewAllDriversResults(year) {
    displayDriverNav();
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);
    let resultData = await fetch(`drivers/${year}`,{method:'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        });
    resultData.mainHeader = `Driver Standings for ${year}`;
    document.getElementById('app').innerHTML = compiledFunction(resultData);
}

export async function viewSpecificDriverResults(year, driverSurname, driverName) {
    displayDriverNav();
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);
    let resultData = await fetch(`drivers/${year}/${driverSurname}/${driverName}`,{method:'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        });
    resultData.mainHeader = `Results - ${year} - ${driverName} ${driverSurname}`;
    document.getElementById('app').innerHTML = compiledFunction(resultData);
}