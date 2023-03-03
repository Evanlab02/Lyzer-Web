function updateVersion(elementId, url) {
    const version = document.getElementById(elementId)

    fetch(url, {method:'GET'})
        .then(response => response.json())
        .then(data => {
            version.innerText = `${elementId} : ${data.version}`;
        })
}

async function displayDefaultTemplate() {
    const template = document.getElementById('default-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let seasonData = [];

    await fetch('/seasons', {method:'GET'})
        .then(response => response.json())
        .then(data => {
            seasonData = Object.keys(data);
        });

    let data = {
        seasons: seasonData
    };

    document.getElementById('app').innerHTML = compiledFunction(data);
}

window.addEventListener('load', () => {
    const app = $('#app');

    const defaultTemplate = Handlebars.compile($('#default-template').html());

    const router = new Router({
        mode:'hash',
        root:'index.html',
        page404: (path) => {
            const html = defaultTemplate();
            app.html(html);
            displayDefaultTemplate();
        }
    });

    router.add("/", async () => {
        let html = defaultTemplate();
        app.html(html);
        displayDefaultTemplate();
    });

    router.addUriListener();

    $('a').on('click', (event) => {
        event.preventDefault();
        const target = $(event.target);
        const href = target.attr('href');
        const path = href.substring(href.lastIndexOf('/'));
        router.navigateTo(path);
    });
    
    router.navigateTo('/');
    displayDefaultTemplate();

    updateVersion("web", "/version")
    updateVersion("scraper", "/version/scraper")
});
