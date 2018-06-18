/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ameher
 */
@Getter
@Setter
@ToString(callSuper = false)
public class IPBaseException extends Exception {

    private static final long serialVersionUID = 7105632584002779714L;

    private final transient List<String> causeOfException = new ArrayList<>();

    public IPBaseException() {
    }

    public IPBaseException(String msg) {
        super(msg);
        addCauseOfException(msg, null);
    }

    public IPBaseException(Throwable th) {
        super(th);
        addCauseOfException(th);
    }

    public IPBaseException(String msg, Throwable th) {
        super(msg, th);
        addCauseOfException(msg, th);
    }

    private void addCauseOfException(String msg, Throwable th) {
        causeOfException.add(this.getClass().getName() + " : " + msg);
        addCauseOfException(th);
    }

    private void addCauseOfException(Throwable th) {
        Throwable temp = th;
        while (Objects.nonNull(temp)) {
            causeOfException.add(temp.getClass().getName() + " : " + temp.getMessage());
            temp = temp.getCause();
        }
    }
}
