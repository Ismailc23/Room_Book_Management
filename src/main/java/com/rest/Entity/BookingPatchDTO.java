package com.rest.Entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BookingPatchDTO
{
    private LocalDate bookedDate;

    private LocalDate stayStartDate;

    private LocalDate stayEndDate;

}
