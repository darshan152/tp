package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.order.OrderUuidContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 *  Finds and lists all orders in ReadyBakey whose predicate (name) contains any of the argument keywords.
 *  Keyword matching is case insensitive.
 */
public class FindOrderNameCommand extends FindOrderCommand {
    private final NameContainsKeywordsPredicate predicate;
    private final String attributeName = "name";

    public FindOrderNameCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> filteredList = model.getFilteredPersonList().filtered(predicate);
        String[] uuidKeywords = filteredList.stream().map(person->person.getUuid().toString()).toArray(String[]::new);
        model.updateFilteredOrderList(new OrderUuidContainsKeywordsPredicate(Arrays.asList(uuidKeywords)));
        return new CommandResult(
                String.format(Messages.MESSAGE_FIND_ORDERS_OVERVIEW, model.getFilteredOrderList().size(),
                        attributeName, predicate.getKeywordsString()),
                true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindOrderNameCommand // instanceof handles nulls
                && predicate.equals(((FindOrderNameCommand) other).predicate)); // state check
    }
}
