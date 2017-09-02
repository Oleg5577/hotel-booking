package com.pronovich.hotelbooking.validator;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ClientValidator {

    private static final String BUNDLE = "property/wrongValues";

    private static final String USER_PARAM = "user";
    private static final String CHECK_IN_REQUEST_PARAM = "checkInRequest";
    private static final String CHECK_OUT_REQUEST_PARAM = "checkOutRequest";
    private static final String ROOM_SIZE_REQUEST_PARAM = "roomSizeRequest";
    private static final String ROOM_TYPE_REQUEST_PARAM = "roomTypeRequest";

    private static final String CHECK_IN_EMPTY = "check-in-empty";
    private static final String CHECK_OUT_EMPTY = "check-out-empty";
    private static final String CHECK_IN_BEFORE_TODAY = "check-in-before-today";
    private static final String CHECK_OUT_BEFORE_CHECK_IN = "check-out-before-check-in";
    private static final String ROOM_SIZE_EMPTY = "room-size-empty";
    private static final String ROOM_SIZE_NOT_INTEGER = "room-size-not-integer";
    private static final String ROOM_TYPE_EMPTY = "room-type-empty";
    private static final String USER_UNAUTHORIZED = "user-unauthorized";

    public static Map<String, String> addRoomRequestValidate(RequestContent content) {
        String checkInRequest = content.getRequestParameters().get(CHECK_IN_REQUEST_PARAM);
        String checkOutRequest = content.getRequestParameters().get(CHECK_OUT_REQUEST_PARAM);
        String roomSizeRequest = content.getRequestParameters().get(ROOM_SIZE_REQUEST_PARAM);
        String roomTypeRequest = content.getRequestParameters().get(ROOM_TYPE_REQUEST_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        Map<String, String> wrongRequestValues = new HashMap<>();

        if (checkInRequest == null || checkInRequest.isEmpty()) {
            wrongRequestValues.put(CHECK_IN_REQUEST_PARAM, resourceBundle.getString(CHECK_IN_EMPTY));
        }
        if (checkOutRequest == null || checkOutRequest.isEmpty()) {
            wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, resourceBundle.getString(CHECK_OUT_EMPTY));
        }
        if ( checkInRequest != null && !checkInRequest.isEmpty() && checkOutRequest != null && !checkOutRequest.isEmpty() ) {
            LocalDate checkInRequestDate = LocalDate.parse(checkInRequest);
            LocalDate checkOutRequestDate = LocalDate.parse(checkOutRequest);

            if (checkInRequestDate.isBefore(LocalDate.now())) {
                wrongRequestValues.put(CHECK_IN_REQUEST_PARAM, resourceBundle.getString(CHECK_IN_BEFORE_TODAY));
            }
            if ( !checkInRequestDate.isBefore(checkOutRequestDate)) {
                wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, resourceBundle.getString(CHECK_OUT_BEFORE_CHECK_IN));
            }
        }

        if (roomSizeRequest == null || roomSizeRequest.isEmpty()) {
            wrongRequestValues.put(ROOM_SIZE_REQUEST_PARAM, resourceBundle.getString(ROOM_SIZE_EMPTY));
        } else if ( !NumberUtils.isCreatable(roomSizeRequest) ) {
            wrongRequestValues.put(ROOM_SIZE_REQUEST_PARAM, resourceBundle.getString(ROOM_SIZE_NOT_INTEGER));
        }

        if (roomTypeRequest == null || roomTypeRequest.isEmpty()) {
            wrongRequestValues.put(ROOM_TYPE_REQUEST_PARAM, resourceBundle.getString(ROOM_TYPE_EMPTY));
        }
        if (user == null) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString(USER_UNAUTHORIZED));
        }
        return wrongRequestValues;
    }
}
