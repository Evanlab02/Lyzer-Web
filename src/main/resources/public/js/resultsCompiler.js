function displayDefaultNav() {
    const nav = document.getElementById("default-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);
}

async function displayResultsForm(dataType) {
    const template = document.getElementById('results-form-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let resultsData = [];

    await fetch(`results/${dataType}`, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            resultsData = data;
        });

    const allYears = Object.keys(resultsData);
    let header = dataType.charAt(0).toUpperCase() + dataType.slice(1);
    header = header.replaceAll("_", " ");
    const data = {
        mainHeader: `Search - ${header}`,
        years: allYears,
        locations: Object.keys(resultsData[allYears[0]])
    };
    document.getElementById('app').innerHTML = compiledFunction(data);
    return resultsData;
}

function updateLocationDropdown(resultsData, year) {
    const locations = Object.keys(resultsData[year]);

    const eventData = {
        locations: locations
    }

    const eventTemplate = document.getElementById('populate-location-dropdown').innerText;
    const eventCompiledFunction = Handlebars.compile(eventTemplate);

    document.getElementById('location-div').innerHTML = eventCompiledFunction(eventData);
    console.log("Changed Data");
}

export async function displaySearchPage(router, dataType) {
    displayDefaultNav();
    const resultsData = await displayResultsForm(dataType);
    
    document.getElementById('year-dd').addEventListener('change', (event) => {
        event.preventDefault();
        const year = event.target.value;
        updateLocationDropdown(resultsData, year);
    });

    document.getElementById("results-form").addEventListener("submit", (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const year = data.get("year-dd");
        const location = data.get("location-dd").replace(" ", "_");
        router.navigateTo(`/results/${dataType}/${year}/${location}`);
    });
}

export async function viewResults(dataType, year, location) {
    const nav = document.getElementById("default-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);

    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);
    let resultData = await fetch(`results/${dataType}/${year}/${location}`, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        });

    let updatedRows = []
    for (let rowIndex in resultData.rows) {
        let rowValue = resultData.rows[rowIndex];
        const lastValue = rowValue.pop();
        while (rowValue.length < resultData.headers.length - 1) {
            rowValue.push("");
        };
        rowValue.push(lastValue);
        updatedRows.push(rowValue);
    };
    resultData.rows = updatedRows;

    let header = dataType.charAt(0).toUpperCase() + dataType.slice(1);
    header = header.replaceAll("_", " ");
    resultData.mainHeader = `${header} result for ${year} ${location.replaceAll("_", " ")}`;

    document.getElementById('app').innerHTML = compiledFunction(resultData);
}