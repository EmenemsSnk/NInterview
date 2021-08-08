package com.mdziedzic.interview.samplerestservice.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date joinedDate;
}
