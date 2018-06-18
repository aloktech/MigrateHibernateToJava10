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
public class RepositoryException extends IPBaseException {

    private static final long serialVersionUID = 9052470689390443476L;

    public RepositoryException() {
    }

    public RepositoryException(String msg) {
        super(msg);
    }

    public RepositoryException(Throwable th) {
        super(th);
    }

    public RepositoryException(String msg, Throwable th) {
        super(msg, th);
    }
}
