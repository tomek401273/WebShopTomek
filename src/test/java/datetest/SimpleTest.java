package datetest;

import org.junit.Test;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SimpleTest {

    @Test
    public void justTest() {
        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        System.out.println("Current Date: " + ft.format(dNow));
    }

    @Test
    public void justTest2() {
        String dateS = "2018-03-21 15:42:54.0";
        System.out.println(dateS);
        int year;
        DateFormatter dateFormatter = new DateFormatter();


//        LocalDateTime localDateTime = LocalDateTime.parse(dateS, )
    }
}
