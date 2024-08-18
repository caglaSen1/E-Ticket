package com.ftbootcamp.eticketuserservice.dto.request;

import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserBulkStatusChangeRequest {

    private List<String> emailList;
    private StatusType statusType;
}
