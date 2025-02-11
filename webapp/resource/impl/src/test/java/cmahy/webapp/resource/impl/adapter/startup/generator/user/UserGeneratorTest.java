//package cmahy.webapp.resource.impl.adapter.startup.generator.user;
//
//import cmahy.common.helper.Generator;
//import cmahy.webapp.user.kernel.application.repository.RoleRepository;
//import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
//import cmahy.webapp.user.kernel.domain.Role;
//import cmahy.webapp.user.kernel.domain.UserSecurity;
//import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilder;
//import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
//import cmahy.webapp.user.kernel.exception.RoleNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Answers.RETURNS_SELF;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserGeneratorTest {
//
//    @Mock
//    private UserSecurityRepository<UserSecurity> userSecurityRepository;
//
//    @Mock
//    private RoleRepository<Role> roleRepository;
//
//    @Mock
//    private UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserGenerator userGenerator;
//
//    @Mock
//    private Role role;
//
//    @Mock(answer = RETURNS_SELF)
//    private UserSecurityBuilder<UserSecurity> userSecurityBuilder;
//
//    @Mock
//    private UserSecurity userSecurity;
//
//    @Mock
//    private ApplicationArguments applicationArguments;
//
//    @Test
//    void run() {
//        assertDoesNotThrow(() -> {
//            when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
//            when(passwordEncoder.encode(anyString())).thenReturn(Generator.generateAString());
//            when(userSecurityBuilderFactory.create()).thenReturn(userSecurityBuilder);
//            when(userSecurityBuilder.build()).thenReturn(userSecurity);
//
//            userGenerator.run(applicationArguments);
//
//            verify(userSecurityRepository, times(2)).save(userSecurity);
//        });
//    }
//
//    @Test
//    void run_whenRoleNotFound_thenThrowException() {
//        assertThrows(RoleNotFoundException.class, () -> {
//            when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
//
//            userGenerator.run(applicationArguments);
//        });
//    }
//}