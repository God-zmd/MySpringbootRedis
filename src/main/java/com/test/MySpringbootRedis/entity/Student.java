package com.test.MySpringbootRedis.entity;



import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {
    private String field;//redis中数据的filed，可以当作数据的id
    private String stuId;//学生id
    private String stuName;//学生姓名
    private Timestamp birthday;//学生出生日期
    private String description;//描述
    private int avgscore;//平均分
}
