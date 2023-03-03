export async function displayRaceForm(router) {
    const nav = document.getElementById("default-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);
    
    const template = document.getElementById('results-races-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let raceData = [];

    await fetch('/races', {method:'GET'})
        .then(response => response.json())
        .then(data => {
            raceData = data;
        });


    const allYears = Object.keys(raceData);
    data = {
        years: allYears,
        locations: Object.keys(raceData[allYears[0]])
    };
    document.getElementById('app').innerHTML = compiledFunction(data);

    document.getElementById('year-dd').addEventListener('change', (event) => {
        event.preventDefault();
        const year = event.target.value;
        const locations = Object.keys(raceData[year]);

        const eventData = {
            locations: locations
        }

        const eventTemplate = document.getElementById('populate-location-dropdown').innerText;
        const eventCompiledFunction = Handlebars.compile(eventTemplate);

        document.getElementById('location-div').innerHTML = eventCompiledFunction(eventData);
        console.log("Changed Data");
    });

    document.getElementById("races-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const year = data.get("year-dd");
        const location = data.get("location-dd").replace(" ", "_");
        router.navigateTo(`/results/races/${year}/${location}`);
    });
}

export async function viewRaceResults(year, location) {
    const nav = document.getElementById("default-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);

    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);
    let raceResult = await fetch(`/races/${year}/${location}`, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        });

    raceResult.mainHeader = `Race result for ${year} ${location}`;

    document.getElementById('app').innerHTML = compiledFunction(raceResult);
}