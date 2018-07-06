/*
    AJAX
        Asynchronus JavaScript And XML
            - not a programming language
            - technique for accessing web servers from a web page using JavaScript
*/

window.onload = function(){
    // Add event listners onto various page elements
    idField.addEventListener('blur', fieldsValid);
    selectField.addEventListener('blur', fieldsValid);
    submitBtn.addEventListener('click', fetchInfo);

    // Disable the submit btn 
    submitBtn.setAttribute('disabled', true);

    // Hide alert message
    alertMessage.style.display = 'none';
}

// check to see if the id has a non-empty string 
function fieldsValid(){
    let id = idField.value;
    let category = selectField.value;

    if(id == '' || category == ''){
        submitBtn.setAttribute('disabled', true);
        alertMessage.style.display = 'block';
    }else{
        alertMessage.style.display = 'none';
        submitBtn.removeAttribute('disabled');
    }
}

function fetchInfo() {
    console.log("in fetchInfo");

    let id = idField.value;
    let category = selectField.value;
    createResultsContainer(container);

    /*
        Notes about Promises
    */
   fetch(`https://swapi.co/api/${category}/${id}/`).then((resp) => {
        if(category == 'people'){
            resp.json().then((jedi) => {
                processPeopleFetch(jedi);
            })
        }
   }).catch(
                $(`#name`).html('Invalid character id!')
            );
}

function getInfo(){
    console.log("in getInfo");

    // Get the value of the input field
    let id = idField.value;

    // Get the value of the select field
    let category = selectField.value;

    // Determine what is being searched for (people, planets, or starships) and create 
    // the appropriate HTML resultsContainer

    createResultsContainer(category);

    /*
        Making asynchronous calls to an external web server using AJAX

            1) Create a XHR (XML-HTTP-Request) Object
            2) Define the XHR callback function which will handle to response when received
            3) Open the XHR request by defining the HTTP method and the target server url
            4) Send the XHR request
    */

    // Step 1
    let xhr = new XMLHttpRequest();

    // Step 2
    xhr.onreadystatechange = function () {
        /*
            The XMLHttpRequest.readyState property returns the state an XHR client is in. 
            An CHR client exists in one of the folowing states:
                0 - UNSENT(the XHR object exists, but .open() has not been called yet)
                1 - OPENED (.open() has been called)
                2 - HEADERS_RECEIVED (.send() has been called, and the response headers and status are available)
                3 - LOADING (downloading the response body; .responseText holds partial information)
                4 - DONE (the operation is complete)
        */
       console.log(xhr.readyState);

       if (xhr.readyState == 4 && xhr.status == 200) {
           if (category == 'people') {
               processPeopleResponse(xhr);
           } else if (category == 'planets') {
               processPlanetResponse(xhr);
           } else if (category == 'starships') {
               processStarshipResponse(xhr);
           }
       }
    }
    // Step 3
    xhr.open('GET', `http://swapi.co/api/${category}/${id}/`, true);

    //Step 4
    xhr.send();
}

function processPeopleResponse(xhr) {
    let jedi = JSON.parse(xhr.responseText);
    console.log(jedi);

    // We will use JQuery here to simplify DOM selections and manipulation statements
    $(`#name`).html(`<b>${jedi.name}</b>`);
    $(`#height`).html(`<b>Height (cm): ${jedi.height}</b>`);
    $(`#mass`).html(`<b>Mass (kg): ${jedi.mass}</b>`);
    $(`#hairColor`).html(`<b>Hair Color: ${jedi.hari_color}</b>`);
    $(`#eyeColor`).html(`<b>Eye Color: ${jedi.eye_color}</b>`);
    $(`#birthYear`).html(`<b>Birth Year: ${jedi.birth_year}</b>`);
    $(`#gender`).html(`<b>Gender: ${jedi.gender}</b>`);
}

function processPlanetResponse(xhr) {
    let planet = JSON.parse(xhr.responseText);
    console.log(planet);
}

function processStarshipResponse(xhr) {
    let starship = JSON.parse(xhr.responseText);
    console.log(starship);
}

function processPeopleFetch(jedi) {
    console.log(jedi);

    $(`#name`).html(`<b>${jedi.name}</b>`);
    $(`#height`).html(`<b>Height (cm): ${jedi.height}</b>`);
    $(`#mass`).html(`<b>Mass (kg): ${jedi.mass}</b>`);
    $(`#hairColor`).html(`<b>Hair Color: ${jedi.hari_color}</b>`);
    $(`#eyeColor`).html(`<b>Eye Color: ${jedi.eye_color}</b>`);
    $(`#birthYear`).html(`<b>Birth Year: ${jedi.birth_year}</b>`);
    $(`#gender`).html(`<b>Gender: ${jedi.gender}</b>`);
}

