package com.verinite.cla.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateService {

	public int numberOfDays(Date fromDate, Date toDate) {
		int days = (int) Duration.between(fromDate.toInstant(), toDate.toInstant()).toDays();
		return days;
	}
}
