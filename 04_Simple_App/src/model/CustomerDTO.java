package model;

import lombok.*;

/**
 * @author : Sandun Induranga
 * @since : 0.1.0
 **/

@Setter
@Getter
@AllArgsConstructor
@ToString
public class CustomerDTO {
    String id;
    String name;
    String address;
    int salary;
}
