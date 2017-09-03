package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.command.impl.admin.*;
import com.pronovich.hotelbooking.command.impl.client.AddRoomRequestCommand;
import com.pronovich.hotelbooking.command.impl.client.CancelOrderByClientCommand;
import com.pronovich.hotelbooking.command.impl.client.CancelRequestByClientCommand;
import com.pronovich.hotelbooking.command.impl.client.FindInfoForClientAccountCommand;
import com.pronovich.hotelbooking.command.impl.common.*;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.impl.AdminReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.ClientReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.CommonReceiverImpl;

/**
 * Enum class that contains all command types
 *
 * Each of the CommandType contains command instance which CommandType accepts in constructor
 * and getCommand() method which return the command instance.
 *
 * Each of the command implements doReceiver() method which accept RequestContent argument
 */
public enum CommandType {

    /**
     * User sign in command
     */
    SIGN_IN( new SignInCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ((CommonReceiverImpl) getCommand().getReceiver()).signIn(content);
        }
    },

    /**
     * User sign up command
     */
    SIGN_UP( new SignUpCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver() ).signUp(content);
        }
    },

    /**
     * Command for editing user personal information
     */
    EDIT_USER_INFO(new EditUserInfoCommand(new CommonReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver()).editUserInfo(content);
        }
    },

    /**
     * Command for changing user password
     */
    CHANGE_PASSWORD( new ChangePasswordCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver() ).changePassword(content);
        }
    },

    /**
     * User sign out command
     */
    SIGN_OUT( new SignOutCommand(null) ) {
        @Override
        public void doReceiver(RequestContent content) {
        }
    },

    /**
     * Command to add a new booking room request
     */
    ADD_ROOM_REQUEST(new AddRoomRequestCommand(new ClientReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).addRoomRequest(content);
        }
    },

    /**
     * Command for finding available rooms according client request
     */
    FIND_ROOMS_ACCORDING_REQUEST( new FindRoomCommand( new AdminReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).findAllRoomsAccordingRequest(content);
        }
    },

    /**
     * Command that finds all the necessary information for a client account
     */
    FIND_INFO_FOR_CLIENT_ACCOUNT(new FindInfoForClientAccountCommand(new ClientReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).findInfoForClientAccount(content);
        }
    },

    /**
     * Command that finds all the necessary information for a administrator account
     */
    FIND_INFO_FOR_ADMIN_ACCOUNT(new FindInfoForAdminAccountCommand( new AdminReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).findInfoForAdminAccount(content);
        }
    },

    /**
     * Command that creates a new order
     */
    CREATE_ORDER(new CreateOrderCommand(new AdminReceiverImpl() ) ){
        @Override
        public void doReceiver(RequestContent content) {
            ((AdminReceiverImpl) getCommand().getReceiver() ).createOrder(content);
        }
    },

    /**
     * Command that finds descriptions of all room types of the hotel
     */
    FIND_ROOMS_DESCRIPTION(new FindRoomsDescriptionCommand(new CommonReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            (  (CommonReceiverImpl) getCommand().getReceiver() ).findRoomsDescription(content);
        }
    },

    /**
     * Command that changes locale
     */
    CHANGE_LOCALE(new ChangeLocaleCommand(null)) {
        @Override
        public void doReceiver(RequestContent content) {
        }
    },

    /**
     * Client execute Command that cancels booking request
     */
    CANCEL_REQUEST_BY_CLIENT(new CancelRequestByClientCommand(new ClientReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).cancelRequestByClient(content);
        }
    },

    /**
     * Client execute Command that cancels the order
     */
    CANCEL_ORDER_BY_CLIENT(new CancelOrderByClientCommand(new ClientReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).cancelOrderByClient(content);
        }
    },

    /**
     * Administrator execute Command that cancels one of the client's booking request
     */
    CANCEL_REQUEST_BY_ADMIN(new CancelRequestByAdminCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).cancelRequestByAdmin(content);
        }
    },

    /**
     * Administrator execute Command that cancels one of the client's booking order
     */
    CANCEL_ORDER_BY_ADMIN(new CancelOrderByAdminCommand(new AdminReceiverImpl())){
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver()).cancelOrderByAdmin(content);
        }
    },

    /**
     * Command that changes order status to paid
     */
    CHANGE_ORDER_STATUS_TO_PAID(new ChangeOrderStatusToPaidCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).changeOrderStatusToPaid(content);
        }
    },

    /**
     * Command that changes order status to checked-in
     */
    CHANGE_ORDER_STATUS_TO_CHECKED_IN(new ChangeOrderStatusToCheckedInCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).changeOrderStatusToCheckedIn(content);
        }
    },

    /**
     * Command that changes order status to checked-out
     */
    CHANGE_ORDER_STATUS_TO_CHECKED_OUT(new ChangeOrderStatusToCheckedOutCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).changeOrderStatusToCheckedOut(content);
        }
    },

    /**
     * Command that issues and prints an invoice
     */
    ISSUE_INVOICE(new IssueInvoiceCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).issueInvoice(content);
        }
    };

    /**
     * field with AbstractCommand in each Command
     */
    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    /**
     * The method gives AbstractCommand instance from each CommandType
     * @return AbstractCommand instance
     */
    public AbstractCommand getCommand() {
        return command;
    }

    /**
     * Each of the command implements doReceiver() method which accept RequestContent argument
     * @param content RequestContent object which contains all information necessary for execute the command
     */
    public abstract void doReceiver(RequestContent content);
}
