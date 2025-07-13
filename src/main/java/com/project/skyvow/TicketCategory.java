package com.project.skyvow;

enum TicketStatus{
    OPEN(1),
    NEW(2),
    IN_PROGRESS(3),
    RESOLVED(4),
    HOLD(5),
    WAITING_FOR_CUSTOMER_REVIEW(6),
    WAITING_FOR_VENDOR(7),
    CLOSED(8);

    private final int code;

    TicketStatus(int code) {
        this.code=code;
    }
    public int getCode(){
        return code;
    }

    public static TicketStatus fromString(String status){
        return TicketStatus.valueOf(status.toUpperCase());
    }
}