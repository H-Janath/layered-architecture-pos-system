package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class OrderDetails {
    @Id
    private String OrderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
