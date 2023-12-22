package dto;

import lombok.*;

import java.util.List;
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto{
    private String Orderid;
    private String date;
    private String custId;
    private List<OrderDetailsDto> dto;
}