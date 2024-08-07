package taka.takaspring.Member.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import taka.takaspring.Member.db.UserEntity;

public class UserInfoDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfoRequest {

        @Size(min = 8, max = 20)
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대, 소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;
        @Size(min = 1, max = 20)
        private String name;
        private String major;
        private String studentNum;
        private String phoneNumber;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInfoResponse{

        private String email;
        private String password;
        private String name;
        private String major;
        private String studentNum;
        private String phoneNumber;

        @Builder
        public UserInfoResponse(UserEntity user){
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.name = user.getName();
            this.major = user.getMajor();
            this.studentNum = user.getStudentNum();
            this.phoneNumber = user.getPhoneNumber();
        }
    }
}
