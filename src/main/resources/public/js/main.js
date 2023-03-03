import { displayRaceForm, viewRaceResults } from "./raceResults.js";
import { viewSeasonSummary } from "./seasonSummary.js";

function updateVersion(elementId, url) {
    const version = document.getElementById(elementId)

    fetch(url, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            version.innerText = `${elementId} : ${data.version}`;
        })
}

async function displayDefaultTemplate(router) {
    const nav = document.getElementById("default-nav").innerText;
    const compiledNav = Handlebars.compile(nav);

    let data = {};

    document.getElementById("dynamic-nav").innerHTML = compiledNav(data);
    
    const template = document.getElementById('default-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let seasonData = [];

    await fetch('/seasons', {method:'GET'})
        .then(response => response.json())
        .then(data => {
            seasonData = Object.keys(data);
        });

    data = {
        seasons: seasonData
    };

    document.getElementById('app').innerHTML = compiledFunction(data);

    document.getElementById("seasons-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const season = data.get("seasons-dd");
        router.navigateTo(`/results/summaries/${season}`);
    });
}

window.addEventListener('load', () => {
    const app = $('#app');

    const errorTemplate = Handlebars.compile($('#error-template').html());
    const defaultTemplate = Handlebars.compile($('#default-template').html());
    const defaultTableTemplate = Handlebars.compile($('#default-table-template').html());
    const resultsRacesTemplate = Handlebars.compile($("#results-races-template").html());

    const router = new Router({
        mode:'hash',
        root:'index.html',
        page404: (path) => {
            const html = errorTemplate;
            app.html(html);
        }
    });

    router.add("/results/summaries", async () => {
        let html = defaultTemplate();
        app.html(html);
        displayDefaultTemplate(router);
    });

    router.add("/results/summaries/{season}", async (season) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewSeasonSummary(season);
    });

    router.add("/results/races", async () => {
        let html = resultsRacesTemplate();
        app.html(html);
        displayRaceForm(router);
    });

    router.add("/results/races/{year}/{location}", async (year, location) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewRaceResults(year, location);
    });

    router.addUriListener();

    $('a').on('click', (event) => {
        event.preventDefault();
        const target = $(event.target);
        const href = target.attr('href');
        router.navigateTo(href);
    });
    
    router.navigateTo('/results/summaries');
    displayDefaultTemplate(router);

    updateVersion("web", "/version")
    updateVersion("scraper", "/version/scraper")
});
