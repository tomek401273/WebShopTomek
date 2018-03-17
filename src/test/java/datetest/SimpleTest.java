package datetest;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleTest {

    @Test
    public void justTest() {
        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        System.out.println("Current Date: " + ft.format(dNow));
    }
}
