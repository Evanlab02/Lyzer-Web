import { getCategories, getLocations } from "../api/apiHelpers.js";

export async function updateCategoryDropdown(year, categories) {
    const template = document.getElementById('category-dropdown-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    categories = await getCategories(year);
    categories = categories.map(category => {
        return {
            "value": category[0],
            "text": category[1]
        }
    });
    const templateData = {
        categories: categories
    };
    document.getElementById('category-selection-div').innerHTML = compiledFunction(templateData);
}

export async function updateLocationDropdown(year, category, locations) {
    const template = document.getElementById('location-dropdown-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let inputLabel = "location";
    switch (category) {
        case "drivers":
            inputLabel = "Driver";
            break;
        case "constructors":
            inputLabel = "Constructor";
            break;
        default:
            inputLabel = "Location";
            break;
    }

    locations = await getLocations(year, category);
    const templateData = {
        locations: locations,
        inputLabel: inputLabel,
    };
    document.getElementById('location-selection-div').innerHTML = compiledFunction(templateData);
}