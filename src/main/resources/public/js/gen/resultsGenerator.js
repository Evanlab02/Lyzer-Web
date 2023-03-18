import { getYears, getCategories, getLocations } from "../api/apiHelpers.js";
import { updateCategoryDropdown, updateLocationDropdown } from "./dropdownUpdater.js";
import { updateStyling } from "./lightModeUpdater.js";

export async function displayResultsSearchPage(router, lightMode) {
    updateStyling(lightMode, "results-page-link");
    
    const template = document.getElementById('search-results-template').innerText;
    const compiledFunction = Handlebars.compile(template);
    
    const years = await getYears();
    years.reverse();
    let selectedYear =  years[0];

    let categories = []
    if (years.length > 0) {
        categories = await getCategories(selectedYear);
    } else {
        categories = [];
    }

   
    let selectedCategory = categories[0][0];
    let locations = [];
    if (categories.length > 0) {
        locations = await getLocations(years[0], selectedCategory);
    } else {
        locations = [];
    }

    categories = categories.map(category => {
        return {
            "value": category[0],
            "text": category[1]
        }
    });

    let formClassName = "";
    if (lightMode === true) {
        formClassName = "form-light";
    }

    const templateData = {
        years: years,
        categories: categories,
        locations: locations,
        formClassName: formClassName
    };
    console.debug(templateData);

    document.getElementById('app').innerHTML = compiledFunction(templateData);

    document.getElementById('year-dd').addEventListener('change', async (event) => {
        event.preventDefault();
        console.debug("Changing Dropdowns");
        selectedYear = event.target.value;
        await updateCategoryDropdown(selectedYear, categories);
        await updateLocationDropdown(selectedYear, selectedCategory, locations);

        document.getElementById('category-dd').addEventListener('change', async (event) => {
            event.preventDefault();
            console.debug("Changing Dropdowns");
            selectedCategory = event.target.value;
            await updateLocationDropdown(selectedYear, selectedCategory, locations);
        });
    });

    document.getElementById('category-dd').addEventListener('change', async (event) => {
        event.preventDefault();
        console.debug("Changing Dropdowns");
        selectedCategory = event.target.value;
        await updateLocationDropdown(selectedYear, selectedCategory, locations);
    });

    document.getElementById('search-results-form').addEventListener('submit', async (event) => {
        event.preventDefault();
        const formSubmissionData = new FormData(event.target);
        const year = formSubmissionData.get('year-dd');
        const category = formSubmissionData.get('category-dd');
        let location = formSubmissionData.get('location-dd');

        location = location.replaceAll(" ", "_");
        switch (category) {
            case "seasons":
                router.navigateTo(`/results/seasons/${year}`);
                break;
            case "drivers":
                if (location === "All") {
                    router.navigateTo(`/results/drivers/${year}`);
                } else {
                    const driverSurname = location.split(",")[0];
                    const driverName = location.split(",")[1].replaceAll("_", "");
                    router.navigateTo(`/results/drivers/${year}/${driverSurname}/${driverName}`);
                }
                break;
            case "constructors":
                router.navigateTo(`/results/constructors/${year}/${location}`);
                break;
            default:
                router.navigateTo(`/results/${category}/${year}/${location}`);
                break;
        };
    });
}