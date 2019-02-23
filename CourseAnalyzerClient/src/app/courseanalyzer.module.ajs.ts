const MODULE_NAME = 'CourseAnalyzer';

export class CONSTANTS {
  public static get READ_STUDY_PLAN():string { return '/courseanalyzer/readStudyPlan';}
  public static get READ_TRANSITIONAL_PROVISION():string { return '/courseanalyzer/readTransitionalProvision';}
  public static get READ_FINISHED_COURSE_LIST():string { return '/courseanalyzer/readFinishedCourseList';}
  public static get COMPARE_COURSES():string { return '/courseanalyzer/compareCourses';}
  public static get CLOSES_APPLICATION():string { return '/courseanalyzer/closeApplication';}
}

export default MODULE_NAME;