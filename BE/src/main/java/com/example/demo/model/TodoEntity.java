package com.example.demo.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
@Getter
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    @Setter
    private String id; // 이 오브젝트의 아이디
    @Setter
    private String userId;  // 이 오브젝트를 생성한 유저의 아이디

    private String title;   // todo타이틀
    private boolean done; // true - todo를 완료한 경우(checked)

}
