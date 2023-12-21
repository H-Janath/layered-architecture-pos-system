package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderDto2 extends RecursiveTreeObject<OrderDto2> {
    private String id;
    private String date;
    private String customerId;
}
