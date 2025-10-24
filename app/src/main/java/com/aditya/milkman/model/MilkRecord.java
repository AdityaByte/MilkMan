package com.aditya.milkman.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/** MilkRecord Entity: Buyer
 * The Person which buys the milk these are the attributes that this class have-
 * PersonId - For uniquely identifying every person.
 * PersonName
 * MilkWanted
 * StartingData
 * Right now just keeping these things in future we will upgrade it too.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilkRecord {
    private String personId;
    private String personName;
    private String personAddress;
    private float milkWanted;
    private String startingDate;
}
