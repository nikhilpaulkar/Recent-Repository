package com.app.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CalculateExpUtil {

	public String getExp(Date startDate, Date endDate) {

		LocalDate date1 = LocalDate.parse(startDate.toString().substring(0, 10));
		LocalDate date2 = LocalDate.parse(endDate.toString().substring(0, 10));
		Period period = date1.until(date2);
		float yearsBetween = period.getYears() + ((float) period.getMonths() / 12);
		return String.format("%.1f", yearsBetween) + " Year";

	}

	public static String getEmpExp(Date careerStartDay) {
		if (careerStartDay == null)
			return "NA";
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.parse(careerStartDay.toString().substring(0, 10));
		Period period = date2.until(date1);
		float yearsBetween = period.getYears() + ((float) period.getMonths() / 12);
		return String.format("%.1f", yearsBetween) + " Year";

	}
	
	
	public static String getDurationByStartAndEndDate(Date startDate, Date endDate) {

		StringBuilder sb = new StringBuilder("");

		LocalDate localStartDate = convertToLocalDateViaInstant(startDate);

		LocalDate localEndDate = convertToLocalDateViaInstant(endDate);

		Period diff = Period.between(localStartDate, localEndDate);

		if (diff.getYears() > 0) {

			sb.append(diff.getYears() + " Years ");

		}

		sb.append(diff.getMonths() + " Months");

		return sb.toString();

	}

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {

		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	}

}
