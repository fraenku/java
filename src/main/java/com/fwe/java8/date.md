#Date

In Java 1.0 the only support for date and time was the java.util.Date class.
Despite its name, this class doesn’t represent a date but a point in time with milliseconds
precision. Even worse, the usability of this class is harmed by some nebulous
design decisions like the choice of its offsets: the years start from 1900, whereas
the months start at index 0. This means that if you want to represent the release date
of Java 8, which is March 18, 2014, you have to create an instance of Date as follows:

``` Java
Date date = new Date(114, 2, 18);
```

Not very intuitive, is it? Moreover even the String returned by the toString method
of the Date class could be quite misleading. It also includes the JVM’s default time
zone, CET, which is Central Europe Time in our case. But this doesn’t mean the Date
class itself is in any way aware of the time zone!
The problems and limitations of the Date class were immediately clear when Java
1.0 came out, but it was also clear that it wasn’t fixable without breaking its backward
compatibility. As a consequence, in Java 1.1 many of the Date class's methods were
deprecated, and it was replaced with the alternative java.util.Calendar class. Unfortunately,
Calendar has similar problems and design flaws that lead to error-prone
code. For instance, months also start at index 0 (at least Calendar got rid of the 1900
offset for the year). Even worse, the presence of both the Date and Calendar classes
increases confusion among developers. Which one should you use? In addition, some
other features such as the DateFormat, used to format and parse dates or time in a language-
independent manner, work only with the Date class.
The DateFormat also comes with its own set of problems. For example, it isn’t
thread-safe. This means that if two threads try to parse a date using the same formatter
at the same time, you may receive unpredictable results.
Finally, both Date and Calendar are mutable classes. What does it mean to mutate
the 18th of March 2014 to the 18th of April? This design choice can lead you into a
maintenance nightmare, as you’ll learn in more detail in the next chapter, which is
about functional programming.

The consequence is that all these flaws and inconsistencies have encouraged the
use of third-party date and time libraries, such as Joda-Time. For these reasons Oracle
decided to provide high-quality date and time support in the native Java API. As a
result, Java 8 integrates many of the Joda-Time features in the java.time package.

``` Java
LocalDate date = LocalDate.of(2014, 3, 18);
LocalDate today = LocalDate.now();
LocalDate date = LocalDate.parse("2014-03-18")

LocalTime time = LocalTime.of(13, 45, 20);

LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);

Instant.ofEpochSecond(3); //number of seconds since Unix epoch time, 1.1 1970

Duration d = Duration.between(time1, time2) -> Diff in seconds

Period tenDays = Period.between(date1, date2)

LocalDate date1 = date2.plusWeek(1)

LocalDate date 20 date3.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth())

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

ZoneId.of("Europe/Rome")
```

The new Date-API works with immutable classes and are therefore thread-safe.



