package com.pronovich.hotelbooking.validator;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ClientValidatorTest {

    private static final String CHECK_IN_REQUEST_PARAM = "checkInRequest";
    private static final String CHECK_OUT_REQUEST_PARAM = "checkOutRequest";
    private static final String ROOM_SIZE_PARAM = "roomSizeRequest";
    private static final String ROOM_TYPE_PARAM = "roomTypeRequest";
    private static final String USER_PARAM = "user";

    private static final String EMPTY_STRING = "";
    private static final String NOT_EMPTY_STRING = "string";

    @Test
    public void addRoomRequestValidateWithEmptyValuesTest() {
        HashMap<String, String> requestValues = new HashMap<>();

        requestValues.put(CHECK_IN_REQUEST_PARAM, EMPTY_STRING);
        requestValues.put(CHECK_OUT_REQUEST_PARAM, EMPTY_STRING);
        requestValues.put(ROOM_SIZE_PARAM, EMPTY_STRING);
        requestValues.put(ROOM_TYPE_PARAM, EMPTY_STRING);

        RequestContent content= new RequestContent(requestValues);
        content.addSessionAttribute(USER_PARAM, null);

        Map<String, String> validationResult =  ClientValidator.addRoomRequestValidate(content);
        assertEquals(5, validationResult.size());
    }

    @Test
    public void addRoomRequestValidateWithNullValuesTest() {
        HashMap<String, String> requestValues = new HashMap<>();
        RequestContent content= new RequestContent(requestValues);

        Map<String, String> validationResult =  ClientValidator.addRoomRequestValidate(content);
        assertEquals(5, validationResult.size());
    }

    @Test
    public void addRoomRequestValidateWithCorrectValuesTest() {
        HashMap<String, String> requestValues = new HashMap<>();

        requestValues.put(CHECK_IN_REQUEST_PARAM, "2017-09-07");
        requestValues.put(CHECK_OUT_REQUEST_PARAM, "2017-09-10");
        requestValues.put(ROOM_SIZE_PARAM, "1x");
        requestValues.put(ROOM_TYPE_PARAM, "standard");
        requestValues.put(ROOM_TYPE_PARAM, NOT_EMPTY_STRING);

        RequestContent content= new RequestContent(requestValues);
        content.addSessionAttribute(USER_PARAM, null);

        Map<String, String> validationResult =  ClientValidator.addRoomRequestValidate(content);
        assertEquals(2, validationResult.size());
    }
}