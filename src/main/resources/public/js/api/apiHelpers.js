export async function getYears() {
    const response = await fetch('/api/years', {method: 'GET'})
        .then(response => response.json())
        .then(data => data[0]);
    const years = response;
    return years;
}

export async function getCategories(year) {
    const response = await fetch(`/api/categories/${year}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data);
    const categories = response;
    return categories;
}

export async function getLocations(year, category) {
    const response = await fetch(`/api/locations/${year}/${category}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data[0]);
    const locations = response;
    return locations;
}

export async function getSeasonSummary(season) {
    return await fetch(`/seasons/${season}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data);
}

export async function getDriverStandings(season) {
    return await fetch(`/drivers/${season}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data);
}

export async function getDriverResults(year, driverSurname, driverName) {
    return await fetch(`/drivers/${year}/${driverSurname}/${driverName}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data);
}

export async function getConstructorResults(year, location) {
    return await fetch(`/constructors/${year}/${location}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data);
}

export async function getResults(category, year, location) {
    return await fetch(`results/${category}/${year}/${location}`, {method: 'GET'})
        .then(response => response.json())
        .then(data => data);
}