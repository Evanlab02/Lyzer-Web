import { 
    getSeasonSummary,
    getDriverStandings,
    getDriverResults,
    getConstructorResults,
    getResults
} from "../api/apiHelpers.js";

export async function viewSeasonSummary(season) {
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    const seasonSummary = await getSeasonSummary(season);

    seasonSummary.mainHeader = `Season Summary for ${season}`;
    document.getElementById('app').innerHTML = compiledFunction(seasonSummary);
}

export async function viewDriverStandings(year) {
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    const driverStandings = await getDriverStandings(year);

    driverStandings.mainHeader = `Driver Standings for ${year}`;
    document.getElementById('app').innerHTML = compiledFunction(driverStandings);
}

export async function viewDriverResults(year, driverSurname, driverName) {
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    const driverResults = await getDriverResults(year, driverSurname, driverName);

    driverResults.mainHeader = `Driver Results for ${driverName} ${driverSurname} in ${year}`;
    document.getElementById('app').innerHTML = compiledFunction(driverResults);
}

export async function viewConstructorResults(year, team) {
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    const constructorResults = await getConstructorResults(year, team);

    if (team == "All") {
        constructorResults.mainHeader = `Constructor Standings for ${year}`;
    } else {
        team = team.replaceAll("_", " ");
        constructorResults.mainHeader = `Constructor Results for ${team} in ${year}`;
    }

    document.getElementById('app').innerHTML = compiledFunction(constructorResults);
}

export async function viewResults(category, year, location) {
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    const results = await getResults(category, year, location);

    const dataManipCategories = ["qualifying", "firstPractice", "secondPractice", "thirdPractice"];
    if (dataManipCategories.includes(category)) {
        const dataRows = results.rows;
        dataRows.forEach(element => {
            const lastElement = element.pop();
            while (element.length < results.headers.length -1) {
                element.push("");
            }
            element.push(lastElement);
        });
        results.rows = dataRows;
    }       

    results.mainHeader = `Results for ${location} in ${year}`;

    document.getElementById('app').innerHTML = compiledFunction(results);
}