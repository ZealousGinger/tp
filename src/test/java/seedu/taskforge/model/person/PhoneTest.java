package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("123")); // less than 4 characters
        assertFalse(Phone.isValidPhone("-1234")); // dash at start
        assertFalse(Phone.isValidPhone("1234-")); // dash at end
        assertFalse(Phone.isValidPhone("1234+")); // plus at end
        assertFalse(Phone.isValidPhone("++65234")); // multiple plus signs
        assertFalse(Phone.isValidPhone("1234   1234")); // multiple consecutive spaces
        assertFalse(Phone.isValidPhone("(Office) 1234 (Home) 912387123")); // label at start
        assertTrue(Phone.isValidPhone("1234 5678 HP")); // complex format

        // valid phone numbers - digits only
        assertTrue(Phone.isValidPhone("1234")); // exactly 4 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers

        // valid phone numbers - with formatting
        assertTrue(Phone.isValidPhone("+65 93767163")); // country code with spaces
        assertTrue(Phone.isValidPhone("1234 5678")); // spaces between numbers
        assertTrue(Phone.isValidPhone("1234 1234 1234")); // single spaces between different parts
        assertTrue(Phone.isValidPhone("1111-3333")); // hyphens between numbers
        assertTrue(Phone.isValidPhone("1234 5678 (HP)")); // spaces and label in parentheses
        assertTrue(Phone.isValidPhone("1234 5678 (HP) 1111-3333 (Office)")); // complex format
    }

    @Test
    public void equals() {
        Phone phone = new Phone("9999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("9999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("9995")));
    }
}
