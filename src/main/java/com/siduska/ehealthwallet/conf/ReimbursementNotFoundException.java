package com.siduska.ehealthwallet.conf;


public class ReimbursementNotFoundException extends RuntimeException {
    public ReimbursementNotFoundException(Long id) {
        super("Reimbursement with id " + id + " not found.");
    }
}
