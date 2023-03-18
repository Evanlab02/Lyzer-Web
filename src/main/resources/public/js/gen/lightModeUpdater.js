export async function updateStyling(lightMode, currentPageLinkId) {
    console.log(lightMode);

    const elements = document.getElementsByClassName("active-nav");
        while (elements.length > 0) {
            elements[0].classList.remove("active-nav");
        }

    const lightElements = document.getElementsByClassName("active-nav-light");
        while (lightElements.length > 0) {
            lightElements[0].classList.remove("active-nav-light");
        }

    if (lightMode === false) {
        document.body.classList.remove("light-mode");
        document.getElementById("results-page-link").classList.remove("light-mode-link");
        document.getElementById("insights-page-link").classList.remove("light-mode-link");
        document.getElementById("patches-page-link").classList.remove("light-mode-link");
        document.getElementById("feedback-page-link").classList.remove("light-mode-link");
        document.getElementById("mode-toggle").classList.remove("light-mode-link");
        document.getElementById(currentPageLinkId).classList.add("active-nav");
        const forms = document.getElementsByTagName("form");
        for (let i = 0; i < forms.length; i++) {
            forms[i].classList.remove("form-light");
        }
    } else {
        document.body.classList.add("light-mode");
        document.getElementById("results-page-link").classList.add("light-mode-link");
        document.getElementById("insights-page-link").classList.add("light-mode-link");
        document.getElementById("patches-page-link").classList.add("light-mode-link");
        document.getElementById("feedback-page-link").classList.add("light-mode-link");
        document.getElementById("mode-toggle").classList.add("light-mode-link");
        document.getElementById(currentPageLinkId).classList.remove("light-mode-link");
        document.getElementById(currentPageLinkId).classList.add("active-nav-light");
        const forms = document.getElementsByTagName("form");
        for (let i = 0; i < forms.length; i++) {
            forms[i].classList.add("form-light");
        }
    }
};