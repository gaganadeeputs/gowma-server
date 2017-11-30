/* Copyright 2017 Mihy, org.
* All rights reserved.
 */

package org.mihy.gowma.exception;

import org.springframework.http.HttpStatus;

/**
 * Enumeration to hold message codes and their respective key to use against a resource bundle. Minor code ranges <br/>
 * <ol>
 * <li>Generic: 1-100 Tenant: 101+</li>
 * <li>User/Account: 201+</li>
 * <li>Role: 401+</li>
 * <li>Group: 501+</li>
 * <li>ContentSite: 601+</li>
 * <li>StorageDomain: 701+</li>
 * <li>Device: 801+</li>
 * <li>KeyMgr: 901+</li>
 * <li>Activities: 1001+</li>
 * <li>Events: 1101+</li>
 * <li>Logs: 1201+</li>
 * <li>Metadata: 1301+</li>
 * <li>Deployment: 1401+</li>
 * <li>Scheduler: 1501+</li>
 * <li>Activity: 1601+</li>
 * <li>Dashboard: 1701+</li>
 * <li>Sharing: 1801+</li>
 * <li>Policy: 1901+</li>
 * <li>SamlPair: 2000+</li>
 * <li>Rule:   2101+</li>
 * <li>Cert: 2201+</li>
 * <li>Profile: 2301+</li>
 * <li>Error Page: 2401+</li>
 * </ol>
 */
public enum GowmaServiceExceptionCode implements ExceptionCode {


    CFG_GENERIC_INVALID_ID("CFG-101", "CFG_GENERIC_INVALID_ID", "The request input is invalid. Reason: %s"),
    CFG_GENERIC_DUPLICATE_NAME("CFG-101", "CFG_GENERIC_DUPLICATE_NAME", "Name with %s already exists in the system"),
    CFG_GENERIC_DATA_ACCESS_EXCEPTION("CFG-102", "CFG_GENERIC_DATA_ACCESS_EXCEPTION", "Error updating database"),
    CFG_GENERIC_INVALID_INPUT("CFG-106", "CFG_GENERIC_INVALID_INPUT", "The request contains invalid input"),
    // 400 minor codes
    USER_DUPLICATE_EMAIL("USER-101", "USER.EMAIL.DUPLICATE", "User with Email %s already exists in the system"),
    USER_DUPLICATE_MOBILE_NO("USER-102", "USER.MOBILE.NUMBER.DUPLICATE", "User with mobile no %s already exists in the system");



    private final String code;
    private final String key;
    private final String message;
    private HttpStatus httpStatus;

    GowmaServiceExceptionCode(final String code, final String key, final String message) {
        this.code = code;
        this.key = key;
        this.message = message;

        // All non-400 minor codes above must call httpStatus constructor
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    GowmaServiceExceptionCode(final String code, final String key, final String message, final HttpStatus httpStatus) {
        this(code, key, message);
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }


    @Override
    public String getMessage() {
        return this.message;
    }


    @Override
    public String getKey() {
        return this.key;
    }


    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
