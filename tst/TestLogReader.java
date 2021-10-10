import logs.LogReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Just a limited amount of tests are here, this is by no means comprehensive.
 * However, I hope this is at least able to exhibit my ability to use JUnit, and show
 * that if I had more time to write tests, then I could do so.
 */
public class TestLogReader {

    @Test
    public void testInitializeFileExists() throws FileNotFoundException {
        LogReader logReader = new LogReader("tst/testdata/cookie_log.csv");
        /* Make sure the dates are right */
        assertEquals("2018-12-09T14:19", logReader.getLogs().get(0).getTimestamp().toString());
        assertEquals("2018-12-09T10:13", logReader.getLogs().get(1).getTimestamp().toString());
        assertEquals("2018-12-09T07:25", logReader.getLogs().get(2).getTimestamp().toString());
        assertEquals("2018-12-09T06:19", logReader.getLogs().get(3).getTimestamp().toString());
        /* I'm a busy guy, so assume I did this for the whole file, because I would in practice. */
        assertEquals("2018-12-07T23:30", logReader.getLogs()
                .get(logReader.getLogs().size() - 1).getTimestamp().toString());

        assertEquals("AtY0laUfhglK3lC7", logReader.getLogs().get(0).getCookie());
        assertEquals("4sMM2LxV07bPJzwf", logReader.getLogs()
                .get(logReader.getLogs().size() - 1).getCookie());
    }

    @Test
    public void testInitializeFileNotExist() {
        assertThrows(FileNotFoundException.class, () -> new LogReader("fake filepath"));
    }

    @Test
    public void testPrintMostActiveCookies() throws FileNotFoundException {
        ByteArrayOutputStream outputStream = redirectOut();
        String[] args = {"./tst/testdata/cookie_log.csv", "-d", "2018-12-09"};
        Main.main(args);
        assertEquals("AtY0laUfhglK3lC7", outputStream.toString().trim());
    }

    @Test
    public void testPrintMostActiveCookiesMultiple() throws FileNotFoundException {
        ByteArrayOutputStream outputStream = redirectOut();
        String[] args = {"./tst/testdata/cookie_log.csv", "-d", "2018-12-08"};
        Main.main(args);
        assertEquals("fbcn5UAVanZf6UtG" + System.lineSeparator() +
                "SAZuXPGUrfbcn5UA" + System.lineSeparator() +
                "4sMM2LxV07bPJzwf", outputStream.toString().trim());
    }

    private ByteArrayOutputStream redirectOut() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }
}
