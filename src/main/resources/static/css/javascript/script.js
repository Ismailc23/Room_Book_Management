function dateValidation() {
    const errorMessageDiv = document.getElementById('error-message');
    errorMessageDiv.style.display = 'none';
    errorMessageDiv.innerText = '';

    const startDateInput = document.getElementById('stayStartDate').value;
    const endDateInput = document.getElementById('stayEndDate').value;

    const startDate = new Date(startDateInput);
    const endDate = new Date(endDateInput);
    const currentDate = new Date();


    if (!startDateInput || !endDateInput) {
        showError('Both start date and end date are required.');
        return;
    }

    if (startDate < currentDate.setHours(0, 0, 0, 0)) {
        showError('Stay start date cannot be in the past.');
        return;
    }

    if (endDate < startDate) {
        showError('Stay end date cannot be before start date.');
        return;
    }

    const daysBetween = (endDate - startDate) / (1000 * 60 * 60 * 24);
    if (daysBetween > 30) {
        showError('Maximum stay duration is 30 days.');
        return;
    }
}

function showError(message) {
    const errorMessageDiv = document.getElementById('error-message');
    errorMessageDiv.style.display = 'block';
    errorMessageDiv.innerText = message;
}
