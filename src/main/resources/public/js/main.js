import { displaySearchPage, viewResults } from "./resultsCompiler.js";
import { viewSeasonSummary } from "./seasonSummary.js";
import { displayDriverSearchPage, viewSpecificDriverResults, viewAllDriversResults } from "./driverCompiler.js";
import { displayTeamSearchPage, viewTeamResults } from "./teamCompiler.js";

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
    const resultsFormTemplate = Handlebars.compile($("#results-form-template").html());
    const driverFormTemplate = Handlebars.compile($("#driver-form-template").html());
    const teamFormTemplate = Handlebars.compile($("#team-form-template").html());
    const patchNotesTemplate = Handlebars.compile($("#patch-notes").html());

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

    router.add("/results/{dataType}", async (dataType) => {
        let html = resultsFormTemplate();
        app.html(html);
        displaySearchPage(router, dataType)
    });

    router.add("/results/{dataType}/{year}/{location}", async (dataType, year, location) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewResults(dataType, year, location);
    });

    router.add("/drivers", async () => {
        let html = driverFormTemplate();
        app.html(html);
        displayDriverSearchPage(router);
    });

    router.add("/drivers/{year}", async (year) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewAllDriversResults(year);
    });

    router.add("/drivers/{year}/{driverSurname}/{driverName}",
    async (year, driverSurname, driverName) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewSpecificDriverResults(year, driverSurname, driverName);
    });

    router.add("/constructors", async () => {
        let html = teamFormTemplate();
        app.html(html);
        displayTeamSearchPage(router);        
    });

    router.add("/constructors/{year}/{team}", async (year, team) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewTeamResults(year, team);
    });

    router.add("/patch-notes", async () => {
        let html = patchNotesTemplate();
        app.html(html);
        const patchNotes = await fetch('/patchnotes', {method:'GET'})
            .then(response => response.json())
            .then(data => {
                return data;
            });
        const data = {
            patchNotes: patchNotes
        }
        const template = document.getElementById('patch-notes').innerText;
        const compiledFunction = Handlebars.compile(template);
        document.getElementById('app').innerHTML = compiledFunction(data);
        updateVersion("web", "/version")
        updateVersion("scraper", "/version/scraper")
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
});
