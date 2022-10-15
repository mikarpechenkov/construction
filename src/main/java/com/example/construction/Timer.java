import java.time.LocalDateTime;
import java.time.LocalTime;

public class Timer extends java.util.Timer {

    private LocalDateTime currentTime;
    public void run(){}
    public void pause(){}
    public void addTime(LocalTime additionTime){}

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
