package com.example.ch3.section5_room

import androidx.room.Entity
import androidx.room.PrimaryKey


// db 에 저장되는 회원 정보를 추상화시킨 Entity 클래스
// @Entity 를 추가하는 것 만으로 멤버들을 저장하기 위한 테이블이 자동으로 준비
// 테이블 명은 클래스 명, Entity 클래스의 멤버 변수가 테이블의 column
// @PrimaryKey 가 추가된 멤버의 column 이 primary key -> 중복 허용 X
@Entity
class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var lastName: String,
    var firstName: String
)