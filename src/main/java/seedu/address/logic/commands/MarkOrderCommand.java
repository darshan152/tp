package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Complete;
import seedu.address.model.order.Details;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class MarkOrderCommand extends Command {

    public static final String COMMAND_WORD = "marko";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the order identified by the index number used in the displayed order list as complete.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ORDER_SUCCESS = "Completed Order: %1$s";

    private final Index targetIndex;

    public MarkOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMark = lastShownList.get(targetIndex.getZeroBased());
        Order editedOrder = createMarkedOrder(orderToMark);

        model.setOrder(orderToMark, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_MARK_ORDER_SUCCESS, editedOrder), true, false);
    }

    private Order createMarkedOrder(Order orderToMark) {
        assert orderToMark != null;

        Name updatedName = orderToMark.getName();
        Phone updatedPhone = orderToMark.getPhone();
        Address updatedAddress = orderToMark.getAddress();
        Details updatedDetails = orderToMark.getDetails();
        Complete updatedComplete = new Complete(true);

        return new Order(updatedName, updatedPhone, updatedAddress, updatedDetails, updatedComplete);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkOrderCommand // instanceof handles nulls
                && targetIndex.equals(((MarkOrderCommand) other).targetIndex)); // state check
    }
}
