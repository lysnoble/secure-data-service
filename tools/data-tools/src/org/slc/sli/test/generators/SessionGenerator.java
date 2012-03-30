package org.slc.sli.test.generators;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.slc.sli.test.edfi.entities.EducationalOrgIdentityType;
import org.slc.sli.test.edfi.entities.EducationalOrgReferenceType;
import org.slc.sli.test.edfi.entities.GradingPeriodIdentityType;
import org.slc.sli.test.edfi.entities.GradingPeriodReferenceType;
import org.slc.sli.test.edfi.entities.GradingPeriodType;
import org.slc.sli.test.edfi.entities.Session;
import org.slc.sli.test.edfi.entities.TermType;

public class SessionGenerator {

	private Calendar beginDate = null;
	private Calendar endDate = null;
	Random generator = new Random();

	public Session sessionGenerator(List<String> stateOrgIds) {
		Session session = new Session();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

		session.setSessionName("2012 Spring");

		Calendar calendar = Calendar.getInstance();
		String schoolYear = Integer.toString(calendar.get(Calendar.YEAR));
		session.setSchoolYear(schoolYear);

		beginDate = GregorianCalendar.getInstance();
		beginDate.roll(Calendar.YEAR, -1);
		endDate = GregorianCalendar.getInstance();
		endDate.roll(Calendar.YEAR, 1);
		session.setBeginDate(beginDate);
		session.setEndDate(endDate);
		session.setTerm(getTermType());
		int roll = 45 + (int) (Math.random() * (150 - 45));
		session.setTotalInstructionalDays(roll);

		EducationalOrgIdentityType eoit = new EducationalOrgIdentityType();
		for (String stateOrgId : stateOrgIds)
			eoit.getStateOrganizationIdOrEducationOrgIdentificationCode().add(
					stateOrgId);
		EducationalOrgReferenceType eort = new EducationalOrgReferenceType();
		eort.setEducationalOrgIdentity(eoit);
		session.setEducationOrganizationReference(eort);

		GradingPeriodIdentityType gpit = new GradingPeriodIdentityType();
		gpit.setGradingPeriod(getGradingPeriodType());
		gpit.setSchoolYear("2010-2012");
		// System.out.println("this is grading period Type :" +
		// gpit.getGradingPeriod());
		// System.out.println("this is school year Type :" +
		// gpit.getSchoolYear());
		for (String stateOrgId : stateOrgIds)
			gpit.getStateOrganizationIdOrEducationOrgIdentificationCode().add(
					stateOrgId);
		// System.out.println("this is state org Id :" +
		// gpit.getStateOrganizationIdOrEducationOrgIdentificationCode().get(0)
		// );
		// System.out.println("this is state org Id :" +
		// gpit.getStateOrganizationIdOrEducationOrgIdentificationCode().get(1)
		// );
		GradingPeriodReferenceType gprt = new GradingPeriodReferenceType();

		gprt.setGradingPeriodIdentity(gpit);

		session.getGradingPeriodReference().add(gprt);
		// System.out.println("This is state org id by gradingPeriodReference: "
		// +
		// session.getGradingPeriodReference().get(0).getGradingPeriodIdentity().getStateOrganizationIdOrEducationOrgIdentificationCode().get(0));
		// System.out.println("This is state org id by gradingPeriodReference : "
		// +
		// session.getGradingPeriodReference().get(0).getGradingPeriodIdentity().getStateOrganizationIdOrEducationOrgIdentificationCode().get(1));

		return session;
	}

