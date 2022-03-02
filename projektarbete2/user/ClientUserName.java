package user;

import java.io.Serializable;

/** ClientUserName
 *       Class for encapsulation
 *       and serialization of a String username.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 *
 * @version 1.0
 */
public class ClientUserName implements Serializable {

    /** Username as String.*/
    public String userName;

    /** Constructor Takes a String
     * Initiates object instance variable with
     * given string.
     * @param user given String
     */
    public ClientUserName(String user){
        this.userName = user;
    }

    /** Returns the encapsulated String message
     * @return String representation of username.
     */
    public String toString() {return userName;}

}
