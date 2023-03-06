function displayTeamNav() {
    const nav = document.getElementById("team-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);
}

async function displayTeamsForm() {
    const template = document.getElementById('team-form-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let teamData = [];

    await fetch("constructors", {method:'GET'})
        .then(response => response.json())
        .then(data => {
            teamData = data;
        });

    const allYears = Object.keys(teamData);
    const data = {
        years: allYears,
        teams: Object.keys(teamData[allYears[0]])
    };
    document.getElementById('app').innerHTML = compiledFunction(data);
    return teamData;
}

function updateTeamDropdown(teamData, year) {
    const teams = Object.keys(teamData[year]);

    const dropdownData = {
        teams: teams
    }

    const eventTemplate = document.getElementById('populate-team-dropdown').innerText;
    const eventCompiledFunction = Handlebars.compile(eventTemplate);

    document.getElementById('team-div').innerHTML = eventCompiledFunction(dropdownData);
    console.log("Changed Data");
}

export async function displayTeamSearchPage(router) {
    displayTeamNav();
    const teamData = await displayTeamsForm();
    
    document.getElementById('year-dd').addEventListener('change', (event) => {
        event.preventDefault();
        const year = event.target.value;
        updateTeamDropdown(teamData, year);
    });

    document.getElementById("teams-form").addEventListener("submit", (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const year = data.get("year-dd");
        let team = data.get("team-dd");
        team = team.replaceAll(" ", "_");
        router.navigateTo(`/constructors/${year}/${team}`);

    });
}

export async function viewTeamResults(year, team) {
    displayTeamNav();
    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);
    let resultData = await fetch(`constructors/${year}/${team}`,{method:'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        });
    team = team.replaceAll("_", " ");
    if (team === "all") {
        resultData.mainHeader = `Constructor Standings`;
    } else {
        resultData.mainHeader = `${team} Results for ${year}`;
    }
    document.getElementById('app').innerHTML = compiledFunction(resultData);
}