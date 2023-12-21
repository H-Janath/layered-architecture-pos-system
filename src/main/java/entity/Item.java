package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
