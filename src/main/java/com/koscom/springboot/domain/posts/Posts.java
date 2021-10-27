package com.koscom.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //디폴트 생성자
@Entity
public class Posts {

    @Id // pk
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // PK 채번 방식
    private Long id; //pk (auto increment, bigint)

    @Column(length = 500, nullable = false) //varchar(500), notnull
    private String title;

    @Column(length = 2000, nullable = false) //varchar(2000), notnull
    private String content;

    private String author; //@Column 없으면 varchar(255), nullable = true

    @Builder // lombok의 builder
    public Posts(String title, String content, String author) { //id가 없고 나머지 필드는 있는 생성자
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // title과 content만 수정 가능하다
    // author 수정 가능하지 않다
    // 수정일자도 신규로 생성된다
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}