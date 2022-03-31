function setDaysRadiosDisabled(disabled) {
    let daysRadios = document.getElementsByName("days");
    let disabledBoolean = Boolean(disabled);
    for (let i = 0; i < daysRadios.length; i++) {
        if (disabledBoolean) {
            daysRadios[i].disabled = true;
        } else {
            daysRadios[i].removeAttribute('disabled');
        }
    }
}