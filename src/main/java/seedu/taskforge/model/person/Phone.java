package seedu.taskforge.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the TaskForge.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers can contain digits, spaces, hyphens (between numbers), parentheses, plus signs, "
            + "and letters (for labels). It should be at least 4 characters long. "
            + "Only one optional leading plus sign is allowed. Spaces must be single spaces (no consecutive spaces). "
            + "Dashes must be between numbers, not at the start or end. "
            + "Examples: +65 93767163, 1234 5678 (HP) 1111-3333 (Office)";
    public static final String VALIDATION_REGEX =
            "^(?=.{4,}$)(?!.*\\s{2,})\\+?\\d[\\d()a-zA-Z\\- ]*[\\d)a-zA-Z]$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
