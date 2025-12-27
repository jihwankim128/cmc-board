package com.cmc.global;

import static com.cmc.user.presentation.api.status.UserSuccessStatus.USER_LOGIN_SUCCESS;

import com.cmc.board.application.port.in.CreateCategoryUseCase;
import com.cmc.board.application.port.in.CreatePostUseCase;
import com.cmc.board.application.port.in.WriteCommentUseCase;
import com.cmc.board.application.port.in.WriteReplyUseCase;
import com.cmc.board.application.port.in.command.CreatePostCommand;
import com.cmc.board.application.port.in.command.ReplyCommentCommand;
import com.cmc.board.application.port.in.command.WriteCommentCommand;
import com.cmc.global.auth.annotation.AuthRole;
import com.cmc.global.auth.annotation.PreAuth;
import com.cmc.global.auth.annotation.UserPrincipal;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.user.application.port.in.LoginUseCase;
import com.cmc.user.application.port.out.PasswordHashPort;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.domain.vo.Email;
import com.cmc.user.domain.vo.Nickname;
import com.cmc.user.domain.vo.UserRole;
import com.cmc.user.infrastructure.database.jpa.UserJpaRepository;
import com.cmc.user.infrastructure.database.jpa.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Test용 임시 컨트롤러 - 제거 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/tool")
public class TestTempController {

    private final PasswordHashPort passwordHashPort;
    private final UserRepository userRepository;
    private final LoginUseCase loginUseCase;
    private final UserJpaRepository userJpaRepository;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final CreatePostUseCase postCreateUseCase;
    private final WriteCommentUseCase writeCommentUseCase;
    private final WriteReplyUseCase writeReplyUseCase;

    @PostConstruct
    public void init() {
        String encodedPassword = passwordHashPort.encode("testPassword");
        User user = User.of(null, new Nickname("admin"), new Email("test@mail.com"), encodedPassword, UserRole.ADMIN);
        userRepository.save(user);

        createCategoryUseCase.create("백엔드");
        createCategoryUseCase.create("프론트엔드");
        createCategoryUseCase.create("기획");

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            long id = random.nextLong(3);
            postCreateUseCase.create(new CreatePostCommand(1L, id + 1, "테스트용 제목" + i, "내용 입니다" + i));
        }

        CreatePostCommand command1 = new CreatePostCommand(1L, 1L, "스프링부트", "그것은 어떻게 하는 것인가?");
        CreatePostCommand command2 = new CreatePostCommand(1L, 1L, "RDB vs NoSQL", "DB 비교를 해보자");
        CreatePostCommand command3 = new CreatePostCommand(1L, 2L, "Vanila Js와 React", "둘은 어떻게 연관되어있나?");
        CreatePostCommand command4 = new CreatePostCommand(1L, 3L, "Event Storming 이란?", "안알려줌니다");
        postCreateUseCase.create(command1);
        postCreateUseCase.create(command2);
        postCreateUseCase.create(command3);
        postCreateUseCase.create(command4);

        WriteCommentCommand writeCommentCommand1 = new WriteCommentCommand(1L, 1L, "그러게요");
        WriteCommentCommand writeCommentCommand2 = new WriteCommentCommand(1L, 2L, "네");
        writeCommentUseCase.create(writeCommentCommand1);
        writeCommentUseCase.create(writeCommentCommand2);

        ReplyCommentCommand command = new ReplyCommentCommand(1L, 1L, "댓글의 답글");
        writeReplyUseCase.reply(command);
    }

    @GetMapping("/auth/admin")
    public CommonResponse<Void> login() {
        loginUseCase.login("test@mail.com", "testPassword");
        return CommonResponse.noContent(USER_LOGIN_SUCCESS, "성공");
    }

    @GetMapping("/users/me")
    @PreAuth
    public CommonResponse<UserDto> getMe(@UserPrincipal Long userId) {
        UserEntity userEntity = userJpaRepository.findById(userId).get();
        UserDto dto = new UserDto(userEntity.getId(), userEntity.getNickname(), userEntity.getEmail(),
                userEntity.getPasswordHash(), userEntity.getRole().name());
        return CommonResponse.ok(dto, UserCode.USER_GET_ME_SUCCESS, "조회 성공");
    }

    @PreAuth(value = AuthRole.ADMIN)
    @GetMapping("/users/me/admin")
    public CommonResponse<UserDto> getMeAdmin() {
        UserEntity userEntity = userJpaRepository.findById(1L).get();
        UserDto dto = new UserDto(userEntity.getId(), userEntity.getNickname(), userEntity.getEmail(),
                userEntity.getPasswordHash(), userEntity.getRole().name());
        return CommonResponse.ok(dto, UserCode.USER_GET_ME_SUCCESS, "조회 성공");
    }

    public enum UserCode implements StatusCode {
        USER_GET_ME_SUCCESS;
    }

    public record UserDto(Long userId, String nickname, String email, String password, String role) {
    }
}
