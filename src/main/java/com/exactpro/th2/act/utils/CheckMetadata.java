package com.exactpro.th2.act.utils;

import com.exactpro.th2.common.event.Event;
import com.exactpro.th2.common.grpc.RequestStatus;

import static com.exactpro.th2.common.event.Event.Status.FAILED;
import static com.exactpro.th2.common.event.Event.Status.PASSED;
import static com.exactpro.th2.common.grpc.RequestStatus.Status.ERROR;
import static com.exactpro.th2.common.grpc.RequestStatus.Status.SUCCESS;
import static java.util.Objects.requireNonNull;

public class CheckMetadata {
    private final Event.Status eventStatus;
    private final RequestStatus.Status requestStatus;
    private final String fieldName;

    private CheckMetadata(String fieldName, Event.Status eventStatus) {
        this.eventStatus = requireNonNull(eventStatus, "Event status can't be null");
        this.fieldName = requireNonNull(fieldName, "Field name can't be null");

        switch (eventStatus) {
            case PASSED:
                requestStatus = SUCCESS;
                break;
            case FAILED:
                requestStatus = ERROR;
                break;
            default:
                throw new IllegalArgumentException("Event status '" + eventStatus + "' can't be convert to request status");
        }
    }

    public static CheckMetadata passOn(String fieldName) {
        return new CheckMetadata(fieldName, PASSED);
    }

    public static CheckMetadata failOn(String fieldName) {
        return new CheckMetadata(fieldName, FAILED);
    }

    public Event.Status getEventStatus() {
        return eventStatus;
    }

    public RequestStatus.Status getRequestStatus() {
        return requestStatus;
    }

    public String getFieldName() {
        return fieldName;
    }
}