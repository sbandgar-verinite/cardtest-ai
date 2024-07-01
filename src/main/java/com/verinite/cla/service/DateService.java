package com.verinite.cla.service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;

import org.springframework.stereotype.Service;


@Service
public class DateService {

	public int numberOfDays(Date fromDate, Date toDate) {
		LocalDate fromLocalDate = fromDate.toLocalDate();
		LocalDate toLocalDate = toDate.toLocalDate();
		int days = (int) Duration.between(fromLocalDate.atStartOfDay(), toLocalDate.atStartOfDay()).toDays();
		
		return days;
	}
}
