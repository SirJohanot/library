function setDaysRadiosDisabled(disabled) {
    let daysRadios = document.getElementsByName("days");
    for (let i = 0; i < daysRadios.length; i++) {
        daysRadios[i].disabled = Boolean(disabled);
    }
}