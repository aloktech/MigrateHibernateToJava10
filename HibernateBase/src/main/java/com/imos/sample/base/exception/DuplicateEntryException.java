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
public class DuplicateEntryException extends RepositoryException {

    private static final long serialVersionUID = 7371406155769185185L;

    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String msg) {
        super(msg);
    }

    public DuplicateEntryException(Throwable th) {
        super(th);
    }

    public DuplicateEntryException(String msg, Throwable th) {
        super(msg, th);
    }
}
