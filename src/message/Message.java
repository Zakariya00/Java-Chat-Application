package message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for encapsulating and manipulating a String message.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 * @version 1.0 3/2/2022
 */
public class Message implements Serializable {

    private final String message;

    /**
     * class constructor takes a String
     * Prepends the current time to the string and stores it.
     *
     * @param message given String
     */
    public Message(String message) {
        String time;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        time = "<" + dtf.format(now) + "> ";
        this.message = time + message;
    }

    /**
     * Returns the encapsulated String message
     *
     * @return String object message
     */
    public String toString() {
        return message;
    }

}
