package message;

import java.io.Serializable;

/** Packet
 *       Class for encapsulation
 *       and serialization of a String message.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 *
 * @version 1.0
*/
public class Packet implements Serializable {

    /** Message as String.*/
    private String message;

    /** Constructor Takes a String
     * Creates a Packet with given String
     * @param message given String
     */
    public Packet(String message){
        this.message = message;
    }

    /** Returns the encapsulated String message
     * @return String object message
     */
    public String toString() {return message;}

}
