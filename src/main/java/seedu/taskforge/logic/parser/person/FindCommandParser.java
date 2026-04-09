package seedu.taskforge.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.logic.commands.person.FindCommand;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform with the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_TASK, PREFIX_PROJECT_TITLE);

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate();

        applyKeywords(argMultimap, PREFIX_NAME, predicate::setNameKeywords);
        applyKeywords(argMultimap, PREFIX_PHONE, predicate::setPhoneKeywords);
        applyKeywords(argMultimap, PREFIX_EMAIL, predicate::setEmailKeywords);
        applyKeywords(argMultimap, PREFIX_TASK, predicate::setTaskKeywords);
        applyKeywords(argMultimap, PREFIX_PROJECT_TITLE, predicate::setProjectKeywords);

        if (!predicate.isAnyFieldChecked() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

    private void applyKeywords(ArgumentMultimap argMultimap,
            seedu.taskforge.logic.parser.Prefix prefix,
            java.util.function.Consumer<List<String>> setter) throws ParseException {
        if (!argMultimap.getValue(prefix).isPresent()) {
            return;
        }
        List<String> keywords = splitKeywords(argMultimap.getAllValues(prefix));
        if (keywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        setter.accept(keywords);
    }

    private List<String> splitKeywords(List<String> values) {
        return values.stream()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
