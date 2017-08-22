package com.pronovich.hotelbooking.validator;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;

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

    public static Map<String, String> addRoomRequestValidate(RequestContent content) {
        String checkInRequest = content.getRequestParameters().get(CHECK_IN_REQUEST_PARAM);
        String checkOutRequest = content.getRequestParameters().get(CHECK_OUT_REQUEST_PARAM);
        String roomSizeRequest = content.getRequestParameters().get(ROOM_SIZE_REQUEST_PARAM);
        String roomTypeRequest = content.getRequestParameters().get(ROOM_TYPE_REQUEST_PARAM);
        User user = (User) content.getSessionAttributes().get(USER_PARAM);

        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, Locale.getDefault());

        Map<String, String> wrongRequestValues = new HashMap<>();

        if (checkInRequest.isEmpty()) {
            wrongRequestValues.put(CHECK_IN_REQUEST_PARAM, resourceBundle.getString("check-in-empty"));
        }
        if (checkOutRequest.isEmpty()) {
            wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, resourceBundle.getString("check-out-empty"));
        }
        if ( !checkInRequest.isEmpty() && !checkOutRequest.isEmpty() ) {
            LocalDate checkInRequestDate = LocalDate.parse(checkInRequest);
            LocalDate checkOutRequestDate = LocalDate.parse(checkOutRequest);

            if (checkInRequestDate.isBefore(LocalDate.now())) {
                wrongRequestValues.put(CHECK_IN_REQUEST_PARAM, resourceBundle.getString("check-in-before-today"));
            }
            if ( !checkInRequestDate.isBefore(checkOutRequestDate)) {
                wrongRequestValues.put(CHECK_OUT_REQUEST_PARAM, resourceBundle.getString("check-out-before-check-in"));
            }
        }

        if (roomSizeRequest.isEmpty()) {
            wrongRequestValues.put(ROOM_SIZE_REQUEST_PARAM, resourceBundle.getString("room-size-empty"));
        }
        if (roomTypeRequest.isEmpty()) {
            wrongRequestValues.put(ROOM_TYPE_REQUEST_PARAM, resourceBundle.getString("room-type-empty"));
        }
        if (user == null) {
            wrongRequestValues.put(USER_PARAM, resourceBundle.getString("user-unauthorized"));
        }
        return wrongRequestValues;
    }
}
