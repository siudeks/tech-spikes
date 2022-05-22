import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        // var date = new Date(1650444630 * 1000);
        var date = new Date(1650444872 * 1000L);
        var localDT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var utcLD = date.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
        System.out.println(date.toString());
        System.out.println(localDT.toString());
        System.out.println(utcLD.toString());
    }
}
