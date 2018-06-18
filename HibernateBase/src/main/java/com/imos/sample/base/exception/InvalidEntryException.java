/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.base.exception;

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
public class InvalidEntryException extends RepositoryException {

    private static final long serialVersionUID = 771812529743890808L;

    public InvalidEntryException() {
    }

    public InvalidEntryException(String msg) {
        super(msg);
    }

    public InvalidEntryException(Throwable th) {
        super(th);
    }

    public InvalidEntryException(String msg, Throwable th) {
        super(msg, th);
    }
}
