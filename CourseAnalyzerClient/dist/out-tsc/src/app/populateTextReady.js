$(document).ready(function () {
    var initLanguage = 'en';
    setLanguage(initLanguage);
    underLineSelectedLanguage(initLanguage);
});
$(document).ready(function () {
    getLanguage();
});
$(document).ready(function () {
    $('#instructionText').text(language.instructionText);
});
$(document).ready(function () {
    $('#studyPlanLabel').text(language.studyPlan);
});
$(document).ready(function () {
    $('#transitionalProvisionLabel').text(language.transitionalProvision);
});
$(document).ready(function () {
    $('#finishedCoursesLabel').text(language.certificateList);
});
$(document).ready(function () {
    $('#analyzeButton').text(language.analyze);
});
$(document).ready(function () {
    $('#optionalLabel').text(language.optional);
});
$(document).ready(function () {
    $('#resultTitle').text(language.result);
});
$(document).ready(function () {
    $('#typeCell').text(language.type);
});
$(document).ready(function () {
    $('#mandatoryCoursesEctsCell').text(language.mandatoryCoursesEcts);
});
$(document).ready(function () {
    $('#additionalMandatoryCoursesEctsCell').text(language.optionalMandatoryCoursesEcts);
});
$(document).ready(function () {
    $('#optionalModuleEctsCell').text(language.optionalModuleEcts);
});
$(document).ready(function () {
    $('#transferableSkillsEctsCell').text(language.transferableSkills);
});
$(document).ready(function () {
    $('#remainingMandatoryCoursesTitle').text(language.transferableSkills);
});
$(document).ready(function () {
    $('#transferableSkillsEctsCell').text(language.transferableSkills);
});
$(document).ready(function () {
    $('#remainingResultedMandatoryCoursesTitle').text(language.remainingMandatoryCourses);
});
$(document).ready(function () {
    $('#remainingUnassignedCoursesTitle').text(language.remainingUnassignedCourses);
});
$(document).ready(function () {
    $('.courseTypeCell').text(language.courseType);
});
$(document).ready(function () {
    $('.courseNameCell').text(language.courseName);
});
$(document).ready(function () {
    $('#hintLabel').text(language.hint);
});
$(document).ready(function () {
    $('#closeApplicationButton').text(language.closeApplication);
});
//# sourceMappingURL=populateTextReady.js.map