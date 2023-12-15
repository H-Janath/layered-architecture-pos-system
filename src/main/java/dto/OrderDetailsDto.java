package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class OrderDetailsDto {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitprice;
}
