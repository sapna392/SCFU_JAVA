package onb.com.scf.datetimeutility;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

public class DateTimeUtility {

	private DateTimeUtility() {
	}

	/**
	 * Current date of system
	 *
	 * @return - system current date
	 */
	public static Date getCurrentDate() {
		return Date.valueOf(LocalDate.now());
	}

	/**
	 * Current timestamp of system
	 *
	 * @return - system current timestamp
	 */
	public static Timestamp getCurrentTimeStamp() {
		return Timestamp.from(Instant.now());
	}

}
