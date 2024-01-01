package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Item {
    @Id
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
