import { displayResultsSearchPage } from "./gen/resultsGenerator.js";
import { 
    viewSeasonSummary,
    viewDriverStandings,
    viewDriverResults,
    viewConstructorResults,
    viewResults
} from "./gen/tableGenerator.js";
import { updateStyling } from "./gen/lightModeUpdater.js";
import { viewFeedbackForm } from "./gen/feedbackEvents.js";

window.addEventListener('load', () => {
    let lightMode = false;
    console.log(lightMode);

    const app = $('#app');

    const pageNotFoundTemplate = Handlebars.compile($('#page-not-found-template').html());
    const searchResultsTemplate = Handlebars.compile($('#search-results-template').html());
    const defaultTableTemplate = Handlebars.compile($('#default-table-template').html());
    const insightsPageTemplate = Handlebars.compile($('#insights-page-template').html());
    const patchNotesTemplate = Handlebars.compile($("#patch-notes").html());
    const feedbackPageTemplate = Handlebars.compile($('#feedback-template').html());

    const router = new Router({
        mode:'hash',
        root:'index.html',
        page404: (path) => {
            const html = pageNotFoundTemplate;
            app.html(html);
        }
    });

    router.add("/results", async () => {
        let html = searchResultsTemplate();
        app.html(html);
        displayResultsSearchPage(router, lightMode);
    });

    router.add("/results/seasons/{year}", async (year) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewSeasonSummary(year);
    });

    router.add("/results/drivers/{year}", async (year) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewDriverStandings(year);
    });

    router.add("/results/drivers/{year}/{driverSurname}/{driverName}", 
    async (year, driverSurname, driverName) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewDriverResults(year, driverSurname, driverName);
    });

    router.add("/results/constructors/{year}/{location}", async (year, location) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewConstructorResults(year, location);
    });

    router.add("/results/{category}/{year}/{location}", async (category, year, location) => {
        let html = defaultTableTemplate();
        app.html(html);
        viewResults(category, year, location);
    });

    router.add("/insights", () => {
        updateStyling(lightMode, "insights-page-link");
        let html = insightsPageTemplate();
        app.html(html);
    });

    router.add("/feedback", () => {
        updateStyling(lightMode, "feedback-page-link");
        let html = feedbackPageTemplate();
        app.html(html);
        viewFeedbackForm(lightMode, "");
    });

    router.add("/patch-notes", async () => {
        updateStyling(lightMode, "patches-page-link");
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

    router.navigateTo('//results');
    displayResultsSearchPage(router, lightMode);

    document.getElementById("mode-toggle").addEventListener('click', (event) => {
        event.preventDefault();
        if (lightMode === false) {
            lightMode = true;
        } else {
            lightMode = false;
        }
        router.navigateTo('//results');
        displayResultsSearchPage(router, lightMode);
    });
});

function updateVersion(elementId, url) {
    const version = document.getElementById(elementId)

    fetch(url, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            version.innerText = `${elementId} : ${data.version}`;
        })
}