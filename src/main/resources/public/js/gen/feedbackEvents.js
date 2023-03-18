export async function viewFeedbackForm(lightMode, response) {
    const template = document.getElementById('feedback-template').innerText;
    const compiledFunction = Handlebars.compile(template);

    let formClassName = "";
    if (lightMode === true) {
        formClassName = "form-light";
    }

    const templateData = {
        formClassName: formClassName,
        response: response
    };

    document.getElementById('app').innerHTML = compiledFunction(templateData);

    document.getElementById('feedback-form').addEventListener('submit', async (event) => {
        event.preventDefault();
        const formSubmissionData = new FormData(event.target);
        
        const feedbackType = formSubmissionData.get("feedback-type");
        const feedbackMessage = formSubmissionData.get("feedback-text");
        const feedbackEmail = formSubmissionData.get("feedback-email");

        const feedbackData = {
            "id": feedbackEmail + "-" + feedbackType + "-" + new Date().getTime().toString(),
            "timestamp": new Date().getTime().toString(),
            "message": feedbackMessage,
        };

        response = await fetch(`/${feedbackType}`, {method:'POST', body: JSON.stringify(feedbackData)})
            .then(response => response.text())
            .then(data => {
                return data;
            });

        viewFeedbackForm(lightMode, response);
    });
}