	public GradingPeriodType getGradingPeriodType() {
		int roll = generator.nextInt(20) + 1;
		switch (roll) {
		case 1:
			return GradingPeriodType.END_OF_YEAR;
		case 2:
			return GradingPeriodType.FIFTH_SIX_WEEKS;
		case 3:
			return GradingPeriodType.FIRST_NINE_WEEKS;
		case 4:
			return GradingPeriodType.FIRST_SEMESTER;
		case 5:
			return GradingPeriodType.FIRST_SIX_WEEKS;
		case 6:
			return GradingPeriodType.FIRST_SUMMER_SESSION;
		case 7:
			return GradingPeriodType.FIRST_TRIMESTER;
		case 8:
			return GradingPeriodType.FOURTH_NINE_WEEKS;
		case 9:
			return GradingPeriodType.FOURTH_SIX_WEEKS;
		case 10:
			return GradingPeriodType.SECOND_NINE_WEEKS;
		case 11:
			return GradingPeriodType.SECOND_SEMESTER;
		case 12:
			return GradingPeriodType.SECOND_SIX_WEEKS;
		case 13:
			return GradingPeriodType.SECOND_SUMMER_SESSION;
		case 14:
			return GradingPeriodType.SECOND_TRIMESTER;
		case 15:
			return GradingPeriodType.SIXTH_SIX_WEEKS;
		case 16:
			return GradingPeriodType.SUMMER_SEMESTER;
		case 17:
			return GradingPeriodType.THIRD_NINE_WEEKS;
		case 18:
			return GradingPeriodType.THIRD_SIX_WEEKS;
		case 19:
			return GradingPeriodType.THIRD_SUMMER_SESSION;
		default:
			return GradingPeriodType.THIRD_TRIMESTER;
		}
	}

	public TermType getTermType() {
		int roll = generator.nextInt(8) + 1;
		switch (roll) {
		case 1:
			return TermType.FALL_SEMESTER;
		case 2:
			return TermType.FIRST_TRIMESTER;
		case 3:
			return TermType.MINI_TERM;
		case 4:
			return TermType.SECOND_TRIMESTER;
		case 5:
			return TermType.SPRING_SEMESTER;
		case 6:
			return TermType.SUMMER_SEMESTER;
		case 7:
			return TermType.THIRD_TRIMESTER;
		default:
			return TermType.YEAR_ROUND;
		}
	}

	public static void main(String args[]) {

		SessionGenerator sg = new SessionGenerator();

		List<String> stateOrgIdss = new ArrayList();
		stateOrgIdss.add("100100100");
		stateOrgIdss.add("200200200");
		List<String> stateOrgIdss2 = new ArrayList();
		stateOrgIdss2.add("300100100");
		stateOrgIdss2.add("400200200");

		Session s = sg.sessionGenerator(stateOrgIdss);
		String sessionString = "\n\n" + " 1" + ".\n" + "sessionName : "
				+ s.getSessionName()
				+ ",\n"
				+ "schoolYear : "
				+ s.getSchoolYear()
				+ ",\n"
				+ "totalInstructionDays : "
				+ s.getTotalInstructionalDays()
				+ ",\n"
				+ "startDate : "
				+ s.getBeginDate().getTime()
				+ ",\n"
				+ "endDate : "
				+ s.getEndDate().getTime()
				+ ",\n"
				+ "term : "
				+ s.getTerm()
				+ ",\n"
				+ "getGradingPeriodReference : "
				+ s.getGradingPeriodReference().size()
				+ ",\n"
				+ "getEducationalOrgIdentity : "
				+ s.getEducationOrganizationReference()
						.getEducationalOrgIdentity()
						.getStateOrganizationIdOrEducationOrgIdentificationCode()
						.size();
		System.out.println(sessionString + ",\n");

		Session s1 = sg.sessionGenerator(stateOrgIdss2);

		String sessionString1 = "\n\n" + " 2" + ".\n" + "sessionName : "
				+ s1.getSessionName()
				+ ",\n"
				+ "schoolYear : "
				+ s1.getSchoolYear()
				+ ",\n"
				+ "totalInstructionDays : "
				+ s1.getTotalInstructionalDays()
				+ ",\n"
				+ "startDate : "
				+ s1.getBeginDate().getTime()
				+ ",\n"
				+ "endDate : "
				+ s1.getEndDate().getTime()
				+ ",\n"
				+ "term : "
				+ s1.getTerm()
				+ ",\n"
				+ "getGradingPeriodReference : "
				+ s1.getGradingPeriodReference().size()
				+ ",\n"
				+ "getEducationalOrgIdentity : "
				+ s1.getEducationOrganizationReference()
						.getEducationalOrgIdentity()
						.getStateOrganizationIdOrEducationOrgIdentificationCode()
						.size();
		System.out.println(sessionString1);

		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		System.out.println("  It is now : " + formatter.format(now.getTime()));

	}

}
