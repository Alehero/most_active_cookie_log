package logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * A logs.LogReader class reads over all of the lines in a log file, and parses them into Log objects containing
 * their respective cookies and timestamps corresponding to their entries in the log file.
 * Assumes the logs input are in valid formatting.
 */
public class LogReader {
    private String logPath;
    List<Log> logs = new ArrayList<>();

    /**
     * Constructor for a logs.LogReader.
     * @param logPath -- File path for the logs.LogReader to read over.
     *                If needed, can implement this later to have a String List as an input,
     *                if need to read over multiple logs.
     * @throws FileNotFoundException If a file path is invalid.
     */
    public LogReader(String logPath) throws FileNotFoundException {
        this.logPath = logPath;
        populateLogs();
    }

    /**
     * Function for populating the log list of the logs.LogReader. Called in the constructor.
     * If needed, can implement this to lazily compute the logs if other commands are added.
     * But for now will precompute.
     */
    public void populateLogs() throws FileNotFoundException {
        File logFile = new File(logPath);
        Scanner scanner = new Scanner(logFile);
        while (scanner.hasNext()) {
            scanner.useDelimiter(",");
            String cookie = scanner.next();
            scanner.skip(",");
            scanner.useDelimiter("\\+");
            String dayAndTime = scanner.next();
            LocalDateTime timeStamp = LocalDateTime.parse(dayAndTime);
            scanner.useDelimiter(":");
            int hoursToAdvance = Integer.parseInt(scanner.next());
            scanner.skip(":");
            int minutesToAdvance = Integer.parseInt(scanner.nextLine());
            timeStamp = timeStamp.plusHours(hoursToAdvance).plusMinutes(minutesToAdvance);
            Log log = new Log(cookie, timeStamp);
            logs.add(log);
        }
        scanner.close();
    }

    /**
     * Get the most active cookies of a certain specified date.
     * @param queryDate -- the date to get the most active cookies from.
     * @return -- a list of the most active cookies on that day.
     */
    public List<String> getMostActiveCookies(LocalDateTime queryDate) {
        HashMap<String, Integer> cookieToCount = new HashMap<>();
        int highestCount = 0;

        /* Populate cookieToCount with cookies with logs of the same date as queryDate */
        for (Log log : logs) {
            String cookie = log.getCookie();
            LocalDateTime timestamp = log.getTimestamp();

            if (timestamp.getDayOfYear() == queryDate.getDayOfYear()
                    && timestamp.getMonthValue() == queryDate.getMonthValue()
                    && timestamp.getYear() == queryDate.getYear()) {
                int count = cookieToCount.getOrDefault(cookie, 0) + 1;
                cookieToCount.put(cookie, count);
                highestCount = Math.max(count, highestCount);
            }
        }
        List<String> mostUsedCookies = new ArrayList<>();
        for (String cookie : cookieToCount.keySet()) {
            if (cookieToCount.get(cookie) == highestCount) {
                mostUsedCookies.add(cookie);
            }
        }
        return mostUsedCookies;
    }

    public List<Log> getLogs() {
        return logs;
    }

    /**
     * A Log abstraction for each line in the log file provided. Assumes proper cookie and Timestamp format.
     */
    public class Log {

        /** The cookie this log contains. */
        private String cookie;

        /** Timestamp for the cookie. */
        private LocalDateTime timestamp;

        /**
         * Constructor for Log.
         * @param cookie -- the cookie.
         * @param timestamp -- the timestamp.
         */
        public Log(String cookie, LocalDateTime timestamp) {
            this.cookie = cookie;
            this.timestamp = timestamp;
        }

        /**
         * Alternate constructor, where the timestamp is a string.
         * @param cookie -- the cookie.
         * @param timeStamp -- the timestamp.
         */
        public Log(String cookie, String timeStamp) {
            this(cookie, LocalDateTime.parse(timeStamp));
        }

        /** Would use lombok for these sections, not sure if you count them as external libraries. */

        /**
         * Retrieve a log's cookie.
         * @return -- cookie.
         */
        public String getCookie() {
            return cookie;
        }

        /**
         * Retrieve a log's timestamp.
         * @return -- timestamp.
         */
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
