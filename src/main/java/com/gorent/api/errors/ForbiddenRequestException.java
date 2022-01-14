package com.gorent.api.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ForbiddenRequestException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ForbiddenRequestException() {
        super(ErrorConstants.DEFAULT_TYPE, "Access Denied", Status.FORBIDDEN);
    }

    public ForbiddenRequestException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.FORBIDDEN);
    }
}
