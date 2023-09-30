import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeShiftAnalyzer {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("employee_shifts.csv"));
        String line;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime date time = LocalDateTime.now();
        String formattedTime = formatter.format(time);

        while ((line = reader.readLine()) != null) {
            String inputString = "Time";
            String[] fields = line.split(",");
            String name = fields[0];
            String position = fields[1];
            LocalDateTime shift1Start = LocalDateTime.parse(fields[2]);
            LocalDateTime shift1End = LocalDateTime.parse(fields[3]);
            LocalDateTime shift2Start = LocalDateTime.parse(fields[4]);
            LocalDateTime shift2End = LocalDateTime.parse(fields[5]);
            
            // Check if the employee has worked for 7 consecutive days
            Duration totalDuration = Duration.between(shift1Start, shift2End).plus(Duration.between(shift1End, shift2Start));
            if (totalDuration.toDays() == 7) {
                System.out.println("Employee who has worked for 7 consecutive days: " + name + ", " + position);
                System.out.println(formattedTime);
            }

            // Check if the employee has less than 10 hours of time between shifts but greater than 1 hour
            Duration timeBetweenShifts = Duration.between(shift1End, shift2Start);
            if (timeBetweenShifts.toHours() < 10 && timeBetweenShifts.toHours() > 1) {
                System.out.println("Employee who has less than 10 hours of time between shifts but greater than 1 hour: " + name + ", " + position);
            }

            // Check if the employee has worked for more than 14 hours in a single shift
            Duration shift1Duration = Duration.between(shift1Start, shift1End);
            if (shift1Duration.toHours() > 14) {
                System.out.println("Employee who has worked for more than 14 hours in a single shift: " + name + ", " + position);
            }
        }
        reader.close();
    }
}