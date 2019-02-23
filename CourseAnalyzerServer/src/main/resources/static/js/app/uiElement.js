
function underLineSelectedLanguage(language) {
    if(language === 'en') {
        document.getElementById('enLink').style.textDecoration = "underline";
        document.getElementById('deLink').style.textDecoration = "none";
    } else {
         document.getElementById('deLink').style.textDecoration = "underline";
         document.getElementById('enLink').style.textDecoration = "none";
    }
}

window.onbeforeunload = function () {
   return 'The application is still running';
}

window.onunload = function () {
   return 'The application is still running';
}