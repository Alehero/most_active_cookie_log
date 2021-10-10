import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import logs.LogReader;

public class Main {

    private static String DATE_FLAG = "-d";

    /**
     * Main function -- receives a log file and flags + args for requests
     * @param args -- args[0] is the log filepath, args[...] are for certain requests.
     *             Flags:
     *             -d [yyyy-mm-dd] for printing the most active cookies on a particular date.
     *             If multiple -d flags are used, only the first one will be interpreted.
     *             Except for args[0], if no values are preceded by a flag it will be ignored.
     * @throws FileNotFoundException if args[0] is not a valid file.
     */
    public static void main(String[] args) throws FileNotFoundException {
        assert args.length > 0;
        LogReader logReader = new LogReader(args[0]);
        for (int i = 1; i < args.length - 1; i++) {
            if (args[i].equals(DATE_FLAG)) {
                printMostActiveCookies(logReader, args[i + 1]);
            }
        }
    }

    /**
     * Print the most active cookies of a log on a certain specified day.
     * @param logReader -- a LogReader that contains all the Logs in a log file.
     * @param queryDate -- a date for which to track the active cookies.
     */
    public static void printMostActiveCookies(LogReader logReader, String queryDate) {
        queryDate = queryDate + "T00:00:00";
        List<String> mostActiveCookies = logReader.getMostActiveCookies(LocalDateTime.parse(queryDate));
        for (String s : mostActiveCookies) {
            System.out.println(s);
        }
    }
}
