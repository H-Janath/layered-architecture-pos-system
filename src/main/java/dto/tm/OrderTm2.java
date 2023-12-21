package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderTm2 extends RecursiveTreeObject<OrderTm2> {
    private String id;
    private String date;
    private String customerId;
}
