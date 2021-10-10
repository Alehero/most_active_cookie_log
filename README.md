# Most Active Cookie
Given a cookie log file in the format of 

cookie,timestamp

i.e.

AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00

SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00

5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00

AtY0laUfhglK3lC7,2018-12-09T06:19:00+00:00

SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00

4sMM2LxV07bPJzwf,2018-12-08T21:30:00+00:00

fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00

4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00

This program will process the log file and return the most active cookie for a specified day.

---

Command:

From within most_active_cookie_log:

java -jar mos_active_cookie_log_jar ./src/test/java/testdata/cookie_log.csv -d 2018-12-09

Output:

AtY0laUfhglK3lC7

This will also print out multiple cookies, one for each line, if there are multiple that are just as active.
Included in the root folder is a sample .jar file that can be executed.

If this is to be worked on further, the build.gradle needs to be cleaned up as 'java' plugin is not meant to be used directly anymore.