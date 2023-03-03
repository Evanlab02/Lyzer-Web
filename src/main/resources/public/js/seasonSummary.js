export async function viewSeasonSummary(season) {
    const nav = document.getElementById("default-nav").innerText;
    const compiledNav = Handlebars.compile(nav);
    let data = {};
    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);

    const template = document.getElementById('default-table-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let seasonSummary = await fetch(`/seasons/${season}`, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        });

    seasonSummary.mainHeader = `Season Summary for ${season}`

    document.getElementById('app').innerHTML = compiledFunction(seasonSummary);
}