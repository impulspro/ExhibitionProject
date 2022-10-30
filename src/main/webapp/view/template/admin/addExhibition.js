const dateInput = document.getElementById('startDateId');
const dateInput2 = document.getElementById('endDateId');

// ✅ Using the visitor's timezone
dateInput.value = formatDate();
dateInput2.value = formatDate();

console.log(formatDate());

function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(date = new Date()) {
    return [
        date.getFullYear(),
        padTo2Digits(date.getMonth() + 1),
        padTo2Digits(date.getDate()),
    ].join('-');
}


function validator() {
    const theme = document.getElementById("themeId").value;
    const details = document.getElementById("detailsId").value;
    const startDate = document.getElementById("startDateId").value;
    const endDate = document.getElementById("endDateId").value;
    const startTime = document.getElementById("startTimeId").value;
    const endTime = document.getElementById("endTimeId").value;
    const price = document.getElementById("priceId").value;
    const erTheme = "<fmt:message key='addExhibition.form.errorTheme'/>";
    const erDetails = "<fmt:message key='addExhibition.form.errorDetails'/>";
    const erDate = "<fmt:message key='addExhibition.form.errorDate'/>";
    const erTime = "<fmt:message key='addExhibition.form.errorTime'/>";
    const erDatePast = "<fmt:message key='addExhibition.form.errorDatePast'/>";
    const today = formatDate();
    const erPrice = "<fmt:message key='addExhibition.form.errorPrice'/>";

    if (theme.length < 5 || theme.length > 255) {
        alert(erTheme);
        return false;
    }
    if (details.length < 5 || details.length > 1024) {
        alert(erDetails);
        return false;
    }
    if (startDate > endDate) {
        alert(erDate);
        return false;
    }

    if (startTime > endTime) {
        alert(erTime);
        return false;
    }
    if (startDate < today || endDate < today) {
        alert(erDatePast);
        return false;
    }
    if (price == null || price < 0 || price > 1000) {
        alert(erPrice);
        return false;
    }
}
