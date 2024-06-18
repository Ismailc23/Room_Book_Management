package com.rest.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class BookingPatchDTO
{
    private Date bookedDate;

    private Date stayStartDate;

    private Date stayEndDate;

}
