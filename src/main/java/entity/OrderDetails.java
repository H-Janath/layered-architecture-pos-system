package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetails {
    private String OrderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
