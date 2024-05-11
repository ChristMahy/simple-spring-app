package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.mapper.PageableInputAppVoMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderPageOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.ClientOrderPagingRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.page.ClientOrderPage;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllClientOrderPagedQueryTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientOrderPagingRepository clientOrderPagingRepository;

    @Mock
    private ClientOrderPageOutputMapper clientOrderPageOutputMapper;

    @Mock
    private PageableInputAppVoMapper pageableInputAppVoMapper;

    @InjectMocks
    private GetAllClientOrderPagedQuery getAllClientOrderPagedQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            User user = mock(User.class);
            PageableInputAppVo pageableInputVo = mock(PageableInputAppVo.class);
            EntityPageable entityPageable = mock(EntityPageable.class);
            ClientOrderPage clientOrderPage = mock(ClientOrderPage.class);
            ClientOrderPageOutputAppVo clientOrderPageOutputAppVo = mock(ClientOrderPageOutputAppVo.class);

            when(userRepository.findById(userId.value())).thenReturn(Optional.of(user));
            when(pageableInputAppVoMapper.map(pageableInputVo)).thenReturn(entityPageable);
            when(clientOrderPagingRepository.getByUser(user, entityPageable)).thenReturn(clientOrderPage);
            when(clientOrderPageOutputMapper.map(clientOrderPage)).thenReturn(clientOrderPageOutputAppVo);

            ClientOrderPageOutputAppVo actual = getAllClientOrderPagedQuery.execute(userId, pageableInputVo);

            assertThat(actual).isEqualTo(clientOrderPageOutputAppVo);
        });
    }

    @Test
    void execute_whenUserNotFound_thenThrowException() {
        assertThrows(UserNotFoundException.class, () -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            PageableInputAppVo pageableInputVo = mock(PageableInputAppVo.class);

            when(userRepository.findById(userId.value())).thenReturn(Optional.empty());

            getAllClientOrderPagedQuery.execute(userId, pageableInputVo);
        });
    }
}