function createResultsContainer(category) {
    console.log('in createResultContainer');
    while (resultsContainer.firstChild) {
        resultsContainer.removeChild(resultsContainer.firstChild);
    }

    if (category == 'people') {
        let nameContainer = document.createElement('h3');
        let heightContainer = document.createElement('h5');
        let massContainer = document.createElement('h5');
        let hairColorContainer = document.createElement('h5');
        let eyeColorContainer = document.createElement('h5');
        let birthYearContainer = document.createElement('h5');
        let genderContainer = document.createElement('h5');

        nameContainer.setAttribute('id', 'name');
        heightContainer.setAttribute('id', 'height');
        massContainer.setAttribute('id', 'mass');
        hairColorContainer.setAttribute('id', 'hairColor');
        eyeColorContainer.setAttribute('id', 'eyeColor');
        birthYearContainer.setAttribute('id', 'birthYear');
        genderContainer.setAttribute('id', 'gender');
        
        resultsContainer.appendChild(nameContainer);
        resultsContainer.appendChild(heightContainer);
        resultsContainer.appendChild(massContainer);
        resultsContainer.appendChild(hairColorContainer);
        resultsContainer.appendChild(eyeColorContainer);
        resultsContainer.appendChild(birthYearContainer);
        resultsContainer.appendChild(genderContainer);

    } else if (category == 'planets') {
        let nameContainer = document.createElement('h3');
        let rotationalPeriodContainer = document.createElement('h5');
        let orbitalPeriod = document.createElement('h5');
        let diameterContainer = document.createElement('h5');
        let climateContainer = document.createElement('h5');
        let terrainContainer = document.createElement('h5');
        let populationContainer = document.createElement('h5');
        
        nameContainer.setAttribute('id', 'name');
        rotationalPeriodContainer.setAttribute('id', 'rotationalPeriod');
        orbitalPeriod.setAttribute('id', 'orbitalPeriod');
        diameterContainer.setAttribute('id', 'diameter');
        climateContainer.setAttribute('id', 'climate');
        terrainContainer.setAttribute('id', 'terrain');
        populationContainer.setAttribute('id', 'population');
        
        resultsContainer.appendChild(nameContainer);
        resultsContainer.appendChild(rotationalPeriodContainer);
        resultsContainer.appendChild(orbitalPeriod);
        resultsContainer.appendChild(diameterContainer);
        resultsContainer.appendChild(climateContainer);
        resultsContainer.appendChild(terrainContainer);
        resultsContainer.appendChild(populationContainer);
        
    } else if (category == 'starships') {
        let nameContainer = document.createElement('h3');
        let modelContainer = document.createElement('h5');
        let manufacturerContainer = document.createElement('h5');
        let crewSizeContainer = document.createElement('h5');
        let passengerCapacityContainer = document.createElement('h5');
        let starshipClassContainer = document.createElement('h5');
        let hyperdriverRatingContainer = document.createElement('h5');
        
        nameContainer.setAttribute('id', 'name');
        modelContainer.setAttribute('id', 'model');
        manufacturerContainer.setAttribute('id', 'manufacturer');
        crewSizeContainer.setAttribute('id', 'crewSize');
        passengerCapacityContainer.setAttribute('id', 'passengerCapacity');
        starshipClassContainer.setAttribute('id', 'starshipClass');
        hyperdriverRatingContainer.setAttribute('id', 'hyperdriveRating');
        
        resultsContainer.appendChild(nameContainer);
        resultsContainer.appendChild(modelContainer);
        resultsContainer.appendChild(manufacturerContainer);
        resultsContainer.appendChild(crewSizeContainer);
        resultsContainer.appendChild(passengerCapacityContainer);
        resultsContainer.appendChild(starshipClassContainer);
        resultsContainer.appendChild(hyperdriverRatingContainer);
        
    }
}

const swForm = document.getElementById('sw-form');
const idField = document.getElementById('id');
const selectField = document.getElementById('category');
const submitBtn = document.getElementById('submitBtn');
const resultsContainer = document.getElementById('resultsContainer');
const alertMessage = document.getElementById('alertMessage');
